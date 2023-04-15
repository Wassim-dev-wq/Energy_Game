package InterfaceGraphique.graphique.component;

import InterfaceGraphique.graphique.ComponentType.*;
import java.util.List;

public class Components {
    public static Component createComponent(String type, int x, int y, int width, int height, boolean hasElectric, List<String> directions, String gridType) {
        switch (type) {
            case "L":
                return new Lamp(x, y, width, height, hasElectric, directions, gridType);
            case "S":
                return new Source(x, y, width, height, directions, gridType);
            case "W":
                return new Wifi(x, y, width, height, hasElectric, directions, gridType);
            case ".":
                if (directions.size() == 0) {
                    return new Empty(x, y, gridType, hasElectric);
                } else {
                    return new CombinedPath(x, y, directions, hasElectric, gridType);
                }
            default:
                return null;
        }
    }
}
