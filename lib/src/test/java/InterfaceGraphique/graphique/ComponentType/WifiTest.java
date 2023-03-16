package InterfaceGraphique.graphique.ComponentType;

import org.junit.Test;
import static org.junit.Assert.*;

public class WifiTest {
    @Test
    public void testWifiDescription(){
        Wifi wifi = new Wifi();
        String description = wifi.getDescription();
        assertEquals("Wifi",description);
    }
}
