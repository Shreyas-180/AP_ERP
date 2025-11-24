import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class StudentGradesWindow extends JFrame {

    // --- FIX 1: Changed from User to Student ---
    private Student currentUser;
    private JTable gradeTable;
    private DefaultTableModel model;

    // --- FIX 2: Changed constructor from User to Student ---
    public StudentGradesWindow(Student student) {
        this.currentUser = student;

        setTitle("My Grades");
        setSize(800, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // --- FIX 3: Changed columns to match your 'grades' table ---
        String[] cols = {
            "Course Code",
            "Course Title",
            "Quiz",
            "Assignment",
            "Midsem",
            "Endsem",
            "Group Project",
            "Final Grade"
        };

        model = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make table non-editable
            }
        };
        gradeTable = new JTable(model);

        add(new JScrollPane(gradeTable), BorderLayout.CENTER);

        loadGrades();
    }

    private void loadGrades() {

        StudentService ss = new StudentService();
        
        // --- FIX 4: Using getUsername() as it's the only valid DB key ---
        ArrayList<String[]> grades = ss.getGrades(currentUser.getUsername());

        if (grades.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No grades available yet.");
            // dispose(); // close window if no grades
            return;
        }

        for (String[] row : grades) {
            model.addRow(row);
        }
    }
}