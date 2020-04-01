import java.util.ArrayList;
import java.util.Scanner;
public class Inventory{
    private ArrayList<Weapon> WeaponInventory;
    private ArrayList<Armor> ArmorInventory;
    private ArrayList<Potion> PotionInventory;
    private ArrayList<Spell> SpellInventory;
    private Scanner sc;

    public Inventory(){
        this.WeaponInventory = new ArrayList<Weapon>();
        this.ArmorInventory = new ArrayList<Armor>();
        this.PotionInventory = new ArrayList<Potion>();
        this.SpellInventory = new ArrayList<Spell>();
        sc = new Scanner(System.in);
    }
    // getters
    
    public ArrayList<Weapon> getWeapons(){
        return WeaponInventory;
    }
    public ArrayList<Armor> getArmors(){
        return ArmorInventory;
    }
    public ArrayList<Potion> getPotions(){
        return PotionInventory;
    }
    public ArrayList<Spell> getSpells(){
        return SpellInventory;
    }

    public <T extends Item> T openSubbag(ArrayList<T> subbag){
        // if specified bag is empty we do not open it and return null;
        if (subbag.size()== 0){
            System.out.println("Inventory Empty");
            return null;
        }

        int choice;
        do {
        for(int i = 0; i<subbag.size(); i++){
            System.out.println((i+1)+ " "+ subbag.get(i).toString());
        }
        System.out.printf("Please choose the item you would like to use/equip/sell (1-%d) \n", subbag.size());
        System.out.println(0 + " to close inventory");

        while (!sc.hasNextInt()) {
            System.out.printf("Invalid input, please enter a number from 1-%d \n", subbag.size());
            sc.next(); // this is important!
        }
        choice = sc.nextInt();
    } while (choice < 0 || choice > subbag.size());
    // returning our choice of item
    if (choice> 0){
        T ret = subbag.get(choice-1);
        subbag.remove(choice-1);
        return ret;
    }
    //return null if we want to navigate out of the menu
    else return null;
    
    }
    

    public void addWeapon(Weapon w){
        WeaponInventory.add( w);
    }
    public void addArmor(Armor a){
        ArmorInventory.add(a);
    }
    public void addSpell(Spell s){
        SpellInventory.add(s);
    }
    public void addPotion(Potion p){
        PotionInventory.add(p);
    }
    //refactored into opensubbag()
    // public Weapon openWeapons(){
    //     if (WeaponInventory.size()== 0){
    //         System.out.println("This bag is empty");
    //     }
    //     for(int i = 0;  i< WeaponInventory.size(); i++){
    //         System.out.println( i + WeaponInventory.get(i).toString());
    //     }
    //     System.out.println((WeaponInventory) + "to close inventory");
    //     int choice;
    //     do {
    //     System.out.println("Please choose the type of item you would like to use! (1-5)");
    //     while (!sc.hasNextInt()) {
    //         System.out.println("Invalid input, please enter a number from 1-5");
    //         sc.next(); // this is important!
    //     }
    //     choice = sc.nextInt();
    // } while (choice <= 0 || choice > WeaponInventory.size());
    // if (choice == WeaponInventory.size()){
    //     Weapon ret = WeaponInventory.get(choice);
    //     WeaponInventory.remove(choice);
    //     return ret;
    // }
    // else return null;
    
    // }

    // public Armor openArmor(){
    //     for (int i = 0; i < ArmorInventory.size(); i++) {
    //         System.out.println(i + ArmorInventory.get(i).toString());
    //     }
    //     System.out.println((ArmorInventory) + "to close inventory");
    //     int choice;
    //     do {
    //         System.out.println("Please choose the type of item you would like to use! (1-5)");
    //         while (!sc.hasNextInt()) {
    //             System.out.println("Invalid input, please enter a number from 1-5");
    //             sc.next(); // this is important!
    //         }
    //         choice = sc.nextInt();
    //     } while (choice <= 0 || choice > WeaponInventory.size());
    //     if(choice == ArmorInventory.size()){
    //         Armor ret = ArmorInventory.get(choice);
    //         ArmorInventory.remove(choice);
    //         return ret;
    //     }
    //     else return null;
        
    // }
    // public Potion openPotion(){
    //     for (int i = 0; i < PotionInventory.size(); i++) {
    //         System.out.println(i + PotionInventory.get(i).toString());
    //     }
    //     System.out.println((PotionInventory) + "to close inventory");
    //     int choice;
    //     do {
    //         System.out.println("Please choose the type of item you would like to use! (1-5)");
    //         while (!sc.hasNextInt()) {
    //             System.out.println("Invalid input, please enter a number from 1-5");
    //             sc.next(); // this is important!
    //         }
    //         choice = sc.nextInt();
    //     } while (choice <= 0 || choice > PotionInventory.size());
    //     if (choice <PotionInventory.size()) {
    //         Potion ret = PotionInventory.get(choice);
    //         PotionInventory.remove(choice);
    //         return ret;
    //     } else
    //         return null;
    // }
    // public Spell openSpell(){
    //     for (int i = 0; i < SpellInventory.size(); i++) {
    //         System.out.println(i + SpellInventory.get(i).toString());
    //     }
    //     System.out.println((SpellInventory) + "to close inventory");
    //     int choice;
    //     do {
    //         System.out.println("Please choose the type of item you would like to use! (1-5)");
    //         while (!sc.hasNextInt()) {
    //             System.out.println("Invalid input, please enter a number from 1-5");
    //             sc.next(); // this is important!
    //         }
    //         choice = sc.nextInt();
    //     } while (choice <= 0 || choice > SpellInventory.size());
    //     if (choice < SpellInventory.size()) {
    //         Spell ret = SpellInventory.get(choice);
    //         SpellInventory.remove(choice);
    //         return ret;
    //     } else
    //         return null;
    // }
}