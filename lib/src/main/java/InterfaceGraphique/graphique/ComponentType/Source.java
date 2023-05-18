package InterfaceGraphique.graphique.ComponentType;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.List;

public class Source extends Component {
    private static BufferedImage source;
    private BufferedImage combined_source;

    public static BufferedImage src = ImageLoader.getSrc();
    private AffineTransform shortPathTransform;
    private List<String> directions;
    private String gridType;
    private int angle;
    private boolean isFirstRotation = true;
//    int angle;
    @Override
    public void rotate() {
        if (gridType.equals("S")) {
            this.angle += 90;
            if (this.angle > 270) {
                this.angle = 0;
            }
        } else {
            this.angle += 60;
            if (this.angle >  300) {
                this.angle = 0;
            }
        }
        updateDirections();
        updateGraphics(0, 0, 120, 120, gridType, angle);
    }
    public Source(int x, int y, int w, int h, List<String> directions, String format) {
        super(x, y);
        this.directions = directions;
        this.gridType = format;
        updateGraphics(x, y, w, h, format, angle);
    }
    public List<String> getDirections() {
        return this.directions;
    }

    private void createCombinedSource(int x, int y, int w, int h, String gridType, int angle) {
        if (gridType.equals("H")) x = 360;
        else x = 0;
        y = 480;
        double anchory;
        if (isFirstRotation) {
            anchory = (double) h / 2;
            isFirstRotation = false;
        } else {
            anchory = (double) h / 2.3;
        }
        source = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        source.getGraphics().drawImage(src, 0, 0, w, h, x, y, x + w, y + h, null);
        ShortPath on_short_path = new ShortPath(0, 0, true, gridType);

        combined_source = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g_combined_source_off = combined_source.createGraphics();
        g_combined_source_off.drawImage(source, 0,0, null);
//        int angle = 0;
        for (int i=0; i<directions.size(); i++){
            double rotationAngle;
            if(gridType.equals("S")){
                rotationAngle = Math.toRadians((Integer.parseInt(directions.get(i))*90));
                shortPathTransform = AffineTransform.getRotateInstance(rotationAngle, (double) w / 2, (double) h / 2);
            }else {
                rotationAngle = Math.toRadians((Integer.parseInt(directions.get(i))*60));
                shortPathTransform = AffineTransform.getRotateInstance(rotationAngle, (double) w / 2, anchory);
            }
            g_combined_source_off.drawImage(on_short_path.getCurrentImage(), shortPathTransform, null);
//            angle = Integer.parseInt(directions.get(i))*turn_angle;
        }
        Empty empty = new Empty(0,0,gridType,true);
        g_combined_source_off.drawImage(empty.getCurrentImage(), 0, 0, null);


    }
    public int getAngle() {
        return angle;
    }
    private void updateGraphics(int x, int y, int w, int h, String gridType, int angle) {
        createCombinedSource(x, y, w, h, gridType, angle);
        setCurrentImage(combined_source);
    }
    private void updateDirections() {
        List<String> newDirections = new ArrayList<>();
        for (String direction : this.directions) {
            int newDirection = (Integer.parseInt(direction) + 1) % 4; // for square grid
            if (this.gridType.equals("H")) {
                newDirection = (Integer.parseInt(direction) + 1) % 6;
            }
            newDirections.add(String.valueOf(newDirection));
        }
        this.directions = newDirections;
    }
}
