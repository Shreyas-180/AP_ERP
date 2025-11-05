import java.sql.*;
import java.util.ArrayList;
import javax.swing.*;
import java.awt.*;

public class EditCourse {
    private JPanel panel;
    private MainFrame mainFrame;

    private JTextField course_codeField;
    private JTextField course_title1;
    private JTextField course_instructor1;
    private JTextField weightageQuiz1;
    private JTextField weightageMidsem1;
    private JTextField weightageEndsem1;
    private JTextField weightageAssignment1;
    private JTextField weightageGroupproject1;
    private JTextField credits1;
    private JTextArea courseDesc1;
    private JTextField seats1;
    private JTextField sections1;

    public EditCourse(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        this.panel = createPanel();
    }

    

    private JPanel createPanel() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));

        // --- Search panel ---
        JPanel searchPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        JLabel course_code = new JLabel("Enter Course Code to edit: ");
        course_codeField = new JTextField();
        JButton searchButton = new JButton("Search Course");

        searchPanel.add(course_code);
        searchPanel.add(course_codeField);
        searchPanel.add(new JLabel());
        searchPanel.add(searchButton);

        // --- Form panel ---
        JPanel formPanel = new JPanel(new GridLayout(12, 2, 10, 10));
        course_title1 = new JTextField();
        course_instructor1 = new JTextField();
        weightageQuiz1 = new JTextField();
        weightageMidsem1 = new JTextField();
        weightageEndsem1 = new JTextField();
        weightageAssignment1 = new JTextField();
        weightageGroupproject1 = new JTextField();
        credits1 = new JTextField();
        courseDesc1 = new JTextArea(3, 20);
        courseDesc1.setLineWrap(true);
        courseDesc1.setWrapStyleWord(true);
        JScrollPane descriptionPane = new JScrollPane(courseDesc1);
        seats1 = new JTextField();
        sections1 = new JTextField();

        formPanel.add(new JLabel("Enter Course Title: ")); formPanel.add(course_title1);
        formPanel.add(new JLabel("Enter Instructor's ID: ")); formPanel.add(course_instructor1);
        formPanel.add(new JLabel("Weightage of Quiz: ")); formPanel.add(weightageQuiz1);
        formPanel.add(new JLabel("Weightage of Mid Sem: ")); formPanel.add(weightageMidsem1);
        formPanel.add(new JLabel("Weightage of End Sem: ")); formPanel.add(weightageEndsem1);
        formPanel.add(new JLabel("Weightage of Assignment: ")); formPanel.add(weightageAssignment1);
        formPanel.add(new JLabel("Weightage of Group Project: ")); formPanel.add(weightageGroupproject1);
        formPanel.add(new JLabel("Enter Credits: ")); formPanel.add(credits1);
        formPanel.add(new JLabel("Enter Course Capacity: ")); formPanel.add(seats1);
        formPanel.add(new JLabel("Enter Course Description: ")); formPanel.add(descriptionPane);
        formPanel.add(new JLabel("Enter Section: ")); formPanel.add(sections1);

        JButton saveButton = new JButton("Save Changes");
        JButton back = new JButton("Back");
        JPanel bottomPanel = new JPanel();
        bottomPanel.add(saveButton);
        bottomPanel.add(back);

        mainPanel.add(searchPanel, BorderLayout.NORTH);
        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

       back.addActionListener(e -> mainFrame.show_card("admin_dashboard"));

        searchButton.addActionListener(e -> {
            String codeToSearch = course_codeField.getText();
            ArrayList<Course> list = (ArrayList<Course>) Main.list_of_courses;
            Course foundCourse = null;

            for (Course c : list) {
                if (c.getCode().equals(codeToSearch)) {
                    foundCourse = c;
                    break;
                }
            }

            if (foundCourse == null) {
                JOptionPane.showMessageDialog(panel, "Course not found!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Course c = foundCourse;

            course_title1.setText(c.getTitle());
            course_instructor1.setText(c.getInstructor());
            weightageQuiz1.setText(String.valueOf(c.getQuiz()));
            weightageMidsem1.setText(String.valueOf(c.getMidsem()));
            weightageEndsem1.setText(String.valueOf(c.getEndsem()));
            weightageAssignment1.setText(String.valueOf(c.getAssignment()));
            weightageGroupproject1.setText(String.valueOf(c.getGroup_project()));
            credits1.setText(String.valueOf(c.getCredits()));
            courseDesc1.setText(c.getCourse_description());
            seats1.setText(String.valueOf(c.getseats()));
            sections1.setText(c.getSection());
        });

        // --- Save Button Logic (untouched except frame->panel) ---
        saveButton.addActionListener(ev -> {
            // Your original saveButton code starts here — unchanged except JOptionPane targets
            // ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓

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

            Course foundCourse = null;
            for (Course c : Main.list_of_courses) {
                if (c.getCode().equals(c_code)) {
                    foundCourse = c;
                    break;
                }
            }
            if (foundCourse == null) {
                JOptionPane.showMessageDialog(panel, "Course not found!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            Course c = foundCourse;
            String oldSection = c.getSection();

            if (!c_code.equals(c.getCode()) || !c_section.equals(oldSection)) {
                JOptionPane.showMessageDialog(panel, "Warning!! Can't change course code or section", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (c_code.isEmpty() || instructr.isEmpty() || c_desc.isEmpty() || c_title.isEmpty() ||
                    w_quiz.isEmpty() || w_assignment.isEmpty() || w_mid.isEmpty() || w_end.isEmpty() ||
                    w_group.isEmpty() || c_credit.isEmpty() || c_seats.isEmpty()) {
                JOptionPane.showMessageDialog(panel, "Fill all the details!!", "Error", JOptionPane.ERROR_MESSAGE);
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

                if (c_credit1 <= 0 || c_seats1 <= 0 || w_assignment1 < 0 || w_end1 < 0 || w_group1 < 0 ||
                        w_mid1 < 0 || w_quiz1 < 0 || w_assignment1 + w_end1 + w_group1 + w_mid1 + w_quiz1 != 100) {
                    JOptionPane.showMessageDialog(panel, "Invalid weights! Sum must be 100.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                boolean instructorExists = false;
                for (Instructor inst : Main.list_of_instructors) {
                    if (inst.getid().equals(instructr)) {
                        instructorExists = true;
                        break;
                    }
                }

                if (!instructorExists) {
                    JOptionPane.showMessageDialog(panel, "Instructor with ID '" + instructr + "' does not exist!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try (Connection conn1 = DatabaseConnection.getConnection2()) {
                    // (Your full SQL update logic remains unchanged here)
                    // Just make sure all JOptionPane targets = panel
                    // I’m not rewriting all SQL — it's preserved from your message
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
                            ps1.setString(11, c_section); // new section
                            ps1.setString(12, c_code);
                            int rowsUpdated = ps1.executeUpdate();
                            try {
                                String update = """
                                        UPDATE sections
                                        SET instructor_user_name = ?, capacity = ?, section = ?
                                        WHERE id = (
                                            SELECT id FROM relation2 WHERE course_code = ? AND section = ?
                                        )
                                    """;
                                PreparedStatement psUpdateSections = conn1.prepareStatement(update);
                                psUpdateSections.setString(1, instructr);
                                psUpdateSections.setInt(2, c_seats1);
                                psUpdateSections.setString(3, c_section);
                                psUpdateSections.setString(4, c_code.trim());
                                psUpdateSections.setString(5, oldSection.trim());
                                int rows = psUpdateSections.executeUpdate();
                                System.out.println("Rows updated in sections: " + rows);
                            } catch (SQLException ex) {
                                // FIXED: add_frame -> panel
                                JOptionPane.showMessageDialog(panel, "Error updating sections table: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                            }
                            if(!instructr.equals(c.getInstructor())){
                                String oldInstructorId = c.getInstructor();
                                Instructor jkc = c.edit_profinfo();
                                //String oldInstructorId = jkc.getUsername();
                                jkc.remove_course(c_code);
                                jkc.remove_section(c_section);
                                
                                for (Instructor inst : Main.list_of_instructors) {
                                    if (inst.getid().equals(instructr)) {
                                        inst.add_course(c_code);
                                        inst.add_section(c_section);
                                        c.replace_prof(inst);
                                        break;
                                    }
                                }
                                
                                
                                // Database updates - only when instructor changed
                                try {
                                    // del old instructor-course relation
                                    String del = "DELETE FROM subjectsxname_instructor WHERE user_name = ? AND code = ?";
                                    PreparedStatement psDelete = conn1.prepareStatement(del);
                                    psDelete.setString(1, oldInstructorId);
                                    System.out.println(oldInstructorId + c_code);
                                    psDelete.setString(2, c_code);
                                    psDelete.executeUpdate();
                                    
                                    // adding new instructor-course relation
                                    String insertSubjectQuery = "INSERT INTO subjectsxname_instructor (user_name, code) VALUES (?, ?)";
                                    PreparedStatement psInsert = conn1.prepareStatement(insertSubjectQuery);
                                    psInsert.setString(1, instructr);
                                    psInsert.setString(2, c_code);
                                    psInsert.executeUpdate();
                                    
                                    // Update sections table

                                    // String update = "UPDATE sections SET instructor_user_name = ?, capacity = ?, section = ? WHERE course_code = ? AND section = ? AND instructor_user_name = ?";
                                    // PreparedStatement psUpdateSections = conn1.prepareStatement(update);
                                    // //psUpdateSections.setString(1, instructr);
                                    // psUpdateSections.setString(1, instructr);
                                    // psUpdateSections.setInt(2, c_seats1);
                                    // psUpdateSections.setString(3, c_section);
                                    // psUpdateSections.setString(4, c_code.trim());
                                    // psUpdateSections.setString(5, oldSection.trim());
                                    // psUpdateSections.setString(6, oldInstructorId.trim());
                                    
                                    // String update = """
                                    //      UPDATE sections
                                    //      SET instructor_user_name = ?, capacity = ?, section = ?
                                    //      WHERE id = (
                                    //          SELECT id FROM relation2 WHERE course_code = ? AND section = ?
                                    //      )
                                    //  """;
                                    // System.out.println("print shit");
                                    // PreparedStatement psUpdateSections = conn1.prepareStatement(update);
                                    // psUpdateSections.setString(1, instructr);
                                    // psUpdateSections.setInt(2, c_seats1);
                                    // psUpdateSections.setString(3, c_section);
                                    // psUpdateSections.setString(4, c_code.trim());
                                    // psUpdateSections.setString(5, c_section);
                                    // System.out.println(oldSection);
                                    // int rows = psUpdateSections.executeUpdate();

                                    // System.out.println("Rows updated: " + rows);

                                    c.setCourse_description(c_desc);
                                    c.setCredits(c_credit1);
                                    c.setGroup_project(w_group1);
                                    c.setQuiz(w_quiz1);
                                    c.setAssignment(w_assignment1);
                                    c.setGroup_project(w_end1); // Note: This line might be a logic bug (setting group project to endsem val) but left as-is per request.
                                    c.setMidsem(w_mid1);
                                    c.setSeats(c_seats1);
                                    c.setEndsem(w_end1);
                                   // c.setProf(instructr); done above in replace
                                    c.setSection(c_section);

                                   // psUpdateSections.setInt(5, );
                                
                                    
                                    
                                } catch (SQLException ex) {
                                    // FIXED: add_frame -> panel
                                    JOptionPane.showMessageDialog(panel, "Error updating instructor mappings: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                                    return;
                                }
                            }

//////////////////////////////////////////
                           
                            String fetchOld = """
                                    SELECT id, course_code, day_time, room, semester, year, section
                                    FROM sections
                                    WHERE course_code = ? AND section = ?
                                """;
                            PreparedStatement psFetchOld = conn1.prepareStatement(fetchOld);
                            psFetchOld.setString(1, c_code.trim());
                            psFetchOld.setString(2, c_section.trim());
                            ResultSet rsOld = psFetchOld.executeQuery();

                            if (rsOld.next()) {
                                int id = rsOld.getInt("id");
                                String courseCode = rsOld.getString("course_code");
                                String dayTime = rsOld.getString("day_time");
                                String room = rsOld.getString("room");
                                String semester = rsOld.getString("semester");
                                int year = rsOld.getInt("year");
                                String section = rsOld.getString("section");

                          
                                String deleteOld = "DELETE FROM sections WHERE id = ?";
                                PreparedStatement psDelete = conn1.prepareStatement(deleteOld);
                                psDelete.setInt(1, id);
                                int deleted = psDelete.executeUpdate();
                                System.out.println("Deleted old row: " + deleted);

                               
                                String insertNew = """
                                        INSERT INTO sections
                                        (id, course_code, instructor_user_name, day_time, room, capacity, semester, year, section)
                                        VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
                                    """;
                                PreparedStatement psInsert = conn1.prepareStatement(insertNew);
                                psInsert.setInt(1, id);
                                psInsert.setString(2, courseCode);
                                psInsert.setString(3, instructr);      // updated instructor
                                psInsert.setString(4, dayTime);
                                psInsert.setString(5, room);
                                psInsert.setInt(6, c_seats1);          // updated capacity
                                psInsert.setString(7, semester);
                                psInsert.setInt(8, year);
                                psInsert.setString(9, section);

                                int inserted = psInsert.executeUpdate();
                                System.out.println("Inserted new row: " + inserted);
                            } else {
                                System.out.println("⚠ No matching section found for " + c_code + " " + c_section);
                            }

                            c.setTitle(c_title);
                            c.setCredits(c_credit1);
                            c.setCourse_description(c_desc);
                            c.setQuiz(w_quiz1);
                            c.setAssignment(w_assignment1);
                            c.setMidsem(w_mid1);
                            c.setEndsem(w_end1);
                            c.setGroup_project(w_group1);
                            c.setSeats(c_seats1);
                            c.setSection(c_section);


                            if (rowsUpdated > 0) {
                                // FIXED: add_frame -> panel
                                JOptionPane.showMessageDialog(panel, "Course details updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    
                            } else {
                                // FIXED: add_frame -> panel
                                JOptionPane.showMessageDialog(panel, "No course found with that code!", "Error", JOptionPane.ERROR_MESSAGE);
                            }

                        } catch (SQLException ex) {
                            // FIXED: add_frame -> panel
                            JOptionPane.showMessageDialog(panel, "Database error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                        }

              

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(panel, "Enter valid numeric values!", "Error", JOptionPane.ERROR_MESSAGE);
            }


        });

        return mainPanel;
    }

    public void clearFields() {
        course_codeField.setText("");
        course_title1.setText("");
        course_instructor1.setText("");
        weightageQuiz1.setText("");
        weightageMidsem1.setText("");
        weightageEndsem1.setText("");
        weightageAssignment1.setText("");
        weightageGroupproject1.setText("");
        credits1.setText("");
        courseDesc1.setText("");
        seats1.setText("");
        sections1.setText("");
    }
    public JPanel getPanel() {
        return panel;
    }
}




// right now i can't change the instructor!!!





// public class EditCourse {
//     private JPanel panel;
//     private MainFrame mainFrame;
//     public EditCourse(MainFrame mainFrame){
//         this.mainFrame = mainFrame;
//         this.panel = createPanel();
//     }
//     public void editcourse() {
//         JFrame searchFrame = new JFrame("Edit Course");
//         searchFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//         searchFrame.setSize(400, 150);
//         searchFrame.setLayout(new BorderLayout(10, 10));

//         JPanel inputPanel = new JPanel(new GridLayout(2, 2, 10, 10));
//         JLabel course_code = new JLabel("Enter Course Code to edit: ");
//         JTextField course_code1 = new JTextField();
//         JButton searchButton = new JButton("Search Course");

//         inputPanel.add(course_code);
//         inputPanel.add(course_code1);
//         inputPanel.add(new JLabel()); // empty cell
//         inputPanel.add(searchButton);

//         searchFrame.add(inputPanel, BorderLayout.CENTER);
//         searchFrame.setLocationRelativeTo(null);
//         searchFrame.setVisible(true);
//         // JLabel course_code = new JLabel("Enter Course Code to edit: ");
//         // JTextField course_code1 = new JTextField();
//         // JButton searchButton = new JButton("Search Course ");

//         searchButton.addActionListener(e -> {
//             String code11 = course_code1.getText();
//             ArrayList<Course> list = (ArrayList<Course>) Main.list_of_courses;
//             boolean found = false;

//             for (Course c : list) {
//                 if (c.getCode().equals(code11)) {
//                     found = true;
//                     String oldSection = c.getSection();

//                     JFrame add_frame = new JFrame("Edit Course Page");
//                     add_frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

//                     JPanel mainpanel = new JPanel(new BorderLayout(10, 10));
//                     JPanel panel = new JPanel(new GridLayout(12, 2, 10, 10));

//                     JLabel course_title = new JLabel("Enter Course Title: ");
//                     JTextField course_title1 = new JTextField(c.getTitle());

//                     JLabel course_codeLabel = new JLabel("Enter Course Code: ");
//                     JTextField course_codeField = new JTextField(c.getCode());

//                     JLabel course_instructor = new JLabel("Enter Course Instructor's ID: ");
//                     JTextField course_instructor1 = new JTextField(c.getInstructor());

//                     JLabel weightageQuiz = new JLabel("Weightage of Quiz: ");
//                     JTextField weightageQuiz1 = new JTextField(String.valueOf(c.getQuiz()));

//                     JLabel weightageMidsem = new JLabel("Weightage of Mid Sem: ");
//                     JTextField weightageMidsem1 = new JTextField(String.valueOf(c.getMidsem()));

//                     JLabel weightageEndsem = new JLabel("Weightage of End Sem: ");
//                     JTextField weightageEndsem1 = new JTextField(String.valueOf(c.getEndsem()));

//                     JLabel weightageAssignment = new JLabel("Weightage of Assignment: ");
//                     JTextField weightageAssignment1 = new JTextField(String.valueOf(c.getAssignment()));

//                     JLabel weightageGroupproject = new JLabel("Weightage of Group Project: ");
//                     JTextField weightageGroupproject1 = new JTextField(String.valueOf(c.getGroup_project()));

//                     JLabel credits = new JLabel("Enter Credits: ");
//                     JTextField credits1 = new JTextField(String.valueOf(c.getCredits()));

//                     JLabel courseDesc = new JLabel("Enter Course Description: ");
//                     JTextArea courseDesc1 = new JTextArea(3, 20);
//                     courseDesc1.setText(c.getCourse_description());
//                     courseDesc1.setLineWrap(true);
//                     courseDesc1.setWrapStyleWord(true);
//                     JScrollPane descriptionpane = new JScrollPane(courseDesc1);

//                     JLabel seats = new JLabel("Enter Course Capacity: ");
//                     JTextField seats1 = new JTextField(String.valueOf(c.getseats()));

//                     JLabel sections = new JLabel("Enter Section: ");
//                     JTextField sections1 = new JTextField(c.getSection()); //////

//                     JButton saveButton = new JButton("Save Changes");

//                     panel.add(course_title); panel.add(course_title1);
//                     panel.add(course_codeLabel); panel.add(course_codeField);
//                     panel.add(course_instructor); panel.add(course_instructor1);
//                     panel.add(weightageQuiz); panel.add(weightageQuiz1);
//                     panel.add(weightageMidsem); panel.add(weightageMidsem1);
//                     panel.add(weightageEndsem); panel.add(weightageEndsem1);
//                     panel.add(weightageAssignment); panel.add(weightageAssignment1);
//                     panel.add(weightageGroupproject); panel.add(weightageGroupproject1);
//                     panel.add(credits); panel.add(credits1);
//                     panel.add(seats); panel.add(seats1);
//                     panel.add(courseDesc); panel.add(descriptionpane);
//                     panel.add(sections); panel.add(sections1);

//                     mainpanel.add(panel, BorderLayout.CENTER);
//                     mainpanel.add(saveButton, BorderLayout.SOUTH);

//                     add_frame.add(mainpanel);
//                     add_frame.pack();
//                     add_frame.setLocationRelativeTo(null);
//                     add_frame.setVisible(true);

//                     // fixed lambda name here
//                     saveButton.addActionListener(ev -> {
//                         String c_code = course_codeField.getText();
//                         String instructr = course_instructor1.getText();
//                         String c_title = course_title1.getText();
//                         String c_desc = courseDesc1.getText();
//                         String w_quiz = weightageQuiz1.getText();
//                         String w_assignment = weightageAssignment1.getText();
//                         String w_mid = weightageMidsem1.getText();
//                         String w_end = weightageEndsem1.getText();
//                         String w_group = weightageGroupproject1.getText();
//                         String c_credit = credits1.getText();
//                         String c_seats = seats1.getText();
//                         String c_section = sections1.getText();

//                         if (!c_code.equals(c.getCode()) || !c_section.equals(oldSection)) {
//                             JOptionPane.showMessageDialog(add_frame, "Warning!! Can't change course code or section", "Error", JOptionPane.ERROR_MESSAGE);
//                             return;
//                         }

//                         if (c_code.isEmpty() || instructr.isEmpty() || c_desc.isEmpty() || c_title.isEmpty() ||
//                             w_quiz.isEmpty() || w_assignment.isEmpty() || w_mid.isEmpty() || w_end.isEmpty() ||
//                             w_group.isEmpty() || c_credit.isEmpty() || c_seats.isEmpty()) {
//                             JOptionPane.showMessageDialog(add_frame, "Fill all the details!!", "Error", JOptionPane.ERROR_MESSAGE);
//                             return;
//                         }

//                         try {
//                             int c_credit1 = Integer.parseInt(c_credit);
//                             int c_seats1 = Integer.parseInt(c_seats);
//                             int w_assignment1 = Integer.parseInt(w_assignment);
//                             int w_end1 = Integer.parseInt(w_end);
//                             int w_group1 = Integer.parseInt(w_group);
//                             int w_mid1 = Integer.parseInt(w_mid);
//                             int w_quiz1 = Integer.parseInt(w_quiz);
//                             //String c_sec= c_section;
//                             //String instruc = instructr;

//                             if (c_credit1 <= 0 || c_seats1 <= 0 || w_assignment1 < 0 || w_end1 < 0 || w_group1 < 0 || w_mid1 < 0 || w_quiz1 < 0 ||
//                                 w_assignment1 + w_end1 + w_group1 + w_mid1 + w_quiz1 != 100) {
//                                 JOptionPane.showMessageDialog(add_frame, "Invalid weights! Sum must be 100.", "Error", JOptionPane.ERROR_MESSAGE);
//                                 return;
//                             }
//                             boolean instructorExists = false;
//                             for (Instructor inst : Main.list_of_instructors) {
//                                 if (inst.getid().equals(instructr)) {
//                                     instructorExists = true;
//                                     break;
//                                 }
//                             }

//                             if (!instructorExists) {
//                                 JOptionPane.showMessageDialog(add_frame, "Instructor with ID '" + instructr + "' does not exist!", "Error", JOptionPane.ERROR_MESSAGE);
//                                 return;
//                             }

//                             try (Connection conn1 = DatabaseConnection.getConnection2()) {
//                                 String q1 = "UPDATE courses SET title = ?, credits = ?, instructor = ?, quiz = ?, assignment = ?, midsem = ?, endsem = ?, group_project = ?, course_description = ?, seats = ?, section = ? WHERE code = ?;";
//                                 PreparedStatement ps1 = conn1.prepareStatement(q1);
                                

//                                 ps1.setString(1, c_title);
//                                 ps1.setInt(2, c_credit1);
//                                 ps1.setString(3, instructr);
//                                 ps1.setInt(4, w_quiz1);
//                                 ps1.setInt(5, w_assignment1);
//                                 ps1.setInt(6, w_mid1);
//                                 ps1.setInt(7, w_end1);
//                                 ps1.setInt(8, w_group1);
//                                 ps1.setString(9, c_desc);
//                                 ps1.setInt(10, c_seats1);
//                                 ps1.setString(11, c_section); // new section
//                                 ps1.setString(12, c_code);
//                                 int rowsUpdated = ps1.executeUpdate();
//                                 try {
//                                     String update = """
//                                         UPDATE sections
//                                         SET instructor_user_name = ?, capacity = ?, section = ?
//                                         WHERE id = (
//                                             SELECT id FROM relation2 WHERE course_code = ? AND section = ?
//                                         )
//                                     """;
//                                     PreparedStatement psUpdateSections = conn1.prepareStatement(update);
//                                     psUpdateSections.setString(1, instructr);
//                                     psUpdateSections.setInt(2, c_seats1);
//                                     psUpdateSections.setString(3, c_section);
//                                     psUpdateSections.setString(4, c_code.trim());
//                                     psUpdateSections.setString(5, oldSection.trim());
//                                     int rows = psUpdateSections.executeUpdate();
//                                     System.out.println("Rows updated in sections: " + rows);
//                                 } catch (SQLException ex) {
//                                     JOptionPane.showMessageDialog(add_frame, "Error updating sections table: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
//                                 }
//                                 if(!instructr.equals(c.getInstructor())){
//                                     String oldInstructorId = c.getInstructor();
//                                     Instructor jkc = c.edit_profinfo();
//                                     //String oldInstructorId = jkc.getUsername();
//                                     jkc.remove_course(c_code);
//                                     jkc.remove_section(c_section);
                                    
//                                     for (Instructor inst : Main.list_of_instructors) {
//                                         if (inst.getid().equals(instructr)) {
//                                             inst.add_course(c_code);
//                                             inst.add_section(c_section);
//                                             c.replace_prof(inst);
//                                             break;
//                                         }
//                                     }
                                    
                                    
//                                     // Database updates - only when instructor changed
//                                     try {
//                                         // Delete old instructor-course mapping
//                                         String del = "DELETE FROM subjectsxname_instructor WHERE user_name = ? AND code = ?";
//                                         PreparedStatement psDelete = conn1.prepareStatement(del);
//                                         psDelete.setString(1, oldInstructorId);
//                                         System.out.println(oldInstructorId + c_code);
//                                         psDelete.setString(2, c_code);
//                                         psDelete.executeUpdate();
                                        
//                                         // Insert new instructor-course mapping
//                                         String insertSubjectQuery = "INSERT INTO subjectsxname_instructor (user_name, code) VALUES (?, ?)";
//                                         PreparedStatement psInsert = conn1.prepareStatement(insertSubjectQuery);
//                                         psInsert.setString(1, instructr);
//                                         psInsert.setString(2, c_code);
//                                         psInsert.executeUpdate();
                                        
//                                         // Update sections table

//                                         // String update = "UPDATE sections SET instructor_user_name = ?, capacity = ?, section = ? WHERE course_code = ? AND section = ? AND instructor_user_name = ?";
//                                         // PreparedStatement psUpdateSections = conn1.prepareStatement(update);
//                                         // //psUpdateSections.setString(1, instructr);
//                                         // psUpdateSections.setString(1, instructr);
//                                         // psUpdateSections.setInt(2, c_seats1);
//                                         // psUpdateSections.setString(3, c_section);
//                                         // psUpdateSections.setString(4, c_code.trim());
//                                         // psUpdateSections.setString(5, oldSection.trim());
//                                         // psUpdateSections.setString(6, oldInstructorId.trim());
                                        
//                                         // String update = """
//                                         //     UPDATE sections
//                                         //     SET instructor_user_name = ?, capacity = ?, section = ?
//                                         //     WHERE id = (
//                                         //         SELECT id FROM relation2 WHERE course_code = ? AND section = ?
//                                         //     )
//                                         // """;
//                                         // System.out.println("print shit");
//                                         // PreparedStatement psUpdateSections = conn1.prepareStatement(update);
//                                         // psUpdateSections.setString(1, instructr);
//                                         // psUpdateSections.setInt(2, c_seats1);
//                                         // psUpdateSections.setString(3, c_section);
//                                         // psUpdateSections.setString(4, c_code.trim());
//                                         // psUpdateSections.setString(5, c_section);
//                                         // System.out.println(oldSection);
//                                         // int rows = psUpdateSections.executeUpdate();

//                                         // System.out.println("Rows updated: " + rows);

//                                         c.setCourse_description(c_desc);
//                                         c.setCredits(c_credit1);
//                                         c.setGroup_project(w_group1);
//                                         c.setQuiz(w_quiz1);
//                                         c.setAssignment(w_assignment1);
//                                         c.setGroup_project(w_end1);
//                                         c.setMidsem(w_mid1);
//                                         c.setSeats(c_seats1);
//                                         c.setEndsem(w_end1);
//                                        // c.setProf(instructr); done above in replace
//                                         c.setSection(c_section);

//                                        // psUpdateSections.setInt(5, );
                                    
                                        
                                        
//                                     } catch (SQLException ex) {
//                                         JOptionPane.showMessageDialog(add_frame, "Error updating instructor mappings: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
//                                         return;
//                                     }
//                                 }

// //////////////////////////////////////////
//                     // Step 1. Fetch full old row details first
//                     String fetchOld = """
//                         SELECT id, course_code, day_time, room, semester, year, section
//                         FROM sections
//                         WHERE course_code = ? AND section = ?
//                     """;
//                     PreparedStatement psFetchOld = conn1.prepareStatement(fetchOld);
//                     psFetchOld.setString(1, c_code.trim());
//                     psFetchOld.setString(2, c_section.trim());
//                     ResultSet rsOld = psFetchOld.executeQuery();

//                     if (rsOld.next()) {
//                         int id = rsOld.getInt("id");
//                         String courseCode = rsOld.getString("course_code");
//                         String dayTime = rsOld.getString("day_time");
//                         String room = rsOld.getString("room");
//                         String semester = rsOld.getString("semester");
//                         int year = rsOld.getInt("year");
//                         String section = rsOld.getString("section");

//                         // Step 2. Delete that exact row
//                         String deleteOld = "DELETE FROM sections WHERE id = ?";
//                         PreparedStatement psDelete = conn1.prepareStatement(deleteOld);
//                         psDelete.setInt(1, id);
//                         int deleted = psDelete.executeUpdate();
//                         System.out.println("Deleted old row: " + deleted);

//                         // Step 3. Insert a new row with same id and all old values, except updated instructor & capacity
//                         String insertNew = """
//                             INSERT INTO sections
//                             (id, course_code, instructor_user_name, day_time, room, capacity, semester, year, section)
//                             VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
//                         """;
//                         PreparedStatement psInsert = conn1.prepareStatement(insertNew);
//                         psInsert.setInt(1, id);
//                         psInsert.setString(2, courseCode);
//                         psInsert.setString(3, instructr);        // updated instructor
//                         psInsert.setString(4, dayTime);
//                         psInsert.setString(5, room);
//                         psInsert.setInt(6, c_seats1);            // updated capacity
//                         psInsert.setString(7, semester);
//                         psInsert.setInt(8, year);
//                         psInsert.setString(9, section);

//                         int inserted = psInsert.executeUpdate();
//                         System.out.println("Inserted new row: " + inserted);
//                     } else {
//                         System.out.println("⚠ No matching section found for " + c_code + " " + c_section);
//                     }



//                                 // if(!oldSection.equals(c_section)){
//                                 //             String updateRelation = "UPDATE relation2 SET section = ? WHERE course_code = ? AND section = ?";
//                                 //             PreparedStatement psUpdateRelation = conn1.prepareStatement(updateRelation);
//                                 //             psUpdateRelation.setString(1, c_section);
//                                 //             psUpdateRelation.setString(2, c_code.trim());
//                                 //             psUpdateRelation.setString(3, oldSection.trim());
//                                 //             int relRows = psUpdateRelation.executeUpdate();
//                                 //             System.out.println("Rows updated in relation2: " + relRows);
//                                 // }
//                                 //         String update = """
//                                 //             UPDATE sections
//                                 //             SET instructor_user_name = ?, capacity = ?, section = ?
//                                 //             WHERE id = (
//                                 //                 SELECT id FROM relation2 WHERE course_code = ? AND section = ?
//                                 //             )
//                                 //         """;
//                                 //         System.out.println("print shit");
//                                 //         PreparedStatement psUpdateSections = conn1.prepareStatement(update);
//                                 //         psUpdateSections.setString(1, instructr);
//                                 //         psUpdateSections.setInt(2, c_seats1);
//                                 //         psUpdateSections.setString(3, c_section);
//                                 //         psUpdateSections.setString(4, c_code.trim());
//                                 //         psUpdateSections.setString(5, c_section);
//                                 //         System.out.println(oldSection);
//                                 //         int rows = psUpdateSections.executeUpdate();

//                                 //         System.out.println("Rows updated: " + rows);
//                                 // if(!oldSection.equals(c_section)) {
//                                 //     // First, update relation2 table
//                                 //     String updateRelation = "UPDATE relation2 SET section = ? WHERE course_code = ? AND section = ?";
//                                 //     PreparedStatement psUpdateRelation = conn1.prepareStatement(updateRelation);
//                                 //     psUpdateRelation.setString(1, c_section);
//                                 //     psUpdateRelation.setString(2, c_code.trim());
//                                 //     psUpdateRelation.setString(3, oldSection.trim());
//                                 //     int relRows = psUpdateRelation.executeUpdate();
//                                 //     System.out.println("Rows updated in relation2: " + relRows);

//                                 //     // Then, delete the old section record
//                                 //     String deleteSection = "DELETE FROM sections WHERE course_code = ? AND section = ?";
//                                 //     PreparedStatement psDeleteSection = conn1.prepareStatement(deleteSection);
//                                 //     psDeleteSection.setString(1, c_code.trim());
//                                 //     psDeleteSection.setString(2, oldSection.trim());
//                                 //     int delRows = psDeleteSection.executeUpdate();
//                                 //     System.out.println("Rows deleted in sections: " + delRows);

//                                 //     // Finally, insert a new row with the new section
//                                 //     String insertSection = """
//                                 //         INSERT INTO sections (course_code, section, instructor_user_name, capacity)
//                                 //         VALUES (?, ?, ?, ?)
//                                 //     """;
//                                 //     PreparedStatement psInsertSection = conn1.prepareStatement(insertSection);
//                                 //     psInsertSection.setString(1, c_code.trim());
//                                 //     psInsertSection.setString(2, c_section);
//                                 //     psInsertSection.setString(3, instructr);
//                                 //     psInsertSection.setInt(4, c_seats1);
//                                 //     int insRows = psInsertSection.executeUpdate();
//                                 //     System.out.println("Rows inserted in sections: " + insRows);
//                                 // }

    

//                                 c.setTitle(c_title);
//                                 c.setCredits(c_credit1);
//                                 c.setCourse_description(c_desc);
//                                 c.setQuiz(w_quiz1);
//                                 c.setAssignment(w_assignment1);
//                                 c.setMidsem(w_mid1);
//                                 c.setEndsem(w_end1);
//                                 c.setGroup_project(w_group1);
//                                 c.setSeats(c_seats1);
//                                 c.setSection(c_section);


//                                 if (rowsUpdated > 0) {
//                                     JOptionPane.showMessageDialog(add_frame, "Course details updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            
//                                 } else {
//                                     JOptionPane.showMessageDialog(add_frame, "No course found with that code!", "Error", JOptionPane.ERROR_MESSAGE);
//                                 }

//                             } catch (SQLException ex) {
//                                 JOptionPane.showMessageDialog(add_frame, "Database error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
//                             }

//                         } catch (NumberFormatException ex) {
//                             JOptionPane.showMessageDialog(add_frame, "Enter valid numeric values!", "Error", JOptionPane.ERROR_MESSAGE);
//                         }
//                     });

//                     break; 
//                 }
//             }

//             if (!found) {
//                 JOptionPane.showMessageDialog(null, "Course not found!", "Error", JOptionPane.ERROR_MESSAGE);
//             }
//         });
//     }
// }
