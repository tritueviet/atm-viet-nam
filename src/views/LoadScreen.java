package views;

import Control.Main;
import com.nokia.lwuit.components.HeaderBar;
import java.io.*;
import javax.microedition.lcdui.*;
import javax.microedition.lcdui.game.GameCanvas;
import models.Text;

/**
 * @WAll
 */
public class LoadScreen extends GameCanvas{
    private Graphics g;
    Main main;
    public LoadScreen(Main main) {
        super(false);
        //super(false);
        this.g = this.getGraphics();
        this.main= main;
//        HeaderBar header = null;
//        try {
//            header = new HeaderBar("ATM Vet Nam");
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//        header.setHeaderTitleColor(Text.colorWhite);
//        header.getStyle().setBgColor(Text.hearder);
        
        setFullScreenMode(true);
    }

    public void paint() {
        g.setColor(0xffffff);
        g.fillRect(0, 0, getWidth(), getHeight());
        try {
            g.drawImage(Image.createImage("/images/flashscreen.png"), getWidth() / 2, getHeight() / 2, Graphics.VCENTER | Graphics.HCENTER);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        

    }

    public void start() {
       
            paint();
            
    }
}
