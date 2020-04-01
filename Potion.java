public class Potion extends Item{
    private PotionType Type;
    private  int Boost;

    public Potion(String name, int Cost, int level, PotionType stat, int Boost){
        super(name, Cost, level);
        this.Type = stat;
        this.Boost = Boost;
    }
    public PotionType getStat(){
        return Type;
    }
    public int getBoost(){
        return Boost;
    }
    
    public String toString() {
        return String.format("Potion Name: %s Potion Type: %s Boost: %d LevelRequirement: %d Price: %d \n",
                getName(),getStat(), getBoost(), getlevelReq(), getCost());
    }
    
}