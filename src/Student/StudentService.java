package Student;
import java.sql.*;
import java.util.ArrayList;
import java.time.LocalDate;
import javax.swing.JOptionPane;

import Common.DatabaseConnection;
import Main.Main; 

public class StudentService {

    // ---------------------------------------------
    // CHECK IF SECTION HAS SEATS AVAILABLE
    // ---------------------------------------------
    public boolean hasSeatAvailable(int sectionId) {
        String sql = "SELECT s.capacity, (SELECT COUNT(*) FROM enrollments e WHERE e.section_id = s.id AND e.status = 'enrolled') AS enrolled " +
                     "FROM sections s WHERE s.id = ?";
        try (Connection con = DatabaseConnection.getConnection2();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, sectionId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int cap = rs.getInt("capacity");
                int taken = rs.getInt("enrolled");
                return taken < cap;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    // ---------------------------------------------
    // CHECK DUPLICATE ENROLLMENT
    // ---------------------------------------------
    public boolean isAlreadyEnrolled(String username, int sectionId) {
        String sql = "SELECT * FROM enrollments WHERE student_user_name = ? AND section_id = ?";
        try (Connection con = DatabaseConnection.getConnection2();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, username); 
            ps.setInt(2, sectionId);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return true;
    }

    // ---------------------------------------------
    // REGISTER STUDENT IN A SECTION (FIXED: Inserts Section & Instructor)
    // ---------------------------------------------
    public String registerSection(String username, int sectionId) {

        if (Main.getstatus()) {
            return "System is under maintenance. Registration is currently disabled.";
        }

        if (!hasSeatAvailable(sectionId)) {
            return "Section Full!";
        }
        if (isAlreadyEnrolled(username, sectionId)) {
            return "Already Registered!";
        }

        Connection con = null;
        String courseCode = "";
        String sectionName = "";      // Store section
        String instructorName = "";   // Store instructor

        try {
            con = DatabaseConnection.getConnection2();
            
            // --- STEP 1: Get the course_code, section, and instructor ---
            String getCodeSql = "SELECT course_code, section, instructor_user_name FROM sections WHERE id = ?";
            try (PreparedStatement psCode = con.prepareStatement(getCodeSql)) {
                psCode.setInt(1, sectionId);
                ResultSet rs = psCode.executeQuery();
                if (rs.next()) {
                    courseCode = rs.getString("course_code");
                    sectionName = rs.getString("section");
                    instructorName = rs.getString("instructor_user_name");
                } else {
                    return "Error: Invalid Section ID!";
                }
            }

            // --- STEP 2: Start transaction ---
            con.setAutoCommit(false); 

            // --- STEP 3: Insert into enrollments ---
            String sqlEnroll = "INSERT INTO enrollments(student_user_name, section_id, status) VALUES(?,?,?)";
            try (PreparedStatement psEnroll = con.prepareStatement(sqlEnroll)) {
                psEnroll.setString(1, username);
                psEnroll.setInt(2, sectionId);
                psEnroll.setString(3, "enrolled");
                psEnroll.executeUpdate();
            }

            // --- STEP 4: Insert into subjectsxname_students ---
            String sqlSubject = "INSERT INTO subjectsxname_students(user_name, code) VALUES(?,?)";
            try (PreparedStatement psSubject = con.prepareStatement(sqlSubject)) {
                psSubject.setString(1, username);
                psSubject.setString(2, courseCode);
                psSubject.executeUpdate();
            }
            
            // --- STEP 5: Insert placeholder in grades (WITH SECTION & INSTRUCTOR) ---
            // Fixed logic here: Added section and instructor columns
            String sqlGrade = "INSERT INTO grades(user_name, subject, grad, section, instructor) VALUES(?,?,?,?,?) ON DUPLICATE KEY UPDATE user_name=user_name";
            try (PreparedStatement psGrade = con.prepareStatement(sqlGrade)) {
                psGrade.setString(1, username);
                psGrade.setString(2, courseCode);
                psGrade.setString(3, "N/A");
                psGrade.setString(4, sectionName);     // Insert Section
                psGrade.setString(5, instructorName);  // Insert Instructor
                psGrade.executeUpdate();
            }

            // --- STEP 6: Commit ---
            con.commit(); 
            return "Registration Successful!";

        } catch (SQLException ex) {
            if (con != null) { try { con.rollback(); } catch (SQLException e) { e.printStackTrace(); } }
            if (ex.getErrorCode() == 1062) { return "Already registered for this course!"; }
            ex.printStackTrace();
            return "Error occurred during registration!";
        } finally {
            if (con != null) { try { con.setAutoCommit(true); con.close(); } catch (SQLException e) { e.printStackTrace(); } }
        }
    }

    public String checkRegistrationWindow() {
        LocalDate today = LocalDate.now();
        
        try (Connection conn = DatabaseConnection.getConnection2()) {
            String q = "SELECT * FROM registration_dates WHERE id = 1";
            PreparedStatement ps = conn.prepareStatement(q);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                Date sqlStart = rs.getDate("start_date");
                Date sqlEnd = rs.getDate("end_date");
                
                LocalDate start = sqlStart.toLocalDate();
                LocalDate end = sqlEnd.toLocalDate();
                
                if (today.isBefore(start)) {
                    return "Registration has not started yet.\nStart Date: " + start;
                }
                if (today.isAfter(end)) {
                    return "Registration deadline has passed.\nEnd Date: " + end;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Database error checking dates.";
        }
        
        return "OK";
    }

    // ---------------------------------------------
    // DROP SECTION
    // ---------------------------------------------
    public String dropSection(String username, int sectionId) {
        
        if (Main.getstatus()) {
            return "System is under maintenance. Dropping courses is currently disabled.";
        }
        
        Connection con = null;
        String courseCode = "";
        try {
            con = DatabaseConnection.getConnection2();

            // STEP 1: Get the course_code
            String getCodeSql = "SELECT course_code FROM sections WHERE id = ?";
            try (PreparedStatement psCode = con.prepareStatement(getCodeSql)) {
                psCode.setInt(1, sectionId);
                ResultSet rs = psCode.executeQuery();
                if (rs.next()) {
                    courseCode = rs.getString("course_code");
                } else {
                    return "Error: Invalid Section ID!";
                }
            }

            // STEP 2: Start transaction
            con.setAutoCommit(false);

            // STEP 3: Delete from enrollments
            String sqlEnroll = "DELETE FROM enrollments WHERE student_user_name = ? AND section_id = ?";
            int rows = 0;
            try (PreparedStatement psEnroll = con.prepareStatement(sqlEnroll)) {
                psEnroll.setString(1, username);
                psEnroll.setInt(2, sectionId);
                rows = psEnroll.executeUpdate();
            }
            if (rows == 0) {
                con.rollback(); 
                return "Not Registered in this section!";
            }

            // STEP 4: Delete from subjectsxname_students
            String sqlSubject = "DELETE FROM subjectsxname_students WHERE user_name = ? AND code = ?";
            try (PreparedStatement psSubject = con.prepareStatement(sqlSubject)) {
                psSubject.setString(1, username);
                psSubject.setString(2, courseCode);
                psSubject.executeUpdate();
            }

            // STEP 5: Delete from grades
            String sqlGrades = "DELETE FROM grades WHERE user_name = ? AND subject = ?";
            try (PreparedStatement psGrades = con.prepareStatement(sqlGrades)) {
                psGrades.setString(1, username);
                psGrades.setString(2, courseCode);
                psGrades.executeUpdate();
            }

            // STEP 6: Commit
            con.commit();
            return "Dropped Successfully!";

        } catch (SQLException ex) {
            if (con != null) { try { con.rollback(); } catch (SQLException e) { e.printStackTrace(); } }
            ex.printStackTrace();
            return "Error while dropping!";
        } finally {
            if (con != null) { try { con.setAutoCommit(true); con.close(); } catch (SQLException e) { e.printStackTrace(); } }
        }
    }

    // ---------------------------------------------
    // FETCH TIMETABLE FOR STUDENT
    // ---------------------------------------------
    public ArrayList<String[]> getTimetable(String username) {
        ArrayList<String[]> list = new ArrayList<>();
        // Using enrollments table to ensure we get the exact section student registered for
        String sql = "SELECT s.id, s.course_code, c.title, s.day_time, s.room " + 
                     "FROM enrollments e " +
                     "JOIN sections s ON e.section_id = s.id " +
                     "JOIN courses c ON s.course_code = c.code AND s.section = c.section " +
                     "WHERE e.student_user_name = ? AND e.status = 'enrolled'";
        try (Connection con = DatabaseConnection.getConnection2();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new String[]{
                    rs.getString("id"),
                    rs.getString("course_code"),
                    rs.getString("title"),
                    rs.getString("day_time"),
                    rs.getString("room")
                });
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    // ---------------------------------------------
    // FETCH GRADES FOR STUDENT
    // ---------------------------------------------
    public ArrayList<String[]> getGrades(String username) {
        ArrayList<String[]> grades = new ArrayList<>();
        String sql = "SELECT g.subject, c.title, g.quiz_marks, g.assignment_marks, " +
                     "g.midsem_marks, g.endsem_marks, g.group_project_marks, g.grad " +
                     "FROM grades g " +
                     "LEFT JOIN courses c ON g.subject = c.code AND g.section = c.section " + // Added section join
                     "WHERE g.user_name = ?";
        try (Connection con = DatabaseConnection.getConnection2();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, username); 
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                grades.add(new String[]{
                    rs.getString("subject"),
                    rs.getString("title"), 
                    rs.getString("quiz_marks"),
                    rs.getString("assignment_marks"),
                    rs.getString("midsem_marks"),
                    rs.getString("endsem_marks"),
                    rs.getString("group_project_marks"),
                    rs.getString("grad")
                });
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return grades;
    }
}