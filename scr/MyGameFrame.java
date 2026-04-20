import javax.swing.*;
import java.awt.*;

public class MyGameFrame extends JFrame {
    public MyGameFrame() {
        setTitle("RaceCraft");
        setSize(500, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        Game game = new Game();
        add(game, BorderLayout.CENTER);

        JLabel scoreLabel = new JLabel("Score: 0     High Score: 0");
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 16));
        add(scoreLabel, BorderLayout.NORTH);

        game.setScoreLabel(scoreLabel); // link label to game

        setVisible(true);

        new Thread(() -> game.startGameLoop()).start(); // start game
    }
}
