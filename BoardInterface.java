interface BoardInterface {
    public void Display();

    public boolean IllegalMove(char c, int num);

    public boolean checkWin();

    public boolean checkLoss();

}