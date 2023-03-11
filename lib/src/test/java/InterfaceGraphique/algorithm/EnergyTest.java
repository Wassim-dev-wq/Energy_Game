package InterfaceGraphique.algorithm;


import org.junit.Test;
import static org.junit.Assert.*;

public class EnergyTest {

    @Test public void testEnergyCalculation() {
        double[] values = {1.0, 2.0, 3.0, 4.0};
        Energy energy = new Energy(values);
        double expected = 300.0;
        //double expected = 30.0; // it will fail
        double actual = energy.calculate();
        assertEquals(expected, actual, 0.0001);
    }
}
