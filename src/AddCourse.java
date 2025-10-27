
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.security.InvalidParameterException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
public class AddCourse{
    public void addcourse(){
        JFrame add_frame = new JFrame("Add Course Page");

        add_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainpanel = new JPanel();
        mainpanel.setLayout(new BorderLayout(10,10));

        JPanel panel = new JPanel();

        panel.setLayout(new GridLayout(12,2,10,10));
        JLabel course_title = new JLabel("Enter Course Title: ");
        JTextField course_title1 = new JTextField();

        JLabel course_code = new JLabel("Enter Course Code: ");
        JTextField course_code1 = new JTextField();

        JLabel course_instructor = new JLabel("Enter COurse instructor's id: ");
        JTextField course_instructor1 = new JTextField();

        JLabel weightageQuiz = new JLabel("Weightage of Quiz: ");
        JTextField weightageQuiz1 = new JTextField();

        JLabel weightageMidsem = new JLabel("Weightage of Mid Sem: ");
        JTextField weightageMidsem1 = new JTextField();

        JLabel weightageEndsem = new JLabel("Weightage of End Sem: ");
        JTextField weightageEndsem1 = new JTextField();

        JLabel weightageAssignment = new JLabel("Weightage of Assignment: ");
        JTextField weightageAssignment1 = new JTextField();

        JLabel weightageGroupproject = new JLabel("Weightage of Group Project: ");
        JTextField weightageGroupproject1 = new JTextField();

        JLabel credits = new JLabel("Enter Credits: ");
        JTextField credits1 = new JTextField();

        JLabel courseDesc = new JLabel("Enter Course Description: ");
        JTextArea courseDesc1 = new JTextArea(3, 20);

        JLabel seats = new JLabel("Enter Course Capacity: ");
        JTextField seats1 = new JTextField();

        JLabel sections = new JLabel("Enter Section: ");
        JTextField sections1 = new JTextField();

        courseDesc1.setLineWrap(true); // Words will wrap to the next line
        courseDesc1.setWrapStyleWord(true);

        JScrollPane descriptionpane = new JScrollPane(courseDesc1);


        JButton addcourseButton= new JButton("Add Course ");
        panel.add(addcourseButton);
        panel.add(course_title);
        panel.add(course_title1);

        panel.add(course_code);
        panel.add(course_code1);

        panel.add(course_instructor);
        panel.add(course_instructor1);

        panel.add(weightageQuiz);
        panel.add(weightageQuiz1);

        panel.add(weightageMidsem);
        panel.add(weightageMidsem1);

        panel.add(weightageEndsem);
        panel.add(weightageEndsem1);

        panel.add(weightageAssignment);
        panel.add(weightageAssignment1);

        panel.add(weightageGroupproject);
        panel.add(weightageGroupproject1);

        panel.add(credits);
        panel.add(credits1);

        panel.add(seats);
        panel.add(seats1);

        panel.add(courseDesc);
        panel.add(descriptionpane);

        panel.add(sections);
        panel.add(sections1);

        mainpanel.add(panel, BorderLayout.CENTER);
        mainpanel.add(addcourseButton, BorderLayout.SOUTH);

        add_frame.add(mainpanel);
        add_frame.pack();
        add_frame.setLocationRelativeTo(null);
        add_frame.setVisible(true);

        addcourseButton.addActionListener(e->{
            String instructr = course_instructor1.getText();
            String c_code = course_code1.getText();
            String c_title = course_title1.getText();
            String c_desc = courseDesc1.getText();
            String w_quiz = weightageQuiz1.getText();
            String w_assignment = weightageAssignment1.getText();
            String w_mid = weightageMidsem1.getText();
            String w_end = weightageEndsem1.getText();
            String w_group = weightageGroupproject1.getText();
            String c_credit = credits1.getText() ;
            String c_seats = seats1.getText();
            String c_section = sections1.getText();

            int c_credit1;
            int c_seats1;
            int w_assignment1;
            int w_end1;
            int w_group1;
            int w_mid1;
            int w_quiz1;
            if(c_code.isEmpty() || instructr.isEmpty() || c_desc.isEmpty() || c_title.isEmpty() || w_quiz.isEmpty() || w_assignment.isEmpty() || w_mid.isEmpty() || w_end.isEmpty() || w_group.isEmpty() || c_credit.isEmpty() || c_seats.isEmpty()){
                JOptionPane.showMessageDialog(add_frame, "Fill all the details !!", "Empty Field", JOptionPane.ERROR_MESSAGE);
                return;
            }
            try{
                c_credit1 = Integer.parseInt(c_credit);
                c_seats1 = Integer.parseInt(c_seats);
                w_assignment1 = Integer.parseInt(w_assignment);
                w_end1 = Integer.parseInt(w_end);
                w_group1 = Integer.parseInt(w_group);
                w_mid1 = Integer.parseInt(w_mid);
                w_quiz1 = Integer.parseInt(w_quiz);
                if(c_credit1 <= 0 || c_seats1 <= 0 || w_assignment1 < 0 || w_end1 < 0 || w_group1 < 0 || w_mid1 < 0 || w_quiz1 < 0 || w_assignment1+w_end1+w_group1+w_mid1+w_quiz1 != 100){
                    JOptionPane.showMessageDialog(add_frame, "Fill all the details sensibly(non negative and sum of components should be 100)!!", "Empty Field", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            
                try(Connection conn1 = DatabaseConnection.getConnection2()){
                    String q1 =  "SELECT* FROM courses WHERE code = ?;";
                    PreparedStatement ps1 = conn1.prepareStatement(q1);
                    ps1.setString(1, c_code);
                    ResultSet rs = ps1.executeQuery();
                    if(rs.next()){
                        JOptionPane.showMessageDialog(add_frame, "Course with this code already exists !!!", "Error", JOptionPane.ERROR_MESSAGE );
                    }
                    else{
                        try(Connection conn3 = DatabaseConnection.getConnection2()){
                            q1 = "SELECT* FROM relation1 WHERE user_name = ?;";
                            PreparedStatement ps3 = conn1.prepareStatement(q1);
                            ps3.setString(1, instructr);
                            ResultSet rs1 = ps3.executeQuery();
                            if(rs1.next()==true && rs1.getString("designation").equals("Instructor")){
                                String query = "INSERT INTO courses VALUES (?,?,?,?,?,?,?,?,?,?,?,?);";
                                PreparedStatement ps = conn1.prepareStatement(query);
                                ps.setString(1,c_code);
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

                                query = "INSERT INTO subjectsxname_instructor VALUES(?,?);";
                                PreparedStatement ps2 = conn1.prepareStatement(query);
                                ps2.setString(1, instructr);
                                ps2.setString(2, c_code);
                                ps2.executeUpdate();
                                // q1 = "SELECT* FROM instructor_name_username WHERE user_name = ?;";
                                // PreparedStatement ps4 = conn1.prepareStatement(q1);
                                // ps4.setString(1, instructr);
                                // ResultSet rs2 = ps4.executeQuery();
                                // if(rs2.next() == true){
                                //     q1 = rs2.getString("name");
                                // }

                                Instructor i1 = null;
                                //Instructor prof = new Instructor(q1);
                                for (Instructor prof: Main.list_of_instructors){
                                    if(prof.get_name_id().equals(instructr)){
                                        prof.add_course(c_code);
                                        prof.add_section(c_section);
                                        i1 = prof;
                                        break;
                                    }
                                }
                                Course new_course = new Course(c_code, c_title, c_credit1/*  instructr*/, w_quiz1, w_assignment1, w_mid1, w_end1, w_group1, c_desc, c_seats1, c_section, i1);

                                
                                JOptionPane.showMessageDialog(add_frame, "Success !!!", "Success", JOptionPane.INFORMATION_MESSAGE );
                            }
                            else{
                                JOptionPane.showMessageDialog(add_frame, "Invalid Instructor !", "Invalid Instructor", JOptionPane.ERROR_MESSAGE );
                            }
                        }
                        catch(SQLException e1){
                            e1.printStackTrace(); 
                            JOptionPane.showMessageDialog(add_frame, "Database error: " + e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                        }
                        
                    }
                    
                }
                catch(SQLException e1){
                    e1.printStackTrace(); 
                    JOptionPane.showMessageDialog(add_frame, "Database error: " + e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
            catch(NumberFormatException nfe){
                JOptionPane.showMessageDialog(add_frame, "Please enter valid numbers for credits, seats, and weightages.", "Invalid Input", JOptionPane.ERROR_MESSAGE );
                // 1. Parent frame (so the pop-up appears over it)
                // 2. The error message
                // 3. The title of the pop-up window
                // 4. The icon to display (an error icon)
            }
           // Course(String code, String title, int credits, String instructor, int quiz, int assignment, int midsem, int endsem, int group_project, String course_description, int seats){
        
        });
    }

}
