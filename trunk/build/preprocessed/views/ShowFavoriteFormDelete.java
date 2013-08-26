/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import Control.Controller;
import Control.HandleConfirm;
import com.nokia.lwuit.components.HeaderBar;
import com.nokia.lwuit.components.HeaderBarListener;
import models.Text;

import com.sun.lwuit.ButtonGroup;
import com.sun.lwuit.CheckBox;
import com.sun.lwuit.Command;
import com.sun.lwuit.Container;
import com.sun.lwuit.Display;
import com.sun.lwuit.Font;
import com.sun.lwuit.Form;
import com.sun.lwuit.Image;
import com.sun.lwuit.Label;
import com.sun.lwuit.List;
import com.sun.lwuit.TextField;
import com.sun.lwuit.events.ActionEvent;
import com.sun.lwuit.events.ActionListener;
import com.sun.lwuit.layouts.BorderLayout;
import com.sun.lwuit.layouts.BoxLayout;
import com.sun.lwuit.list.GenericListCellRenderer;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Vector;
import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.AlertType;
import models.ATM;
import util.StorageData;

/**
 *
 * @author PhongHoang
 */
public class ShowFavoriteFormDelete extends Form implements ActionListener {

    TextField khoangcach;
    ButtonGroup radioButtonGroup;
    Container dau, newlist;
    public Vector items;
    StorageData sd;
    Command xoa, back;
    Text text = new Text();
    List list;

    public ShowFavoriteFormDelete(String string, final Vector items) {
        Display.getInstance().setForceFullScreen(true);
        //setTitle(text.TimATM);
        HeaderBar header = null;
        try {
            header = new HeaderBar(string, Image.createImage("/images/tick.png"), null);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        header.setHeaderTitleColor(Text.colorWhite);
        header.getStyle().setBgColor(Text.hearder);
        header.setScrollable(false);
        header.setHeaderBarListener(new HeaderBarListener() {

            public void notifyHeaderBarListener(HeaderBar hb) {
                int k = 0;

                for (int i = 0; i < Text.mangXoa.length; i++) {

                    if (Text.mangXoa[i] == true) {
                        k++;
                    }
                }

                if (k == 0) {// ko co phan tu nao duoc chon
                    Controller.getInstance().showMessage(Text.listChong, AlertType.INFO);
                    return;
                }
                Controller.getInstance().showConfirm(new HandleConfirm() {

                    public void actionYes() {
                        int[] chao = new int[Text.mangXoa.length];
                        int k = 0;
                        ATM a;

                        for (int i = 0; i < Text.mangXoa.length; i++) {

                            if (Text.mangXoa[i] == true) {
                                a = (ATM) items.elementAt(i);
                                chao[k] = a.getId();
                                //StorageData.getInstance().removeFavoriteATM(i);
                                k++;
                            }
                        }
                        int[] mang = new int[k];
                        for (int i = 0; i < k; i++) {
                            mang[i] = chao[i];
                            System.out.println(">>>><<:" + mang[i]);
                        }


                        StorageData.getInstance().removeFavoriteATMs(mang);

                        Controller.getInstance().categoryBar.setVisibility(true);
                        Controller.getInstance().showFormFavoriteAtm();
                    }

                    public void actionNo() {
                        show();
                    }
                });
            }
        });

        addComponent(header);
        setScrollable(false);

        this.items = items;


        System.out.println("items.size(): " + items.size());
        setLayout(new BoxLayout(BoxLayout.Y_AXIS));

        Container deleteall = new Container(new BorderLayout());
        Container deleteall1 = new Container(new BoxLayout(CENTER));
        Container deleteall2 = new Container(new BoxLayout(CENTER));

        Label labelbank = new Label(Text.XoaTatCa);

        labelbank.getStyle().setMargin(TOP, CENTER, TOP, TOP);
        labelbank.getStyle().setBgTransparency(0);
        labelbank.getStyle().setFgColor(Text.colorWhite);
        
        deleteall1.addComponent(labelbank);

        CheckBox selectall = new CheckBox();
        selectall.setSelected(Text.chonTat);
        selectall.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent ae) {
                if (Text.chonTat == false) {
                    Text.chonTat = true;
                } else {
                    Text.chonTat = false;
                }

                if (Text.chonTat == true) {
                    for (int i = 0; i < Text.mangXoa.length; i++) {
                        Text.mangXoa[i] = true;
                    }
                } else {
                    for (int i = 0; i < Text.mangXoa.length; i++) {
                        Text.mangXoa[i] = false;
                    }
                }

                //  xu lys su kien bat tat ca tai day 
                Controller.getInstance().showDeleteFavariteForm(items);

            }
        });
        //selectall.setName("SelectedAll");
        deleteall2.addComponent(selectall);
        selectall.setPreferredW(35);
        deleteall.addComponent(BorderLayout.CENTER, deleteall1);
        deleteall.addComponent(BorderLayout.EAST, deleteall2);

        deleteall.getStyle().setMargin(5, 5, 15, 15);

        addComponent(deleteall);

        newlist = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        //newlist.getStyle().setMargin(5, 5, 5, 0);

        list = new List(createGenericListCellRendererModelData());
        list.setRenderer(new GenericListCellRenderer(createGenericRendererContainer(),
                createGenericRendererContainer()));
        list.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent ae) {
                list.setSelectedIndex(list.getSelectedIndex());

                if (Text.mangXoa[list.getSelectedIndex()] == true) {
                    Text.mangXoa[list.getSelectedIndex()] = false;
                } else {
                    Text.mangXoa[list.getSelectedIndex()] = true;
                }
            }
        });
        newlist.addComponent(list);
        addComponent(newlist);

//        try {
        xoa = new Command(Text.xoa);//Command(Text.xoa, Image.createImage("/images/delete.png"));
//        } catch (IOException ex) {
//            ex.printStackTrace();
//        }
        //  setDefaultCommand(xoa);
        // addCommand(xoa);
        back = new Command(" ");
        setBackCommand(back);
        addCommandListener(this);

        // show();
    }

    private Container createGenericRendererContainer() {
        Font font = Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_SMALL);
        Container f = new Container(new BorderLayout());

        Container c = new Container(new BoxLayout(BoxLayout.X_AXIS));
        //f.setUIID("ListRenderer");
        f.getStyle().setBgTransparency(0);
        c.getStyle().setBgTransparency(0);


        CheckBox selected = new CheckBox();
        selected.setName("Selected");
        selected.setPreferredW(35);

        Container d = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        Container e = new Container(new BoxLayout(CENTER));
        d.getStyle().setBgTransparency(0);
        e.getStyle().setBgTransparency(0);

        e.addComponent(selected);

        Label bank = new Label();
        bank.setName("bank");
        bank.setFocusable(true);
        bank.getStyle().setBgTransparency(0);
        bank.getStyle().setFgColor(Text.colorWhite);
        d.addComponent(bank);


        Label name = new Label();
        //name.getStyle().setFont(font);

        name.setName("Name");
        name.setFocusable(true);
        name.getStyle().setBgTransparency(0);
        name.getStyle().setFgColor(Text.colorWhite);
        d.addComponent(name);


        Label surname = new Label();
        surname.getStyle().setFont(font);
        surname.setFocusable(true);
        surname.getStyle().setBgTransparency(0);
        surname.getStyle().setFgColor(Text.colorWhite);
        surname.getStyle().setFont(font);
        surname.getSelectedStyle().setFont(font);
        surname.getUnselectedStyle().setFont(font);
        surname.getPressedStyle().setFont(font);
        surname.setName("Surname");
        d.addComponent(surname);


        // c.addComponent(imageLabel);
        c.addComponent(d);
        f.addComponent(BorderLayout.CENTER, c);
        f.addComponent(BorderLayout.EAST, e);



        return f;
    }

    private Hashtable[] createGenericListCellRendererModelData() {
        Hashtable[] data = new Hashtable[items.size()];
        ATM a;
        String[] s;
        for (int i = 0; i < items.size(); i++) {
            a = (ATM) items.elementAt(i);
            data[i] = new Hashtable();
            s = util.Util.getAtmNameBank(a.getName());
            data[i].put("bank", s[0]);
            data[i].put("Name", s[1]);
            data[i].put("Surname", a.getAddr());

            data[i].put("Selected", text.mangXoa[i] + "");
        }


        return data;
    }

    public void actionPerformed(ActionEvent ae) {
        int k = 0;

        for (int i = 0; i < Text.mangXoa.length; i++) {

            if (Text.mangXoa[i] == true) {
                k++;
            }
        }


        if (ae.getCommand() == xoa) {
            if (k == 0) {// ko co phan tu nao duoc chon
                Controller.getInstance().showMessage(Text.listChong, AlertType.INFO);
                return;
            }
            Controller.getInstance().showConfirm(new HandleConfirm() {

                public void actionYes() {
                    int[] chao = new int[Text.mangXoa.length];
                    int k = 0;
                    ATM a;

                    for (int i = 0; i < Text.mangXoa.length; i++) {

                        if (Text.mangXoa[i] == true) {
                            a = (ATM) items.elementAt(i);
                            chao[k] = a.getId();
                            //StorageData.getInstance().removeFavoriteATM(i);
                            k++;
                        }
                    }
                    int[] mang = new int[k];
                    for (int i = 0; i < k; i++) {
                        mang[i] = chao[i];
                        System.out.println(">>>><<:" + mang[i]);
                    }


                    StorageData.getInstance().removeFavoriteATMs(mang);

                    Controller.getInstance().categoryBar.setVisibility(true);
                    Controller.getInstance().showFormFavoriteAtm();
                }

                public void actionNo() {
                    show();
                }
            });
            // xu ly xoa  goi ve form truoc

        } else if (ae.getCommand() == back) {
            Controller.getInstance().showBack();
        }

    }
}
