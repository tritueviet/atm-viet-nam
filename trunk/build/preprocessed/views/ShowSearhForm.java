/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import Control.Controller;
import com.sun.lwuit.Graphics;
import com.sun.lwuit.geom.Rectangle;
import models.Text;

import com.nokia.lwuit.GestureHandler;
import com.nokia.mid.ui.gestures.GestureEvent;
import com.sun.lwuit.Container;
import com.sun.lwuit.Font;
import com.sun.lwuit.Form;
import com.sun.lwuit.Label;
import com.sun.lwuit.List;

import com.sun.lwuit.events.ActionEvent;
import com.sun.lwuit.events.ActionListener;
import com.nokia.lwuit.components.ContextMenu;
import com.nokia.lwuit.components.HeaderBar;
import com.sun.lwuit.Command;
import com.sun.lwuit.Dialog;
import com.sun.lwuit.Display;
import com.sun.lwuit.Painter;
import com.sun.lwuit.layouts.BorderLayout;
import com.sun.lwuit.layouts.BoxLayout;
import com.sun.lwuit.list.GenericListCellRenderer;
import event.Event;
import java.util.Hashtable;
import java.util.Vector;
import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.AlertType;
import models.ATM;
import util.StorageData;
import util.Util;

/**
 *
 * @author WALL
 */
public class ShowSearhForm extends Form implements ActionListener {

    Vector items;
    List list;
    Command backCommand, lienKet;

    public ShowSearhForm(String string, Vector item) {
        Display.getInstance().setForceFullScreen(true);
        Display.setObjectTrait(Display.getInstance().getImplementation(), "nokia.ui.canvas.status_zone", Boolean.TRUE);

//        Controller.getInstance().loadTheme(1);
//        refreshTheme();
        
        Display.getInstance().setForceFullScreen(true);
        HeaderBar header = null;
        try {
            header = new HeaderBar(string);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        header.setHeaderTitleColor(Text.colorWhite);
        header.getUnselectedStyle().setBgColor(Text.hearder);

        header.setScrollable(false);

               // setLayout(new BorderLayout());
        //Display.getInstance().setForceFullScreen(true);
        
        addComponent(header);



        this.items = item;

        lienKet = new Command(Text.NganHangLienKet);
        addCommand(lienKet);

        if (items.size() == 0) {
            Label nullResuilts = new Label(Text.khongCoKqtQua);
            nullResuilts.getStyle().setMargin(10, 10, 10, 10);
            nullResuilts.getStyle().setBgTransparency(0);
            nullResuilts.getStyle().setFgColor(Text.colorWhite);
            addComponent(nullResuilts);
        } else {
            updateData(string);
        }


        backCommand = new Command(Text.Back);
        setBackCommand(backCommand);
        addCommand(backCommand);
        addCommandListener(this);
        setScrollable(false);
        //show();
    }

    private void updateData(final String tittle) {
        
        
        list = new List(createGenericListCellRendererModelData());
        list.setRenderer(new GenericListCellRenderer(createGenericRendererContainer(),
                createGenericRendererContainer()));
        //addComponent(list);
        list.getPressedStyle().setBgPainter(new Painter() {

            public void paint(Graphics grphcs, Rectangle rctngl) {
                grphcs.setColor(0xE90335);
                grphcs.fillRect(rctngl.getX(), rctngl.getY(), rctngl.getX() + rctngl.getSize().getWidth(), rctngl.getY() + rctngl.getSize().getHeight());
            }
        });
        
        
        Container contai= new Container();
        contai.getUnselectedStyle().setBgTransparency(0);
        //contai.getStyle().setMargin(0, 0, 5, 5);
        list.getUnselectedStyle().setMargin(0, 0, 0, 0);
        list.addActionListener(this);
        contai.addComponent(list);
        
        contai.setScrollable(true);
        contai.setScrollableX(false);
        //contai.setScrollVisible(true);
        addComponent(contai);
        
        
        /*
        GestureHandler longtap = new GestureHandler(GestureHandler.GESTURE_LONG_PRESS) {
        
        public void gestureAction(GestureEvent ge) {
        int x = ge.getStartX();
        int y = ge.getStartY();
        
        //   System.out.println("x :"+ x+"y: "+y);
        
        if (list.contains(x, y)) {
        
        setTitle(tittle);
        Display.getInstance().callSerially(new Runnable() {
        //setTitle("");
        String[] menulist = {Text.BanDo, Text.DanDuong, Text.ThemUaThich};
        
        public void run() {
        int selected = list.getSelectedIndex();
        ATM atmmm;
        atmmm = (ATM) items.elementAt(selected);
        Command[] cmds = null;
        
        //                           
        cmds = new Command[menulist.length];
        for (int i = 0; i < cmds.length; i++) {
        cmds[i] = new Command(menulist[i]);
        }
        System.out.println("+++"+ContextMenu.getDefaultDialogPosition());
        
        Command selectedContextCmd = ContextMenu.show(cmds, list);
        
        // xem map
        if (selectedContextCmd == cmds[0]) {
        //  xem map 
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
        
        } else 
        if (selectedContextCmd == cmds[2]) {
        // them ua thich
        if (StorageData.getInstance().hasFavorite(atmmm.getId())) {
        //                                    Dialog.setDefaultDialogType(Dialog.TYPE_INFO);
        //                                     Dialog.show(Text.Loi,Text.TonTaiTrongUaThich, Text.Ok, null);
        Alert a= new Alert(Text.Loi,Text.TonTaiTrongUaThich, null, AlertType.INFO);
        javax.microedition.lcdui.Display.getDisplay(Controller.getInstance().main).setCurrent(a);
        } else {
        Event evt = new Event();
        evt.setData("ATM", items.elementAt(selected));
        Controller.getInstance().handleEvent(evt.ADD_FAVARITES, evt);
        } 
        
        }
        
        }
        });
        }
        }
        };
        GestureHandler.setFormGestureHandler(this, longtap);
        
         */
    }

    private Container createGenericRendererContainer() {
        Container f = new Container(new BoxLayout(BoxLayout.Y_AXIS));

        //f.setUIID("ListRenderer");
        f.getUnselectedStyle().setBgTransparency(0);
        //f.getSelectedStyle().setBgColor(CENTER);
        
        
        Label name = new Label();
        name.setFocusable(true);
        name.getUnselectedStyle().setBgTransparency(0);
        name.getStyle().setFgColor(Text.colorWhite);
        
        Font font = Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_SMALL);
        //Font font2 = Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_MEDIUM);


        name.setName("Name");
        f.addComponent(name);

        Label surname = new Label();
        surname.setFocusable(true); 
        surname.getStyle().setFont(font);
        surname.getUnselectedStyle().setFont(font);
        surname.getSelectedStyle().setFont(font);
        surname.getPressedStyle().setFont(font);
        
        surname.getUnselectedStyle().setBgTransparency(0);
        surname.getStyle().setFgColor(Text.colorWhite);
        surname.setName("Surname");
        //surname.setEndsWith3Points(false);
        f.addComponent(surname);




        return f;
    }

    private Hashtable[] createGenericListCellRendererModelData() {
        Hashtable[] data = new Hashtable[items.size()];
        ATM atm;
        String[] s;
        for (int i = 0; i < items.size(); i++) {
            data[i] = new Hashtable();
            atm = (ATM) items.elementAt(i);
            s = Util.getAtmNameBank(atm.getName());
            // item can truyen vao o day la ten atm 
            data[i].put("Name", s[1]);
            // item can truyen vao la dia hi ngan hang
            data[i].put("Surname", atm.getAddr());

        }

        return data;
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == list) {
            Event evt = new Event();
            evt.setData("ATM", items.elementAt(list.getSelectedIndex()));
            Controller.getInstance().handleEvent(Event.VIEW_MAP, evt);
//            Controller.getInstance().handleEvent(Event.VIEW_MAP_CONDUCTOR, evt);

            Controller.getInstance().categoryBar.setVisibility(false);
            Controller.getInstance().catrgoryBar2.setVisibility(true);
            Controller.getInstance().catrgoryBar2.setSelectedIndex(0);
        } else if (ae.getCommand() == backCommand) {
//                Controller.getInstance().loadTheme(0);
//                refreshTheme();
            Controller.getInstance().categoryBar.setVisibility(true);
            Controller.getInstance().showFindATMView();
        } else if (ae.getCommand() == lienKet) {
//                Controller.getInstance().loadTheme(0);
//                refreshTheme();
            //Controller.getInstance().categoryBar.setVisibility(true);
            Controller.getInstance().handleEvent(Event.VIEW_NGAN_HANGLIENKET, null);
        }
    }
}
