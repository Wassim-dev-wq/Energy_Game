package InterfaceGraphique.graphique;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.Buffer;
import java.util.*;
import java.util.List;


public class test_png extends JPanel implements MouseListener {
    public static List<String> sources = new ArrayList<String>();
    public static List<List<String>> directions = new ArrayList<List<String>>();
    public static List<String> data_list = new ArrayList<String>();
    public static List<Boolean> has_electric = new ArrayList<Boolean>();
    public static int height = 0;
    public static int length = 0;
    public static String format;
    public test_png(){
        setSize(length*120, height*120);
        setBackground(Color.black);
        addMouseListener(this);
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
                    while (i!=data_list.size() && !(data_list.get(i).equals(".") || data_list.get(i).equals("S") || data_list.get(i).equals("L"))){
                        position.add(data_list.get(i));
                        i++;
                    }
                    sources.add(s);
                    directions.add(position);
                    i--;
                }else{
                    sources.add(s);
                    directions.add(position);
                    break;
                }
            }

        }
        for (int i = 0; i<height*length; i++){
            has_electric.add(false);
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

        BufferedImage combined_lamp_dark = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g_combined_lamp_dark = combined_lamp_dark.createGraphics();
        g_combined_lamp_dark.drawImage(dark_lamp, 0, 0, null);

        BufferedImage combined_lamp_light = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g_combined_lamp_light = combined_lamp_light.createGraphics();
        g_combined_lamp_light.drawImage(light_lamp, 0, 0, null);
        g_combined_lamp_light.drawImage(light_short_path, 0, 0, null);
        g_combined_lamp_light.drawImage(empty, 0, 0, null);

        BufferedImage combined_energy = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g_combined_energy = combined_energy.createGraphics();
        g_combined_energy.drawImage(energy, 0, 0, null);

        readFile("level1.nrg");
        for(int j=0; j<height; j++){
            for (int k=0; k<length; k++){
                System.out.println("x = " + k*120);
                System.out.println("y = " + j*120);
                if (sources.get(j+k+(j*height)).equals(".")){
                    if (directions.get(j+k+(j*height)).size() == 0){
                        g.drawImage(empty, k*120, j*120, this);
                    }else{
                        BufferedImage combined_path = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
                        Graphics2D g_combined_path = combined_path.createGraphics();
                        int difference = Integer.parseInt(directions.get(j+k+(j*height)).get(1))-Integer.parseInt(directions.get(j+k+(j*height)).get(0));
                        if (difference == 1 || difference == 3){
                            if (difference == 1){
                                g_combined_path.rotate(Math.toRadians(Integer.parseInt(directions.get(j+k+(j*height)).get(0))*90), (double) w / 2, (double) h / 2);
                                g_combined_path.drawImage(dark_curved_path, 0, 0, null);
                            }else {
                                g_combined_path.rotate(Math.toRadians(Integer.parseInt(directions.get(j+k+(j*height)).get(1))*90), (double) w / 2, (double) h / 2);
                                g_combined_path.drawImage(dark_curved_path, 0, 0, null);
                            }
                        }else if (difference == 2){
                            g_combined_path.rotate(Math.toRadians(Integer.parseInt(directions.get(j+k+(j*height)).get(0))*90), (double) w / 2, (double) h / 2);
                            g_combined_path.drawImage(dark_long_path, 0, 0, null);
                        }
                        g_combined_path.drawImage(empty, 0, 0, null);
                        g.drawImage(combined_path, k*120, j*120, this);
                    }
                } else if (sources.get(j+k+(j*height)).equals("S")) {
                    switch (directions.get(j+k+(j*height)).get(0)){
                        case "0":
                            g_combined_energy.rotate(Math.toRadians(0), (double) w / 2, (double) h / 2);
                            break;
                        case "1":
                            g_combined_energy.rotate(Math.toRadians(90), (double) w / 2, (double) h / 2);
                            break;
                        case "2":
                            g_combined_energy.rotate(Math.toRadians(180), (double) w / 2, (double) h / 2);
                            break;
                        case "3":
                            g_combined_energy.rotate(Math.toRadians(270), (double) w / 2, (double) h / 2);
                            break;
                    }
                    g_combined_energy.drawImage(light_short_path, 0, 0, null);
                    g_combined_energy.drawImage(empty, 0, 0, null);
                    g.drawImage(combined_energy, k*120, j*120, this);
                    has_electric.set(j+k+(j*height), true);
                } else if (sources.get(j+k+(j*height)).equals("L")) {
                    BufferedImage combined_lamp = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
                    Graphics2D g_combined_lamp = combined_lamp.createGraphics();
                    switch (directions.get(j+k+(j*height)).get(0)){
                        case "0":
                            g_combined_lamp_dark.rotate(Math.toRadians(0), (double) w / 2, (double) h / 2);
                            break;
                        case "1":
                            g_combined_lamp_dark.rotate(Math.toRadians(90), (double) w / 2, (double) h / 2);
                            break;
                        case "2":
                            g_combined_lamp_dark.rotate(Math.toRadians(180), (double) w / 2, (double) h / 2);
                            break;
                        case "3":
                            g_combined_lamp_dark.rotate(Math.toRadians(270), (double) w / 2, (double) h / 2);
                            break;
                    }
                    g_combined_lamp_dark.drawImage(short_path, 0, 0, null);
                    g_combined_lamp_dark.drawImage(empty, 0, 0, null);
                    g.drawImage(combined_lamp_dark, k*120, j*120, this);
                } else if (sources.get(j+k+(j*height)).equals("W")) {
                    g.drawImage(dark_wifi, k*120, j*120, this);
                }
            }
        }
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

    @Override
    public void mouseClicked(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        System.out.println("x = " + x + " y = " + y);
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
