package InterfaceGraphique.graphique.ComponentType;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.geom.AffineTransform;

public class Source extends Component {
    private static BufferedImage source;
    private BufferedImage combined_source;

    public static BufferedImage src = ImageLoader.getSrc();
    private String direction;

    public Source(int x, int y, int w, int h, String direction) {
        super(x, y);
        this.direction = direction;
        updateGraphics(x, y, w, h);
    }

    private void createCombinedSource(int x, int y, int w, int h) {
        x = 0;
        y = 480;
        source = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        source.getGraphics().drawImage(src, 0, 0, w, h, x, y, x + w, y + h, null);
        ShortPath off_short_path = new ShortPath(0, 0, true);
        combined_source = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g_combined_source_off = combined_source.createGraphics();

        int intDirection = Integer.parseInt(direction);
        double rotationAngle = 0;
        switch (intDirection) {
            case 0:
                rotationAngle = Math.toRadians(0);
                break;
            case 1:
                rotationAngle = Math.toRadians(90);
                break;
            case 2:
                rotationAngle = Math.toRadians(180);
                break;
            case 3:
                rotationAngle = Math.toRadians(270);
                break;
        }

        g_combined_source_off.drawImage(source, 0,0, null);

        AffineTransform shortPathTransform = AffineTransform.getRotateInstance(rotationAngle, (double) w / 2, (double) h / 2);
        g_combined_source_off.drawImage(off_short_path.getCurrentImage(), shortPathTransform, null);
    }

    private void updateGraphics(int x, int y, int w, int h) {
        createCombinedSource(x, y, w, h);
        setCurrentImage(combined_source);
    }
}
