package InterfaceGraphique.algorithm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Level {
    private int height;
    private int width;
    private List<String> data_list = new ArrayList<>();
    private List<String> sources = new ArrayList<>();
    private List<List<String>> directions = new ArrayList<>();
    private List<Boolean> has_electric = new ArrayList<>();

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public List<String> getSources() {
        return sources;
    }

    public List<List<String>> getDirections() {
        return directions;
    }

    public Level(InputStream levelStream) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(levelStream));
            String line;

            while ((line = br.readLine()) != null) {
                String[] parts = line.split(" ");
                for (String part : parts) {
                    data_list.add(part);
                }
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        height = Integer.parseInt(data_list.get(0));
        width = Integer.parseInt(data_list.get(1));
        String format = data_list.get(2);

        for (int i = 3; i < data_list.size(); i++) {
            String s = data_list.get(i);
            if (s.equals(".") || s.equals("S") || s.equals("L")) {
                List<String> position = new ArrayList<>();
                if (i + 1 < data_list.size()) {
                    i++;
                    while (i != data_list.size() && !(data_list.get(i).equals(".") || data_list.get(i).equals("S") || data_list.get(i).equals("L"))) {
                        position.add(data_list.get(i));
                        i++;
                    }
                    sources.add(s);
                    directions.add(position);
                    i--;
                } else {
                    sources.add(s);
                    directions.add(position);
                    break;
                }
            }
        }

        // Initialize the has_electric list
        for (int i = 0; i < height * width; i++) {
            has_electric.add(false);
        }
    }
}
