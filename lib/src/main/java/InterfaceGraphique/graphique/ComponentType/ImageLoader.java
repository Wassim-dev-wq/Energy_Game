package InterfaceGraphique.graphique.ComponentType;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class ImageLoader {
    private static BufferedImage src;

    static {
        try {
            src = ImageIO.read(ImageLoader.class.getResourceAsStream("/tuiles.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static BufferedImage getSrc() {
        return src;
    }
}
