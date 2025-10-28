import javax.swing.*;
import java.awt.*;

public class MainFrame {
    private JFrame frame;
    private JPanel cards;
    private CardLayout layout;

    // keep the variable names simple
    private LoginPage loginPage;
    private InstructDashboard instruct_dashboard;
    private StudDashboard student_dashboard;
    private AdminDashboard admin_dashboard;
    private GiveGrades give_grades;
    private ComputeGrades compute_grades;

    public MainFrame() {
        frame = new JFrame("main");
        layout = new CardLayout();
        cards = new JPanel(layout);

        // create pages (pass this to pages which need MainFrame reference)
        loginPage = new LoginPage(this); // LoginPage has constructor LoginPage(MainFrame)
        instruct_dashboard = new InstructDashboard(this); // modify constructor accordingly
        give_grades = new GiveGrades(this);
        compute_grades = new ComputeGrades(this);

        // student_dashboard = new StudDashboard(this);
        // admin_dashboard = new AdminDashboard(this);

        // add their panels to cards (use the get_panel() naming you requested)
        cards.add(loginPage.get_panel(), "login");
        cards.add(instruct_dashboard.get_panel(), "instructor_dashboard");
        cards.add(give_grades.get_panel(), "give_grades");
        cards.add(compute_grades.get_panel(),"compute_grades");
        // cards.add(student_dashboard.get_panel(), "student_dashboard");
        // cards.add(admin_dashboard.get_panel(), "admin_dashboard");

        frame.add(cards, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        // start with login
        show_card("login");
    }

    // card switching (snake_case)
    public void show_card(String name) {
        CardLayout cl = (CardLayout) cards.getLayout();
        cl.show(cards, name);
    }

    public void show_login() {
        show_card("login");
    }

    // these are called by LoginPage (snake_case)
    public void load_instructor_dashboard(Instructor i) {
        instruct_dashboard.load_instructor_dashboard(i);
    }

    public void load_give_grades(Instructor i) {
        give_grades.set_instructor(i);
    }

    public void load_compute_grades(Instructor i){
        compute_grades.set_instructor(i);
    }
    public void load_student_dashboard(Student s) {
        //student_dashboard.load_student_dashboard(s);
    }

    public void load_admin_dashboard(Admin a) {
       // admin_dashboard.load_admin_dashboard(a);
    }

    // main
    // public static void main(String[] args) {
    //     SwingUtilities.invokeLater(() -> new MainFrame());
    // }
}
