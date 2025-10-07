import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class InstructDashboard{

    public InstructDashboard(){

    }
    
    void display_dashboard(Instructor i){
        JFrame dashboard = new JFrame();
        dashboard.setSize(500, 400);

        String sec = i.getSections();
        JLabel mysections = new JLabel("My Sections: " + sec);
        dashboard.add(mysections);
        mysections.setBounds(50, 50, 300, 30);
        dashboard.setVisible(true);
        

        
    }
    
}
