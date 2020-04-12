public class ExoSkeleton extends Monster {
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_RESET = "\u001B[0m";

    public ExoSkeleton(String Name, int level, int Damage, int Defense, int DodgeChance) {
        super(Name, level, Damage, Defense, DodgeChance);
    }

    public String toString() {
        return ANSI_PURPLE
                + String.format("Name:%s, Hp:%d Type:Dragon, Level:%d\n", this.getName(), this.getHp(), this.getLevel())
                + ANSI_RESET;

    }

    public String MapRepresent() {
        return ANSI_PURPLE + "M" + Integer.toString(getMonsterNum()) + ANSI_RESET;

    }

    public ExoSkeleton clone() {
        return new ExoSkeleton(getName(), getLevel(), getDamage(), getDefense(), getDodgeChance());
    }

}