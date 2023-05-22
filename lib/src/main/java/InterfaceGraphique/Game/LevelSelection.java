package InterfaceGraphique.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Objects;

import InterfaceGraphique.graphique.component.Board;
import InterfaceGraphique.graphique.component.Empty_Board;

public class LevelSelection extends JPanel {
    private Game game;
    private Board board;
    private Empty_Board emptyBoard;
    private String buttonType;
    private int levelNumber= 10;
    private int currentLevel ;
    private int created_levels ;
    public LevelSelection(Game game, String buttonType) {
        String userDirectory = new File("").getAbsolutePath();
        File createdLevelsDir = new File(userDirectory + "/lib/src/main/resources/Levels/created_levels/");
        File[] files = createdLevelsDir.listFiles();
        int created_levels = (files != null) ? files.length : 0;
        this.created_levels = created_levels;
        this.game = game;
        this.buttonType = buttonType;
        setLayout(new BorderLayout());
        JLabel titleLabel = new JLabel("Energy Game", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 30));
        add(titleLabel, BorderLayout.NORTH);
        JPanel levelsPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gridBag = new GridBagConstraints();
        gridBag.insets = new Insets(10, 10, 10, 10);
        if(buttonType.equals("created_levels")){
            for (int i = 1; i <= created_levels; i++) {
                addButton("Level " + i, i, levelsPanel, gridBag);
                JButton button_create = createNewButton(gridBag, levelNumber);
                levelsPanel.add(button_create,gridBag);
            }
        }else{
            for (int i = 1; i <= levelNumber; i++) {
                addButton("Level " + i, i, levelsPanel, gridBag);
            }
        }

        JButton homeButton = new JButton("Home");
        homeButton.setFont(new Font("Arial", Font.BOLD, 16));
        homeButton.setBackground(new Color(51, 150, 255));
//        homeButton.setForeground(Color.WHITE);
        homeButton.setForeground(Color.BLACK);
        homeButton.setFocusPainted(false);
        homeButton.setPreferredSize(new Dimension(80, 40));
        homeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.setContentPane(new HomeScreen(game));
                game.pack();
                game.setLocationRelativeTo(null);
            }
        });
        add(homeButton, BorderLayout.NORTH);

        add(levelsPanel, BorderLayout.CENTER);
    }

    private JButton createNewButton(GridBagConstraints gridBag, int total_level) {
        JButton button_create = new JButton("Create");
        button_create.setPreferredSize(new Dimension(120, 120));
        button_create.setFont(new Font("Arial", Font.PLAIN, 18));
        button_create.setBackground(Color.LIGHT_GRAY);
        int row = (total_level+1 - 1) / 3;
        int col = (total_level+1 - 1) % 3;
        gridBag.gridx = col;
        gridBag.gridy = row;
        button_create.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                createNewLevel();
            }
        });
        return button_create;
    }

    private void createNewLevel() {
        String height[]={"2","3","4","5","6","7","8"};
        String width[]={"2","3","4","5","6","7","8"};
        String format[] = {"Square", "Hexagone"};

        JComboBox combo_width=new JComboBox(width);
        JComboBox combo_height=new JComboBox(height);
        JComboBox combo_format=new JComboBox(format);
        JPanel myPanel = new JPanel();
        myPanel.add(new JLabel("Width:"));
        myPanel.add(combo_width);
        myPanel.add(Box.createHorizontalStrut(15)); // a spacer
        myPanel.add(new JLabel("Height:"));
        myPanel.add(combo_height);
        myPanel.add(Box.createHorizontalStrut(15)); // a spacer
        myPanel.add(new JLabel("Format:"));
        myPanel.add(combo_format);
        JOptionPane.showMessageDialog(null, myPanel);
        int height_ = Integer.parseInt(combo_height.getSelectedItem().toString());
        int width_ =Integer.parseInt(combo_width.getSelectedItem().toString());
        JPanel gamePanel = new JPanel(new BorderLayout());
        created_levels++;
        emptyBoard = new Empty_Board(game, width_, height_, combo_format.getSelectedItem().toString(), created_levels, LevelSelection.this, buttonType);
        emptyBoard.setBackground(Color.BLACK);
        game.updateWindowSize(emptyBoard.getHeight(), emptyBoard.getWidth());
        Dimension boardSize = new Dimension(10 * 120, 10 * 120);
        emptyBoard.setPreferredSize(boardSize);
        gamePanel.add(emptyBoard, BorderLayout.CENTER);
        game.setContentPane(gamePanel);
        game.pack();
        game.setLocationRelativeTo(null);
    }


    public int getLevelNumber(){
        return currentLevel;
    }
    private void addButton(String label, int levelNumber, JPanel levelsPanel, GridBagConstraints gridBag) {
        JButton button = new JButton(label);
        button.setPreferredSize(new Dimension(120, 120));
        button.setFont(new Font("Arial", Font.PLAIN, 18));
        button.setBackground(Color.LIGHT_GRAY);
//        button.setForeground(Color.WHITE);
        button.setForeground(Color.BLACK);
        button.setFocusPainted(false);

        // grid position
        int row = (levelNumber - 1) / 3;
        int col = (levelNumber - 1) % 3;
        gridBag.gridx = col;
        gridBag.gridy = row;
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JPanel gamePanel = new JPanel(new BorderLayout());
                currentLevel = levelNumber;
                board = new Board(game, levelNumber,buttonType,LevelSelection.this);
                board.setBackground(Color.BLACK);
                String levelFilePath = "/Levels/"+buttonType+"/level" + levelNumber + ".nrg";
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

    public void reload() {
        removeAll(); // remove all components
        JLabel titleLabel = new JLabel("Energy Game", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 30));
        add(titleLabel, BorderLayout.NORTH);
        JButton homeButton = new JButton("Home");
        homeButton.setFont(new Font("Arial", Font.BOLD, 16));
        homeButton.setBackground(new Color(51, 150, 255));
//        homeButton.setForeground(Color.WHITE);
        homeButton.setForeground(Color.BLACK);
        homeButton.setFocusPainted(false);
        homeButton.setPreferredSize(new Dimension(80, 40));
        homeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.setContentPane(new HomeScreen(game));
                game.pack();
                game.setLocationRelativeTo(null);
            }
        });

        add(homeButton, BorderLayout.NORTH);
        JPanel levelsPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gridBag = new GridBagConstraints();
        gridBag.insets = new Insets(10, 10, 10, 10);
        if(buttonType.equals("created_levels")){
            for (int i = 1; i <= created_levels; i++) {
                addButton("Level " + i, i, levelsPanel, gridBag);
                JButton button_create = createNewButton(gridBag, levelNumber);
                levelsPanel.add(button_create,gridBag);
            }
        }else{
            for (int i = 1; i <= levelNumber; i++) {
                addButton("Level " + i, i, levelsPanel, gridBag);
            }
        }
        add(levelsPanel, BorderLayout.CENTER);
        revalidate();
        repaint();
    }
}
