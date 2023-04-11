package InterfaceGraphique.graphique.ComponentType;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SourceTest {
    @Test
    public void testSourceDescription(){
        Source source = new Source();
        String description = source.getDescription();
        assertEquals("Source",description);
    }
}
