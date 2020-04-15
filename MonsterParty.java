import java.util.ArrayList;
import java.util.Random;

public class MonsterParty extends Party<Monster> {
    private int maxLevel;
    private Random rd;
    private int counter;
    private Coordinates M1;
    private Coordinates M2;
    private Coordinates M3;

    public MonsterParty(int maxLevel, int partysize) {
        super(partysize);
        System.out.println("Creating party");
        this.maxLevel = maxLevel;
        this.rd = new Random();
        counter = 0;
        M1 = new Coordinates(0, 0);
        M2 = new Coordinates(0, 3);
        M3 = new Coordinates(0, 6);

    }

    public void setMaxLevel(int i) {
        maxLevel = i;

    }

    public String toString() {
        String s = "";
        for (int i = 0; i < getPartySize(); i++) {
            s += getHero(i).toString();
        }
        return s;
    }

    // function to create a randomized team of monsters
    public void createParty() {
        System.out.println("Creating Monsters");
        Monster D1 = new Dragon("Desghidorrah", 3, 50, 70, 35);
        // System.out.println("First Dragon Created");
        Monster D2 = new Dragon("Chrysophylax", 2, 25, 100, 20);
        Monster D3 = new Dragon("Bunsen Burner", 4, 70, 120, 45);
        Monster D4 = new Dragon("The Scaleless", 7, 100, 80, 75);
        Monster D5 = new Dragon("Kas-Ethelinh", 5, 100, 80, 60);
        Monster D6 = new Dragon("Alexstraszan", 10, 200, 450, 55);
        Monster D7 = new Dragon("D-Maleficent", 9, 200, 150, 85);
        Monster D8 = new Dragon("The Weather Be", 8, 400, 450, 80);
        Monster D9 = new Dragon("WINGED DRAGON OF RA", 15, 5000, 5000, 50);
        Monster D10 = new Dragon("Dragon Hatchling", 1, 50, 50, 20);
        // System.out.println("All Dragons Created");

        Monster Ex1 = new ExoSkeleton("Cyrollalee", 7, 350, 400, 75);
        Monster Ex2 = new ExoSkeleton("Brandobaris", 3, 175, 225, 30);
        Monster Ex3 = new ExoSkeleton("Skeleton", 1, 75, 125, 15);
        Monster Ex4 = new ExoSkeleton("Wicked Witch", 2, 125, 175, 25);
        Monster Ex5 = new ExoSkeleton("Aasterinian", 4, 200, 250, 45);
        Monster Ex6 = new ExoSkeleton("Chronepsish", 6, 325, 375, 60);
        Monster Ex7 = new ExoSkeleton("Merrshaullk", 10, 500, 450, 55);
        Monster Ex8 = new ExoSkeleton("St-Yeenoghu", 9, 475, 425, 90);
        Monster Ex9 = new ExoSkeleton("OBELISK THE TORMENTOR", 15, 6000, 4000, 50);

        Monster S1 = new Spirit("Andrealphus", 2, 300, 250, 40);
        Monster S2 = new Spirit("Aim-Haborum", 1, 225, 100, 35);
        Monster S3 = new Spirit("Andromalius", 3, 225, 225, 25);
        Monster S4 = new Spirit("Fallen Angel", 5, 400, 350, 50);
        Monster S5 = new Spirit("Ereshkigall", 6, 475, 225, 35);
        Monster S6 = new Spirit("Jormunngad", 8, 300, 450, 20);
        Monster S7 = new Spirit("Rakkshasass", 9, 275, 300, 35);
        Monster S8 = new Spirit("Tatlcuhtli", 10, 300, 200, 50);
        Monster S9 = new Spirit("EXODIA", 20, 8000, 6000, 50);

        ArrayList<Monster> Monsters = new ArrayList<Monster>();
        Monsters.add(D1);
        Monsters.add(D2);
        Monsters.add(D3);
        Monsters.add(D4);
        Monsters.add(D5);
        Monsters.add(D6);
        Monsters.add(D7);
        Monsters.add(D8);
        Monsters.add(D9);
        Monsters.add(D10);

        Monsters.add(Ex1);
        Monsters.add(Ex2);
        Monsters.add(Ex3);
        Monsters.add(Ex4);
        Monsters.add(Ex5);
        Monsters.add(Ex6);
        Monsters.add(Ex7);
        Monsters.add(Ex8);
        Monsters.add(Ex9);

        Monsters.add(S1);
        Monsters.add(S2);
        Monsters.add(S3);
        Monsters.add(S4);
        Monsters.add(S5);
        Monsters.add(S6);
        Monsters.add(S7);
        Monsters.add(S8);
        Monsters.add(S9);

        ArrayList<Monster> MonsterPool = new ArrayList<Monster>();
        System.out.println("Monsters Created, Creating a party now");
        for (int i = 0; i < Monsters.size(); i++) {
            // Narrowing down the monsters we can use in a fight
            if (Monsters.get(i).getLevel() <= maxLevel) {
                MonsterPool.add(Monsters.get(i));
            }
        }
        int k = 0;
        // We fill the party with the appropriate monsters
        while (k < getPartySize()) {
            for (int j = 0; j < MonsterPool.size(); j++) {
                int roll = rd.nextInt(MonsterPool.size());
                Monster choice = MonsterPool.get(roll).clone();
                choice.setMonsterNum(k);
                AddHero(choice);

            }
            k++;
        }

    }

    public void addMonsters() {
        System.out.println("Creating Monsters");
        Monster D1 = new Dragon("Desghidorrah", 3, 50, 70, 35);
        // System.out.println("First Dragon Created");
        Monster D2 = new Dragon("Chrysophylax", 2, 25, 100, 20);
        Monster D3 = new Dragon("Bunsen Burner", 4, 70, 120, 45);
        Monster D4 = new Dragon("The Scaleless", 7, 100, 80, 75);
        Monster D5 = new Dragon("Kas-Ethelinh", 5, 100, 80, 60);
        Monster D6 = new Dragon("Alexstraszan", 10, 200, 450, 55);
        Monster D7 = new Dragon("D-Maleficent", 9, 200, 150, 85);
        Monster D8 = new Dragon("The Weather Be", 8, 400, 450, 80);
        Monster D9 = new Dragon("WINGED DRAGON OF RA", 15, 5000, 5000, 50);
        Monster D10 = new Dragon("Dragon Hatchling", 1, 50, 50, 20);
        // System.out.println("All Dragons Created");

        Monster Ex1 = new ExoSkeleton("Cyrollalee", 7, 350, 400, 75);
        Monster Ex2 = new ExoSkeleton("Brandobaris", 3, 175, 225, 30);
        Monster Ex3 = new ExoSkeleton("Skeleton", 1, 75, 125, 15);
        Monster Ex4 = new ExoSkeleton("Wicked Witch", 2, 125, 175, 25);
        Monster Ex5 = new ExoSkeleton("Aasterinian", 4, 200, 250, 45);
        Monster Ex6 = new ExoSkeleton("Chronepsish", 6, 325, 375, 60);
        Monster Ex7 = new ExoSkeleton("Merrshaullk", 10, 500, 450, 55);
        Monster Ex8 = new ExoSkeleton("St-Yeenoghu", 9, 475, 425, 90);
        Monster Ex9 = new ExoSkeleton("OBELISK THE TORMENTOR", 15, 6000, 4000, 50);

        Monster S1 = new Spirit("Andrealphus", 2, 300, 250, 40);
        Monster S2 = new Spirit("Aim-Haborum", 1, 225, 100, 35);
        Monster S3 = new Spirit("Andromalius", 3, 225, 225, 25);
        Monster S4 = new Spirit("Fallen Angel", 5, 400, 350, 50);
        Monster S5 = new Spirit("Ereshkigall", 6, 475, 225, 35);
        Monster S6 = new Spirit("Jormunngad", 8, 300, 450, 20);
        Monster S7 = new Spirit("Rakkshasass", 9, 275, 300, 35);
        Monster S8 = new Spirit("Tatlcuhtli", 10, 300, 200, 50);
        Monster S9 = new Spirit("EXODIA", 20, 8000, 6000, 50);

        ArrayList<Monster> Monsters = new ArrayList<Monster>();
        Monsters.add(D1);
        Monsters.add(D2);
        Monsters.add(D3);
        Monsters.add(D4);
        Monsters.add(D5);
        Monsters.add(D6);
        Monsters.add(D7);
        Monsters.add(D8);
        Monsters.add(D9);
        Monsters.add(D10);

        Monsters.add(Ex1);
        Monsters.add(Ex2);
        Monsters.add(Ex3);
        Monsters.add(Ex4);
        Monsters.add(Ex5);
        Monsters.add(Ex6);
        Monsters.add(Ex7);
        Monsters.add(Ex8);
        Monsters.add(Ex9);

        Monsters.add(S1);
        Monsters.add(S2);
        Monsters.add(S3);
        Monsters.add(S4);
        Monsters.add(S5);
        Monsters.add(S6);
        Monsters.add(S7);
        Monsters.add(S8);
        Monsters.add(S9);

        ArrayList<Monster> MonsterPool = new ArrayList<Monster>();
        System.out.println("Monsters Created, Creating a party now");
        for (int i = 0; i < Monsters.size(); i++) {
            // Narrowing down the monsters we can use in a fight
            if (Monsters.get(i).getLevel() <= maxLevel) {
                MonsterPool.add(Monsters.get(i));
            }
        }
        for (int k = 0; k < 3; k++) {
            int roll = rd.nextInt(MonsterPool.size());
            Monster choice = MonsterPool.get(roll).clone();
            // choice.setMonsterNum(k);
            if (k == 0) {
                choice.setCoordinates(new Coordinates(M1.getRow(), M1.getCol()));
            } else if (k == 1) {
                choice.setCoordinates(new Coordinates(M2.getRow(), M2.getCol()));
            } else {
                choice.setCoordinates(new Coordinates(M3.getRow(), M3.getCol()));
            }
            System.out.println(choice.toString());
            AddHero(choice);
        }
        System.out.println("MONSTERS CREATED");

    }

    public void AddMonsters(int MaxLevel) {
        setMaxLevel(MaxLevel);
        setPartySize(getPartySize() + 3);
        addMonsters();

    }

}