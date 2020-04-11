public abstract class Combatant {
    private String Name;
    private int Level;
    private boolean fainted;
    private int currHp;

    public Combatant(String Name, int Level) {
        this.Name = Name;
        this.Level = Level;
        this.fainted = false;
        this.currHp = 100 * getLevel();

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

    public abstract String MapRepresent();
}