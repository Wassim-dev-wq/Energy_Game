import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.List;


public class test_png extends JPanel{
    public static List<String> sources = new ArrayList<String>();
    public static List<List<String>> directions = new ArrayList<List<String>>();
    public static List<String> data_list = new ArrayList<String>();
    public static int height = 0;
    public static int length = 0;
    public static String format;
    public test_png(){
        setSize(500, 500);
        setBackground(Color.black);
    }

    public void readFile(String fileName){
        try{
            File level_file = new File("./Levels/"+fileName);
            Scanner scanner = new Scanner(level_file);
            while (scanner.hasNext()){
                data_list.add(scanner.next());
            }
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }
        height = Integer.parseInt(data_list.get(0));
        length = Integer.parseInt(data_list.get(1));
        format = data_list.get(2);
        for (int i = 3; i<data_list.size(); i++){
            String s = data_list.get(i);
            if (s.equals(".") || s.equals("S") || s.equals("L")){
                List<String> position = new ArrayList<String>();
                if (i+1 < data_list.size()){
                    i++;
                    while (!(data_list.get(i).equals(".") || data_list.get(i).equals("S") || data_list.get(i).equals("L"))){
                        position.add(data_list.get(i));
                        i++;
                    }
                    sources.add(s);
                    directions.add(position);
                }else{
                    sources.add(s);
                    directions.add(position);
                    break;
                }
            }
            i--;
        }
    }
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Image src = null;
        try {
            src = ImageIO.read(new File("./src/tuiles.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        int x = 0, y = 0, w = 120, h = 120;
        BufferedImage empty = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        empty.getGraphics().drawImage(src, 0, 0, w, h, x, y, x + w, y + h, null);
        x = 120;
        y = 240;
        BufferedImage dark_curved_path = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        dark_curved_path.getGraphics().drawImage(src, 0, 0, w, h, x, y, x + w, y + h, null);
        x = 120;
        y = 120;
        BufferedImage dark_wifi = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        dark_wifi.getGraphics().drawImage(src, 0, 0, w, h, x, y, x + w, y + h, null);
        x = 240;
        y = 120;
        BufferedImage dark_lamp = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        dark_lamp.getGraphics().drawImage(src, 0, 0, w, h, x, y, x + w, y + h, null);
        x = 0;
        y = 240;
        BufferedImage short_path = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        short_path.getGraphics().drawImage(src, 0, 0, w, h, x, y, x + w, y + h, null);
        x = 240;
        y = 240;
        BufferedImage dark_long_path = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        dark_long_path.getGraphics().drawImage(src, 0, 0, w, h, x, y, x + w, y + h, null);
        x = 0;
        y = 480;
        BufferedImage energy = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        energy.getGraphics().drawImage(src, 0, 0, w, h, x, y, x + w, y + h, null);
        x = 120;
        BufferedImage light_wifi = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        light_wifi.getGraphics().drawImage(src, 0, 0, w, h, x, y, x + w, y + h, null);
        x = 240;
        BufferedImage light_lamp = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        light_lamp.getGraphics().drawImage(src, 0, 0, w, h, x, y, x + w, y + h, null);
        x = 0;
        y = 600;
        BufferedImage light_short_path = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        light_short_path.getGraphics().drawImage(src, 0, 0, w, h, x, y, x + w, y + h, null);
        x = 120;
        BufferedImage light_curved_path = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        light_curved_path.getGraphics().drawImage(src, 0, 0, w, h, x, y, x + w, y + h, null);
        x = 240;
        BufferedImage light_long_path = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        light_long_path.getGraphics().drawImage(src, 0, 0, w, h, x, y, x + w, y + h, null);
        readFile("level1.nrg");
        for(int j=0; j<height; j++){
            for (int k=0; k<length; k++){
                System.out.println("x = " + k*120);
                System.out.println("y = " + j*120);
                if (sources.get(j+k+(j*height)).equals(".")){
                    if (directions.get(j+k+(j*height)).size() == 0){
                        g.drawImage(empty, k*120, j*120, this);
                    }else{
                        g.drawImage(dark_curved_path, k*120, j*120, this);
                    }
                } else if (sources.get(j+k+(j*height)).equals("S")) {
                    g.drawImage(energy, k*120, j*120, this);
                } else if (sources.get(j+k+(j*height)).equals("L")) {
                    g.drawImage(dark_lamp, k*120, j*120, this);
                } else if (sources.get(j+k+(j*height)).equals("W")) {
                    g.drawImage(dark_wifi, k*120, j*120, this);
                }
            }
        }
//        g.drawImage(empty, 0, 0, this);
//        g.drawImage(dark_curved_path, 120, 0, this);
//        g.drawImage(dark_lamp, 240, 0, this);
//        g.drawImage(energy, 0, 120, this);
//        g.drawImage(dark_curved_path, 120, 120, this);
//        g.drawImage(empty,240, 120, this);
    }

    public static void main(String[] args) throws IOException {
        test_png t = new test_png();
        t.readFile("level1.nrg");
        System.out.println(length);
        System.out.println(height);
        System.out.println(sources);
        System.out.println(directions);
        JFrame f = new JFrame();
        f.add(new test_png());
        f.setSize(500, 500);
        f.setBackground(Color.BLACK);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setLayout(null);
        f.setVisible(true);
    }
}
