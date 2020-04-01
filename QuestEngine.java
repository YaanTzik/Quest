import java.util.Scanner;
import javafx.util.Pair;
import java.util.Random;

public class QuestEngine{
    private Board World;
    private HeroParty party;
    private Scanner sc;
    private Market mkt;
    private Random rd;

    public QuestEngine(){
        this.World = new Board(8,8);
        this.mkt = new Market();
        this.rd = new Random(); 
        this.sc = new Scanner(System.in);
    }
    public void play(){
        int choice;
        do {
            System.out.println("How many Heros would you like in your party(1-3)");
            while (!sc.hasNextInt()) {
                System.out.println("Invalid input, please enter a number from 1-3");
                sc.next(); // this is important!
            }
            choice = sc.nextInt();
        } while (choice < 1 || choice > 3);
        this.party= new HeroParty(choice);
        party.HeroSelect();
        boolean play = true;
        while(play){
            World.Display();
            String move;
            do {
                System.out.println("wasd to move around the map \n e to display Hero Party and access inventories \n m to print the board\n q to quite the game");
                while (!sc.hasNextLine()) {
                    System.out.println("Invalid input, please enter a number from 1-3");
                    sc.next(); // this is important!
                }
                move = sc.next();
                // System.out.println(move);
                move.toLowerCase();
            } while (!move.equals("w") && !move.equals("a")&& !move.equals("s")&& !move.equals("d") && !move.equals("q") && !move.equals("i") && !move.equals("e") && !move.equals("m"));
            
            char c = move.charAt(0);
            if (move.equals("q")){
                System.out.println("Thanks for playing!");
                return;
            }
            else if (move.equals("m")){
                World.Display();
                continue;
            }
            else if (move.equals("e")){
                int MenuChoice;
                System.out.println(party.toString());
                do {
                    System.out.println("Which Hero would you like to inspect?");
                    while (!sc.hasNextLine()) {
                        System.out.println("Invalid input, please enter a number from 1-3, 0 to return to map");
                        sc.next(); // this is important!
                    }
                    MenuChoice = sc.nextInt();
                    } while ( MenuChoice<0 || MenuChoice > party.getPartySize());
                    System.out.println(MenuChoice);
                    if (MenuChoice == 0){
                        continue;
                    }
                    else{
                        party.getHero(MenuChoice-1).OpenInventory();
                    }
                    
            }
            else if (!World.IlegalMove(c)){
                continue;
            }
            else{
                // System.out.println("we made it here");
                Pair<Integer,Integer> coords = World.convertMove(c);
                Tile currTile = World.MoveParty(coords);
                switch (currTile){
                    case MARKET:
                        World.Display();
                        boolean menu =true;
                        String yn;
                        while(menu){
                            do {
                                System.out.println("Would you like to enter the market (y/n)");
                                while (!sc.hasNextLine()) {
                                    System.out.println("Invalid input, please enter y for yes or n for no");
                                    sc.next(); // this is important!
                                }
                                yn = sc.next().toLowerCase();
                            } while (!yn.equals("y") && !yn.equals("n"));
                            if (yn.equals("y")) {
                                int HeroChoice;
                                do {
                                    System.out.println(party.toString());
                                    System.out.printf("Which Hero would like to enter the market? (1-%d), 0 to exit \n",party.getPartySize());
                                    while (!sc.hasNextInt()) {
                                        System.out.printf("Invalid input, please enter a number from 1-%d \n", party.getPartySize());
                                        sc.next(); // this is important!
                                    }
                                    HeroChoice = sc.nextInt();
                                } while ( HeroChoice<0 || HeroChoice > party.getPartySize());
                                if (HeroChoice ==0){
                                    menu = false;
                                    continue;
                                }
                                else
                                mkt.DetermineMarketMode(party.getHero(HeroChoice-1));    
                            }
                            else{
                                menu = false;
                                continue;
                            }
                        }
                        break;
                        

                    case COMMON:
                        double fightroll = rd.nextDouble();
                        // System.out.println(fightroll);
                        if (fightroll>= 0.8){
                            Fight FightInstance = new Fight(party);
                            FightInstance.getMonsters().createParty();
                            FightInstance.run();
                        }
                        break;
                    case NONPLAYABLE:
                        break;
                
                }

            }

        }
    }
public static void main(String[] args){
    QuestEngine GameInstance = new QuestEngine();
    GameInstance.play();
}
}