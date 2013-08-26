/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import com.sun.lwuit.util.Resources;
import java.util.Vector;
import javax.microedition.lcdui.Command;
import com.sun.lwuit.*;
import javax.microedition.lcdui.Image;
import javax.microedition.midlet.MIDlet;
import views.LoadScreen;

/**
 * @author WALL
 */
public class Main extends MIDlet {

    public static Resources theme;
    public Vector views;
    public Image icon;
    public Command back, gioithieu;
    
    

    public Main() {
        Display.init(this);
        Display.getInstance().setPureTouch(true);
    }

    public void startApp() {

        Controller.getInstance().loadTheme(1);
        Controller.getInstance().loading(this);
        
    }

    public void pauseApp() {
    }

    public void destroyApp(boolean unconditional) {
        Controller.getInstance().saveConfig();
        notifyDestroyed();
    }
}
