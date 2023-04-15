package InterfaceGraphique.graphique.component;

import InterfaceGraphique.Game.Game;
import InterfaceGraphique.algorithm.Level;
import InterfaceGraphique.graphique.ComponentType.*;
import InterfaceGraphique.graphique.ComponentType.Component;

import javax.swing.JPanel;
import java.awt.*;
import java.io.InputStream;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;


public class Board extends JPanel {
    private Game game;
    private Level level;

    public Board(Game game) {
        this.game = game;
    }

    public void loadLevel(InputStream levelStream) {
        this.level = new Level(levelStream);
        game.updateWindowSize(level.getHeight(), level.getWidth());
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        List<Component> components = new ArrayList<>();

        for (int j = 0; j < level.getHeight(); j++) {
            for (int k = 0; k < level.getWidth(); k++) {
                int index = j * level.getWidth() + k;
                String tileType = level.getComponents().get(index);
                boolean has_electric = level.getHasElectric().get(index);
                Component component = null;
                int y_value = 0;
                int x_value = 0;
                int width = 120;
                int height = 0;
                if (level.getFormat().equals("S")){
                    x_value = k * 120;
                    y_value = j * 120;
                    height = 120;
                }else{
                    int y = 0;
                    if (k %2 == 1) y = 52;
                    x_value = k * 90;
                    y_value = (j*104) + y;
                    height = 104;
                }
                switch (tileType) {
                    case "L":
                        component = new Lamp(x_value, y_value, width, height, has_electric, level.getDirections().get(index), level.getFormat());
                        break;
                    case "S":
                        component = new Source(x_value, y_value, width, height, level.getDirections().get(index), level.getFormat());
                        break;
                    case "W":
                        component = new Wifi(x_value, y_value, width, height, has_electric, level.getDirections().get(index), level.getFormat());
                        break;
                    case ".":
                        if (level.getDirections().get(index).size() == 0) {
                            component = new Empty(x_value, y_value, level.getFormat(), has_electric);
                        } else {
                            component = new CombinedPath(x_value, y_value, level.getDirections().get(index), has_electric, level.getFormat());
                        }
                        break;
                }
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
