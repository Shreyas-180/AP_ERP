

import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginPage {
    Boolean exist = false;
    JFrame login_frame = new JFrame("Login Page");
    JLabel username_text = new JLabel("Enter Username: ");
    JTextField username_enter = new JTextField();
    JLabel pass_text = new JLabel("Enter password: ");
    JPasswordField pass_enter = new JPasswordField();
    JButton login = new JButton("login");
    JLabel failed_login = new JLabel("Wrong passwpord or user name! ");
    public LoginPage(){
        failed_login.setBounds(180, 50, 200, 25);
        pass_enter.setEchoChar('x');
        username_text.setBounds(50, 50, 120, 25);
        pass_text.setBounds(50, 90, 120, 25);
        login_frame.setBounds(0, 0, 500, 400);
        login_frame.setLayout(null);
        login_frame.setDefaultCloseOperation(login_frame.EXIT_ON_CLOSE);
        username_enter.setBounds(180, 50, 150, 25);
        pass_enter.setBounds(180, 90, 150, 25);
        login.setBounds(150, 140, 100, 30);
        failed_login.setBounds(150, 160, 100, 30);
        login_frame.setVisible(true);
        login.addActionListener(e -> {
            try (Connection conn = DatabaseConnection.getConnection()) {
                String user_name = username_enter.getText();
                char[] password = pass_enter.getPassword();
                String passw =  new String(password);
                String query = "SELECT * FROM username_password WHERE user_name = ?;";
                
                PreparedStatement ps = conn.prepareStatement(query);
                ps.setString(1, user_name);
                ResultSet check = ps.executeQuery();
                if(check.next() != false){
                    String p = check.getString(2);
                    if(p.equals(passw)){// what i have done now is created a new obj of instrictor, i guess this will be applicable in admin part too
                        //InstructDashboard id = new InstructDashboard();
                        try(Connection conn2 = DatabaseConnection.getConnection()){
                            query = "SELECT * FROM username_password WHERE user_name = ?;";
                            ps = conn2.prepareStatement(query);
                            ps.setString(1, user_name);
                            check = ps.executeQuery();
                            if(check.next() != false){
                                String desg = check.getString(3);
                                if(desg.equals("Instructor")){
                                    InstructDashboard i_dash = new InstructDashboard();
                                    Instructor obj = Factory.factory_for_instruc(user_name);
                                    i_dash.display_dashboard(obj);

                                }
                                else if(desg.equals("Student")){
                                    StudDashboard s_dash = new StudDashboard();
                                    Student obj = Factory.factory_for_stud(user_name);
                                    s_dash.display_dashboard(obj);
                                    
                                    
                                }
                                else{
                                    AdminDashboard a_dash = new AdminDashboard();
                                    Admin obj = Factory.factory_for_admin(user_name);
                                    a_dash.display_dashboard(obj);
                                }
                            }



                        }catch(SQLException e2){
                            e2.printStackTrace();
                        }
                        
                    }
                    else{
                        login_frame.add(failed_login);
                        login_frame.revalidate();
                        login_frame.repaint();
                    }

                }
                else{
                    login_frame.add(failed_login);
                    login_frame.revalidate();
                    login_frame.repaint();
                }
                //ps.setString(1, passw);
            }
            catch (SQLException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        });
        // login.addActionListener(e -> {
        //     String user_name = username_enter.getText();
        //     System.out.println("Welcome");
        //     System.out.println(user_name);
        //     char[] password = pass_enter.getPassword();
        //     String passw = new String(password);

        //     System.out.println("Logging In");
        //     try (Connection conn = DatabaseConnection.getConnection()) {
        //     if (conn != null) {
        //         String insertQuery = "INSERT INTO users (username, password) VALUES (?, ?)";
        //         PreparedStatement ps = conn.prepareStatement(insertQuery);
        //         ps.setString(1, user_name);
        //         ps.setString(2, passw);
        //         int rows = 0;
        //         try{
        //             rows = ps.executeUpdate();
        //             System.out.println(rows + " row(s) inserted.");
        //         }catch(SQLIntegrityConstraintViolationException E){
        //             System.out.println("Duplicat Key");
                
        //         }
        //         insertQuery = "SELECT * FROM users";
        //         ps = conn.prepareStatement(insertQuery);
        //         //rows = ps.executeUpdate(); // INSERT, UPDATE, DELETE → use executeUpdate
        //         ResultSet rs = ps.executeQuery();
        //         while(rs.next()){
        //             System.out.println(rs.getInt("id") + " | " +
        //                rs.getString("username") + " | " +
        //                rs.getString("password"));
        //         }
        //     } else {
        //         System.out.println("Could not connect.");
        //     }
        //     } catch (Exception ex) {
        //         ex.printStackTrace();
        //     }
        //     // call your method to open a new window
        // });

        login_frame.add(username_enter);
        login_frame.add(username_text);
        login_frame.add(pass_enter);
        login_frame.add(pass_text);
        login_frame.add(login);
    }
}