import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import javax.swing.*;

public class ComputeGrades {
    private JPanel panel;
    private MainFrame mainFrame;
    private JComboBox<String> course_box; 
    private JButton enter_btn, back_btn;

    public ComputeGrades(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        panel = new JPanel(new BorderLayout());

        JLabel title = new JLabel("Compute Grades Window", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(title, BorderLayout.NORTH);

        JPanel form = new JPanel(new GridLayout(3, 2, 10, 10));

        JLabel enter_code = new JLabel("Select the Course Code for which to compute the grades: ");
        course_box = new JComboBox<>();
        enter_btn = new JButton("Enter");
        back_btn = new JButton("Back");
        //int row = 0;

        form.add(enter_code);
        form.add(course_box);
        form.add(enter_btn);
        form.add(back_btn);

        panel.add(form, BorderLayout.CENTER);

        // go back to instructor dashboard
        back_btn.addActionListener(e -> mainFrame.show_card("instructor_dashboard"));
        enter_btn.addActionListener(e->{
            String code = (String) course_box.getSelectedItem();
            int row = get_result(code);
            System.out.println(row);
        }
        );
    }

    public ArrayList<Integer> get_weightage(String code){
        ArrayList<Integer> x = new ArrayList<>();
        int q;
        int a;
        int m;
        int e;
        int g;
        String select = "SELECT * FROM courses WHERE code = ?;";
        try (Connection conn = DatabaseConnection.getConnection2()) {
            PreparedStatement ps = conn.prepareStatement(select);
            ps.setString(1, code);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                q = Integer.parseInt(rs.getString("quiz"));
                a = Integer.parseInt(rs.getString("assignment"));
                m = Integer.parseInt(rs.getString("midsem"));
                e = Integer.parseInt(rs.getString("endsem"));
                g = Integer.parseInt(rs.getString("group_project"));
                x.add(q);
                x.add(a);
                x.add(m);
                x.add(e);
                x.add(g);
            } else {
                //System.out.println("⚠️ No record found for " + username + " in " + subject);
            }
        } catch (SQLException e1) {
            e1.printStackTrace();
        }

        return x;
    }
    public int get_result(String code){
        String select = "SELECT * FROM grades WHERE subject=?;";
        int q;
        int a;
        int m;
        int e;
        int g;
        int row = 0;
        String user_name;
        int total;
        try (Connection conn = DatabaseConnection.getConnection2()) {
            PreparedStatement ps = conn.prepareStatement(select);
            ps.setString(1, code);

            ResultSet rs = ps.executeQuery();

            ArrayList<Integer> weights = get_weightage(code);
            int q1 = weights.get(0);
            int a1 = weights.get(1);
            int m1 = weights.get(2);
            int e1 = weights.get(3);
            int g1 = weights.get(4);
            while (rs.next()) {
                row +=1;
                total = 0;
                q = Integer.parseInt(rs.getString("quiz_marks"));
                a = Integer.parseInt(rs.getString("assignment_marks"));
                m = Integer.parseInt(rs.getString("midsem_marks"));
                e = Integer.parseInt(rs.getString("endsem_marks"));
                g = Integer.parseInt(rs.getString("group_project_marks"));
                user_name = rs.getString("user_name");
                total = (q*q1 + a*a1 + m*m1 + e*e1 + g*g1)/100;
                System.out.println("Computing grade for " + user_name + " total=" + total);
                System.out.println("Weights: " + q1 + "," + a1 + "," + m1 + "," + e1 + "," + g1);
                if (total >= 95) {
                    set_alphabet("A+", user_name, code);
                } else if (total >= 90) {
                    set_alphabet("A", user_name, code);
                } else if (total >= 80) {
                    set_alphabet("A-", user_name, code);
                } else if (total >= 70) {
                    set_alphabet("B", user_name, code);
                } else if (total >= 60) {
                    set_alphabet("B-", user_name, code);
                } else if (total >= 50) {
                    set_alphabet("C", user_name, code);
                } else {
                    set_alphabet("F", user_name, code);
                }
            } 
        } catch (SQLException e3) {
            e3.printStackTrace();
        }
        return row;

    }
    public void set_instructor(Instructor instructor) { // this loads the courses
        course_box.removeAllItems();
        for (String course : instructor.get_course_list()) {
            course_box.addItem(course);
        }
    }
    public void set_alphabet(String s, String username, String code){
        String update = "UPDATE grades SET grad = ? WHERE user_name=? AND subject=?";
        int row = 0;
        try (Connection conn = DatabaseConnection.getConnection2();
            PreparedStatement ps = conn.prepareStatement(update)) {
            ps.setString(1, s);
            ps.setString(2, username);
            ps.setString(3, code);
            row = ps.executeUpdate();
            System.out.println(s);
        }
        catch(SQLException e2){
            e2.printStackTrace();
        }
        //return row;
    }
    public int set_grades(String username, String subject, int quiz, int ass, int mid, int end, int group){
        String update = "UPDATE grades SET quiz_marks=?, assignment_marks=?, midsem_marks=?, endsem_marks=?, group_project_marks=? WHERE user_name=? AND subject=?";
        int row = 0;
        try (Connection conn = DatabaseConnection.getConnection2();
            PreparedStatement ps = conn.prepareStatement(update)) {

            ps.setInt(1, quiz);
            ps.setInt(2, ass);
            ps.setInt(3, mid);
            ps.setInt(4, end);
            ps.setInt(5, group);
            ps.setString(6, username);
            ps.setString(7, subject);

            row = ps.executeUpdate();

            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error updating grades: " + e.getMessage());
            }
            return row;
    }
    public ArrayList<String> get_grades(String username, String subject) {
        ArrayList<String> x = new ArrayList<>();
        String selectQuery = "SELECT * FROM grades WHERE user_name=? AND subject=?";

        try (Connection conn = DatabaseConnection.getConnection2()) {
            PreparedStatement ps = conn.prepareStatement(selectQuery);
            ps.setString(1, username);
            ps.setString(2, subject);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                x.add(rs.getString("quiz_marks"));
                x.add(rs.getString("assignment_marks"));
                x.add(rs.getString("midsem_marks"));
                x.add(rs.getString("endsem_marks"));
                x.add(rs.getString("group_project_marks"));
            } else {
                System.out.println("⚠️ No record found for " + username + " in " + subject);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return x;
    }

    public JPanel get_panel() {
        return panel;
    }
}
