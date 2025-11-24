import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame{
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
    private AddUsers add_users;
    private Maintainence set_maintainence;
    private AddCourse add_course;
    private EditCourse edit_course;
    private ViewCourseWindow view_course_window;
    private ChangePasswordPanel change_password_panel;
    private StatsWindow stats_window;
    private AdminResetPassword admin_reset_pass;
    public MainFrame() {
        frame = new JFrame("main");
        layout = new CardLayout();
        cards = new JPanel(layout);

        // create pages (pass this to pages which need MainFrame reference)
        loginPage = new LoginPage(this); // LoginPage has constructor LoginPage(MainFrame)
        instruct_dashboard = new InstructDashboard(this); // modify constructor accordingly
        give_grades = new GiveGrades(this);
        compute_grades = new ComputeGrades(this);
        admin_dashboard = new AdminDashboard(this);
        add_users = new AddUsers(this);
        set_maintainence = new Maintainence(this);
        add_course = new AddCourse(this);
        edit_course = new EditCourse(this);
        change_password_panel = new ChangePasswordPanel(this);
        student_dashboard = new StudDashboard(this);
        view_course_window = new ViewCourseWindow(this);
        stats_window = new StatsWindow(this);
        admin_reset_pass = new AdminResetPassword(this);
        // admin_dashboard = new AdminDashboard(this);

        // add their panels to cards (use the get_panel() naming you requested)
        cards.add(loginPage.get_panel(), "login");
        cards.add(instruct_dashboard.get_panel(), "instructor_dashboard");
        cards.add(give_grades.get_panel(), "give_grades");
        cards.add(compute_grades.get_panel(),"compute_grades");
        cards.add(admin_dashboard.get_panel(),"admin_dashboard");
        cards.add(add_users.get_panel(), "add_users");
        cards.add(set_maintainence.get_panel(),"set_maintainence");
        cards.add(add_course.get_panel(),"add_course");
        cards.add(new EditCourse(this).getPanel(), "edit_course");
        cards.add(change_password_panel.get_panel(), "change_password");
        //cards.add(student_dashboard.get_panel(), "student_dashboard");
        cards.add(student_dashboard, "student_dashboard");
        // cards.add(admin_dashboard.get_panel(), "admin_dashboard");
        cards.add(view_course_window, "view_courses");
        cards.add(stats_window.get_panel(), "stats_window");
        cards.add(admin_reset_pass.get_panel(), "admin_reset_password");
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
        student_dashboard.loadStudentDashboard(s);
    }

    public void load_admin_dashboard(Admin a) {
       admin_dashboard.load_admin_dashboard(a);
    }
    public void load_add_users(Admin a){
      
    }
    public void load_change_password(String username, String returnCard) {
        change_password_panel.set_user(username, returnCard);
        show_card("change_password");
    }
    public void load_stats_window(Instructor i) {
        stats_window.load_stats(i);
        show_card("stats_window");
    }
    // main
    // public static void main(String[] args) {
    //     SwingUtilities.invokeLater(() -> new MainFrame());
    // }
}
