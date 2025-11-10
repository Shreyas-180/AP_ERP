import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class StudDashboard {

    Student the_student;
    StudDashboard(){
        
    }

    void display_dashboard(Student s){
        this.the_student = s;
         JFrame dashboard = new JFrame("Student Dashboard");
        dashboard.setSize(600, 500);
        dashboard.setLayout(null); // using absolute positioning for simplicity
        dashboard.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Welcome message
        JLabel welcome_msg = new JLabel("Welcome to the ERP, " + s.getUsername());
        welcome_msg.setBounds(50, 20, 400, 30);
        dashboard.add(welcome_msg);

        JLabel rollLabel = new JLabel("Roll No: " + s.getRollno());
        rollLabel.setBounds(50, 60, 200, 25);
        dashboard.add(rollLabel);

        JLabel programLabel = new JLabel("Program: " + s.getprogram());
        programLabel.setBounds(50, 90, 200, 25);
        dashboard.add(programLabel);
        System.out.println(s.getYear());
        JLabel yearLabel = new JLabel("Year: " + s.getYear());
        yearLabel.setBounds(50, 120, 200, 25);
        dashboard.add(yearLabel);

        System.out.println(s.getcourses());

        JLabel coursesLabel = new JLabel("Enrolled Courses:" + s.getcourses());

        coursesLabel.setBounds(50, 160, 200, 25);
        dashboard.add(coursesLabel);


        // Example: dynamically list courses
        int y = 190;
        
        JButton viewGrades = new JButton("View Grades");
        viewGrades.setBounds(400, 50, 120, 25);
        dashboard.add(viewGrades);

        JButton viewCourses = new JButton("View Courses");
        viewCourses.setBounds(400, 80, 120, 25);
        dashboard.add(viewCourses);

        viewCourses.addActionListener(e -> {
            ViewCourseWindow cw = new ViewCourseWindow(); // open new window with grades
            cw.display();

        });

        viewGrades.addActionListener(e -> {
            GradesWindow gw = new GradesWindow(); // open new window with grades
            gw.display(s);

        });
        dashboard.setVisible(true);
        // view/download transcript 
        // add/drop courses. 

        // changePass.addActionListener(e -> {
        //     mainFrame.load_change_password(s.getUsername(), "student_dashboard");
        // });
    }

}
