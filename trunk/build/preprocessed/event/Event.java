/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package event;

import java.util.Hashtable;

/**
 *
 * @author Trung Hoàng
 */
public class Event {
    
    // Define Key Event
    public static final int FIND_ATM_VIEW_SEARCH_ADV = 0x001;
    public static final int FIND_ATM_VIEW_ABOUT = 0x002;
    public static final int ABOUT_BACK = 0x003;
    public static final int SECLECT_LANGUAGES = 0x004;
    
    public static final int FIND_ATM_VIEW_SEARCH_BANK = 0x005;
    public static final int FIND_ATM_VIEW_SEARCH_PROVINCE = 0x006;
    public static final int FIND_ATM_VIEW_SEARCH_DISTRICT = 0x007;
    public static final int FIND_ATM_LEAGUE = 0x09;
    
    public static final int VIEW_MAP = 0xB1;
    public static final int VIEW_MAP_CONDUCTOR = 0xB2; 
    
    public static final int SELECT_BANK = 0xA0;
    public static final int SELECT_PROVINCE = 0xA1;
    public static final int SELECT_DISTRICT = 0xA2;
    
    public static final int SET_GAN_BAN=0x008;
    
    public static final int NOT_DISTANCE = 0xB0;
    public static final int ADD_FAVARITES = 0xC0;
    public static final int DELETE_FAVORITES = 0xD0;
    public static final int VIEW_DELETE_FAVORITE= 0xE0;
    public static final int VIEW_NGAN_HANGLIENKET= 0xF0;
    public static final int VIEW_AUTO_SEARCH = 0xA7;
    
    public static final int FORM_FIND_ATM = 0;
    public static final int FORM_RESULT_ATM = 1;
    public static final int FORM_NEAR = 2;
    public static final int FORM_FAVORITE = 3;
    public static final int FORM_LEAGUE_BANK = 4;
    public static final int END = 5;
    
    
    public Hashtable data;
    
    public Event() {
        data = new Hashtable();
    }
    
    public void setData(String key, Object value) {
        data.put(key, value);
    }
    
    public Object getData(String key) {
        if(data.containsKey(key)) {
            return data.get(key);
        }
        return null;
    }
    
}
