/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package map;

import com.nokia.maps.common.GeoCoordinate;
import com.nokia.mid.ui.gestures.GestureEvent;
import com.nokia.mid.ui.gestures.GestureInteractiveZone;
import com.nokia.mid.ui.gestures.GestureListener;
import com.nokia.mid.ui.gestures.GestureRegistrationManager;
import javax.microedition.lcdui.Display;

/**
 *
 * @author PHAN THAI
 */
public class MapCanvas extends com.nokia.maps.map.MapCanvas {
    
    private Object gestureListener;

    private GeoCoordinate center;
    private boolean pinching;
    private boolean hidden;
    
    /**
     * @param display
     */
    public MapCanvas(Display display) {
        
        super(display);
        
    }

    public void pointerPressed(int x, int y) {
        super.pointerPressed(x, y);
    }

    public void pointerReleased(int x, int y) {
        
        if (!pinching) {
            super.pointerReleased(x, y);
        }
    }

    public void pointerDragged(int x, int y) {
        if (!pinching) {
            super.pointerDragged(x, y);
        }
    }

    public void onMapContentComplete() {
        super.onMapContentUpdated();
    }

    public void onMapUpdateError(String err, Throwable t, boolean critical) {
    }

    public Display getDisplay() {
        return display;
    }
    
    /**
     * @see MapCanvas#showNotify() 
     */
    protected final void showNotify() {
        super.showNotify();
        hidden = false;
       // Main.getInstance().refreshLocationFinder();
        if (gestureListener != null) {
            GestureRegistrationManager.setListener(this, (GestureListener) gestureListener);
            GestureInteractiveZone gestureZone = new GestureInteractiveZone(
                    GestureInteractiveZone.GESTURE_ALL);
            GestureRegistrationManager.register(this, gestureZone);
        }
    }

    /**
     * @see MapCanvas#hideNotify() 
     */
    protected final void hideNotify() {
        hidden = true;
     //   Main.getInstance().refreshLocationFinder();
        super.hideNotify();
        if (gestureListener != null) {
            GestureRegistrationManager.unregisterAll(this);
        }
    }

    
}
