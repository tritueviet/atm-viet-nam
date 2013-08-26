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
public class ATMsFilter implements RecordFilter {

    int[] atms;
    
    public ATMsFilter(int[] atms) {
        this.atms = atms;
    }
    
    public boolean matches(byte[] candidate) {
        int n = atms.length;
        int id;
        for(int i = 0; i < n; ++i) {
            id = Integer.parseInt(new String(candidate));
            if(id == atms[i])
                return true;
        }
        return false;
    }
    
}
