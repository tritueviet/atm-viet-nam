/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import Control.Controller;
import com.nokia.lwuit.components.HeaderBar;
import com.nokia.lwuit.components.HeaderBarListener;
import com.sun.lwuit.Command;
import com.sun.lwuit.Container;
import com.sun.lwuit.Dialog;
import com.sun.lwuit.Display;
import com.sun.lwuit.Form;
import com.sun.lwuit.Image;
import com.sun.lwuit.Label;
import com.sun.lwuit.events.ActionEvent;
import com.sun.lwuit.events.ActionListener;
import com.sun.lwuit.layouts.BorderLayout;
import event.Event;
import java.io.IOException;
import java.util.Vector;
import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.AlertType;
import map.Map;
import models.Text;
import util.StorageData;

/**
 *
 * @author PHAN THAI
 */
public class MapView extends Form implements ActionListener, HeaderBarListener {

    private Map map;
    private Command cmdBack, addFavarite;
    HeaderBar header = null;

    public MapView(Map map) {

        Display.getInstance().setForceFullScreen(true);


        System.out.println("" + Controller.head);

        if (Controller.head == 0) {
            try {
                if (Controller.tap == 2) {
                    header = new HeaderBar(Text.BanDo);
                } else {
                    header = new HeaderBar(Text.BanDo, Image.createImage("/images/favorite.png"), null);
                }
                Controller.head = 0;
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } else {
            try {

                if (Controller.tap == 2) {
                    header = new HeaderBar(Text.DanDuong);
                } else {
                    header = new HeaderBar(Text.DanDuong, Image.createImage("/images/favorite.png"), null);
                }
                Controller.head = 0;
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }



        header.setHeaderTitleColor(Text.colorWhite);
        header.getStyle().setBgColor(Text.hearder);
        header.setScrollable(false);
        header.setHeaderBarListener(this);
        setLayout(new BorderLayout());

        addComponent(BorderLayout.NORTH, header);
        this.map = map;
        try {
            if (Controller.getInstance().requestUsingGET("http://google.com") == true) {
                addComponent(BorderLayout.CENTER, map);
            } else {
                Label nullResuilts = new Label(Text.khongCoKqtQua);
                nullResuilts.getStyle().setMargin(10, 10, 10, 10);
                nullResuilts.getStyle().setBgTransparency(0);
                nullResuilts.getStyle().setFgColor(Text.colorWhite);

                Container containn = new Container();
                containn.addComponent(nullResuilts);
                containn.setScrollableY(true);
                addComponent(BorderLayout.CENTER, containn);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }



        cmdBack = new Command(Text.Back);
        setBackCommand(cmdBack);
        addCommand(cmdBack);

//        addFavarite = new Command(Text.ThemUaThich);
//        addCommand(addFavarite);

//        try {
//            addFavarite= new Command(Text.ThemUaThich, Image.createImage("/images/icon3.png"));
//        } catch (IOException ex) {
//            ex.printStackTrace();
//        }
//        setDefaultCommand(addFavarite);
//        addCommand(addFavarite);


        addCommandListener(this);

    }

    public void huyMap() {
        if (map != null) {
            map = null;
        }
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getCommand() == cmdBack) {
            // map.huy();
            map = null;
            Controller.getInstance().showBack();
            Controller.getInstance().catrgoryBar2.setVisibility(false);

        }

    }

    public void notifyHeaderBarListener(HeaderBar hb) {
        if (StorageData.getInstance().hasFavorite(Text.Items.getId())) {
            Dialog.setDefaultDialogType(Dialog.TYPE_INFO);
            
//            Dialog.show(Text.Loi, Text.TonTaiTrongUaThich, Text.Ok, null);
            Controller.getInstance().showMessenger(Text.Loi, Text.TonTaiTrongUaThich);
            
//            Alert a = new Alert(Text.Loi, Text.TonTaiTrongUaThich, null, AlertType.INFO);
//            javax.microedition.lcdui.Display.getDisplay(Controller.getInstance().main).setCurrent(a);
        } else {
            Event evt = new Event();
            evt.setData("ATM", Text.Items);
            Controller.getInstance().handleEvent(evt.ADD_FAVARITES, evt);
//                    Dialog.setDefaultDialogType(Dialog.TYPE_INFO);
//                    Dialog.show(Text.Ok,Text.TonTaiTrongUaThich, Text.Ok, null);
//                Alert a= new Alert(Text.Ok,Text.DaThemUaThich, null, AlertType.INFO);
//                   javax.microedition.lcdui.Display.getDisplay(Controller.getInstance().main).setCurrent(a);
        }
    }
}
