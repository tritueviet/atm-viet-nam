/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package models;

/**
 *
 * @author cdit
 */
public class ATM implements IModel {
    
    private int id;
    private int bankId;
    private int districtId;
    private double lon;
    private double lat;
    private String name;
    private String addr;
    
    private float priority;

    public ATM() {
    }

    public ATM(int id, int bankId, int districtId, double lon, double lat, String name, String addr) {
        this.id = id;
        this.bankId = bankId;
        this.districtId = districtId;
        this.lon = lon;
        this.lat = lat;
        this.name = name;
        this.addr = addr;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBankId() {
        return bankId;
    }

    public void setBankId(int bankId) {
        this.bankId = bankId;
    }

    public int getDistrictId() {
        return districtId;
    }

    public void setDistrictId(int districtId) {
        this.districtId = districtId;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public float getPriority() {
        return priority;
    }

    public void setPriority(float priority) {
        this.priority = priority;
    }
    
    public String toString() {
        //return "{ ID " + id + " name " + name + " long " + lon + " lat " + lat + " addr " + addr + " } ";
        return lat + " | " + lon;
    }
    
}
