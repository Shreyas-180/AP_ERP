import javax.swing.*;
import java.awt.*;

class SectionInfo {
    String semester;
    int year;
    String dayTime;
    String room;
}

public class SectionDialog {

    public static SectionInfo getSectionInfo(JFrame parentFrame) {
        SectionInfo info = new SectionInfo();

        // Modal dialog
        JDialog dialog = new JDialog(parentFrame, "Enter Section Details", true);
        dialog.setSize(350, 200);
        dialog.setLayout(new BorderLayout(10,10));
        dialog.setLocationRelativeTo(parentFrame);

        JPanel panel = new JPanel(new GridLayout(4,2,10,10));
        JTextField semesterField = new JTextField();
        JTextField yearField = new JTextField();
        JTextField dayTimeField = new JTextField();
        JTextField roomField = new JTextField();

        panel.add(new JLabel("Semester:")); panel.add(semesterField);
        panel.add(new JLabel("Year:")); panel.add(yearField);
        panel.add(new JLabel("Day & Time:")); panel.add(dayTimeField);
        panel.add(new JLabel("Room:")); panel.add(roomField);

        JButton submitButton = new JButton("Submit");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(submitButton);

        dialog.add(panel, BorderLayout.CENTER);
        dialog.add(buttonPanel, BorderLayout.SOUTH);

        submitButton.addActionListener(e -> {
            // Validate input
            if(semesterField.getText().isEmpty() || yearField.getText().isEmpty() ||
               dayTimeField.getText().isEmpty() || roomField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "All fields are required!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            try {
                info.semester = semesterField.getText();
                info.year = Integer.parseInt(yearField.getText());
                info.dayTime = dayTimeField.getText();
                info.room = roomField.getText();
            } catch(NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, "Year must be a number!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            dialog.dispose(); // close dialog
        });

        dialog.setVisible(true); // blocks until user clicks submit

        return info; // always valid info
    }


}
