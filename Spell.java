public class Spell extends Item {
    private SpellType Type;
    private int Damage;
    private int ManaCost;
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    public Spell(String name, int Cost, int level, int dmg, int Mana, SpellType type) {
        super(name, Cost, level);
        this.Damage = dmg;
        this.ManaCost = Mana;
        this.Type = type;
    }

    public int getDamage() {
        return Damage;
    }

    public int getManaCost() {
        return ManaCost;
    }

    public SpellType getType() {
        return Type;
    }

    public String toString() {
        switch (Type) {
            case FIRE:
                return ANSI_RED + String.format(
                        "Spell Name: %s Spell Type: Fire Damage: %d ManaCost: %d LevelRequirement: %d Price: %d \n",
                        getName(), getDamage(), getManaCost(), getlevelReq(), getCost()) + ANSI_RESET;
            case ICE:
                return ANSI_CYAN + String.format(
                        "Spell Name: %s Spell Type: Ice Damage: %d ManaCost: %d LevelRequirement: %d Price: %d \n",
                        getName(), getDamage(), getManaCost(), getlevelReq(), getCost()) + ANSI_RESET;
            case LIGHTNING:
                return ANSI_YELLOW + String.format(
                        "Spell Name: %s Spell Type: Lightning Damage: %d ManaCost: %d LevelRequirement: %d Price: %d \n",
                        getName(), getDamage(), getManaCost(), getlevelReq(), getCost()) + ANSI_RESET;
        }

        return String.format("Spell Name: %s Spell Type: %s Damage: %d ManaCost: %d LevelRequirement: %d Price: %d \n",
                getName(), getType(), getDamage(), getManaCost(), getlevelReq(), getCost());
    }
}