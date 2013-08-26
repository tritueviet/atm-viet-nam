/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import Control.Controller;
import com.nokia.lwuit.GestureHandler;
import com.nokia.lwuit.components.ContextMenu;
import com.nokia.mid.ui.gestures.GestureEvent;
import models.Text;
import ads.AdBanner;
import ads.NaxForm;
import com.nokia.lwuit.components.HeaderBar;
import com.sun.lwuit.ButtonGroup;
import com.sun.lwuit.Command;
import com.sun.lwuit.Container;
import com.sun.lwuit.Dialog;
import com.sun.lwuit.Display;
import com.sun.lwuit.Font;
import com.sun.lwuit.Form;
import com.sun.lwuit.Graphics;
import com.sun.lwuit.Image;
import com.sun.lwuit.Label;
import com.sun.lwuit.List;
import com.sun.lwuit.Painter;
import com.sun.lwuit.TextField;
import com.sun.lwuit.events.ActionEvent;
import com.sun.lwuit.events.ActionListener;
import com.sun.lwuit.geom.Rectangle;
import com.sun.lwuit.layouts.BorderLayout;
import com.sun.lwuit.layouts.BoxLayout;
import com.sun.lwuit.list.GenericListCellRenderer;
import event.Event;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Vector;
import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.AlertType;
import models.ATM;
import util.StorageData;
import util.Util;

/**
 *
 * @author PhongHoang
 */
public class FormNearestMe extends NaxForm implements ActionListener {

    TextField khoangcach;
    ButtonGroup radioButtonGroup;
    List list;
    private Image img;
    Vector items;

    public FormNearestMe(String txt, final Vector items) {
        
        Display.getInstance().setForceFullScreen(true);
        
        HeaderBar header = null;
        try {
            header = new HeaderBar(txt);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        header.setHeaderTitleColor(Text.colorWhite);
        header.getStyle().setBgColor(Text.hearder);
        header.setScrollable(false);
       // header.setVisible(true);
        
        
        
        // super(Text.GanBan);
        //super(txt);
        Controller.tap = 1;
        this.items = items;
//         Controller.getInstance().loadTheme(1);
//                        refreshTheme();
        try {
            if (img == null) {
                img = Image.createImage("/images/pin_icon.png");
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
       // setScrollable(false);
        
        
        
        
        if (items.size() == 0) {
            
            addComponent(BorderLayout.NORTH,header);
            
            
            Label nullResuilts = new Label(Text.khongCoKqtQua);
            nullResuilts.getStyle().setMargin(10, 10, 10, 10);
            nullResuilts.getStyle().setBgTransparency(0);
            nullResuilts.getStyle().setFgColor(Text.colorWhite);
            
            Container containn= new Container();
            containn.addComponent(nullResuilts);
            containn.setScrollableY(true);
            addComponent(BorderLayout.CENTER,containn);
        } else {
            
            //setLayout(new BorderLayout());
            addComponent(BorderLayout.NORTH,header);
            
            setScrollable(false);
            list = new List(createGenericListCellRendererModelData());
            list.setRenderer(new GenericListCellRenderer(createGenericRendererContainer(), createGenericRendererContainer()));
            
            Container container2 = new Container();
            container2.addComponent(list);
            container2.setScrollableY(true);
            addComponent(BorderLayout.CENTER, container2);

            //Container container4 = new Container();
            //Container4.getStyle().setMargin(0, 0, 13, 9);

//            container4.addComponent(new AdBanner("SRC_ATM_WALL_Nokia", 65));
//            container4.getStyle().setMargin(0, 40, 10, 10);
//            container4.setScrollable(false);
            
            //addComponent(BorderLayout.SOUTH, container4);

            this.items = items;
           //  updateData();
            list.addActionListener(this);
            
        }
         enableAds("CDiT_ATM_WP", 30000);
    }

    private void updateData() {


        GestureHandler longtap = new GestureHandler(GestureHandler.GESTURE_LONG_PRESS) {

            public void gestureAction(GestureEvent ge) {
                int x = ge.getStartX();
                int y = ge.getStartY();

                System.out.println("x :" + x + "y: " + y);

                if (list.contains(x, y)) {


                    Display.getInstance().callSerially(new Runnable() {

                        String[] menulist = {Text.BanDo, Text.DanDuong, Text.ThemUaThich};

                        public void run() {
                            int selected = list.getSelectedIndex();
                            ATM atmmm;
                            atmmm = (ATM) items.elementAt(selected);
                            Command[] cmds;

                            cmds = new Command[menulist.length];
                            for (int i = 0; i < cmds.length; i++) {
                                cmds[i] = new Command(menulist[i]);
                            }


                            Command selectedContextCmd = ContextMenu.show(cmds, list);
                            // xem map
                            if (selectedContextCmd == cmds[0]) {

                                Event evt = new Event();
                                evt.setData("ATM", items.elementAt(selected));
                                Controller.getInstance().handleEvent(Event.VIEW_MAP, evt);
                                Controller.getInstance().categoryBar.setVisibility(false);
                                Controller.getInstance().catrgoryBar2.setVisibility(true);
                                Controller.getInstance().catrgoryBar2.setSelectedIndex(0);

                            } else if (selectedContextCmd == cmds[1]) {
                                //  dan duong ban do tai day okie 
                                Event evt = new Event();
                                evt.setData("ATM", items.elementAt(selected));
                                Controller.getInstance().handleEvent(Event.VIEW_MAP_CONDUCTOR, evt);

                                Controller.getInstance().categoryBar.setVisibility(false);
                                Controller.getInstance().catrgoryBar2.setVisibility(true);
                                Controller.getInstance().catrgoryBar2.setSelectedIndex(1);
                            } else if (cmds.length == 3) {
                                if (selectedContextCmd == cmds[2]) {
                                    // them ua thich
                                    if (StorageData.getInstance().hasFavorite(atmmm.getId())) {
//                                        Alert a = new Alert(Text.Loi, Text.TonTaiTrongUaThich, null, AlertType.INFO);
//                                        javax.microedition.lcdui.Display.getDisplay(Controller.getInstance().main).setCurrent(a);
//                                        Dialog.show(Text.Loi, Text.TonTaiTrongUaThich,Text.Ok, null);
                                        Controller.getInstance().showMessenger(Text.Loi, Text.TonTaiTrongUaThich);
                                    } else {
                                        Event evt = new Event();
                                        evt.setData("ATM", items.elementAt(selected));

                                        Controller.getInstance().handleEvent(evt.ADD_FAVARITES, evt);
                                    }
                                }
                            }

                        }
                    });
                }
            }
        };
        GestureHandler.setFormGestureHandler(this, longtap);

    }

    private Container createGenericRendererContainer() {

        Font font = Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_SMALL);

        Container f = new Container(new BorderLayout());
        f.getStyle().setBgTransparency(0);
        f.getSelectedStyle().setBgColor(0xF00F2A);
        f.getPressedStyle().setBgColor(0xF00F2A);

        Container c = new Container(new BoxLayout(BoxLayout.X_AXIS));
        c.getStyle().setBgTransparency(0);
        //f.setUIID("ListRenderer");

        Label imageLabel = new Label(img);
        imageLabel.getUnselectedStyle().setFont(font);
        imageLabel.getSelectedStyle().setFont(font);
        imageLabel.getUnselectedStyle().setAlignment(CENTER);
        imageLabel.getSelectedStyle().setAlignment(CENTER);
        imageLabel.setName("anh");
        imageLabel.getStyle().setBgTransparency(0);
        imageLabel.getStyle().setFgColor(Text.colorWhite);

        imageLabel.getStyle().setBgPainter(new Painter() {

            public void paint(Graphics grphcs, Rectangle rctngl) {
                grphcs.drawImage(img, rctngl.getX(), rctngl.getY() + 10);
            }
        });
        Container d = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        Container e = new Container(new BoxLayout(CENTER));
        d.getStyle().setBgTransparency(0);
        e.getStyle().setBgTransparency(0);


        Label name = new Label();
        name.setFocusable(true);
        name.getSelectedStyle().setFont(font);
        name.getUnselectedStyle().setFont(font);
        name.setName("Name");
        name.getStyle().setBgTransparency(0);
        name.getStyle().setFgColor(Text.colorWhite);

        d.addComponent(name);

        Label surname = new Label();
        surname.setFocusable(true);
        surname.getSelectedStyle().setFont(font);
        surname.getUnselectedStyle().setFont(font);
        surname.setName("Surname");
        surname.getStyle().setBgTransparency(0);
        surname.getStyle().setFgColor(Text.colorWhite);

        d.addComponent(surname);

        Label km = new Label();
        //km.setFocusable(true);
        km.setName("km");
        km.setFocusable(true);
        km.getUnselectedStyle().setFont(font);
        
        km.getSelectedStyle().setFont(font);
        km.getStyle().setBgTransparency(0);
        km.getStyle().setFgColor(Text.colorWhite);

        Label bank = new Label();
        bank.setFocusable(true);
        bank.setName("bank");
        bank.getStyle().setBgTransparency(0);
        bank.getStyle().setFgColor(Text.colorWhite);

        e.addComponent(km);
        c.addComponent(imageLabel);
        c.addComponent(d);
        f.addComponent(BorderLayout.CENTER, c);
        f.addComponent(BorderLayout.EAST, e);
        f.addComponent(BorderLayout.NORTH, bank);

        return f;
    }

    private Hashtable[] createGenericListCellRendererModelData() {
        Hashtable[] data = new Hashtable[items.size()];
        ATM a;
        String[] s;
        for (int i = 0; i < items.size(); i++) {
            data[i] = new Hashtable();
            a = (ATM) items.elementAt(i);
            if (i < 9) {
                data[i].put("anh", " " + (i + 1) + "   ");
            } else {
                if (i < 99) {
                    data[i].put("anh", "" + (i + 1) + "  ");
                } else {
                    data[i].put("anh", (i + 1) + " ");
                }
            }
            s = Util.getAtmNameBank(a.getName());
            data[i].put("Name", s[1]);
            data[i].put("Surname", a.getAddr());
            data[i].put("km", a.getPriority() + " km");
            data[i].put("bank", s[0]);
        }


        return data;
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == list) {

            Event evt = new Event();
            evt.setData("ATM", items.elementAt(list.getSelectedIndex()));
            Controller.getInstance().handleEvent(Event.VIEW_MAP, evt);
            Controller.getInstance().categoryBar.setVisibility(false);
            Controller.getInstance().catrgoryBar2.setVisibility(true);
            Controller.getInstance().catrgoryBar2.setSelectedIndex(0);
        }
    }
}
