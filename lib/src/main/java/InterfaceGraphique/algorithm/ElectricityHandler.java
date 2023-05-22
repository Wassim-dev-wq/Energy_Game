package InterfaceGraphique.algorithm;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import java.util.ArrayList;

public class ElectricityHandler {
    public static final Logger LOGGER = Logger.getLogger(ElectricityHandler.class.getName());
    private String[][] components;
    private List<Integer>[][] directions;
    private boolean[][] has_electric;
    private String gridType;

    public ElectricityHandler(String[][] components, List<Integer>[][] directions, boolean[][] has_electric, String gridType) {
        this.gridType = gridType;
        this.components = components;
        this.directions = directions;
        this.has_electric = has_electric;
    }

    private List<int[]> getValidConnections(int row1, int col1, int row2, int col2, List<Integer> directions1, List<Integer> directions2) {
        LOGGER.info("Entering getValidConnections method with parameters row1: " + row1 + ", col1: " + col1 + ", row2: " + row2 + ", col2: " + col2 + ", directions1: " + directions1 + ", directions2: " + directions2);
        List<int[]> validConnections = new ArrayList<>();

        for (Integer dir1 : directions1) {
            for (Integer dir2 : directions2) {
                if (connected(row1, col1, row2, col2, dir1, dir2)) {
                    String componentType1 = components[row1][col1];
                    String componentType2 = components[row2][col2];
                    boolean isValidComponentType1 = componentType1.equals("S") || componentType1.equals("W") || (componentType1.equals(".") || componentType1.equals("L")  && !directions[row1][col1].isEmpty());
                    boolean isValidComponentType2 = componentType2.equals("W") || componentType2.equals(".") || componentType2.equals("L");

                    LOGGER.info("isValidComponentType1: " + isValidComponentType1 + ", isValidComponentType2: " + isValidComponentType2);
                    if (isValidComponentType1 && isValidComponentType2) {
                        validConnections.add(new int[] {row2, col2});
                    }
                }
            }
        }

        LOGGER.info("Exiting getValidConnections method with " + validConnections.size() + " valid connections");
        return validConnections;
    }

    private boolean connected(int sourceRow, int sourceCol, int targetRow, int targetCol, int sourceDir, int targetDir) {
        LOGGER.info("Entering connected method with parameters sourceRow: " + sourceRow + ", sourceCol: " + sourceCol + ", targetRow: " + targetRow + ", targetCol: " + targetCol + ", sourceDir: " + sourceDir + ", targetDir: " + targetDir);
        if (gridType.equals("S")) {
            boolean connectionStatus = ((sourceDir == 0 && targetDir == 2 && sourceRow > targetRow) ||
                    (sourceDir == 1 && targetDir == 3 && sourceCol < targetCol) ||
                    (sourceDir == 2 && targetDir == 0 && sourceRow < targetRow) ||
                    (sourceDir == 3 && targetDir == 1 && sourceCol > targetCol));
            LOGGER.info("Grid type: S, Connection status: " + connectionStatus);
            return connectionStatus;
        } else if (gridType.equals("H")) {
            boolean connectionStatus;
            if (sourceCol % 2 == 0){
                connectionStatus = ((sourceDir == 0 && targetDir == 3 && sourceRow == targetRow + 1 && sourceCol == targetCol) ||
                        (sourceDir == 1 && targetDir == 4 && sourceCol == targetCol - 1 && sourceRow == targetRow + 1) ||  // Updated condition
                        (sourceDir == 2 && targetDir == 5 && sourceRow == targetRow && sourceCol == targetCol - 1) ||
                        (sourceDir == 3 && targetDir == 0 && sourceRow == targetRow - 1 && sourceCol == targetCol) ||
                        (sourceDir == 4 && targetDir == 1 && sourceCol == targetCol + 1 && sourceRow == targetRow) ||
                        (sourceDir == 5 && targetDir == 2 && sourceCol == targetCol + 1 && sourceRow == targetRow + 1));

            }else{
                connectionStatus = ((sourceDir == 0 && targetDir == 3 && sourceRow == targetRow + 1 && sourceCol == targetCol) ||
                        (sourceDir == 1 && targetDir == 4 && sourceCol == targetCol - 1 && sourceRow == targetRow) ||  // Updated condition
                        (sourceDir == 2 && targetDir == 5 && sourceRow == targetRow - 1 && sourceCol == targetCol - 1) ||
                        (sourceDir == 3 && targetDir == 0 && sourceRow == targetRow - 1 && sourceCol == targetCol) ||
                        (sourceDir == 4 && targetDir == 1 && sourceCol == targetCol + 1 && sourceRow == targetRow - 1) ||
                        (sourceDir == 5 && targetDir == 2 && sourceCol == targetCol + 1 && sourceRow == targetRow));

            }

            LOGGER.info("Grid type: H, Connection status: " + connectionStatus);
            return connectionStatus;
        }

        LOGGER.info("Grid type not S or H, returning false");
        return false;
    }


    public void propagateElectricity() {
        LOGGER.info("Entering propagateElectricity method");
        for (int row = 0; row < components.length; row++) {
            for (int col = 0; col < components[row].length; col++) {
                LOGGER.info("Checking components at row: " + row + ", col: " + col);
                if (components[row][col].equals("S")) {
                    LOGGER.info("Found 'S' at row: " + row + ", col: " + col);
                    has_electric[row][col] = true;
                    propagateElectricityFromIndex(row, col);
                }
            }
        }
    }


    private void propagateElectricityFromIndex(int sourceRow, int sourceCol) {
        LOGGER.info("Entering propagateElectricityFromIndex method with sourceRow: " + sourceRow + ", sourceCol: " + sourceCol);
        int[][] directions;

        if (gridType.equals("S")) {
            directions = new int[][] {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        } else if (gridType.equals("H")) {
            if (sourceCol % 2 != 0){
                directions = new int[][] {{-1, 0}, {1, 1}, {0, -1}, {1, 0}, {1, -1}, {0, 1}};
            }else{
                directions = new int[][] {{-1, 0}, {-1, -1}, {0, -1}, {1, 0}, {-1, 1}, {0, 1}};
            }
            // Modify directions to include all neighboring cells in a hexagonal grid
        } else {
            throw new IllegalArgumentException("Invalid grid type: " + gridType);
        }

        for (int[] dir : directions) {
            int newRow = sourceRow + dir[0];
            int newCol = sourceCol + dir[1];
            if (newRow >= 0 && newRow < components.length && newCol >= 0 && newCol < components[newRow].length) {
                boolean isNotEmptyPath = (components[newRow][newCol].equals(".") && !this.directions[newRow][newCol].isEmpty()) ||
                        components[newRow][newCol].equals("L") || components[newRow][newCol].equals("W");
                LOGGER.info("sourceRow -> " + sourceRow + ", sourceCol -> " + sourceCol);
                LOGGER.info("Processing cell at newRow: " + newRow + ", newCol: " + newCol + ". isNotEmptyPath: " + isNotEmptyPath);
                if (isNotEmptyPath) {
                    List<Integer> directions1 = this.directions[sourceRow][sourceCol];
                    List<Integer> directions2 = this.directions[newRow][newCol];
                    List<int[]> validConnections = getValidConnections(sourceRow, sourceCol, newRow, newCol, directions1, directions2);

                    // propagate to all valid connections
                    for (int[] conn : validConnections) {
                        int connRow = conn[0];
                        int connCol = conn[1];
                        LOGGER.info("Valid connection found at connRow: " + connRow + ", connCol: " + connCol);
                        if (!has_electric[connRow][connCol]) {
                            LOGGER.info("Propagating electricity to cell at connRow: " + connRow + ", connCol: " + connCol);
                            has_electric[connRow][connCol] = true;

                            if (components[connRow][connCol].equals("W")){
                                for (int i=0; i<components.length; i++){
                                    for (int j=0; j<components[i].length; j++){
                                        if (components[i][j].equals("W")){
                                            has_electric[i][j] = true;
                                            propagateElectricityFromIndex(i, j);
                                        }
                                    }
                                }
                            }
                            propagateElectricityFromIndex(connRow, connCol);
                        }
                    }
                }
            }
        }

        LOGGER.info("Exiting propagateElectricityFromIndex method");
    }


    public void setDirections(List<Integer>[][] directions) {
        this.directions = directions;
    }
    public void resetElectricity() {
        //LOGGER.info("Entering resetElectricity method");
        for (int i = 0; i < has_electric.length; i++) {
            //LOGGER.info("Resetting electricity for row: " + i);
            Arrays.fill(has_electric[i], false);
        }
        // After reset, set the source cell (with 'S') to have electricity.
        for (int row = 0; row < components.length; row++) {
            for (int col = 0; col < components[row].length; col++) {
                if (components[row][col].equals("S")) {
                    LOGGER.info("Found 'S' at row: " + row + ", col: " + col + ". Setting has_electric to true");
                    has_electric[row][col] = true;
                }
            }
        }
        LOGGER.info("Exiting resetElectricity method");
    }


    public boolean[][] getHasElectric() {
        return has_electric;
    }

    public String[][] getComponents() {
        return components;
    }

    public List<Integer>[][] getDirections() {
        return directions;
    }
}
