/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Vector;
import javax.microedition.rms.RecordEnumeration;
import javax.microedition.rms.RecordStore;
import javax.microedition.rms.RecordStoreException;
import models.ATM;
import models.District;
import models.Text;

/**
 *
 * @author HAIPV
 */
public class StorageData {
    
    private static StorageData instance = null;
    
    private final byte NUM_BANK = 26;
    private final byte NUM_PROVINCE = 63;
    private final int NUM_DISTRICT = 697;
    private final int NUM_ATM = 5897;
    private final int NUM_LEAGUE = 159;
    private final int LIMIT = 10;
    private final double MAX_DISTANCE = 100.0;
    private ModelFactory fac;
    private final String DBNAME = "ATMVN";
    private final String DBFAVORITE = "ATMVN_Favorites";
    private final int ID_LANGUAGE = 1;
    private final int ID_BANK_ADV = 2;
    private final int ID_PROV_ADV = 3;
    private final int ID_DISTANCE_AUTO = 4;
    private final int ID_BANKS_AUTO = 5;
    
    private StorageData() {
        fac = new ModelFactory();
    }
    
    public static StorageData getInstance() {
        if(instance == null)
            instance = new StorageData();
        return instance;
    }

    /**
     * Get all banks from db.
     * @return List banks
     */
    public Vector getBanks() {
        Vector banks = new Vector(NUM_BANK);
        
        InputStream in = this.getClass().getResourceAsStream("/data/bank.in");
        
        DataInputStream dis = new DataInputStream(in);
        int i;
        int id;
        String name;
        String fullname;
        String tel;
        try {
            for (i = 0; i < NUM_BANK; ++i) {
                id = dis.readInt();
                name = dis.readUTF();
                fullname = dis.readUTF();
                tel = dis.readUTF();
                banks.addElement(fac.createBank(id, name, fullname, tel));
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return banks;
    }

    /**
     * Get name of specific bank
     * @param bankid
     * @return Name of bank.
     */
    public String getBankName(int bankid) {
        InputStream in = this.getClass().getResourceAsStream("/data/bank.in");
        
        DataInputStream dis = new DataInputStream(in);
        int i;
        int id;
        String name = "";
        String fullname;
        String tel;
        try {
            for (i = 0; i < NUM_BANK; ++i) {
                id = dis.readInt();
                name = dis.readUTF();
                fullname = dis.readUTF();
                tel = dis.readUTF();
                if (id == bankid) {
                    break;
                }
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return name;
    }
    
    /**
     * Gets banks league of specific bank.
     * @param bank_id
     * @return 
     */
    public Vector getBanksLeague(int bank_id) {
        Vector banks = new Vector(3);
        Vector ids = getIdBanksLeague(bank_id);
        InputStream in = this.getClass().getResourceAsStream("/data/bank.in");
        
        DataInputStream dis = new DataInputStream(in);
        int i, j;
        int id;
        String name;
        String fullname;
        String tel;
        int n = ids.size();
        try {
            for (i = 0; i < NUM_BANK; ++i) {
                id = dis.readInt();
                name = dis.readUTF();
                fullname = dis.readUTF();
                tel = dis.readUTF();
                if(ids.contains(id+"")) {
                    banks.addElement(fac.createBank(id, name, fullname, tel));
                }
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return banks;
    }
    
    /**
     * Gets all banks leage with a specific bank.
     * @param bank_id
     * @return 
     */
    private Vector getIdBanksLeague(int bank_id) {
        Vector ids = new Vector(3, 1);
        InputStream in = this.getClass().getResourceAsStream("/data/league.in");
        
        DataInputStream dis = new DataInputStream(in);
        int i;
        int id, bankId, co_bank_id;
        try {
            for (i = 0; i < NUM_LEAGUE; ++i) {
                id = dis.readInt();
                bankId = dis.readInt();
                co_bank_id = dis.readInt();
                if (bankId == bank_id) {
                    ids.addElement(co_bank_id+"");
                }
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return ids;
    }

    /**
     * Gets all provinces from db
     * @return List of provinces
     */
    public Vector getProvinces() {
        Vector provinces = new Vector(NUM_PROVINCE);
        
        InputStream in = this.getClass().getResourceAsStream("/data/province.in");
        
        DataInputStream dis = new DataInputStream(in);
        int i;
        int id;
        String name;
        try {
            for (i = 0; i < NUM_PROVINCE; ++i) {
                id = dis.readInt();
                name = dis.readUTF();
                provinces.addElement(fac.createProvince(id, name));
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return provinces;
    }

    /**
     * Get name of specific province.
     * @param proId
     * @return Name of province
     */
    public String getProvinceName(int proId) {
        
        InputStream in = this.getClass().getResourceAsStream("/data/province.in");
        
        DataInputStream dis = new DataInputStream(in);
        int i;
        int id;
        String name = "";
        try {
            for (i = 0; i < NUM_PROVINCE; ++i) {
                id = dis.readInt();
                name = dis.readUTF();
                if (id == proId) {
                    break;
                }
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return name;
    }

    /**
     * Gets all districts of sepecific province
     * @param ma
     * @return Districts
     */
    public Vector getDistricts(int ma) {
        Vector provinces = new Vector(NUM_DISTRICT);
        
        InputStream in = this.getClass().getResourceAsStream("/data/district.in");
        
        DataInputStream dis = new DataInputStream(in);
        int i;
        int id;
        String name;
        int provinceId;
        try {
            for (i = 0; i < NUM_DISTRICT; ++i) {
                id = dis.readInt();
                
                name = dis.readUTF();
                provinceId = dis.readInt();
                
                if (ma == provinceId) {
                    provinces.addElement(fac.createDistrict(id, name, provinceId));
                }
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return provinces;
    }

    /**
     * Get a district of specific province.
     * @param province
     * @return A Distritc
     */
    public District getDistrict(int province) {
        InputStream in = this.getClass().getResourceAsStream("/data/district.in");
        
        DataInputStream dis = new DataInputStream(in);
        int i;
        int id;
        String name;
        int provinceId;
        try {
            for (i = 0; i < NUM_DISTRICT; ++i) {
                id = dis.readInt();
                
                name = dis.readUTF();
                provinceId = dis.readInt();
                
                if (province == provinceId) {
                    return (fac.createDistrict(id, name, provinceId));
                }
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return new District();
    }

    /**
     * Get nearest ATMs
     * @param lon1
     * @param lat1
     * @return 
     */
    public Vector getNearestAtms(double lon1, double lat1) {
        Sort atms = new Sort(10);
        
        InputStream in = this.getClass().getResourceAsStream("/data/atm.in");
        
        DataInputStream dis = new DataInputStream(in);
        int i;
        int id;
        int bankId;
        int districtId;
        double lon;
        double lat;
        String name;
        String addr;
        try {
            for (i = 0; i < NUM_ATM; ++i) {
                id = dis.readInt();
                bankId = dis.readInt();
                districtId = dis.readInt();
                lon = dis.readDouble();
                lat = dis.readDouble();
                name = dis.readUTF();
                addr = dis.readUTF();
                float tmp = Util.getDistance(lon1, lat1, lon, lat);
                if (tmp < MAX_DISTANCE) {
                    ATM atm = fac.createAtm(id, bankId, districtId, lon, lat, name, addr);
                    atm.setPriority(tmp);
                    atms.addLimitElement(atm);
                }
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return atms.getVector();
    }
    
    public Vector getAtmsByBankDistrict(int bank, int district) {
        Vector atms = new Vector(NUM_ATM);
        
        InputStream in = this.getClass().getResourceAsStream("/data/atm.in");
        
        DataInputStream dis = new DataInputStream(in);
        int i;
        int id;
        int bankId;
        int districtId;
        double lon;
        double lat;
        String name;
        String addr;
        try {
            for (i = 0; i < NUM_ATM; ++i) {
                id = dis.readInt();
                bankId = dis.readInt();
                districtId = dis.readInt();
                lon = dis.readDouble();
                lat = dis.readDouble();
                name = dis.readUTF();
                addr = dis.readUTF();
                if (bankId == bank && districtId == district) {
                    atms.addElement(fac.createAtm(id, bankId, districtId, lon, lat, name, addr));
                }
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return atms;
    }

    /**
     * Get ATMs only distance.
     * @param distance
     * @param lon1
     * @param lat1
     * @return 
     */
    public Vector getAtmsWithDistance(double distance, double lon1, double lat1) {
        if(distance > MAX_DISTANCE)
            distance = MAX_DISTANCE;
        Sort atms = new Sort(20);
        
        InputStream in = this.getClass().getResourceAsStream("/data/atm.in");
        
        DataInputStream dis = new DataInputStream(in);
        int i;
        int id;
        int bankId;
        int districtId;
        double lon;
        double lat;
        String name;
        String addr;
        try {
            for (i = 0; i < NUM_ATM; ++i) {
                id = dis.readInt();
                bankId = dis.readInt();
                districtId = dis.readInt();
                lon = dis.readDouble();
                lat = dis.readDouble();
                name = dis.readUTF();
                addr = dis.readUTF();
                float tmp = Util.getDistance(lon1, lat1, lon, lat);
                if (tmp < distance) {
                    ATM atm = fac.createAtm(id, bankId, districtId, lon, lat, name, addr);
                    atm.setPriority(tmp);
                    atms.addElement(atm);
                }
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return atms.getVector();
    }

    /**
     * Get ATMs with distance and banks
     * @param distance
     * @param banks
     * @param lon1
     * @param lat1
     * @return 
     */
    public Vector getAtmsWithDistanceAndBank(double distance, int[] banks, double lon1, double lat1) {
        if(distance > MAX_DISTANCE)
            distance = MAX_DISTANCE;
        Sort atms = new Sort(20);
        
        InputStream in = this.getClass().getResourceAsStream("/data/atm.in");
        
        DataInputStream dis = new DataInputStream(in);
        int i, j;
        int id;
        int bankId;
        int districtId;
        double lon;
        double lat;
        String name;
        String addr;
        int bankSize = banks.length;
        try {
            for (i = 0; i < NUM_ATM; ++i) {
                id = dis.readInt();
                bankId = dis.readInt();
                districtId = dis.readInt();
                lon = dis.readDouble();
                lat = dis.readDouble();
                name = dis.readUTF();
                addr = dis.readUTF();
                
                for (j = 0; j < bankSize; ++j) {
                    if (bankId == banks[j]) {
                        
                        float tmp = Util.getDistance(lon1, lat1, lon, lat);
                        if (tmp < distance ) {
                            ATM atm = fac.createAtm(id, bankId, districtId, lon, lat, name, addr);
                            atm.setPriority(tmp);
                            atms.addElement(atm);
                        }
                        break;
                    }
                }
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return atms.getVector();
    }

    /**
     * Get ATMs with banks id
     * @param banks
     * @param lon1
     * @param lat1
     * @return 
     */
    public Vector getAtmsWithBank(int[] banks, double lon1, double lat1) {
        Sort atms = new Sort(20);
        
        InputStream in = this.getClass().getResourceAsStream("/data/atm.in");
        
        DataInputStream dis = new DataInputStream(in);
        int i, j;
        int id;
        int bankId;
        int districtId;
        double lon;
        double lat;
        String name;
        String addr;
        int bankSize = banks.length;
        try {
            for (i = 0; i < NUM_ATM; ++i) {
                id = dis.readInt();
                bankId = dis.readInt();
                districtId = dis.readInt();
                lon = dis.readDouble();
                lat = dis.readDouble();
                name = dis.readUTF();
                addr = dis.readUTF();
                
                for (j = 0; j < bankSize; ++j) {
                    if (bankId == banks[j]) {
                        
                        float tmp = Util.getDistance(lon1, lat1, lon, lat);
                        if (tmp < 100) {
                            ATM atm = fac.createAtm(id, bankId, districtId, lon, lat, name, addr);
                            atm.setPriority(tmp);
                            atms.addElement(atm);
                        }
                        break;
                    }
                }
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return atms.getVector();
    }
    
    /**
     * Gets favorite ATMs.
     * @return 
     */
    public Vector getFavoriteATMs() {
        int[] ids = getIdFavoriteATMs();
        Vector atms = new Vector();
        if(ids.length == 0)
            return atms;
        InputStream in = this.getClass().getResourceAsStream("/data/atm.in");
        
        DataInputStream dis = new DataInputStream(in);
        int i, j;
        int id;
        int bankId;
        int districtId;
        double lon;
        double lat;
        String name;
        String addr;
        int idSize = ids.length;
        try {
            for (i = 0; i < NUM_ATM; ++i) {
                id = dis.readInt();
                bankId = dis.readInt();
                districtId = dis.readInt();
                lon = dis.readDouble();
                lat = dis.readDouble();
                name = dis.readUTF();
                addr = dis.readUTF();
                
                for (j = 0; j < idSize; ++j) {
                    if (id == ids[j]) {
                        ATM atm = fac.createAtm(id, bankId, districtId, lon, lat, name, addr);
                        atms.addElement(atm);
                        break;
                    }
                }
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return atms;        
    }
    
    public void updateConfig() {
        
        try {
            RecordStore rs = RecordStore.openRecordStore(DBNAME, true);

            // Update language
            byte[] data;
            data = Integer.toString(Text.languages).getBytes();
            rs.setRecord(ID_LANGUAGE, data, 0, data.length);

            // update serach advance
            data = Integer.toString(Text.bank_adv_id).getBytes();
            rs.setRecord(ID_BANK_ADV, data, 0, data.length);
            data = Integer.toString(Text.prov_adv).getBytes();
            rs.setRecord(ID_PROV_ADV, data, 0, data.length);

            // Update distance when search automatic
            String txt = Text.setKhoangcach.trim();
            if (txt.length() == 0) {
                txt = " ";
            }
            data = txt.getBytes();
            rs.setRecord(ID_DISTANCE_AUTO, data, 0, data.length);
            // Update selected bank when search automatic
            String selected = "";
            int n = Text.setgan.length;
            for (int i = 0; i < n; ++i) {
                selected += (Text.setgan[i]) ? '1' : '0';
            }
            data = selected.getBytes();
            rs.setRecord(ID_BANKS_AUTO, data, 0, data.length);
            
            rs.closeRecordStore();
            
        } catch (RecordStoreException ex) {
            ex.printStackTrace();
        }
    }
    
    public void loadConfig() {
        try {
            RecordStore rs = RecordStore.openRecordStore(DBNAME, true);
            if (rs.getNumRecords() == 0) {
                createRecord();
                return;
            }
            byte[] data;
            //Read language
            data = rs.getRecord(ID_LANGUAGE);
            Text.languages = Integer.parseInt(new String(data));
            Text text = new Text();
            text.Languge(Text.languages);
            // Read search advance
            data = rs.getRecord(ID_BANK_ADV);
            // dis = new DataInputStream(bis);
            Text.bank_adv_id = Integer.parseInt(new String(data));
            Text.bank_adv_name = getBankName(Text.bank_adv_id);
            
            data = rs.getRecord(ID_PROV_ADV);
            Text.prov_adv = Integer.parseInt(new String(data));
            // Read distance when searach automatic
            data = rs.getRecord(ID_DISTANCE_AUTO);
            Text.setKhoangcach = new String(data).trim();

            // Read banks id when search automatic
            data = rs.getRecord(ID_BANKS_AUTO);
            String txt = new String(data);
            int n = Text.setgan.length;
            for (int i = 0; i < n; ++i) {
                if (txt.charAt(i) == '0') {
                    Text.setgan[i] = false;
                } else {
                    Text.setgan[i] = true;
                }
            }
            
            rs.closeRecordStore();
        } catch (RecordStoreException ex) {
            ex.printStackTrace();
        }        
    }
    
    private void createRecord() {
        try {
            
            RecordStore rs = RecordStore.openRecordStore(DBNAME, true);

            // language
            byte[] data;
            data = Integer.toString(Text.languages).getBytes();
            rs.addRecord(data, 0, data.length);
            // update serach advance
            data = Integer.toString(Text.bank_adv_id).getBytes();
            rs.addRecord(data, 0, data.length);
            data = Integer.toString(Text.prov_adv).getBytes();
            rs.addRecord(data, 0, data.length);

            // Update distance when search automatic
            String txt = Text.setKhoangcach;
            if (txt.length() == 0) {
                txt = " ";
            }
            data = txt.getBytes();
            rs.addRecord(data, 0, data.length);
            // Update selected bank when search automatic
            String selected = "";
            int n = Text.setgan.length;
            for (int i = 0; i < n; ++i) {
                selected += (Text.setgan[i]) ? '1' : '0';
            }
            data = selected.getBytes();
            rs.addRecord(data, 0, data.length);
            rs.closeRecordStore();
        } catch (RecordStoreException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Add id of atm into favorite RMS
     * @param id ID of atm.
     */
    public void addFavoriteATM(int id) {
        try {
            RecordStore rs = RecordStore.openRecordStore(DBFAVORITE, true);
            byte[] data = Integer.toString(id).getBytes();
            rs.addRecord(data, 0, data.length);
            rs.closeRecordStore();
        } catch (RecordStoreException e) {
            e.printStackTrace();
        }
    }

    /**
     * Remove id of atm from favorite RMS
     * @param id 
     */
    public void removeFavoriteATM(int atm_id) {
        try {
            RecordStore rs = RecordStore.openRecordStore(DBFAVORITE, true);
            int id;
            RecordEnumeration re = rs.enumerateRecords(new ATMFilter(atm_id), null, false);
            if(re.hasNextElement()) {
                id = re.nextRecordId();
                rs.deleteRecord(id);
            }
            rs.closeRecordStore();
        } catch (RecordStoreException e) {
            e.printStackTrace();
        }
    }

    public void removeFavoriteATMs(int[] atms) {
        try {
            RecordStore rs = RecordStore.openRecordStore(DBFAVORITE, true);
            int id;
            RecordEnumeration re = rs.enumerateRecords(new ATMsFilter(atms), null, false);
            while(re.hasNextElement()) {
                id = re.nextRecordId();
                rs.deleteRecord(id);
            }
            rs.closeRecordStore();
        } catch (RecordStoreException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Gets all id of atms from favorite rms.
     * @return Vector of id atms.
     */
    private int[] getIdFavoriteATMs() {
        int[] atms = new int[0];
        try {
            RecordStore rs = RecordStore.openRecordStore(DBFAVORITE, true);
            int n = rs.getNumRecords(), atm_id, i = 0;
            if(n == 0)
                return atms;
            atms = new int[n];
            byte[] data;
            RecordEnumeration re = rs.enumerateRecords(null, null, false);
            while(re.hasNextElement()) {
                data = re.nextRecord();
                atm_id = Integer.parseInt(new String(data));
                atms[i++] = atm_id;
            }
            
            rs.closeRecordStore();
        } catch (RecordStoreException e) {
            e.printStackTrace();
        }
        return atms;
    }
    
    /**
     * Check favorite.
     * @param atm_id
     * @return 
     */
    public boolean hasFavorite(int atm_id) {
        boolean exist = false;
        try {
            RecordStore rs = RecordStore.openRecordStore(DBFAVORITE, true);
            int n = rs.getNumRecords();
            if(n == 0)
                return false;
            RecordEnumeration re = rs.enumerateRecords(new ATMFilter(atm_id), null, false);
            exist = re.hasNextElement();
            
            rs.closeRecordStore();
        } catch (RecordStoreException e) {
            e.printStackTrace();
        }
        return exist;
    }
}
