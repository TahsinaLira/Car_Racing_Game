import javax.swing.*;

public class NameInput {
    public static String playerName;

    public static void show() {
        playerName = JOptionPane.showInputDialog(null, "Enter your name:", "Player Login", JOptionPane.QUESTION_MESSAGE);
        if (playerName == null || playerName.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Name cannot be empty!");
            System.exit(0);
        }
        DBManager.savePlayer(playerName);
    }
}
