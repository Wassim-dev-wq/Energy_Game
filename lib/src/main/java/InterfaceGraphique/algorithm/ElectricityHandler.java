package InterfaceGraphique.algorithm;

import java.util.List;

public class ElectricityHandler {
    private int height;
    private int width;
    private List<String> components;
    private List<List<String>> directions;
    private List<Boolean> has_electric;
    private String gridType;

    public ElectricityHandler(int height, int width, List<String> components, List<List<String>> directions, List<Boolean> has_electric, String gridType) {
        this.height = height;
        this.width = width;
        this.gridType = gridType;
        this.components = components;
        this.directions = directions;
        this.has_electric = has_electric;
    }

    private boolean isConnected(int row1, int col1, int row2, int col2, List<String> directions1, List<String> directions2) {
        for (String dir1 : directions1) {System.out.println("directions1 "+dir1);}

        for (String dir2 : directions2) {System.out.println(dir2);}

        for (String dir1 : directions1) {
            int dir1Int = Integer.parseInt(dir1);
            for (String dir2 : directions2) {
                int dir2Int = Integer.parseInt(dir2);
                if (connected(row1, col1, row2, col2, dir1Int, dir2Int)) {
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
        for (int i = 0; i < components.size(); i++) {
            System.out.println("components.get("+i+") :"+components.get(i));
            if (components.get(i).equals("S")) {
                has_electric.set(i, true);
                propagateElectricityFromIndex(i);
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
    private void propagateElectricityFromIndex(int sourceIndex) {
        int sourceRow = sourceIndex / width;
        int sourceCol = sourceIndex % width;

        System.out.println("sourceIndex" + sourceIndex + " sourceRow" + sourceRow + " sourceCol" + sourceCol);

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
