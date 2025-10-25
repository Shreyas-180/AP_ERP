import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Font;
import java.awt.BorderLayout;
import java.awt.GridLayout;

public class AdminDashboard {

    public AdminDashboard() {
        // Constructor remains the same
    }

    void display_dashboard(Admin a) {
        // --- Frame Setup ---
        JFrame dashboard = new JFrame("Admin Dashboard");
        dashboard.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        dashboard.setSize(600, 500);
        dashboard.setLayout(new BorderLayout(15, 15)); 

        // --- Greeting Label (Top Area) ---
        JLabel greetLabel = new JLabel("Welcome, " + a.getUsername(), JLabel.CENTER);
        greetLabel.setFont(new Font("Arial", Font.BOLD, 24));
        dashboard.add(greetLabel, BorderLayout.NORTH); 

        // --- Button Panel (Center Area) ---
        JPanel buttonPanel = new JPanel();
        // GridLayout creates a grid of equal-sized components. Perfect for big buttons.
        // (rows, cols, h-gap, v-gap)
        buttonPanel.setLayout(new GridLayout(2, 2, 15, 15));

        // --- "Add Users" Button ---
        JButton addUsersButton = new JButton("Add Users");
        addUsersButton.setFont(new Font("Arial", Font.PLAIN, 18));
        addUsersButton.addActionListener(e -> {
            
            AddUsers add1 = new AddUsers();
            add1.addusers();
            //System.out.println("'Add Users' button clicked!");
        });
        buttonPanel.add(addUsersButton); // Add button to the panel

       
        JButton add_course = new JButton("Add Course");
        add_course.setFont(new Font("Arial", Font.PLAIN, 18));
        add_course.addActionListener(e -> {
            
            AddCourse add2 = new AddCourse();
            add2.addcourse();
            //System.out.println("'Add Users' button clicked!");
        });
        buttonPanel.add(add_course);

        JButton viewReportsButton = new JButton("View Reports");
        viewReportsButton.setFont(new Font("Arial", Font.PLAIN, 18));
        buttonPanel.add(viewReportsButton);
        
        JButton settingsButton = new JButton("Edit Course");
        settingsButton.setFont(new Font("Arial", Font.PLAIN, 18));
        settingsButton.addActionListener(e -> {
            
            EditCourse add2 = new EditCourse();
            add2.editcourse();
            //System.out.println("'Add Users' button clicked!");
        });
        buttonPanel.add(settingsButton);

        dashboard.add(buttonPanel, BorderLayout.CENTER); 
        dashboard.setLocationRelativeTo(null);
        dashboard.setVisible(true);
    }
}
