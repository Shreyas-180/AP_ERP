
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
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class AddUsers {
    public void addusers(){
        Boolean exist = false;
        JFrame add_frame = new JFrame("Add Users Page");
        JLabel user_type = new JLabel("Enter User type: ");

        String[] userTypes = {"Student", "Instructor", "Admin"};
        JComboBox<String> userTypeComboBox = new JComboBox<>(userTypes);

        JLabel username_text = new JLabel("Enter Username: ");
        JTextField username_enter = new JTextField();
        JLabel pass_text = new JLabel("Enter password: ");
        JLabel pass_text1 = new JLabel("Enter password again: ");
        JPasswordField pass_enter = new JPasswordField();
        JPasswordField pass_enter2 = new JPasswordField(); // to check password matching when creating user
        JButton add = new JButton("Add User");

        JLabel nameLabel = new JLabel("Enter Name:");
        JTextField nameField = new JTextField();
        JLabel rollNoLabel = new JLabel("Enter Roll No:");
        JTextField rollNoField = new JTextField();
        JLabel programLabel = new JLabel("Enter Program:");
        JTextField programField = new JTextField();
        JLabel yearLabel = new JLabel("Enter Year:");
        JTextField yearField = new JTextField();
        JLabel err = new JLabel();
        err.setBounds(50, 340, 400, 25);
        

        //JLabel failed_login = new JLabel("Wrong passwpord or user name! ");
        user_type.setBounds(50, 20, 120, 25);
        userTypeComboBox.setBounds(180, 20, 150, 25);
        add_frame.add(user_type);
        add_frame.add(userTypeComboBox);
        //failed_login.setBounds(180, 50, 200, 25);
        pass_enter.setEchoChar('x');
        username_text.setBounds(50, 50, 120, 25);
        
        pass_text.setBounds(50, 90, 120, 25);
        pass_text1.setBounds(50, 120, 120, 25);
        add_frame.setBounds(0, 0, 500, 400);
        add_frame.setLayout(null);
        add_frame.setDefaultCloseOperation(add_frame.EXIT_ON_CLOSE);
        username_enter.setBounds(180, 50, 150, 25);
        pass_enter.setBounds(180, 90, 150, 25);
        pass_enter2.setBounds(180, 120, 150, 25);
        add.setBounds(150, 140, 100, 30);
        //failed_login.setBounds(150, 160, 100, 30);
        // --- ADD THESE MISSING LINES ---
        add_frame.add(username_text);
        add_frame.add(username_enter);
        add_frame.add(pass_text);
        add_frame.add(pass_enter);
        add_frame.add(pass_text1);
        add_frame.add(pass_enter2);
        add_frame.add(add);

        // --- SNIPPET 2: POSITION NEW FIELDS AND MOVE BUTTON ---

     // Position and add the student-specific fields
        int studentFieldsY = 150; // Starting Y-position for the new fields
        nameLabel.setBounds(50, studentFieldsY, 120, 25);
        nameField.setBounds(180, studentFieldsY, 150, 25);
        studentFieldsY += 30;
        rollNoLabel.setBounds(50, studentFieldsY, 120, 25);
        rollNoField.setBounds(180, studentFieldsY, 150, 25);
        studentFieldsY += 30;
        programLabel.setBounds(50, studentFieldsY, 120, 25);
        programField.setBounds(180, studentFieldsY, 150, 25);
        studentFieldsY += 30;
        yearLabel.setBounds(50, studentFieldsY, 120, 25);
        yearField.setBounds(180, studentFieldsY, 150, 25);

        add_frame.add(nameLabel);
        add_frame.add(nameField);
        add_frame.add(rollNoLabel);
        add_frame.add(rollNoField);
        add_frame.add(programLabel);
        add_frame.add(programField);
        add_frame.add(yearLabel);
        add_frame.add(yearField);
        JLabel success = new JLabel("Succes");
        success.setVisible(false);
        add.setBounds(150, studentFieldsY + 40, 100, 30);

        add_frame.setVisible(true);
        userTypeComboBox.addActionListener(e1 -> {
                    String selected = (String) userTypeComboBox.getSelectedItem();

                    // Determine which user type is selected
                    boolean isStudent  = "Student".equals(selected);
                    boolean isInstructor = "Instructor".equals(selected);
                    boolean isAdmin = "Admin".equals(selected);
                    
                    // In your UI, Name is required for all types (Student, Instructor, Admin)
                    // so we can leave it visible.
                    // nameLabel.setVisible(true);
                    // nameField.setVisible(true);

                    // These fields are ONLY visible if the user is a Student
                    rollNoLabel.setVisible(isStudent);
                    rollNoField.setVisible(isStudent);
                    programLabel.setVisible(isStudent);
                    programField.setVisible(isStudent);
                    yearLabel.setVisible(isStudent);
                    yearField.setVisible(isStudent);
                });
        add.addActionListener(e ->{
            err.setText("");
            try (Connection conn = DatabaseConnection.getConnection()) {
                String user_name = username_enter.getText();
                char[] password = pass_enter.getPassword();
                char[] password1 = pass_enter2.getPassword();
                String passw =  new String(password);
                String passw1 =  new String(password1);
                String selectedUserType = (String) userTypeComboBox.getSelectedItem();
                if(passw.equals(passw1)){

                    String query = "SELECT * FROM username_password WHERE user_name = ?;";
                
                    PreparedStatement ps = conn.prepareStatement(query);
                    ps.setString(1, user_name);
                    ResultSet check = ps.executeQuery();
                    if(check.next() != false){
                        JLabel tt1 = new JLabel("User name already taken!!!");
                        tt1.setBounds(50, 120, 400, 20);
                        tt1.setName("detailLabel");
                        add_frame.add(tt1);
                        add_frame.revalidate();
                        add_frame.repaint();
                    }
                    else{
                        String query1 = "INSERT INTO username_password VALUES(?,?,?)";
                        PreparedStatement ps11 = conn.prepareStatement(query1);
                        ps11.setString(1, user_name);
                        ps11.setString(2, passw);
                        ps11.setString(3, selectedUserType);
                        int rowsAffected = ps11.executeUpdate();
                        // adding this in relation 1 also
                        try (Connection conn1 = DatabaseConnection.getConnection2()) {
                            String query2 = "INSERT INTO relation1 VALUES(?,?)";
                            PreparedStatement ps12 = conn1.prepareStatement(query2);
                            ps12.setString(1, user_name);
                            ps12.setString(2,selectedUserType);
                            rowsAffected = ps12.executeUpdate();
                        
                        }catch(SQLException e2){
                            e2.printStackTrace();
                        }
                        if ("Student".equals(selectedUserType)) {
                        
                            String name = nameField.getText();
                            int rollNo;
                            String program = programField.getText();
                            int year; 
                            if (name.isBlank() || rollNoField.getText().isBlank() || program.isBlank() || yearField.getText().isBlank()) {
                                err.setText("Details not filled");
                                return;
                            }
                            try {
                                rollNo = Integer.parseInt(rollNoField.getText());
                                year = Integer.parseInt(yearField.getText());
                                if (year <= 0 || rollNo <= 0) {
                                    err.setText("Roll number and year must be positive numbers.");
                                    return;
                                }
                            } catch (NumberFormatException nfe) {
                                err.setText("Roll number and year must be numbers.");
                                return;
                            }
                            try (Connection conn1 = DatabaseConnection.getConnection2()) {
                                String studentQuery = "INSERT INTO students VALUES (?, ?, ?, ?, ?);";
                                
                                PreparedStatement ps13 = conn1.prepareStatement(studentQuery);
                                ps13.setString(1, user_name);
                                ps13.setString(2, name);
                                ps13.setInt(3, rollNo);
                                ps13.setString(4, program);
                                ps13.setInt(5, year);
                                ps13.executeUpdate();
                            } catch(SQLException e3){
                                e3.printStackTrace();
                            }
                            success.setVisible(true);
                        }
                        else if("Instructor".equals(selectedUserType)){
                        // might want to take department as an input too.

                            String name = nameField.getText();
                            if(name.isBlank()){
                                err.setText("Enter a name!!!");
                                return;
                            }
                            Instructor ins = new Instructor(name, user_name);
                            
                            Main.list_of_instructors.add(ins);
                            // need to start from here, need to add admin power to add subject of user.


                        }

                    }
                } 
                else {
                    JLabel tt = new JLabel("Password is not the same!!!");
                    tt.setBounds(50, 120, 400, 20);
                    tt.setName("detailLabel");
                    add_frame.add(tt);
                    add_frame.revalidate();
                    add_frame.repaint();
                }
            } catch(SQLException e1){
                e1.printStackTrace();
            }
        });
    }
}
