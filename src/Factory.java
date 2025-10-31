import java.util.ArrayList;
import java.sql.*;

public class Factory {

    static Admin factory_for_admin(String Username){
        Admin admin = null;
        try(Connection conn2 = DatabaseConnection.getConnection2()){
            
            String query = "SELECT * FROM admins WHERE username = ?;";
            PreparedStatement ps = conn2.prepareStatement(query);
                ps.setString(1, Username);
                ResultSet rs = ps.executeQuery();
                if(rs.next() != false){
                    admin = new Admin(Username, rs.getString("name"));
                }
            //return admin;
        } catch(SQLException e1){
                e1.printStackTrace();
        }
        return admin;
    }
    // static Instructor factory_for_instruc(){

    //     Instructor sir = new Instructor(Username, id);
    //     return sir;
    // }
    static ArrayList<Instructor> factory_for_instruc(){
        ArrayList<Instructor> instructors = new ArrayList<>();
        
        String instructorQuery = "SELECT user_name, name FROM instructor_name_username";
        
        try (Connection conn = DatabaseConnection.getConnection2();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(instructorQuery)) {
            
            while (rs.next()) {
                String username = rs.getString("user_name");
                String name = rs.getString("name");
                
                Instructor instructor = new Instructor(name, username);
                
                // Load courses/subjects for this instructor
                String subjectQuery = "SELECT code FROM subjectsxname_instructor WHERE user_name = ?";
                try (PreparedStatement pstmt = conn.prepareStatement(subjectQuery)) {
                    pstmt.setString(1, username);
         
                    try (ResultSet subjectRs = pstmt.executeQuery()) {
                        while (subjectRs.next()) {
                            String courseCode = subjectRs.getString("code");
                            instructor.add_course(courseCode);
                        }
                    }
                }
                
                instructors.add(instructor);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return instructors;
    }
    static Student factory_for_stud(String Username){
        Student child = new Student(Username);
        try(Connection conn2 = DatabaseConnection.getConnection2()){
            String query = "SELECT * FROM students WHERE user_name = ?;";
            PreparedStatement ps = conn2.prepareStatement(query);
            ps.setString(1, Username);
            ResultSet rs = ps.executeQuery();
            if(rs.next() != false){
                child.setProgram(rs.getString(3));
                child.setRollno(rs.getInt(2));
                child.setYear(rs.getInt(4));
            }
            query = "SELECT * FROM subjectsxname_students WHERE user_name = ?;";
            ps = conn2.prepareStatement(query);
            ps.setString(1, Username);
            rs = ps.executeQuery();
            while(rs.next() != false){
                child.addcourse(rs.getString(2));
                
            }
            query = "SELECT * FROM grades WHERE user_name = ?;";
            ps = conn2.prepareStatement(query);
            ps.setString(1, Username);
            rs = ps.executeQuery();
            
            while(rs.next() != false){
                String subject = rs.getString("subject"); // or rs.getString(2)
                String grade   = rs.getString("grad");    // or rs.getString(3)
                child.addgrades(subject, grade);
                
            }

        }catch(SQLException E){
            E.printStackTrace();
        }
        return child;
    }
    // static ArrayList<Course> factory_for_course(){
    //     ArrayList<Course> course_array = new ArrayList<>();
    //     try(Connection conn = DatabaseConnection.getConnection2()){  
    //         String query = "SELECT* FROM courses;";
    //         PreparedStatement ps = conn.prepareStatement(query);
    //         ResultSet rs = ps.executeQuery();

    //         while(rs.next() != false){
    //             String code = rs.getString("code");
    //             String title = rs.getString("title");
    //             int credits = rs.getInt("credits");
    //             int quiz = rs.getInt("quiz");
    //             int ass = rs.getInt("assignment");
    //             int mid = rs.getInt("midsem");
    //             int end = rs.getInt("endsem");
    //             int grp = rs.getInt("group_project");
    //             String desc = rs.getString("course_description");
    //             int seat = rs.getInt("seats");
    //             String sec = rs.getString("section");
    //             String ins = rs.getString("instructor");
                
    //             if(list_of_instructors.contains()){

    //             }
    //             Course temp = new Course(code,title,credits,quiz,ass,mid,end,grp,desc,seat,sec,);
                
    //              // some how get the object insturtor.
                
    //             course_array.add(temp);
    //         }
    //         //return course_array;
    //     }
    //     catch(SQLException e){
    //         e.printStackTrace();
    //         return new ArrayList<>();
    //     }
    //     return course_array;
    // }
    static ArrayList<Course> factory_for_course(){
        ArrayList<Course> course_array = new ArrayList<>();
        try(Connection conn = DatabaseConnection.getConnection2();
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM courses");
            ResultSet rs = ps.executeQuery()) {  

            while(rs.next()) {
                String code = rs.getString("code");
                String title = rs.getString("title");
                int credits = rs.getInt("credits");
                int quiz = rs.getInt("quiz");
                int ass = rs.getInt("assignment");
                int mid = rs.getInt("midsem");
                int end = rs.getInt("endsem");
                int grp = rs.getInt("group_project");
                String desc = rs.getString("course_description");
                int seat = rs.getInt("seats");
                String sec = rs.getString("section");
                String instructorUsername = rs.getString("instructor");
                
                // Find the instructor object from the list
                Instructor instructorj = null;
                for(Instructor inst : Main.list_of_instructors) {
                    if(inst.getid().equals(instructorUsername)) {
                        instructorj = inst;
                        break;
                    }
                }
                
                // Create course with the instructor object
                Course temp = new Course(code, title, credits, quiz, ass, mid, end, grp, desc, seat, sec, instructorj);
                course_array.add(temp);
            }
        }
        catch(SQLException e){
            e.printStackTrace();
            return new ArrayList<>();
        }
        return course_array;
    }


}
