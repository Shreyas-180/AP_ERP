package Instructor;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import Common.DatabaseConnection;
import Main.MainFrame;

import java.awt.*;
import java.sql.*;
import java.util.ArrayList;

public class StatsWindow {
    private JPanel panel;
    private MainFrame mainFrame;
    private JTable stats_table;
    private DefaultTableModel tableModel;
    private JLabel header;

    public StatsWindow(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        panel = new JPanel(new BorderLayout(10, 10));

        // --- Header ---
        header = new JLabel("Course Statistics", SwingConstants.CENTER);
        header.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(header, BorderLayout.NORTH);

        // --- Table to show stats ---
        // Columns: Course Code, Number of Students, Class Average
        String[] columns = {"Course Code", "Student Count", "Class Average"};
        tableModel = new DefaultTableModel(columns, 0);
        stats_table = new JTable(tableModel);
        panel.add(new JScrollPane(stats_table), BorderLayout.CENTER);

        // --- Back Button ---
        JButton back_btn = new JButton("Back to Dashboard");
        back_btn.addActionListener(e -> mainFrame.show_card("instructor_dashboard"));
        
        JPanel bottomPanel = new JPanel();
        bottomPanel.add(back_btn);
        panel.add(bottomPanel, BorderLayout.SOUTH);
    }

    public JPanel get_panel() {
        return panel;
    }

    // This is called when the "View Stats" button is clicked in the Dashboard
    public void load_stats(Instructor instructor) {
        // Clear previous data
        tableModel.setRowCount(0); 
        
        ArrayList<String> courses = instructor.get_course_list();
        
        // We need the instructor's username to distinguish their specific section/weights
        String instructorUsername = instructor.get_name_id(); 

        for (String courseCode : courses) {
            calculate_and_add_row(courseCode, instructorUsername);
        }
    }

    private void calculate_and_add_row(String courseCode, String instructorUsername) {
        // 1. Get the Weights SPECIFIC to this instructor & course
        // (Because different instructors might have different weighting for the same course code)
        int[] weights = get_weights(courseCode, instructorUsername); 
        
        // 2. Get student grades filtered by this instructor
        ArrayList<Integer> studentTotals = get_student_totals(courseCode, weights, instructorUsername);

        // 3. Calculate Average
        double average = 0.0;
        if (!studentTotals.isEmpty()) {
            int sum = 0;
            for (int score : studentTotals) {
                sum += score;
            }
            average = (double) sum / studentTotals.size();
        }

        // 4. Add to table
        tableModel.addRow(new Object[]{
            courseCode, 
            studentTotals.size(), 
            String.format("%.2f", average) // Format to 2 decimal places
        });
    }

    // --- Helper: Fetch weights for Course AND Instructor ---
    private int[] get_weights(String code, String instructorName) {
        int[] w = new int[5]; // Stores: [quiz, assignment, midsem, endsem, group_project]
        
        // We filter by BOTH code and instructor to get the correct section's grading policy
        String query = "SELECT * FROM courses WHERE code = ? AND instructor = ?";
        
        try (Connection conn = DatabaseConnection.getConnection2();
             PreparedStatement ps = conn.prepareStatement(query)) {
            
            ps.setString(1, code);
            ps.setString(2, instructorName);
            
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                // Using getInt is safer for numbers, but works same as Integer.parseInt(getString(...))
                w[0] = rs.getInt("quiz");
                w[1] = rs.getInt("assignment");
                w[2] = rs.getInt("midsem");
                w[3] = rs.getInt("endsem");
                w[4] = rs.getInt("group_project");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return w;
    }

    // --- Helper: Calculate total score for students in this specific section ---
    private ArrayList<Integer> get_student_totals(String code, int[] w, String instructorName) {
        ArrayList<Integer> totals = new ArrayList<>();
        
        // We filter by Subject AND Instructor to ensure we don't average other sections' students
        String query = "SELECT * FROM grades WHERE subject = ? AND instructor = ?";
        
        try (Connection conn = DatabaseConnection.getConnection2();
             PreparedStatement ps = conn.prepareStatement(query)) {
            
            ps.setString(1, code);
            ps.setString(2, instructorName); 
            
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                // Fetch the raw marks
                int q = Integer.parseInt(rs.getString("quiz_marks"));
                int a = Integer.parseInt(rs.getString("assignment_marks"));
                int m = Integer.parseInt(rs.getString("midsem_marks"));
                int e = Integer.parseInt(rs.getString("endsem_marks"));
                int g = Integer.parseInt(rs.getString("group_project_marks"));

                // Calculate the weighted total for this student
                int total = (q*w[0] + a*w[1] + m*w[2] + e*w[3] + g*w[4]) / 100;
                totals.add(total);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return totals;
    }
}