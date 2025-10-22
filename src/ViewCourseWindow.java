import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.*;
import java.awt.*;

public class ViewCourseWindow {
    public void display(){
        String temp = "";
        // ArrayList<Course> list = Factory.factory_for_course();
        
        // JFrame frame = new JFrame("Courses: ");
        // frame.setSize(400, 300);
        // frame.setLayout(null); // using absolute positioning
        // frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // String[] colmn = {"code", "title", "instructor", "credits"};
        
        // Object[][] data = new Object[list.size()][4];
       
        // for (int i = 0; i < list.size(); i++) {
        //     Course course = list.get(i);
        //     data[i][0] = course.getCode(); 
        //     data[i][1] = course.getTitle();
        //     data[i][2] = course.getInstructor();
        //     data[i][3] = course.getCredits();
        // }
        // // ---- Table ----// gpt shi
        // JTable table = new JTable(data, colmn);
        // table.getTableHeader().setBounds(50, 30, 700, 20);
        // table.setBounds(50, 50, 700, 20 * list.size());
        // frame.add(table.getTableHeader());
        // frame.add(table);

        // // ---- Search Section (moved down below table) ----
        // int bottomY = 80 + (20 * list.size()); // Calculate Y position below table

        // JLabel check_course = new JLabel("Enter Course Code for details:");
        // check_course.setBounds(50, bottomY, 250, 25);
        // frame.add(check_course);

        // JTextField text = new JTextField();
        // text.setBounds(300, bottomY, 150, 25);
        // frame.add(text);

        // JButton search = new JButton("Search");
        // search.setBounds(470, bottomY, 100, 25); 
        // frame.add(search);
        //
        ArrayList<Course> list = Factory.factory_for_course();

        JFrame frame = new JFrame("Courses:");
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // --- Table ---
        String[] column = {"Code", "Title", "Instructor", "Credits"};
        Object[][] data = new Object[list.size()][4];
        for (int i = 0; i < list.size(); i++) {
            Course c = list.get(i);
            data[i][0] = c.getCode();
            data[i][1] = c.getTitle();
            data[i][2] = c.getInstructor();
            data[i][3] = c.getCredits();
        }

        JTable table = new JTable(data, column);
        JScrollPane scrollPane = new JScrollPane(table);
        frame.add(scrollPane, BorderLayout.CENTER);

        // --- Bottom Panel (search) ---
        JPanel bottomPanel = new JPanel();
        bottomPanel.add(new JLabel("Enter Course Code for details:"));
        JTextField text = new JTextField(10);
        bottomPanel.add(text);
        JButton search = new JButton("Search");
        bottomPanel.add(search);
        frame.add(bottomPanel, BorderLayout.SOUTH);

        // my og, tf
        // JTable table = new JTable(data,colmn);
        // table.getTableHeader().setBounds(50,30,700,20);
        // table.setBounds(50, 50, 700, 20* list.size());
        // frame.add(table.getTableHeader());
        // frame.add(table);
        // JLabel check_course = new JLabel("Enter Course Code of the course for which you want more information: ");
        // JTextField text = new JTextField();
        // check_course.setBounds(50, 50, 120, 25);
        // text.setBounds(180, 50, 150, 25);
        // JButton search = new JButton("Search");
        // search.setBounds(350, 50, 100, 25);
        // frame.add(search);

        search.addActionListener(e->{
            String code_entered = text.getText();
            int checker = -1;
            for (java.awt.Component comp : frame.getContentPane().getComponents()) {
                if (comp.getName() != null && comp.getName().equals("detailLabel")) {
                    frame.remove(comp);
                }
            }
            for(int i = 0; i < list.size(); i++){
                // if(list.get(i).getCode().equals(code_entered)){
                //     Course temp1 = list.get(i);
                //     JLabel codeLabel = new JLabel("Code: " + temp1.getCode());
                //     JLabel titleLabel = new JLabel("Title: " + temp1.getTitle());
                //     JLabel instructorLabel = new JLabel("Instructor: " + temp1.getInstructor());
                //     JLabel creditsLabel = new JLabel("Credits: " + temp1.getCredits());
                //     JLabel quizLabel = new JLabel("Quiz Weightage: " + temp1.getQuiz() + "%");
                //     JLabel assignmentLabel = new JLabel("Assignment Weightage: " + temp1.getAssignment() + "%");
                //     JLabel midsemLabel = new JLabel("Mid-Sem Weightage: " + temp1.getMidsem() + "%");
                //     JLabel endsemLabel = new JLabel("End-Sem Weightage: " + temp1.getEndsem() + "%");
                //     JLabel projectLabel = new JLabel("Group Project Weightage: " + temp1.getGroup_project() + "%");
                //     JLabel seatsLabel = new JLabel("Available Seats: " + temp1.getseats());
                //     JTextArea descriptionArea = new JTextArea("Description: " + temp1.getCourse_description());

                //     int y = 120; // start below the table
                //     int gap = 25;

                //     JLabel[] labels = { codeLabel, titleLabel, instructorLabel, creditsLabel,
                //                         quizLabel, assignmentLabel, midsemLabel,
                //                         endsemLabel, projectLabel, seatsLabel };

                //     for (JLabel label : labels) {
                //         label.setBounds(50, y, 400, 20);
                //         label.setName("detailLabel"); // mark for removal on next search
                //         frame.add(label);
                //         y += gap;
                //     }

                //     descriptionArea.setBounds(50, y, 300, 60);
                //     descriptionArea.setLineWrap(true);
                //     descriptionArea.setWrapStyleWord(true);
                //     descriptionArea.setEditable(false);
                //     descriptionArea.setName("detailLabel");
                //     frame.add(descriptionArea);

                //     frame.revalidate();
                //     frame.repaint();
                //     checker = 0;
                //     break;
                // }
                if (list.get(i).getCode().equals(code_entered)) {
                    Course temp1 = list.get(i);

                    // Build message for the popup
                    String message = String.format(
                        "Code: %s\nTitle: %s\nInstructor: %s\nCredits: %d\n\n"
                    + "Quiz Weightage: %d%%\nAssignment Weightage: %d%%\n"
                    + "Mid-Sem Weightage: %d%%\nEnd-Sem Weightage: %d%%\n"
                    + "Group Project Weightage: %d%%\nAvailable Seats: %d\n\n"
                    + "Description: %s",
                        temp1.getCode(),
                        temp1.getTitle(),
                        temp1.getInstructor(),
                        temp1.getCredits(),
                        temp1.getQuiz(),
                        temp1.getAssignment(),
                        temp1.getMidsem(),
                        temp1.getEndsem(),
                        temp1.getGroup_project(),
                        temp1.getseats(),
                        temp1.getCourse_description()
                    );

                    JOptionPane.showMessageDialog(frame, message, "Course Details", JOptionPane.INFORMATION_MESSAGE);
                    checker = 0;
                    break;
                }
            }
            if (checker == -1) {
                JOptionPane.showMessageDialog(frame, "No course with the code entered", "Error", JOptionPane.ERROR_MESSAGE);
            }

            // if(checker == -1){
            //     JLabel tt = new JLabel("No course with the code entered");
            //     tt.setBounds(50, 120, 400, 20);
            //     tt.setName("detailLabel");
            //     frame.add(tt);
            //     frame.revalidate();
            //     frame.repaint();

            // }
            
            // try (Connection conn = DatabaseConnection.getConnection2()) {
            //     String query = "SECLECT* FROM courses WHERE code = ?;";
            //     PreparedStatement ps = conn.prepareStatement(query);
            //     ps.setString(1, code_entered);
            //     ResultSet rs = ps.executeQuery();
            //     while(rs.next() != false){
            //         rs.

            //     }
            // }
            // catch(SQLException e){
                
            // }
        });
        
        frame.setVisible(true);
    }
}
