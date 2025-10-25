import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.*;
import java.awt.*;




// right now i can't change the instructor!!!





public class EditCourse {
    public void editcourse() {
        JFrame searchFrame = new JFrame("Edit Course");
        searchFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        searchFrame.setSize(400, 150);
        searchFrame.setLayout(new BorderLayout(10, 10));

        JPanel inputPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        JLabel course_code = new JLabel("Enter Course Code to edit: ");
        JTextField course_code1 = new JTextField();
        JButton searchButton = new JButton("Search Course");

        inputPanel.add(course_code);
        inputPanel.add(course_code1);
        inputPanel.add(new JLabel()); // empty cell
        inputPanel.add(searchButton);

        searchFrame.add(inputPanel, BorderLayout.CENTER);
        searchFrame.setLocationRelativeTo(null);
        searchFrame.setVisible(true);
        // JLabel course_code = new JLabel("Enter Course Code to edit: ");
        // JTextField course_code1 = new JTextField();
        // JButton searchButton = new JButton("Search Course ");

        searchButton.addActionListener(e -> {
            String code11 = course_code1.getText();
            ArrayList<Course> list = Factory.factory_for_course();
            boolean found = false;

            for (Course c : list) {
                if (c.getCode().equals(code11)) {
                    found = true;

                    JFrame add_frame = new JFrame("Edit Course Page");
                    add_frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

                    JPanel mainpanel = new JPanel(new BorderLayout(10, 10));
                    JPanel panel = new JPanel(new GridLayout(12, 2, 10, 10));

                    JLabel course_title = new JLabel("Enter Course Title: ");
                    JTextField course_title1 = new JTextField(c.getTitle());

                    JLabel course_codeLabel = new JLabel("Enter Course Code: ");
                    JTextField course_codeField = new JTextField(c.getCode());

                    JLabel course_instructor = new JLabel("Enter Course Instructor's ID: ");
                    JTextField course_instructor1 = new JTextField(c.getInstructor());

                    JLabel weightageQuiz = new JLabel("Weightage of Quiz: ");
                    JTextField weightageQuiz1 = new JTextField(String.valueOf(c.getQuiz()));

                    JLabel weightageMidsem = new JLabel("Weightage of Mid Sem: ");
                    JTextField weightageMidsem1 = new JTextField(String.valueOf(c.getMidsem()));

                    JLabel weightageEndsem = new JLabel("Weightage of End Sem: ");
                    JTextField weightageEndsem1 = new JTextField(String.valueOf(c.getEndsem()));

                    JLabel weightageAssignment = new JLabel("Weightage of Assignment: ");
                    JTextField weightageAssignment1 = new JTextField(String.valueOf(c.getAssignment()));

                    JLabel weightageGroupproject = new JLabel("Weightage of Group Project: ");
                    JTextField weightageGroupproject1 = new JTextField(String.valueOf(c.getGroup_project()));

                    JLabel credits = new JLabel("Enter Credits: ");
                    JTextField credits1 = new JTextField(String.valueOf(c.getCredits()));

                    JLabel courseDesc = new JLabel("Enter Course Description: ");
                    JTextArea courseDesc1 = new JTextArea(3, 20);
                    courseDesc1.setText(c.getCourse_description());
                    courseDesc1.setLineWrap(true);
                    courseDesc1.setWrapStyleWord(true);
                    JScrollPane descriptionpane = new JScrollPane(courseDesc1);

                    JLabel seats = new JLabel("Enter Course Capacity: ");
                    JTextField seats1 = new JTextField(String.valueOf(c.getseats()));

                    JLabel sections = new JLabel("Enter Section: ");
                    JTextField sections1 = new JTextField(c.getSection());

                    JButton saveButton = new JButton("Save Changes");

                    panel.add(course_title); panel.add(course_title1);
                    panel.add(course_codeLabel); panel.add(course_codeField);
                    panel.add(course_instructor); panel.add(course_instructor1);
                    panel.add(weightageQuiz); panel.add(weightageQuiz1);
                    panel.add(weightageMidsem); panel.add(weightageMidsem1);
                    panel.add(weightageEndsem); panel.add(weightageEndsem1);
                    panel.add(weightageAssignment); panel.add(weightageAssignment1);
                    panel.add(weightageGroupproject); panel.add(weightageGroupproject1);
                    panel.add(credits); panel.add(credits1);
                    panel.add(seats); panel.add(seats1);
                    panel.add(courseDesc); panel.add(descriptionpane);
                    panel.add(sections); panel.add(sections1);

                    mainpanel.add(panel, BorderLayout.CENTER);
                    mainpanel.add(saveButton, BorderLayout.SOUTH);

                    add_frame.add(mainpanel);
                    add_frame.pack();
                    add_frame.setLocationRelativeTo(null);
                    add_frame.setVisible(true);

                    // fixed lambda name here
                    saveButton.addActionListener(ev -> {
                        String c_code = course_codeField.getText();
                        String instructr = course_instructor1.getText();
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

                        if (!c_code.equals(c.getCode()) || !instructr.equals(c.getInstructor())) {
                            JOptionPane.showMessageDialog(add_frame, "Warning!! Can't change course code or Instructor", "Error", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        if (c_code.isEmpty() || instructr.isEmpty() || c_desc.isEmpty() || c_title.isEmpty() ||
                            w_quiz.isEmpty() || w_assignment.isEmpty() || w_mid.isEmpty() || w_end.isEmpty() ||
                            w_group.isEmpty() || c_credit.isEmpty() || c_seats.isEmpty()) {
                            JOptionPane.showMessageDialog(add_frame, "Fill all the details!!", "Error", JOptionPane.ERROR_MESSAGE);
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

                            if (c_credit1 <= 0 || c_seats1 <= 0 || w_assignment1 < 0 || w_end1 < 0 || w_group1 < 0 || w_mid1 < 0 || w_quiz1 < 0 ||
                                w_assignment1 + w_end1 + w_group1 + w_mid1 + w_quiz1 != 100) {
                                JOptionPane.showMessageDialog(add_frame, "Invalid weights! Sum must be 100.", "Error", JOptionPane.ERROR_MESSAGE);
                                return;
                            }

                            try (Connection conn1 = DatabaseConnection.getConnection2()) {
                                String q1 = "UPDATE courses SET title = ?, credits = ?, instructor = ?, quiz = ?, assignment = ?, midsem = ?, endsem = ?, group_project = ?, course_description = ?, seats = ?, section = ? WHERE code = ?;";
                                PreparedStatement ps1 = conn1.prepareStatement(q1);

                                ps1.setString(1, c_title);
                                ps1.setInt(2, c_credit1);
                                ps1.setString(3, instructr);
                                ps1.setInt(4, w_quiz1);
                                ps1.setInt(5, w_assignment1);
                                ps1.setInt(6, w_mid1);
                                ps1.setInt(7, w_end1);
                                ps1.setInt(8, w_group1);
                                ps1.setString(9, c_desc);
                                ps1.setInt(10, c_seats1);
                                ps1.setString(11, c_section);
                                ps1.setString(12, c_code);

                                int rowsUpdated = ps1.executeUpdate();

                                if (rowsUpdated > 0) {
                                    JOptionPane.showMessageDialog(add_frame, "Course details updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                                } else {
                                    JOptionPane.showMessageDialog(add_frame, "No course found with that code!", "Error", JOptionPane.ERROR_MESSAGE);
                                }
                            } catch (SQLException ex) {
                                JOptionPane.showMessageDialog(add_frame, "Database error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                            }

                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(add_frame, "Enter valid numeric values!", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    });

                    break; 
                }
            }

            if (!found) {
                JOptionPane.showMessageDialog(null, "Course not found!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}
