import java.awt.*;
import java.sql.*;
import javax.swing.*;

public class AddUsers {
    private MainFrame mainFrame;
    private JPanel panel;
    private JComboBox<String> userTypeComboBox;
    private JTextField username_enter, nameField, rollNoField, programField, yearField;
    private JPasswordField pass_enter, pass_enter2;
    private JLabel err;
    private JLabel rollNoLabel, programLabel, yearLabel;

    public AddUsers(MainFrame mainFrame) {
        this.mainFrame = mainFrame;

        panel = new JPanel(new BorderLayout(10, 10));

        JLabel title = new JLabel("Add Users", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(title, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridLayout(0, 2, 10, 10));

        // --- Fields ---
        JLabel user_type = new JLabel("User type:");
        String[] userTypes = {"Student", "Instructor", "Admin"};
        userTypeComboBox = new JComboBox<>(userTypes);

        JLabel username_text = new JLabel("Username:");
        username_enter = new JTextField();

        JLabel pass_text = new JLabel("Password:");
        pass_enter = new JPasswordField();

        JLabel pass_text1 = new JLabel("Re-enter Password:");
        pass_enter2 = new JPasswordField();

        JLabel nameLabel = new JLabel("Name:");
        nameField = new JTextField();

        rollNoLabel = new JLabel("Roll No:");
        rollNoField = new JTextField();

        programLabel = new JLabel("Program:");
        programField = new JTextField();

        yearLabel = new JLabel("Year:");
        yearField = new JTextField();

        err = new JLabel("", SwingConstants.CENTER);
        JLabel success = new JLabel("Success!", SwingConstants.CENTER);
        success.setVisible(false);

    
        formPanel.add(user_type);
        formPanel.add(userTypeComboBox);
        formPanel.add(username_text);
        formPanel.add(username_enter);
        formPanel.add(pass_text);
        formPanel.add(pass_enter);
        formPanel.add(pass_text1);
        formPanel.add(pass_enter2);
        formPanel.add(nameLabel);
        formPanel.add(nameField);
        formPanel.add(rollNoLabel);
        formPanel.add(rollNoField);
        formPanel.add(programLabel);
        formPanel.add(programField);
        formPanel.add(yearLabel);
        formPanel.add(yearField);

        panel.add(formPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout());
        JButton add = new JButton("Add User");
        JButton back = new JButton("Back");
        bottomPanel.add(add);
        bottomPanel.add(back);

        panel.add(err, BorderLayout.SOUTH);
        panel.add(bottomPanel, BorderLayout.PAGE_END);

        // Hide student-specific fields initially
        rollNoLabel.setVisible(false);
        rollNoField.setVisible(false);
        programLabel.setVisible(false);
        programField.setVisible(false);
        yearLabel.setVisible(false);
        yearField.setVisible(false);

        // Combo box logic
        userTypeComboBox.addActionListener(e -> {
            String selected = (String) userTypeComboBox.getSelectedItem();
            boolean isStudent = "Student".equals(selected);
            rollNoLabel.setVisible(isStudent);
            rollNoField.setVisible(isStudent);
            programLabel.setVisible(isStudent);
            programField.setVisible(isStudent);
            yearLabel.setVisible(isStudent);
            yearField.setVisible(isStudent);
            panel.revalidate();
            panel.repaint();
        });

        add.addActionListener(e -> {
            err.setText("");
            try (Connection conn = DatabaseConnection.getConnection()) {
                String user_name = username_enter.getText();
                char[] password = pass_enter.getPassword();
                char[] password1 = pass_enter2.getPassword();
                String passw = new String(password);
                String passw1 = new String(password1);
                String selectedUserType = (String) userTypeComboBox.getSelectedItem();
                if (user_name.isBlank()) {
                    JOptionPane.showMessageDialog(null, "Username cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
                    err.setText("Username cannot be empty!");
                    return;
                }

                if (passw.equals(passw1)) {
                    String query = "SELECT * FROM username_password WHERE user_name = ?;";
                    PreparedStatement ps = conn.prepareStatement(query);
                    ps.setString(1, user_name);
                    ResultSet check = ps.executeQuery();

                    if (check.next()) {
                        JOptionPane.showMessageDialog(null,"Username already taken!","Error",JOptionPane.ERROR_MESSAGE);
                        err.setText("User name already taken!");
                    } else {
                        if ("Student".equals(selectedUserType)) {
                                if (nameField.getText().isBlank() || rollNoField.getText().isBlank() || programField.getText().isBlank() || yearField.getText().isBlank()) {
                                        JOptionPane.showMessageDialog(null, "Details not filled", "Error", JOptionPane.ERROR_MESSAGE);
                                        err.setText("Details not filled");
                                        return;
                                    }
                                int rollNo = Integer.parseInt(rollNoField.getText());
                                int year = Integer.parseInt(yearField.getText());
                                if (year <= 0 || rollNo <= 0) {
                                    JOptionPane.showMessageDialog(null, "Invalid year/roll number", "Error", JOptionPane.ERROR_MESSAGE);
                                    err.setText("Invalid year/roll number");
                                    return;
                                }
                        }
                        if("Instructor".equals(selectedUserType)){
                            if (nameField.getText().isBlank()){
                                        JOptionPane.showMessageDialog(null, "Details not filled", "Error", JOptionPane.ERROR_MESSAGE);
                                        err.setText("Details not filled");
                                        return;
                            }        
                        }
                        if("Admin".equals(selectedUserType)){
                            if (nameField.getText().isBlank()){
                                        JOptionPane.showMessageDialog(null, "Details not filled", "Error", JOptionPane.ERROR_MESSAGE);
                                        err.setText("Details not filled");
                                        return;
                            }  
                        }
                        
                        String query1 = "INSERT INTO username_password VALUES(?,?,?)";
                        PreparedStatement ps11 = conn.prepareStatement(query1);
                        ps11.setString(1, user_name);
                        ps11.setString(2, passw);
                        ps11.setString(3, selectedUserType);
                        ps11.executeUpdate();
                        boolean userInserted = true;
                        try (Connection conn1 = DatabaseConnection.getConnection2()) {
                            conn1.setAutoCommit(false); // begin transaction for DB2
                            if ("Student".equals(selectedUserType)) {
                                if (nameField.getText().isBlank() || rollNoField.getText().isBlank() ||
                                    programField.getText().isBlank() || yearField.getText().isBlank()) {
                                    JOptionPane.showMessageDialog(null, "Details not filled", "Error", JOptionPane.ERROR_MESSAGE);
                                    err.setText("Details not filled");
                                    return;
                                }
                            }
                           

                            String query2 = "INSERT INTO relation1 VALUES(?,?)";
                            PreparedStatement ps12 = conn1.prepareStatement(query2);
                            ps12.setString(1, user_name);
                            ps12.setString(2, selectedUserType);
                            ps12.executeUpdate();

                            if ("Student".equals(selectedUserType)) {
                                String name = nameField.getText();
                                String program = programField.getText();
                                if (name.isBlank() || rollNoField.getText().isBlank() || program.isBlank() || yearField.getText().isBlank()) {
                                    JOptionPane.showMessageDialog(null, "Details not filled", "Error", JOptionPane.ERROR_MESSAGE);
                                    err.setText("Details not filled");
                                    return;
                                }

                                int rollNo = Integer.parseInt(rollNoField.getText());
                                int year = Integer.parseInt(yearField.getText());
                                if (year <= 0 || rollNo <= 0) {
                                    JOptionPane.showMessageDialog(null, "Invalid year/roll number", "Error", JOptionPane.ERROR_MESSAGE);
                                    err.setText("Invalid year/roll number");
                                    return;
                                }

                                String studentQuery = "INSERT INTO students VALUES (?, ?, ?, ?, ?);";
                                PreparedStatement ps13 = conn1.prepareStatement(studentQuery);
                                ps13.setString(1, user_name);
                                ps13.setString(2, name);
                                ps13.setInt(3, rollNo);
                                ps13.setString(4, program);
                                ps13.setInt(5, year);
                                ps13.executeUpdate();

                            } else if ("Instructor".equals(selectedUserType)){
                                String name = nameField.getText();
                                if (name.isBlank()) {
                                    JOptionPane.showMessageDialog(null, "Enter a name!", "Error", JOptionPane.ERROR_MESSAGE);
                                    err.setText("Enter a name!");
                                    return;
                                }

                                String insQuery = "INSERT INTO instructor_name_username VALUES(?, ?)";
                                PreparedStatement ps14 = conn1.prepareStatement(insQuery);
                                ps14.setString(1, name);
                                ps14.setString(2, user_name);
                                ps14.executeUpdate();

                                Instructor ins = new Instructor(name, user_name);
                                Main.list_of_instructors.add(ins);
                            }
                            else{
                                String name = nameField.getText();
                                if (name.isBlank()) {
                                    JOptionPane.showMessageDialog(null, "Enter a name!", "Error", JOptionPane.ERROR_MESSAGE);
                                    err.setText("Enter a name!");
                                    return;
                                }
                                String insQuery = "INSERT INTO admins VALUES(?, ?)";
                                PreparedStatement ps14 = conn1.prepareStatement(insQuery);
                                ps14.setString(1, user_name);
                                ps14.setString(2, name);
                                ps14.executeUpdate();
                            }

                            conn1.commit();
                            JOptionPane.showMessageDialog(null, "User added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                            err.setText("User added successfully!");
                            resetFields();

                        } catch (SQLException ex2) {
                            ex2.printStackTrace();
                            if (userInserted) {
                                try (PreparedStatement rollbackUser = conn.prepareStatement("DELETE FROM username_password WHERE user_name = ?")) {
                                    rollbackUser.setString(1, user_name);
                                    rollbackUser.executeUpdate();
                                } catch (SQLException ex3) {
                                    ex3.printStackTrace();
                                }
                            }
                            JOptionPane.showMessageDialog(null, "Failed to add user to secondary DB. Changes reverted.", "Error", JOptionPane.ERROR_MESSAGE);
                            err.setText("User add failed ");
                        }
                    }
                } else {
                     JOptionPane.showMessageDialog(null,"Passwords do not match!","Error",JOptionPane.ERROR_MESSAGE);
 
                    err.setText("Passwords do not match!");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                 JOptionPane.showMessageDialog(null,"Database error occurred","Error",JOptionPane.ERROR_MESSAGE);
 
                err.setText("Database error occurred.");
            }
        });

        // --- Back button ---
        back.addActionListener(e -> mainFrame.show_card("admin_dashboard"));
        resetFields(); 
    }

    public JPanel get_panel() {
        resetFields();
        return panel;
    }
    private void resetFields() {
        username_enter.setText("");
        pass_enter.setText("");
        pass_enter2.setText("");
        nameField.setText("");
        rollNoField.setText("");
        programField.setText("");
        yearField.setText("");
        userTypeComboBox.setSelectedIndex(0);

        // Hide student fields
        rollNoLabel.setVisible(false);
        rollNoField.setVisible(false);
        programLabel.setVisible(false);
        programField.setVisible(false);
        yearLabel.setVisible(false);
        yearField.setVisible(false);

        err.setText("");
    }

}
