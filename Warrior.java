public class Warrior extends Hero {
    public Warrior(String name, int mp, int str, int agi, int dex, int Money, int exp) {
        super(name, mp, str, agi, dex, Money, exp);
    }

    public String toString() {
        String ANSI_GREEN = "\u001B[32m";
        String ANSI_RESET = "\u001B[0m";
        String ret = "";
        String slots = "";
        if (getWeaponSlot() != null) {
            slots += "Weapon Slot:" + getWeaponSlot().toString() + "\n";
        } else
            slots += "Weapon Slot: Empty \n";
        if (getArmorSlot() != null) {
            slots += getArmorSlot().toString() + "\n";
        } else
            slots += "Armor Slot: Empty \n";
        ret = String.format(
                "Name: %s, Class: Warrior,Level: %d \nHp:%d, Mp%d \nStrength: %d, Agility: %d, Dexterity:%d \nMoney: %d \n",
                getName(), getLevel(), getHp(), getMp(), getStr(), getAgi(), getDex(), getMoney());
        return ANSI_GREEN + ret + slots + ANSI_RESET;

    }

    public String MapRepresent(int Num) {
        String ANSI_GREEN = "\u001B[32m";
        String ANSI_RESET = "\u001B[0m";
        return ANSI_GREEN + "M" + Integer.toString(Num) + ANSI_RESET;
    }

    public void LevelUp() {
        setLevel(getLevel() + 1);
        setHp(getLevel() * 100);
        setMp((int) Math.round(getMp() + (getMp() * 0.1)));
        setStr((int) Math.round(1.1 * getStr()));
        setAgi((int) Math.round(1.1 * getAgi()));
        setDex((int) Math.round(1.05 * getDex()));

    }

}