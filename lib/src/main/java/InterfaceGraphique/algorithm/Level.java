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
    private String format;
    private List<String> data_list = new ArrayList<>();
    private List<String> componentsList = new ArrayList<>();
    private List<List<String>> directionsList = new ArrayList<>();
    private List<Boolean> has_electricList = new ArrayList<>();

    private String[][] components;
    private List<String>[][] directions;
    private boolean[][] has_electric;
    private ElectricityHandler electricityHandler;

    public int getHeight() {
        return height;
    }

    public String getFormat() {
        return format;
    }

    public int getWidth() {
        return width;
    }

    public String[][] getComponents() {
        return components;
    }

    public List<String>[][] getDirections() {
        return directions;
    }

    public Level(String levelFilePath) {
        try {
            InputStream levelStream = getClass().getResourceAsStream(levelFilePath);
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
        format = data_list.get(2);

        int i = 3;
        while (i < data_list.size()) {
            String s = data_list.get(i);
            if (s.equals(".") || s.equals("S") || s.equals("L") || s.equals("W")) {
                List<String> position = new ArrayList<>();
                i++;
                while (i < data_list.size() && !(data_list.get(i).equals(".") || data_list.get(i).equals("S") || data_list.get(i).equals("L") || data_list.get(i).equals("W"))) {
                    position.add(data_list.get(i));
                    i++;
                }
                componentsList.add(s);
                directionsList.add(position);
            }
        }
        for (int j = 0; j < height * width; j++) {
            has_electricList.add(false);
        }

        components = new String[height][width];
        directions = new List[height][width];
        has_electric = new boolean[height][width];

        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                int index = row * width + col;
                components[row][col] = componentsList.get(index);
                directions[row][col] = directionsList.get(index);
                has_electric[row][col] = has_electricList.get(index);
            }
        }
        electricityHandler = new ElectricityHandler(components, directions, has_electric, format);
        electricityHandler.propagateElectricity();
        has_electric = electricityHandler.getHasElectric();
    }
    public ElectricityHandler getElectricityHandler() {
        return electricityHandler;
    }
}
