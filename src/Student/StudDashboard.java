package Student;
import javax.swing.*;

import Common.DatabaseConnection;
import Common.TimetableWindow;
import Main.MainFrame;

import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class StudDashboard extends JPanel {

    private Student currentUser;
    private StudentService studentService = new StudentService();
    private MainFrame mainFrame;
    private JLabel title; // Made title a class field to update it

    public StudDashboard(MainFrame frame) {
        this.mainFrame = frame;

        setLayout(new BorderLayout());

        title = new JLabel("Student Dashboard", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 28));
        add(title, BorderLayout.NORTH);

        JPanel btnPanel = new JPanel();
        btnPanel.setLayout(new GridLayout(7, 1, 10, 10));

        JButton viewCatalogBtn = new JButton("Browse Course Catalog");
        JButton registerBtn = new JButton("Register for a Section");
        JButton dropBtn = new JButton("Drop Course");
        JButton timetableBtn = new JButton("View Timetable");
        JButton gradesBtn = new JButton("View Grades");
        JButton transcriptBtn = new JButton("Download Transcript");
        
        btnPanel.add(viewCatalogBtn);
        btnPanel.add(registerBtn);
        btnPanel.add(dropBtn);
        btnPanel.add(timetableBtn);
        btnPanel.add(gradesBtn);
        btnPanel.add(transcriptBtn);
     

        JPanel centerPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 100, 10, 100); // Padding
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        centerPanel.add(btnPanel, gbc);
        add(centerPanel, BorderLayout.CENTER);


        JPanel southPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton logout = new JButton("Logout");
        logout.addActionListener(e -> {
            title.setText("Student Dashboard"); // Reset title on logout
            mainFrame.show_card("login");
        });
        southPanel.add(logout);
        add(southPanel, BorderLayout.SOUTH);
       


        viewCatalogBtn.addActionListener(e -> {
            mainFrame.show_card("view_courses");
        });

        registerBtn.addActionListener(e -> {
            
            RegisterSectionDialog rsd = new RegisterSectionDialog(currentUser, studentService);
            rsd.setVisible(true);
        });

        
        dropBtn.addActionListener(e -> {
            String dateStatus = studentService.checkRegistrationWindow();
            
            if (!dateStatus.equals("OK")) {
                JOptionPane.showMessageDialog(this, dateStatus, "Access Denied", JOptionPane.ERROR_MESSAGE);
                return; 
            }
            dropSectionUI(); 
        });

        timetableBtn.addActionListener(e -> {
           
            TimetableWindow t = new TimetableWindow(currentUser);
            t.setVisible(true);
        });

        gradesBtn.addActionListener(e -> {
        
            StudentGradesWindow sw = new StudentGradesWindow(currentUser);
            sw.setVisible(true);
        });

        transcriptBtn.addActionListener(e -> {
           
             TranscriptExporter.exportTranscript(currentUser);
            //JOptionPane.showMessageDialog(this, "Transcript Exporter not implemented yet.");
        });
    }

    public void loadStudentDashboard(Student student) {
        this.currentUser = student;
        // Update the welcome label
        title.setText("Welcome, " + currentUser.getUsername());
        SwingUtilities.invokeLater(() -> checkAndShowNotifications());
    }

   // In StudDashboard.java
// This is the new method that creates the dropdown
private void dropSectionUI() {
    
    //  Get the list of enrolled courses
    java.util.ArrayList<String[]> enrolledCourses = studentService.getTimetable(currentUser.getUsername());

    // Check if the student is enrolled in anything 
    if (enrolledCourses.isEmpty()) {
        JOptionPane.showMessageDialog(this, "You are not enrolled in any courses to drop.", "Drop Section", JOptionPane.INFORMATION_MESSAGE);
        return;
    }

    //  Prepare the data for the dropdown menu 
    java.util.HashMap<String, Integer> courseMap = new java.util.HashMap<>();
    String[] displayOptions = new String[enrolledCourses.size()];
    
    for (int i = 0; i < enrolledCourses.size(); i++) {
        String[] courseData = enrolledCourses.get(i);
        int sectionId = Integer.parseInt(courseData[0]); // [0] is Section ID
        String courseCode = courseData[1]; // [1] is Course Code
        String courseTitle = courseData[2]; // [2] is Title
        
        String displayString = String.format("%s - %s (Section ID: %d)", courseCode, courseTitle, sectionId);
        
        displayOptions[i] = displayString;
        courseMap.put(displayString, sectionId); // Link the string to the ID
    }

    // Create UI
    JFrame f = new JFrame("Drop Section");
    f.setSize(500, 200);
    f.setLocationRelativeTo(this); 
    
    JLabel lbl = new JLabel("Select the section you want to drop:", SwingConstants.CENTER);
    JComboBox<String> courseDropdown = new JComboBox<>(displayOptions);
    JButton dropBtn = new JButton("Drop Selected Section");

    JPanel panel = new JPanel(new GridLayout(3, 1, 10, 10));
    panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); 
    panel.add(lbl);
    panel.add(courseDropdown);
    panel.add(dropBtn);
    
    f.add(panel);

    // Define the drop button's action 
    dropBtn.addActionListener(e -> {
        try {
            String selectedString = (String) courseDropdown.getSelectedItem();
            int sectionId = courseMap.get(selectedString);
            
            String msg = studentService.dropSection(currentUser.getUsername(), sectionId);
            
            JOptionPane.showMessageDialog(f, msg);
            f.dispose(); 
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(f, "An error occurred: " + ex.getMessage());
            ex.printStackTrace();
        }
    });

    f.setVisible(true);
}
private void checkAndShowNotifications() {
        if (currentUser == null) return;

        String checkQuery = "SELECT status FROM notifications WHERE username = ?";
        String updateQuery = "UPDATE notifications SET status = 'NO' WHERE username = ?";
        boolean hasNewNotification = false;

        try (Connection conn = DatabaseConnection.getConnection2()) {
            
            // Check status i
            try (PreparedStatement psCheck = conn.prepareStatement(checkQuery)) {
                psCheck.setString(1, currentUser.getUsername());
                ResultSet rs = psCheck.executeQuery();
                
                // We loop in case there are multiple entries, but we only need one "YES" to trigger the popup
                while (rs.next()) {
                    String status = rs.getString("status");
                    if ("YES".equalsIgnoreCase(status)) {
                        hasNewNotification = true;
                        break; // Found a notification, no need to keep checking
                    }
                }
            }

            //  If we found a "YES", Update DB and Show Popup
            if (hasNewNotification) {
                // Change status to "NO" so it doesn't show again
                try (PreparedStatement psUpdate = conn.prepareStatement(updateQuery)) {
                    psUpdate.setString(1, currentUser.getUsername());
                    psUpdate.executeUpdate();
                }

                // Show the popup
                JOptionPane.showMessageDialog(this, 
                    "New Grade Assigned! Please check your grade sheet.", 
                    "Notification", 
                    JOptionPane.INFORMATION_MESSAGE);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            // 
            System.out.println("Notification check failed: " + e.getMessage());
        }
    }
}