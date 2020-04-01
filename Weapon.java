public class Weapon extends Item{
    private int Damage;
    private int Hands;
    
    public Weapon(String Name, int Cost, int level, int dmg, int hands){
        super(Name, Cost, level);
        this.Damage = dmg;
        this.Hands = hands;
    }
    public int getDamage(){
        return Damage;
    }
    public int getHands(){
        return Hands;
    }
    public String toString() {
        return String.format("Weapon Name: %s Level Requirement: %d Price: %d Damage : %d , WeaponType: %d \n",
                getName(), getlevelReq(), getCost(), getDamage(), getHands());
    }
}
