package InterfaceGraphique.algorithm;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Level {
    private int height;
    private int width;
    private List<String> data_list = new ArrayList<>();
    private List<String> components = new ArrayList<>();
    private List<List<String>> directions = new ArrayList<>();
    private List<Boolean> has_electric = new ArrayList<>();

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public List<String> getComponents() {
        return components;
    }

    public List<List<String>> getDirections() {
        return directions;
    }

    public Level(InputStream levelStream) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(levelStream));
            String line;

            while ((line = br.readLine()) != null) {
                String[] parts = line.split(" ");
                for (String part : parts) {
                    data_list.add(part);
                }
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        height = Integer.parseInt(data_list.get(0));
        width = Integer.parseInt(data_list.get(1));
        String format = data_list.get(2);

        for (int i = 3; i < data_list.size(); i++) {
            String s = data_list.get(i);
            if (s.equals(".") || s.equals("S") || s.equals("L") || s.equals("W")) {
                List<String> position = new ArrayList<>();
                if (i + 1 < data_list.size()) {
                    i++;
                    while (i != data_list.size() && !(data_list.get(i).equals(".") || data_list.get(i).equals("S") || data_list.get(i).equals("L") || data_list.get(i).equals("W"))) {
                        position.add(data_list.get(i));
                        i++;
                    }
                    components.add(s);
                    directions.add(position);
                    i--;
                } else {
                    components.add(s);
                    directions.add(position);
                    break;
                }
            }
        }

        // Initialize the has_electric list
        for (int i = 0; i < height * width; i++) {
            has_electric.add(false);
        }
        propagateElectricity();
    }

    private boolean isConnected(int component1, int component2, List<String> directions1, List<String> directions2) {
        for (String dir1 : directions1) {
            for (String dir2 : directions2) {
                if (Integer.parseInt(dir1) == (Integer.parseInt(dir2) + 3) % 6) {
                    return true;
                }
            }
        }
        return false;
    }

    private void propagateElectricity() {
        for (int i = 0; i < components.size(); i++) {
            if (components.get(i).equals("S")) {
                has_electric.set(i, true);
                for (int j = 0; j < components.size(); j++) {
                    if (!components.get(j).equals(".") && isConnected(i, j, directions.get(i), directions.get(j))) {
                        has_electric.set(j, true);
                    }
                }
            }
        }
    }

    public List<Boolean> getHasElectric() {
        return has_electric;
    }
}
