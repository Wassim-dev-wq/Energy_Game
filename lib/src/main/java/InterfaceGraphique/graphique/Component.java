package InterfaceGraphique.graphique;

public abstract class Component {
    protected Boolean powered = false;
    public abstract String getDescription();
    public void power_on() {
        powered = true;
    }
    public void power_off() {
        powered = false;
    }
    public boolean is_powered() {
        return powered;
    }
}
