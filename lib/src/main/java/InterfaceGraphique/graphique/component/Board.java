package InterfaceGraphique.graphique.component;

import InterfaceGraphique.Game.Game;
import InterfaceGraphique.algorithm.Level;
import InterfaceGraphique.graphique.ComponentType.Component;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class Board extends JPanel {
    private Game game;
    private Level level;
    private List<Component> components;
    public Board(Game game) {
        this.game = game;
        components = new ArrayList<>();
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
        game.updateWindowSize(level.getHeight(), level.getWidth());
    }
    private void handleClick(int x, int y) {
        for (Component component : components) {
            if (component.containsPoint(x, y)) {
                System.out.println("Component found at: (" + x + ", " + y + ")");
                component.rotate();
                repaint();
                break;
            }
        }
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (level != null) {
            game.updateWindowSize(level.getHeight(), level.getWidth());
        }
        components.clear();
        int xOffset = (getWidth() - level.getWidth() * 120) / 2;
        int yOffset = (getHeight() - level.getHeight() * 120) / 2;
        for (int j = 0; j < level.getHeight(); j++) {
            for (int k = 0; k < level.getWidth(); k++) {
                int index = j * level.getWidth() + k;
                String tileType = level.getComponents().get(index);
                boolean has_electric = level.getElectricityHandler().getHasElectric().get(index);
                Component component = null;
                int y_value = 0;
                int x_value = 0;
                int width = 120;
                int height = 0;

                if (level.getFormat().equals("S")) {
                    x_value = k * 120 + xOffset;
                    y_value = j * 120 + yOffset;
                    height = 120;
                } else {
                    int y = 0;
                    if (k % 2 == 1) y = 52;
                    x_value = k * 90 + xOffset;
                    y_value = (j * 104) + y + yOffset;
                    height = 104;
                }

                component = Components.createComponent(tileType, x_value, y_value, width, height, has_electric, level.getDirections().get(index), level.getFormat());
                if (component != null) {
                    components.add(component);
                }
            }
        }

        for (Component component : components) {
            component.draw(g);
        }
    }
}
