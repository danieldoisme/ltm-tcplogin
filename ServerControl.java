/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tcplogin;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.List;
import java.util.ArrayList;

/**
 *
 * @author danieldoisme
 */
public class ServerControl {
    private ServerView view;
    private Connection con;
    private ServerSocket myServer;
    private Socket clientSocket;
    private int serverPort = 8888;
    
    public ServerControl (ServerView view) {
        this.view = view;
        getDBConnection("your_database_name", "your_username", "your_password");
        openServer(serverPort);
        view.showMessage("TCP server is running...");
        
        while (true) {            
            listenning();
        }
    }
    
    private void getDBConnection(String dbName, String username, String password) {
        String dbURL = "jdbc:mysql://localhost:3306/" + dbName;
        String dbClass = "com.mysql.jdbc.Driver";
        
        try {
            Class.forName(dbClass);
            con = DriverManager.getConnection(dbURL, username, password);
        } catch (Exception e) {
            view.showMessage(e.getStackTrace().toString());
        }
    }
    
    private void openServer(int portNumber) {
        try {
            myServer = new ServerSocket(portNumber);
        } catch (IOException e) {
            view.showMessage(e.toString());
        }
    }
    
    private void listenning() {
        try {
            clientSocket = myServer.accept();
            ObjectInputStream ois = new ObjectInputStream(clientSocket.getInputStream());
            ObjectOutputStream oos = new ObjectOutputStream(clientSocket.getOutputStream());
            
            Object o = ois.readObject();
            if (o instanceof User){
                User user = (User) o;
                if(checkUser(user)){
                    oos.writeObject("ok");
                } else { 
                    oos.writeObject("false");
                }
            } else if (o instanceof String) {
                String keyword = (String) o;
                List<String> users = searchUsers(keyword);
                oos.writeObject(users);
            }
            clientSocket.close();
        } catch (Exception e) {
            view.showMessage(e.toString());
        }
    }
    
    private boolean checkUser(User user) throws Exception {
        String query = "SELECT * FROM tbluser WHERE username ='"
            + user.getUserName()
            + "' AND password ='" + user.getPassword() + "'";
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            return rs.next();
        } catch (Exception e) {
            throw e;
        }
    }
    
    private List<String> searchUsers(String keyword) throws Exception {
        List<String> users = new ArrayList<>();
        String query = "SELECT * FROM tbluser WHERE username LIKE '%" + keyword + "%'";
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                String userInfo = "ID: " + rs.getInt("id") + ", Username: " + rs.getString("username") + 
                                  ", Address: " + rs.getString("address") + 
                                  ", Birthday: " + rs.getDate("birthday") + 
                                  ", Sex: " + rs.getString("sex") + 
                                  ", Description: " + rs.getString("description");
                users.add(userInfo);
            }
        } catch (Exception e) {
            view.showMessage(e.toString());
        }
        return users;
    }
}
