
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ads;

import com.sun.lwuit.Graphics;
import com.sun.lwuit.Image;

/**
 *
 * @author Wall
 */
public class Resize {

    public Image resizeImage(Image src, int screenHeight, int screenWidth) {
        int srcWidth = src.getWidth();

        int srcHeight = src.getHeight();
        Image tmp = Image.createImage(screenWidth, srcHeight);
        Graphics g = tmp.getGraphics();
        int ratio = (srcWidth << 16) / screenWidth;
        int pos = ratio / 2;

//Horizontal Resize 

        for (int index = 0; index < screenWidth; index++) {
            g.setClip(index, 0, 1, srcHeight);
            g.drawImage(src, index - (pos >> 16), 0);
            pos += ratio;
        }

        Image resizedImage = Image.createImage(screenWidth, screenHeight);
        g = resizedImage.getGraphics();
        ratio = (srcHeight << 16) / screenHeight;
        pos = ratio / 2;

//Vertical resize

        for (int index = 0; index < screenHeight; index++) {
            g.setClip(0, index, screenWidth, 1);
            g.drawImage(tmp, 0, index - (pos >> 16));
            pos += ratio;
        }
        return resizedImage;

    }
}
