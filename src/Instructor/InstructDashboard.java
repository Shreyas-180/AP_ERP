package Instructor;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import Common.DatabaseConnection;
import Main.Main;
import Main.MainFrame;

import java.awt.*;
import java.io.File;
import java.sql.*;
import java.util.ArrayList;

public class InstructDashboard {
    private JPanel panel;
    private MainFrame mainFrame;
    private JTable course_table;
    private DefaultTableModel tableModel;
    private JLabel header;
    private Instructor current_instructor;

    public InstructDashboard(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        panel = new JPanel(new BorderLayout(10, 10));

        header = new JLabel("Instructor Dashboard", SwingConstants.CENTER);
        header.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(header, BorderLayout.NORTH);

        JButton change_pass = new JButton("Change Password");
        change_pass.addActionListener(e -> {
        if (current_instructor != null) {
            mainFrame.load_change_password(current_instructor.get_name_id(), "instructor_dashboard");
        }
        });
        String[] columns = {"Course", "Timing", "Room"};
        tableModel = new DefaultTableModel(columns, 0);
        course_table = new JTable(tableModel);
        panel.add(new JScrollPane(course_table), BorderLayout.CENTER);

       
        JPanel button_panel = new JPanel();
        JButton give_grades = new JButton("Give Grades");
        JButton compute_grade = new JButton("Compute Grade");
        JButton stats_btn = new JButton("View Stats");
        stats_btn.addActionListener(e -> {
            if (current_instructor != null) {
                mainFrame.load_stats_window(current_instructor);
            }
        });
        button_panel.add(stats_btn);
        button_panel.add(compute_grade);
        button_panel.add(give_grades);
        button_panel.add(change_pass);
        JButton logout = new JButton("Logout");
        logout.addActionListener(e -> {
            mainFrame.show_card("login");
        });
        button_panel.add(logout);
        panel.add(button_panel, BorderLayout.SOUTH);

        compute_grade.addActionListener(e -> {
            if (current_instructor == null) {
                JOptionPane.showMessageDialog(panel, "No instructor loaded.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            mainFrame.load_compute_grades(current_instructor);
            mainFrame.show_card("compute_grades");
        });
        JButton csvBtn = new JButton("Upload CSV Grades");
        csvBtn.addActionListener(e -> {
            if (current_instructor == null) return;

            // Reuse the instructor's course list
            // Make sure you added get_course_list() to Instructor.java!
            java.util.ArrayList<String> courses = current_instructor.get_course_list(); 

            if (courses.isEmpty()) {
                // ERROR WAS HERE: changed 'this' to 'panel'
                JOptionPane.showMessageDialog(panel, "You have no courses assigned."); 
                return;
            }
            
            String[] courseArray = courses.toArray(new String[0]);
            
            // ERROR WAS HERE: changed 'this' to 'panel'
            String selectedCourse = (String) JOptionPane.showInputDialog(
                    panel, 
                    "Select Course for CSV Upload:", 
                    "Upload Grades", 
                    JOptionPane.QUESTION_MESSAGE, 
                    null, 
                    courseArray, 
                    courseArray[0]);

            if (selectedCourse != null) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Select Grades CSV File");
                
                // ERROR WAS HERE: changed 'this' to 'panel'
                int userSelection = fileChooser.showOpenDialog(panel); 

                if (userSelection == JFileChooser.APPROVE_OPTION) {
                    java.io.File fileToUpload = fileChooser.getSelectedFile();
                    
                    // Run the Uploader
                    CSVGradeUploader.uploadGrades(fileToUpload, selectedCourse);
                }
            }
        });

        button_panel.add(csvBtn);
        give_grades.addActionListener(e -> {
            if (Main.getstatus() == false) {
                if (current_instructor == null) {
                    JOptionPane.showMessageDialog(panel, "No instructor loaded.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                mainFrame.load_give_grades(current_instructor);
                mainFrame.show_card("give_grades");
            } else {
                JOptionPane.showMessageDialog(panel, "Maintenance Mode is on! You can't edit anything right now!!!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    public JPanel get_panel() {
        return panel;
    }

    public void load_instructor_dashboard(Instructor i) {
        current_instructor = i;
        header.setText("Welcome, " + i.get_real_name());
        tableModel.setRowCount(0); // clear old rows

        add_timing(i.get_name_id());
    }


    private void add_timing(String instructorUsername) {
        String query = "SELECT course_code, day_time, room FROM sections WHERE instructor_user_name = ?";

        try (Connection conn23 = DatabaseConnection.getConnection2()){
            PreparedStatement ps =conn23.prepareStatement(query);
            ps.setString(1, instructorUsername);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String course = rs.getString("course_code");
                String timing = rs.getString("day_time");
                String room = rs.getString("room");

                timing = parseDayTime(timing);

                
                tableModel.addRow(new Object[]{course, timing, room});
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(panel, "Error loading courses: " + e.getMessage(),
                    "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

private String parseDayTime(String dayTimeRaw) {
    if (dayTimeRaw == null) {
        return "";
    }

    String result = dayTimeRaw.trim();

    // Replace multi-day combinations first (longest first to prevent overlap)
    result = result.replace("MWF", "Monday Wednesday Friday");
    result = result.replace("TTh", "Tuesday Thursday");
    result = result.replace("TuTh", "Tuesday Thursday");
    result = result.replace("MTh", "Monday Thursday");
    result = result.replace("MT", "Monday Tuesday");
    result = result.replace("WF", "Wednesday Friday");
    result = result.replace("MW", "Monday Wednesday");
    result = result.replace("ThF", "Thursday Friday");

    // Handle single-day abbreviations (using word boundaries)
    result = result.replaceAll("\\bM\\b", "Monday");
    result = result.replaceAll("\\bT\\b", "Tuesday");
    result = result.replaceAll("\\bTu\\b", "Tuesday");
    result = result.replaceAll("\\bW\\b", "Wednesday");
    result = result.replaceAll("\\bTh\\b", "Thursday");
    result = result.replaceAll("\\bF\\b", "Friday");
    result = result.replaceAll("\\bSa\\b", "Saturday");
    result = result.replaceAll("\\bSu\\b", "Sunday");

    // Normalize extra spaces
    result = result.replaceAll("\\s+", " ").trim();

    return result;
}



//    private String parseDayTime(String dayTimeRaw) {
//         if (dayTimeRaw == null) return "";
//         String result = dayTimeRaw;

//         result = result.replace("TTh", "Tuesday Thursday");
//         result = result.replace("MWF", "Monday Wednesday Friday");

//         result = result.replace("M", "Monday");
//         result = result.replace("T", "Tuesday");
//         result = result.replace("W", "Wednesday");
//         result = result.replace("F", "Friday");

    
//         result = result.replaceAll("\\s+", " ").trim();
//         return result;
//     }

}
