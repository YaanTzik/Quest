import java.util.Scanner;
import javafx.util.Pair;
import java.util.Random;

public class QuestEngine {
    private Board World;
    private HeroParty party;
    private MonsterParty Mparty;
    private Scanner sc;
    private Market mkt;
    private Random rd;
    private int turnCount;

    public QuestEngine() {
        this.World = new Board(8, 8);
        this.mkt = new Market();
        this.rd = new Random();
        this.sc = new Scanner(System.in);
        this.turnCount = 0;
    }

    public void play() {

        System.out.println(Board.ANSI_PURPLE + "Welcome to the Quest of Legends ");
        System.out.println("Please choose your 3 Champions: " + Board.ANSI_RESET);
        this.party = new HeroParty(3);
        party.HeroSelect();

        System.out.println(Board.ANSI_RED + "Monster generating" + Board.ANSI_RESET);
        this.Mparty = new MonsterParty(party.getMaxLevel(), party.getPartySize());
        Mparty.createParty();
        System.out.println(Mparty);

        boolean play = true;


        while (play) {

            World.Display();

            int turn = turnCount % 3;
//            System.out.println(turn);
//            System.out.println(turnCount);
            String turnHero = party.getHero(turn).getName();
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
                    System.out.println("Invalid input, please enter a vaild move");
                    sc.next(); // this is important!
                }
                move = sc.next();
                // System.out.println(move);
                move.toLowerCase();
            } while (!move.equals("w") && !move.equals("a")
                    && !move.equals("s") && !move.equals("d")
                    && !move.equals("i") && !move.equals("e")
                    && !move.equals("b") && !move.equals("t")
                    && !move.equals("m") && !move.equals("q"));

            char c = move.charAt(0);
            if (move.equals("q")) {
                System.out.println("Thanks for playing!");
                return;
            } else if (move.equals("m")) {
                World.Display();
                continue;
            } else if (move.equals("e")) {
                party.getHero(turn).OpenInventory();
                continue;
            } else if (move.equals("b")) {
                World.convertMove(turn, c);
            } else if (move.equals("t")) {
//                System.out.println(turn);
                int helper;
                do {
                    System.out.println("Which Hero would you like to teleport to?\n" +
                    " 1-3: select Hero \n" +
                    " 0:   cancle \n" +
                    " 4:   teleport back its own line");
//                    System.out.println(party);
                    while (!sc.hasNextLine()) {
                        System.out.println("Invalid input, please enter a number from 1-3, 0 to cancel, 4 to teleport back its own line");
                        sc.next(); // this is important!
                    }
                    helper = sc.nextInt()-1;
                } while (helper < 0 || helper > party.getPartySize() || helper == turn);
//                System.out.println(helper);
                if (helper == -1) {
                    continue;
                } else if (helper == 3) {
                    if (!World.backTele(turn)) {
                        continue;
                    }
                } else {
                    int tileselect;
                    World.printTele(helper);
                    do {
                        System.out.println("Which tile you want to telport to?");
                        while (!sc.hasNextLine()) {
                            System.out.println("Invalid input, Please enter a number to select, 0 cancel");
                            sc.next(); // this is important!
                        }
                        tileselect = sc.nextInt()-1;
                    } while (tileselect < -1 || tileselect > World.getTelLocation(helper).size());
                    if (tileselect == -1) {
                        continue;
                    } else {
                        World.finishTele(turn, helper, tileselect);
                    }
                }

            } else if (!World.IllegalMove(c, turn)) {
                continue;
            } else {
                // System.out.println("we made it here");
                Coordinates coords = World.convertMove(turn, c);
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
                    case BUSH:
                        World.Display();
                        System.out.println("Hero " + Board.ANSI_BLUE + turnHero + Board.ANSI_RESET + " in the bush");

                        int turn_Dex = party.getHero(turn).getDex();
                        party.getHero(turn).setDex((int) Math.round(turn_Dex * 1.1));
                        System.out.println(party.getHero(turn));
                        break;

                    case KOULOU:
                        World.Display();
                        System.out.println("Hero " + Board.ANSI_BLUE + turnHero + Board.ANSI_RESET + " in the Koulou");

                        int turn_Str = party.getHero(turn).getStr();
                        party.getHero(turn).setStr((int) Math.round(turn_Str * 1.1));
                        System.out.println(party.getHero(turn));
                        break;

                    case CAVE:
                        World.Display();
                        System.out.println("Hero " + Board.ANSI_BLUE + turnHero + Board.ANSI_RESET + " in the Koulou");

                        int turn_Agi = party.getHero(turn).getAgi();
                        party.getHero(turn).setStr((int) Math.round(turn_Agi * 1.1));
                        System.out.println(party.getHero(turn));
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
            turnCount++;
//            }
        }
    }




public static void main(String[] args){
    QuestEngine GameInstance = new QuestEngine();
    GameInstance.play();
}
}