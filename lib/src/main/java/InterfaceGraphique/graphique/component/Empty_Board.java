package InterfaceGraphique.graphique.component;


import InterfaceGraphique.Game.Game;
import InterfaceGraphique.Game.LevelSelection;
import InterfaceGraphique.algorithm.Level;
import InterfaceGraphique.graphique.ComponentType.Component;


import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.util.ArrayList;

public class Empty_Board extends JPanel {
    private Game game;
    private int width;
    private int height;
    private String format;
    private Component[][] components;
    private LevelSelection levelSelection;
    private String levelsType;
    private Level level;

    public Empty_Board(Game game, int width, int height, String format, int levelNumber, LevelSelection levelSelection,String levelsType){
        this.levelsType = levelsType;
        this.game = game;
        this.width = width;
        this.height = height;
        this.levelSelection = levelSelection;
        if (format.equals("Square")){
            this.format = "S";
        }else{
            this.format = "H";
        }

        this.components = new Component[height][width];
        setLayout(new BorderLayout());
        add(new ControlPanel(game, -1, levelsType,levelSelection), BorderLayout.NORTH);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("Mouse clicked at: (" + e.getX() + ", " + e.getY() + ")");
                handleClick(e.getX(), e.getY(), "level"+levelNumber);
            }
        });
        JButton saveButton = new JButton("Save Level");
        String levelName = "level"+levelNumber;
        String directoryPath = "src/main/resources/Levels/created_levels/";
        String fileName = levelName + ".nrg";
        String filePath = directoryPath + fileName;
        this.saveLevelToFile(filePath);
        saveButton.setForeground(Color.WHITE);
        saveButton.setBackground(new Color(59, 89, 182));
        saveButton.setFont(new Font("Arial", Font.BOLD, 14));
        saveButton.setFocusPainted(false);
        add(saveButton, BorderLayout.SOUTH);
        saveButton.addActionListener(e -> {
            boolean isSaved = this.saveLevelToFile(filePath);
            if (isSaved) {
                this.setVisible(false);
                game.setContentPane(levelSelection);
                game.pack();
                game.setLocationRelativeTo(null);
                this.levelSelection.reload();
            }
        });

    }

    public void loadAndDisplayLevel(String levelFilePath) {
        level = new Level(levelFilePath);
        components = new Component[level.getHeight()][level.getWidth()];
        game.updateWindowSize(level.getHeight(), level.getWidth());
    }
    private void handleClick(int x, int y, String levelName) {
        int clickedRow = y / 120;
        int clickedCol = x / 120;
        Component clickedComponent = components[clickedRow][clickedCol];
        if (clickedComponent != null) {
            String currentState = clickedComponent.toString();
            String newState = ".";
            switch (currentState) {
                case ".":
                    newState = "L";
                    System.out.println("YES");
                    break;
                case "L":
                    newState = "W";
                    break;
                case "W":
                    newState = ".";
                    break;
            }
            int x_value = clickedCol * 120;
            int y_value = clickedRow * 120;
            int width = 120;
            int height = this.format.equals("S") ? 120 : 104;
            components[clickedRow][clickedCol] = Components.createComponent(newState, x_value, y_value, width, height, true, new ArrayList<>(), format);
            String directoryPath = "src/main/resources/Levels/created_levels/";
            String fileName = levelName + ".nrg";
            String filePath = directoryPath + fileName;
            boolean isSaved = this.saveLevelToFile(filePath);
            loadAndDisplayLevel(filePath);
            this.repaint();
        }
    }
    public boolean saveLevelToFile(String filename) {
        try {
            PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(filename)));
            out.println(this.height + " " + this.width + " " + this.format);

            for (int i = 0; i < components.length; i++) {
                for (int j = 0; j < components[i].length; j++) {
                    if(components[i][j] instanceof InterfaceGraphique.graphique.ComponentType.Empty){
                        out.print(". 1 2 ");
                    }
                    else if(components[i][j] instanceof InterfaceGraphique.graphique.ComponentType.Lamp){
                        out.print("L 1 ");
                    }
                    else if(components[i][j] instanceof InterfaceGraphique.graphique.ComponentType.Wifi){
                        out.print("W 1 ");
                    }
                    else if(components[i][j] instanceof InterfaceGraphique.graphique.ComponentType.Source){
                        out.print("S 1 ");
                    }
                }
                out.println();
            }
            out.flush();
            out.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }


    private void createComponents() {
        int horizontalAlignment = (getWidth() - this.width * 120) / 2;
        int verticalAlignment = (getHeight() - this.height * 120) / 2;
        for (int j = 0; j < this.height; j++) {
            for (int k = 0; k < this.width; k++) {
                int y_value = 0;
                int x_value = 0;
                int width = 120;
                int height = 0;
                if (this.format.equals("S")) {
                    x_value = k * 120 + horizontalAlignment;
                    y_value = j * 120 + verticalAlignment;
                    height = 120;
                } else {
                    int y = 0;
                    if (k % 2 == 1) y = 52;
                    x_value = k * 90 + horizontalAlignment;
                    y_value = (j * 104) + y + verticalAlignment;
                    height = 104;
                }
                Component component = Components.createComponent(".", x_value, y_value, width, height, true, new ArrayList<>(), format);
                components[j][k] = component;
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        game.updateWindowSize(this.height, this.width);
        createComponents();
        for (int i = 0; i < components.length; i++) {
            for (int j = 0; j < components[i].length; j++) {
                Component component = components[i][j];
                if (component != null) {
                    component.draw(g);
                }
            }
        }
    }
}
