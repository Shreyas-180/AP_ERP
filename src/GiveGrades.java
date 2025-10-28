import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import javax.swing.*;

public class GiveGrades {
    private JPanel panel;
    private MainFrame mainFrame;
    private JComboBox<String> course_box; 
    private JTextField enter_name1;
    private JButton enter_btn, back_btn;

    public GiveGrades(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        panel = new JPanel(new BorderLayout());

        JLabel title = new JLabel("Give Grades Window", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(title, BorderLayout.NORTH);

        JPanel form = new JPanel(new GridLayout(3, 2, 10, 10));
        JLabel enter_name = new JLabel("Enter the username of the student: ");
        enter_name1 = new JTextField();

        JLabel enter_code = new JLabel("Select the course to grade: ");
        course_box = new JComboBox<>();

        enter_btn = new JButton("Enter");
        back_btn = new JButton("Back");

        form.add(enter_name);
        form.add(enter_name1);
        form.add(enter_code);
        form.add(course_box);
        form.add(enter_btn);
        form.add(back_btn);

        panel.add(form, BorderLayout.CENTER);

        // go back to instructor dashboard
        back_btn.addActionListener(e -> mainFrame.show_card("instructor_dashboard"));
        enter_btn.addActionListener(e->{
            String name = enter_name1.getText();
            String code = (String) course_box.getSelectedItem();
            ArrayList<String> k = get_grades(name, code);
            if(k.size() == 0){
                JOptionPane.showMessageDialog(null, "This student is not in this course !!!");
                return;
            }
            else{
                JPanel updatePanel = new JPanel(new GridLayout(6, 2, 10, 10));

                JLabel weightageQuiz = new JLabel("Quiz Marks: ");
                JTextField weightageQuiz1 = new JTextField(k.get(0));

                JLabel weightageAssignment = new JLabel("Assignment Marks: ");
                JTextField weightageAssignment1 = new JTextField(k.get(1));

                JLabel weightageMidsem = new JLabel("Mid Sem Marks: ");
                JTextField weightageMidsem1 = new JTextField(k.get(2));

                JLabel weightageEndsem = new JLabel("End Sem Marks: ");
                JTextField weightageEndsem1 = new JTextField(k.get(3));

                JLabel weightageGroupproject = new JLabel("Group Project Marks: ");
                JTextField weightageGroupproject1 = new JTextField(k.get(4));

                JButton savebtn = new JButton("Save Grades");

                updatePanel.add(weightageQuiz);
                updatePanel.add(weightageQuiz1);
                updatePanel.add(weightageAssignment);
                updatePanel.add(weightageAssignment1);
                updatePanel.add(weightageMidsem);
                updatePanel.add(weightageMidsem1);
                updatePanel.add(weightageEndsem);
                updatePanel.add(weightageEndsem1);
                updatePanel.add(weightageGroupproject);
                updatePanel.add(weightageGroupproject1);
                updatePanel.add(savebtn);

                JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(panel), "Update Grades", true);
                dialog.add(updatePanel);
                dialog.pack();
                dialog.setLocationRelativeTo(panel);
                //dialog.setVisible(true);
                savebtn.addActionListener(ev -> {
                    try {
                        int quiz = Integer.parseInt(weightageQuiz1.getText());
                        int ass = Integer.parseInt(weightageAssignment1.getText());
                        int mid = Integer.parseInt(weightageMidsem1.getText());
                        int end = Integer.parseInt(weightageEndsem1.getText());
                        int group = Integer.parseInt(weightageGroupproject1.getText());

                        int y = set_grades(name, code, quiz, ass, mid, end, group);
                        
                        // close after saving
                        if (y > 0) {
                            JOptionPane.showMessageDialog(null, "Grades updated successfully!");
                            dialog.dispose(); 
                        } 
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(dialog, "Please enter valid numeric values.");
                    }
                });
                dialog.setVisible(true);
            }
            
        }
        );
    }

    // 
    public void set_instructor(Instructor instructor) {
        course_box.removeAllItems();
        for (String course : instructor.get_course_list()) {
            course_box.addItem(course);
        }
    }
    public int set_grades(String username, String subject, int quiz, int ass, int mid, int end, int group){
        String update = "UPDATE grades SET quiz_marks=?, assignment_marks=?, midsem_marks=?, endsem_marks=?, group_project_marks=? WHERE user_name=? AND subject=?";
        int row = 0;
        try (Connection conn = DatabaseConnection.getConnection2();
            PreparedStatement ps = conn.prepareStatement(update)) {

            ps.setInt(1, quiz);
            ps.setInt(2, ass);
            ps.setInt(3, mid);
            ps.setInt(4, end);
            ps.setInt(5, group);
            ps.setString(6, username);
            ps.setString(7, subject);

            row = ps.executeUpdate();

            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error updating grades: " + e.getMessage());
            }
            return row;
    }
    public ArrayList<String> get_grades(String username, String subject) {
        ArrayList<String> x = new ArrayList<>();
        String selectQuery = "SELECT * FROM grades WHERE user_name=? AND subject=?";

        try (Connection conn = DatabaseConnection.getConnection2()) {
            PreparedStatement ps = conn.prepareStatement(selectQuery);
            ps.setString(1, username);
            ps.setString(2, subject);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                x.add(rs.getString("quiz_marks"));
                x.add(rs.getString("assignment_marks"));
                x.add(rs.getString("midsem_marks"));
                x.add(rs.getString("endsem_marks"));
                x.add(rs.getString("group_project_marks"));
            } else {
                System.out.println("⚠️ No record found for " + username + " in " + subject);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return x;
    }

    public JPanel get_panel() {
        return panel;
    }
}
