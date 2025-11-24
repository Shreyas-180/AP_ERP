import javax.swing.*;
import java.awt.*;
import java.sql.*;
import org.mindrot.jbcrypt.BCrypt;

public class AdminResetPassword {
    private JPanel panel;
    private MainFrame mainFrame;
    private JTextField username_field;
    private JPasswordField new_pass_field;
    private JPasswordField confirm_pass_field;
    private JLabel err;

    public AdminResetPassword(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        panel = new JPanel(new BorderLayout(10, 10));

        JLabel title = new JLabel("Admin: Force Password Reset", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 18));
        title.setForeground(Color.RED); // distinctive color for admin tool
        panel.add(title, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        // 1. Username Field (Editable for Admin)
        formPanel.add(new JLabel("Target Username:"));
        username_field = new JTextField();
        formPanel.add(username_field);

        // 2. New Password
        formPanel.add(new JLabel("New Password:"));
        new_pass_field = new JPasswordField();
        formPanel.add(new_pass_field);

        // 3. Confirm Password
        formPanel.add(new JLabel("Confirm Password:"));
        confirm_pass_field = new JPasswordField();
        formPanel.add(confirm_pass_field);

        panel.add(formPanel, BorderLayout.CENTER);

        // --- Buttons ---
        JPanel southPanel = new JPanel(new BorderLayout());
        err = new JLabel("", SwingConstants.CENTER);
        err.setForeground(Color.RED);
        southPanel.add(err, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton resetBtn = new JButton("Reset Password");
        JButton backBtn = new JButton("Back");
        
        buttonPanel.add(resetBtn);
        buttonPanel.add(backBtn);
        southPanel.add(buttonPanel, BorderLayout.SOUTH);

        panel.add(southPanel, BorderLayout.SOUTH);

        // --- Logic ---
        backBtn.addActionListener(e -> {
            clear_fields();
            mainFrame.show_card("admin_dashboard");
        });

        resetBtn.addActionListener(e -> perform_reset());
    }

    private void perform_reset() {
        String targetUser = username_field.getText().trim();
        String newPass = new String(new_pass_field.getPassword());
        String confirmPass = new String(confirm_pass_field.getPassword());

        if (targetUser.isEmpty() || newPass.isEmpty()) {
            err.setText("Username and Password are required.");
            return;
        }
        if (!newPass.equals(confirmPass)) {
            err.setText("Passwords do not match.");
            return;
        }

        try (Connection conn = DatabaseConnection.getConnection()) {
            // 1. Check if user exists first
            String checkQ = "SELECT user_name FROM username_password WHERE user_name = ?";
            PreparedStatement psCheck = conn.prepareStatement(checkQ);
            psCheck.setString(1, targetUser);
            ResultSet rs = psCheck.executeQuery();

            if (rs.next()) {
                // 2. Update Password directly (No old password check needed)
                String newHash = BCrypt.hashpw(newPass, BCrypt.gensalt(12));
                
                String updateQ = "UPDATE username_password SET password = ?, failed_attempts = 0, is_locked = 0 WHERE user_name = ?";
                PreparedStatement psUp = conn.prepareStatement(updateQ);
                psUp.setString(1, newHash);
                psUp.setString(2, targetUser);
                int rows = psUp.executeUpdate();

                if (rows > 0) {
                    JOptionPane.showMessageDialog(panel, "Password successfully reset for user: " + targetUser);
                    clear_fields();
                    mainFrame.show_card("admin_dashboard");
                }
            } else {
                err.setText("User '" + targetUser + "' not found in database.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            err.setText("Database Error: " + e.getMessage());
        }
    }

    private void clear_fields() {
        username_field.setText("");
        new_pass_field.setText("");
        confirm_pass_field.setText("");
        err.setText("");
    }

    public JPanel get_panel() {
        return panel;
    }
}