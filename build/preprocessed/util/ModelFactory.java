/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import models.ATM;
import models.Bank;
import models.District;
import models.Province;

/**
 *
 * @author cdit
 */
public class ModelFactory {
    
    public Bank createBank(int id, String name, String fullname, String tel) {
        return new Bank(id, name, fullname, tel);
    }
     
    public Province createProvince(int id, String name) {
        return new Province(id, name);
    }
    
    public District createDistrict(int id, String name, int provinceId) {
        return new District(id, name, provinceId);
    }
    
    public ATM createAtm(int id, int bankId, int districtId, double lon, double lat, String name, String addr) {
        return new ATM(id, bankId, districtId, lon, lat, name, addr);
    }
    
}
