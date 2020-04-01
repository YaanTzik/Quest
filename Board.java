import java.util.Random;
import javafx.util.Pair;
public class Board{
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
    private Tile[][] PlayArea ;
    private Random rd;
    // private (int)
    private Pair<Integer,Integer> Location;

    
    public Board(int r, int c){
        this.Height = r;
        this.Width =c;
        PlayArea = new Tile[r][c];
        this.rd = new Random();
        // Heroes start at the coordinates(0,0);
        Location = new Pair<Integer,Integer>(0,0);
        fillBoard();

    }

    public Pair<Integer,Integer> getLocation(){
        return Location;
    }
    public int getHeight(){
        return Height;
    }
    public int getWidth(){
        return Width;
    }
    private void fillBoard(){
        for(int r = 0 ; r< Height; r++){
            for (int c =0; c< Width; c++){
                double roll =rd.nextDouble();
                if (roll <= 0.3){
                    this.PlayArea[r][c]= Tile.MARKET;
                }
                else if (roll< 0.8){
                    this.PlayArea[r][c] = Tile.COMMON;
                }
                else{
                    this.PlayArea[r][c] = Tile.NONPLAYABLE;
                }
                
            }
        }
    }
    public void Display(){
        String s = "";
        // s += "current location" + Location+ "\n";
        // int tilecount = 0;
        for (int r = 0; r < Height; r++) {
            s += "+";
            for (int c = 0; c < Width; c++) {
                s += "---+";
            }
            s += "\n";
            for (int c = 0; c < Width; c++) {
                if(Location.getKey() == r && Location.getValue()==c){
                    s+="|"+ANSI_BLUE + " H " + ANSI_RESET;
                }
                else{
                    // System.out.println("Row"+r);
                    // System.out.println("Col"+c);
                    Tile currTile = PlayArea[r][c];
                    if (currTile == Tile.MARKET){
                        s+= "|" +ANSI_PURPLE+ " M "+ ANSI_RESET;
                    }
                    else if(currTile == Tile.COMMON){
                        s+= "|" +ANSI_GREEN_BACKGROUND+ "   "+ ANSI_RESET;
                    } 
                    else{
                        s+= "|" +ANSI_RED+ " X "+ ANSI_RESET;
                    }
                
                }
                
            }
            s += "|\n";
        }
        s += "+";
        for (int c = 0; c < Width; c++) {
            s += "---+";
        }
        s += "\n";
        // System.out.println("Tilecount " + tilecount);
        System.out.print(s);

    }
    public Tile MoveParty(Pair<Integer,Integer> p){
        this.Location = p;
        int r = p.getKey();
        int c = p.getValue();
        return PlayArea[r][c];
        }
    public Pair<Integer,Integer> convertMove(char c){
        switch(c){
            case'w':
                return new Pair<Integer,Integer>(Location.getKey()-1, Location.getValue());
            case'a':
                return new Pair<Integer, Integer>(Location.getKey(), Location.getValue()-1);
            case 'd':
                return new Pair<Integer, Integer>(Location.getKey(),Location.getValue()+1);
            }
        return new Pair<Integer, Integer>(Location.getKey() + 1, Location.getValue());
    }

    
    
    public boolean IlegalMove(char c){
        switch(c){
            case 'w':
                if (Location.getKey() == 0)
                    return false;
                else if (PlayArea[Location.getKey()-1][Location.getValue()]== Tile.NONPLAYABLE)
                    return false;
                else
                    return true;
            case 'a':
                if (Location.getValue() == 0)
                    return false;
                else if (PlayArea[Location.getKey()][Location.getValue()-1] == Tile.NONPLAYABLE)
                    return false;
                else
                    return true;
            case 'd':
                if (Location.getValue() ==Width-1)
                    return false;
                else if (PlayArea[Location.getKey()][Location.getValue() +1] == Tile.NONPLAYABLE)
                    return false;
                else
                    return true;
            case 's':
                if (Location.getKey() == Height-1)
                    return false;
                else if (PlayArea[Location.getKey()+1][Location.getValue()] == Tile.NONPLAYABLE)
                    return false;
                else
                    return true;
        
        }
        return false;

    }
    

}
