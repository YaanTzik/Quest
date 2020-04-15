import java.util.ArrayList;

public class Party<T extends Combatant> {
    private ArrayList<T> party;
    private int PartySize;

    public Party(int i) {
        party = new ArrayList<T>();
        PartySize = i;
    }

    public void AddHero(T c) {
        System.out.println("current size" + party.size());
        System.out.println("PartySize" + PartySize);
        // System.out.println(party.size()> PartySize);
        if (party.size() < PartySize) {
            party.add(c);
            System.out.println(party.size());
            System.out.println("Monster added");
        }

    }

    public int getPartySize() {
        return PartySize;
    }

    public void setPartySize(int i) {
        PartySize = i;
    }

    public T getHero(int i) {
        return party.get(i);
    }

    public ArrayList<T> getParty() {
        return party;
    }

    public boolean AllFaint() {
        boolean ret = true;
        for (int i = 0; i < PartySize; i++) {
            if (!getHero(i).getFainted()) {
                return false;
            }
        }
        return ret;
    }

    public int target() {
        // Since no specific form of targetting heroes
        // for monsters i take the Hero with
        // The lowest Health value
        // Also used in scenario where User targets invalid target
        int target = 0;
        for (int i = 0; i < getPartySize(); i++) {
            if (getHero(i).getFainted()) {
                continue;
            }
            if (getHero(target).getHp() < getHero(i).getHp()) {
                target = i;
            }
        }
        return target;
    }

    public void remove(int i) {
        party.remove(i);
        this.setPartySize(getPartySize() - 1);
    }
}