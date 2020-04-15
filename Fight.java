import java.util.Scanner;

public class Fight {
    private HeroParty heroes;
    private MonsterParty monsters;
    private Scanner sc;

    public Fight(HeroParty h) {
        this.heroes = h;
        sc = new Scanner(System.in);
        System.out.println("Fight initializing");
        monsters = new MonsterParty(heroes.getMaxLevel(), heroes.getPartySize());

    }

    public Fight(Hero h, Monster m) {
        HeroParty Hp = new HeroParty(1);
        Hp.AddHero(h);
        MonsterParty Mp = new MonsterParty(1, h.getLevel());
        Mp.AddHero(m);
        sc = new Scanner(System.in);
        this.heroes = Hp;
        this.monsters = Mp;

    }

    public MonsterParty getMonsters() {
        return monsters;
    }

    public void run() {
        System.out.println("BATTLE ENCOUNTERED");
        System.out.println(monsters.toString());
        while (true) {

            // Rotation of heroes
            for (int i = 0; i < heroes.getPartySize(); i++) {
                // if hero has fainted then he is unable to fight
                if (heroes.getHero(i).getFainted()) {
                    System.out.printf("%s has fainted and is unable to fight\n", heroes.getHero(i).getName());
                } else {
                    boolean turn = true;
                    int Herochoice;
                    // We can choose what the hero will do
                    while (turn) {
                        do {
                            System.out.printf("What would %s like to do ?\n", heroes.getHero(i).getName());
                            System.out.println(heroes.getHero(i).toString());
                            System.out.println("1. Attack \n2. Spell \n3. Item");
                            System.out.println("enter a number 1-3");
                            while (!sc.hasNextInt()) {
                                System.out.println("Invalid input, please enter a number from 1-3 \n");
                                sc.next(); // this is important!
                            }
                            Herochoice = sc.nextInt();
                        } while (Herochoice < 1 || Herochoice > 3);
                        if (Herochoice == 3) {
                            heroes.getHero(i).OpenInventory();
                            turn = false;
                        } else {
                            // Targetting System for picking which Monster to attack
                            int HeroTarget;
                            do {
                                System.out.println(monsters);
                                System.out.printf("Which Monster do you want to target?\n");
                                System.out.printf("enter 1-%d \n", monsters.getPartySize());

                                while (!sc.hasNextInt()) {
                                    System.out.printf("Invalid input, please enter a number from 1 -%d\n",
                                            monsters.getPartySize());
                                    sc.next(); // this is important!
                                }
                                HeroTarget = sc.nextInt();
                            } while ((HeroTarget < 1 || HeroTarget > monsters.getPartySize()));
                            if (monsters.getHero(HeroTarget - 1).getFainted())
                                HeroTarget = monsters.target() + 1;
                            if (Herochoice == 1) {
                                monsters.getHero(HeroTarget - 1).receiveAttack(heroes.getHero(i).AttackDamage());
                            } else {
                                Hero hero = heroes.getHero(i);
                                Spell spell = hero.getBag().openSubbag(hero.getBag().getSpells());
                                if (spell != null) {
                                    monsters.getHero(HeroTarget - 1).receiveSpell(spell, hero);
                                } else {
                                    System.out.println("Spell Inventory Empty, Will jus use Raw attack");
                                    monsters.getHero(HeroTarget - 1).receiveAttack(heroes.getHero(i).AttackDamage());
                                }

                            }
                            turn = false;
                        }
                    }
                }
            }
            if (monsters.AllFaint()) {
                heroes.Win();
                System.out.println(heroes);
                return;
            }
            for (int j = 0; j < monsters.getPartySize(); j++) {
                Monster mons = monsters.getHero(j);
                int target = heroes.target();
                heroes.getHero(target).incomingDamage(mons.getDamage());
            }
            if (heroes.AllFaint()) {
                return;
            }
        }
    }
}