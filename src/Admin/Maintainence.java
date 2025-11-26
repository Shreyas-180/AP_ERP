package Admin;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.*;

import Common.DatabaseConnection;
import Main.MainFrame;

public class Maintainence extends JPanel {

    private JTextField jkc19;
    private MainFrame mainFrame;

    public Maintainence(MainFrame mainFrame) {
        this.mainFrame = mainFrame;

        setLayout(new GridLayout(3, 1, 10, 10));

        JLabel label = new JLabel("Enter Yes for turning on maintenance and No for turning off");
        jkc19 = new JTextField();
        JButton noida = new JButton("Save Preference");
        JButton back = new JButton("Back");
    

        add(label);
        add(jkc19);
        add(noida);
        add(back);

        noida.addActionListener(e -> {
            String t104 = jkc19.getText().trim().toLowerCase();

            if (t104.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Empty", "Error", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            if (!t104.equals("yes") && !t104.equals("no")) {
                JOptionPane.showMessageDialog(null, "Enter yes or no only!!!", "Error", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            try (Connection jconn = DatabaseConnection.getConnection2()) {
                String clearQuery = "DELETE FROM maintainence;";
                jconn.prepareStatement(clearQuery).executeUpdate();

                String query = "INSERT INTO maintainence VALUES(?);";
                PreparedStatement ps2 = jconn.prepareStatement(query);
                ps2.setString(1, t104);
                ps2.executeUpdate();

                JOptionPane.showMessageDialog(null, "Success !!!", "Success", JOptionPane.INFORMATION_MESSAGE);
                jkc19.setText(""); 

            } catch (SQLException eee) {
                JOptionPane.showMessageDialog(null, "Database Error: " + eee.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                eee.printStackTrace();
            }
        });
        back.addActionListener(e -> mainFrame.show_card("admin_dashboard"));
    }

   
    public JPanel get_panel() {
        return this;
    }

    
    public void clearFields() {
        jkc19.setText("");
    }
}
