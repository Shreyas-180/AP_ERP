import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

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

    
        String[] columns = {"Course", "Timing", "Room"};
        tableModel = new DefaultTableModel(columns, 0);
        course_table = new JTable(tableModel);
        panel.add(new JScrollPane(course_table), BorderLayout.CENTER);

       
        JPanel button_panel = new JPanel();
        JButton give_grades = new JButton("Give Grades");
        JButton compute_grade = new JButton("Compute Grade");
        button_panel.add(compute_grade);
        button_panel.add(give_grades);
        panel.add(button_panel, BorderLayout.SOUTH);

        compute_grade.addActionListener(e -> {
            if (current_instructor == null) {
                JOptionPane.showMessageDialog(panel, "No instructor loaded.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            mainFrame.load_compute_grades(current_instructor);
            mainFrame.show_card("compute_grades");
        });

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
    if (dayTimeRaw == null){ 
        return "";
    }
    String result = dayTimeRaw;

    // Replace in decreasing order of complexity to avoid overlap
    result = result.replace("MWF", "Monday Wednesday Friday");
    result = result.replace("TTh", "Tuesday Thursday");

    // Then handle single-day abbreviations carefully (use regex to match exact letters)
    result = result.replaceAll("\\bM\\b", "Monday");
    result = result.replaceAll("\\bT\\b", "Tuesday");
    result = result.replaceAll("\\bW\\b", "Wednesday");
    result = result.replaceAll("\\bF\\b", "Friday");

    // Cleanup spacing
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
