import java.util.ArrayList;

interface BuyerInterface {
    public <e extends Item> void PurchaseItem(e item, ArrayList<e> subbag);

    public <e extends Item> void sellItem(e item);
}