import javax.swing.*;

public class CarConfigDialog {
    public static int baseSpeed = 1; // affects car movement
    public static int botSpeed = 2;  // affects difficulty

    public static void configure() {
        try {
            String speed = JOptionPane.showInputDialog(null, "Enter your car speed (1-5):", "Configure Car", JOptionPane.PLAIN_MESSAGE);
            String bot = JOptionPane.showInputDialog(null, "Enter bot difficulty (1-5):", "Configure Bot", JOptionPane.PLAIN_MESSAGE);
            baseSpeed = Math.min(5, Math.max(1, Integer.parseInt(speed)));
            botSpeed = Math.min(5, Math.max(1, Integer.parseInt(bot)));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Invalid input. Using default config.");
        }
    }
}
