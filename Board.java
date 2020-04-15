import java.util.Random;

import javafx.util.Pair;

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

    // private ArrayList<Coordinates> HeroLocations;
    // private ArrayList<Coordinates> MonsterLocations;
    private MonsterParty Mp;
    private HeroParty Hp;

    // Dead Columns
    private int dead1;
    private int dead2;

    // Line Range
    private ArrayList<Pair<Integer, Integer>> LineRange;

    private Random rd;

    // private HeroParty heroParty;
    // private MonsterParty monsterParty;

    // Spawn Locations for Monsters
    private Coordinates M1;
    private Coordinates M2;
    private Coordinates M3;

    // Spawn Locations for Heros
    private Coordinates H1;
    private Coordinates H2;
    private Coordinates H3;

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
        M1 = new Coordinates(0, 0);
        M2 = new Coordinates(0, dead1 + 1);
        M3 = new Coordinates(0, dead2 + 1);

        // Heros will start at the bottom left of the lane they are in.

        H1 = new Coordinates(Height - 1, 0);
        H2 = new Coordinates(Height - 1, dead1 + 1);
        H3 = new Coordinates(Height - 1, dead2 + 1);

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

    public void setHp(HeroParty hp) {
        hp.getHero(0).setCoordinates(H1);
        hp.getHero(1).setCoordinates(H2);
        hp.getHero(2).setCoordinates(H3);
        this.Hp = hp;
    }

    public void setMp(MonsterParty mp) {
        mp.getHero(0).setCoordinates(M1);
        mp.getHero(1).setCoordinates(M2);
        mp.getHero(2).setCoordinates(M3);
        this.Mp = mp;
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
                } else if (r == 0 || r == Height - 1) {
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

    // Checks if an Input for Moves for Hero if there is a Hero already in the
    // target tile.
    public boolean OverHero(Coordinates TestTile) {
        int test_r = TestTile.getRow();
        int test_c = TestTile.getCol();
        for (int i = 0; i < Hp.getPartySize(); i++) {
            Coordinates Hero_Location = Hp.getHero(i).getCoordinates();
            int r = Hero_Location.getRow();
            int c = Hero_Location.getCol();

            if (test_r == r && test_c == c) {
                return true;
            }
        }
        return false;
    }

    // Find the all available tile for teleport to, return a list of pair(int, int)
    // for the locations
    public ArrayList<Pair<Integer, Integer>> getTelLocation(int HeroNum) {
        Coordinates current = Hp.getHero(HeroNum).getCoordinates();
        int r = current.getRow();
        int c = current.getCol();
        ArrayList<Pair<Integer, Integer>> TelLocations = new ArrayList<Pair<Integer, Integer>>();
        if (c % 3 == 0) {
            for (int i = r; i < Height; i++) {
                for (int j = c; j < c + 2; j++) {
                    Coordinates Tile = new Coordinates(i, j);
                    if (OverHero(Tile) == false) {
                        TelLocations.add(new Pair<Integer, Integer>(i, j));
                    }
                }
            }
        } else if (c % 3 == 1) {
            for (int i = r; i < Height; i++) {
                for (int j = c - 1; j < c + 1; j++) {
                    Coordinates Tile = new Coordinates(i, j);
                    if (OverHero(Tile) == false) {
                        TelLocations.add(new Pair<Integer, Integer>(i, j));
                    }
                }
            }
        }
        return TelLocations;
    }

    // Print All available locations for teleporting to hero (HeroNumber)
    public void printTele(int HeroNum) {
        int helper;
        ArrayList<Pair<Integer, Integer>> tl = getTelLocation(HeroNum);
        System.out.println("Here are the available teleport positions: ");
        for (int i = 0; i < tl.size(); i++) {
            Pair<Integer, Integer> t = tl.get(i);
            System.out.printf("%d [ %d, %d ] \n", i + 1, t.getKey(), t.getValue());
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
        Coordinates current = Hp.getHero(HeroNum).getCoordinates();
        int l = LineRange.get(HeroNum).getKey();
        int r = LineRange.get(HeroNum).getValue();
        if (current.getCol() == l || current.getCol() == r) {
            System.out.println("Hero " + (HeroNum + 1) + " is already in his home line");
            return false;
        } else {
            System.out.println("Hero " + (HeroNum + 1) + " is back his home line");
            Coordinates H = new Coordinates(Height - 1, l);
            MoveHero(HeroNum, H);
            return true;

        }

        // if (LineNum == 0 || LineNum == 1) {
        // Coordinates H = new Coordinates(Height-1, 0);
        // MoveHero(turn, H);
        //
        // }else if (LineNum == 2 || LineNum == 3) {
        // Coordinates H = new Coordinates(Height-1, dead1-1);
        // MoveHero(turn, H);
        // }
        // else {
        // Coordinates H = new Coordinates(Height-1, dead2-2);
        // MoveHero(turn, H);
        // }
    }

    // Checks if an Input for Moves is Illegal for the Hero of that turn.
    public boolean IllegalMove(char c, int HeroNum) {
        Coordinates Location = Hp.getHero(HeroNum).getCoordinates();
        switch (c) {
            case 'w':
                Coordinates w = new Coordinates(Location.getRow() - 1, Location.getCol());
                if (Location.getRow() <= 0)
                    return false;
                else if (PlayArea[Location.getRow() - 1][Location.getCol()] == Tile.NONPLAYABLE)
                    return false;

                else if (PastMonster(Location)) {
                    return false;
                } else
                    return true;
            case 'a':
                Coordinates a = new Coordinates(Location.getRow(), Location.getCol() - 1);
                if (Location.getCol() <= 0)
                    return false;
                else if (PlayArea[Location.getRow()][Location.getCol() - 1] == Tile.NONPLAYABLE)
                    return false;
                else if (OverHero(a))
                    return false;
                else
                    return true;
            case 'd':
                Coordinates d = new Coordinates(Location.getRow(), Location.getCol() + 1);
                if (Location.getRow() >= Width - 1)
                    return false;
                else if (PlayArea[Location.getCol()][Location.getCol() + 1] == Tile.NONPLAYABLE)
                    return false;
                else if (OverHero(d))
                    return false;
                else
                    return true;
            case 's':
                Coordinates s = new Coordinates(Location.getRow() + 1, Location.getCol());
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

    // Check Tail effects using this method.
    public void CheckTail(int HeroNum) {
        Coordinates Location = Hp.getHero(HeroNum).getCoordinates();
        int c = Location.getCol();
        int r = Location.getRow();

        if (PlayArea[r][c] == Tile.BUSH) {

        }
    }

    // Get board to display using this method.
    public void Display() {
        List<StringBuilder> printableMap = new ArrayList<StringBuilder>();
        for (int row = 0; row < Height * 3; row++) {
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

        for (int i = 0; i < Height * 3; i++) {
            System.out.print(printableMap.get(i));
        }
    }

    // Moves Hero to Tile Specified Coordinates
    public Tile MoveHero(int HeroNum, Coordinates c) {
        Hp.getHero(HeroNum).setCoordinates(c);
        return PlayArea[c.getRow()][c.getCol()];
    }

    // Convert Character Input to Coordinate type for easy movement.

    public Coordinates convertMove(int HeroNum, char c, int tel_sign) {
        Coordinates current = Hp.getHero(HeroNum).getCoordinates();

        switch (c) {
            case 'w':
                return new Coordinates(current.getRow() - 1, current.getCol());
            case 'a':
                return new Coordinates(current.getRow(), current.getCol() - 1);
            case 'd':
                return new Coordinates(current.getRow(), current.getCol() + 1);
            case 's':
                return new Coordinates(current.getRow() + 1, current.getCol());

            case 't':
                break;

        }
        current.setRow(Height - 1);
        return current;
        // return new Coordinates(current.getRow() + 1, current.getCol());
    }

    private static void createOutterCell(Tile[][] map, List<StringBuilder> printableMap, int row, int col) {
        System.out.println(row);
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

    private String getHeroComponent(Coordinates c) {
        for (int i = 0; i < Hp.getPartySize(); i++) {
            if (Hp.getHero(i).getCoordinates().equals(c)) {
                // ret = heroParty.getHero(i).MapRepresent();
                return "H" + Integer.toString(i + 1);
            }
        }
        return "  ";
    }

    private String getMonsterComponent(Coordinates c) {
        for (int i = 0; i < Mp.getPartySize(); i++) {
            // System.out.println("PartySize" + Mp.getPartySize());
            if (Mp.getHero(i).getCoordinates().equals(c)) {
                // ret = heroParty.getHero(i).MapRepresent();
                return "M" + Integer.toString(i);
            }
        }
        return "  ";

    }

    private String getCellComponent(int row, int col) {
        Coordinates coords = new Coordinates(row, col);
        String ret;
        ret = getHeroComponent(coords);
        ret += " ";
        ret += getMonsterComponent(coords);
        return ret;

    }

    public Tile getTile(int i) {
        Coordinates c = Hp.getHero(i).getCoordinates();
        return PlayArea[c.getRow()][c.getCol()];
    }

    public boolean checkWin() {
        for (int i = 0; i < Hp.getPartySize(); i++) {
            Coordinates c = Hp.getHero(i).getCoordinates();
            if (c.getRow() == 0) {
                return true;
            }
        }
        return false;

    }

    public boolean checkLoss() {
        for (int i = 0; i < Mp.getPartySize(); i++) {
            Coordinates c = Mp.getHero(i).getCoordinates();
            if (c.getRow() == Height - 1) {
                return true;
            }
        }
        return false;

    }

    public void respawn(int i) {
        if (i == 0) {
            System.out.println("Hero1 sent to spawn");
            Coordinates c = Hp.getHero(i).getCoordinates();
            c.setCoords(H1.getRow(), H1.getCol());
        } else if (i == 1) {
            Coordinates c = Hp.getHero(i).getCoordinates();
            c.setCoords(H2.getRow(), H2.getCol());
        } else {
            Coordinates c = Hp.getHero(i).getCoordinates();
            c.setCoords(H3.getRow(), H3.getCol());
        }

    }

    public int hasMonster(Coordinates c) {
        for (int i = 0; i < Mp.getPartySize(); i++) {
            Coordinates l = Mp.getHero(i).getCoordinates();
            if (l.equals(c)) {
                return i;
            }
        }
        // return -1 if no monsters in the same cell.
        return -1;
    }

    public int hasHeroAdjacent(int mon) {
        Coordinates c = Mp.getHero(mon).getCoordinates();
        for (int i = 0; i < Hp.getPartySize(); i++) {
            // case if Hero is below Monster
            Coordinates hL = Hp.getHero(i).getCoordinates();
            if (hL.getRow() == c.getRow() - 1 && hL.getCol() == c.getCol()) {
                return i;
            }
            // Same row but if hero is to the right of the monster
            else if (hL.getCol() + 1 == c.getCol() && hL.getRow() == c.getRow()) {
                return i;
            }
            // Same row but hero is to the left of the monster.
            else if (hL.getCol() - 1 == c.getCol() && hL.getRow() == c.getRow()) {
                return i;
            }
            // hero is diagonally to the left of the monster
            else if (hL.getCol() - 1 == c.getCol() && hL.getRow() == c.getRow() - 1) {
                return i;
            }
            // hero is diagonally to the right of the monster
            else if (hL.getCol() + 1 == c.getCol() && hL.getRow() == c.getRow() - 1) {
                return i;
            }

        }
        return -1;
    }

    public void Hdeath(int i) {
        System.out.println("Hero Removed from Map");
        Hp.getHero(i).setCoordinates(new Coordinates(-1, -1));

    }

    public void Mdeath(int i) {

        Mp.getHero(i).setCoordinates(new Coordinates(-1, -1));

    }

    public void moveMonster(int i) {
        Coordinates mL = Mp.getHero(i).getCoordinates();
        mL.setCoords(mL.getRow() + 1, mL.getCol());
    }

    public boolean hasMonsterAdjacent(Coordinates c) {
        for (int i = 0; i < Mp.getPartySize(); i++) {
            Coordinates M = Mp.getHero(i).getCoordinates();
            // check Horizontals
            if (M.getRow() == c.getRow() && Math.abs(c.getCol() - M.getCol()) == 1) {
                return true;
            }
            // check Verticals
            else if (M.getCol() == c.getCol() && Math.abs(c.getRow() - M.getRow()) == 1) {
                return true;
            }
            // checking diagonals
            else if (Math.abs(M.getCol() - c.getCol()) == 1 && Math.abs(M.getRow() - c.getRow()) == 1) {
                return true;
            }
        }
        return false;

    }

    public ArrayList<Integer> getAdjacentM(Coordinates c) {
        ArrayList<Integer> ret = new ArrayList<Integer>();
        for (int i = 0; i < Mp.getPartySize(); i++) {
            Coordinates M = Mp.getHero(i).getCoordinates();
            // check Horizontals
            if (M.getRow() == c.getRow() && Math.abs(c.getCol() - M.getCol()) == 1) {
                ret.add(i);
            }
            // check Verticals
            else if (M.getCol() == c.getCol() && Math.abs(c.getRow() - M.getRow()) == 1) {
                ret.add(i);
            }
            // checking diagonals
            else if (Math.abs(M.getCol() - c.getCol()) == 1 && Math.abs(M.getRow() - c.getRow()) == 1) {
                ret.add(i);
            }

        }
        return ret;
    }

    public boolean PastMonster(Coordinates Location) {
        for (int i = 0; i < Mp.getPartySize(); i++) {
            Coordinates mLocation = Mp.getHero(i).getCoordinates();
            if (mLocation.equals(Location)) {
                return true;
            } else if (mLocation.getRow() == Location.getRow()
                    && Math.abs(mLocation.getCol() - Location.getCol()) == 1) {
                return true;
            }
        }
        return false;
    }

    // Testing Code for the Board class.
    // public static void main(String[] args) {
    // Board world = new Board(8, 8);
    // world.Display();
    // }

}
