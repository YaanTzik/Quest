import java.util.ArrayList;
import java.util.Scanner;

public class HeroParty extends Party<Hero> {
    private Scanner sc;

    public HeroParty(int i) {
        super(i);
        sc = new Scanner(System.in);
    }

    public int getMaxLevel() {
        int max = 1;
        for (int i = 0; i < this.getPartySize(); i++) {
            if (getHero(i).getLevel() > max) {
                max = getHero(i).getLevel();
            }
        }
        return max;
    }

    public String toString() {
        String s = "";
        for (int i = 0; i < getPartySize(); i++) {
            // System.out.println(i);
            s = s + getParty().get(i).toString() + "\n";
        }
        return s;
    }

    public void HeroSelect() {

        // Warriors
        Hero Warr1 = new Warrior("Gaerdal Ironhand", 100, 700, 500, 600, 1354, 7);
        Hero Warr2 = new Warrior("Sehanin Monnbow", 600, 700, 800, 500, 2500, 8);
        Hero Warr3 = new Warrior("Muamman Duathall", 300, 900, 500, 750, 2546, 6);
        Hero Warr4 = new Warrior("Flandal Stellskin", 200, 750, 650, 700, 2500, 7);

        // Sorcerors
        Hero Src1 = new Sorcerer("Garl Glittergold", 700, 550, 600, 500, 2500, 7);
        Hero Src2 = new Sorcerer("Rillifane Rallathil", 1300, 750, 450, 500, 2500, 9);
        Hero Src3 = new Sorcerer("Segojan Earthcaller", 900, 800, 500, 650, 2500, 5);
        Hero Src4 = new Sorcerer("Skoraeus Stonebones", 800, 85, 600, 450, 2500, 6);

        // Paladin
        Hero Pally1 = new Paladin("Solonor Thelandira", 300, 750, 650, 700, 2500, 7);
        Hero Pally2 = new Paladin("Sehanin Moonbow", 300, 750, 700, 700, 2500, 7);
        Hero Pally3 = new Paladin("Skoraeus Stonebones", 250, 650, 600, 350, 2500, 4);
        Hero Pally4 = new Paladin("Garl Glittergold", 100, 600, 500, 400, 2500, 5);

        ArrayList<Hero> HeroList = new ArrayList<Hero>();
        HeroList.add(Warr1);
        HeroList.add(Warr2);
        HeroList.add(Warr3);
        HeroList.add(Warr4);

        HeroList.add(Src1);
        HeroList.add(Src2);
        HeroList.add(Src3);
        HeroList.add(Src4);

        HeroList.add(Pally1);
        HeroList.add(Pally2);
        HeroList.add(Pally3);
        HeroList.add(Pally4);
        int count = 0;
        while (count < getPartySize()) {
            for (int i = 0; i < HeroList.size(); i++) {
                System.out.println("Hero" + i + "\n" + HeroList.get(i).toString());
            }
            int choice;
            do {
                System.out.printf("Please choose a hero to add to your party(0 - %d) \n", HeroList.size() - 1);
                while (!sc.hasNextInt()) {
                    System.out.printf("Invalid input, please enter a number from 0-% \n", HeroList.size() - 1);
                    sc.next(); // this is important!
                }
                choice = sc.nextInt();
            } while (choice < 0 || choice >= HeroList.size());
            Hero chosen = HeroList.get(choice);
            chosen.setHeroNum(count);
            this.AddHero(chosen);
            HeroList.remove(choice);
            count++;
        }
    }

    public void Win() {
        System.out.println("Heroes Won!");
        for (int i = 0; i < getPartySize(); i++) {
            getHero(i).WinFight();
        }

    }

    public void Lose() {
        System.out.println("All Heroes have fainted, you have lost!");
        for (int i = 0; i < getPartySize(); i++) {
            getHero(i).Lose();
        }
    }
}