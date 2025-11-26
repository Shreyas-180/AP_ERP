import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.sql.*;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class AdminDashboard extends JPanel {
    private JPanel panel;
    private MainFrame mainFrame;
    private JLabel header;
    private Admin current_admin;
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "pichu panda"; 
    private static final String BACKUP_FILE_NAME = "my_system_backup.sql";
    private static final String MYSQL_PATH = "C:\\Program Files\\MySQL\\MySQL Server 8.0\\bin\\";
    public AdminDashboard(MainFrame mainframe) {
        this.mainFrame = mainframe;
        panel = new JPanel(new BorderLayout(10, 10));

        header = new JLabel("Admin Dashboard", SwingConstants.CENTER);
        header.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(header, BorderLayout.NORTH);

        JPanel button_panel = new JPanel();
        JButton add_users = new JButton("Add Users");
        JButton add_course = new JButton("Add Course");
        JButton set_maintainence = new JButton("Set Maintainence");
        JButton edit_course = new JButton("Edit Course");
        JButton btnBackup = new JButton("Backup DB");
        JButton btnRestore = new JButton("Restore DB");

        // --- NEW BUTTON ---
        JButton drop_section = new JButton("Drop Section"); 

        button_panel.add(add_course);
        button_panel.add(add_users);
        button_panel.add(set_maintainence);
        button_panel.add(edit_course);
        button_panel.add(drop_section); // Add to existing panel
        button_panel.add(btnBackup);
        button_panel.add(btnRestore);
        panel.add(button_panel, BorderLayout.CENTER); // Fixed layout placement

        JButton change_pass = new JButton("Change Password");
        button_panel.add(change_pass);
        
        JButton unlock_user = new JButton("Unlock User");
        unlock_user.addActionListener(e -> {
            String user_to_unlock = JOptionPane.showInputDialog(panel, "Enter username to unlock:");
            if (user_to_unlock != null && !user_to_unlock.trim().isEmpty()) {
                unlockUser(user_to_unlock.trim());
            }
        });
        btnBackup.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(panel, 
                "This will OVERWRITE your previous backup with the current database state.\nAre you sure?", 
                "Create Backup", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                performBackup();
            }
        });
        btnRestore.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(panel, 
                "WARNING: This will WIPEOUT current data and revert to the last backup.\nAre you sure?", 
                "Restore Database", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                performRestore();
            }
        });
        button_panel.add(unlock_user);
        JButton logout = new JButton("Logout");
        logout.addActionListener(e -> {
            mainFrame.show_card("login");
        });
        button_panel.add(logout);
        JButton resetUserPass = new JButton("Reset User Password");
        resetUserPass.addActionListener(e -> mainFrame.show_card("admin_reset_password"));
        button_panel.add(resetUserPass);

        JButton datesBtn = new JButton("Set Deadlines");
        datesBtn.addActionListener(e -> {
            SetRegistrationDates screen = new SetRegistrationDates(mainFrame);
            screen.showWindow();
        });
        button_panel.add(datesBtn);

        // --- EXISTING LISTENERS ---
        change_pass.addActionListener(e -> mainFrame.load_change_password(current_admin.getid(), "admin_dashboard"));
        add_users.addActionListener(e -> {
            mainFrame.load_add_users(current_admin);
            mainFrame.show_card("add_users");
        });
        set_maintainence.addActionListener(e -> mainframe.show_card("set_maintainence"));
        add_course.addActionListener(e -> mainframe.show_card("add_course"));
        edit_course.addActionListener(e -> mainframe.show_card("edit_course"));

        // --- NEW LISTENER FOR DROP SECTION ---
        drop_section.addActionListener(e -> {
            // Simple popup for inputs
            JTextField codeField = new JTextField();
            JTextField sectionField = new JTextField();
            Object[] message = { "Course Code:", codeField, "Section:", sectionField };

            int option = JOptionPane.showConfirmDialog(panel, message, "Drop Section", JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.OK_OPTION) {
                dropSectionLogic(codeField.getText().trim(), sectionField.getText().trim());
            }
        });
    }
    private void performBackup() {
        try {
            File backupFile = new File(BACKUP_FILE_NAME);

            // 1. Construct command using the Full Path
            String dumpCommand = MYSQL_PATH + "mysqldump.exe";

            // 2. Setup ProcessBuilder
            // Note: "-p" + DB_PASSWORD automatically handles the space in "pichu panda"
            ProcessBuilder pb = new ProcessBuilder(
                dumpCommand, 
                "-u" + DB_USER, 
                "-p" + DB_PASSWORD, 
                "--databases", "erp", "auth", 
                "-r", backupFile.getAbsolutePath()
            );

            pb.redirectErrorStream(true); 
            Process process = pb.start();

            // 3. Read Output (for debugging)
            StringBuilder output = new StringBuilder();
            try (java.io.BufferedReader reader = new java.io.BufferedReader(
                    new java.io.InputStreamReader(process.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    output.append(line).append("\n");
                }
            }

            int processComplete = process.waitFor();

            if (processComplete == 0) {
                JOptionPane.showMessageDialog(panel, "Backup Successful!\nSaved to: " + backupFile.getAbsolutePath());
            } else {
                JOptionPane.showMessageDialog(panel, 
                    "Backup Failed! (Exit Code: " + processComplete + ")\n\nError Details:\n" + output.toString(), 
                    "Backup Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(panel, 
                "Error: " + ex.getMessage() + "\n\nDouble-check that this file exists:\n" + MYSQL_PATH + "mysqldump.exe", 
                "Java Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void performRestore() {
        try {
            File backupFile = new File(BACKUP_FILE_NAME);
            if (!backupFile.exists()) {
                JOptionPane.showMessageDialog(panel, "No backup file found!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // 1. Construct command using the Full Path
            String mysqlCommand = MYSQL_PATH + "mysql.exe";

            ProcessBuilder pb = new ProcessBuilder(
                mysqlCommand, 
                "-u" + DB_USER, 
                "-p" + DB_PASSWORD
            );

            pb.redirectErrorStream(true);
            Process process = pb.start();

            // 2. Feed the backup file to the process
            try (java.io.OutputStream os = process.getOutputStream();
                 java.io.FileInputStream fis = new java.io.FileInputStream(backupFile)) {
                byte[] buffer = new byte[1024];
                int length;
                while ((length = fis.read(buffer)) != -1) {
                    os.write(buffer, 0, length);
                }
                os.flush();
            }

            // 3. Read Output
            StringBuilder output = new StringBuilder();
            try (java.io.BufferedReader reader = new java.io.BufferedReader(
                    new java.io.InputStreamReader(process.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    output.append(line).append("\n");
                }
            }

            int processComplete = process.waitFor();

            if (processComplete == 0) {
                JOptionPane.showMessageDialog(panel, "Restore Successful!");
            } else {
                JOptionPane.showMessageDialog(panel, 
                    "Restore Failed! (Exit Code: " + processComplete + ")\n\nError Details:\n" + output.toString(), 
                    "Restore Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(panel, 
                "Error: " + ex.getMessage() + "\n\nDouble-check that this file exists:\n" + MYSQL_PATH + "mysql.exe", 
                "Java Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    private void dropSectionLogic(String code, String section) {
        if (code.isEmpty() || section.isEmpty()) return;

        try (Connection conn = DatabaseConnection.getConnection2()) {
            // 1. Get Section ID and Instructor Username
            String q1 = "SELECT id, instructor_user_name FROM sections WHERE course_code = ? AND section = ?";
            PreparedStatement ps1 = conn.prepareStatement(q1);
            ps1.setString(1, code);
            ps1.setString(2, section);
            ResultSet rs = ps1.executeQuery();

            if (!rs.next()) {
                JOptionPane.showMessageDialog(panel, "Section not found!");
                return;
            }
            int sectionId = rs.getInt("id");
            String instructorUser = rs.getString("instructor_user_name");

            // 2. Check if students are enrolled (SAFETY CHECK)
            String q2 = "SELECT COUNT(*) FROM enrollments WHERE section_id = ? AND status = 'enrolled'";
            PreparedStatement ps2 = conn.prepareStatement(q2);
            ps2.setInt(1, sectionId);
            ResultSet rs2 = ps2.executeQuery();
            rs2.next();
            if (rs2.getInt(1) > 0) {
                JOptionPane.showMessageDialog(panel, "Cannot drop! Students are enrolled.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // 3. Delete from tables (Transaction)
            conn.setAutoCommit(false);
            try {
                // A. Delete from courses (Removes specific section A or B)
                PreparedStatement del1 = conn.prepareStatement("DELETE FROM courses WHERE code = ? AND section = ?");
                del1.setString(1, code);
                del1.setString(2, section);
                del1.executeUpdate();

                // B. Delete from sections (Removes metadata)
                PreparedStatement del2 = conn.prepareStatement("DELETE FROM sections WHERE id = ?");
                del2.setInt(1, sectionId);
                del2.executeUpdate();

                // C. FIX: Only delete instructor mapping if they teach NO other sections
                // Since we already ran del1, the current section is gone. 
                // We check if any *other* sections remain for this instructor + course.
                String checkRemaining = "SELECT COUNT(*) FROM courses WHERE code = ? AND instructor = ?";
                PreparedStatement psCheck = conn.prepareStatement(checkRemaining);
                psCheck.setString(1, code);
                psCheck.setString(2, instructorUser);
                ResultSet rsCheck = psCheck.executeQuery();
                
                if (rsCheck.next() && rsCheck.getInt(1) == 0) {
                    // count is 0, meaning they have no sections left for this course.
                    // NOW it is safe to remove the mapping.
                    PreparedStatement del3 = conn.prepareStatement("DELETE FROM subjectsxname_instructor WHERE user_name = ? AND code = ?");
                    del3.setString(1, instructorUser);
                    del3.setString(2, code);
                    del3.executeUpdate();
                }

                conn.commit();
                JOptionPane.showMessageDialog(panel, "Section Dropped Successfully.");
                
            } catch (SQLException ex) {
                conn.rollback();
                ex.printStackTrace();
                JOptionPane.showMessageDialog(panel, "Error dropping section: " + ex.getMessage());
            } finally {
                conn.setAutoCommit(true);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(panel, "Database Error: " + e.getMessage());
        }
    }

    private void unlockUser(String username) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String checkQuery = "SELECT is_locked FROM username_password WHERE user_name = ?";
            PreparedStatement checkPs = conn.prepareStatement(checkQuery);
            checkPs.setString(1, username);
            ResultSet rs = checkPs.executeQuery();

            if (rs.next()) {
                boolean isLocked = rs.getBoolean("is_locked");
                if (isLocked) {
                    String unlockQuery = "UPDATE username_password SET failed_attempts = 0, is_locked = 0 WHERE user_name = ?";
                    PreparedStatement unlockPs = conn.prepareStatement(unlockQuery);
                    unlockPs.setString(1, username);
                    unlockPs.executeUpdate();
                    JOptionPane.showMessageDialog(panel, "User '" + username + "' unlocked successfully.");
                } else {
                    JOptionPane.showMessageDialog(panel, "User '" + username + "' is not currently locked.");
                }
            } else {
                JOptionPane.showMessageDialog(panel, "User '" + username + "' not found.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(panel, "Database error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public JPanel get_panel() {
        return panel;
    }
    public void load_admin_dashboard(Admin i) {
        current_admin = i;
        if(i!=null) header.setText("Welcome, " + i.get_real_name());
    }
    public void load_admin_dashboard(){ }
}
// public class AdminDashboard extends JPanel{
//     private JPanel panel;
//     private MainFrame mainFrame;
//     //private DefaultListModel<String> course_model;
//    // private JList<String> course_list;
//     private JLabel header;
//     private Admin current_admin;

//     public AdminDashboard(MainFrame mainframe) {
//         this.mainFrame = mainframe;
//         panel = new JPanel(new BorderLayout(10, 10));

//         header = new JLabel("Admin Dashboard", SwingConstants.CENTER);
//         header.setFont(new Font("Arial", Font.BOLD, 18));
//         panel.add(header, BorderLayout.NORTH);

//         JPanel button_panel = new JPanel();
//         JButton add_users = new JButton("Add Users");
//         JButton add_course = new JButton("Add Course");
//         JButton set_maintainence = new JButton("Set Maintainence");
//         JButton edit_course = new JButton("Edit Course");
//         button_panel.add(add_course);
//         button_panel.add(add_users);
//         button_panel.add(set_maintainence);
//         button_panel.add(edit_course);
//         panel.add(button_panel);
//         JButton change_pass = new JButton("Change Password");
//         button_panel.add(change_pass);
//         JButton unlock_user = new JButton("Unlock User");
//         unlock_user.addActionListener(e -> {
//             String user_to_unlock = JOptionPane.showInputDialog(panel, "Enter username to unlock:");
//             if (user_to_unlock != null && !user_to_unlock.trim().isEmpty()) {
//                 unlockUser(user_to_unlock.trim());
//             }
//         });
//         button_panel.add(unlock_user);
//         change_pass.addActionListener(e -> {
//             mainFrame.load_change_password(current_admin.getid(), "admin_dashboard");
//         });
//         add_users.addActionListener(e->{
//             //AddUsers add1 = new AddUsers(mainframe);
//             //add1.addusers();
//             mainFrame.load_add_users(current_admin);
//             mainFrame.show_card("add_users");
//         });

//         set_maintainence.addActionListener(e->{
//             mainframe.show_card("set_maintainence");
//         });
//         add_course.addActionListener(e->{
//             mainframe.show_card("add_course");
//         });
//         edit_course.addActionListener(e->{
//             mainframe.show_card("edit_course");
//         });
//         JButton resetUserPass = new JButton("Reset User Password");
//         resetUserPass.addActionListener(e -> {
//             mainFrame.show_card("admin_reset_password");
//         });
//         button_panel.add(resetUserPass);
//         JButton datesBtn = new JButton("Set Deadlines");
//         datesBtn.addActionListener(e -> {
//             SetRegistrationDates screen = new SetRegistrationDates(mainFrame);
//             screen.showWindow();
//         });
//         button_panel.add(datesBtn);
//     }

//     private void unlockUser(String username) {
//         try (Connection conn = DatabaseConnection.getConnection()) {
//             // Check if user exists and is actually locked
//             String checkQuery = "SELECT is_locked FROM username_password WHERE user_name = ?";
//             PreparedStatement checkPs = conn.prepareStatement(checkQuery);
//             checkPs.setString(1, username);
//             ResultSet rs = checkPs.executeQuery();

//             if (rs.next()) {
//                 boolean isLocked = rs.getBoolean("is_locked");
//                 if (isLocked) {
//                     // Unlock them
//                     String unlockQuery = "UPDATE username_password SET failed_attempts = 0, is_locked = 0 WHERE user_name = ?";
//                     PreparedStatement unlockPs = conn.prepareStatement(unlockQuery);
//                     unlockPs.setString(1, username);
//                     unlockPs.executeUpdate();
//                     JOptionPane.showMessageDialog(panel, "User '" + username + "' unlocked successfully.");
//                 } else {
//                     JOptionPane.showMessageDialog(panel, "User '" + username + "' is not currently locked.");
//                 }
//             } else {
//                 JOptionPane.showMessageDialog(panel, "User '" + username + "' not found.", "Error", JOptionPane.ERROR_MESSAGE);
//             }
//         } catch (SQLException ex) {
//             ex.printStackTrace();
//             JOptionPane.showMessageDialog(panel, "Database error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
//         }
//     }

//     // void display_dashboard(Admin a) {
//     //     // --- Frame Setup ---
//     //     JFrame dashboard = new JFrame("Admin Dashboard");
//     //     dashboard.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
//     //     dashboard.setSize(600, 500);
//     //     dashboard.setLayout(new BorderLayout(15, 15)); 

//     //     // --- Greeting Label (Top Area) ---
//     //     JLabel greetLabel = new JLabel("Welcome, " + a.getUsername(), JLabel.CENTER);
//     //     greetLabel.setFont(new Font("Arial", Font.BOLD, 24));
//     //     dashboard.add(greetLabel, BorderLayout.NORTH); 

//     //     // --- Button Panel (Center Area) ---
//     //     JPanel buttonPanel = new JPanel();
//     //     // GridLayout creates a grid of equal-sized components. Perfect for big buttons.
//     //     // (rows, cols, h-gap, v-gap)
//     //     buttonPanel.setLayout(new GridLayout(2, 2, 15, 15));

//     //     // --- "Add Users" Button ---
//     //     JButton addUsersButton = new JButton("Add Users");
//     //     addUsersButton.setFont(new Font("Arial", Font.PLAIN, 18));
//     //     addUsersButton.addActionListener(e -> {
            
//     //         AddUsers add1 = new AddUsers();
//     //         add1.addusers();
//     //         //System.out.println("'Add Users' button clicked!");
//     //     });
//     //     buttonPanel.add(addUsersButton); // Add button to the panel

       
//     //     JButton add_course = new JButton("Add Course");
//     //     add_course.setFont(new Font("Arial", Font.PLAIN, 18));
//     //     add_course.addActionListener(e -> {
            
//     //         AddCourse add2 = new AddCourse();
//     //         add2.addcourse();
//     //         //System.out.println("'Add Users' button clicked!");
//     //     });
//     //     buttonPanel.add(add_course);

//     //     JButton viewReportsButton = new JButton("Set Maintainence");
//     //     viewReportsButton.setFont(new Font("Arial", Font.PLAIN, 18));
//     //     viewReportsButton.addActionListener(e -> {
            
//     //         Maintainence add2 = new Maintainence();
//     //         add2.maintainence();
//     //         //System.out.println("'Add Users' button clicked!");
//     //     });
//     //     buttonPanel.add(viewReportsButton);

        
//     //     JButton settingsButton = new JButton("Edit Course");
//     //     settingsButton.setFont(new Font("Arial", Font.PLAIN, 18));
//     //     settingsButton.addActionListener(e -> {
            
//     //         EditCourse add2 = new EditCourse();
//     //         add2.editcourse();
//     //         //System.out.println("'Add Users' button clicked!");
//     //     });
//     //     buttonPanel.add(settingsButton);

//     //     dashboard.add(buttonPanel, BorderLayout.CENTER); 
//     //     dashboard.setLocationRelativeTo(null);
//     //     dashboard.setVisible(true);
//     // }

//     public JPanel get_panel() {
//         return panel;
//     }
//     public void load_admin_dashboard(Admin i) {
//         current_admin = i;
//         System.out.println(i.getid());
//         header.setText("Welcome, " + i.get_real_name());
//         System.out.println("sus");
//        // course_model.clear();
//         // for (String section : i.get_course_list()) {
//         //     course_model.addElement(section);
//         //     System.out.println(section);
//         // }
//     }
//     public void load_admin_dashboard(){

//     }
// }
