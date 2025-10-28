import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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

        // overall panel uses BorderLayout for structure
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

        // failed login message (hidden by default)
        failed_login.setHorizontalAlignment(SwingConstants.CENTER);
        failed_login.setForeground(Color.RED);
        failed_login.setVisible(false);
        add(failed_login, BorderLayout.SOUTH);

        
        pass_enter.setEchoChar('x');

        // same login logic as before
        login.addActionListener(e -> {
            try (Connection conn = DatabaseConnection.getConnection()) {
                String user_name = username_enter.getText();
                char[] password = pass_enter.getPassword();
                String passw = new String(password);
                String query = "SELECT * FROM username_password WHERE user_name = ?;";
                
                PreparedStatement ps = conn.prepareStatement(query);
                ps.setString(1, user_name);
                ResultSet check = ps.executeQuery();
                if (check.next() != false) {
                    String p = check.getString(2);
                    if (p.equals(passw)) {
                        try (Connection conn2 = DatabaseConnection.getConnection()) {
                            query = "SELECT * FROM username_password WHERE user_name = ?;";
                            ps = conn2.prepareStatement(query);
                            ps.setString(1, user_name);
                            check = ps.executeQuery();
                            if (check.next() != false) {
                                String desg = check.getString(3);
                                System.out.println(desg);
                                if (desg.equals("Instructor")) {
                                    System.out.println(desg);
                                    for (Instructor i : Main.list_of_instructors) {
                                        if(i.get_name_id().equals(user_name)){
                                            System.out.println(i.getSections());
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
                            }
                        } catch (SQLException e2) {
                            e2.printStackTrace();
                        }
                    } else {
                        failed_login.setVisible(true);
                    }
                } else {
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



