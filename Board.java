import java.util.Random;
import javafx.util.Pair;

import javax.management.ListenerNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class Board implements BoardInterface {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";
    public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
    public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
    public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
    public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
    public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
    public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
    public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
    public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";

    private int Height;
    private int Width;
    private Tile[][] PlayArea;
    // We use Key Value Pairs to keep track of each moving entity on the board.
    private ArrayList<Coordinates> HeroLocations;
    private ArrayList<Coordinates> MonsterLocations;
    private ArrayList<Pair<Integer, Integer>> TelLocations;

    // Dead Columns
    private int dead1;
    private int dead2;

    // Line Range
    private ArrayList<Pair<Integer, Integer>> LineRange;

    private Random rd;

    // private HeroParty heroParty;
    // private MonsterParty monsterParty;

    public Board(int r, int c) {
        this.Height = r;
        this.Width = c;
        PlayArea = new Tile[r][c];
        // We portion the unplayable tiles at the start for quick reference and so it is
        // general enough to play with any number of columns
        // Ideally we want all lanes to be equal but if the requested width is not
        // congruent with this desire the we handle the input by
        // making the bottom lane bigger than the rest if 1 lane is bigger than the
        // other 2 or make the bottom lane smaller by 1.
        // Inherently this shouldn't make a large difference. I haven't played Lol much
        // but in dota2 top bottom and middle lanes all have
        // differing sizes and I feel this makes the game more dynamic
        int laneWidth;

        if ((c - 2) % 3 == 2) {
            laneWidth = (c - 4) / 2;
            dead1 = laneWidth;
            dead2 = laneWidth * 2 + 1;
        } else {
            // We process lane logic similiarly when c-2%3 == 1 and 0
            laneWidth = (c - 2) / 3;
            dead1 = laneWidth;
            dead2 = laneWidth * 2 + 1;
        }

        this.rd = new Random();

        // Initializing the starting positions of Heros and Monsters.
        // Monsters start at top left of the lane they are in.
        Coordinates M1 = new Coordinates(0, 0);
        Coordinates M2 = new Coordinates(0, dead1 + 1);
        Coordinates M3 = new Coordinates(0, dead2 + 1);
        MonsterLocations = new ArrayList<Coordinates>();
        MonsterLocations.add(M1);
        MonsterLocations.add(M2);
        MonsterLocations.add(M3);

        // Heros will start at the bottom left of the lane they are in.
        Coordinates H1 = new Coordinates(r - 1, 0);
        Coordinates H2 = new Coordinates(r - 1, dead1 + 1);
        Coordinates H3 = new Coordinates(r - 1, dead2 + 1);
        HeroLocations = new ArrayList<Coordinates>();
        HeroLocations.add(H1);
        HeroLocations.add(H2);
        HeroLocations.add(H3);

        //
        Pair<Integer, Integer> L1 = new Pair<Integer, Integer>(0, 1);
        Pair<Integer, Integer> L2 = new Pair<Integer, Integer>(dead1 + 1, dead1 + 2);
        Pair<Integer, Integer> L3 = new Pair<Integer, Integer>(dead2 + 1, dead2 + 2);
        LineRange = new ArrayList<Pair<Integer, Integer>>();
        LineRange.add(L1);
        LineRange.add(L2);
        LineRange.add(L3);


        // Randomly fill boards with tile types
        fillBoard();

    }

    public int getHeight() {
        return Height;
    }

    public int getWidth() {
        return Width;
    }

    // Fills Board with Appropriate Tile Types
    private void fillBoard() {
        for (int r = 0; r < Height; r++) {
            for (int c = 0; c < Width; c++) {
                if (c == dead1 || c == dead2) {
                    PlayArea[r][c] = Tile.NONPLAYABLE;
                } else if (r == 0 || r == Width - 1) {
                    PlayArea[r][c] = Tile.NEXUS;
                } else {
                    double roll = rd.nextDouble();
                    if (roll <= 0.1)
                        PlayArea[r][c] = Tile.BUSH;
                    else if (roll <= 0.2)
                        PlayArea[r][c] = Tile.KOULOU;
                    else if (roll <= 0.3)
                        PlayArea[r][c] = Tile.CAVE;
                    else
                        PlayArea[r][c] = Tile.COMMON;
                }

            }
        }
    }

    // Checks if an Input for Moves for Hero if there is a Hero already in the target tile.
    public boolean OverHero(Coordinates TestTile) {
        int test_r = TestTile.getRow();
        int test_c = TestTile.getCol();
        for (int i = 0; i < HeroLocations.size(); i++) {
            Coordinates Hero_Location = HeroLocations.get(i);
            int r = Hero_Location.getRow();
            int c = Hero_Location.getCol();

            if (test_r == r && test_c == c) {
                return true;
            }
        }
        return false;
    }
    // Find the all available tile for teleport to, return a list of pair(int, int) for the locations
    public ArrayList<Pair<Integer, Integer>> getTelLocation(int HeroNum) {
        Coordinates current = HeroLocations.get(HeroNum);
        int r = current.getRow();
        int c = current.getCol();
        TelLocations  = new ArrayList<Pair<Integer, Integer>>();
        if (c%3 == 0) {
            for (int i = r; i < Height; i++) {
                for (int j = c; j < c+2; j++) {
                    Coordinates Tile = new Coordinates(i,j);
                    if (OverHero(Tile)==false) {
                        TelLocations.add( new Pair<Integer, Integer> (i,j));
                    }
                }
            }
        } else if (c%3 == 1) {
            for (int i = r; i < Height; i++) {
                for (int j = c-1; j < c+1; j++) {
                    Coordinates Tile = new Coordinates(i,j);
                    if (OverHero(Tile)==false) {
                        TelLocations.add(new Pair<Integer, Integer> (i,j));
                    }
                }
            }
        }
        return TelLocations;
    }

    // Print All available locations for teleporting to hero (HeroNumber)
    public void printTele(int HeroNum){
        int helper;
        ArrayList<Pair<Integer, Integer>> tl = getTelLocation(HeroNum);
        System.out.println("Here are the available teleport positions: ");
        for (int i = 0; i < tl.size(); i++) {
            Pair<Integer, Integer> t = tl.get(i);
            System.out.printf( "%d [ %d, %d ] \n", i+1, t.getKey(), t.getValue());
        }
    }

    // Finish the teleport for Hero(turn) to HeroHum at selected tile (TeleNum)
    public void finishTele(int turn, int HeroNum, int TeleNum) {
        ArrayList<Pair<Integer, Integer>> tl = getTelLocation(HeroNum);
        int tr = tl.get(TeleNum).getKey();
        int tc = tl.get(TeleNum).getValue();
        MoveHero(turn, new Coordinates(tr, tc));
    }

    public boolean backTele(int HeroNum) {
        Coordinates current = HeroLocations.get(HeroNum);
        int l = LineRange.get(HeroNum).getKey();
        int r = LineRange.get(HeroNum).getValue();
        if (current.getCol() == l || current.getCol() == r) {
            System.out.println("Hero " + (HeroNum+1) + " is already in his home line");
            return false;
            }
        else {
            System.out.println("Hero " + (HeroNum+1) + " is back his home line");
            Coordinates H = new Coordinates(Height-1, l);
            MoveHero(HeroNum, H);
            return true;

        }


//        if (LineNum == 0 || LineNum == 1) {
//            Coordinates H = new Coordinates(Height-1, 0);
//            MoveHero(turn, H);
//
//        }else if (LineNum == 2 || LineNum == 3) {
//            Coordinates H = new Coordinates(Height-1, dead1-1);
//            MoveHero(turn, H);
//        }
//        else {
//            Coordinates H = new Coordinates(Height-1, dead2-2);
//            MoveHero(turn, H);
//        }
    }

    // Checks if an Input for Moves is Illegal for the Hero of that turn.
    public boolean IllegalMove(char c, int HeroNum) {
        Coordinates Location = HeroLocations.get(HeroNum);
        switch (c) {
            case 'w':
                Coordinates w = new Coordinates(Location.getRow()-1, Location.getCol());
                if (Location.getRow() <= 0)
                    return false;
                else if (PlayArea[Location.getRow() - 1][Location.getCol()] == Tile.NONPLAYABLE)
                    return false;
                else if (OverHero(w))
                    return false;
                else
                    return true;
            case 'a':
                Coordinates a = new Coordinates(Location.getRow(), Location.getCol()-1);
                if (Location.getCol() <= 0)
                    return false;
                else if (PlayArea[Location.getRow()][Location.getCol() - 1] == Tile.NONPLAYABLE)
                    return false;
                else if (OverHero(a))
                    return false;
                else
                    return true;
            case 'd':
                Coordinates d = new Coordinates(Location.getRow(), Location.getCol()+1);
                if (Location.getRow() >= Width - 1)
                    return false;
                else if (PlayArea[Location.getCol()][Location.getCol() + 1] == Tile.NONPLAYABLE)
                    return false;
                else if (OverHero(d))
                    return false;
                else
                    return true;
            case 's':
                Coordinates s = new Coordinates(Location.getRow()+1, Location.getCol());
                if (Location.getRow() >= Height - 1)
                    return false;
                else if (PlayArea[Location.getRow() + 1][Location.getCol()] == Tile.NONPLAYABLE)
                    return false;
                else if (OverHero(s))
                    return false;
                else
                    return true;

        }
        return false;

    }

    // Get board to display using this method.
    public void Display() {
        List<StringBuilder> printableMap = new ArrayList<StringBuilder>();
        for (int row = 0; row < Width * 3; row++) {
            printableMap.add(new StringBuilder());
            if ((row / 3) % 2 == 0) {
                for (int col = 0; col < Width; col++) {
                    if (row % 2 == 0) {
                        createOutterCell(PlayArea, printableMap, row, col);
                    } else {
                        createInnerCell(PlayArea, printableMap, row, col);
                    }

                    if (col == Width - 1)
                        printableMap.get(row).append("\n");
                }
            } else {
                for (int col = 0; col < Width; col++) {
                    if (row % 2 == 1) {
                        createOutterCell(PlayArea, printableMap, row, col);
                    } else {
                        createInnerCell(PlayArea, printableMap, row, col);
                    }

                    if (col == Width - 1)
                        printableMap.get(row).append("\n");
                }
            }

            if (row % 3 == 2)
                printableMap.get(row).append("\n");
        }

        for (int i = 0; i < Width * 3; i++) {
            System.out.print(printableMap.get(i));
        }
    }

    // Moves Hero to Tile Specified Coordinates
    public Tile MoveHero(int HeroNum, Coordinates c) {
        HeroLocations.get(HeroNum).setCoords(c.getRow(), c.getCol());
        return PlayArea[c.getRow()][c.getCol()];
    }

    // Convert Character Input to Coordinate type for easy movement.
    public Coordinates convertMove(int HeroNum, char c) {
        Coordinates current = HeroLocations.get(HeroNum);
        switch (c) {
            case 'w':
                return new Coordinates(current.getRow() - 1, current.getCol());
            case 'a':
                return new Coordinates(current.getRow(), current.getCol() - 1);
            case 'd':
                return new Coordinates(current.getRow(), current.getCol() + 1);
            case 's':
                return new Coordinates(current.getRow() + 1, current.getCol());
//            case 't':
//                Coordinates helper = HeroLocations.get(tel_sign);
//                current = helper;
        }
        current.setRow(Height-1);
        return current;
//        return new Coordinates(current.getRow() + 1, current.getCol());
    }


    private static void createOutterCell(Tile[][] map, List<StringBuilder> printableMap, int row, int col) {
        switch (map[row / 3][col]) {
            case NEXUS:
                printableMap.get(row).append(getOuterCellStr('N', ANSI_BLUE));
                break;
            case COMMON:
                printableMap.get(row).append(getOuterCellStr('P', ANSI_YELLOW));
                break;
            case KOULOU:
                printableMap.get(row).append(getOuterCellStr('K', ANSI_CYAN));
                break;
            case CAVE:
                printableMap.get(row).append(getOuterCellStr('C', ANSI_PURPLE));
                break;
            case BUSH:
                printableMap.get(row).append(getOuterCellStr('B', ANSI_GREEN));
                break;
            case NONPLAYABLE:
                printableMap.get(row).append(getOuterCellStr('I', ANSI_RED));
                break;
        }
    }

    private static String getOuterCellStr(char c, String Color) {
        StringBuilder str = new StringBuilder();
        str.append(Color);
        for (int i = 0; i < 2; i++) {
            str.append(c).append(" - ");
        }

        str.append(c).append("   ");
        str.append(ANSI_RESET);
        return str.toString();
    }

    private String getInnerCellStr(String component) {
        return "| " + component + " |   ";
    }

    private void createInnerCell(Tile[][] map, List<StringBuilder> printableMap, int row, int col) {
        String component = getCellComponent(row / 3, col);
        if (map[row / 3][col] == Tile.NONPLAYABLE)
            component = ANSI_RED + "X X X" + ANSI_RESET;
        printableMap.get(row).append(getInnerCellStr(component));
    }

    private String getHeroComponent(Coordinates c, ArrayList<Coordinates> l) {
        for (int i = 0; i < l.size(); i++) {
            if (l.get(i).equals(c)) {
                // ret = heroParty.getHero(i).MapRepresent();
                return "H" + Integer.toString(i+1);
            }
        }
        return "  ";
    }

    private String getMonsterComponent(Coordinates c, ArrayList<Coordinates> l) {
        for (int i = 0; i < l.size(); i++) {
            if (l.get(i).equals(c)) {
                // ret = heroParty.getHero(i).MapRepresent();
                return "M" + Integer.toString(i);
            }
        }
        return "  ";
    }

    private String getCellComponent(int row, int col) {
        Coordinates coords = new Coordinates(row, col);
        String ret;
        ret = getHeroComponent(coords, HeroLocations);
        ret += " ";
        ret += getMonsterComponent(coords, MonsterLocations);
        return ret;

    }

//    public Coordinates IllgealTeleport(int turn, int helper) {
//        Coordinates helper_loc = HeroLocations.get(helper);
//        if (Coordinates(helper_loc.getRow(), helper_loc.getCol() - 1) ){
//
//        }
//
//        return new Coordinates(helper_loc.getRow(), helper_loc.getCol() + 1);
//
//        return new Coordinates(helper_loc.getRow() + 1, helper_loc.getCol());
//    }

}
