abstract class Item{
    private String Name;
    private int Cost;
    private int levelReq;

    public Item(String name, int cost, int level ){
        Name = name;
        Cost = cost;
        levelReq = level;
    }
    public String getName(){
        return Name;
    }
    public int getCost(){
        return Cost;
    }
    public int getlevelReq(){
        return levelReq;
    }
    public abstract String toString();

} 