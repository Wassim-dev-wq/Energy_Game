package InterfaceGraphique.graphique.ComponentType;


import java.awt.image.BufferedImage;

public class Empty extends Component {
    private static BufferedImage empty;
    public static BufferedImage src = ImageLoader.getSrc();

//    static {
//        int x = 0, y = 0, w = 120, h = 120;
//        empty = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
//        empty.getGraphics().drawImage(src, 0, 0, w, h, x, y, x + w, y + h, null);
//    }

    public Empty(int x, int y, String f, boolean electric) {
        super(x, y);
        createEmpty(f, electric);
        setCurrentImage(empty); // Default state, change it if necessary
    }

    public void createEmpty(String format, boolean electric){
        int x = 0;
        int y = 0;
        int w = 120, h = 120;
        if (electric) y = 360;
        if (format.equals("H")){
            x = 360;
            h = 104;
        }
        empty = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        empty.getGraphics().drawImage(src, 0, 0, w, h, x, y, x + w, y + h, null);
    }

}
