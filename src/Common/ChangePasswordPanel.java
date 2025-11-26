package Common;
import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.Arrays;
import org.mindrot.jbcrypt.BCrypt;

import Main.MainFrame;

public class ChangePasswordPanel {
    private JPanel panel;
    private MainFrame mainFrame;
    private JTextField username_field;
    private JPasswordField old_pass_field;
    private JPasswordField new_pass_field;
    private JPasswordField confirm_pass_field;
    private JLabel err;
    private String current_username;
    private String return_card_name; // To know where to go back to

    public ChangePasswordPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        panel = new JPanel(new BorderLayout(10, 10));

        JLabel title = new JLabel("Change Password", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(title, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        
        formPanel.add(new JLabel("Username:"));
        username_field = new JTextField();
        username_field.setEditable(false); 
        formPanel.add(username_field);

        formPanel.add(new JLabel("Old Password:"));
        old_pass_field = new JPasswordField();
        formPanel.add(old_pass_field);

        formPanel.add(new JLabel("New Password:"));
        new_pass_field = new JPasswordField();
        formPanel.add(new_pass_field);

        formPanel.add(new JLabel("Confirm New Password:"));
        confirm_pass_field = new JPasswordField();
        formPanel.add(confirm_pass_field);

        panel.add(formPanel, BorderLayout.CENTER);

        JPanel southPanel = new JPanel(new BorderLayout());
        err = new JLabel("", SwingConstants.CENTER);
        err.setForeground(Color.RED);
        southPanel.add(err, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton submit = new JButton("Change Password");
        JButton back = new JButton("Back");
        buttonPanel.add(submit);
        buttonPanel.add(back);
        southPanel.add(buttonPanel, BorderLayout.SOUTH);

        panel.add(southPanel, BorderLayout.SOUTH);

        // --- Action Listeners ---
        back.addActionListener(e -> {
            reset_fields();
            mainFrame.show_card(return_card_name);
        });

        submit.addActionListener(e -> change_password());
    }

    public JPanel get_panel() {
        return panel;
    }

    public void set_user(String username, String returnCard) {
        this.current_username = username;
        this.return_card_name = returnCard;
        this.username_field.setText(username);
        reset_fields();
    }

    private void reset_fields() {
        old_pass_field.setText("");
        new_pass_field.setText("");
        confirm_pass_field.setText("");
        err.setText("");
    }

    private void change_password() {
        String old_pass = new String(old_pass_field.getPassword());
        String new_pass = new String(new_pass_field.getPassword());
        String confirm_pass = new String(confirm_pass_field.getPassword());

        if (old_pass.isEmpty() || new_pass.isEmpty() || confirm_pass.isEmpty()) {
            err.setText("All fields are required.");
            return;
        }

        if (!new_pass.equals(confirm_pass)) {
            err.setText("New passwords do not match.");
            return;
        }

        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "SELECT password FROM username_password WHERE user_name = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, current_username);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String stored_hash = rs.getString("password");
                if (BCrypt.checkpw(old_pass, stored_hash)) {
                    // Good old password, now update to new hash
                    String new_hash = BCrypt.hashpw(new_pass, BCrypt.gensalt(12));
                    String updateQuery = "UPDATE username_password SET password = ? WHERE user_name = ?";
                    PreparedStatement psUpdate = conn.prepareStatement(updateQuery);
                    psUpdate.setString(1, new_hash);
                    psUpdate.setString(2, current_username);
                    psUpdate.executeUpdate();

                    JOptionPane.showMessageDialog(panel, "Password changed successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    mainFrame.show_card(return_card_name);
                } else {
                    err.setText("Incorrect old password.");
                }
            } else {
                err.setText("User not found.");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            err.setText("Database error: " + ex.getMessage());
        }
    }
}