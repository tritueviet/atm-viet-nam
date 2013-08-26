/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

/**
 *
 * @author cdit
 */
public class Bank implements IViewModel {
    private int id;
    private String name;
    private String fullname;
    private String tel;

    public Bank() {
    }

    public Bank(int id, String name, String fullname, String tel) {
        this.id = id;
        this.name = name;
        this.fullname = fullname;
        this.tel = tel;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }
    
    
}
