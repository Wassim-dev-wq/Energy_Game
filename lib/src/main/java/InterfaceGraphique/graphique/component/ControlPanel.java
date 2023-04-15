package InterfaceGraphique.graphique.component;

import InterfaceGraphique.Game.Game;
import InterfaceGraphique.Game.LevelSelection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControlPanel extends JPanel {
    private Game game;
    private JLabel scoreLabel;

    public ControlPanel(Game game) {
        this.game = game;
        setLayout(new FlowLayout(FlowLayout.LEFT));
        setBackground(Color.DARK_GRAY);

        Color buttonTextColor = Color.WHITE;
        createHomeButton(buttonTextColor);
        createHelpButton(buttonTextColor);
        createSolutionButton(buttonTextColor);
        createScoreLabel();
    }

    private void createHomeButton(Color buttonTextColor) {
        JButton homeButton = new JButton("Home");
        homeButton.setBackground(Color.DARK_GRAY);
        homeButton.setForeground(buttonTextColor);
        homeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LevelSelection levelSelection = new LevelSelection(game);
                game.setContentPane(levelSelection);
                game.pack();
                game.setLocationRelativeTo(null);
            }
        });
        add(homeButton);
    }

    private void createHelpButton(Color buttonTextColor) {
        JButton helpButton = new JButton("Help");
        helpButton.setBackground(Color.DARK_GRAY);
        helpButton.setForeground(buttonTextColor);
        helpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO
            }
        });
        add(helpButton);
    }

    private void createSolutionButton(Color buttonTextColor) {
        JButton solutionButton = new JButton("Solution");
        solutionButton.setBackground(Color.DARK_GRAY);
        solutionButton.setForeground(buttonTextColor);
        solutionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO
            }
        });
        add(solutionButton);
    }

    private void createScoreLabel() {
        scoreLabel = new JLabel("Score: 0");
        scoreLabel.setForeground(Color.YELLOW);
        add(scoreLabel);
    }

    public void updateScore(int score) {
        scoreLabel.setText("Score: " + score);
    }
}
