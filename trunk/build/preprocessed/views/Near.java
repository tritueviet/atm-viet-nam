/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import Control.Controller;
import com.nokia.lwuit.components.HeaderBar;
import com.nokia.lwuit.components.HeaderBarListener;
import com.sun.lwuit.geom.Rectangle;
import models.Text;

import com.sun.lwuit.ButtonGroup;
import com.sun.lwuit.CheckBox;
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
import com.sun.lwuit.RadioButton;
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
import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.AlertType;
import models.Bank;

/**
 *
 * @author WALL
 */
public class Near extends Form implements ActionListener, HeaderBarListener {

    TextField txtfieldKhoangcach;
    ButtonGroup radioButtonGroup;
    Container dau, ContainerSau;
    Command backCommand, back;
    Vector items;
    public boolean[] luuu = {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false};

    public Near(String string, Vector item) {
        //super(string);
//        Controller.getInstance().loadTheme(0);
        refreshTheme();
        
        this.items = item;
        for (int i = 0; i < 26; i++) {
            luuu[i] = Text.setgan[i];
        }
        Display.getInstance().setForceFullScreen(true);
        HeaderBar header = null;
        try {
            header = new HeaderBar(string, Image.createImage("/images/tick.png"), null);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        header.setHeaderTitleColor(Text.colorWhite);
        header.getStyle().setBgColor(Text.hearder);

        header.setHeaderBarListener(this);
        
        header.setScrollable(false);
        addComponent(header);
        
        //setLayout(new BoxLayout(BoxLayout.Y_AXIS));
        setScrollable(false);
        Container conTong= new Container();
        
        
        dau = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        

        Label mot = new Label(Text.KhoangCach);
        txtfieldKhoangcach = new TextField();
        txtfieldKhoangcach.getStyle().setFgColor(Text.colorWhite);
        
        txtfieldKhoangcach.setText((Text.setKhoangcach));
        //txtfieldKhoangcach.setHint(string);

        // txtfieldKhoangcach.setInputMode(123 + "");
        txtfieldKhoangcach.setConstraint(TextField.PHONENUMBER);
        mot.getStyle().setBgTransparency(0);
        mot.getStyle().setFgColor(Text.colorWhite);




        //khoangcach.getStyle().setMargin(2, 15, 5, 15);
        //khoangcach.getStyle().setPadding(5, 5, 0, 0);
        dau.addComponent(mot);

        dau.setLayout(new BoxLayout(BoxLayout.Y_AXIS));

        dau.addComponent(txtfieldKhoangcach);
        dau.getStyle().setBgPainter(new Painter() {

            public void paint(Graphics grphcs, Rectangle rctngl) {
                grphcs.setColor(0x204478);
                grphcs.fillRect(rctngl.getX(), rctngl.getY(), rctngl.getX() + rctngl.getSize().getWidth(), rctngl.getY() + rctngl.getSize().getHeight());
            }
        });

        conTong.addComponent(dau);

        ContainerSau = new Container(new BoxLayout(BoxLayout.Y_AXIS));


        ContainerSau.getStyle().setBgPainter(new Painter() {

            public void paint(Graphics grphcs, Rectangle rctngl) {
                grphcs.setColor(0x204478);
                grphcs.fillRect(rctngl.getX(), rctngl.getY(), rctngl.getX() + rctngl.getSize().getWidth(), rctngl.getY() + rctngl.getSize().getHeight());
            }
        });

        dau.getStyle().setMargin(10, 5, 5, 5);
        ContainerSau.getStyle().setMargin(5, 5, 5, 5);
        Label labelbank = new Label(Text.NganHang);
        labelbank.getStyle().setBgTransparency(0);
        labelbank.getStyle().setFgColor(Text.colorWhite);

        ContainerSau.addComponent(labelbank);

//        String[] items = { "Red", "Blue", "Green", "Yellow" };
//        DefaultListModel myListModel = new DefaultListModel(items);
//        List list = new List(myListModel);


        final List list = new List(createGenericListCellRendererModelData());
        Container c = createGenericRendererContainer();
        Container c1 = createGenericRendererContainer();
        list.setRenderer(new GenericListCellRenderer(c, c1));
        list.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent ae) {
                // System.out.println(">>"+list.getSelectedItem());

                list.setSelectedIndex(list.getSelectedIndex());

                if (Text.setgan[list.getSelectedIndex()] == true) {
                    Text.setgan[list.getSelectedIndex()] = false;
                } else {
                    Text.setgan[list.getSelectedIndex()] = true;
                }
//                int c;
//                for (int i=0 ; i <items.size(); i++){
//                    if (Text.setgan[i]) 
//                    System.out.println(">>>"+Text.setgan[i]);   
//                }

            }
        });

        //addComponent(list);
        ContainerSau.addComponent(list);
        conTong.addComponent(ContainerSau);
        conTong.setScrollableY(true);
        addComponent(conTong);
        
//        try {
        backCommand = new Command(Text.Ok);//new Command(Text.Back, Image.createImage("/images/saveicon.png"));
//        } catch (IOException ex) {
//            ex.printStackTrace();
//        }
        back = new Command(Text.Back);
        setBackCommand(back);
        addCommand(back);

//        setDefaultCommand(backCommand);
//        addCommand(backCommand);
        addCommandListener(this);
        layoutContainer();
    }

    private Container createGenericRendererContainer() {
        Container f = new Container(new BorderLayout());

        Container c = new Container(new BoxLayout(BoxLayout.X_AXIS));
        //f.setUIID("ListRenderer");
        f.getStyle().setBgTransparency(0);
        c.getStyle().setBgTransparency(0);


        CheckBox selected = new CheckBox();
//        RadioButton selected = new RadioButton();
//        selected.
        selected.setName("Seclect");
        selected.getUnselectedStyle().setFgColor(0x204478);
        selected.getStyle().setBgTransparency(0);
        //selected.getUnselectedStyle().setPadding(0, 0, 10, 0);
        //selected.getUnselectedStyle().setMargin(0, 0, 50, 0);
        selected.getSelectedStyle().setFgColor(0x8894A0);
        selected.setPreferredW(35);
        selected.setFocusable(true);

        Container d = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        Container e = new Container(new BoxLayout(CENTER));
        d.getStyle().setBgTransparency(0);
        e.getStyle().setBgTransparency(0);

        e.addComponent(selected);
        Font font = Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_SMALL);

        Label name = new Label();
        name.setFocusable(true);
        name.setTickerEnabled(true);
        name.setName("Name");
        name.getStyle().setBgTransparency(0);
        name.getStyle().setFgColor(Text.colorWhite);

        d.addComponent(name);


        Label surname = new Label();
        surname.getStyle().setFont(font);
        surname.setFocusable(true);
        surname.setTickerEnabled(true);
        surname.setName("Surname");
        surname.getSelectedStyle().setFont(font);
        surname.getStyle().setBgTransparency(0);
        surname.getStyle().setFgColor(Text.colorWhite);


        d.addComponent(surname);

        c.addComponent(d);
        f.addComponent(BorderLayout.CENTER, c);
        f.addComponent(BorderLayout.EAST, e);

        return f;
    }

    private Hashtable[] createGenericListCellRendererModelData() {
        Hashtable[] data = new Hashtable[items.size()];
        //Bank b= new Bank();
        Bank b;
        for (int i = 0; i < items.size(); i++) {
            b = (Bank) items.elementAt(i);
            data[i] = new Hashtable();
            data[i].put("Seclect", Text.setgan[i] + "");
            data[i].put("Name", b.getName());
            data[i].put("Surname", b.getFullname());

        }
        return data;
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getCommand() == back) {
            for (int i = 0; i < 26; i++) {
                Text.setgan[i] = luuu[i];
            }
//            Controller.loadTheme(1);
//            refreshTheme();
            Controller.getInstance().showOptionATM();
            Controller.getInstance().categoryBar.setVisibility(true);

        }

    }

    public void notifyHeaderBarListener(HeaderBar hb) {
        String abc = txtfieldKhoangcach.getText().trim();
        int dem = 0;
        int showw = 0;
        if (abc.length() != 0) {
            //System.out.println("+++>>: "+abc.substring(abc.length()-1, 1));
            //System.out.println("+++>>: "+abc.length());
            for (int i = 0; i < abc.length(); i++) {
                if (abc.charAt(i) == '0' || abc.charAt(i) == '1' || abc.charAt(i) == '2' || abc.charAt(i) == '3'
                        || abc.charAt(i) == '4' || abc.charAt(i) == '5' || abc.charAt(i) == '6' || abc.charAt(i) == '7'
                        || abc.charAt(i) == '8' || abc.charAt(i) == '9' || abc.charAt(i) == '.') {

                    if (abc.charAt(i) == '.') {
                        dem++;
                    }
                    if (dem > 1) {
                        showw = 2;
                        break;
                    }
                } else {
                    showw = 2;
                    break;
                }
            }
            if (abc.charAt(0) == '0') {
                showw = 2;
            }

            if (showw == 2) {

//                Alert a = new Alert(Text.Loi, Text.Khongdung, null, AlertType.INFO);
//                javax.microedition.lcdui.Display.getDisplay(Controller.getInstance().main).setCurrent(a);
//                Dialog.show(Text.Loi, Text.Khongdung,Text.Ok, null);
                Controller.getInstance().showMessenger(Text.Loi, Text.Khongdung);
            } else {

                if (Double.parseDouble(abc) > 100) {

//                    Alert a = new Alert(Text.Loi, Text.ThongBaoLoi, null, AlertType.INFO);
//                    javax.microedition.lcdui.Display.getDisplay(Controller.getInstance().main).setCurrent(a);
//                    Dialog.show(Text.Loi, Text.ThongBaoLoi,Text.Ok, null);
                    Controller.getInstance().showMessenger(Text.Loi, Text.ThongBaoLoi);
                    
                } else {
                    if (abc.charAt(abc.length() - 1) == '.') {
                        abc = abc.substring(0, abc.length() - 1);
                    }
                    Text.setKhoangcach = abc;
//                    Controller.loadTheme(1);
//                    refreshTheme();
                    Controller.getInstance().showOptionATM();
                    Controller.getInstance().categoryBar.setVisibility(true);

                }
            }
        } else {

            Text.setKhoangcach = abc;
//            Controller.loadTheme(1);
//            refreshTheme();
            Controller.getInstance().showOptionATM();
            Controller.getInstance().categoryBar.setVisibility(true);

        }
    }
}
