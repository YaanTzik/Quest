import java.util.Scanner;
import javafx.util.Pair;
import java.util.Random;

public class QuestEngine{
    private Board World;
    private HeroParty party;
    private MonsterParty Mparty;
    private Scanner sc;
    private Market mkt;
    private Random rd;
    private int turnCount;

    public QuestEngine(){
        this.World = new Board(8,8);
        this.mkt = new Market();
        this.rd = new Random(); 
        this.sc = new Scanner(System.in);
        this.turnCount = 0;
    }
    public void play(){

        System.out.println( Board.ANSI_PURPLE + "Welcome to the Quest of Legends ");
        System.out.println("Please choose your 3 Champions: " + Board.ANSI_RESET);
        this.party= new HeroParty(3);
        party.HeroSelect();

        boolean play = true;

        while(play){

            System.out.println( Board.ANSI_RED + "Monster generating" + Board.ANSI_RESET);
            this.Mparty = new MonsterParty(party.getMaxLevel(),party.getPartySize());
            Mparty.createParty();
            System.out.println(Mparty);

            World.Display();

            int turn = turnCount%3;
            System.out.println(turn);
            System.out.println(turnCount);
            String turnHero;
            turnHero = party.getHero(turn).getName();
            System.out.println("It is Hero " + Board.ANSI_BLUE + turnHero + Board.ANSI_RESET + " Turn");

            String move;

            do {
                System.out.println("wasd to move around the map \n" +
                        " e to display Hero info and access inventories \n" +
                        " b to return Hero Nexus \n" +
                        " t to teleport to other lane \n" +
                        " m to print the board \n" +
                        " q to quite the game");
                while (!sc.hasNextLine()) {
                    System.out.println("Invalid input, please enter a number from 1-3");
                    sc.next(); // this is important!
                }
                move = sc.next();
                    // System.out.println(move);
                move.toLowerCase();
            } while (!move.equals("w") && !move.equals("a") && !move.equals("s") && !move.equals("d") && !move.equals("i") && !move.equals("e") && !move.equals("b") && !move.equals("m") && !move.equals("q"));

            char c = move.charAt(0);
            if (move.equals("q")) {
                System.out.println("Thanks for playing!");
                return;
            } else if (move.equals("m")) {
                World.Display();
                continue;
            } else if (move.equals("e")) {

                party.getHero(turn).OpenInventory();

            } else if (move.equals("b")) {
                World.convertMove(turn, c, 0);
            } else if (move.equals("t")) {
                int helper;
                do {
                    System.out.println("Which Hero would you like to teleport to?");
//                    System.out.println(party);
                    while (!sc.hasNextLine()) {
                        System.out.println("Invalid input, please enter a number from 1-3, 0 to cancel");
                        sc.next(); // this is important!
                    }
                    helper = sc.nextInt();
                    } while (helper < 0 || helper > party.getPartySize() || helper == turn+1);
                    System.out.println(helper);
                    if (helper == 0) {
                        continue;
                    }
                    else {
                    World.convertMove(turn, c, helper);
                    }

            } else if (!World.IllegalMove(c, turn)) {
                continue;
            } else {
                    // System.out.println("we made it here");
                Coordinates coords = World.convertMove(turn, c,0);
                Tile currTile = World.MoveHero(turn, coords);
                switch (currTile) {
                    case NEXUS:
                        World.Display();
                        boolean menu = true;
                        String yn;
                        while (menu) {
                            do {
                                System.out.println("Would you like to enter the market (y/n)");
                                while (!sc.hasNextLine()) {
                                    System.out.println("Invalid input, please enter y for yes or n for no");
                                    sc.next(); // this is important!
                                }
                                yn = sc.next().toLowerCase();
                            } while (!yn.equals("y") && !yn.equals("n"));
                            if (yn.equals("y")) {
                                mkt.DetermineMarketMode(party.getHero(turn - 1));
                            } else {
                                menu = false;
                                continue;
                            }
                        }
                        break;
                    case COMMON:
//                        double fightroll = rd.nextDouble();
//                            // System.out.println(fightroll);
//                        if (fightroll >= 0.8) {
//                            Fight FightInstance = new Fight(party);
//                            FightInstance.getMonsters().createParty();
//                            FightInstance.run();
//                        }


                        break;
                    case NONPLAYABLE:
                        break;

                }
            }
            turnCount ++;
//            }
        }
    }

public static void main(String[] args){
    QuestEngine GameInstance = new QuestEngine();
    GameInstance.play();
}
}