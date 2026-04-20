import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class LeaderboardDialog extends JDialog {
    public LeaderboardDialog() {
        setTitle("Leaderboard");
        setSize(300, 200);
        setLayout(new BorderLayout());

        JTextArea area = new JTextArea();
        area.setEditable(false);
        area.setFont(new Font("Monospaced", Font.PLAIN, 14));

        StringBuilder sb = new StringBuilder("Top 5 High Scores:\n\n");
        try {
            ResultSet rs = DBManager.getTopPlayers();
            while (rs != null && rs.next()) {
                sb.append(rs.getString("name")).append(" - ").append(rs.getInt("highscore")).append("\n");
            }
        } catch (Exception e) {
            sb.append("Error loading leaderboard.");
        }

        area.setText(sb.toString());
        add(new JScrollPane(area), BorderLayout.CENTER);

        setLocationRelativeTo(null);
        setVisible(true);
    }
}
