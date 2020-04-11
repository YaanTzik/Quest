public class Coordinates {
    private int row;
    private int col;

    public Coordinates(int r, int c) {
        this.row = r;
        this.col = c;
    }

    public int getCol() {
        return col;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int r) {
        this.row = r;
    }

    public void setCol(int c) {
        this.col = c;
    }

    public void setCoords(int r, int c) {
        this.row = r;
        this.col = c;
    }

    public boolean equals(Coordinates c) {
        return (this.row == c.getRow() && this.col == c.getCol());
    }

}