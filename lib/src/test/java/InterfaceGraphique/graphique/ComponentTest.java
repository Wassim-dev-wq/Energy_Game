package InterfaceGraphique.graphique;

<<<<<<< HEAD
=======
import InterfaceGraphique.graphique.ComponentType.Component;
>>>>>>> recovery
import InterfaceGraphique.graphique.ComponentType.Lamp;
import InterfaceGraphique.graphique.ComponentType.Source;
import InterfaceGraphique.graphique.ComponentType.Wifi;

import org.junit.Test;
import static org.junit.Assert.*;

public class ComponentTest {

    @Test
    public void testWifiDescription() {
        Wifi wifi = new Wifi();
        String description = wifi.getDescription();
        assertEquals("Wifi", description);
    }

    @Test
    public void testLampDescription() {
        Lamp lamp = new Lamp();
        String description = lamp.getDescription();
        assertEquals("Lamp", description);
    }

    @Test
    public void testSourceDescription() {
        Source source = new Source();
        String description = source.getDescription();
        assertEquals("Source", description);
    }

    @Test
    public void testPowerMethods() {
        Component[] components = new Component[] {new Wifi(), new Lamp(), new Source()};

        for (Component component : components) {
            assertFalse(component.is_powered()); // The component should be off by default

            component.power_on();
            assertTrue(component.is_powered()); // The component should be on

            component.power_off();
            assertFalse(component.is_powered()); // The component should be off
        }
    }
}
