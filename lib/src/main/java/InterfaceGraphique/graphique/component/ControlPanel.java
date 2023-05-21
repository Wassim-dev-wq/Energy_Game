package InterfaceGraphique.graphique.component;

import InterfaceGraphique.Game.Game;
import InterfaceGraphique.Game.LevelSelection;
import InterfaceGraphique.algorithm.Level;
import InterfaceGraphique.graphique.ComponentType.ImageLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ControlPanel extends JPanel {
    private Game game;
    private JLabel scoreLabel;
    private JLabel levelLabel;
    private int level;
    private String levelsType;
    private LevelSelection levelSelection;

    public ControlPanel(Game game, int level,String levelsType, LevelSelection levelSelection) {
        this.levelSelection = levelSelection;
        this.levelsType = levelsType;
        this.game = game;
        this.level = level;
        setLayout(new FlowLayout(FlowLayout.LEFT));
        setBackground(Color.LIGHT_GRAY);

        Color buttonTextColor = Color.BLACK;
        createHomeButton(buttonTextColor);
        createHelpButton(buttonTextColor);
        if (level>=0){
            createSolutionButton(buttonTextColor);
            createScoreLabel();
        }
        createLevelLabel();
    }

    private void createHomeButton(Color buttonTextColor) {
        JButton homeButton = new JButton("Home");
        homeButton.setBackground(Color.WHITE);
        homeButton.setForeground(buttonTextColor);
        homeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.setContentPane(levelSelection);
                game.pack();
                game.setLocationRelativeTo(null);
            }
        });
        add(homeButton);
    }

    private void createHelpButton(Color buttonTextColor) {
        JButton helpButton = new JButton("Help");
        helpButton.setBackground(Color.WHITE);
        helpButton.setForeground(buttonTextColor);
        helpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO
                JOptionPane.showMessageDialog(game, "Welcome to Swing!");
            }
        });
        add(helpButton);
    }

    private void createSolutionButton(Color buttonTextColor) {
        JButton solutionButton = new JButton("Solution");
        solutionButton.setBackground(Color.WHITE);
        solutionButton.setForeground(buttonTextColor);
        solutionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String file = "/Levels/levels_solution/level" + level + ".nrg";
                System.out.println(file);
                test_png t = new test_png(file);
                JOptionPane.showMessageDialog(game, t);
            }
        });
        add(solutionButton);
    }

    private void createScoreLabel() {
        scoreLabel = new JLabel("Score: " + Board.score);
        scoreLabel.setForeground(Color.black);
        add(scoreLabel);
    }
    private void createLevelLabel() {
        levelLabel = new JLabel("  Niveau: " + levelSelection.getLevelNumber());
        levelLabel.setForeground(Color.black);
        add(levelLabel);
    }
    public void updateScore(int score) {
        scoreLabel.setText("Score: " + score);
    }
}
