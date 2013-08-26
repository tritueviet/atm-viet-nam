/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.util.Vector;
import models.IModel;

/**
 *
 * @author cdit
 */
public class Sort {

    private Vector v;
    private int cap;

    public Sort(int cap) {
        this.cap = cap;
        v = new Vector(cap, 4);
    }
    
    public void addElement(IModel o) {
        if (v.isEmpty()) {
            v.addElement(o);
            return;
        }
        int n = v.size();
        IModel o1;
//        if (n == cap) {
//            o1 = (IModel) v.elementAt(n - 1);
//            if (o1.getPriority() <= o.getPriority()) {
//                return;
//            }
//            v.removeElement(o1);
//            n--;
//        }

        boolean isLower = false;
        for (int i = n - 1; i >= 0; --i) {
            o1 = (IModel) v.elementAt(i);
            if (o1.getPriority() <= o.getPriority()) {
                v.insertElementAt(o, i + 1);
                isLower = true;
                break;
            }
        }
        if (!isLower) {
            v.insertElementAt(o, 0);
        }
    }
    
    public void addLimitElement(IModel o) {
        if (v.isEmpty()) {
            v.addElement(o);
            return;
        }
        int n = v.size();
        IModel o1;
        if (n == cap) {
            o1 = (IModel) v.elementAt(n - 1);
            if (o1.getPriority() <= o.getPriority()) {
                return;
            }
            v.removeElement(o1);
            n--;
        }

        boolean isLower = false;
        for (int i = n - 1; i >= 0; --i) {
            o1 = (IModel) v.elementAt(i);
            if (o1.getPriority() <= o.getPriority()) {
                v.insertElementAt(o, i + 1);
                isLower = true;
                break;
            }
        }
        if (!isLower) {
            v.insertElementAt(o, 0);
        }
    }
    
    public Vector getVector() {
        return v;
    }
}
