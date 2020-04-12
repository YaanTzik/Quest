public class Dragon extends Monster {
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_RESET = "\u001B[0m";

    public Dragon(String Name, int level, int Damage, int Defense, int DodgeChance) {
        super(Name, level, Damage, Defense, DodgeChance);
    }

    public String toString() {
        return ANSI_RED
                + String.format("Name:%s, Hp:%d Type:Dragon, Level:%d\n", this.getName(), this.getHp(), this.getLevel())
                + ANSI_RESET;

    }

    public String MapRepresent() {
        return ANSI_RED + "M" + Integer.toString(getMonsterNum()) + ANSI_RESET;

    }

    public Dragon clone() {
        return new Dragon(getName(), getLevel(), getDamage(), getDefense(), getDodgeChance());
    }
}