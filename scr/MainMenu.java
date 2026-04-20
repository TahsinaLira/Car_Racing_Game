import javax.swing.*;
import java.awt.*;

public class MainMenu extends JFrame {
    private boolean isConfigured = false;

    public MainMenu() {
        setTitle("RaceCraft");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Custom panel with background image
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon icon = new ImageIcon("images/menu_bg.jpg");
                g.drawImage(icon.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };
        panel.setLayout(null);  // Absolute positioning

        // Buttons
        JButton configureBtn = new JButton("Configure Car");
        configureBtn.setBounds(120, 40, 150, 30);
        JButton startBtn = new JButton("Start Game");
        startBtn.setBounds(120, 80, 150, 30);
        JButton howToBtn = new JButton("How to Play");
        howToBtn.setBounds(120, 120, 150, 30);
        JButton leaderboardBtn = new JButton("Leaderboard");
        leaderboardBtn.setBounds(120, 160, 150, 30);
        JButton exitBtn = new JButton("Exit");
        exitBtn.setBounds(120, 200, 150, 30);

        // Add buttons to panel
        panel.add(configureBtn);
        panel.add(startBtn);
        panel.add(howToBtn);
        panel.add(leaderboardBtn);
        panel.add(exitBtn);

        // Add panel to frame
        add(panel);

        // Button actions
        configureBtn.addActionListener(e -> {
            CarConfigDialog.configure(); // your method
            isConfigured = true;
        });

        startBtn.addActionListener(e -> {
            if (!isConfigured) {
                JOptionPane.showMessageDialog(this, "Please configure your car first.");
            } else {
                new MyGameFrame(); // launch game
                dispose();
            }
        });

        howToBtn.addActionListener(e ->
            JOptionPane.showMessageDialog(this, "Use Arrow Keys to Move. Avoid crashing!\nEarn high scores!", "How to Play", JOptionPane.INFORMATION_MESSAGE)
        );

        leaderboardBtn.addActionListener(e -> new LeaderboardDialog());
        exitBtn.addActionListener(e -> System.exit(0));

        setLocationRelativeTo(null);
        setVisible(true);
    }
}
