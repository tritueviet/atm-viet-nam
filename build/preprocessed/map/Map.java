/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package map;

import Control.Controller;
import com.nokia.lwuit.components.HeaderBar;
import com.nokia.maps.common.ApplicationContext;
import com.nokia.maps.common.GeoCoordinate;
import com.nokia.maps.common.Location;
import com.nokia.maps.map.MapCircle;
import com.nokia.maps.map.MapComponent;
import com.nokia.maps.map.MapDisplay;
import com.nokia.maps.map.MapFactory;
import com.nokia.maps.map.MapListener;
import com.nokia.maps.map.MapMarker;
import com.nokia.maps.map.MapStandardMarker;
import com.nokia.maps.map.Point;
import com.nokia.maps.routing.Route;
import com.nokia.maps.routing.RouteFactory;
import com.nokia.maps.routing.RouteListener;
import com.nokia.maps.routing.RouteManeuver;
import com.nokia.maps.routing.RouteRequest;
import com.nokia.maps.routing.RoutingMode;
import com.nokia.maps.routing.RoutingType;
import com.nokia.maps.routing.TransportMode;
import com.nokia.maps.routing.WaypointParameterList;
import com.nokia.maps.search.GeocodeRequest;
import com.nokia.maps.search.GeocodeRequestListener;
import com.nokia.maps.search.SearchFactory;
import com.sun.lwuit.Button;
import com.sun.lwuit.Component;
import com.sun.lwuit.Form;
import com.sun.lwuit.Graphics;
import com.sun.lwuit.Label;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Vector;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Image;
import javax.microedition.location.LocationListener;
import javax.microedition.location.LocationProvider;
import javax.microedition.location.QualifiedCoordinates;
import models.ATMLocation;
import models.Text;

/**
 *
 * @author PHAN THAI
 */
public class Map extends Component implements MapListener, RouteListener {

    private static MapCanvas mapCanvas;
    private static MapDisplay mapDisplay;
    private static MapFactory factory;
   private final String APP_ID = "s3R07afTi7PEybn4bB7Z";
    private final String TOKEN_ID = "jB9o6LBWygIpTcEZiDdtFA";
    private Display display;
    private int markerCount;
    private Hashtable markerDataTable;
    private Image helperImg;
    private javax.microedition.lcdui.Graphics helperImgGraphics;
    RouteManeuver currentManeuver = null;
    MapStandardMarker currentMarker = null;
    private MapCircle currentPositionCircle;
    private Image img, diaChi;
    boolean clicking, movingMarker;
    double lat, lon;
    boolean hien = true;
    int dau = 0, cuoi = 0;
    Vector addmapm;

    public Map(Display display) {
        this.display = display;

        //      setWidth(100);
        //       setHeight(100);
        createMap();
        //  getStyle().setBgColor(0x3333);
//        addComponent(new Button("..."));
    }

    private void createMap() {
        
//        HeaderBar header = null;
//        try {
//            header = new HeaderBar(Text.BanDo, com.sun.lwuit.Image.createImage("/images/icon3.png"), null);
//        } catch (IOException ex) {
//            ex.printStackTrace();
//        }
//        header.setHeaderTitleColor(Text.colorWhite);
//        header.getStyle().setBgColor(Text.hearder);
//        
//        
//        header.setScrollable(false);
//        
//
//        header.setHeaderBarListener(this);
//        
//        
//        addComponent(header);
//        
//        
        ApplicationContext.getInstance().setAppID(APP_ID);
        ApplicationContext.getInstance().setToken(TOKEN_ID);
        mapCanvas = new MapCanvas(display);
        factory = mapCanvas.getMapFactory();
        mapDisplay = mapCanvas.getMapDisplay();
        mapDisplay.setMapListener(this);

        mapDisplay.setZoomLevel(15, 0, 0);
        setFocusable(true);
        markerCount = 0;
        markerDataTable = new Hashtable();

        repaint();
    }

    /**
     * Ve duong di ngan nhat giua 2 dia diem
     * @param p1 Dia diem dau
     * @param p2 Dia diem cuoi
     * @param transportMode TransportMode (CAR, ....)
     */
    public void drawRoute(double lat1, double lon1, double lat2, double lon2) {
        mapDisplay.setZoomLevel(14, 0, 0);
        WaypointParameterList waypoints = new WaypointParameterList();
        waypoints.addCoordinate(new GeoCoordinate(lat1, lon1, 0.0f));
        waypoints.addCoordinate(new GeoCoordinate(lat2, lon2, 0.0f));

        RoutingMode[] routingModes = new RoutingMode[1];
        routingModes[0] = new RoutingMode(
                RoutingType.FASTEST,
                new int[]{TransportMode.CAR},
                new String[0]);

        RouteFactory rf = RouteFactory.getInstance();

        rf.createRouteRequest().calculateRoute(waypoints, routingModes, this);
        
        Vector v = new Vector(2);
        v.addElement(new ATMLocation(lat1, lon1));
        v.addElement(new ATMLocation(lat2, lon2));
        // addmapm=v;
        addMarker2(v);
        repaint();
    }
  public void zoom(int k) {
       mapDisplay.setZoomLevel(15, 0, 0);
       setFocus(true);
//       clicking = true;
//        movingMarker = false;
//        try {
//            Thread.sleep(500);
//        } catch (InterruptedException ex) {
//            ex.printStackTrace();
//        }
//        if (k>13) {
//            clicking = false;
//            movingMarker = true;
//            mapDisplay.setCenter(mapDisplay.pixelToGeo(new Point(mapCanvas.getWidth() / 2, mapCanvas.getHeight() / 2 -10)));
//            mapCanvas.repaint();
//        }
        
    }
    public void paint(Graphics g) {
        super.paint(g);
        //g.drawLine(6, 5, 100, 100);
        if (helperImg == null) {
            helperImg = javax.microedition.lcdui.Image.createImage(getWidth(), 275);
            helperImgGraphics = helperImg.getGraphics();
        }
        mapDisplay.paint(helperImgGraphics);
        MapComponent[] comps = mapDisplay.getAllMapComponents();
        for (int i = 0; i < comps.length; i++) {
            comps[i].paint(helperImgGraphics);
        }
        com.sun.lwuit.Image img = com.sun.lwuit.Image.createImage(helperImg);
        g.drawImage(img, 0, 0);

    }

    /*xu li su kien keo map tai day */
    public void pointerPressed(int x, int y) {
     //  System.out.println("x::"+x+"   y: "+y);
        if (x < 235 && x > 210 && y < 110 && y > 85) {
            // zom ++
            if (mapDisplay.getZoomLevel() + 1 <= mapDisplay.getMaxZoomLevel()) {
                mapDisplay.setZoomLevel(mapDisplay.getZoomLevel() + 1, 0, 0);
            }
            setFocusable(true);
        } else if (x < 235 && x > 210 && y < 175 && y > 150) {
            //zom --
            if (mapDisplay.getZoomLevel() - 1 >= mapDisplay.getMinZoomLevel()) {
                mapDisplay.setZoomLevel(mapDisplay.getZoomLevel() - 1, 0, 0);
            }
            setFocusable(true);
        }
        dau = x;
        cuoi = y;
//        mapDisplay.setCenter(mapDisplay.pixelToGeo(new Point(x, y)));
//        
//        mapCanvas.repaint();
        clicking = true;
        movingMarker = false;
        //    repaint();
    }


    public void pointerDragged(int x, int y) {
        if (mapDisplay != null) {

            clicking = false;
            movingMarker = true;
            // System.out.println("mapCanvas.getWidth()/2: "+mapCanvas.getWidth()/2+"  mapCanvas.getWidth()/2: "+mapCanvas.getWidth()/2);
            mapDisplay.setCenter(mapDisplay.pixelToGeo(new Point(mapCanvas.getWidth() / 2 - x + dau, mapCanvas.getHeight() / 2 - y + cuoi)));

            mapCanvas.repaint();
            dau = x;
            cuoi = y;
        } else {
            movingMarker = clicking = false;
        }
    }

    public void pointerReleased(int x, int y) {
        clicking = movingMarker = false;
    }

    public void onMapContentComplete() {
        repaint();
    }

    public void onMapContentUpdated() {
        repaint();
    }

    public void onMapUpdateError(String string, Throwable thrwbl, boolean bln) {
        repaint();
    }

    public MapDisplay getMapDisplay() {
        return mapDisplay;
    }

    // Danh dau vi tri
    public void addMarker(Vector loc) {
        // my code
        //Remove all markers
        addmapm = loc;
        mapDisplay.removeAllMapObjects();
        if (currentPositionCircle != null) {
            mapDisplay.addMapObject(currentPositionCircle);
        }

 
        Font font2 = Font.getFont(Font.FACE_MONOSPACE, Font.STYLE_PLAIN, Font.SIZE_SMALL);
        diaChi = Image.createImage(Text.Items.getAddr().length() * 7, 38);
        javax.microedition.lcdui.Graphics g = diaChi.getGraphics();
        g.setColor(Text.hearder);
        g.fillRect(0, 0, diaChi.getWidth(), diaChi.getHeight());

//        g.drawImage(img, 0, 0, javax.microedition.lcdui.Graphics.TOP | javax.microedition.lcdui.Graphics.LEFT);
        g.setColor(0xffffff);

        g.setFont(font2);
        String[] s = util.Util.getAtmNameBank(Text.Items.getName());
        g.drawString(s[0], 2, 2, javax.microedition.lcdui.Graphics.LEFT | javax.microedition.lcdui.Graphics.TOP);
        g.drawString(Text.Items.getAddr(), 2, 18, javax.microedition.lcdui.Graphics.LEFT | javax.microedition.lcdui.Graphics.TOP);

        int n = loc.size();
        ATMLocation atm;
        for (int i = 0; i < n; ++i) {
            atm = (ATMLocation) loc.elementAt(i);
            GeoCoordinate c = new GeoCoordinate(atm.getLat(), atm.getLon(), 0);

            MapStandardMarker marker = factory.createStandardMarker(c);// .createStandardMarker(c, i+1, "", MapStandardMarker.BALLOON);//factory.createMapMarker(c, img);
            MapMarker marker2 = factory.createMapMarker(c, diaChi);
            //   marker.setAnchor(new Point(img.getWidth()/2, img.getHeight()));
            marker2.setAnchor(new Point(diaChi.getWidth() / 2, 2 * diaChi.getHeight() + 2));

            mapDisplay.addMapObject(marker);
            mapDisplay.addMapObject(marker2);
        }
    }

    public void addMarker2(Vector loc) {
        // my code
        //Remove all markers
        addmapm = loc;
        mapDisplay.removeAllMapObjects();
        if (currentPositionCircle != null) {
            mapDisplay.addMapObject(currentPositionCircle);
        }

        if (img == null) {
            try {
                img = Image.createImage("/images/pin_icon.png");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        Font font2 = Font.getFont(Font.FACE_MONOSPACE, Font.STYLE_PLAIN, Font.SIZE_SMALL);
        diaChi = Image.createImage(Text.Items.getAddr().length() * 7, 38);
        javax.microedition.lcdui.Graphics g = diaChi.getGraphics();
        g.setColor(Text.hearder);
        g.fillRect(0, 0, diaChi.getWidth(), diaChi.getHeight());

//        g.drawImage(img, 0, 0, javax.microedition.lcdui.Graphics.TOP | javax.microedition.lcdui.Graphics.LEFT);
        g.setColor(0xffffff);

        g.setFont(font2);
        String[] s = util.Util.getAtmNameBank(Text.Items.getName());
        g.drawString(s[0], 2, 2, javax.microedition.lcdui.Graphics.LEFT | javax.microedition.lcdui.Graphics.TOP);
        g.drawString(Text.Items.getAddr(), 2, 18, javax.microedition.lcdui.Graphics.LEFT | javax.microedition.lcdui.Graphics.TOP);

        int n = loc.size();
        ATMLocation atm;
        for (int i = 0; i < n; ++i) {
            atm = (ATMLocation) loc.elementAt(i);
            GeoCoordinate c = new GeoCoordinate(atm.getLat(), atm.getLon(), 0);
            if (i == n - 1) {
                MapStandardMarker marker = factory.createStandardMarker(c, i + 1, "", MapStandardMarker.BALLOON);//factory.createMapMarker(c, img);
                mapDisplay.addMapObject(marker);
                MapMarker marker2 = factory.createMapMarker(c, diaChi);
                marker2.setAnchor(new Point(diaChi.getWidth() / 2, 2 * diaChi.getHeight() + 2));
                mapDisplay.addMapObject(marker2);
                mapDisplay.setCenter(c);
            }
            if (i == 0) {
                MapMarker marker = factory.createMapMarker(c, img);
                marker.setAnchor(new Point(img.getWidth() / 2, img.getHeight()));
                mapDisplay.addMapObject(marker);
            }

        }
    }

    public void onRequestComplete(RouteRequest rr, Route[] routes) {
        if (routes.length > 0) {
            Route firstRoute = routes[0];
            //   ;
            mapDisplay.addMapObject(factory.createMapPolyline(firstRoute.getShape(), 3));
            setCurrentManeuver(firstRoute.getFirstManeuver());
        } else {
            System.out.println("Router not found");
        }
        repaint();
    }

    public void onRequestError(RouteRequest rr, Throwable thrwbl) {
        System.out.println(rr.toString());
    }

    private synchronized void setCurrentManeuver(RouteManeuver maneuver) {
//        if (currentMarker == null) {
//            currentMarker = factory.createStandardMarker(maneuver.getPosition());
//            mapDisplay.addMapObject(currentMarker);
//        } else {
//            currentMarker.setCoordinate(maneuver.getPosition());
//        }

        currentManeuver = maneuver;
        mapDisplay.setCenter(currentManeuver.getPosition());

        repaint();
    }

    public void setPlace(double lat, double lon) {
        mapDisplay.setZoomLevel(14, 0, 0);
        if (helperImg != null) {
            helperImg = null;
        }
        this.lat = lat;
        this.lon = lon;
        Vector v = new Vector();
        v.addElement(new ATMLocation(lat, lon));
        addMarker(v);
        mapDisplay.setCenter(new GeoCoordinate(lat, lon, 0));
        repaint();
    }

    public void huy() {
       
    }
}
