import java.util.ArrayList;
import java.util.Random;
public class MonsterParty extends Party<Monster>{
    private int maxLevel;
    private Random rd;

    public MonsterParty(int maxLevel, int partysize ){
        super(partysize);
        System.out.println("Creating party");
        this.maxLevel = maxLevel;
        this.rd = new Random();
        

    }
    public String toString(){
        String s ="";
        for(int i =0; i <getPartySize(); i++){
            s+= getHero(i).toString();
        }
        return s;
    }
    //function to create a randomized team of monsters
    public void createParty(){
        System.out.println("Creating Monsters");
        Monster D1 = new Monster("Desghidorrah",3,150,200,35,MonsterType.Dragon);
        // System.out.println("First Dragon Created");
        Monster D2 = new Monster("Chrysophylax",2,100,250,20,MonsterType.Dragon);
        Monster D3 = new Monster("Bunsen Burner",4,200,250,45,MonsterType.Dragon);
        Monster D4 = new Monster("The Scaleless",7,350,200,75,MonsterType.Dragon);
        Monster D5 = new Monster("Kas-Ethelinh",5,300,250,60,MonsterType.Dragon);
        Monster D6 = new Monster("Alexstraszan", 10 ,500,4500,55,MonsterType.Dragon);
        Monster D7 = new Monster("D-Maleficent", 9,450,400,85,MonsterType.Dragon);
        Monster D8 = new Monster("The Weather Be", 8,400,450,80, MonsterType.Dragon);
        Monster D9 = new Monster("WINGED DRAGON OF RA",15,5000,5000,50,MonsterType.Dragon);
        Monster D10 = new Monster("Dragon Hatchling",1,50,50,20,MonsterType.Dragon);
        // System.out.println("All Dragons Created");

        Monster Ex1 = new Monster("Cyrollalee",7,350,400,75, MonsterType.Exoskeletons);
        Monster Ex2 = new Monster("Brandobaris",3,175,225,30,MonsterType.Exoskeletons);
        Monster Ex3 = new Monster("Skeleton",1,75,125,15, MonsterType.Exoskeletons);
        Monster Ex4 = new Monster("Wicked Witch",2,125,175,25,MonsterType.Exoskeletons);
        Monster Ex5 = new Monster("Aasterinian", 4,200,250,45,MonsterType.Exoskeletons);
        Monster Ex6 = new Monster("Chronepsish",6,325,375,60,MonsterType.Exoskeletons);
        Monster Ex7 = new Monster("Merrshaullk",10,500,450,55,MonsterType.Exoskeletons);
        Monster Ex8 = new Monster("St-Yeenoghu",9,475,425,90, MonsterType.Exoskeletons);
        Monster Ex9 = new Monster("OBELISK THE TORMENTOR", 15,6000,4000,50,MonsterType.Exoskeletons);

        Monster S1 = new Monster("Andrealphus",2,300,250,40,MonsterType.Spirits);
        Monster S2 = new Monster("Aim-Haborum",1,225,100,35, MonsterType.Spirits);
        Monster S3 = new Monster("Andromalius",3,225,225,25, MonsterType.Spirits);
        Monster S4 = new Monster("Fallen Angel",5, 400,350,50, MonsterType.Spirits);
        Monster S5 = new Monster("Ereshkigall",6,475,225,35,MonsterType.Spirits);
        Monster S6 = new Monster("Jormunngad",8,300,450,20,MonsterType.Spirits);
        Monster S7 = new Monster("Rakkshasass", 9,275,300,35,MonsterType.Spirits);
        Monster S8 = new Monster("Tatlcuhtli",10,300,200,50, MonsterType.Spirits);
        Monster S9 = new Monster("EXODIA", 20,8000,6000, 50, MonsterType.Spirits);

        ArrayList<Monster> Monsters = new ArrayList<Monster>();
        Monsters.add(D1);
        Monsters.add(D2);
        Monsters.add(D3);
        Monsters.add(D4);
        Monsters.add(D5);
        Monsters.add(D6);
        Monsters.add(D7);
        Monsters.add(D8);
        Monsters.add(D9);
        Monsters.add(D10);

        Monsters.add(Ex1);
        Monsters.add(Ex2);
        Monsters.add(Ex3);
        Monsters.add(Ex4);
        Monsters.add(Ex5);
        Monsters.add(Ex6);
        Monsters.add(Ex7);
        Monsters.add(Ex8);
        Monsters.add(Ex9);

        Monsters.add(S1);
        Monsters.add(S2);
        Monsters.add(S3);
        Monsters.add(S4);
        Monsters.add(S5);
        Monsters.add(S6);
        Monsters.add(S7);
        Monsters.add(S8);
        Monsters.add(S9);
        
        ArrayList<Monster>MonsterPool = new ArrayList<Monster>(); 
        System.out.println("Monsters Created, Creating a party now");
        for(int i =0; i < Monsters.size(); i++){
            //Narrowing down the monsters we can use in a fight
            if (Monsters.get(i).getLevel()<= maxLevel){
                MonsterPool.add(Monsters.get(i));
            }
        }
        int k=0;
        //We fill the party with the appropriate monsters
        while(k< getPartySize()){
            for(int j = 0; j<MonsterPool.size();j++){
            int roll =rd.nextInt(MonsterPool.size());
            AddHero(MonsterPool.get(roll).clone());

        }
        k++;
        }
        

    }
}