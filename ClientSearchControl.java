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
import java.util.List;

/**
 *
 * @author danieldoisme
 */
public class ClientSearchControl {
    private ClientSearchView view;
    private String serverHost = "localhost";
    private int serverPort = 8888;

    public ClientSearchControl(ClientSearchView view) {
        this.view = view;
        this.view.addSearchListener(new SearchListener());
    }

    class SearchListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                String keyword = view.getSearchKeyword();
                Socket socket = new Socket(serverHost, serverPort);
                
                ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                out.writeObject(keyword);
                
                ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
                Object response = in.readObject();
                
                if (response instanceof List<?>) {
                    List<?> results = (List<?>) response;
                    StringBuilder resultText = new StringBuilder();
                    for (Object result : results) {
                        resultText.append(result.toString()).append("\n");
                    }
                    view.setResults(resultText.toString());
                }

                socket.close();
            } catch (Exception ex) {
                view.setResults("Error: " + ex.getMessage());
                ex.printStackTrace();
            }
        }
    }
}
