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
    private ArrayList<Coordinates> HeroLocations;
    private ArrayList<Coordinates> MonsterLocations;

    // Dead Rows
    private int dead1;
    private int dead2;

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

    // Checks if an Input for Moves is Illegal for the Hero of that turn.
    public boolean IllegalMove(char c, int HeroNum) {
        Coordinates Location = HeroLocations.get(HeroNum);
        switch (c) {
            case 'w':
                if (Location.getRow() <= 0)
                    return false;
                else if (PlayArea[Location.getRow() - 1][Location.getCol()] == Tile.NONPLAYABLE)
                    return false;
                else
                    return true;
            case 'a':
                if (Location.getCol() <= 0)
                    return false;
                else if (PlayArea[Location.getRow()][Location.getCol() - 1] == Tile.NONPLAYABLE)
                    return false;
                else
                    return true;
            case 'd':
                if (Location.getRow() == Width - 1)
                    return false;
                else if (PlayArea[Location.getCol()][Location.getCol() + 1] == Tile.NONPLAYABLE)
                    return false;
                else
                    return true;
            case 's':
                if (Location.getRow() == Height - 1)
                    return false;
                else if (PlayArea[Location.getRow() + 1][Location.getCol()] == Tile.NONPLAYABLE)
                    return false;
                else
                    return true;

        }
        return false;

    }

    // Check Tail effects using this method.
    public void CheckTail(int HeroNum) {
        Coordinates Location = HeroLocations.get(HeroNum);
        int c = Location.getCol();
        int r = Location.getRow();

        if (PlayArea[r][c] == Tile.BUSH) {

        }
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
    public Coordinates convertMove(int HeroNum, char c, int tel_sign) {
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
            case 't':
                Coordinates helper = HeroLocations.get(tel_sign);
                current = helper;
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
                return "H" + Integer.toString(i);
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

    // Testing Code for the Board class.
//    public static void main(String[] args) {
//        Board world = new Board(8, 8);
//        world.Display();
//    }

}
