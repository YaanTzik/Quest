import java.util.Random;
import java.util.Scanner;
import java.util.ArrayList;

public abstract class Hero extends Combatant {
    private int CurrMp;
    private int Str;
    private int Dex;
    private int Agi;
    private int Exp;
    private int ExpCap;
    private Weapon WeaponSlot;
    private Armor ArmorSlot;
    private int Money;
    private Inventory bag;
    private Random rd;
    private Scanner sc;
    // tracks the number of the hero in the roster
    // they are put in. Helps with identification
    // on Map.
    private int HeroNum;
    private Tile CurrTile;
    private int Boost;

    public Hero(String name, int mp, int str, int agi, int dex, int Money, int exp) {
        super(name, 1);
        // removed hero type as it should be an actual class as opposed to an enum.
        // this.Type = type;
        this.CurrMp = mp;
        this.Str = str;
        this.Dex = dex;
        this.Agi = agi;
        this.Exp = exp;
        this.bag = new Inventory();
        this.ExpCap = getLevel() * 10;
        this.Money = Money;
        this.WeaponSlot = null;
        this.ArmorSlot = null;
        this.rd = new Random();
        this.Boost = 0;
        this.CurrTile = Tile.COMMON;
        sc = new Scanner(System.in);

    }

    // toString method
    public abstract String toString();

    // Getters and Setters for all relevant Fields
    public void setHeroNum(int num) {
        this.HeroNum = num;
    }

    public int getHeroNum() {
        return this.HeroNum;
    }

    public int getMp() {
        return CurrMp;
    }

    public void setMp(int mp) {
        this.CurrMp = mp;
    }

    public int getStr() {
        return Str;
    }

    public void setStr(int s) {
        this.Str = s;
    }

    public int getDex() {
        return Dex;
    }

    public void setDex(int d) {
        this.Dex = d;
    }

    public int getAgi() {
        return Agi;
    }

    public void setAgi(int A) {
        this.Agi = A;
    }

    public int getMoney() {
        return this.Money;
    }

    public void setMoney(int m) {
        this.Money = m;
    }

    public Weapon getWeaponSlot() {
        return this.WeaponSlot;
    }

    public Armor getArmorSlot() {
        return this.ArmorSlot;
    }

    public Inventory getBag() {
        return this.bag;
    }

    public void display() {
        System.out.printf(
                "Hero Name: %s \n Class: %s \n Level: %d \n Hp: %d \n Mp: %d \nStr: %d \n Dex: %d \n Age: %d \n Exp: %d / %d",
                getName(), getLevel(), getHp(), CurrMp, Str, Dex, Agi, Exp, ExpCap);
        System.out.print(this.bag);
    }

    public int AttackDamage() {
        if (WeaponSlot != null)
            return (int) Math.round((getStr() + WeaponSlot.getDamage()) * 0.05);
        else {
            return (int) Math.round(getStr() * 0.05);
        }

    }

    public int SpellDmg(Spell spell) {
        int BaseDmg = spell.getDamage();
        return (int) Math.round(BaseDmg + (this.getDex() / 10000) * BaseDmg);
    }

    public void incomingDamage(int attack) {
        double dodgeroll = rd.nextDouble();
        // System.out.println(dodgeroll);
        // System.out.println(this.getAgi() * 0.02);
        if (dodgeroll < 1 / (this.getAgi() * 0.02)) {
            System.out.printf("%s dodged the attack attack\n", this.getName());
        } else {
            int TrueDmg;
            if (ArmorSlot != null)
                TrueDmg = attack - ArmorSlot.getReduction();
            else
                TrueDmg = attack;
            if (TrueDmg > 1) {
                setHp(getHp() - TrueDmg);
                System.out.printf("%s has taken %d damange \n", getName(), TrueDmg);

            } else {
                setHp(getHp() - 1);
                System.out.printf("%s has taken 1 damage\n", getName());
            }
            if (getHp() <= 0) {
                setHp(0);
                super.setFainted(true);
                System.out.printf("%s has fainted \n", getName());
            }
        }
    }

    public abstract void LevelUp();

    public void WinFight() {
        if (getFainted() == true) {
            setHp(50 * getLevel());
            setFainted(false);
        } else {
            this.Exp += 2;
            setHp((int) Math.round(getHp() * 1.05));
            this.CurrMp = (int) Math.round(this.CurrMp * 1.05);
            setMoney(getMoney() + 150);
            if (Exp >= 10 * getLevel()) {
                System.out.printf("%s leveled up!\n", this.getName());
                this.LevelUp();
            }
        }
    }

    public void usePotion(Potion pot) {
        if (pot.getlevelReq() > this.getLevel()) {
            System.out.println("Your level is too low to use this item.");
            return;
        } else {
            PotionType boosting = pot.getStat();
            switch (boosting) {
                case All:
                    setStr(getStr() + pot.getBoost());
                    setDex(getDex() + pot.getBoost());
                    setAgi(getAgi() + pot.getBoost());
                    System.out.printf("All Stats Boosted by %d \n", pot.getBoost());
                case Strength:
                    setStr(getStr() + pot.getBoost());
                    System.out.printf("Strength boosted by %d \n", pot.getBoost());
                case Dexterity:
                    setDex(getDex() + pot.getBoost());
                    System.out.printf("Dexterity boosted by %d \n", pot.getBoost());
                case Agility:
                    setAgi(getAgi() + pot.getBoost());
                    System.out.printf("Agility boosted by %d \n", pot.getBoost());
                case Hp:
                    setHp(getHp() + pot.getBoost());
                    System.out.printf("Hp increased by %d \n", pot.getBoost());
                case Mp:
                    setMp(getMp() + pot.getBoost());
                    System.out.printf("Mp increased by %d \n", pot.getBoost());

            }

        }

    }

    public void OpenInventory() {
        boolean close = false;
        while (!close) {
            // We create an interface for the menu so each category of item takes a page.
            System.out.println("What would you like to use?");
            System.out.println("1. Weapon ");
            System.out.println("2. Armor");
            System.out.println("3. Potion");
            System.out.println("4. Spell");
            System.out.println("enter 5 to close inventory");
            int choice;
            do {
                System.out.println("Please choose the type of item you would like to use! (1-5)");
                while (!sc.hasNextInt()) {
                    System.out.println("Invalid input, please enter a number from 1-5");
                    sc.next(); // this is important!
                }
                choice = sc.nextInt();
            } while (choice <= 0 || choice > 5);
            if (choice == 5) {
                return;
            } else if (choice == 1) {
                Weapon eq = bag.openSubbag(bag.getWeapons());
                if (eq != null) {
                    EquipWeapon(eq);
                    close = true;
                } else
                    continue;
            } else if (choice == 2) {
                Armor ar = bag.openSubbag(bag.getArmors());
                if (ar != null) {
                    EquipArmor(ar);
                    close = true;
                } else
                    continue;
            }

            else if (choice == 3) {
                Potion pot = bag.openSubbag(bag.getPotions());
                if (pot != null) {
                    usePotion(pot);
                    close = true;

                } else
                    continue;
            } else {
                // Spell doesn't go off as it should be directly accessed in the spell menu in
                // combat
                Spell sp = bag.openSubbag(bag.getSpells());
                if (sp != null) {
                    close = true;
                } else
                    continue;
            }
        }
    }

    public void EquipWeapon(Weapon w) {
        if (w.getlevelReq() <= this.getLevel()) {
            if (WeaponSlot == null) {
                WeaponSlot = w;
            } else {
                Weapon temp = WeaponSlot;
                WeaponSlot = w;
                bag.addWeapon(temp);
            }
        } else {
            System.out.println("Your level is too low to equip this weapon");
            bag.addWeapon(w);
        }
    }

    public void EquipArmor(Armor ar) {
        if (ar.getlevelReq() <= this.getLevel()) {
            if (ArmorSlot == null) {
                ArmorSlot = ar;
            } else {
                Armor temp = ArmorSlot;
                ArmorSlot = ar;
                bag.addArmor(temp);
            }
        } else {
            System.out.println("Your level is too low to equip this armor");
            bag.addArmor(ar);
        }
    }

    public <T extends Item> void PurchaseItem(T item, ArrayList<T> subbag) {
        int cost = item.getCost();
        if (cost > this.Money) {
            System.out.println("You don't have enough money to purchase this item. \n");
            return;
        } else {
            setMoney(this.Money - cost);
            subbag.add(item);
            System.out.println("Purchase Successful!\n");
            return;
        }
    }

    public <T extends Item> void sellItem(T item) {
        // handles
        int sellPrice = item.getCost() / 2;
        setMoney(this.Money - sellPrice);
        System.out.printf("Sale successful,you now have %d monies\n", this.getMoney());
    }

    public void Lose() {
        this.setFainted(false);
        this.setHp(getLevel() * 100 / 2);
        this.setMoney(this.getMoney() / 2);
    }

    public void respawn() {
        setFainted(false);
        setHp(getLevel() * 100);
        setMp(getLevel() * 300);
    }

    public void TileBoost(Tile t) {
        if (t == CurrTile) {
            return;
        }
        // We first remove the Buff of the previous Tile.
        switch (CurrTile) {
            case COMMON:
                break;
            case NONPLAYABLE:
                break;
            case NEXUS:
                break;
            case KOULOU:
                Str -= Boost;
                break;
            case CAVE:
                Agi -= Boost;
                break;
            case BUSH:
                Dex -= Boost;
                break;
        }// calculate Boost value then swap the currTile
        switch (t) {
            case COMMON:
                break;
            case NONPLAYABLE:
                break;
            case NEXUS:
                break;
            case KOULOU:
                Boost = (int) Math.round(Str * 0.1);
                Str += Boost;
                System.out.printf("%s's Strength boosted \n", getName());
                break;
            case CAVE:
                Boost = (int) Math.round(Agi * 0.1);
                Agi += Boost;
                System.out.printf("%s's Agility boosted \n", getName());
                break;
            case BUSH:
                Boost = (int) Math.round(Dex * 0.1);
                Dex += Boost;
                System.out.printf("%s's Dexterity boosted \n", getName());
                break;

        }
        CurrTile = t;
        return;

    }
    // All refactored into generic Method sellItem()

    // public void SellWeapon(Weapon w){
    // int sellPrice = w.getCost()/2;
    // setMoney(this.Money-sellPrice);
    // System.out.printf("Sale successful,you now have %d
    // monies\n",this.getMoney());
    // }
    // public void SellArmor(Armor ar){
    // int sellPrice = ar.getCost() / 2;
    // setMoney(this.Money - sellPrice);
    // System.out.printf("Sale successful,you now have %d monies\n",
    // this.getMoney());
    // }
    // public void SellPotion(Potion pot){
    // int sellPrice = pot.getCost() / 2;
    // setMoney(this.Money - sellPrice);
    // System.out.printf("Sale successful,you now have %d monies\n",
    // this.getMoney());
    // }
    // public void SellSpell(Spell sp ){
    // int sellPrice = sp.getCost() / 2;
    // setMoney(this.Money - sellPrice);
    // System.out.printf("Sale successful,you now have %d monies\n",
    // this.getMoney());
    // }

    // Refactored into purchaseItem

    // public void PurchaseArmor(Armor ar) {
    // int cost = ar.getCost();
    // if (cost > this.Money) {
    // System.out.println("You don't have enough money to purchase this item.");
    // return;
    // } else {
    // setMoney(this.Money - cost);
    // bag.addArmor(ar);
    // System.out.println("Purchase Successful!");
    // return;
    // }
    // }

    // public void PurchasePotion(Potion pot) {
    // int cost = pot.getCost();
    // if (cost > this.Money) {
    // System.out.println("You don't have enough money to purchase this item.");
    // return;
    // } else {
    // setMoney(this.Money - cost);
    // bag.addPotion(pot);
    // return;
    // }

    // }

    // public void PurchaseSpell(Spell sp) {
    // int cost = sp.getCost();
    // if (cost > this.Money) {
    // System.out.println("You don't have enough money to purchase this item.");
    // return;
    // } else {
    // setMoney(this.Money - cost);
    // bag.addSpell(sp);
    // return;
    // }
    // }
}