package InterfaceGraphique.graphique.component;

import InterfaceGraphique.graphique.ComponentType.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Components {
    // Here we create a map to store our component objects.
    // This is so we can retrieve them again later without having to create, so we can avoid creating them every time we rotate
    private static Map<String, Component> componentsMap = new HashMap<>();
    public static Component createComponent(String type, int x, int y, int width, int height, boolean hasElectric, List<Integer> directions, String gridType) {
        // Here we generate a unique key for our component.
        // This key is based on all the parameters of the component
        String key = type + x + y + width + height + hasElectric + directions + gridType;
        if (componentsMap.containsKey(key)) {
            return componentsMap.get(key);
        }
        Component component = null;
        switch (type) {
            case "L":
                component = new Lamp(x, y, width, height, hasElectric, directions, gridType);
                break;
            case "S":
                component = new Source(x, y, width, height, directions, gridType);
                break;
            case "W":
                component = new Wifi(x, y, width, height, hasElectric, directions, gridType);
                break;
            case ".":
                if (directions.size() == 0) {
                    component = new Empty(x, y, gridType, hasElectric);
                } else {
                    component = new CombinedPath(x, y, directions, hasElectric, gridType);
                }
                break;
        }
        if (component != null) {
            componentsMap.put(key, component);
        }
        return component;
    }
}
