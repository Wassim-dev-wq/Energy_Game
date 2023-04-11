package InterfaceGraphique.graphique.ComponentType;


import java.awt.*;
import java.awt.image.BufferedImage;

public class Source extends Component {
    private static BufferedImage source;
    private static BufferedImage combined_source;

    public static BufferedImage src = ImageLoader.getSrc();


    public Source(int x, int y, int w, int h) {
        super(x, y);
        updateGraphics(x, y, w, h);
    }

    private void createCombinedSource(int x, int y, int w, int h) {
        if (combined_source == null) {
            x = 0;
            y = 480;
            source = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
            source.getGraphics().drawImage(src, 0, 0, w, h, x, y, x + w, y + h, null);
            ShortPath off_short_path = new ShortPath(0, 0,true);
            combined_source = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g_combined_source_off = combined_source.createGraphics();
            g_combined_source_off.drawImage(source, 0, 0, null);
            g_combined_source_off.drawImage(off_short_path.getCurrentImage(), 0, 0, null);
        }
    }


    private void updateGraphics(int x, int y, int w, int h) {
        createCombinedSource(x, y, w, h);
        setCurrentImage(combined_source);
    }
}
