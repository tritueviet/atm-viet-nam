/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import Control.Controller;

import com.nokia.lwuit.components.HeaderBar;
import com.nokia.lwuit.components.HeaderBarListener;
import models.Text;
import com.sun.lwuit.ButtonGroup;
import com.sun.lwuit.Command;
import com.sun.lwuit.Container;
import com.sun.lwuit.Display;
import com.sun.lwuit.Font;
import com.sun.lwuit.Form;

import com.sun.lwuit.Image;
import com.sun.lwuit.RadioButton;
import com.sun.lwuit.events.ActionEvent;
import com.sun.lwuit.events.ActionListener;
import com.sun.lwuit.layouts.BoxLayout;
import event.Event;
import java.util.Vector;

import javax.microedition.lcdui.AlertType;
import models.IViewModel;

/**
 *
 * @author WALL
 */
public class SelectModelForm extends Form implements ActionListener, HeaderBarListener {

    public Command backCommand, doneCmd;
    public RadioButton radioButton;
    public ButtonGroup radioButtonGroup;
    public Vector itemm;
    private int eventType;
    //String[] items= { "Red", "Blue", "Green", "Yellow" };

    public SelectModelForm(int eventType, String string, Vector item1, int seleccted) {
        //super(string);
        
        HeaderBar header = null;
        try {
            header = new HeaderBar(string, Image.createImage("/images/tick.png"), null);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        header.setHeaderTitleColor(Text.colorWhite);
        header.getStyle().setBgColor(Text.hearder);

        header.setScrollable(false);
        header.setHeaderBarListener(this);
        addComponent(header);

        this.itemm = item1;
        this.eventType = eventType;

//        Controller.getInstance().loadTheme(1);
//        refreshTheme();
        setLayout(new BoxLayout(BoxLayout.Y_AXIS));
        Container contai= new Container(new BoxLayout(BoxLayout.Y_AXIS));
        Font font = Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_SMALL);
        
        radioButtonGroup = new ButtonGroup();

        for (int i = 0; i < itemm.size(); i++) {

            IViewModel b = (IViewModel) itemm.elementAt(i);
            radioButton = new RadioButton(b.getName());
            if (b.getId() == seleccted) {
                radioButton.setSelected(true);
            }
             radioButton.getStyle().setFont(font);
            //radioButton.setUIID("ListItem");
            radioButton.getStyle().setBgTransparency(0);
            radioButton.getStyle().setFgColor(Text.colorWhite);

            radioButtonGroup.add(radioButton);
            contai.addComponent(radioButton);

            radioButton.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent ae) {
                }
            });
        }
        contai.setScrollable(true);
        addComponent(contai);
        backCommand = new Command(Text.Back);
        setBackCommand(backCommand);
        addCommand(backCommand);
//        doneCmd = new Command(Text.done);
//        addCommand(doneCmd);
//        setDefaultCommand(doneCmd);
        addCommandListener(this);
        setScrollable(false);
    }

    public void actionPerformed(ActionEvent ae) {


        if (ae.getCommand() == backCommand) {
            Controller.getInstance().showBack();
        }
    }

    public void notifyHeaderBarListener(HeaderBar hb) {
           
            Event evt = new Event();
            
            IViewModel b = ((IViewModel) itemm.elementAt(radioButtonGroup.getSelectedIndex()));
            evt.setData("data", b);    
            
//             Controller.getInstance().loadTheme(0);
//            refreshTheme();
            Controller.getInstance().handleEvent(eventType, evt);
            itemm=null;
            Runtime.getRuntime().gc();
        
    }
}
