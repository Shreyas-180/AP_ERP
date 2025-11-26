package Instructor;
import javax.swing.*;

import Common.DatabaseConnection;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;

public class CSVGradeUploader {

    public static void uploadGrades(File file, String courseCode) {
        String line = "";
        String splitBy = ",";
        int successCount = 0;
        int failCount = 0;

        try (Connection conn = DatabaseConnection.getConnection2();
             BufferedReader br = new BufferedReader(new FileReader(file))) {
            
            // 1. Skip the Header Row
            br.readLine();

            // 2. Prepare the SQL Query
            // We update the existing row (created when student registered)
            String query = "UPDATE grades SET " +
                           "quiz_marks = ?, " +
                           "assignment_marks = ?, " +
                           "midsem_marks = ?, " +
                           "endsem_marks = ?, " +
                           "group_project_marks = ? " +
                           "WHERE user_name = ? AND subject = ?";

            PreparedStatement ps = conn.prepareStatement(query);

            // 3. Read line by line
            while ((line = br.readLine()) != null) {
                try {
                    String[] data = line.split(splitBy);
                    
                    // CSV Format: username [0], quiz [1], assign [2], mid [3], end [4], group [5]
                    
                    String studentUser = data[0].trim();
                    int quiz = Integer.parseInt(data[1].trim());
                    int assign = Integer.parseInt(data[2].trim());
                    int mid = Integer.parseInt(data[3].trim());
                    int end = Integer.parseInt(data[4].trim());
                    int group = Integer.parseInt(data[5].trim());

                    // Set parameters
                    ps.setInt(1, quiz);
                    ps.setInt(2, assign);
                    ps.setInt(3, mid);
                    ps.setInt(4, end);
                    ps.setInt(5, group);
                    ps.setString(6, studentUser);
                    ps.setString(7, courseCode);

                    int rows = ps.executeUpdate();
                    if (rows > 0) {
                        successCount++;
                    } else {
                        System.out.println("Student not found in this course: " + studentUser);
                        failCount++;
                    }

                } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                    System.out.println("Skipping bad line: " + line);
                    failCount++;
                }
            }

            JOptionPane.showMessageDialog(null, 
                "Upload Complete!\nUpdated: " + successCount + "\nFailed/Skipped: " + failCount);

        } catch (IOException | SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error reading file or database: " + e.getMessage());
        }
    }
}