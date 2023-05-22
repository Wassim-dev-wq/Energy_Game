package InterfaceGraphique.graphique.ComponentType;


import java.awt.image.BufferedImage;

public class Empty extends Component {
    private static BufferedImage empty;
    public static BufferedImage src = ImageLoader.getSrc();
    int angle;

//    static {
//        int x = 0, y = 0, w = 120, h = 120;
//        empty = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
//        empty.getGraphics().drawImage(src, 0, 0, w, h, x, y, x + w, y + h, null);
//    }
@Override
public void rotate() {
}

    @Override
    public void updates() {
        System.out.println("update-empty");
    }

    public Empty(int x, int y, String f, boolean electric) {
        super(x, y);
        createEmpty(f, electric);
        setCurrentImage(empty); // Default state, change it if necessary
    }
    @Override
    public int getAngle() {
        return this.angle;
    }

    @Override
    public boolean getElectric() {
        return false;
    }

    @Override
    public boolean getIsOn() {
        return false;
    }

    @Override
    public String getName() {
        return "Empty";
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
