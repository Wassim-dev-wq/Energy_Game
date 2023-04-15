package InterfaceGraphique.Game;

import javax.swing.*;
import java.awt.*;

public class Game extends JFrame {

    public void updateWindowSize(int height, int length) {
        setSize(length * 120 + 100 , height * 120 + 150);
    }

    public static void main(String[] args) {
        Game game = new Game();

        LevelSelection levelSelection = new LevelSelection(game);

        JPanel mainPanel = new JPanel(new BorderLayout());
        game.setContentPane(mainPanel);

        mainPanel.add(levelSelection, BorderLayout.CENTER);

        game.setTitle("Energy Game");
        game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        game.pack();
        game.setLocationRelativeTo(null);
        game.setVisible(true);
    }
}
