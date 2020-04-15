interface BuyerInterface {
    public <e extends Item> void PurchaseItem(e item);

    public <e extends Item> void sellItem(e item);
}