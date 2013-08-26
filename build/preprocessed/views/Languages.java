/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import com.sun.lwuit.Command;
import com.sun.lwuit.Font;
import com.sun.lwuit.Form;
import com.sun.lwuit.TextArea;
import com.sun.lwuit.events.ActionEvent;
import com.sun.lwuit.events.ActionListener;
import com.sun.lwuit.layouts.BoxLayout;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Displayable;

/**
 *
 * @author PhongHoang
 */
public class Languages extends Form implements ActionListener, CommandListener {
    public FindATMView midlet;
    public String title = "Giới Thiệu";
    public Command backCommand ;
    public Languages(FindATMView aThis, String string) {
        super(string);
        this.midlet=aThis;
        this.title=string;
        Font font = Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_SMALL);
    
        setLayout(new BoxLayout(CENTER));
        TextArea ta = new TextArea("Nội dung giới thiệu");

        ta.getStyle().setFgColor(0xFFFFFF);
        ta.getStyle().setMargin(10, 0, 10, 0);
        ta.setUIID("Label");
        ta.getStyle().setFont(font);
        ta.setColumns(20);
        ta.setRows(20);
        ta.setGrowByContent(true);
        ta.setEditable(false);
        addComponent(ta);
      //  backCommand=new Command("Backcomment");
        //addCommand(backCommand);
        //addCommandListener(this);
    }

    public void actionPerformed(ActionEvent ae) {
       if(ae.getSource()=="Backcomment"){
           
          
       }
    }

    public void commandAction(javax.microedition.lcdui.Command c, Displayable d) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

  

   
   
}
