import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import java.awt.Font;
import java.awt.BorderLayout;
import java.awt.GridLayout;

public class AdminDashboard extends JPanel{
    private JPanel panel;
    private MainFrame mainFrame;
    //private DefaultListModel<String> course_model;
   // private JList<String> course_list;
    private JLabel header;
    private Admin current_admin;

    public AdminDashboard(MainFrame mainframe) {
        this.mainFrame = mainframe;
        panel = new JPanel(new BorderLayout(10, 10));

        header = new JLabel("Admin Dashboard", SwingConstants.CENTER);
        header.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(header, BorderLayout.NORTH);

        JPanel button_panel = new JPanel();
        JButton add_users = new JButton("Add Users");
        JButton add_course = new JButton("Add Course");
        JButton set_maintainence = new JButton("Set Maintainence");
        button_panel.add(add_course);
        button_panel.add(add_users);
        button_panel.add(set_maintainence);
        panel.add(button_panel);
        add_users.addActionListener(e->{
            //AddUsers add1 = new AddUsers(mainframe);
            //add1.addusers();
            mainFrame.load_add_users(current_admin);
            mainFrame.show_card("add_users");
        });

        // Constructor remains the same
    }

    // void display_dashboard(Admin a) {
    //     // --- Frame Setup ---
    //     JFrame dashboard = new JFrame("Admin Dashboard");
    //     dashboard.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
    //     dashboard.setSize(600, 500);
    //     dashboard.setLayout(new BorderLayout(15, 15)); 

    //     // --- Greeting Label (Top Area) ---
    //     JLabel greetLabel = new JLabel("Welcome, " + a.getUsername(), JLabel.CENTER);
    //     greetLabel.setFont(new Font("Arial", Font.BOLD, 24));
    //     dashboard.add(greetLabel, BorderLayout.NORTH); 

    //     // --- Button Panel (Center Area) ---
    //     JPanel buttonPanel = new JPanel();
    //     // GridLayout creates a grid of equal-sized components. Perfect for big buttons.
    //     // (rows, cols, h-gap, v-gap)
    //     buttonPanel.setLayout(new GridLayout(2, 2, 15, 15));

    //     // --- "Add Users" Button ---
    //     JButton addUsersButton = new JButton("Add Users");
    //     addUsersButton.setFont(new Font("Arial", Font.PLAIN, 18));
    //     addUsersButton.addActionListener(e -> {
            
    //         AddUsers add1 = new AddUsers();
    //         add1.addusers();
    //         //System.out.println("'Add Users' button clicked!");
    //     });
    //     buttonPanel.add(addUsersButton); // Add button to the panel

       
    //     JButton add_course = new JButton("Add Course");
    //     add_course.setFont(new Font("Arial", Font.PLAIN, 18));
    //     add_course.addActionListener(e -> {
            
    //         AddCourse add2 = new AddCourse();
    //         add2.addcourse();
    //         //System.out.println("'Add Users' button clicked!");
    //     });
    //     buttonPanel.add(add_course);

    //     JButton viewReportsButton = new JButton("Set Maintainence");
    //     viewReportsButton.setFont(new Font("Arial", Font.PLAIN, 18));
    //     viewReportsButton.addActionListener(e -> {
            
    //         Maintainence add2 = new Maintainence();
    //         add2.maintainence();
    //         //System.out.println("'Add Users' button clicked!");
    //     });
    //     buttonPanel.add(viewReportsButton);

        
    //     JButton settingsButton = new JButton("Edit Course");
    //     settingsButton.setFont(new Font("Arial", Font.PLAIN, 18));
    //     settingsButton.addActionListener(e -> {
            
    //         EditCourse add2 = new EditCourse();
    //         add2.editcourse();
    //         //System.out.println("'Add Users' button clicked!");
    //     });
    //     buttonPanel.add(settingsButton);

    //     dashboard.add(buttonPanel, BorderLayout.CENTER); 
    //     dashboard.setLocationRelativeTo(null);
    //     dashboard.setVisible(true);
    // }

    public JPanel get_panel() {
        return panel;
    }
    public void load_admin_dashboard(Admin i) {
        current_admin = i;
        System.out.println(i.getid());
        header.setText("Welcome, " + i.get_real_name());
        System.out.println("sus");
       // course_model.clear();
        // for (String section : i.get_course_list()) {
        //     course_model.addElement(section);
        //     System.out.println(section);
        // }
    }
    public void load_admin_dashboard(){

    }
}
