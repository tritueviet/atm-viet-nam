package views;

import Control.Controller;
import com.nokia.lwuit.components.HeaderBar;
import com.nokia.lwuit.components.HeaderBarListener;
import models.Text;
import com.sun.lwuit.ButtonGroup;
import com.sun.lwuit.CheckBox;
import com.sun.lwuit.Command;
import com.sun.lwuit.Container;
import com.sun.lwuit.Form;
import com.sun.lwuit.Image;
import com.sun.lwuit.Label;
import com.sun.lwuit.List;
import com.sun.lwuit.RadioButton;
import com.sun.lwuit.events.ActionEvent;
import com.sun.lwuit.events.ActionListener;
import com.sun.lwuit.layouts.BorderLayout;
import com.sun.lwuit.layouts.BoxLayout;
import event.Event;
import java.io.IOException;
import java.util.Hashtable;

/**
 *
 * @author WALL
 */
public class Select extends Form implements ActionListener {

    Text text = new Text();
    public OptionATM midlet;
    public Command back2, okie;
    //private String title = "Giới Thiệu";
    public ButtonGroup radioButtonGroup;
    public Image img;

    public Select(String string) {
        ///super(string);
        Text.bienn = Text.languages;
        setLayout(new BoxLayout(BoxLayout.Y_AXIS));

        HeaderBar header = null;
        try {
            header = new HeaderBar(string, Image.createImage("/images/tick.png"), null);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        header.setHeaderTitleColor(Text.colorWhite);
        header.getStyle().setBgColor(Text.hearder);
        header.setHeaderBarListener(new HeaderBarListener() {

            public void notifyHeaderBarListener(HeaderBar hb) {
                Text.languages = radioButtonGroup.getSelectedIndex();
                text.Languge(Text.languages);
                Controller.getInstance().showOptionATM();
                Controller.getInstance().categoryBar.setVisibility(true);
            }
        });
        addComponent(header);

        radioButtonGroup = new ButtonGroup();
        RadioButton radioButton = new RadioButton("  English");

        // radioButton.setUIID("ListItem");
        radioButton.getStyle().setBgTransparency(0);
        radioButton.getStyle().setFgColor(Text.colorWhite);

        radioButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent ae) {
                text.Languge(0);
                Text.languages = 0;

//                    Event evt = new Event();
//                    evt.setData(text.NgonNgu, ae);
                //Control.Controller.getInstance().handleEvent(Event.SECLECT_LANGUAGES, null);
            }
        });

        radioButtonGroup.add(radioButton);
        addComponent(radioButton);

        RadioButton radioButton1 = new RadioButton("  Tiếng Việt");
        //radioButton1.setUIID("ListItem");
        radioButton1.getStyle().setBgTransparency(0);
        radioButton1.getStyle().setFgColor(Text.colorWhite);



        radioButton1.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent ae) {
                text.Languge(1);
                Text.languages = 1;
                //Event evt = new Event();
                //evt.setData(text.NgonNgu, ae);
                //Control.Controller.getInstance().handleEvent(Event.SECLECT_LANGUAGES, null);
            }
        });

        radioButtonGroup.add(radioButton1);
        addComponent(radioButton1);

        //setShowConfirmationQuery(true);
        radioButtonGroup.setSelected(text.languages);



        back2 = new Command(Text.Back);
        okie = new Command(Text.Ok);
        //   setDefaultCommand(okie);
        setBackCommand(back2);
        addCommand(back2);
        // addCommand(okie);
        addCommandListener(this);
        //setShowConfirmationQuery(true);
        layoutContainer();


        /*
        List list = new List(createGenericListCellRendererModelData());
        list.setRenderer(new GenericListCellRenderer(createGenericRendererContainer(),
        createGenericRendererContainer()));
        addComponent(list);
         * 
         */
    }

    public Container createGenericRendererContainer() {
        Container f = new Container(new BorderLayout());



        Container c = new Container(new BoxLayout(BoxLayout.X_AXIS));
        f.setUIID("ListRenderer");
        CheckBox selected = new CheckBox();
        selected.setName("Selected");
        selected.setFocusable(true);
        try {
            img = Image.createImage("/images/pin_icon.png");

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        Label imageLabel = new Label(img);
        Container d = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        Container e = new Container(new BoxLayout(CENTER));
        e.addComponent(selected);


        System.out.println(">>>>>>>>>  7");
        Label name = new Label();
        name.setFocusable(true);
        name.setName("Name");
        d.addComponent(name);
        Label surname = new Label();
        surname.setFocusable(true);
        surname.setName("Surname");
        d.addComponent(surname);

        c.addComponent(imageLabel);
        c.addComponent(d);
        f.addComponent(BorderLayout.CENTER, c);
        f.addComponent(BorderLayout.EAST, e);



        return f;
    }

    public Hashtable[] createGenericListCellRendererModelData() {
        System.out.println(">>>>>>>>>  2");
        Hashtable[] data = new Hashtable[5];
        data[0] = new Hashtable();
        data[0].put("Name", "Shai");
        data[0].put("Surname", "Almog");
        data[0].put("Selected", Boolean.TRUE);
        data[1] = new Hashtable();
        data[1].put("Name", "Chen");
        data[1].put("Surname", "Fishbein");
        data[1].put("Selected", Boolean.TRUE);
        data[2] = new Hashtable();
        data[2].put("Name", "Ofir");
        data[2].put("Surname", "Leitner");
        data[3] = new Hashtable();
        data[3].put("Name", "Yaniv");
        data[3].put("Surname", "Vakarat");
        data[4] = new Hashtable();
        data[4].put("Name", "Meirav");
        data[4].put("Surname", "Nachmanovitch");


        return data;
    }

    public void actionPerformed(ActionEvent ae) {
        System.out.println("chon" + radioButtonGroup.getSelectedIndex());

        if (ae.getSource() == okie) {
            Text.languages = radioButtonGroup.getSelectedIndex();
            text.Languge(Text.languages);
            Controller.getInstance().showOptionATM();
            Controller.getInstance().categoryBar.setVisibility(true);
        } else if (ae.getCommand() == back2) {
            Text.languages = Text.bienn;
            text.Languge(Text.languages);
            Controller.getInstance().showOptionATM();
            Controller.getInstance().categoryBar.setVisibility(true);
        }
    }
}
