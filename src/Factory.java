import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Factory {

    static Instructor factory_for_instruc(String Username){
        Instructor sir = new Instructor(Username);
        return sir;
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
    static ArrayList<Course> factory_for_course(){
        ArrayList<Course> course_array = new ArrayList<>();
        try(Connection conn = DatabaseConnection.getConnection2()){  
            String query = "SELECT* FROM courses;";
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while(rs.next() != false){
                
                Course temp = new Course(rs.getString("code"),
                rs.getString("title"),
                rs.getInt("credits"),
                rs.getString("instructor"),
                rs.getInt("quiz"),
                rs.getInt("assignment"),
                rs.getInt("midsem"),
                rs.getInt("endsem"),
                rs.getInt("group_project"),
                rs.getString("course_description"),
                rs.getInt("seats"));
                course_array.add(temp);
            }
            //return course_array;
        }
        catch(SQLException e){
            e.printStackTrace();
            return new ArrayList<>();
        }
        return course_array;
    }


}
