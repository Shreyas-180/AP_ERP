import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.time.LocalDate;

public class SetRegistrationDates {
    private JFrame frame;
    private MainFrame mainFrame;
    private JTextField startDateField;
    private JTextField endDateField;

    public SetRegistrationDates(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
    }

    public void showWindow() {
        frame = new JFrame("Set Registration Deadlines");
        frame.setSize(400, 250);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout(10, 10));

        JPanel form = new JPanel(new GridLayout(3, 2, 10, 10));
        form.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        form.add(new JLabel("Start Date (YYYY-MM-DD):"));
        startDateField = new JTextField();
        
        form.add(new JLabel("End Date (YYYY-MM-DD):"));
        endDateField = new JTextField();

        // Load current values from DB
        loadCurrentDates();

        form.add(startDateField);
        form.add(endDateField);

        JPanel btnPanel = new JPanel();
        JButton saveBtn = new JButton("Save Dates");
        btnPanel.add(saveBtn);

        frame.add(new JLabel("Format: YYYY-MM-DD (e.g., 2025-11-18)", SwingConstants.CENTER), BorderLayout.NORTH);
        frame.add(form, BorderLayout.CENTER);
        frame.add(btnPanel, BorderLayout.SOUTH);

        saveBtn.addActionListener(e -> saveDates());

        frame.setVisible(true);
    }

    private void loadCurrentDates() {
        try (Connection conn = DatabaseConnection.getConnection2()) {
            String q = "SELECT * FROM registration_dates WHERE id = 1";
            PreparedStatement ps = conn.prepareStatement(q);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                startDateField.setText(rs.getString("start_date"));
                endDateField.setText(rs.getString("end_date"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void saveDates() {
        String start = startDateField.getText();
        String end = endDateField.getText();

        try {
            // Validate 
            Date.valueOf(start);
            Date.valueOf(end);

            try (Connection conn = DatabaseConnection.getConnection2()) {
                String q = "UPDATE registration_dates SET start_date = ?, end_date = ? WHERE id = 1";
                PreparedStatement ps = conn.prepareStatement(q);
                ps.setString(1, start);
                ps.setString(2, end);
                ps.executeUpdate();
                
                JOptionPane.showMessageDialog(frame, "Deadlines updated successfully!");
                frame.dispose();
            }
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(frame, "Invalid Date Format. Please use YYYY-MM-DD", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}