import java.awt.GridLayout;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class Maintainence {
    public void maintainence(){
        JFrame jkc = new JFrame();
        jkc.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JLabel label = new JLabel("Enter Yes for turning on maintainence and No for turning off");
        JTextField jkc19 = new JTextField();
        JButton noida = new JButton("Save Preference");
        jkc.add(noida);
        jkc.add(label);
        jkc.add(jkc19);
        jkc.setSize(400, 150);
        jkc.setLayout(new GridLayout(3, 1, 10, 10));
        jkc.setLocationRelativeTo(null);
        jkc.setVisible(true);
       
        noida.addActionListener(e->{
            String t104 = jkc19.getText();
            t104 = t104.toLowerCase();
            if(t104.isEmpty()){
                JOptionPane.showMessageDialog(jkc, "Empty", "Error", JOptionPane.INFORMATION_MESSAGE );
                return;
            }
            if(t104.equals("yes") == false && t104.equals("no") == false){
                JOptionPane.showMessageDialog(jkc, "Enter yes or no only!!!", "Error", JOptionPane.INFORMATION_MESSAGE );
                return;
            }
            try(Connection jconn = DatabaseConnection.getConnection2()){
               
                String clearQuery = "DELETE FROM maintainence;";
                jconn.prepareStatement(clearQuery).executeUpdate();

                String query = "INSERT INTO maintainence VALUES(?);";
                PreparedStatement ps2 = jconn.prepareStatement(query);
                ps2.setString(1,t104);
                ps2.executeUpdate();
                JOptionPane.showMessageDialog(jkc, "Success !!!", "Success", JOptionPane.INFORMATION_MESSAGE );
                
            }catch(SQLException eee){
                
            }


        });
    }
}
