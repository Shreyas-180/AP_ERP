
//import ui.LoginPage;
//import AP_ERP.UI.LoginPage; // Add this import if LoginPage is in the same package or adjust the package name accordingly

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static ArrayList<Instructor> list_of_instructors;
    public static List<Course> list_of_courses;
    
    static {
        // initialize instructors first
        list_of_instructors = Factory.factory_for_instruc(); // bruh fixed the problem of mismatch i think idk for sure
        // then initialize courses (which can now access instructors)
        list_of_courses = Factory.factory_for_course();
    }
    
    public static void main(String[] args) {
        MainFrame frame = new MainFrame();
        
    }
}
// public class Main {
//     public static ArrayList<Instructor> list_of_instructors = new ArrayList<>(Factory.factory_for_instruc());
//     public static List<Course> list_of_courses = new ArrayList<>(Factory.factory_for_course());
//     public static void main(String[] args) {
//         LoginPage frame = new LoginPage();  // create a window object
//        // frame.initialize();                 // call your method to set it up
//        //frame.addi();
//        //List <Instructor> list_of_instructors = new ArrayList<>();
       
//     }
// }
