package InterfaceGraphique.algorithm;

import java.util.Arrays;

public class Energy {
    private final double[] values;
    public Energy(double[] values) {
        this.values = values;
    }

    public double calculate() {
        double sum = Arrays.stream(values).sum();
        return Math.pow(sum, 2);
    }

}
