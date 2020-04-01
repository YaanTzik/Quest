import java.util.Random;
import java.util.Scanner;
import java.util.ArrayList;
public class Hero extends Combatant{
    private HeroType Type;
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
    

public Hero(String name, HeroType type ,int mp, int str, int agi, int dex,int Money, int exp){
    super(name,1);
    this.Type = type;
    this.CurrMp = mp; 
    this.Str= str;
    this.Dex= dex;
    this.Agi = agi;
    this.Exp = exp;
    this.bag= new Inventory();
    this.ExpCap =getLevel() * 10;
    this.Money = Money;
    this.WeaponSlot= null;
    this.ArmorSlot = null;
    this.rd = new Random();
    sc= new Scanner(System.in);

}
//toString method 
public String toString(){
    String ANSI_RESET = "\u001B[0m";
    String ANSI_RED = "\u001B[31m";
    String ANSI_BLUE = "\u001B[34m";
    String ANSI_YELLOW = "\u001B[33m";
    String ret ="";
    String slots ="";
    if(WeaponSlot!= null){
        slots +="Weapon Slot:" +WeaponSlot.toString() + "\n";
    }
    else slots += "Weapon Slot: Empty \n";
    if(ArmorSlot != null){
        slots += ArmorSlot.toString() + "\n";
    }
    else slots += "Armor Slot: Empty \n";
    switch(this.Type){
        
        case Warrior:
            ret = String.format("Name: %s, Class: Warrior, Level: %d \nHp:%d ,Mp:%d \nStrength: %d, Agility: %d, Dexterity:%d \nMoney: %d\n",getName(),getLevel(),getHp(),getMp(),getStr(),getAgi(),getDex(),getMoney());
            return ANSI_RED + ret + slots +ANSI_RESET;

        case Sorcerer:
            ret = String.format("Name: %s, Class: Sorcerer,Level: %d \nHp:%d, Mp:%d \nStrength: %d, Agility: %d, Dexterity:%d \nMoney: %d \n",getName(),getLevel(),getHp(),getMp(),getStr(),getAgi(),getDex(),getMoney());
            return ANSI_BLUE + ret +slots + ANSI_RESET;
        case Paladin:
            ret = String.format("Name: %s, Class: Paladin,Level: %d \nHp:%d, Mp%d \nStrength: %d, Agility: %d, Dexterity:%d \nMoney: %d \n",getName(),getLevel(),getHp(),getMp(),getStr(),getAgi(),getDex(),getMoney());
            return ANSI_YELLOW + ret + slots + ANSI_RESET;
            }
        return ret;
}
// Getters and Setters for all relevant Fields

public int getMp(){
    return CurrMp;
}

public void setMp(int mp){
    this.CurrMp =mp;
}

public int getStr(){
    return Str;
}

public void setStr(int s){
    this.Str = s;
}

public int getDex(){
    return Dex;
}

public void setDex(int d){
    this.Dex=d;
}

public int getAgi(){
    return Agi;
}

public void setAgi(int A){
    this.Agi =A;
}
public int getMoney(){
    return this.Money;
}
public void setMoney(int m){
    this.Money = m;
}
public HeroType getHeroType(){
    return this.Type;
}
public Inventory getBag(){
    return this.bag;
}
public void display(){
    System.out.printf("Hero Name: %s \n Class: %s \n Level: %d \n Hp: %d \n Mp: %d \nStr: %d \n Dex: %d \n Age: %d \n Exp: %d / %d", getName(), getHeroType(), getLevel(), getHp(), CurrMp, Str, Dex, Agi, Exp, ExpCap);
    System.out.print(this.bag);
}
public int AttackDamage(){
    if (WeaponSlot!= null)
        return (int)Math.round((getStr() + WeaponSlot.getDamage()) *0.05);
    else{
        return (int) Math.round(getStr() *0.05);
    }

}
public int SpellDmg(Spell spell){
    int BaseDmg = spell.getDamage();
    return(int) Math.round(BaseDmg +(this.getDex()/10000)*BaseDmg);
}
public void incomingDamage(int attack){
    double dodgeroll= rd.nextDouble();
    // System.out.println(dodgeroll);
    // System.out.println(this.getAgi() * 0.02);
    if (dodgeroll < 1/(this.getAgi() *0.02)){
        System.out.printf("%s dodged the attack attack\n", this.getName());
    }
    else{
        int TrueDmg;
        if (ArmorSlot != null)
            TrueDmg = attack - ArmorSlot.getReduction();
        else TrueDmg = attack;
        if (TrueDmg>1){
            setHp( getHp() - TrueDmg);
            System.out.printf("%s has taken %d damange \n", getName(),TrueDmg );
       
        }
        else {
            setHp(getHp()-1);
            System.out.printf("%s has taken 1 damage", getName());
        }
        if (getHp() <= 0){
            setHp(0);
            super.setFainted(true);
            System.out.printf("%s has fainted \n", getName());
        }
    }
}

public void LevelUp(){
    setLevel(getLevel() + 1);
    setHp(getLevel() * 100);
    //handling level up based off the type of hero
    switch(getHeroType()){
        case Warrior:
            setMp((int) Math.round(getMp() + (getMp() * 0.1)));
            setStr((int) Math.round(1.1 * getStr()));
            setAgi((int) Math.round(1.1 * getAgi()));
            setDex((int) Math.round(1.05 * getDex()));
        case Paladin:
            setMp((int) Math.round(getMp() + (getMp() * 0.1)));
            setStr((int) Math.round(1.1 * getStr()));
            setAgi((int) Math.round(1.05 * getAgi()));
            setDex((int) Math.round(1.1 * getDex()));
        case Sorcerer:
            setMp((int) Math.round(getMp() + (getMp() * 0.1)));
            setStr((int) Math.round(1.05 * getStr()));
            setAgi((int) Math.round(1.1 * getAgi()));
            setDex((int) Math.round(1.1 * getDex()));
    }
    
}
public void WinFight(){
    if (getFainted()== true){
        setHp(50*getLevel());
        setFainted(false);
    }
    else{
        this.Exp +=2;
    setHp((int) Math.round(getHp() *1.05));
    this.CurrMp = (int) Math.round(this.CurrMp *1.05);
    setMoney(getMoney()+150);
    if(Exp >= 10*getLevel()){
        System.out.printf("%s leveled up!\n", this.getName());
        this.LevelUp();
        }
    }
}
public void usePotion(Potion pot){
    if (pot.getlevelReq()> this.getLevel()){
        System.out.println("Your level is too low to use this item.");
        return;
    }
    else{
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
public void OpenInventory(){
    boolean close = false;
    while(!close){
    //We create an interface for the menu so each category of item takes a page.
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
    if(choice ==5){
        return;
    }
    else if (choice ==1 ){
        Weapon eq = bag.openSubbag(bag.getWeapons());
        if (eq!= null){
            EquipWeapon(eq);
            close = true;
        }
        else
            continue;
    }
    else if (choice == 2){
        Armor ar = bag.openSubbag(bag.getArmors());
        if(ar != null){
            EquipArmor(ar);
            close = true;
        }
        else
            continue;
    }

    else if (choice ==3){
        Potion pot = bag.openSubbag(bag.getPotions());
        if (pot!= null){
            usePotion(pot);
            close =true;

        }
        else
            continue;
    }
    else{
        //Spell doesn't go off as it should be directly accessed in the spell menu in combat
        Spell sp = bag.openSubbag(bag.getSpells());
        if (sp!= null){
            close =true;
        }
        else
            continue;
    }
}
}

public void EquipWeapon(Weapon w){
    if(w.getlevelReq() <=this.getLevel()){
        if (WeaponSlot == null){
            WeaponSlot = w;
        }
        else{
            Weapon temp = WeaponSlot;
            WeaponSlot = w;
            bag.addWeapon(temp);
        }
    }
    else{
        System.out.println("Your level is too low to equip this weapon");
        bag.addWeapon(w);
    }
}
public void EquipArmor(Armor ar){
    if(ar.getlevelReq()<=this.getLevel()){
        if (ArmorSlot == null){
            ArmorSlot =ar;
        }
        else{
            Armor temp = ArmorSlot;
            ArmorSlot = ar;
            bag.addArmor(temp);
        }
    }
    else{
        System.out.println("Your level is too low to equip this armor");
        bag.addArmor(ar);
    }
}
public <T extends Item> void PurchaseItem(T item, ArrayList<T> subbag){
    int cost = item.getCost();
    if (cost> this.Money){
        System.out.println("You don't have enough money to purchase this item. \n");
        return;
    }
    else{
        setMoney(this.Money- cost);
        subbag.add(item);
        System.out.println("Purchase Successful!\n");
        return;
    }
}

public <T extends Item> void sellItem(T item){
    //handles
    int sellPrice = item.getCost()/2;
    setMoney(this.Money-sellPrice);
    System.out.printf("Sale successful,you now have %d monies\n", this.getMoney());
}
public void Lose(){
    this.setFainted(false);
    this.setHp(getLevel()*100/2);
    this.setMoney(this.getMoney()/2);
}
// All refactored into generic Method sellItem()

// public void SellWeapon(Weapon w){
//     int sellPrice = w.getCost()/2;
//     setMoney(this.Money-sellPrice);
//     System.out.printf("Sale successful,you now have %d monies\n",this.getMoney());
// }
// public void SellArmor(Armor ar){
//     int sellPrice = ar.getCost() / 2;
//     setMoney(this.Money - sellPrice);
//     System.out.printf("Sale successful,you now have %d monies\n", this.getMoney());
// }
// public void SellPotion(Potion pot){
//     int sellPrice = pot.getCost() / 2;
//     setMoney(this.Money - sellPrice);
//     System.out.printf("Sale successful,you now have %d monies\n", this.getMoney());
// }
// public void SellSpell(Spell sp ){
//     int sellPrice = sp.getCost() / 2;
//     setMoney(this.Money - sellPrice);
//     System.out.printf("Sale successful,you now have %d monies\n", this.getMoney());
// }

//Refactored into purchaseItem

// public void PurchaseArmor(Armor ar) {
//     int cost = ar.getCost();
//     if (cost > this.Money) {
//         System.out.println("You don't have enough money to purchase this item.");
//         return;
//     } else {
//         setMoney(this.Money - cost);
//         bag.addArmor(ar);
//         System.out.println("Purchase Successful!");
//         return;
//     }
// }

// public void PurchasePotion(Potion pot) {
//     int cost = pot.getCost();
//     if (cost > this.Money) {
//         System.out.println("You don't have enough money to purchase this item.");
//         return;
//     } else {
//         setMoney(this.Money - cost);
//         bag.addPotion(pot);
//         return;
//     }

// }

// public void PurchaseSpell(Spell sp) {
//     int cost = sp.getCost();
//     if (cost > this.Money) {
//         System.out.println("You don't have enough money to purchase this item.");
//         return;
//     } else {
//         setMoney(this.Money - cost);
//         bag.addSpell(sp);
//         return;
//     }
// }
}