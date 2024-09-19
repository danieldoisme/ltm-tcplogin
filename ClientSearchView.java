/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tcplogin;

import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author danieldoisme
 */
public class ClientSearchView extends JFrame {
    private JTextField txtSearch;
    private JButton btnSearch;
    private JTextArea txtResults;

    public ClientSearchView() {
        super("User Search Client");
        
        txtSearch = new JTextField(20);
        btnSearch = new JButton("Search");
        
        txtResults = new JTextArea(10, 30);
        txtResults.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(txtResults);

        JPanel content = new JPanel();
        content.setLayout(new FlowLayout());
        content.add(new JLabel("Search Username:"));
        content.add(txtSearch);
        content.add(btnSearch);
        content.add(scrollPane);

        this.setContentPane(content);
        this.pack();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public String getSearchKeyword() {
        return txtSearch.getText();
    }

    public void setResults(String results) {
        txtResults.setText(results);
    }

    public void addSearchListener(ActionListener listener) {
        btnSearch.addActionListener(listener);
    }
}
