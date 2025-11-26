package Common;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.mindrot.jbcrypt.BCrypt;

import Admin.Admin;
import Instructor.Instructor;
import Main.Main;
import Main.MainFrame;
import Student.Student;

import javax.swing.*;

public class LoginPage extends JPanel {
    Boolean exist = false;

    JLabel username_text = new JLabel("Enter Username: ");
    JTextField username_enter = new JTextField();
    JLabel pass_text = new JLabel("Enter password: ");
    JPasswordField pass_enter = new JPasswordField();
    JButton login = new JButton("login");
    JLabel failed_login = new JLabel("Wrong password or user name! ");

    private MainFrame mainFrame; // reference to parent frame

    public LoginPage(MainFrame mainFrame) {
        this.mainFrame = mainFrame;


        setLayout(new BorderLayout());

        // main content panel uses GridLayout for simple form
        JPanel formPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(40, 50, 40, 50));

        formPanel.add(username_text);
        formPanel.add(username_enter);
        formPanel.add(pass_text);
        formPanel.add(pass_enter);
        formPanel.add(new JLabel()); // empty placeholder
        formPanel.add(login);

        add(formPanel, BorderLayout.CENTER);

        failed_login.setHorizontalAlignment(SwingConstants.CENTER);
        failed_login.setForeground(Color.RED);
        failed_login.setVisible(false);
        add(failed_login, BorderLayout.SOUTH);

        pass_enter.setEchoChar('x');

    
        login.addActionListener(e -> {
            failed_login.setVisible(false); // Hide previous error
            try (Connection conn = DatabaseConnection.getConnection()) {
                String user_name = username_enter.getText();
                char[] password = pass_enter.getPassword();
                String passw = new String(password);
                
                // UPDATED QUERY: Get lock status and attempts
                String query = "SELECT * FROM username_password WHERE user_name = ?;";
                PreparedStatement ps = conn.prepareStatement(query);
                ps.setString(1, user_name);
                ResultSet check = ps.executeQuery();
                
                if (check.next()) {
                    // NEW: Check lock status first
                    boolean is_locked = check.getBoolean("is_locked");
                    if (is_locked) {
                         failed_login.setText("Account is LOCKED.");
                         failed_login.setVisible(true);
                         return;
                    }

                    String p = check.getString("password");
                    int failed_attempts = check.getInt("failed_attempts"); // Get current attempts

                    if (BCrypt.checkpw(passw, p)) {
                        // NEW: Reset attempts on success
                        if (failed_attempts > 0) {
                            String resetQ = "UPDATE username_password SET failed_attempts = 0 WHERE user_name = ?";
                            PreparedStatement psReset = conn.prepareStatement(resetQ);
                            psReset.setString(1, user_name);
                            psReset.executeUpdate();
                        }

                        
                        try (Connection conn2 = DatabaseConnection.getConnection()) {
                           
                            String desg = check.getString("desg"); 
                            // System.out.println(desg); 
                            if (desg.equals("Instructor")) {
                                for (Instructor i : Main.list_of_instructors) {
                                    if(i.get_name_id().equals(user_name)){
                                        mainFrame.load_instructor_dashboard(i);
                                        mainFrame.show_card("instructor_dashboard");
                                        break;
                                    }
                                }
                            } else if (desg.equals("Student")) {
                                Student obj = Factory.factory_for_stud(user_name);
                                mainFrame.load_student_dashboard(obj);
                                mainFrame.show_card("student_dashboard");
                            } else {
                                Admin obj = Factory.factory_for_admin(user_name);
                                mainFrame.load_admin_dashboard(obj);
                                mainFrame.show_card("admin_dashboard");
                            }
                        } catch (SQLException e2) {
                            e2.printStackTrace();
                        }
                    } else {
                        //handle failed attempt
                        failed_attempts++;
                        if (failed_attempts >= 5) {
                             String lockQ = "UPDATE username_password SET failed_attempts = ?, is_locked = 1 WHERE user_name = ?";
                             PreparedStatement psLock = conn.prepareStatement(lockQ);
                             psLock.setInt(1, failed_attempts);
                             psLock.setString(2, user_name);
                             psLock.executeUpdate();
                             failed_login.setText("Account LOCKED (Too many failures)");
                        } else {
                             String updateQ = "UPDATE username_password SET failed_attempts = ? WHERE user_name = ?";
                             PreparedStatement psUpdate = conn.prepareStatement(updateQ);
                             psUpdate.setInt(1, failed_attempts);
                             psUpdate.setString(2, user_name);
                             psUpdate.executeUpdate();
                             failed_login.setText("Wrong password! Attempts left: " + (5 - failed_attempts));
                        }
                        failed_login.setVisible(true);
                    }
                } else {
                    failed_login.setText("Wrong password or user name!");
                    failed_login.setVisible(true);
                }
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        });
    }
    public JPanel get_panel() {
        return this;
    }
}