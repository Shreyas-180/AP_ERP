package Student;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class GradesWindow {
    
    public void display(Student s){
        
        JFrame frame = new JFrame("Grades");
        frame.setSize(400, 300);
        frame.setLayout(null); // using absolute positioning
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Create JLabel with grades text
        JLabel marks = new JLabel("<html>" + "Grades: " + s.getgrades().replace("\n", "<br>") + "</html>");
        marks.setBounds(50, 50, 300, 30); // x=50, y=50, width=300, height=30

        // Add label to frame
        frame.add(marks);

        frame.setVisible(true);
    }
}
