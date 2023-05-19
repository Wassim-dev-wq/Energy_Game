package InterfaceGraphique.algorithm;

import InterfaceGraphique.graphique.ComponentType.Component;

import java.util.Arrays;
import java.util.List;

public class ElectricityHandler {
    private String[][] components;
    private List<String>[][] directions;
    private boolean[][] has_electric;
    private String gridType;

    public ElectricityHandler(String[][] components, List<String>[][] directions, boolean[][] has_electric, String gridType) {
        this.gridType = gridType;
        this.components = components;
        this.directions = directions;
        this.has_electric = has_electric;
    }

    private boolean isConnected(int row1, int col1, int row2, int col2, List<String> directions1, List<String> directions2) {
        for (String dir1 : directions1) {
            System.out.println("directions1->"+dir1);
        }
        for (String dir2 : directions2) {
            System.out.println("directions2->" +dir2);
        }
        for (String dir1 : directions1) {
            int dir1Int = Integer.parseInt(dir1);
            for (String dir2 : directions2) {
                int dir2Int = Integer.parseInt(dir2);
                if (connected(row1, col1, row2, col2, dir1Int, dir2Int)) {
                    String componentType1 = components[row1][col1];
                    String componentType2 = components[row2][col2];
                    boolean isValidComponentType1 = componentType1.equals("S") || componentType1.equals("W") || (componentType1.equals(".") && !directions[row1][col1].isEmpty());
                    boolean isValidComponentType2 = componentType2.equals("W") || componentType2.equals(".") || componentType2.equals("L");

                    if (isValidComponentType1 && isValidComponentType2) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    private boolean connected(int sourceRow, int sourceCol, int targetRow, int targetCol, int sourceDir, int targetDir) {
        if (gridType.equals("S")) {
            return ((sourceDir == 0 && targetDir == 2 && sourceRow > targetRow) ||
                    (sourceDir == 1 && targetDir == 3 && sourceCol < targetCol) ||
                    (sourceDir == 2 && targetDir == 0 && sourceRow < targetRow) ||
                    (sourceDir == 3 && targetDir == 1 && sourceCol > targetCol));
        } else if (gridType.equals("H")) {
            return ((sourceDir == 0 && targetDir == 3) || (sourceDir == 1 && targetDir == 4) ||
                    (sourceDir == 2 && targetDir == 5) || (sourceDir == 3 && targetDir == 0) ||
                    (sourceDir == 4 && targetDir == 1) || (sourceDir == 5 && targetDir == 2));        }

        return false;
    }

    public void propagateElectricity() {
        for (int row = 0; row < components.length; row++) {
            for (int col = 0; col < components[row].length; col++) {
                System.out.println(components[row][col]);
                if (components[row][col].equals("S")) {
                    has_electric[row][col] = true;
                    propagateElectricityFromIndex(row, col);
                }
            }
        }
    }
    private int[][] getHexagonalDirections(int row) {
        if (row % 2 == 0) {
            return new int[][] {{-1, 0}, {-1, 1}, {0, -1}, {0, 1}, {1, 0}, {1, 1}};
        } else {
            return new int[][] {{-1, -1}, {-1, 0}, {0, -1}, {0, 1}, {1, -1}, {1, 0}};
        }
    }
    private void propagateElectricityFromIndex(int sourceRow, int sourceCol) {
        int[][] directions;
        if (gridType.equals("S")) {
            directions = new int[][] {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        } else if (gridType.equals("H")) {
            directions = getHexagonalDirections(sourceRow);
        } else {
            throw new IllegalArgumentException("Invalid grid type: " + gridType);
        }
        for (int[] dir : directions) {
            int newRow = sourceRow + dir[0];
            int newCol = sourceCol + dir[1];
            if (newRow >= 0 && newRow < components.length && newCol >= 0 && newCol < components[newRow].length) {
                boolean isNotEmptyPath = components[newRow][newCol].equals(".") && !this.directions[newRow][newCol].isEmpty();
                if ((!components[newRow][newCol].equals(".") || isNotEmptyPath) && !has_electric[newRow][newCol]) {
                    if (isConnected(sourceRow, sourceCol, newRow, newCol, this.directions[sourceRow][sourceCol], this.directions[newRow][newCol])) {
                        System.out.println("i: " + " targetRow " + newRow + " targetCol : " + newCol);
                        System.out.println("- components.get(i).equals(.) " + components[newRow][newCol].equals(".") + " has_electric.get(i) " + has_electric[newRow][newCol]);
                        System.out.println("- sourceRow " + sourceRow + " sourceCol " + sourceCol + " targetRow " + newRow + " targetCol " + newCol + " directions.get(sourceIndex) " + this.directions[sourceRow][sourceCol] + " directions.get(i) " + this.directions[newRow][newCol]);
                        has_electric[newRow][newCol] = true;
                        propagateElectricityFromIndex(newRow, newCol);
                    }
                }
            }
        }
    }

    public boolean[][] getHasElectric() {
        return has_electric;
    }

    public String[][] getComponents() {
        return components;
    }

    public List<String>[][] getDirections() {
        return directions;
    }
}
