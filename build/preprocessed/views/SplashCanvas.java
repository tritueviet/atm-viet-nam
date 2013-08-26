/**
* Copyright (c) 2012-2013 Nokia Corporation. All rights reserved.
* Nokia and Nokia Connecting People are registered trademarks of Nokia Corporation. 
* Oracle and Java are trademarks or registered trademarks of Oracle and/or its
* affiliates. Other product and company names mentioned herein may be trademarks
* or trade names of their respective owners. 
* See LICENSE.TXT for license information.
*/
package views;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.GameCanvas;
import javax.microedition.lcdui.game.Sprite;
import util.Util;

/**
 * Splash view. Shows application name and loading animation while 
 * initializing resources.
 */
public final class SplashCanvas
    extends GameCanvas {

    private static SplashCanvas self = null;
    private Image image;
    private static final int LOADER_HEIGHT = 24;
    private Sprite loaderSprite;
    private Timer animationTimer;
    private int width;
    private int height;
    private Graphics g;
    private volatile boolean hidden = true;

    private SplashCanvas() {
        super(false);
        
        
      //  setFullScreenMode(true);
    }

    public boolean isHidden() {
        return hidden;
    }

    /**
     * @return SplashCanvas singleton
     */
    public static SplashCanvas getInstance() {
        if (self == null) {
            self = new SplashCanvas();
        }
        return self;
    }

    /**
     * @see GameCanvas#showNotify() 
     */
    protected final void showNotify() {
        hidden = false;
        g = getGraphics();

        // S60 doesn't call sizeChanged on start-up so it has to be called here.
        sizeChanged(getWidth(), getHeight());
            
        draw();
        animationTimer = new Timer();
        animationTimer.schedule(new TimerTask() {

            public void run() {
                draw();
            }
        }, 50, 50);
    }

    /**
     * @see GameCanvas#hideNotify() 
     */
    protected final void hideNotify() {
        hidden = true;
        animationTimer.cancel();
        animationTimer = null;
        image = null;
        loaderSprite = null;
        System.gc();
    }

    /**
     * @see GameCanvas#sizeChanged(int, int) 
     */
    protected final void sizeChanged(int w, int h) {
        width = w;
        height = h;
    }

    private void draw() {
        //Draw background
       // g.setColor(0xffffff);
       // g.fillRect(0, 0, width, height);

        if (image == null) {
            try {
                image = Image.createImage("/images/background.png");
            }
            catch (IOException e) {
            }
        }

     //   Draw the image
        if (image != null) {
            g.drawImage(image, getWidth() / 2, getHeight() / 2, Graphics.VCENTER | Graphics.HCENTER);
        }

        if (loaderSprite == null) {
            try {
                Image i = Image.createImage("/images/loading.png");
                loaderSprite = new Sprite(i, i.getWidth() / 8, i.getHeight());
            }
            catch (IOException e) {
            }
        }

        if (loaderSprite != null) {
            loaderSprite.nextFrame();
            loaderSprite.setPosition((width - loaderSprite.getWidth()) / 2, height/2);
            loaderSprite.paint(g);
        }

        flushGraphics();
    }

    /**
     * @see GameCanvas#pointerPressed(int, int) 
     */
    protected final void pointerPressed(int x, int y) {
    }
}
