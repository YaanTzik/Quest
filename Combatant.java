public abstract class Combatant {
    private String Name;
    private int Level;
    private boolean fainted;
    private int currHp;
    private Coordinates coordinates;

    public Combatant(String Name, int Level) {
        this.Name = Name;
        this.Level = Level;
        this.fainted = false;
        this.currHp = 100 * getLevel();

    }

    public void setCoordinates(Coordinates c) {
        this.coordinates = c;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public String getName() {
        return this.Name;
    }

    public void setLevel(int i) {
        this.Level = i;
    }

    public int getLevel() {
        return this.Level;
    }

    public void setFainted(boolean status) {
        this.fainted = status;
    }

    public boolean getFainted() {
        return this.fainted;
    }

    public void setHp(int i) {
        this.currHp = i;
    }

    public int getHp() {
        return this.currHp;
    }

    // public abstract String MapRepresent();
}