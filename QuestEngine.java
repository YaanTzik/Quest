import java.util.Scanner;
import java.util.ArrayList;
import java.util.Random;

public class QuestEngine {
    private Board World;
    private HeroParty party;
    private MonsterParty Mparty;
    private Scanner sc;
    private Market mkt;
    private Random rd;
    private int roundCount;

    public QuestEngine() {
        this.World = new Board(8, 8);
        this.mkt = new Market();
        this.rd = new Random();
        this.sc = new Scanner(System.in);
        this.roundCount = 1;
    }

    public void play() {

        System.out.println(Board.ANSI_PURPLE + "Welcome to the Quest of Legends ");
        System.out.println("Please choose your 3 Champions: " + Board.ANSI_RESET);
        this.party = new HeroParty(3);
        party.HeroSelect();
        System.out.println(Board.ANSI_RED + "Monster generating" + Board.ANSI_RESET);
        this.Mparty = new MonsterParty(party.getMaxLevel(), party.getPartySize());
        Mparty.createParty();
        World.setHp(party);
        World.setMp(Mparty);

        System.out.println(Board.ANSI_RED + "Monster generating" + Board.ANSI_RESET);
        this.Mparty = new MonsterParty(party.getMaxLevel(), party.getPartySize());
        Mparty.createParty();
        System.out.println(Mparty);

        boolean play = true;


        while (play) {

            // World.Display();n
            // System.out.println(Mparty);
            System.out.println("Round: " + roundCount);
            if (roundCount % 8 == 0) {
                Mparty.AddMonsters(party.getMaxLevel());
            }
            // We iterate 3 times, 1 for each Hero.
            for (int turn = 0; turn < 3; turn++) {
                Hero h = party.getHero(turn);
                // if Hero has fainted we respawn him at the beginning of his turn.
                if (h.getFainted() == true) {
                    System.out.println("Resetting Hero Location");
                    h.respawn();
                    World.respawn(turn);
                }
                World.Display();
                String turnHero = party.getHero(turn).getName();
                System.out.println("It is Hero " + Board.ANSI_BLUE + turnHero + Board.ANSI_RESET + " Turn");
                Tile startTile = World.getTile(turn);
                // If we start the turn on a nexus, we can access the market to acquire Items.
                if (startTile == Tile.NEXUS) {
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
                            mkt.DetermineMarketMode(h);
                        } else {
                            menu = false;
                        }
                    }

                }
                if (World.hasMonsterAdjacent(h.getCoordinates())) {
                    ArrayList<Integer> adj = World.getAdjacentM(h.getCoordinates());
                    // boolean fight;
                    int fchoice = 0;

                    do {

                        System.out.println("Would you like to fight Monster?, 0 to not fight.");
                        for (int i = 0; i < adj.size(); i++) {
                            int mnum = adj.get(i);
                            System.out.println((i + 1) + Mparty.getHero(mnum).toString());
                            while (!sc.hasNextInt()) {
                                System.out.println(
                                        "Invalid input, please enter the corresponding integer to fight or 0 to not fight");
                                sc.next();
                            }
                            fchoice = sc.nextInt();

                        }

                    } while (fchoice < 0 || fchoice > adj.size());
                    if (fchoice != 0) {
                        // fight = true;
                        Fight afight = new Fight(h, Mparty.getHero(fchoice - 1));
                        afight.run();
                        if (h.getFainted()) {
                            World.Hdeath(turn);
                        } else {
                            Mparty.remove(fchoice - 1);
                        }
                        continue;
                    }

                }

                String move;

                do {
                    System.out.println("wasd to move around the map \n"
                            + " e to display Hero info and access inventories \n" + " b to return Hero Nexus \n"
                            + " t to teleport to other lane \n" + " m to print the board \n" + " q to quite the game");
                    while (!sc.hasNextLine()) {
                        System.out.println("Invalid input, please enter a number from 1-3");
                        sc.next(); // this is important!
                    }
                    move = sc.next();
                    // System.out.println(move);
                    move.toLowerCase();
                } while (!move.equals("w") && !move.equals("a") && !move.equals("s") && !move.equals("d")
                        && !move.equals("i") && !move.equals("e") && !move.equals("b") && !move.equals("m")
                        && !move.equals("q"));

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
                    Coordinates coords = World.convertMove(turn, c, 0);
                    // moving hero
                    Tile currTile = World.MoveHero(turn, coords);
                    h.TileBoost(currTile);
                    // If hero is in the same cell as a monster, we start a fight
                    int MonsterNum = World.hasMonster(coords);
                    if (MonsterNum != -1) {
                        Fight fight = new Fight(h, Mparty.getHero(MonsterNum));
                        fight.run();
                        if (h.getFainted()) {
                            World.Hdeath(turn);
                        }
                    }


                }
                // System.out.println("Troublshooting Map");
                // World.Display();

            }
            // Monsters Move.
            for (int monTurn = 0; monTurn < Mparty.getPartySize(); monTurn++) {

                if (!Mparty.getHero(monTurn).getFainted()) {

                    int adjacency = World.hasHeroAdjacent(monTurn);
                    if (adjacency != -1) {
                        Fight fight = new Fight(party.getHero(adjacency), Mparty.getHero(monTurn));
                        fight.run();
                        // If hero fainted in fight we remove him from map.
                        if (party.getHero(adjacency).getFainted()) {
                            World.Hdeath(adjacency);
                        } else {
                            Mparty.remove(monTurn);
                        }
                    } else {
                        World.moveMonster(monTurn);
                        boolean loss = World.checkLoss();
                        if (loss) {
                            System.out.println("Heroes lose, Thanks for playing!");
                            return;
                        }
                    }
                }

            }


            roundCount++;

        }

    }


    public static void main(String[] args) {
        QuestEngine GameInstance = new QuestEngine();
        GameInstance.play();
    }

}