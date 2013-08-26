/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import javax.microedition.lcdui.Image;

/**
 *
 * @author cdit
 */
public class Util {
    private static final double d2r = (Math.PI / 180D);
    private static final double R = 6371;    //6,371km 
    
    /**
     * Return format string around two number of distance.
     * @param distance
     * @return format string distance.
     */
    public static float formatDistance(float distance) {
        float mdistane = distance*100;
        mdistane = (float) Math.ceil(mdistane);
        mdistane /= 100;
        return mdistane;
    }
    
    /**
     * Distance from point(lon1, lat1) to point(lon2, lat2)
     *
     * @param lon1 Longitude point1
     * @param lat1 Latitude point1
     * @param lon2 Longitude point2
     * @param lat2 Latitude point2
     * @return distance with round 2 numbers.
     */
    public static float getDistance(double long1, double lat1, double long2, double lat2) {
        final double dlong = (long2 - long1) * d2r;
        final double dlat = (lat2 - lat1) * d2r;
        //calculate _sinDlat seperate to improve the performance
        double _sinDlat = Math.sin(dlat / 2.0);
        double _sinDlong = Math.sin(dlong / 2.0);
        
        _sinDlat *= _sinDlat;
        _sinDlong *= _sinDlong;
        
        double a = _sinDlat + Math.cos(lat1 * d2r) * Math.cos(lat2 * d2r) * _sinDlong;
        
        a = Math.sqrt(a);
        float distance = (float) (2*R*MathUtil.asin(a));
        return formatDistance(distance);
    }
    
    /**
     * Convert object to integer.
     * @param obj
     * @return 
     */
    public static int objectToInt(Object obj) {
        return Integer.parseInt(obj.toString());
    }
    
    public static Image scaleImage(Image original, int newWidth,
        int newHeight) {
        int[] rawInput = new int[original.getHeight() * original.getWidth()];
        original.getRGB(rawInput, 0, original.getWidth(), 0, 0, original.
            getWidth(), original.getHeight());

        int[] rawOutput = new int[newWidth * newHeight];

        // YD compensates for the x loop by subtracting the width back out
        int YD = (original.getHeight() / newHeight) * original.getWidth()
            - original.getWidth();
        int YR = original.getHeight() % newHeight;
        int XD = original.getWidth() / newWidth;
        int XR = original.getWidth() % newWidth;
        int outOffset = 0;
        int inOffset = 0;

        for (int y = newHeight, YE = 0; y > 0; y--) {
            for (int x = newWidth, XE = 0; x > 0; x--) {
                rawOutput[outOffset++] = rawInput[inOffset];
                inOffset += XD;
                XE += XR;
                if (XE >= newWidth) {
                    XE -= newWidth;
                    inOffset++;
                }
            }
            inOffset += YD;
            YE += YR;
            if (YE >= newHeight) {
                YE -= newHeight;
                inOffset += original.getWidth();
            }
        }
        return Image.createRGBImage(rawOutput, newWidth, newHeight, true);
    }
    
    public static String[] getAtmNameBank(String str) {
        int k = str.indexOf((int)'|');
        String[] s = new String[2];
        s[0] = str.substring(0, k);
        s[1] = str.substring(k+1);
        return s;
    }
    
}
