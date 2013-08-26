package Control;

import com.sun.lwuit.events.ActionEvent;
import javax.microedition.lcdui.Displayable;
import models.Text;
import views.ShowSearhForm;
import views.About;
import views.SelectModelForm;
import views.FormNearestMe;
import views.FindATMView;
import views.FormFavoriteAtm;
import views.OptionATM;
import views.Select;
import com.nokia.mid.ui.CategoryBar;
import com.nokia.mid.ui.ElementListener;
import com.nokia.mid.ui.IconCommand;
import com.sun.lwuit.Button;
import com.sun.lwuit.Container;
import com.sun.lwuit.Dialog;
import com.sun.lwuit.Font;
import com.sun.lwuit.Label;
import com.sun.lwuit.TextArea;
import com.sun.lwuit.events.ActionListener;
import com.sun.lwuit.layouts.BorderLayout;
import com.sun.lwuit.layouts.BoxLayout;
import com.sun.lwuit.plaf.UIManager;
import com.sun.lwuit.util.Resources;
import event.Event;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;
import javax.microedition.io.Connector;
import javax.microedition.io.HttpConnection;
import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.AlertType;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Image;
import location.LocationFinder;
import map.Map;
import models.ATM;
import models.Bank;
import models.District;
import models.IViewModel;
import models.Province;
import util.StorageData;
import views.BankForm;
import views.BankLeagueForm;
import views.LoadScreen;
import views.MapView;
import views.Near;
import views.ShowFavoriteFormDelete;
import views.SplashCanvas;

/**
 *
 * @author WALL
 */
public class Controller {

    boolean ktrr = false;
    int pp = 10;
    public static int head = 0;
    public static int tap = 0;
    private static Controller instance = null;
    public BankLeagueForm bankLeagueForm = null;
    public FindATMView findATMView = null;
    public OptionATM optionATM = null;
    public BankForm bankss = null;
    public FormFavoriteAtm formFavoriteAtm = null;
    public FormNearestMe formNearestMe = null;
    public Select select = null;
    public SelectModelForm selectModelForm = null;
    public About about = null;
    public Near Set_Gan_Ban = null;
    public MapView mapview;
    public ShowFavoriteFormDelete Show_Favorite_Delete;
    public CategoryBar categoryBar = null, catrgoryBar2 = null;
    public Image icon = null, icon2 = null;
    public Main main = null;
    public static Resources theme = null, theme1 = null;
    public Vector atms;
    public ShowSearhForm show_search_Form;
    public Vector banks;
    public Vector provinces;
    public Vector districts;
    private int provinceId;
//    private int bankId;
//    private String bankName;
    private int districtId;
    private Map map;
    private LocationFinder locationFinder;
    public double curentLat, curentLon;
    private int currentForm;
    public Text text = new Text();
    private SplashCanvas splashCanvas;
    Timer timer;
    private boolean hasCurrentLocation = false;
    double latz, lonz;
    public boolean dau = false;  //  show map lan 1

    private Controller() {
        // Init here
        // getCurrentLocation();
        init();
    }

    private void init() {
        StorageData.getInstance().loadConfig();
        setCarogriBar2();
        cai();
    }

    public void loading(Main main) {
        //loadTheme(1);
        this.main = main;
        map = new Map(javax.microedition.lcdui.Display.getDisplay(main));

        // Set up the first view of the application.
//        splashCanvas = SplashCanvas.getInstance();
//        javax.microedition.lcdui.Display.getDisplay(main).setCurrent(splashCanvas);
//      
        if (1 == 0) {
            LoadScreen load = new LoadScreen(main);
            javax.microedition.lcdui.Display.getDisplay(main).setCurrent(load);
            load.start();
            load = null;
        }
        categoryBar.setVisibility(false);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }

        categoryBar.setVisibility(true);
        showFindATMView();

    }

    public static Controller getInstance() {
        if (instance == null) {
            instance = new Controller();
        }
        return instance;
    }

    public boolean requestUsingGET(String URLString) throws IOException {
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

    private void cai() {
        IconCommand[] iconCommands = new IconCommand[4];

        for (int i = 0; i < iconCommands.length; i++) {
            try {
                icon = Image.createImage("/images/icon" + (i + 1) + ".png");
                icon2 = Image.createImage("/images/I" + (i + 1) + ".png");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            iconCommands[i] = new IconCommand("", icon, icon2, Command.SCREEN, 1);

        }
        categoryBar = new CategoryBar(iconCommands, true);
        // categoryBar.setHighlightColour(bankId);

        categoryBar.setElementListener(new CategoryElementListener());

        //categoryBar.setVisibility(true);

    }

    public void setCarogriBar2() {
        IconCommand[] iconCommands = new IconCommand[2];

        for (int i = 0; i < iconCommands.length; i++) {
            try {
                icon = Image.createImage("/images/anh" + (i + 1) + ".png");
                icon2 = Image.createImage("/images/A" + (i + 1) + ".png");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            iconCommands[i] = new IconCommand("", icon, icon2, Command.SCREEN, 1);

        }
        catrgoryBar2 = new CategoryBar(iconCommands, true);
        // categoryBar.setHighlightColour(bankId);

        catrgoryBar2.setElementListener(new CategoryElementListener());

        catrgoryBar2.setVisibility(false);

    }

    public void handleEvent(int eventType, Event evt) {
        switch (eventType) {


            case Event.DELETE_FAVORITES:
                ATM atm = (ATM) evt.getData("ATM");

                StorageData.getInstance().removeFavoriteATM(atm.getId());
                //   showMessage(Text.done, AlertType.CONFIRMATION);
                showFormFavoriteAtm();

                break;
            case Event.VIEW_NGAN_HANGLIENKET:
                showBankLeaguaForm();
                break;
            case Event.FIND_ATM_LEAGUE:
                Bank b1 = (Bank) evt.getData("bank");
                Text.bank_adv_id = b1.getId();
                atms = StorageData.getInstance().getAtmsByBankDistrict(Text.bank_adv_id, districtId);
                Text.bank_adv_name = b1.getName();
                showSearchForm(Text.bank_adv_name);
                break;
            case Event.ADD_FAVARITES:
                atm = (ATM) evt.getData("ATM");
                StorageData.getInstance().addFavoriteATM(atm.getId());
                showMessage(Text.done, AlertType.CONFIRMATION);
                break;
            case Event.VIEW_DELETE_FAVORITE:
                Vector a = (Vector) evt.getData("ATM");
                text.InitMangXoa(a.size());
                showDeleteFavariteForm(a);

                break;
            case Event.VIEW_MAP:
                atm = (ATM) evt.getData("ATM");
                Text.Items = atm;
                //  if (map == null) {
                //      map = new Map(javax.microedition.lcdui.Display.getDisplay(main));
                //  }
                //   map.setPlace(atm.getLat(), atm.getLon());

                if (dau == false) {
                    dau = true;
                    showMap(atm.getLat(), atm.getLon());

                } else {
                    showMap2(atm.getLat(), atm.getLon());
                }
                break;

            case Event.VIEW_MAP_CONDUCTOR:
                atm = (ATM) evt.getData("ATM");
                Text.Items = atm;
                showDirector(atm.getLat(), atm.getLon());
                break;

            case Event.FIND_ATM_VIEW_SEARCH_ADV: {
                atms = StorageData.getInstance().getAtmsByBankDistrict(Text.bank_adv_id, districtId);
                showSearchForm(Text.bank_adv_name);

            }
            break;
            case Event.FIND_ATM_VIEW_ABOUT: {
                showAboutView(Text.GioiThieu);
            }
            break;
            case Event.ABOUT_BACK: {

                showFindATMView();
                //categoryBar.setVisibility(true);
            }
            break;
            case Event.SECLECT_LANGUAGES: {
                showSelectLanguages(Text.NgonNgu);
            }
            break;
            case Event.FIND_ATM_VIEW_SEARCH_BANK: {
                // String title = evt.getData("Title").toString();
                showSelectBank(Text.NganHang);
            }
            break;
            case Event.FIND_ATM_VIEW_SEARCH_PROVINCE: {
                //String title = evt.getData("Title").toString();
                showSelectProvince(Text.Tinh);
            }
            break;
            case Event.FIND_ATM_VIEW_SEARCH_DISTRICT: {
                //String title = evt.getData("Title").toString();
                showSelectDistrict(Text.QuanHuyen);
            }
            break;
            case Event.SET_GAN_BAN: {

                // if (banks==null){
                banks = StorageData.getInstance().getBanks();
                //    System.out.println(" load lai");
                //}
                showSetGanBan();

            }
            break;
            // Tim kiem nang cao
            case Event.SELECT_BANK:
                IViewModel b = (IViewModel) evt.getData("data");
                Text.bank_adv_id = b.getId();
                Text.bank_adv_name = b.getName();
                if (findATMView != null) {
                    findATMView.loadBank(b.getName());
                    findATMView.show();
                    categoryBar.setVisibility(true);
                }
                break;

            case Event.SELECT_PROVINCE:
                IViewModel p = (IViewModel) evt.getData("data");
                provinceId = p.getId();
                Text.prov_adv = provinceId;
                if (findATMView != null) {
                    findATMView.loadProvince(p.getName());
                    District d = StorageData.getInstance().getDistrict(provinceId);
                    findATMView.loadDistrict(d.getName());
                    districtId = d.getId();
                    findATMView.show();
                    categoryBar.setVisibility(true);
                }
                break;

            case Event.SELECT_DISTRICT:
                IViewModel d = (IViewModel) evt.getData("data");
                districtId = d.getId();
                if (findATMView != null) {
                    findATMView.loadDistrict(d.getName());
                    findATMView.show();
                    categoryBar.setVisibility(true);
                }
                break;
        }
        evt = null;
    }

    public void showFindATMView(Bank bank, Province p, District d) {
        if (findATMView == null) {
            findATMView = new FindATMView();
        }
        //categoryBar.setVisibility(false);

        findATMView.loadBank(bank.getName());
        findATMView.loadProvince(p.getName());
        findATMView.loadDistrict(d.getName());

        findATMView.show();
    }

    public void showFindATMView() {
        //   if (findATMView == null) {
        currentForm = Event.FORM_FIND_ATM;

        findATMView = new FindATMView();
        provinceId = Text.prov_adv;
        String proName = StorageData.getInstance().getProvinceName(provinceId);
        districts = StorageData.getInstance().getDistricts(provinceId);
        findATMView.loadBank(Text.bank_adv_name);
        findATMView.loadProvince(proName);
        District district = StorageData.getInstance().getDistrict(provinceId);
        districtId = district.getId();
        findATMView.loadDistrict(district.getName());
        banks = null;
        provinces = null;
        districts = null;
        Controller.getInstance().categoryBar.setVisibility(true);
        categoryBar.setSelectedIndex(0);
        findATMView.show();
    }

    public void showBankLeaguaForm() {

        currentForm = Event.FORM_RESULT_ATM;

        Vector items = StorageData.getInstance().getBanksLeague(Text.bank_adv_id);
        text.InitNganHangLienKet(items.size());
        bankLeagueForm = new BankLeagueForm(Text.NganHangLienKet, items);
        bankLeagueForm.show();
    }

    public void showBankLeaguaForm2(Vector items) {
        bankLeagueForm = new BankLeagueForm(Text.NganHangLienKet, items);
        bankLeagueForm.show();
    }

    public void showSearchForm(String name) {
        System.out.println(">>" + currentForm);
        currentForm = Event.FORM_RESULT_ATM;
        if (currentForm == Event.FORM_RESULT_ATM) {
            if (show_search_Form == null) {
                show_search_Form = new ShowSearhForm(name, atms);
            }
        } else {
            show_search_Form = new ShowSearhForm(name, atms);
        }
        categoryBar.setVisibility(false);
        show_search_Form.show();
    }

    private void getAtms() {
        boolean d = true;
        boolean b = true;
        if (Text.setKhoangcach.trim().length() == 0) {
            d = false;
        }

        int j = 0, i, c = 0, n = Text.setgan.length;
        for (i = 0; i < n; ++i) {
            if (Text.setgan[i] == true) {
                ++c;
            }
        }
        int[] banksID = new int[c];
        for (i = 0; i < n; ++i) {
            if (Text.setgan[i] == true) {
                //System.out.println("ID " +i);
                banksID[j++] = i + 1;
            }
        }

        if (c == 0) {
            b = false;
        }
        // get current location

        //Distance + Bank
        if (d && b) {
            atms = StorageData.getInstance().getAtmsWithDistanceAndBank(Integer.parseInt(Text.setKhoangcach), banksID, curentLon, curentLat);
        } else if (d && !b) {// Only distance
            atms = StorageData.getInstance().getAtmsWithDistance(Integer.parseInt(Text.setKhoangcach), curentLon, curentLat);
        } else if (!d && b) { // Only banks
            atms = StorageData.getInstance().getAtmsWithBank(banksID, curentLon, curentLat);
        } else {
            atms = StorageData.getInstance().getNearestAtms(curentLon, curentLat);
        }
        System.out.println("End load");
    }

    public void showFormNearestAtm() {

        currentForm = Event.FORM_NEAR;

        getAtms();

        //   if (favoriteATM == null) {
        if (tap == 1) {
            formNearestMe = new FormNearestMe(Text.GanBan, atms);
            formNearestMe.show();
        }
        //currentForm = Event.VIEW_AUTO_SEARCH;
        // categoryBar.setVisibility(true);

    }

    public void showDeleteFavariteForm(Vector items) {
        Show_Favorite_Delete = new ShowFavoriteFormDelete(Text.XoaUaThich, items);
        Show_Favorite_Delete.show();
    }

    public void showFormFavoriteAtm() {

        currentForm = Event.FORM_FAVORITE;

        splashCanvas = SplashCanvas.getInstance();
        javax.microedition.lcdui.Display.getDisplay(main).setCurrent(splashCanvas);
        timer = new Timer();
        timer.schedule(new TimerTask() {

            public void run() {
                formFavoriteAtm = new FormFavoriteAtm();
                if (splashCanvas == javax.microedition.lcdui.Display.getDisplay(main).getCurrent()) {
                    if (tap == 2) {
                        formFavoriteAtm.show();
                    }
                }


            }
        }, 0);

    }

    public void showSelectLanguages(String tittle) {
        //if (select == null) {
        select = new Select(tittle);
        //}
        select.show();
    }

    public void showOptionATM() {
        //if (optionATM == null) {
        optionATM = new OptionATM();
        // }
        optionATM.show();
    }

    public void showBank() {
        // if (bankss == null) {
        bankss = new BankForm();
        //   }
        bankss.show();
    }

    public void showSelectBank(String title) {

        banks = StorageData.getInstance().getBanks();

        selectModelForm = new SelectModelForm(Event.SELECT_BANK, title, banks, Text.bank_adv_id);
        selectModelForm.show();
        banks = null;
        Runtime.getRuntime().gc();
    }

    public void showSelectDistrict(String title) {

        districts = StorageData.getInstance().getDistricts(provinceId);
        selectModelForm = new SelectModelForm(Event.SELECT_DISTRICT, title, districts, districtId);
        selectModelForm.show();

        districts = null;
        Runtime.getRuntime().gc();
    }

    public void showSelectProvince(String title) {
        provinces = StorageData.getInstance().getProvinces();
        selectModelForm = new SelectModelForm(Event.SELECT_PROVINCE, title, provinces, provinceId);
        selectModelForm.show();

        provinces = null;
        Runtime.getRuntime().gc();
    }

    public static void loadTheme(int k) {
        try {


//            if (k == 0) {
//                if (theme1 == null) {
//                    theme1 = Resources.open("/themes/full_touch_theme_1.res");
//                }
//                UIManager.getInstance().setThemeProps(theme1.getTheme(theme1.getThemeResourceNames()[0]));
//            } else {
            if (theme == null) {
                theme = Resources.open("/themes/full_touch_theme.res");
            }
            UIManager.getInstance().setThemeProps(theme.getTheme(theme.getThemeResourceNames()[0]));


        } catch (Throwable ex) {
            //Dialog.show("Exception", ex.getMessage(), "OK", null);
        }

        //refreshTheme();
    }

    public void showBack() {
        System.out.println("curent form: " + currentForm);
        if (dau == true) {
            mapview.huyMap();
        }

        switch (currentForm) {
            case Event.FORM_NEAR:

                backShowNearMe();
                break;
            case Event.FORM_RESULT_ATM:
                showSearchForm(Text.bank_adv_name);
                break;
            case Event.FORM_LEAGUE_BANK:
                showBankLeaguaForm();
                break;
            case Event.FORM_FIND_ATM:
                showFindATMView();
                break;
            case Event.FORM_FAVORITE:
                categoryBar.setVisibility(true);
                showFormFavoriteAtm();
                break;
            case Event.END:
                exit();
            default:
                showFindATMView();
        }
    }

    public void showAboutView(String tittle) {
        //if (about == null) {
        about = new About(tittle);
        //}
        about.show();
    }

    public void exit() {
        //   saveConfig();
        main.destroyApp(false);
    }

    public void showSetGanBan() {
        if (Set_Gan_Ban == null) {
            Set_Gan_Ban = new Near(Text.GanBan, banks);
        }
        Set_Gan_Ban.show();
        Set_Gan_Ban = null;
        Runtime.getRuntime().gc();
    }

    public void showMap(double lat, double lon) {
        head = 0;
        System.out.println("lat :" + lat + "  lon: " + lon);
        System.out.println("lat :" + Text.Items.getLat() + "  lon: " + Text.Items.getLon());
        //mapview.huyMap();
        MyTimerTask mt = new MyTimerTask();
        Timer timer = new Timer();
        timer.schedule(mt, 10, 10);
    }

    public void showMap2(double lat, double lon) {
        head = 0;
//        splashCanvas = SplashCanvas.getInstance();
//        javax.microedition.lcdui.Display.getDisplay(main).setCurrent(splashCanvas);
        timer = new Timer();
        timer.schedule(new TimerTask() {

            public void run() {
                map = new Map(javax.microedition.lcdui.Display.getDisplay(main));
                //map.drawRoute(0, 0, 0, 0);
                map.setPlace(Text.Items.getLat(), Text.Items.getLon());
                mapview = new MapView(map);
//                if (splashCanvas == javax.microedition.lcdui.Display.getDisplay(main).getCurrent()) {
                mapview.show();
//                }


            }
        }, 0);


    }

    /**
     * Auto finder
     */
    public void autoFinder() {

        currentForm = Event.VIEW_AUTO_SEARCH;
        timer = new Timer();

        timer.schedule(new TimerTask() {

            public void run() {
                locationFinder = LocationFinder.getFinder(new LocationFinder.Listener() {

                    public void newLocation(double lat, double lon, int accuracy) {
                        curentLat = lat;//21.027561;//lat;
                        curentLon = lon;//105.85596;//lon;
                        timer.cancel();
                        if (splashCanvas == javax.microedition.lcdui.Display.getDisplay(main).getCurrent()) {
                            System.out.println("tap: " + tap);
                            if (tap == 1) {
                                showFormNearestAtm();
                            }
                        }

                    }
                }, new LocationFinder.NotFoundLocation() {

                    public void actionPerforment() {
                        if (splashCanvas == javax.microedition.lcdui.Display.getDisplay(main).getCurrent()) {
//                            Alert a = new Alert(Text.khongTimThay);
//                            a.setType(AlertType.INFO);
//                            javax.microedition.lcdui.Display.getDisplay(main).setCurrent(a);
//                            Dialog.show(null, Text.khongTimThay, Text.Ok, null);
                            showMessenger(null, Text.khongTimThay);
                            
                            timer.cancel();
                            showBack();
                        }
                    }
                });

                splashCanvas = SplashCanvas.getInstance();
                javax.microedition.lcdui.Display.getDisplay(main).setCurrent(splashCanvas);
                refreshLocationFinder();
            }
        }, 0); // minimum time the splash is shown


    }

    public void showMessage(String txt, AlertType type) {
//        Alert alrt = new Alert(Text.Loi);
//        alrt.setString(txt);
//        alrt.setType(type);
//        javax.microedition.lcdui.Display.getDisplay(main).setCurrent(alrt);
        
        Dialog di = new Dialog(Text.Loi);
        di.setScrollable(false);
        di.addComponent(new TextArea(txt));
        di.setTimeout(1000);
        di.show();
//        Dialog.show(Text.Loi, txt,Text.Ok,null);
//        di.setDialogPosition(BorderLayout.CENTER);
        
        //showMessenger(Text.Loi, txt);
    }

    public void backShowNearMe() {
//        if (map != null) {
//            map.huy();
//        }
        System.out.println("Call back: " + atms.size());
        formNearestMe = new FormNearestMe(Text.GanBan, atms);
        formNearestMe.show();
        //   }
        //currentForm = Event.VIEW_AUTO_SEARCH;
        categoryBar.setVisibility(true);
    }

    public void showDirector(double latzt, double lonzt) {

        mapview.huyMap();

        this.latz = latzt;
        this.lonz = lonzt;
        System.out.println("cuẻntLAt: " + curentLat);
        System.out.println("cuẻntLon: " + curentLon);
//        map = new Map(javax.microedition.lcdui.Display.getDisplay(main));
//        map.drawRoute(21.027561, 105.85596, 21.047561, 105.95596);
//        mapview = new MapView(map);
//        mapview.show();
        inittLocationFinder();

        /* 
        MyTimerTask2 mt = new MyTimerTask2();
        Timer timer = new Timer();
        timer.schedule(mt, 10, 10);
         */

    }

    private void inittLocationFinder() {
        timer = new Timer();
        timer.schedule(new TimerTask() {

            public void run() {
                locationFinder = LocationFinder.getFinder(new LocationFinder.Listener() {

                    public void newLocation(double lat, double lon, int accuracy) {
                        curentLat = lat;//21.027561;//lat;
                        curentLon = lon;//105.85596;//lon;
                        //timer.cancel();
                        map = new Map(javax.microedition.lcdui.Display.getDisplay(main));
                        map.drawRoute(curentLat, curentLon, latz, lonz);
                        mapview = new MapView(map);

                        if (splashCanvas == javax.microedition.lcdui.Display.getDisplay(main).getCurrent()) {
                            //showDirection();
                            head = 1;


                            //map.zoom(14);
                            mapview.show();
//                            try {
//                                Thread.sleep(50);
//                            } catch (InterruptedException ex) {
//                                ex.printStackTrace();
//                            }
                            //map.zoom(14);
//                            map.zoom(14);
                            timer.cancel();
                        }

                    }
                }, new LocationFinder.NotFoundLocation() {

                    public void actionPerforment() {
                        if (splashCanvas == javax.microedition.lcdui.Display.getDisplay(main).getCurrent()) {
//                            Alert a = new Alert(Text.khongTimThay);
//                            a.setType(AlertType.INFO);
//                            javax.microedition.lcdui.Display.getDisplay(main).setCurrent(a);
//                            Dialog.show(null, Text.khongTimThay, Text.Ok, null);
                            showMessenger(null, Text.khongTimThay);
                            timer.cancel();
                            showBack();
                        }
                    }
                });

                splashCanvas = SplashCanvas.getInstance();
                javax.microedition.lcdui.Display.getDisplay(main).setCurrent(splashCanvas);
                refreshLocationFinder();
            }
        }, 0);

//        map = new Map(javax.microedition.lcdui.Display.getDisplay(main));
//        map.drawRoute(curentLat, curentLon, latz, lonz);
//        mapview = new MapView(map);
//        mapview.show();
    }

    private synchronized void startLocationFinder() {
        if (locationFinder != null) {
            locationFinder.start();
        }
    }

    private synchronized void stopLocationFinder() {
        if (locationFinder != null) {
            locationFinder.quit();
        }
    }

    public void showConfirm2(final HandleConfirm handle) {

        Alert a = new Alert(Text.tDelete, Text.delete, null, AlertType.INFO);//new Alert(txt);
        a.setType(AlertType.INFO);
        final Command cmdYes = new Command(Text.txtYes, Command.OK, 1);
        final Command cmdNo = new Command(Text.txtNo, Command.CANCEL, 2);
        a.addCommand(cmdYes);
        a.addCommand(cmdNo);
        a.setCommandListener(new CommandListener() {

            public void commandAction(Command c, Displayable d) {
                if (c == cmdYes) {
                    handle.actionYes();
                } else {
                    handle.actionNo();
                }
            }
        });
        Display.getDisplay(main).setCurrent(a);
    }
    public void showMessenger(String title, String content){
         Dialog di = new Dialog(title);
        di.setScrollable(false);
        TextArea txtarea= new TextArea(content,2,10);
        di.addComponent(txtarea);
        di.setTimeout(1000);
        di.show();
    }
    
    public void showConfirm(final HandleConfirm handle) {
        Container dispback = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        TextArea textArea = new TextArea(Text.delete); //pass the alert text here
        textArea.setFocusable(false);
        textArea.setEditable(false);
        Font fnt = Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_MEDIUM);
        textArea.getStyle().setFont(fnt);
        textArea.getSelectedStyle().setBorder(null);
        textArea.getUnselectedStyle().setBorder(null);
        textArea.getStyle().setFgColor(0xFF0000);
        textArea.getStyle().setBgTransparency(0);
        textArea.setIsScrollVisible(false);
        textArea.getStyle().setMargin(20, 0, 0, 0);

        Button okbut = new Button(Text.txtYes);
        Button canl = new Button(Text.txtNo);
        //okbut.setAlignment(Component.CENTER);
        okbut.getStyle().setFont(fnt);
        canl.getStyle().setFont(fnt);
        okbut.getStyle().setAlignment(Button.CENTER);
        okbut.getPressedStyle().setAlignment(Button.CENTER);
        okbut.getUnselectedStyle().setAlignment(Button.CENTER);
        canl.getPressedStyle().setAlignment(Button.CENTER);
        canl.getUnselectedStyle().setAlignment(Button.CENTER);
        
        okbut.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent ae) {
                handle.actionYes();
            }
        });
        canl.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent ae) {
                handle.actionNo();
            }
        });
        dispback.addComponent(textArea);
        dispback.addComponent(okbut);
        dispback.addComponent(canl);

        System.out.println("show dialog");
        Dialog.show(Text.tDelete, dispback, null, 0, null);
    }

    /**
     * Refresh the location finder.
     */
    public final void refreshLocationFinder() {

        startLocationFinder();
    }

    public void saveConfig() {
        StorageData.getInstance().updateConfig();
    }

    class CategoryElementListener implements ElementListener {

        public void notifyElementSelected(CategoryBar bar, int selectedIndex) {
            if (bar == categoryBar) {
                if (selectedIndex == 0) {
                    tap = 0;
                    showFindATMView();

                } else if (selectedIndex == 1) {
                    tap = 1;
                    autoFinder();

                } else if (selectedIndex == 2) {
                    tap = 2;
                    showFormFavoriteAtm();
                } else if (selectedIndex == 3) {
                    tap = 3;
                    showOptionATM();
                } else {
                    try {
                        if (categoryBar.getVisibility()) {
                            exit();
                        } else {
                            showBack();
                        }
                    } catch (Exception ex) {
                        System.out.println("k thoats ddc ");
                    }
                }
            } else {
                if (selectedIndex == 0) {
                    head = 0;
                    if (map != null) {
//                        showDirector(Text.Items.getLat(), Text.Items.getLon());
                        showMap2(Text.Items.getLat(), Text.Items.getLon());
                    }
//                      map.setPlace(Text.Items.getLat(), Text.Items.getLon());

                } else if (selectedIndex == 1) {
                    head = 1;
//                    showMap(Text.Items.getLat(), Text.Items.getLon());

                    showDirector(Text.Items.getLat(), Text.Items.getLon());

                } else {
//                    Controller.getInstance().showBack();
//                    Controller.getInstance().catrgoryBar2.setVisibility(false);
                }


            }
        }
    }

    private class MyTimerTask extends TimerTask {

        int val = 3;

        public void run() {
            if (val > 0) {
                splashCanvas = SplashCanvas.getInstance();
                javax.microedition.lcdui.Display.getDisplay(main).setCurrent(splashCanvas);
                map = new Map(javax.microedition.lcdui.Display.getDisplay(main));
                //map.drawRoute(0, 0, 0, 0);
                map.setPlace(Text.Items.getLat(), Text.Items.getLon());
                mapview = new MapView(map);
                val--;
                System.out.println("val:" + val);
                //val--;
            } else {

                mapview.show();
//                map = null;
//                mapview = null;
                timer.cancel();

            }
        }
    }

    private class MyTimerTask2 extends TimerTask {

        public void run() {

            locationFinder = LocationFinder.getFinder(new LocationFinder.Listener() {

                public void newLocation(double lat, double lon, int accuracy) {
                    curentLat = lat;//21.027561;//lat;
                    curentLon = lon;//21.027561,105.85596;//lon;
                    System.out.println("..cuẻntLAt: " + curentLat);
                    System.out.println("..cuẻntLon: " + curentLon);

                    System.out.println("chay  nnnn");
//                        map = new Map(javax.microedition.lcdui.Display.getDisplay(main));
//                        map.drawRoute(curentLat, curentLon, latz, lonz);
//                        mapview = new MapView(map);
//                        if (val==1) mapview.show();
//                        splashCanvas = SplashCanvas.getInstance();
//                        javax.microedition.lcdui.Display.getDisplay(main).setCurrent(splashCanvas);
                }
            }, new LocationFinder.NotFoundLocation() {

                public void actionPerforment() {
                    if (splashCanvas == javax.microedition.lcdui.Display.getDisplay(main).getCurrent()) {
//                        Alert a = new Alert(Text.khongTimThay);
//                        a.setType(AlertType.INFO);
//                        javax.microedition.lcdui.Display.getDisplay(main).setCurrent(a);
//                        Dialog.show(null, Text.khongTimThay, Text.Ok, null);
                        showMessenger(null, Text.khongTimThay);
                        map = new Map(javax.microedition.lcdui.Display.getDisplay(main));
                        map.drawRoute(curentLat, curentLon, latz, lonz);
                        mapview = new MapView(map);
                        if (splashCanvas == javax.microedition.lcdui.Display.getDisplay(main).getCurrent()) {
                            mapview.show();
                        }
                        timer.cancel();
                        showBack();
                    }
                }
            });

            splashCanvas = SplashCanvas.getInstance();
            javax.microedition.lcdui.Display.getDisplay(main).setCurrent(splashCanvas);
            refreshLocationFinder();

        }
    }
}
