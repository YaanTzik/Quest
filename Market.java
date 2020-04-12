import java.util.ArrayList;
import java.util.Scanner;

public class Market {
    // Weapons hard coded
    private Weapon Sword = new Weapon("Sword", 500, 1, 800, 1);
    private Weapon Bow = new Weapon("Bow", 300, 2, 500, 2);
    private Weapon Scythe = new Weapon("Scythe", 100, 6, 1100, 2);
    private Weapon Axe = new Weapon("Axe", 550, 5, 850, 1);
    private Weapon Shield = new Weapon("Shield", 400, 1, 100, 1);
    private Weapon Tswords = new Weapon("Tsword", 1400, 8, 1600, 2);
    private Weapon Dagger = new Weapon("Dagger", 200, 1, 250, 1);
    private Weapon Excalibur = new Weapon("Excalibur", 2000, 12, 2500, 2);
    // Armor
    private Armor PlatinumShield = new Armor("Platinum Shield", 150, 1, 200);
    private Armor BreastPlate = new Armor("Breastplate", 350, 3, 600);
    private Armor FullBodyArmor = new Armor("Full Body Armor", 1000, 8, 1100);
    private Armor WizardShield = new Armor("Wizard Shield", 1200, 10, 1500);
    private Armor SpeedBoots = new Armor("Speed Boots", 550, 4, 600);
    // Fire Spells
    private Spell FlameTornado = new Spell("Flame Tornado", 700, 4, 850, 300, SpellType.FIRE);
    private Spell BreathOfFire = new Spell("Breath of Fire", 350, 1, 450, 100, SpellType.FIRE);
    private Spell HeatWave = new Spell("Heat Wave", 450, 2, 600, 150, SpellType.FIRE);
    private Spell LavaCommet = new Spell("Lava Commet", 800, 7, 1000, 550, SpellType.FIRE);

    // Ice Spells
    private Spell SnowCannon = new Spell("Snow Cannon", 500, 2, 650, 250, SpellType.ICE);
    private Spell IceBlade = new Spell("Ice Blade", 250, 1, 450, 100, SpellType.ICE);
    private Spell FrostBlizzard = new Spell("Frost Blizzard", 750, 5, 850, 350, SpellType.ICE);
    private Spell ArcticStorm = new Spell("Arctic Storm", 700, 6, 800, 300, SpellType.ICE);

    // Lightning Spells
    private Spell LightningDagger = new Spell("Lightning Dagger", 400, 1, 500, 150, SpellType.LIGHTNING);
    private Spell ThunderBlast = new Spell("Thunder Blast", 750, 4, 950, 400, SpellType.LIGHTNING);
    private Spell ElectricArrows = new Spell("Electric Arrows", 550, 5, 650, 200, SpellType.LIGHTNING);
    private Spell SparkNeedles = new Spell("Spark Needles", 500, 2, 600, 200, SpellType.LIGHTNING);

    // Potions
    private Potion HealingPotion = new Potion("Healing Potion", 250, 1, PotionType.Hp, 100);
    private Potion ManaPotion = new Potion("Mana Potion", 50, 2, PotionType.Mp, 100);
    private Potion StrengthPotion = new Potion("Strength Potion", 200, 1, PotionType.Strength, 75);
    private Potion MagicPotion = new Potion("Strength Potion", 350, 2, PotionType.Dexterity, 100);
    private Potion LuckElixir = new Potion("Luck Elixir", 500, 4, PotionType.Agility, 65);
    private Potion MermaidTears = new Potion("Mermaid Tears", 850, 5, PotionType.All, 100);
    private Potion Ambrosia = new Potion("Ambrosia", 1000, 8, PotionType.All, 150);

    // Private Values
    private ArrayList<Weapon> Weapons;
    private ArrayList<Armor> Armors;
    private ArrayList<Spell> Spells;
    private ArrayList<Potion> Potions;

    private Scanner sc;

    public Market() {
        Weapons = new ArrayList<Weapon>();
        Weapons.add(Sword);
        Weapons.add(Bow);
        Weapons.add(Scythe);
        Weapons.add(Axe);
        Weapons.add(Shield);
        Weapons.add(Tswords);
        Weapons.add(Dagger);
        Weapons.add(Excalibur);

        Armors = new ArrayList<Armor>();
        Armors.add(PlatinumShield);
        Armors.add(BreastPlate);
        Armors.add(FullBodyArmor);
        Armors.add(WizardShield);
        Armors.add(SpeedBoots);

        Spells = new ArrayList<Spell>();
        Spells.add(FlameTornado);
        Spells.add(BreathOfFire);
        Spells.add(HeatWave);
        Spells.add(LavaCommet);
        Spells.add(SnowCannon);
        Spells.add(IceBlade);
        Spells.add(FrostBlizzard);
        Spells.add(ArcticStorm);
        Spells.add(LightningDagger);
        Spells.add(ThunderBlast);
        Spells.add(ElectricArrows);
        Spells.add(SparkNeedles);

        Potions = new ArrayList<Potion>();
        Potions.add(HealingPotion);
        Potions.add(StrengthPotion);
        Potions.add(MagicPotion);
        Potions.add(LuckElixir);
        Potions.add(MermaidTears);
        Potions.add(Ambrosia);
        Potions.add(ManaPotion);
        sc = new Scanner(System.in);
    }

    public void DetermineMarketMode(Hero h) {
        boolean close = false;
        while (!close) {
            System.out.println("Do you want to buy or sell?");
            System.out.println("1.Buy");
            System.out.println("2.Sell");
            System.out.println("3.Exit");
            int choice;
            do {
                System.out.println("Please choose if you want to buy or sell (1-3)");
                while (!sc.hasNextInt()) {
                    System.out.println("Invalid input, please enter a number from 1-3");
                    sc.next(); // this is important!
                }
                choice = sc.nextInt();
            } while (choice < 1 || choice > 3);
            if (choice == 1) {
                EnterMarket(h, "buy");
            } else if (choice == 2) {
                EnterMarket(h, "sell");
            } else {
                close = true;
            }

        }
        return;
    }

    public void EnterMarket(Hero h, String mode) {

        boolean close = false;
        while (!close) {
            // We create an interface for the menu so each category of item takes a page.
            System.out.println("What would you like to Purchase?");
            System.out.printf("current money %d\n", h.getMoney());
            System.out.println("1. Weapon ");
            System.out.println("2. Armor");
            System.out.println("3. Potion");
            System.out.println("4. Spell");
            System.out.println("enter 5 to close inventory");
            int choice;
            do {
                System.out.println("Please choose the type of item you would like to Purchase! (1-5)\n");
                while (!sc.hasNextInt()) {
                    System.out.println("Invalid input, please enter a number from 1-5");
                    sc.next(); // this is important!
                }
                choice = sc.nextInt();
            } while (choice <= 0 || choice > 5);
            if (choice == 5) {
                close = true;
                return;
            } else if (choice == 1) {
                Weapon eq;
                if (mode.equals("buy")) {
                    eq = ListForPurchase(Weapons);
                    if (eq != null)
                        h.PurchaseItem(eq, h.getBag().getWeapons());

                } else if (mode.equals("sell")) {
                    eq = h.getBag().openSubbag(h.getBag().getWeapons());
                    if (eq != null)
                        h.sellItem(eq);
                } else
                    continue;
            } else if (choice == 2) {
                Armor eq;
                if (mode.equals("buy")) {
                    eq = ListForPurchase(Armors);
                    if (eq != null)
                        h.PurchaseItem(eq, h.getBag().getArmors());
                } else if (mode.equals("sell")) {
                    eq = h.getBag().openSubbag(h.getBag().getArmors());
                    if (eq != null)
                        h.sellItem(eq);
                } else
                    continue;
            }

            else if (choice == 3) {
                Potion pot;
                if (mode.equals("buy")) {
                    pot = ListForPurchase(Potions);
                    if (pot != null)
                        h.PurchaseItem(pot, h.getBag().getPotions());
                }

                else if (mode.equals("sell")) {
                    pot = h.getBag().openSubbag(h.getBag().getPotions());
                    if (pot != null)
                        h.sellItem(pot);
                }

                else
                    continue;
            } else {
                // Spell doesn't go off as it should be directly accessed in the spell menu in
                // combat
                Spell sp;
                if (mode.equals("buy")) {
                    sp = ListForPurchase(Spells);
                    if (sp != null)
                        h.PurchaseItem(sp, h.getBag().getSpells());
                } else if (mode.equals("sell")) {
                    sp = h.getBag().openSubbag(h.getBag().getSpells());
                    if (sp != null)
                        h.sellItem(sp);
                }

                else
                    continue;
            }
        }
    }

    public <T extends Item> T ListForPurchase(ArrayList<T> window) {
        int choice;
        do {
            for (int i = 0; i < window.size(); i++) {
                System.out.println((i + 1) + " " + window.get(i).toString());
            }
            System.out.printf("Please choose the item you would like to purchase! (0- %d) \n enter 0 to return \n",
                    window.size());
            while (!sc.hasNextInt()) {
                System.out.printf("Invalid input, please enter a number from 0-%d \n", window.size());
                sc.next(); // this is important!
            }
            choice = sc.nextInt();
        } while (choice < 0 || choice > window.size());
        if (choice == 0) {
            return null;
        } else
            return window.get(choice - 1);
    }
}