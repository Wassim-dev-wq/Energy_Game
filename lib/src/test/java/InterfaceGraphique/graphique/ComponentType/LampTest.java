package InterfaceGraphique.graphique.ComponentType;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class LampTest {
    @Test
    public void testLampDescription(){
        Lamp lamp = new Lamp();
        String description = lamp.getDescription();
        assertEquals("Lamp",description);
    }
}
