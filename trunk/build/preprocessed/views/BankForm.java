/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import com.nokia.lwuit.templates.list.NokiaListCellRenderer;
import models.Text;
import com.sun.lwuit.CheckBox;
import com.sun.lwuit.Command;
import com.sun.lwuit.Container;
import com.sun.lwuit.Form;
import com.sun.lwuit.Image;
import com.sun.lwuit.Label;
import com.sun.lwuit.List;
import com.sun.lwuit.events.ActionEvent;
import com.sun.lwuit.events.ActionListener;
import com.sun.lwuit.layouts.BorderLayout;
import com.sun.lwuit.layouts.BoxLayout;
import com.sun.lwuit.list.DefaultListModel;
import com.sun.lwuit.list.GenericListCellRenderer;
import java.io.IOException;
import java.util.Hashtable;


/**
 *
 * @author PhongHB
 */
public class BankForm extends Form implements ActionListener{
 
    public String title = "Danh sach ngan hang";
    
    public Command backCommand ;
    public  Image img;
    public  List list;
    public BankForm() {
        System.out.println("chay bank");
//        String[] items = { "Red", "Blue", "Green", "Yellow" };
//        DefaultListModel myListModel = new DefaultListModel(items);
//        List list = new List(myListModel);
//        addComponent(list);
        try{ 
        list = new List(createGenericListCellRendererModelData());
        
        }
       catch(Exception e){ e.printStackTrace();}
        
//        NokiaListCellRenderer cha= new NokiaListCellRenderer();
//        list.setRenderer(cha);
        
       try{ 
        list.setRenderer(new GenericListCellRenderer(createGenericRendererContainer(),createGenericRendererContainer()));
       }
       catch(Exception e){ e.printStackTrace();}
        
        addComponent(list);
        
        
        backCommand= new Command(Text.Back);
        setBackCommand(backCommand);
        addCommand(backCommand);
        
    }
public Container createGenericRendererContainer() {
   Container f = new Container(new BorderLayout());
  
   Container c = new Container(new BoxLayout(BoxLayout.X_AXIS));
   f.setUIID("ListRenderer");
   CheckBox selected = new CheckBox();
   selected.setName("Selected");
   selected.setFocusable(true);
        try {
           img= Image.createImage("/images/pin_icon.png");
          
        } catch (IOException ex) {
            ex.printStackTrace();
            System.out.println("loi 12");
        }
   Label imageLabel = new Label(img);
   Container d= new Container(new BoxLayout(BoxLayout.Y_AXIS));
   Container e= new Container(new BoxLayout(CENTER));
   
    System.out.println("chay 13");
   e.addComponent(selected);
   
   
   
   Label name = new Label();
   name.setFocusable(true);
   name.setName("Name");
   d.addComponent( name);    
   Label surname = new Label();
   surname.setFocusable(true); 
   surname.setName("Surname");
   d.addComponent( surname);
    System.out.println("chay 15");
   c.addComponent(imageLabel);
   c.addComponent(d);
   f.addComponent(BorderLayout.CENTER,c);
   f.addComponent(BorderLayout.EAST,e);
   
   
  
   return f;
}
public Hashtable[] createGenericListCellRendererModelData() {
   Hashtable[] data = new Hashtable[6];
   data[0] = new Hashtable(4);
   //data[0].put(Text.GanBan, Text.GioiThieu);
   data[0].put("Name", "Chen");
   data[0].put("Surname", "Almog");
   data[0].put("Selected", Boolean.TRUE);
   data[1] = new Hashtable(4);
   data[1].put("Name", "Chen");
   data[1].put("Surname", "Fishbein");
   data[1].put("Selected", Boolean.TRUE);
   data[2] = new Hashtable(4);
   data[2].put("Name", "Ofir");
   data[2].put("Surname", "Leitner");
   data[2].put("Selected", Boolean.TRUE);
   data[3] = new Hashtable(4);
   data[3].put("Name", "Yaniv");
   data[3].put("Surname", "Vakarat");
   data[3].put("Selected", Boolean.TRUE);
   data[4] = new Hashtable(4);
   data[4].put("Name", "Meirav");
   data[4].put("Surname", "Nachmanovitch");
   data[4].put("Selected", Boolean.TRUE);
   return data;
}
    

   
    

    
    
    public void actionPerformed(ActionEvent ae) {
        
    }

}
