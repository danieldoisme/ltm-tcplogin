/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tcplogin;

import java.io.Serializable;

/**
 *
 * @author danieldoisme
 */
public class User implements Serializable {
    private String username;
    private String password;

    public User() {
    }

    public User(String username, String password) { 
        this.username = username;
        this.password = password;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    public String getUserName() {
        return username;
    }
    
    public void setUserName(String userName) {
        this.username = userName;
    }
}
