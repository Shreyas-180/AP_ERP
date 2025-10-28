import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class InstructDashboard {
    private JPanel panel;
    private MainFrame mainFrame;
    private DefaultListModel<String> course_model;
    private JList<String> course_list;
    private JLabel header;
    private Instructor current_instructor;

    public InstructDashboard(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        panel = new JPanel(new BorderLayout(10, 10));

        header = new JLabel("Instructor Dashboard", SwingConstants.CENTER);
        header.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(header, BorderLayout.NORTH);

        // list to show courses
        course_model = new DefaultListModel<>();
        course_list = new JList<>(course_model);
        panel.add(new JScrollPane(course_list), BorderLayout.CENTER);

        // buttons section
        JPanel button_panel = new JPanel();
        JButton give_grades = new JButton("Give Grades");
        JButton compute_grade = new JButton("Compute Grade");
        button_panel.add(compute_grade);
        button_panel.add(give_grades);
        panel.add(button_panel, BorderLayout.SOUTH);

        // action listener for card switch — use the stored instructor
        compute_grade.addActionListener(e->{
             if (current_instructor == null) {
                JOptionPane.showMessageDialog(panel, "No instructor loaded.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            // tell MainFrame to load data for GiveGrades, then show card
            mainFrame.load_compute_grades(current_instructor);
            mainFrame.show_card("compute_grades");


        });
        give_grades.addActionListener(e -> {
            if (current_instructor == null) {
                JOptionPane.showMessageDialog(panel, "No instructor loaded.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            // tell MainFrame to load data for GiveGrades, then show card
            mainFrame.load_give_grades(current_instructor);
            mainFrame.show_card("give_grades");
        });
    }

    public JPanel get_panel() {
        return panel;
    }

    // This is what runs when instructor logs in
    public void load_instructor_dashboard(Instructor i) {
        current_instructor = i;

        System.out.println(i.getid());
        header.setText("Welcome, " + i.get_real_name());
        System.out.println("sus");
        course_model.clear();
        for (String section : i.get_course_list()) {
            course_model.addElement(section);
            System.out.println(section);
        }
    }
}
