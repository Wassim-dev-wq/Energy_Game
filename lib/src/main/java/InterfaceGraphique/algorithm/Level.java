package InterfaceGraphique.algorithm;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class Level {
    private static final Logger LOGGER = Logger.getLogger(Level.class.getName());

    private int height;
    private int width;
    private String format;
    private List<String> data_list = new ArrayList<>();
    private List<String> componentsList = new ArrayList<>();
    private List<List<Integer>> directionsList = new ArrayList<>();
    private List<Boolean> has_electricList = new ArrayList<>();

    private String[][] components;
    private List<Integer>[][] directions;
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

    public List<Integer>[][] getDirections() {
        return directions;
    }

    public Level(String levelFilePath) {
        LOGGER.info("Parsing level file: " + levelFilePath);
        try {
            InputStream levelStream = getClass().getResourceAsStream(levelFilePath);
            assert levelStream != null;
            BufferedReader br = new BufferedReader(new InputStreamReader(levelStream));
            String line;

            int row = 0;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(" ");
                for (int col = 0; col < parts.length; col++) {
                    data_list.add(parts[col]);
                    logComponent(row, col, parts[col]);
                }
                row++;
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        if (data_list.size() < 3) {
            throw new IllegalArgumentException("Insufficient data in the input file.");
        }

        try {
            height = Integer.parseInt(data_list.get(0));
            width = Integer.parseInt(data_list.get(1));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid height or width in the input file.", e);
        }

        format = data_list.get(2);
        LOGGER.info("Parsed format: " + format);

        int i = 3;
        while (i < data_list.size()) {
            String s = data_list.get(i);
            if (s.equals(".") || s.equals("S") || s.equals("L") || s.equals("W")) {
                List<Integer> position = new ArrayList<>();
                i++;
                if (s.equals(".") && (i >= data_list.size() || isComponent(data_list.get(i)))) {
                    componentsList.add(s);
                    directionsList.add(position);
                    LOGGER.info("Added empty component at [" + (i / width) + "][" + (i % width) + "]: " + s);
                } else {
                    while (i < data_list.size() && !isComponent(data_list.get(i))) {
                        try {
                            int dir = Integer.parseInt(data_list.get(i));
                            position.add(dir);
                            i++;
                            LOGGER.info("Added direction to component at [" + (i / width) + "][" + (i % width) + "]: " + s + ", direction: " + dir);
                        } catch (NumberFormatException e) {
                            throw new IllegalArgumentException("Invalid position in the input file.", e);
                        }
                    }
                    componentsList.add(s);
                    directionsList.add(position);
                    LOGGER.info("Added component with directions at [" + (i / width) + "][" + (i % width) + "]: " + s + ", directions: " + position);
                }
                LOGGER.info("Parsed component: " + s + ", with directions: " + position);
            } else {
                throw new IllegalArgumentException("Invalid component in the input file.");
            }
        }

        if (componentsList.size() != height * width || directionsList.size() != height * width) {
            throw new IllegalArgumentException("Mismatch between the provided grid size and the number of components.");
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
                LOGGER.info("Assigning to cell at [" + row + "][" + col + "] component: " + componentsList.get(index) + ", directions: " + directionsList.get(index));
            }
        }

        electricityHandler = new ElectricityHandler(components, directions, has_electric, format);
        electricityHandler.propagateElectricity();
        has_electric = electricityHandler.getHasElectric();
        LOGGER.info("Finished parsing level file.");
    }

    public ElectricityHandler getElectricityHandler() {
        return electricityHandler;
    }

    private boolean isComponent(String s) {
        return s.equals(".") || s.equals("S") || s.equals("L") || s.equals("W");
    }

    private void logComponent(int row, int col, String component) {
        LOGGER.info("Parsing component at [" + row + "][" + col + "]: " + component);
    }
}
