package Common;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import Student.Student;
import Student.StudentService;

import java.awt.*;
import java.util.ArrayList;

public class TimetableWindow extends JDialog { 

    private Student currentUser;
    private JTable table;
    private DefaultTableModel model;

    public TimetableWindow(Student student) {
        this.currentUser = student;

        setTitle("My Timetable");
        setSize(700, 400);
        setLocationRelativeTo(null); 
        setLayout(new BorderLayout());
        setModal(true); 

        String[] cols = {"Course Code", "Course Title", "Day/Time", "Room"};
        model = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; 
            }
        };
        table = new JTable(model);

        add(new JScrollPane(table), BorderLayout.CENTER);

        loadTimetable();
    }

    private void loadTimetable() {
        StudentService ss = new StudentService();
        ArrayList<String[]> list = ss.getTimetable(currentUser.getUsername());

        if (list.isEmpty()) {
            JOptionPane.showMessageDialog(this, "You are not enrolled in any classes yet.");
            return;
        }

        for (String[] row : list) {
            // data comes as: [ID, Code, Title, Time, Room]
            // We need to skip the ID (index 0)
            model.addRow(new Object[]{
                row[1], // Course Code
                row[2], // Course Title
                row[3], // Day/Time
                row[4]  // Room
            });
        }
    }
}