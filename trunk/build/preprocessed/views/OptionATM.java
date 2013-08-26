/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import ads.AdBanner;
import Control.Controller;
import Control.Main;
import ads.NaxForm;
import com.nokia.lwuit.components.HeaderBar;
import models.Text;
import com.sun.lwuit.Button;
import com.sun.lwuit.Command;
import com.sun.lwuit.Container;
import com.sun.lwuit.Font;
import com.sun.lwuit.Form;
import com.sun.lwuit.Graphics;
import com.sun.lwuit.Label;
import com.sun.lwuit.Image;
import com.sun.lwuit.List;
import com.sun.lwuit.Painter;
import com.sun.lwuit.events.ActionEvent;
import com.sun.lwuit.events.ActionListener;
import com.sun.lwuit.geom.Rectangle;
import com.sun.lwuit.layouts.BorderLayout;
import com.sun.lwuit.layouts.BoxLayout;
import com.sun.lwuit.list.GenericListCellRenderer;
import event.Event;
import java.util.Hashtable;

/**
 *
 * @author WALL
 */
public class OptionATM extends NaxForm implements ActionListener {

    Text text = new Text();
    public Select selec;
    Container Container1 = new Container();
    Container Container2 = new Container();
    Container Container3 = new Container();
    Container Container4 = new Container();
    Container contai5 = new Container();
    Container contai6 = new Container();
    Label lb1, lb2, lb3, lb4;
    Button searc1;
    Button searc2;
    List list;

    public OptionATM() {
//        Controller.getInstance().categoryBar.setVisibility(false);
//        Controller.getInstance().catrgoryBar2.setVisibility(false);
        
        Controller.tap = 3;
        HeaderBar header = null;
        try {
            header = new HeaderBar(Text.CaiDat);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        header.setHeaderTitleColor(Text.colorWhite);
        header.getStyle().setBgColor(Text.hearder);
        header.setScrollable(false);
        setScrollable(false);
        addComponent(header);

        list = new List(createGenericListCellRendererModelData());
        list.setRenderer(new GenericListCellRenderer(createGenericRendererContainer(), createGenericRendererContainer()));
        list.addActionListener(this);

        //Setting();
        enableAds("CDiT_ATM_WP", 30000);
        addComponent(list);

    }

    private Container createGenericRendererContainer() {
        Font font = Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_MEDIUM);
        Container f = new Container(new BorderLayout());
        f.getStyle().setBgTransparency(0);

        Container c = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        c.getStyle().setBgTransparency(0);

        Label imageLabel = new Label("Name");
        imageLabel.getUnselectedStyle().setFont(font);
        imageLabel.getSelectedStyle().setFont(font);
        imageLabel.getUnselectedStyle().setAlignment(LEFT);
        imageLabel.getSelectedStyle().setAlignment(LEFT);
        imageLabel.getStyle().setBgTransparency(0);
        imageLabel.setName("Name");
        imageLabel.getStyle().setFgColor(Text.colorWhite);

        Container d = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        d.getStyle().setBgTransparency(0);

        Label imageLabel2 = new Label("Surname");
        imageLabel2.getUnselectedStyle().setFont(font);
        imageLabel2.getSelectedStyle().setFont(font);
        imageLabel2.getUnselectedStyle().setAlignment(LEFT);
        imageLabel2.getSelectedStyle().setAlignment(LEFT);
        imageLabel2.getStyle().setBgTransparency(0);
        imageLabel2.setName("Surname");
        imageLabel2.getStyle().setFgColor(Text.hearder);

        c.addComponent(imageLabel);
        d.addComponent(imageLabel2);

        f.addComponent(BorderLayout.NORTH, c);
        f.addComponent(BorderLayout.SOUTH, d);

        return f;
    }

    private Hashtable[] createGenericListCellRendererModelData() {
        Hashtable[] data = new Hashtable[2];
        data[0] = new Hashtable();
        data[0].put("Name", "   " + Text.NgonNgu);
        data[0].put("Surname", "   " + Text.lb_option1);

        String d = "";
        if (Text.setKhoangcach.trim().length() != 0) {
            d += Text.setKhoangcach.trim() + " km";
        }

        for (int i = 0; i < (Text.setgan).length; i++) {
            if (Text.setgan[i] == true) {
                if (Text.languages == 0) {
                    d += " banks";
                } else {
                    d += " ngân hàng";
                }
                break;
            }
        }

        Text.lb_option2 = d;


        data[1] = new Hashtable();
        data[1].put("Name", "   " + Text.GanBan);
        data[1].put("Surname", "   " + Text.lb_option2);

        return data;
    }

    public void Setting() {
        try {
            searc1 = new Button(Image.createImage("/images/ok.png"));
            searc2 = new Button(Image.createImage("/images/ok.png"));
            searc1.getStyle().setBgPainter(new Painter() {

                public void paint(Graphics grphcs, Rectangle rctngl) {
                    grphcs.setColor(0x204478);
                    grphcs.fillRect(rctngl.getX(), rctngl.getY(), rctngl.getX() + rctngl.getSize().getWidth(), rctngl.getY() + rctngl.getSize().getHeight());
                }
            });

            searc1.getStyle().setBorder(null);
            searc1.getPressedStyle().setBorder(null);
            searc1.getPressedStyle().setBgPainter(new Painter() {

                public void paint(Graphics grphcs, Rectangle rctngl) {
                    grphcs.setColor(0x204478);
                    grphcs.fillRect(rctngl.getX(), rctngl.getY(), rctngl.getX() + rctngl.getSize().getWidth(), rctngl.getY() + rctngl.getSize().getHeight());
                }
            });
            searc1.addActionListener(this);

            searc2.getStyle().setBgPainter(new Painter() {

                public void paint(Graphics grphcs, Rectangle rctngl) {
                    grphcs.setColor(0x204478);
                    grphcs.fillRect(rctngl.getX(), rctngl.getY(), rctngl.getX() + rctngl.getSize().getWidth(), rctngl.getY() + rctngl.getSize().getHeight());
                }
            });
            searc2.getStyle().setBorder(null);
            searc2.getPressedStyle().setBorder(null);
            searc2.getPressedStyle().setBgPainter(new Painter() {

                public void paint(Graphics grphcs, Rectangle rctngl) {
                    grphcs.setColor(0x204478);
                    grphcs.fillRect(rctngl.getX(), rctngl.getY(), rctngl.getX() + rctngl.getSize().getWidth(), rctngl.getY() + rctngl.getSize().getHeight());
                }
            });
            searc2.addActionListener(this);

            Container1.getStyle().setBgPainter(new Painter() {

                public void paint(Graphics grphcs, Rectangle rctngl) {
                    grphcs.setColor(0x204478);
                    grphcs.fillRect(rctngl.getX(), rctngl.getY(), rctngl.getX() + rctngl.getSize().getWidth(), rctngl.getY() + rctngl.getSize().getHeight());
                }
            });
            Container2.getStyle().setBgPainter(new Painter() {

                public void paint(Graphics grphcs, Rectangle rctngl) {
                    grphcs.setColor(0x204478);
                    grphcs.fillRect(rctngl.getX(), rctngl.getY(), rctngl.getX() + rctngl.getSize().getWidth(), rctngl.getY() + rctngl.getSize().getHeight());
                }
            });

            Container3.getStyle().setBgPainter(new Painter() {

                public void paint(Graphics grphcs, Rectangle rctngl) {
                    grphcs.setColor(0x204478);
                    grphcs.fillRect(rctngl.getX(), rctngl.getY(), rctngl.getX() + rctngl.getSize().getWidth(), rctngl.getY() + rctngl.getSize().getHeight());
                }
            });
            Container4.getStyle().setBgPainter(new Painter() {

                public void paint(Graphics grphcs, Rectangle rctngl) {
                    grphcs.setColor(0x204478);
                    grphcs.fillRect(rctngl.getX(), rctngl.getY(), rctngl.getX() + rctngl.getSize().getWidth(), rctngl.getY() + rctngl.getSize().getHeight());
                }
            });

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        setLayout(new BoxLayout(BoxLayout.Y_AXIS));
        lb1 = new Label(text.NgonNgu);
        lb2 = new Label(text.GanBan);
        lb3 = new Label(text.lb_option1);
        lb1.getStyle().setBgTransparency(0);
        lb2.getStyle().setBgTransparency(0);
        lb3.getStyle().setBgTransparency(0);

        lb1.getStyle().setFgColor(Text.colorWhite);
        lb2.getStyle().setFgColor(Text.colorWhite);
        lb3.getStyle().setFgColor(Text.colorWhite);


        String d = "";
        if (Text.setKhoangcach.trim().length() != 0) {
            d += Text.setKhoangcach.trim() + " km";
        }

        for (int i = 0; i < (Text.setgan).length; i++) {
            if (Text.setgan[i] == true) {
                if (Text.languages == 0) {
                    d += " banks";
                } else {
                    d += " ngân hàng";
                }
                break;
            }
        }

        Text.lb_option2 = d;


        System.out.println(">>>" + Text.lb_option2);

        lb4 = new Label(text.lb_option2);

        Font ft = Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_SMALL);

        lb3.getStyle().setFont(ft);

        lb4.getStyle().setFont(ft);
        lb4.getStyle().setBgTransparency(0);
        lb4.getStyle().setFgColor(Text.colorWhite);


        Container3.setLayout(new BoxLayout(BoxLayout.X_AXIS));
        Container4.setLayout(new BoxLayout(BoxLayout.X_AXIS));


        Container3.setLayout(new BoxLayout(BoxLayout.Y_AXIS));
        Container4.setLayout(new BoxLayout(BoxLayout.Y_AXIS));


        Container3.addComponent(lb1);
        Container3.addComponent(lb3);
        Container4.addComponent(lb2);
        Container4.addComponent(lb4);

        Container1.setLayout(new BorderLayout());
        Container2.setLayout(new BorderLayout());

//        Container1.addComponent(search1);
//        Container2.addComponent(search2);

        contai5.setLayout(new BoxLayout(RIGHT));
        contai6.setLayout(new BoxLayout(RIGHT));
        try {
        } catch (Exception ex) {
        }
        contai5.addComponent(searc1);
        contai6.addComponent(searc2);

        Container3.getStyle().setMargin(5, 5, 5, 5);
        //contai5.getStyle().setMargin(5,5,75,5);

        Container4.getStyle().setMargin(5, 5, 5, 5);
        //contai6.getStyle().setMargin(5,5,40,5);
        contai5.getStyle().setPadding(0, 0, 0, 0);
        contai6.getStyle().setPadding(0, 0, 0, 0);

        Container1.addComponent(BorderLayout.WEST, Container3);
        Container2.addComponent(BorderLayout.WEST, Container4);

        Container1.addComponent(BorderLayout.EAST, contai5);
        Container2.addComponent(BorderLayout.EAST, contai6);


        Container1.getStyle().setMargin(10, 3, 10, 10);
        Container2.getStyle().setMargin(3, 3, 10, 10);


        try {
            Container1.getStyle().setBgPainter(new Painter() {

                public void paint(Graphics grphcs, Rectangle rctngl) {
                    grphcs.setColor(0x204478);
                    grphcs.fillRect(rctngl.getX(), rctngl.getY(), rctngl.getX() + rctngl.getSize().getWidth(), rctngl.getY() + rctngl.getSize().getHeight());
                }
            });
            Container2.getStyle().setBgPainter(new Painter() {

                public void paint(Graphics grphcs, Rectangle rctngl) {
                    grphcs.setColor(0x204478);
                    grphcs.fillRect(rctngl.getX(), rctngl.getY(), rctngl.getX() + rctngl.getSize().getWidth(), rctngl.getY() + rctngl.getSize().getHeight());
                }
            });


        } catch (Exception ex) {
        }

        addComponent(Container1);
        addComponent(Container2);

        Container Container7 = new Container();

//        Container7.getStyle().setMargin(80, 40, 0, 0);
        Container7.getStyle().setMargin(40, 40, 10, 10);

        
        
        Container7.addComponent(new AdBanner("CDiT_ATM_WP", 65));

        Container7.setScrollable(false);
        
        
        //addComponent(Container7);
        //show();
    }

    public void actionPerformed(ActionEvent ae) {

        if (ae.getSource() == list) {
            if (list.getSelectedIndex() == 0) {
                Event evt = new Event();
                evt.setData(text.NgonNgu, ae);
                Control.Controller.getInstance().handleEvent(Event.SECLECT_LANGUAGES, evt);
                Controller.getInstance().categoryBar.setVisibility(false);
            } else if (list.getSelectedIndex() == 1) {
                Event evt = new Event();
                Control.Controller.getInstance().handleEvent(Event.SET_GAN_BAN, evt);
                Controller.getInstance().categoryBar.setVisibility(false);

            }
        }

    }
}
