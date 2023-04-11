package InterfaceGraphique.graphique.component;

import InterfaceGraphique.Game.Game;
import InterfaceGraphique.algorithm.Level;
import InterfaceGraphique.graphique.ComponentType.*;
import InterfaceGraphique.graphique.ComponentType.Component;

import javax.swing.JPanel;
import java.awt.*;
import java.io.InputStream;
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
                int index = j * (level.getSources().size() / level.getHeight()) + k;
                String tileType = level.getSources().get(index);
                Component component = null;
                switch (tileType) {
                    case "L":
                        component = new Lamp(k * 120, j * 120, 120, 120, false);
                        break;
                    case "S":
                        component = new Source(k * 120, j * 120, 120, 120);
                        break;
                    case "W":
                        component = new Wifi(k * 120, j * 120, 120, 120, true);
                        break;
                    case "LP":
                        component = new LongPath(k * 120, j * 120, true);
                        break;
                    case "SP":
                        component = new ShortPath(k * 120, j * 120, true);
                        break;
                    case "LC":
                        component = new LongCurved(k * 120, j * 120, true);
                        break;
                    case "SC":
                        component = new ShortCurved(k * 120, j * 120, true);
                        break;
                    case ".":
                        if (level.getDirections().get(index).size() == 0) {
                            component = new Empty(k * 120, j * 120);
                        } else {
                            component = new CombinedPath(k * 120, j * 120, level.getDirections().get(index));
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
