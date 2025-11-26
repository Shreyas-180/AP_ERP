package Common;
import java.util.ArrayList;
import javax.swing.*;

import Main.Main;
import Main.MainFrame;

import java.awt.*;
import java.util.List;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*; // Import SQL

public class ViewCourseWindow extends JPanel {

    private MainFrame mainFrame;

    public ViewCourseWindow(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        this.setLayout(new BorderLayout());

        // 1. Populate the Table (We still use the list for the summary table)
        List<Course> list = Main.list_of_courses;

        String[] column = {"Code", "Title", "Section", "Instructor", "Credits"};
        Object[][] data = new Object[list.size()][5];
        
        for (int i = 0; i < list.size(); i++) {
            Course c = list.get(i);
            data[i][0] = c.getCode();
            data[i][1] = c.getTitle();
            data[i][2] = c.getSection(); 
            data[i][3] = c.getInstructor(); 
            data[i][4] = c.getCredits();
        }

        JTable table = new JTable(data, column);
        table.setDefaultEditor(Object.class, null); 
        JScrollPane scrollPane = new JScrollPane(table);
        
        add(scrollPane, BorderLayout.CENTER);

        // --- Bottom Panel ---
        JPanel bottomPanel = new JPanel();
        bottomPanel.add(new JLabel("Enter Course Code (or select from table):"));
        JTextField text = new JTextField(10);
        bottomPanel.add(text);
        JButton search = new JButton("Search / View Details");
        bottomPanel.add(search);

        JButton backButton = new JButton("Back");
        bottomPanel.add(backButton);

        add(bottomPanel, BorderLayout.SOUTH);

        // --- Auto-fill text box when user clicks a row ---
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                if (row != -1) {
                    String code = table.getValueAt(row, 0).toString();
                    text.setText(code);
                }
            }
        });

        // --- SQL BASED SEARCH LISTENER ---
        search.addActionListener(e -> {
            String code_target = "";
            String section_target = null; 
            
            int selectedRow = table.getSelectedRow();
            
            // 1. Determine what we are looking for
            if (selectedRow != -1) {
                // If table row clicked, get specific Code AND Section
                code_target = table.getValueAt(selectedRow, 0).toString();
                if(table.getValueAt(selectedRow, 2) != null) {
                    section_target = table.getValueAt(selectedRow, 2).toString();
                }
            } else {
                // If manual entry, we only have the code
                code_target = text.getText().trim();
            }

            if (code_target.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please select a course or enter a code.", "Input Required", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // 2. Run the SQL Query
            try (Connection conn = DatabaseConnection.getConnection2()) {
                String query;
                PreparedStatement ps;

                // Dynamic Query: If we know the section, use it. If not, just find the first match for the code.
                if (section_target != null) {
                    query = "SELECT * FROM courses WHERE code = ? AND section = ?";
                    ps = conn.prepareStatement(query);
                    ps.setString(1, code_target);
                    ps.setString(2, section_target);
                } else {
                    query = "SELECT * FROM courses WHERE code = ?";
                    ps = conn.prepareStatement(query);
                    ps.setString(1, code_target);
                }

                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    // Found the course! Extract details directly from DB
                    String message = String.format(
                        "Code: %s\nTitle: %s\nSection: %s\nInstructor: %s\nCredits: %s\n\n"
                        + "Quiz Weightage: %s%%\nAssignment Weightage: %s%%\n"
                        + "Mid-Sem Weightage: %s%%\nEnd-Sem Weightage: %s%%\n"
                        + "Group Project Weightage: %s%%\nAvailable Seats: %s\n\n"
                        + "Description: %s",
                        rs.getString("code"),
                        rs.getString("title"),
                        rs.getString("section"), 
                        rs.getString("instructor"),
                        rs.getString("credits"),
                        rs.getString("quiz"),
                        rs.getString("assignment"),
                        rs.getString("midsem"),
                        rs.getString("endsem"),
                        rs.getString("group_project"),
                        rs.getString("seats"),
                        rs.getString("course_description")
                    );
                    JOptionPane.showMessageDialog(this, message, "Course Details", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "No details found for this course.", "Error", JOptionPane.ERROR_MESSAGE);
                }

            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Database Error: " + ex.getMessage());
            }
        });

        backButton.addActionListener(e -> {
            mainFrame.show_card("student_dashboard");
        });
    }
}