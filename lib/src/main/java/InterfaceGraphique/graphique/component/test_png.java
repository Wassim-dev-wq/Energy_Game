package InterfaceGraphique.graphique.component;

import InterfaceGraphique.graphique.ComponentType.ImageLoader;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;
import java.util.List;


public class test_png extends JPanel{
    public  List<String> sources_ = new ArrayList<String>();
    public  List<List<String>> directions_ = new ArrayList<List<String>>();
    public  List<String> data_list_ = new ArrayList<String>();
    public static int height_ = 0;
    public static int length_ = 0;
    public static String format_;
    public test_png(String filename){
        this.readFile(filename);
        this.setPreferredSize(new Dimension(length_*120, height_*120));
        this.setBackground(Color.black);
    }

    public void readFile(String fileName){
        try{
            InputStream levelStream = test_png.class.getResourceAsStream(fileName);
            BufferedReader br = new BufferedReader(new InputStreamReader(levelStream));
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(" ");
                for (String part : parts) {
                    data_list_.add(part);
                }
            }
        }catch (FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        height_ = Integer.parseInt(data_list_.get(0));
        length_ = Integer.parseInt(data_list_.get(1));
        format_ = data_list_.get(2);
        for (int i = 3; i<data_list_.size(); i++){
            String s = data_list_.get(i);
            if (s.equals(".") || s.equals("S") || s.equals("L") || s.equals("W")){
                List<String> position = new ArrayList<String>();
                if (i+1 < data_list_.size()){
                    i++;
                    while (i!=data_list_.size() && !(data_list_.get(i).equals(".") || data_list_.get(i).equals("S") || data_list_.get(i).equals("L") || data_list_.get(i).equals("W"))){
                        position.add(data_list_.get(i));
                        i++;
                    }
                    sources_.add(s);
                    directions_.add(position);
                    i--;
                }else{
                    sources_.add(s);
                    directions_.add(position);
                    break;
                }
            }

        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Image src = null;
        try {
            src = ImageIO.read(ImageLoader.class.getResourceAsStream("/tuiles.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        int x = 0, y = 0, w = 120, h = 120;
        BufferedImage empty_light = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        empty_light.getGraphics().drawImage(src, 0, 0, w, h, x, y, x + w, y + h, null);
        x = 120;
        y = 240;
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

        if (format_.equals("S")) {
            int u = 0;
            int u_y = 0;
            for (int i = 0; i < length_ * height_; i++) {
                if (sources_.get(i).equals(".")) {
                    BufferedImage combined_empty_square_dark = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
                    Graphics2D g_combined_empty_square_dark = combined_empty_square_dark.createGraphics();
                    g_combined_empty_square_dark.drawImage(empty_light, 0, 0, null);
                    if (directions_.get(i).size() == 0) {
                        g.drawImage(empty_light, (i % length_) * 120, u * 120, this);
                        u_y++;
                    } else {
                        int start = Integer.parseInt(directions_.get(i).get(0));
                        int angle = 0;
                        for (int t = 1; t < directions_.get(i).size(); t++) {
                            int diff = Integer.parseInt(directions_.get(i).get(t)) - start;
                            if (diff == 1 || diff == 3) {
                                if (diff == 1) {
                                    g_combined_empty_square_dark.rotate(Math.toRadians((start * 90) - angle), (double) w / 2, (double) h / 2);
                                    angle = start * 90;
                                } else {
                                    g_combined_empty_square_dark.rotate(Math.toRadians((Integer.parseInt(directions_.get(i).get(t)) * 90) - angle), (double) w / 2, (double) h / 2);
                                    angle = Integer.parseInt(directions_.get(i).get(t)) * 90;
                                }
                                g_combined_empty_square_dark.drawImage(light_curved_path, 0, 0, null);
                            } else if (diff == 2) {
                                g_combined_empty_square_dark.rotate(Math.toRadians((start * 90) - angle), (double) w / 2, (double) h / 2);
                                angle = start * 90;
                                g_combined_empty_square_dark.drawImage(light_long_path, 0, 0, null);
                            }
                        }
                        g.drawImage(combined_empty_square_dark, (i % length_) * 120, u * 120, this);
                        u_y++;
                    }
                } else if (sources_.get(i).equals("S")) {
                    BufferedImage combined_energy = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
                    Graphics2D g_combined_energy = combined_energy.createGraphics();
                    g_combined_energy.drawImage(energy, 0, 0, null);
                    int angle = 0;
                    for (int i_w = 0; i_w < directions_.get(i).size(); i_w++) {
                        g_combined_energy.rotate(Math.toRadians((Integer.parseInt(directions_.get(i).get(i_w)) * 90) - angle), (double) w / 2, (double) h / 2);
                        g_combined_energy.drawImage(light_short_path, 0, 0, null);
                        angle = Integer.parseInt(directions_.get(i).get(i_w)) * 90;
                    }
                    g_combined_energy.drawImage(empty_light, 0, 0, null);
                    g.drawImage(combined_energy, (i % length_) * 120, u * 120, this);
                    u_y++;
                } else if (sources_.get(i).equals("L")) {
                    BufferedImage combined_lamp_square_dark = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
                    Graphics2D g_combined_lamp_square_dark = combined_lamp_square_dark.createGraphics();
                    g_combined_lamp_square_dark.drawImage(light_lamp, 0, 0, null);
                    int angle = 0;
                    for (int i_w = 0; i_w < directions_.get(i).size(); i_w++) {
                        g_combined_lamp_square_dark.rotate(Math.toRadians((Integer.parseInt(directions_.get(i).get(i_w)) * 90) - angle), (double) w / 2, (double) h / 2);
                        g_combined_lamp_square_dark.drawImage(light_short_path, 0, 0, null);
                        angle = Integer.parseInt(directions_.get(i).get(i_w)) * 90;
                    }
                    g_combined_lamp_square_dark.drawImage(empty_light, 0, 0, null);
                    g.drawImage(combined_lamp_square_dark, (i % length_) * 120, u * 120, this);
                    u_y++;
                } else if (sources_.get(i).equals("W")) {
                    BufferedImage combined_wifi_dark_square = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
                    Graphics2D g_combined_wifi_dark_square = combined_wifi_dark_square.createGraphics();
                    g_combined_wifi_dark_square.drawImage(light_wifi, 0, 0, null);
                    int angle = 0;
                    for (int i_w = 0; i_w < directions_.get(i).size(); i_w++) {
                        g_combined_wifi_dark_square.rotate(Math.toRadians((Integer.parseInt(directions_.get(i).get(i_w)) * 90) - angle), (double) w / 2, (double) h / 2);
                        g_combined_wifi_dark_square.drawImage(light_short_path, 0, 0, null);
                        angle = Integer.parseInt(directions_.get(i).get(i_w)) * 90;
                    }
                    g_combined_wifi_dark_square.drawImage(empty_light, 0, 0, null);
                    g.drawImage(combined_wifi_dark_square, (i % length_) * 120, u * 120, this);
                    u_y++;
                }
                if (u_y == length_) {
                    u++;
                    u_y = 0;
                }
            }
        } else {
            x = 360;
            h = 104;
            y = 360;
            BufferedImage empty_hexagone_light = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
            empty_hexagone_light.getGraphics().drawImage(src, 0, 0, w, h, x, y, x + w, y + h, null);
            y = 480;
            BufferedImage energy_hexagone = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
            energy_hexagone.getGraphics().drawImage(src, 0, 0, w, h, x, y, x + w, y + h, null);
            y = 600;
            BufferedImage short_path_hexagone_light = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
            short_path_hexagone_light.getGraphics().drawImage(src, 0, 0, w, h, x, y, x + w, y + h, null);
            x = 480;
            y = 480;
            BufferedImage wifi_hexagone_light = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
            wifi_hexagone_light.getGraphics().drawImage(src, 0, 0, w, h, x, y, x + w, y + h, null);
            y = 600;
            BufferedImage short_curved_hexagone_path_light = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
            short_curved_hexagone_path_light.getGraphics().drawImage(src, 0, 0, w, h, x, y, x + w, y + h, null);
            x = 600;
            y = 480;
            BufferedImage lamp_hexagone_light = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
            lamp_hexagone_light.getGraphics().drawImage(src, 0, 0, w, h, x, y, x + w, y + h, null);
            y = 600;
            BufferedImage long_curved_hexagone_path_light = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
            long_curved_hexagone_path_light.getGraphics().drawImage(src, 0, 0, w, h, x, y, x + w, y + h, null);
            x = 720;
            y = 600;
            BufferedImage long_hexagone_path_light = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
            long_hexagone_path_light.getGraphics().drawImage(src, 0, 0, w, h, x, y, x + w, y + h, null);

            int u = 0;
            int u_y = 0;
            for (int i = 0; i < length_ * height_; i++) {
                BufferedImage combined_empty_hexagone_dark = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
                Graphics2D g_combined_empty_hexagone_dark = combined_empty_hexagone_dark.createGraphics();
                g_combined_empty_hexagone_dark.drawImage(empty_hexagone_light, 0, 0, null);
                if (sources_.get(i).equals(".")) {
                    if (directions_.get(i).size() == 0) {
                        if (u_y % 2 == 1) {
                            g.drawImage(empty_hexagone_light, (i % length_) * 90, ((u * 104) + 52), this);
                            u_y++;
                        } else {
                            g.drawImage(empty_hexagone_light, (i % length_) * 90, (u * 104), this);
                            u_y++;
                        }
                    } else if (directions_.get(i).size() == 2) {
                        int diff = Integer.parseInt(directions_.get(i).get(1)) - Integer.parseInt(directions_.get(i).get(0));
                        if (diff == 1 || diff == 5) {
                            if (diff == 1)
                                g_combined_empty_hexagone_dark.rotate(Math.toRadians(Integer.parseInt(directions_.get(i).get(0)) * 60), (double) w / 2, (double) h / 2);
                            else
                                g_combined_empty_hexagone_dark.rotate(Math.toRadians(Integer.parseInt(directions_.get(i).get(1)) * 60), (double) w / 2, (double) h / 2);
                            g_combined_empty_hexagone_dark.drawImage(short_curved_hexagone_path_light, 0, 0, null);
                        } else if (diff == 3) {
                            g_combined_empty_hexagone_dark.rotate(Math.toRadians(Integer.parseInt(directions_.get(i).get(0)) * 60), (double) w / 2, (double) h / 2);
                            g_combined_empty_hexagone_dark.drawImage(long_hexagone_path_light, 0, 0, null);
                        } else if (diff == 2 || diff == 4) {
                            if (diff == 2)
                                g_combined_empty_hexagone_dark.rotate(Math.toRadians(Integer.parseInt(directions_.get(i).get(0)) * 60), (double) w / 2, (double) h / 2);
                            else
                                g_combined_empty_hexagone_dark.rotate(Math.toRadians(Integer.parseInt(directions_.get(i).get(1)) * 60), (double) w / 2, (double) h / 2);
                            g_combined_empty_hexagone_dark.drawImage(long_curved_hexagone_path_light, 0, 0, null);
                        }
                        if (u_y % 2 == 1) {
                            g.drawImage(combined_empty_hexagone_dark, (i % length_) * 90, ((u * 104) + 52), this);
                            u_y++;
                        } else {
                            g.drawImage(combined_empty_hexagone_dark, (i % length_) * 90, (u * 104), this);
                            u_y++;
                        }
                    } else {
                        int start = Integer.parseInt(directions_.get(i).get(0));
                        int angle = 0;
                        for (int k = 1; k < directions_.get(i).size(); k++) {
                            int diff = Integer.parseInt(directions_.get(i).get(k)) - start;
                            if (diff == 1 || diff == 5) {
                                if (diff == 1) {
                                    g_combined_empty_hexagone_dark.rotate(Math.toRadians((start * 60) - angle), (double) w / 2, (double) h / 2);
                                    angle = start * 60;
                                } else {
                                    g_combined_empty_hexagone_dark.rotate(Math.toRadians((Integer.parseInt(directions_.get(i).get(k)) * 60) - angle), (double) w / 2, (double) h / 2);
                                    angle = Integer.parseInt(directions_.get(i).get(k)) * 60;
                                }
                                g_combined_empty_hexagone_dark.drawImage(short_curved_hexagone_path_light, 0, 0, null);
                            } else if (diff == 3) {
                                g_combined_empty_hexagone_dark.rotate(Math.toRadians((start * 60) - angle), (double) w / 2, (double) h / 2);
                                angle = start * 60;
                                g_combined_empty_hexagone_dark.drawImage(long_hexagone_path_light, 0, 0, null);
                            } else if (diff == 2 || diff == 4) {
                                if (diff == 2) {
                                    g_combined_empty_hexagone_dark.rotate(Math.toRadians((start * 60) - angle), (double) w / 2, (double) h / 2);
                                    angle = start * 60;
                                } else {
                                    g_combined_empty_hexagone_dark.rotate(Math.toRadians((Integer.parseInt(directions_.get(i).get(k)) * 60) - angle), (double) w / 2, (double) h / 2);
                                    angle = Integer.parseInt(directions_.get(i).get(k)) * 60;
                                }
                                g_combined_empty_hexagone_dark.drawImage(long_curved_hexagone_path_light, 0, 0, null);
                            }
                        }
                        if (u_y % 2 == 1) {
                            g.drawImage(combined_empty_hexagone_dark, (i % length_) * 90, ((u * 104) + 52), this);
                            u_y++;
                        } else {
                            g.drawImage(combined_empty_hexagone_dark, (i % length_) * 90, (u * 104), this);
                            u_y++;
                        }
                    }
                } else if (sources_.get(i).equals("W")) {
                    BufferedImage combined_wifi_dark_hexagone = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
                    Graphics2D g_combined_wifi_dark_hexagone = combined_wifi_dark_hexagone.createGraphics();
                    g_combined_wifi_dark_hexagone.drawImage(wifi_hexagone_light, 0, 0, null);
                    int angle = 0;
                    for (int i_w = 0; i_w < directions_.get(i).size(); i_w++) {
                        g_combined_wifi_dark_hexagone.rotate(Math.toRadians((Integer.parseInt(directions_.get(i).get(i_w)) * 60) - angle), (double) w / 2, (double) h / 2);
                        g_combined_wifi_dark_hexagone.drawImage(short_path_hexagone_light, 0, 0, null);
                        angle = Integer.parseInt(directions_.get(i).get(i_w)) * 60;
                    }
                    g_combined_wifi_dark_hexagone.drawImage(empty_hexagone_light, 0, 0, null);
                    if (u_y % 2 == 1) {
                        g.drawImage(combined_wifi_dark_hexagone, (i % length_) * 90, ((u * 104) + 52), this);
                        u_y++;
                    } else {
                        g.drawImage(combined_wifi_dark_hexagone, (i % length_) * 90, (u * 104), this);
                        u_y++;
                    }
                } else if (sources_.get(i).equals("S")) {
                    BufferedImage combined_source_hexagone = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
                    Graphics2D g_combined_source_hexagone = combined_source_hexagone.createGraphics();
                    g_combined_source_hexagone.drawImage(energy_hexagone, 0, 0, null);
                    int angle = 0;
                    for (int i_w = 0; i_w < directions_.get(i).size(); i_w++) {
                        g_combined_source_hexagone.rotate(Math.toRadians((Integer.parseInt(directions_.get(i).get(i_w)) * 60) - angle), (double) w / 2, (double) h / 2);
                        g_combined_source_hexagone.drawImage(short_path_hexagone_light, 0, 0, null);
                        angle = Integer.parseInt(directions_.get(i).get(i_w)) * 60;
                    }
                    g_combined_source_hexagone.drawImage(empty_hexagone_light, 0, 0, null);
                    if (u_y % 2 == 1) {
                        g.drawImage(combined_source_hexagone, (i % length_) * 90, ((u * 104) + 52), this);
                        u_y++;
                    } else {
                        g.drawImage(combined_source_hexagone, (i % length_) * 90, (u * 104), this);
                        u_y++;
                    }
                } else if (sources_.get(i).equals("L")) {
                    BufferedImage combined_lamp_hexagone_dark = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
                    Graphics2D g_combined_lamp_hexagone_dark = combined_lamp_hexagone_dark.createGraphics();
                    g_combined_lamp_hexagone_dark.drawImage(lamp_hexagone_light, 0, 0, null);
                    int angle = 0;
                    for (int i_w = 0; i_w < directions_.get(i).size(); i_w++) {
                        g_combined_lamp_hexagone_dark.rotate(Math.toRadians((Integer.parseInt(directions_.get(i).get(i_w)) * 60) - angle), (double) w / 2, (double) h / 2);
                        g_combined_lamp_hexagone_dark.drawImage(short_path_hexagone_light, 0, 0, null);
                        angle = Integer.parseInt(directions_.get(i).get(i_w)) * 60;
                    }
                    g_combined_lamp_hexagone_dark.drawImage(empty_hexagone_light, 0, 0, null);
                    if (u_y % 2 == 1) {
                        g.drawImage(combined_lamp_hexagone_dark, (i % length_) * 90, ((u * 104) + 52), this);
                        u_y++;
                    } else {
                        g.drawImage(combined_lamp_hexagone_dark, (i % length_) * 90, (u * 104), this);
                        u_y++;
                    }
                }
                if (u_y == length_) {
                    u++;
                    u_y = 0;
                }
            }
        }
    }
}
