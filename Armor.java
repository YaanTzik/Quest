public class Armor extends Item{
    private int Reduction;

    public Armor(String name , int cost, int level, int reduction){
        super(name, cost, level);
        this.Reduction = reduction;
    }
    public int getReduction(){
        return Reduction;
    }
    
    public String toString() {
        return String.format("ArmorName: %s LevelRequirement: %d Price: %d ReductionValue : %d \n",
                getName(), getlevelReq(), getCost(), getReduction());
    }

}