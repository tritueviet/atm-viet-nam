/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import ads.AdBanner;
import Control.Controller;
import Control.HandleConfirm;
import ads.NaxForm;
import com.nokia.lwuit.GestureHandler;
import com.nokia.lwuit.components.ContextMenu;
import com.nokia.lwuit.components.HeaderBar;
import com.nokia.lwuit.components.HeaderBarListener;
import com.nokia.mid.ui.gestures.GestureEvent;
import com.sun.lwuit.ButtonGroup;
import com.sun.lwuit.Command;
import com.sun.lwuit.Container;
import com.sun.lwuit.Display;
import com.sun.lwuit.Font;
import com.sun.lwuit.Form;
import com.sun.lwuit.Image;
import com.sun.lwuit.Label;
import com.sun.lwuit.List;
import com.sun.lwuit.TextField;
import com.sun.lwuit.events.ActionEvent;
import com.sun.lwuit.events.ActionListener;
import com.sun.lwuit.layouts.BorderLayout;
import com.sun.lwuit.layouts.BoxLayout;
import com.sun.lwuit.list.GenericListCellRenderer;
import event.Event;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Vector;
import javax.microedition.io.CommConnection;
import models.ATM;
import models.Text;
import util.StorageData;

/**
 *
 * @author WALL
 */
public class FormFavoriteAtm extends NaxForm implements ActionListener {

    TextField khoangcach;
    ButtonGroup radioButtonGroup;
    Vector items;
    Command xoa;
    List list;

    public FormFavoriteAtm() {
        // setTitle(Text.YeuThich);

        Display.getInstance().setForceFullScreen(true);
        //Controller.tap = 2;
        items = StorageData.getInstance().getFavoriteATMs();
        HeaderBar header = null;
        try {
            header = new HeaderBar(Text.YeuThich, Image.createImage("/images/delete.png"), null);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        header.setHeaderTitleColor(Text.colorWhite);
        header.getStyle().setBgColor(Text.hearder);


        header.setHeaderBarListener(new HeaderBarListener() {

            public void notifyHeaderBarListener(HeaderBar hb) {
                Text.chonTat = false;
                Event e = new Event();
                e.setData("ATM", items);
                Controller.getInstance().categoryBar.setVisibility(false);
                Controller.getInstance().handleEvent(Event.VIEW_DELETE_FAVORITE, e);
            }
        });
        header.setScrollable(false);



        if (items.isEmpty()) {
            //setLayout(new BoxLayout(BoxLayout.Y_AXIS));
            addComponent(BorderLayout.NORTH, header);
            Label nullResuilts = new Label(Text.khongCoKqtQua);
            nullResuilts.getStyle().setMargin(10, 10, 10, 10);
            nullResuilts.getStyle().setBgTransparency(0);
            nullResuilts.getStyle().setFgColor(Text.colorWhite);

            Container containn = new Container();
            containn.addComponent(nullResuilts);
            containn.setScrollableY(true);
            addComponent(BorderLayout.CENTER,containn);
            
        } else {
            //setLayout(new BorderLayout());
            addComponent(BorderLayout.NORTH, header);

            setScrollable(false);

            //System.out.println("size item : "+items.size() );

            //setLayout(new BorderLayout());


            list = new List(createGenericListCellRendererModelData());
            list.setRenderer(new GenericListCellRenderer(createGenericRendererContainer(),
                    createGenericRendererContainer()));

            Container container2 = new Container();
            container2.addComponent(list);
            container2.setScrollableY(true);
            addComponent(BorderLayout.CENTER, container2);

            Container container4 = new Container();
            //  Container4.getStyle().setMargin(0, 0, 13, 9);
            
//            container4.addComponent(new AdBanner("CDiT_ATM_WP", 60));
//            container4.getStyle().setMargin(0, 0, 10, 10);
//            container4.setScrollable(false);
            //addComponent(BorderLayout.SOUTH, container4);
            list.addActionListener(this);
            
            /*      
            GestureHandler longtap = new GestureHandler(GestureHandler.GESTURE_LONG_PRESS) {
            
            public void gestureAction(GestureEvent ge) {
            int x = ge.getStartX();
            int y = ge.getStartY();
            
            System.out.println("x :" + x + "y: " + y);
            
            if (list.contains(x, y)) {
            
            
            Display.getInstance().callSerially(new Runnable() {
            
            String[] menulist = {Text.BanDo, Text.DanDuong, Text.XoaUaThich};
            
            public void run() {
            final int selected = list.getSelectedIndex();
            
            Command[] cmds = null;
            cmds = new Command[menulist.length];
            for (int i = 0; i < cmds.length; i++) {
            cmds[i] = new Command(menulist[i]);
            
            }
            Command selectedContextCmd = ContextMenu.show(cmds, list);
            //System.out.println("selected:" + selectedContextCmd+(selected+1));
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
            } else if (selectedContextCmd == cmds[2]) {
            // xoa ua thich
            Controller.getInstance().showConfirm(new HandleConfirm() {
            
            public void actionYes() {
            Event evt = new Event();
            evt.setData("ATM", items.elementAt(selected));
            Controller.getInstance().handleEvent(Event.DELETE_FAVORITES, evt);
            //               list.re
            }
            
            public void actionNo() {
            show();
            }
            });
            
            
            }
            
            }
            });
            }
            }
            };
            GestureHandler.setFormGestureHandler(this, longtap);
            
             */

            try {
                xoa = new Command(Text.xoa, Image.createImage("/images/delete.png"));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
//            setDefaultCommand(xoa);
//            addCommand(xoa);
            addCommandListener(this);
        }
        enableAds("CDiT_ATM_WP", 30000);

    }

    private Container createGenericRendererContainer() {
        Font font = Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_SMALL);

        Container f = new Container(new BorderLayout());

        Container c = new Container(new BoxLayout(BoxLayout.X_AXIS));
        //f.setUIID("ListRenderer");
        f.getStyle().setBgTransparency(0);
        c.getStyle().setBgTransparency(0);

        Container d = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        d.getStyle().setBgTransparency(0);

        Label bank = new Label();
        bank.setName("bank");
        bank.setFocusable(true);
        bank.getStyle().setBgTransparency(0);
        bank.getStyle().setFgColor(Text.colorWhite);
        d.addComponent(bank);

        Label name = new Label();
        name.setFocusable(true);
        name.getStyle().setBgTransparency(0);
        name.getStyle().setFgColor(Text.colorWhite);
        name.setName("Name");
        d.addComponent(name);

        Label surname = new Label();
        surname.setFocusable(true);
        surname.getStyle().setBgTransparency(0);
        surname.getStyle().setFgColor(Text.colorWhite);
        surname.getStyle().setFont(font);
        surname.getSelectedStyle().setFont(font);
        surname.getUnselectedStyle().setFont(font);
        surname.getPressedStyle().setFont(font);

        surname.setName("Surname");
        d.addComponent(surname);


        // c.addComponent(imageLabel);
        c.addComponent(d);
        f.addComponent(BorderLayout.CENTER, c);

        return f;
    }

    private Hashtable[] createGenericListCellRendererModelData() {
        Hashtable[] data = new Hashtable[items.size()];
        ATM a;
        for (int i = 0; i < items.size(); i++) {
            data[i] = new Hashtable();
            a = (ATM) items.elementAt(i);
            String[] s = util.Util.getAtmNameBank(a.getName());
            data[i].put("bank", s[0]);
            data[i].put("Name", s[1]);
            data[i].put("Surname", a.getAddr());
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
        } else if (ae.getCommand() == xoa) {
            //  xu ly 
            Text.chonTat = false;
            Event e = new Event();
            e.setData("ATM", items);
            Controller.getInstance().categoryBar.setVisibility(false);
            Controller.getInstance().handleEvent(Event.VIEW_DELETE_FAVORITE, e);
        }

    }
}
