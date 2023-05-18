package InterfaceGraphique.graphique.component;

import InterfaceGraphique.Game.Game;
import InterfaceGraphique.algorithm.ElectricityHandler;
import InterfaceGraphique.algorithm.Level;
import InterfaceGraphique.graphique.ComponentType.Component;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class Board extends JPanel {
    private Game game;
    private Level level;
    private Component[][] components;
    private Map<String, Component> directionToComponentMap = new HashMap<>(); // to keep track on the directions of the components


    public Board(Game game) {
        this.game = game;
        setLayout(new BorderLayout());
        add(new ControlPanel(game), BorderLayout.NORTH);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("Mouse clicked at: (" + e.getX() + ", " + e.getY() + ")");
                handleClick(e.getX(), e.getY());
            }
        });

    }

    public int getLevelWidth() {
        return level.getWidth();
    }

    public int getLevelHeight() {
        return level.getHeight();
    }

    // TODO
    public void updateScore(int score) {
        ControlPanel controlPanel = (ControlPanel) getComponent(0);
        controlPanel.updateScore(score);
    }

    public void loadAndDisplayLevel(String levelFilePath) {
        level = new Level(levelFilePath);
        components = new Component[level.getHeight()][level.getWidth()];
        game.updateWindowSize(level.getHeight(), level.getWidth());
    }

    private void handleClick(int x, int y) {
        int rowIndex;
        int columnIndex;

        // Handling for square tiles
        if (level.getFormat().equals("S")) {
            rowIndex = (y - 70) / 120; // the -70 because I added it to the window in the game
            columnIndex = (x - 50) / 120;
        }
        // Handling for hexagonal tiles
        else {
            rowIndex = (y - 60) / 104;
            if (rowIndex % 2 == 0) {
                columnIndex = (x - 70) / 90;
            } else {
                columnIndex = (x - 50) / 90;
            }
        }
        // Handle click on a component.
        if (rowIndex >= 0 && rowIndex < components.length &&
                columnIndex >= 0 && columnIndex < components[rowIndex].length) {
            Component clickedComponent = components[rowIndex][columnIndex];
            if (clickedComponent != null) {
                if (clickedComponent.containsPoint(x, y)) {
                    // Remove old directions
                    for (String direction : clickedComponent.getDirections()) {
                        directionToComponentMap.remove(direction);
                    }
                    clickedComponent.setOn(true);
                    clickedComponent.rotate();
                    for (String direction : clickedComponent.getDirections()) {
                        directionToComponentMap.put(direction, clickedComponent);
                    }
                }
                ElectricityHandler electricityHandler = level.getElectricityHandler();
                electricityHandler.propagateElectricity();
                this.repaint();
//                for (boolean[] connects: electricityHandler.getHasElectric()){
//                    for (boolean c: connects){
//                        System.out.println(c);
//                    }
//                }
//
//                for (String[] components_: electricityHandler.getComponents()){
//                    for (String c_: components_){
//                        System.out.println(c_);
//                    }
//                }
//
//                for (List<String>[] directions_: electricityHandler.getDirections()){
//                    for (List<String> d_: directions_){
//                        System.out.println(d_);
//                    }
//                }
//
//                System.out.println(directionToComponentMap);
            }
        }
    }

    private void createComponents() {
        int horizontalAlignment = (getWidth() - level.getWidth() * 120) / 2;
        int verticalAlignment = (getHeight() - level.getHeight() * 120) / 2;
        for (int j = 0; j < level.getHeight(); j++) {
            for (int k = 0; k < level.getWidth(); k++) {
                String tileType = level.getComponents()[j][k];
                System.out.println(level.getElectricityHandler().getHasElectric()[j][k]);
                boolean has_electric = level.getElectricityHandler().getHasElectric()[j][k];
                int y_value = 0;
                int x_value = 0;
                int width = 120;
                int height = 0;
                if (level.getFormat().equals("S")) {
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
                Component component = Components.createComponent(tileType, x_value, y_value, width, height, has_electric, level.getDirections()[j][k], level.getFormat());
                if (component != null) {
                    components[j][k] = component;
                    for (String direction : component.getDirections()) {
                        directionToComponentMap.put(direction, component);
                    }
                }
            }
        }
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (level != null) {
            game.updateWindowSize(level.getHeight(), level.getWidth());
        }
        // need to be called only when level change //TODO
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
    public Component getComponentInDirection(String direction) {
        return directionToComponentMap.get(direction);
    }

}
