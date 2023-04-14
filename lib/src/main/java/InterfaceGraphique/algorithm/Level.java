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

    public String getFormat(){return format;}

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
        format = data_list.get(2);

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

        for (int i = 0; i < height * width; i++) {
            has_electric.add(false);
        }
        propagateElectricity();
    }

    private boolean isConnected(int row1, int col1, int row2, int col2, List<String> directions1, List<String> directions2) {
        for (String dir1 : directions1) {System.out.println("directions1 "+dir1);}

        for (String dir2 : directions2) {System.out.println(dir2);}

            for (String dir1 : directions1) {
            int dir1Int = Integer.parseInt(dir1);
            for (String dir2 : directions2) {
                int dir2Int = Integer.parseInt(dir2);
                if (connected(dir1Int, dir2Int)) {
                    String componentType1 = components.get(row1 * width + col1);
                    String componentType2 = components.get(row2 * width + col2);
                    boolean isValidComponentType1 = componentType1.equals("S") || componentType1.equals("W") || (componentType1.equals(".") && !directions.get(row1 * width + col1).isEmpty());
                    boolean isValidComponentType2 = componentType2.equals("W") || componentType2.equals(".") || componentType2.equals("L");

                    if (isValidComponentType1 && isValidComponentType2) {
                        return true;
                    }
                }
            }
        }

        return false;
    }
    private boolean connected(int sourceDir, int targetDir) {
        return ((sourceDir == 0 && targetDir == 2) || (sourceDir == 1 && targetDir == 3) ||
                (sourceDir == 2 && targetDir == 0) || (sourceDir == 3 && targetDir == 1));
    }



    private void propagateElectricity() {
        for (int i = 0; i < components.size(); i++) {
            System.out.println("components.get("+i+") :"+components.get(i));
            if (components.get(i).equals("S")) {
                has_electric.set(i, true);
                propagateElectricityFromIndex(i);
            }
        }
    }

    private void propagateElectricityFromIndex(int sourceIndex) {
        int sourceRow = sourceIndex / width;
        int sourceCol = sourceIndex % width;

        System.out.println("sourceIndex" + sourceIndex + " sourceRow" + sourceRow + " sourceCol" + sourceCol);

        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

        for (int[] dir : directions) {
            int newRow = sourceRow + dir[0];
            int newCol = sourceCol + dir[1];

            if (newRow >= 0 && newRow < height && newCol >= 0 && newCol < width) {
                int targetIndex = newRow * width + newCol;
                boolean isNotEmptyPath = components.get(targetIndex).equals(".") && !this.directions.get(targetIndex).isEmpty();

                if ((!components.get(targetIndex).equals(".") || isNotEmptyPath) && !has_electric.get(targetIndex)) {
                    if (isConnected(sourceRow, sourceCol, newRow, newCol, this.directions.get(sourceIndex), this.directions.get(targetIndex))) {
                        System.out.println("i: " + targetIndex + " targetRow" + newRow + " targetCol : " + newCol);
                        System.out.println("- components.get(i).equals(.) " + components.get(targetIndex).equals(".") + " has_electric.get(i) " + has_electric.get(targetIndex));
                        System.out.println("- sourceRow " + sourceRow + " sourceCol " + sourceCol + " targetRow " + newRow + " targetCol " + newCol + " directions.get(sourceIndex) " + this.directions.get(sourceIndex) + " directions.get(i) " + this.directions.get(targetIndex));
                        has_electric.set(targetIndex, true);
                        propagateElectricityFromIndex(targetIndex);
                    }
                }
            }
        }
    }


    public List<Boolean> getHasElectric() {
        return has_electric;
    }
}
