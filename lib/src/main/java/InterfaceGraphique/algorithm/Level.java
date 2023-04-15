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
    private List<String> components = new ArrayList<>();
    private List<List<String>> directions = new ArrayList<>();
    private List<Boolean> has_electric = new ArrayList<>();

    public int getHeight() {
        return height;
    }

    public String getFormat() {
        return format;
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
    private ElectricityHandler electricityHandler;

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
                components.add(s);
                directions.add(position);
            }
        }

        for (int j = 0; j < height * width; j++) {
            has_electric.add(false);
        }
        electricityHandler = new ElectricityHandler(height, width, components, directions, has_electric,format);
        electricityHandler.propagateElectricity();
        has_electric = electricityHandler.getHasElectric();
    }

    public ElectricityHandler getElectricityHandler() {
        return electricityHandler;
    }
}
