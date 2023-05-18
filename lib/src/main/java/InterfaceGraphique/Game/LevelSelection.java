package InterfaceGraphique.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.InputStream;

import InterfaceGraphique.graphique.component.Board;

public class LevelSelection extends JPanel {
    private Game game;
    private Board board;

    public LevelSelection(Game game) {
        this.game = game;

        setLayout(new BorderLayout());

        // Add a title label
        JLabel titleLabel = new JLabel("Energy Game", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 30));
        add(titleLabel, BorderLayout.NORTH);

        // Use a GridBagLayout to allow more control over button placement
        JPanel levelsPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gridBag = new GridBagConstraints();
        gridBag.insets = new Insets(10, 10, 10, 10);

        for (int i = 1; i <= 10; i++) {
            addButton("Level " + i, i, levelsPanel, gridBag);
        }

        add(levelsPanel, BorderLayout.CENTER);
    }

    private void addButton(String label, int levelNumber, JPanel levelsPanel, GridBagConstraints gridBag) {
        JButton button = new JButton(label);

        // Style the buttons
        button.setPreferredSize(new Dimension(120, 120));
        button.setFont(new Font("Arial", Font.PLAIN, 18));
        button.setBackground(Color.DARK_GRAY);
//        button.setForeground(Color.WHITE);
        button.setForeground(Color.BLACK);
        button.setFocusPainted(false);

        // grid position
        int row = (levelNumber - 1) / 3;
        int col = (levelNumber - 1) % 3;
        gridBag.gridx = col;
        gridBag.gridy = row;

        if (levelNumber == 10) {
            gridBag.gridwidth = 1;
            gridBag.gridx = 1;
        } else {
            gridBag.gridwidth = 1;
        }
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JPanel gamePanel = new JPanel(new BorderLayout());
                board = new Board(game);
                board.setBackground(Color.BLACK);
                String levelFilePath = "/Levels/game_levels/level" + levelNumber + ".nrg";
                board.loadAndDisplayLevel(levelFilePath);
                Dimension boardSize = new Dimension(board.getLevelWidth() * 120, board.getLevelHeight() * 120);
                board.setPreferredSize(boardSize);
                gamePanel.add(board, BorderLayout.CENTER);
                game.setContentPane(gamePanel);
                game.pack();
                game.setLocationRelativeTo(null);
            }
        });

        levelsPanel.add(button, gridBag);
    }
}
