/**
* Copyright (c) 2012-2013 Nokia Corporation. All rights reserved.
* Nokia and Nokia Connecting People are registered trademarks of Nokia Corporation. 
* Oracle and Java are trademarks or registered trademarks of Oracle and/or its
* affiliates. Other product and company names mentioned herein may be trademarks
* or trade names of their respective owners. 
* See LICENSE.TXT for license information.
*/
package location;


/**
 * Abstract location finder.
 * <P>
 * Extended by the class AbstractLocationFinder.
 * @see com.nokia.example.attractions.location.AbstractLocationFinder
 */
public abstract class LocationFinder {

    private static final String[] S40_GPS_DEVICES = {"2710", "6350", "6750",
        "3710", "6700", "6260"};

    /**
     * Initialize the location finder.
     */
    protected void init(Listener listener, NotFoundLocation notFoundLocation)
        throws InitializationException, SecurityException {
        this.listener = listener;
        this.notFoundLocation = notFoundLocation;
    }

    /**
     * Start finding location.
     */
    public abstract void start();

    /**
     * Stop finding location.
     */
    public abstract void quit();
    protected Listener listener;
    protected NotFoundLocation notFoundLocation;
    
    /**
     * Interface for notifying when the location has changed.
     */
    public interface Listener {

        void newLocation(double lat, double lon, int accuracy);
    }
    
    public interface NotFoundLocation {
        void actionPerforment();
    }

    /**
     * Creates a location finder based on the capabilities the device.
     * @param listener
     * @return 
     */
    public static LocationFinder getFinder(Listener listener, NotFoundLocation notFoundLocation) {
        if (listener == null) {
            throw new NullPointerException("listener not defined");
        }

        LocationFinder finder = null;
        try {
            // this will throw an exception if JSR-179 is missing
            Class.forName("javax.microedition.location.Location");

            if (finder == null && supportsGPS()) {
                Class c =
                    Class.forName(
                    "location.GpsLocationFinder");
                finder = (LocationFinder) (c.newInstance());
                try {
                    finder.init(listener, notFoundLocation);
                }
                catch (InitializationException e) {
                    finder = null;
                }
            }
            if (finder == null && supportsCellId()) {
                Class c =
                    Class.forName(
                    "location.CellIdLocationFinder");
                finder = (LocationFinder) (c.newInstance());
                try {
                    finder.init(listener, notFoundLocation);
                }
                catch (InitializationException e) {
                    finder = null;
                }
            }
        }
        catch (Exception e) {
            finder = null;
        }
        if(finder == null)
            notFoundLocation.actionPerforment();
        return finder;
    }

    private static boolean supportsGPS() {
        String platform = System.getProperty("microedition.platform");
        if (platform != null) {
            for (int i = 0; i < S40_GPS_DEVICES.length; i++) {
                if (platform.indexOf(S40_GPS_DEVICES[i]) > -1) {
                    System.out.println("TRUE");
                    return true;
                }
            }
        }
        System.out.println("FALSE");
        return false;// Main.isS60Phone();
    }

    private static boolean supportsCellId() {
        try {
            Class.forName("com.nokia.mid.location.LocationUtil");
            System.out.println("CELL ID " + true);
            return true;
        }
        catch (Exception e) {
            System.out.println("FALSE");
            return false;
        }
    }

    protected static class InitializationException
        extends Exception {

        public InitializationException() {
        }

        public InitializationException(String s) {
            super(s);
        }
    }
}
