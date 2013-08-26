package views;

import Control.Controller;
import com.sun.lwuit.Graphics;
import com.sun.lwuit.geom.Rectangle;
import models.Text;
import com.sun.lwuit.Button;
import ads.AdBanner;
import com.nokia.lwuit.components.HeaderBar;
import com.nokia.lwuit.components.HeaderBarListener;
import com.sun.lwuit.events.ActionEvent;
import com.sun.lwuit.events.ActionListener;
import com.sun.lwuit.Command;
import com.sun.lwuit.Image;
import com.sun.lwuit.Label;
import com.sun.lwuit.Container;
import com.sun.lwuit.Display;
import com.sun.lwuit.Form;
import com.sun.lwuit.Painter;
import com.sun.lwuit.layouts.BorderLayout;
import com.sun.lwuit.layouts.BoxLayout;
import event.Event;
import java.io.IOException;
import java.util.Vector;

/**
 *
 * @author WALL
 */
public class FindATMView extends Form implements ActionListener {

    public Command search;
    public Text text;
    public Vector item1;
    public Command about;
    public About abo;
    public BankForm newBank;
    public SelectModelForm newCity;
    public Button btnBank, btnProvince, btnDistrict;
    private String bankName;
    private String provinceName;
    private String districtName;

    public void actionPerformed(ActionEvent ae) {
        Command cmd = ae.getCommand();
        if (cmd == search) {
            // Event evt = new Event();
            //  evt.setData("Title", bankName);
            Controller.getInstance().handleEvent(Event.FIND_ATM_VIEW_SEARCH_ADV, null);
        } else if (cmd == about) {
            Event evt = new Event();
            evt.setData(text.GioiThieu, ae);
            Controller.getInstance().categoryBar.setVisibility(false);
            Controller.getInstance().catrgoryBar2.setVisibility(false);
            Control.Controller.getInstance().handleEvent(Event.FIND_ATM_VIEW_ABOUT, evt);

        }
    }

    public FindATMView() {
        Display.getInstance().setForceFullScreen(true);
        Display.setObjectTrait(Display.getInstance().getImplementation(), "nokia.ui.canvas.status_zone", Boolean.TRUE);

        Controller.tap = 0;
        
        HeaderBar header = null;
        try {
            header = new HeaderBar(Text.TimATM, Image.createImage("/images/qgn_top_search.png"), null);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        header.setHeaderTitleColor(Text.colorWhite);
        header.getStyle().setBgColor(Text.hearder);
        
        
        header.setScrollable(false);
        

        header.setHeaderBarListener(new HeaderBarListener() {

            public void notifyHeaderBarListener(HeaderBar hb) {
                Controller.getInstance().handleEvent(Event.FIND_ATM_VIEW_SEARCH_ADV, null);
            }
        });
        addComponent(header);
        text = new Text();
//        Controller.getInstance().loadTheme(0);
//        refreshTheme();
        findATM();
    }

    public void findATM() {
        setLayout(new BoxLayout(BoxLayout.Y_AXIS));
        setScrollableY(false);
        try {


            //search = new Command(text.TimATM, Image.createImage("/images/qgn_top_search.png"));
            about = new Command(text.GioiThieu);
            //setDefaultCommand(search);
            //addCommand(search);

            addCommand(about);

            addCommandListener(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        setLayout(new BoxLayout(BoxLayout.Y_AXIS));

        Container Container1 = new Container();
        Container Container2 = new Container();
        Container Container3 = new Container();
        Label lb1 = new Label(text.NganHang);
        Label lb2 = new Label(text.Tinh);
        Label lb3 = new Label(text.QuanHuyen);
        lb1.getStyle().setBgTransparency(0);
        lb2.getStyle().setBgTransparency(0);
        lb3.getStyle().setBgTransparency(0);
        lb1.getStyle().setFgColor(0xffffff);
        lb2.getStyle().setFgColor(0xffffff);
        lb3.getStyle().setFgColor(0xffffff);
        
        //a = new Label("chao");

        btnBank = new Button(text.bank_adv_name);
        btnBank.getStyle().setBgPainter(new Painter() {

            public void paint(Graphics grphcs, Rectangle rctngl) {
                grphcs.setColor(0x55A0D7);
                grphcs.fillRect(rctngl.getX(), rctngl.getY(), rctngl.getX() + rctngl.getSize().getWidth(), rctngl.getY() + rctngl.getSize().getHeight());
            }
        });

        btnBank.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent ae) {
                Event evt = new Event();
                evt.setData("Title", bankName);

                Controller.getInstance().handleEvent(Event.FIND_ATM_VIEW_SEARCH_BANK, evt);
                Controller.getInstance().categoryBar.setVisibility(false);
            }
        });
        btnProvince = new Button(text.buttom_search2);
        btnProvince.getStyle().setBgPainter(new Painter() {

            public void paint(Graphics grphcs, Rectangle rctngl) {
                grphcs.setColor(0x55A0D7);
                grphcs.fillRect(rctngl.getX(), rctngl.getY(), rctngl.getX() + rctngl.getSize().getWidth(), rctngl.getY() + rctngl.getSize().getHeight());
            }
        });
        btnProvince.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent ae) {
                Event evt = new Event();
                evt.setData("Title", provinceName);

                Controller.getInstance().handleEvent(Event.FIND_ATM_VIEW_SEARCH_PROVINCE, evt);
                Controller.getInstance().categoryBar.setVisibility(false);
            }
        });
        btnDistrict = new Button(text.buttom_search3);
        btnDistrict.getStyle().setBgPainter(new Painter() {

            public void paint(Graphics grphcs, Rectangle rctngl) {
                grphcs.setColor(0x55A0D7);
                grphcs.fillRect(rctngl.getX(), rctngl.getY(), rctngl.getX() + rctngl.getSize().getWidth(), rctngl.getY() + rctngl.getSize().getHeight());
            }
        });
        btnDistrict.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent ae) {
                Event evt = new Event();
                evt.setData("Title", districtName);

                Controller.getInstance().handleEvent(Event.FIND_ATM_VIEW_SEARCH_DISTRICT, evt);
                Controller.getInstance().categoryBar.setVisibility(false);
            }
        });
        btnBank.getStyle().setMargin(0, 0, 5, 5);
        btnBank.getPressedStyle().setMargin(0, 0, 5, 5);
        btnBank.getSelectedStyle().setMargin(0, 0, 5, 5);
        btnProvince.getStyle().setMargin(0, 0, 5, 5);
        btnProvince.getPressedStyle().setMargin(0, 0, 5, 5);
        btnProvince.getSelectedStyle().setMargin(0, 0, 5, 5);
        btnDistrict.getStyle().setMargin(0, 0, 5, 5);
        btnDistrict.getPressedStyle().setMargin(0, 0, 5, 5);
        btnDistrict.getSelectedStyle().setMargin(0, 0, 5, 5);
        
        lb1.getStyle().setMargin(0, 0, 5, 5);
        lb1.getPressedStyle().setMargin(0, 0, 5, 5);
        lb2.getStyle().setMargin(0, 0, 5, 5);
        lb2.getPressedStyle().setMargin(0, 0, 5, 5);
        lb3.getStyle().setMargin(0, 0, 5, 5);
        lb3.getPressedStyle().setMargin(0, 0, 5, 5);
        

        btnBank.getStyle().setAlignment(LEFT);
        btnProvince.getStyle().setAlignment(LEFT);
        btnDistrict.getStyle().setAlignment(LEFT);
//        try {
//            btnBank.getStyle().setBgImage(Image.createImage("/images/nen.png"));
//        
//            btnProvince.getStyle().setBgImage(Image.createImage("/images/nen.png"));
//            btnDistrict.getStyle().setBgImage(Image.createImage("/images/nen.png"));
//        } catch (IOException ex) {
//            ex.printStackTrace();
//        }

        //a.getStyle().setMargin(10, 100, 5, 5);

        Container1.setLayout(new BoxLayout(BoxLayout.Y_AXIS));
        Container2.setLayout(new BoxLayout(BoxLayout.Y_AXIS));
        Container3.setLayout(new BoxLayout(BoxLayout.Y_AXIS));

        Container1.addComponent(lb1);
        Container2.addComponent(lb2);
        Container3.addComponent(lb3);

        Container1.addComponent(btnBank);
        Container2.addComponent(btnProvince);
        Container3.addComponent(btnDistrict);

        Container1.getStyle().setMargin(0, 0, 10, 10);
        Container1.getPressedStyle().setMargin(0, 0, 10, 10);
        Container2.getStyle().setMargin(0, 0, 10, 10);
        Container2.getPressedStyle().setMargin(0, 0, 10, 10);
        Container3.getStyle().setMargin(0, 0, 10, 10);
        Container3.getPressedStyle().setMargin(0, 0, 10, 10);

//        Container1.getStyle().setBgPainter(new Painter() {
//
//            public void paint(Graphics grphcs, Rectangle rctngl) {
//                grphcs.setColor(0x204478);
//                grphcs.fillRect(rctngl.getX(), rctngl.getY(), rctngl.getX() + rctngl.getSize().getWidth(), rctngl.getY() + rctngl.getSize().getHeight());
//            }
//        });
//        Container2.getStyle().setBgPainter(new Painter() {
//
//            public void paint(Graphics grphcs, Rectangle rctngl) {
//                grphcs.setColor(0x204478);
//                grphcs.fillRect(rctngl.getX(), rctngl.getY(), rctngl.getX() + rctngl.getSize().getWidth(), rctngl.getY() + rctngl.getSize().getHeight());
//            }
//        });
//        Container3.getStyle().setBgPainter(new Painter() {
//
//            public void paint(Graphics grphcs, Rectangle rctngl) {
//                grphcs.setColor(0x204478);
//                grphcs.fillRect(rctngl.getX(), rctngl.getY(), rctngl.getX() + rctngl.getSize().getWidth(), rctngl.getY() + rctngl.getSize().getHeight());
//            }
//        });

        
        Container container5= new Container(new BoxLayout(BoxLayout.Y_AXIS));
        container5.addComponent(Container1);
        container5.addComponent(Container2);
        container5.addComponent(Container3);
        container5.setScrollable(true);
        
        container5.getStyle().setMargin(0, 0, 0, 0);
        container5.setScrollableY(true);
        addComponent(container5);
//        
//        Container Container4 = new Container();
//        //Container4.getStyle().setMargin(20, 0, 13, 9);
//
//        Container4.addComponent(new AdBanner("CDiT_ATM_WP", 65));
//        //Container4.setScrollableY(false);
//        
//        Container4.setScrollable(false);
//        
//        //addComponent(Container1);
//       // addComponent(Container2);
////        Container f = new Container(new BorderLayout());
////        f.addComponent(BorderLayout.CENTER, Container3);
//
//        
//        Container4.getStyle().setMargin(0, 0, 10, 10);
//        //addComponent(Container4);
//        
//        
        setScrollable(false);
        //show();
    }

    public void loadBank(String name) {
        bankName = name;
        btnBank.setText(name);
    }

    public void loadProvince(String name) {
        provinceName = name;
        btnProvince.setText(name);
    }

    public void loadDistrict(String name) {
        districtName = name;
        btnDistrict.setText(name);
    }
}
