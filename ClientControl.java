/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tcplogin;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 *
 * @author danieldoisme
 */
public class ClientControl {
    private ClientView view;
    private String serverHost = "localhost";
    private int serverPort = 8888;
    
    public ClientControl(ClientView view) {
        this.view = view;
        this.view.addLoginListener(new LoginListener());
    }
    
    class LoginListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                User user = view.getUser();
                Socket mySocket = new Socket(serverHost, serverPort);
                ObjectOutputStream oos = new ObjectOutputStream(mySocket.getOutputStream());
                oos.writeObject(user);
                ObjectInputStream ois = new ObjectInputStream(mySocket.getInputStream());
                Object o = ois.readObject();
                if (o instanceof String) {
                    String result = (String) o;
                    if (result.equals("ok")) {
                        view.showMessage("Login succesfully!");
                        view.setVisible(false);
                        openSearchView();
                    } else {
                        view.showMessage("Invalid username and/or password!");
                    }
                }
                mySocket.close();
            } catch (Exception ex) {
                view.showMessage(ex.getStackTrace().toString());
            }
        }
        
        private void openSearchView() {
            ClientSearchView searchView = new ClientSearchView();
            new ClientSearchControl(searchView);
            searchView.setVisible(true);
        }
    }
}
