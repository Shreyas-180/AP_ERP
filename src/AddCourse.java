import java.awt.*;
import java.sql.*;
import javax.swing.*;

public class AddCourse {
    private JPanel panel;
    private MainFrame mainFrame;

    public AddCourse(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        panel = new JPanel(new BorderLayout(10, 10));

        JLabel title = new JLabel("Add Course Page", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(title, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridLayout(12, 2, 10, 10));

        JLabel course_title = new JLabel("Enter Course Title:");
        JTextField course_title1 = new JTextField();

        JLabel course_code = new JLabel("Enter Course Code:");
        JTextField course_code1 = new JTextField();

        JLabel course_instructor = new JLabel("Enter Course Instructor's ID:");
        JTextField course_instructor1 = new JTextField();

        JLabel weightageQuiz = new JLabel("Weightage of Quiz:");
        JTextField weightageQuiz1 = new JTextField();

        JLabel weightageMidsem = new JLabel("Weightage of Mid Sem:");
        JTextField weightageMidsem1 = new JTextField();

        JLabel weightageEndsem = new JLabel("Weightage of End Sem:");
        JTextField weightageEndsem1 = new JTextField();

        JLabel weightageAssignment = new JLabel("Weightage of Assignment:");
        JTextField weightageAssignment1 = new JTextField();

        JLabel weightageGroupproject = new JLabel("Weightage of Group Project:");
        JTextField weightageGroupproject1 = new JTextField();

        JLabel credits = new JLabel("Enter Credits:");
        JTextField credits1 = new JTextField();

        JLabel courseDesc = new JLabel("Enter Course Description:");
        JTextArea courseDesc1 = new JTextArea(3, 20);
        courseDesc1.setLineWrap(true);
        courseDesc1.setWrapStyleWord(true);
        JScrollPane descriptionpane = new JScrollPane(courseDesc1);

        JLabel seats = new JLabel("Enter Course Capacity:");
        JTextField seats1 = new JTextField();

        JLabel sections = new JLabel("Enter Section:");
        JTextField sections1 = new JTextField();

        // Add components
        formPanel.add(course_title);
        formPanel.add(course_title1);
        formPanel.add(course_code);
        formPanel.add(course_code1);
        formPanel.add(course_instructor);
        formPanel.add(course_instructor1);
        formPanel.add(weightageQuiz);
        formPanel.add(weightageQuiz1);
        formPanel.add(weightageMidsem);
        formPanel.add(weightageMidsem1);
        formPanel.add(weightageEndsem);
        formPanel.add(weightageEndsem1);
        formPanel.add(weightageAssignment);
        formPanel.add(weightageAssignment1);
        formPanel.add(weightageGroupproject);
        formPanel.add(weightageGroupproject1);
        formPanel.add(credits);
        formPanel.add(credits1);
        formPanel.add(seats);
        formPanel.add(seats1);
        formPanel.add(courseDesc);
        formPanel.add(descriptionpane);
        formPanel.add(sections);
        formPanel.add(sections1);

        JButton addcourseButton = new JButton("Add Course");
        JButton backButton = new JButton("Back");

        JPanel bottomPanel = new JPanel(new FlowLayout());
        bottomPanel.add(addcourseButton);
        bottomPanel.add(backButton);

        panel.add(formPanel, BorderLayout.CENTER);
        panel.add(bottomPanel, BorderLayout.SOUTH);

        addcourseButton.addActionListener(e -> {
            String instructr = course_instructor1.getText();
            String c_code = course_code1.getText();
            String c_title = course_title1.getText();
            String c_desc = courseDesc1.getText();
            String w_quiz = weightageQuiz1.getText();
            String w_assignment = weightageAssignment1.getText();
            String w_mid = weightageMidsem1.getText();
            String w_end = weightageEndsem1.getText();
            String w_group = weightageGroupproject1.getText();
            String c_credit = credits1.getText();
            String c_seats = seats1.getText();
            String c_section = sections1.getText();

            if (c_code.isEmpty() || instructr.isEmpty() || c_desc.isEmpty() || c_title.isEmpty()
                    || w_quiz.isEmpty() || w_assignment.isEmpty() || w_mid.isEmpty() || w_end.isEmpty()
                    || w_group.isEmpty() || c_credit.isEmpty() || c_seats.isEmpty()) {
                JOptionPane.showMessageDialog(panel, "Fill all the details !!", "Empty Field", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                int c_credit1 = Integer.parseInt(c_credit);
                int c_seats1 = Integer.parseInt(c_seats);
                int w_assignment1 = Integer.parseInt(w_assignment);
                int w_end1 = Integer.parseInt(w_end);
                int w_group1 = Integer.parseInt(w_group);
                int w_mid1 = Integer.parseInt(w_mid);
                int w_quiz1 = Integer.parseInt(w_quiz);

                if (c_credit1 <= 0 || c_seats1 <= 0 || w_assignment1 < 0 || w_end1 < 0 || w_group1 < 0
                        || w_mid1 < 0 || w_quiz1 < 0
                        || w_assignment1 + w_end1 + w_group1 + w_mid1 + w_quiz1 != 100) {
                    JOptionPane.showMessageDialog(panel, "Fill all details sensibly (non-negative & total=100)!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try (Connection conn1 = DatabaseConnection.getConnection2()) {
                    String q1 = "SELECT * FROM courses WHERE code = ? AND section = ?";
                    PreparedStatement ps1 = conn1.prepareStatement(q1);
                    ps1.setString(1, c_code);
                    ps1.setString(2, c_section);  
                    ResultSet rs = ps1.executeQuery();

                    if (rs.next()) {
                        JOptionPane.showMessageDialog(panel, 
                            "Course with this code and section already exists!");} 
                    else {
                        q1 = "SELECT * FROM relation1 WHERE user_name = ?;";
                        PreparedStatement ps3 = conn1.prepareStatement(q1);
                        ps3.setString(1, instructr);
                        ResultSet rs1 = ps3.executeQuery();
                        if (rs1.next() && rs1.getString("designation").equals("Instructor")) {
                            
          
                            SectionInfo section = SectionDialog.getSectionInfo(mainFrame);
                            
                            String query = "INSERT INTO courses (code, title, credits, instructor, quiz, assignment, midsem, endsem, group_project, course_description, seats, section) VALUES (?,?,?,?,?,?,?,?,?,?,?,?);";
                            PreparedStatement ps = conn1.prepareStatement(query);
                            
                            ps.setString(1, c_code);
                            ps.setString(2, c_title);
                            ps.setInt(3, c_credit1);
                            ps.setString(4, instructr);
                            ps.setInt(5, w_quiz1);
                            ps.setInt(6, w_assignment1);
                            ps.setInt(7, w_mid1);
                            ps.setInt(8, w_end1);
                            ps.setInt(9, w_group1);
                            ps.setString(10, c_desc);
                            ps.setInt(11, c_seats1);
                            ps.setString(12, c_section);
                            
                            ps.executeUpdate();

                            // Insert into sections table
                            String query12 = "INSERT INTO sections (course_code, instructor_user_name, day_time, room, capacity, semester, year, section) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
                            PreparedStatement ps12 = conn1.prepareStatement(query12);
                            ps12.setString(1, c_code);
                            ps12.setString(2, instructr);
                            ps12.setString(3, section.dayTime);
                            ps12.setString(4, section.room);
                            ps12.setInt(5, c_seats1);
                            ps12.setString(6, section.semester);
                            ps12.setInt(7, section.year);
                            ps12.setString(8, c_section);
                            ps12.executeUpdate();

                            // Insert into instructor mapping
                            String checkLink = "SELECT count(*) FROM subjectsxname_instructor WHERE user_name = ? AND code = ?";
                            PreparedStatement psCheck = conn1.prepareStatement(checkLink);
                            psCheck.setString(1, instructr);
                            psCheck.setString(2, c_code);
                            ResultSet rsCheck = psCheck.executeQuery();

                            if (rsCheck.next() && rsCheck.getInt(1) == 0) {
                                // Only insert if the link does NOT exist yet
                                String query2 = "INSERT INTO subjectsxname_instructor VALUES(?,?);";
                                PreparedStatement ps2 = conn1.prepareStatement(query2);
                                ps2.setString(1, instructr);
                                ps2.setString(2, c_code);
                                ps2.executeUpdate();
                            }
                            // String query2 = "INSERT INTO subjectsxname_instructor VALUES(?,?);";
                            // PreparedStatement ps2 = conn1.prepareStatement(query2);
                            // ps2.setString(1, instructr);
                            // ps2.setString(2, c_code);
                            // ps2.executeUpdate();

                            // Update Local List
                            Instructor i1 = null;
                            for (Instructor prof : Main.list_of_instructors) {
                                if (prof.get_name_id().equals(instructr)) {
                                    prof.add_course(c_code);
                                    prof.add_section(c_section);
                                    i1 = prof;
                                    break;
                                }
                            }
                            Course new_course = new Course(c_code, c_title, c_credit1, w_quiz1, w_assignment1, w_mid1, w_end1, w_group1, c_desc, c_seats1, c_section, i1);
                        
                            
                            JOptionPane.showMessageDialog(panel, "Course Added Successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(panel, "Invalid Instructor!", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(panel, "Database error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(panel, "Please enter valid numbers for credits, seats, and weightages.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            }
        });
        backButton.addActionListener(e -> mainFrame.show_card("admin_dashboard"));
    }

    public JPanel get_panel() {
        return panel;
    }
}
