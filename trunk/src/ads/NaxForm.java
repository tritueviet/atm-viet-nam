package ads;

import Control.Controller;
import Control.Main;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

import InneractiveSDK.IADView;

import com.sun.lwuit.Button;
import com.sun.lwuit.Component;
import com.sun.lwuit.Container;
import com.sun.lwuit.Form;
import com.sun.lwuit.Image;
import com.sun.lwuit.events.ActionEvent;
import com.sun.lwuit.events.ActionListener;
import com.sun.lwuit.layouts.BorderLayout;
import com.sun.lwuit.layouts.BoxLayout;
import java.io.InputStream;
import javax.microedition.io.Connector;
import javax.microedition.io.HttpConnection;

public class NaxForm extends Form {

    public Main main;
    protected Container mainContainer;
    private Button adButton;
    Image adImage = null;
    String adLink = null;
    long refreshInterval = 5000;
    String naxAppId;

    public NaxForm() {
        //super("NAX Form");

        BorderLayout layout = new BorderLayout();
        super.setLayout(layout);
        super.setScrollable(false);

        mainContainer = new Container();
        mainContainer.setLayout(new BoxLayout(BoxLayout.Y_AXIS));

        adButton = new Button();
        adButton.getUnselectedStyle().setBorder(null);
        adButton.getPressedStyle().setBorder(null);

        adButton.getPressedStyle().setPadding(0, 0, 0, 0);
        adButton.getUnselectedStyle().setPadding(0, 0, 0, 0);


//        adButton.getPressedStyle().setMargin(0, 0,12,12);
//        adButton.getUnselectedStyle().setMargin(0, 0,12,12);

        adButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent actionEvent) {
                if (adLink != null) {
                    try {
                        main.platformRequest(adLink);
                    } catch (Exception e) {
                    }
                }
            }
        });

        super.addComponent(BorderLayout.CENTER, mainContainer);
        super.addComponent(BorderLayout.SOUTH, adButton);
    }

    public  boolean requestUsingGET(String URLString) throws IOException {
        HttpConnection hpc = null;

        boolean content = false;
        try {
            hpc = (HttpConnection) Connector.open(URLString);
//            System.out.println("1");
            int status = hpc.getResponseCode();
            if (status != HttpConnection.HTTP_OK) {
                content = true;
//                System.out.println("2");
            } else {
                content = false;
//                System.out.println("3");
            }
            if (hpc != null) {
                hpc.close();
            }
        } catch (IOException e2) {
            content = false;
//            System.out.println("4");
        }
//        System.out.println("::"+ content);
        return content;
    }

    public void enableAds(String naxAppId, int refreshInterval) {
        this.naxAppId = naxAppId;
        this.refreshInterval = refreshInterval;

        try {
            if (requestUsingGET("http://google.com") == true) {
                System.out.println("show  nax");
                asyncLoadBanner();
            }
        } catch (IOException ex) {
            ex.printStackTrace();

        }
        // asyncLoadBanner();
        /*
        try {
        Connector.open("http://www.nokia.com");
        asyncLoadBanner();
        } catch (IOException e) {
        }
         */
        Runtime.getRuntime().gc();

    }

    protected void asyncLoadBanner() {
        new Thread(new Runnable() {

            public void run() {
                loadBanner();
            }
        }).start();
    }

    private void loadBanner() {
        Vector ads = IADView.getBannerAdData(main, naxAppId);

        if (ads.size() >= 2) {
            javax.microedition.lcdui.Image lcduiImage = (javax.microedition.lcdui.Image) ads.elementAt(0);
            adLink = (String) ads.elementAt(1);

            Resize re = new Resize();
            adImage = re.resizeImage(Image.createImage(lcduiImage), 30, 240);

            if (adImage != null) {
                adButton.setIcon(adImage);

                this.revalidate();
            }
        }
        if (refreshInterval > 0) {
            new Timer().schedule(new TimerTask() {

                public void run() {
                    try {
                        if (requestUsingGET("http://google.com") == true) {
                            System.out.println("show  nax");
                            asyncLoadBanner();
                        }
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }, refreshInterval);
        }
    }

    public void addComponent(Component cmp) {
        mainContainer.addComponent(cmp);
    }
}
