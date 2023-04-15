package InterfaceGraphique.graphique.ComponentType;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

public class CombinedPath extends Component {
    private List<String> directions;

    private LongCurved off_curved_path;
    private LongCurved on_curved_path;
    private ShortCurved off_short_curved;
    private ShortCurved on_short_curved;
    private MediumCurved off_medium_curved;
    private MediumCurved on_medium_curved;
    private LongPath off_long_path;
    private LongPath on_long_path;
    private Empty on_empty;
    private Empty off_empty;
    private int _x;
    private int _y;
    private String componentType;


    public CombinedPath(int x, int y, List<String> directions, boolean isOn, String format) {
        super(x, y);
        this._x = x;
        this._y = y;
        this.directions = directions;
        createComponents(format);
        updateGraphics(isOn, format);
        if (directions.isEmpty()) {
            componentType = "empty";
        } else {
            componentType = "path";
        }
    }

    public int getX() {
        return _x;
    }

    public int getY() {
        return _y;
    }

    private void createComponents(String format) {
        off_curved_path = new LongCurved(0, 0, false);
        on_curved_path = new LongCurved(0, 0, true);
        off_long_path = new LongPath(0, 0, false, format);
        on_long_path = new LongPath(0, 0, true, format);
        off_short_curved = new ShortCurved(0, 0, false);
        on_short_curved = new ShortCurved(0, 0, true);
        off_medium_curved = new MediumCurved(0, 0, false);
        on_medium_curved = new MediumCurved(0, 0, true);
        on_empty = new Empty(0, 0, format, true);
        off_empty = new Empty(0, 0, format, false);
    }
    public String getComponentType() {
        return componentType;
    }

    private void updateGraphics(boolean isOn, String format) {
        int w = 120;
        int h = 120;
        if (format.equals("H")){
            h = 104;
        }
        BufferedImage combined_path = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g_combined_path = combined_path.createGraphics();
        int start = Integer.parseInt(directions.get(0));
        int angle = 0;
        for (int i = 1; i<directions.size(); i++){
            int difference = 0;
            try {
                difference = Integer.parseInt(directions.get(i)) - start;
            } catch (NumberFormatException e) {
                System.out.println("Invalid value : " + e.getMessage());
            }
            if (format.equals("S")){
                if (difference == 1 || difference == 3){
                    if (difference == 1){
                        g_combined_path.rotate(Math.toRadians((start*90)-angle), 60, 60);
                        angle = start*90;
                    } else {
                        g_combined_path.rotate(Math.toRadians((Integer.parseInt(directions.get(i))*90)-angle), 60, 60);
                        angle = (Integer.parseInt(directions.get(i))*90);
                    }
                    if (isOn) {
                        on_medium_curved.draw(g_combined_path);
                    } else {
                        off_medium_curved.draw(g_combined_path);
                    }
                }else if (difference == 2){
                    g_combined_path.rotate(Math.toRadians((start*90)-angle), 60, 60);
                    angle = start*90;
                    if (isOn) {
                        on_long_path.draw(g_combined_path);
                    } else {
                        off_long_path.draw(g_combined_path);
                    }
                }
            } else if (format.equals("H")) {
                if (difference == 1 || difference == 5){
                    if (difference == 1){
                        g_combined_path.rotate(Math.toRadians((start*60)-angle), 60, 52);
                        angle = start*60;
                    }else{
                        g_combined_path.rotate(Math.toRadians((Integer.parseInt(directions.get(i))*60)-angle), 60, 52);
                        angle = (Integer.parseInt(directions.get(i))*60);
                    }
                    if (isOn) {
                        on_short_curved.draw(g_combined_path);
                    } else {
                        off_short_curved.draw(g_combined_path);
                    }
                } else if (difference == 3) {
                    g_combined_path.rotate(Math.toRadians((start*60)-angle), 60, 52);
                    angle = start*60;
                    if (isOn) {
                        on_long_path.draw(g_combined_path);
                    } else {
                        off_long_path.draw(g_combined_path);
                    }
                }else if(difference == 2 || difference == 4){
                    if (difference == 2){
                        g_combined_path.rotate(Math.toRadians((start*60)-angle), 60, 52);
                        angle = start*60;
                    }else{
                        g_combined_path.rotate(Math.toRadians((Integer.parseInt(directions.get(i))*60)-angle), 60, 52);
                        angle = (Integer.parseInt(directions.get(i))*60);
                    }
                    if (isOn) {
                        on_curved_path.draw(g_combined_path);
                    } else {
                        off_curved_path.draw(g_combined_path);
                    }
                }
            }
        }
        if (isOn) {
            on_empty.draw(g_combined_path);
        } else {
            off_empty.draw(g_combined_path);
        }
        setCurrentImage(combined_path);
    }
}
