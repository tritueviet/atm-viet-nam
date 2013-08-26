/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import javax.microedition.rms.RecordFilter;

/**
 *
 * @author wall
 */
public class ATMFilter implements RecordFilter {
    private int atm_id;
    
    public ATMFilter(int atm_id) {
        this.atm_id = atm_id;
    }

    public boolean matches(byte[] candidate) {
         int _atm_id = Integer.parseInt(new String(candidate));
         if(_atm_id == atm_id)
             return true;
         return false;
    }
    
}
