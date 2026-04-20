import java.sql.*;

public class DBManager {
    private static final String URL = "jdbc:mysql://localhost:3306/car_game";
    private static final String USER = "root";
    private static final String PASSWORD = "ict10"; 

    private static Connection conn;

    public static Connection getConnection() throws SQLException {
        if (conn == null || conn.isClosed()) {
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
        }
        return conn;
    }

    public static void savePlayer(String name) {
        try {
            Connection conn = getConnection();
            String query = "INSERT INTO players (name) VALUES (?)";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, name);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ResultSet getTopPlayers() {
        try {
            Connection conn = getConnection();
            String query = "SELECT name, highscore FROM players ORDER BY highscore DESC LIMIT 5";
            Statement stmt = conn.createStatement();
            return stmt.executeQuery(query);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void updateScore(String name, int score) {
        try {
            Connection conn = getConnection();
            String query = "UPDATE players SET score = ?, highscore = GREATEST(highscore, ?) WHERE name = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, score);
            ps.setInt(2, score);
            ps.setString(3, name);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
