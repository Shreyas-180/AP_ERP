package Student;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import Common.DatabaseConnection;

import java.awt.*;
import java.sql.*;

public class RegisterSectionDialog extends JFrame {

    private Student currentUser;
    private StudentService studentService;
    private JTable table;
    private DefaultTableModel model;

    public RegisterSectionDialog(Student student, StudentService service) {
        this.currentUser = student;
        this.studentService = service;

        setTitle("Register for a Section");
        setSize(900, 400); // Made slightly wider
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Added "ID" as the first column
        model = new DefaultTableModel(new String[]{
            "ID", "Section", "Course Code", "Course Title", "Instructor",
            "Capacity", "Enrolled", "Day/Time", "Room"
        }, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; 
            }
        };

        table = new JTable(model);
        
       
        // table.getColumnModel().getColumn(0).setMinWidth(0);
        // table.getColumnModel().getColumn(0).setMaxWidth(0);
        // table.getColumnModel().getColumn(0).setWidth(0);

        add(new JScrollPane(table), BorderLayout.CENTER);

        loadSections();

        JButton regBtn = new JButton("Register Selected Section");
        add(regBtn, BorderLayout.SOUTH);

        regBtn.addActionListener(e -> registerSelected());
    }

    private void loadSections() {
        model.setRowCount(0); 
        
        try (Connection con = DatabaseConnection.getConnection2()) { 
            
            String q = "SELECT s.id, s.section, s.course_code, c.title, i.name as instructor, s.capacity, " +
                       "(SELECT COUNT(*) FROM enrollments e WHERE e.section_id = s.id AND e.status = 'enrolled') AS enrolled, " +
                       "s.day_time, s.room " +
                       "FROM sections s " +
                       "JOIN courses c ON s.course_code = c.code AND s.section = c.section " +
                       "LEFT JOIN instructor_name_username i ON s.instructor_user_name = i.user_name";

            PreparedStatement ps = con.prepareStatement(q);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                
                model.addRow(new Object[]{
                    rs.getInt("id"),             // Column 0 (The ID)
                    rs.getString("section"),     // Column 1
                    rs.getString("course_code"), // Column 2
                    rs.getString("title"),       // Column 3
                    rs.getString("instructor"),  // Column 4
                    rs.getInt("capacity"),       // Column 5
                    rs.getInt("enrolled"),       // Column 6
                    rs.getString("day_time"),    // Column 7
                    rs.getString("room")         // Column 8
                });
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading sections: " + ex.getMessage());
        }
    }

    private void registerSelected() {
        //  Check selection first
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Please select a section!");
            return;
        }

        // Check Registration Window
        try {
            String dateStatus = studentService.checkRegistrationWindow();
            if (!dateStatus.equals("OK")) {
                JOptionPane.showMessageDialog(this, dateStatus, "Access Denied", JOptionPane.ERROR_MESSAGE);
                return; 
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error checking dates: " + e.getMessage());
            return;
        }

        // Get ID (Now this works because Column 0 is actually an Integer)
        try {
            int sectionId = Integer.parseInt(model.getValueAt(row, 0).toString());
            
            String msg = studentService.registerSection(currentUser.getUsername(), sectionId);
            JOptionPane.showMessageDialog(this, msg);

            if (msg.contains("Successful")) {
                loadSections(); // Reload to update counts
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error during registration: " + e.getMessage());
        }
    } 
}