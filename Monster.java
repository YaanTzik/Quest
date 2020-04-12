import java.util.Random;

public abstract class Monster extends Combatant implements Cloneable {
    // private String Type;
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_RESET = "\u001B[0m";
    private int Damage;
    private int Defense;
    private int DodgeChance;
    private Random rd;
    private int MonsterNum;

    public Monster(String Name, int level, int Damage, int Defense, int DodgeChance) {
        super(Name, level);
        // System.out.println("Creating Monster");
        this.Damage = Damage;
        this.Defense = Defense;
        this.DodgeChance = DodgeChance;
        this.rd = new Random();

    }

    public abstract String toString();

    public abstract Monster clone();

    public void setMonsterNum(int num) {
        this.MonsterNum = num;
    }

    public int getMonsterNum() {
        return this.MonsterNum;
    }

    public int getDamage() {
        return Damage;
    }

    public int getDefense() {
        return Defense;
    }

    public int getDodgeChance() {
        return DodgeChance;
    }

    public void receiveAttack(int Damage) {
        double p = 1 / DodgeChance;
        double dodgeroll = rd.nextDouble();
        if (dodgeroll < p) {
            System.out.printf("%s dodged the attack!\n", this.getName());
        } else {
            int TrueDmg = Damage - this.Defense;
            if (TrueDmg <= 0) {
                TrueDmg = 1;
            }
            System.out.printf("%s received %d damage! \n", getName(), TrueDmg);
            setHp(getHp() - TrueDmg);

        }
        if (getHp() <= 0) {
            setFainted(true);
            System.out.printf("%s has fainted\n", getName());
        }

    }

    public <E extends Hero> void receiveSpell(Spell incoming, E h) {
        double p = 1 / DodgeChance;
        double dodgeroll = rd.nextDouble();
        if (dodgeroll < p) {
            System.out.printf("%s dodged the attack!", getName());
        } else {
            int TrueDmg = h.SpellDmg(incoming) - this.Defense;
            if (TrueDmg <= 0) {
                TrueDmg = 1;
            }
            setHp(getHp() - TrueDmg);
            System.out.printf("%s took %d damage\n", getName(), TrueDmg);
            SpellType Effect = incoming.getType();
            switch (Effect) {
                case FIRE:
                    this.Defense = (int) Math.round(this.Defense * 0.9);
                    System.out.printf("%s's defense got reduced!\n", this.getName());
                    break;
                case ICE:
                    this.Damage = (int) Math.round(this.Damage * 0.9);
                    System.out.printf("%s's attack got reduced!\n", this.getName());
                    break;
                case LIGHTNING:
                    this.DodgeChance = (int) Math.round(this.DodgeChance * 1.1);
                    System.out.printf("%s's Dodge got reduced!\n", this.getName());
                    break;
            }
        }
        if (getHp() <= 0) {
            this.setFainted(true);
            System.out.printf("%s has fainted\n", getName());
        }
    }
}