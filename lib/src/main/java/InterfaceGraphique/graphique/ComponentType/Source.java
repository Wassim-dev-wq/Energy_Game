package InterfaceGraphique.graphique.ComponentType;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.geom.AffineTransform;
import java.util.List;

public class Source extends Component {
    private static BufferedImage source;
    private BufferedImage combined_source;

    public static BufferedImage src = ImageLoader.getSrc();
    private List<String> directions;
    @Override
    public void rotate() {
    }
    public Source(int x, int y, int w, int h, List<String> directions, String format) {
        super(x, y);
        this.directions = directions;
        updateGraphics(x, y, w, h, format);
    }

    public Source(){
        super(0, 0);
    }

    private void createCombinedSource(int x, int y, int w, int h, String format) {
        if (format.equals("H")) x = 360;
        else x = 0;
        y = 480;
        source = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        source.getGraphics().drawImage(src, 0, 0, w, h, x, y, x + w, y + h, null);
        ShortPath on_short_path = new ShortPath(0, 0, true, format);
        Empty empty = new Empty(0,0,format,true);
        combined_source = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g_combined_source_off = combined_source.createGraphics();
        g_combined_source_off.drawImage(source, 0,0, null);
        g_combined_source_off.drawImage(empty.getCurrentImage(), 0, 0, null);
        double rotationAngle = 0;
        int angle = 0;
        int turn_angle = 90;
        if (format.equals("H")) turn_angle = 60;
        for (int i=0; i<directions.size(); i++){
            int intDirection = Integer.parseInt(directions.get(i));
            rotationAngle = Math.toRadians(intDirection*turn_angle) - angle;

            AffineTransform shortPathTransform = AffineTransform.getRotateInstance(rotationAngle, (double) w / 2, (double) h / 2);
            g_combined_source_off.drawImage(on_short_path.getCurrentImage(), shortPathTransform, null);
            angle = Integer.parseInt(directions.get(i))*turn_angle;
        }



    }

    private void updateGraphics(int x, int y, int w, int h, String format) {
        createCombinedSource(x, y, w, h, format);
        setCurrentImage(combined_source);
    }
}
