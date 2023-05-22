package InterfaceGraphique.Game;

import javax.swing.*;
import java.awt.*;
import java.io.*;

public class Game extends JFrame {
    public static int score = 0;

    public void updateWindowSize(int height, int length) {
        setSize(length * 120 + 100 , height * 120 + 150);
    }

    public static void main(String[] args){
        Game game = new Game();

        HomeScreen homeScreen = new HomeScreen(game);

        JPanel mainPanel = new JPanel(new BorderLayout());
        game.setContentPane(mainPanel);

        mainPanel.add(homeScreen, BorderLayout.CENTER);

        game.setTitle("Energy Game");
        game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        game.pack();
        game.setLocationRelativeTo(null);
        game.setVisible(true);
    }
}
