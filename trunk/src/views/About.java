/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import Control.Controller;
import com.nokia.lwuit.components.HeaderBar;
import models.Text;
import com.sun.lwuit.Command;
import com.sun.lwuit.Container;
import com.sun.lwuit.Font;
import com.sun.lwuit.Form;
import com.sun.lwuit.Label;
import com.sun.lwuit.TextArea;
import com.sun.lwuit.events.ActionEvent;
import com.sun.lwuit.events.ActionListener;
import com.sun.lwuit.layouts.BoxLayout;
import event.Event;


/**
 *
 * @author wall
 */
public class About extends Form implements ActionListener {
   // private FindATMView midlet;
    //private String title = "Giới Thiệu";
    private Command backCommand ;
    private String backView;
      
    
    public About(String backView) {
        //super(backView);
//        Controller.getInstance().loadTheme(0);
//        refreshTheme();
        
        HeaderBar header = null;
        try {
            header = new HeaderBar(backView);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        header.setHeaderTitleColor(Text.colorWhite);
        header.getStyle().setBgColor(Text.hearder);
        
        header.setVisible(true);
        header.setScrollable(false);
        
        addComponent(header);
        setScrollable(false);
        
        
        this.backView = backView;
        //setScrollable(false);
        Controller.getInstance().categoryBar.setVisibility(false);
        refreshTheme();
        repaint();
        Font font = Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_SMALL);
    
      //  setLayout(new BoxLayout(CENTER));
        //TextArea ta = new TextArea("Nội dung giới thiệu aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");

       // ta.getStyle().setFgColor(0xFFFFFF);
      
       // ta.setUIID("Label");
        TextArea textArea=null;
        if (Text.languages==1)
            textArea = new TextArea("Phần mềm ATM Viet Nam cung cấp thông tin địa chỉ của gần 6,000 cây ATM của 26 ngân hàng hiện đang hoạt động trên khắp 64 tỉnh thành của Việt Nam "
        +"\nNgôn ngữ hỗ trợ: Tiếng Anh và tiếng Việt."
        +"\nCác tính năng: Tìm kiếm cây atm với nhiều tùy chọn, xem bản đồ vị trí cây atm, dẫn đường vị trí người dùng đến vị trí cây atm, lưu địa điểm cây atm yêu thích."
        + "\n\nPhần mềm được xây dựng và cập nhật thường xuyên bởi Viện công nghệ thông tin và truyền thông CDIT."
        + "\nLiên hệ:"
        + "\nWebsite: www.cdit.com.vn "
        + "\nFacebook: www.facebook.com/CDITpage"
        + "\nEmail: support@cdit.com.vn "
        + "\n\nApplication : ATM Viet Nam for Nokia Asha 501 "
        + "\nVersion: 1.0.0 "
        + "\nCopyright  ©2013 CDiT. All rights reversed", 2, 10);
         
        else  textArea = new TextArea("ATM Viet Nam application provides the ATM information include: 6000 ATM address of 26 banks in 64 provinces and cities of Viet Nam."
        + "\nSupported languages: English and Vietnamese."
                + "\nFunction: Finding with many options, viewing map, getting direction, saving favourite locations."
                + "\n\nThe application has been developed and updated by Institute of Information and Communications Technology CDiT. "
        + "\n\nContact:"
        + "\nWebsite: www.cdit.com.vn "
        + "\nFacebook: www.facebook.com/CDITpage"
        + "\nEmail: support@cdit.com.vn "
        + "\n\nApplication : ATM Viet Nam for Nokia Asha 501"
        + "\nVersion: 1.0.0 "
        + "\nCopyright  ©2013 CDiT. All rights reversed", 2, 10);
        
        textArea.setEditable(false);
        textArea.setGrowByContent(true);
        textArea.getStyle().setBgTransparency(0);
        //textArea.setUIID("Label");
        textArea.getStyle().setFont(font);
        Container contai= new Container();
        contai.addComponent(textArea);
        contai.setScrollable(true);
        contai.setScrollableX(false);
        addComponent(contai);
       // ta.setEditable(false);
        //setLayout(new BoxLayout(BoxLayout.Y_AXIS));
        backCommand= new Command(Text.Back);
        setBackCommand(backCommand);
        addCommand(backCommand);
       // addComponent(ta);
        addCommandListener(this);
    }

  
    public void actionPerformed(ActionEvent ae) {
       if(ae.getCommand()==backCommand){
           Controller.getInstance().categoryBar.setVisibility(true);
           Event evt = new Event();
           Controller.getInstance().handleEvent(Event.ABOUT_BACK, evt);
       }
    }

}
