package Student;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import Common.User;
import Instructor.*;
public class Student extends User {

    private ArrayList<String> courses;
    private int rollno;
    private int enrollmentid; 
    private int year;
    private String program;

    private Map<String, String> grades;

    public void setRollno(int rollno) {
        this.rollno = rollno;
    }

    public void setEnrollmentid(int enrollmentid) {
        this.enrollmentid = enrollmentid;
    }

     public void setYear(int year) {
        this.year = year;
    }

    public void setProgram(String program) {
        this.program = program;
    }

    public void addgrades(String course, String marks){
        grades.put(course, marks);
    }

    public String getgrades(){
        StringBuilder formattedGrades = new StringBuilder();
        // Check if the map is not null and not empty before iterating
        if (grades != null && !grades.isEmpty()) {
            for (Map.Entry<String, String> entry : this.grades.entrySet()) {
                formattedGrades.append(entry.getKey())
                               .append(" : ")
                               .append(entry.getValue())
                               .append("\n");
            }
        }
        
        // Return the built string, removing the final newline character if it exists
        if (formattedGrades.length() > 0) {
            return formattedGrades.substring(0, formattedGrades.length() - 1);
        } else {
            return "No grades available."; // Return a clear message if there are no grades
        }
    }

    public String getcourses(){
        String temp = " ";
        for(int i = 0; i < courses.size(); i++){
            temp += courses.get(i);
            temp += ' ';
        }
        return temp;
    }

    public int getEnrollmentid() {
        return enrollmentid;
    }

    public int getYear() {
        return year;
    }
    
    public String getprogram(){
        return program;
    }

    public int getRollno() {
        return rollno;
    }

    public void addcourse(String course1){
        courses.add(course1);
    }
    public Student(String username){
        super(username);
        this.courses = new ArrayList<>();
        this.grades = new HashMap<>(); 

    }

}