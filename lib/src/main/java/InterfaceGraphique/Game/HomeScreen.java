package InterfaceGraphique.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomeScreen extends JPanel {
    private Game game;

    public HomeScreen(Game game) {
        this.game = game;
        setLayout(new GridLayout(3, 1, 15, 15));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setBackground(Color.DARK_GRAY);
        JLabel title = new JLabel("Welcome to the Game", SwingConstants.CENTER);
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        // levels
        JButton gameLevelsButton = new JButton("Game Levels");
        gameLevelsButton.setFont(new Font("Arial", Font.PLAIN, 18));
        gameLevelsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LevelSelection levelSelection = new LevelSelection(game, "game_levels");
                game.setContentPane(levelSelection);
                game.pack();
                game.setLocationRelativeTo(null);
            }
        });
        JButton createdLevelsButton = new JButton("Levels Created");
        createdLevelsButton.setFont(new Font("Arial", Font.PLAIN, 18));
        createdLevelsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LevelSelection levelSelection = new LevelSelection(game, "created_levels");
                game.setContentPane(levelSelection);
                game.pack();
                game.setLocationRelativeTo(null);
            }
        });
        add(title);
        add(gameLevelsButton);
        add(createdLevelsButton);
    }
}
