package field;

import util.Validations;

public class Field {
    private final char[][] field = new char[10][10];


    public char[][] getField() {
        return field;
    }

    public void setCell(int i, int j, String value) {
        if (value.equals("ship")) {
            this.field[i][j] = '/';
        }
        if (value.equals("hit")) {
            this.field[i][j] = 'X';
        }
        if (value.equals("miss")) {
            this.field[i][j] = '*';
        }
        if (value.equals(".")) {
            this.field[i][j] = '.';
        }
    }
    public Field() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                field[i][j] = '~';
            }
        }
    }

    public int getFreeCellsForShip() {
        int freeCells = 0;
        for (char[] chars : this.field)
            for (int j = 0; j < this.field.length; j++)
                if (chars[j] == '~')
                    freeCells++;
        return freeCells;
    }

    public int getFreeCellsForShot(){
        int freeCells = 0;
        for (char[] chars : this.field)
            for (int j = 0; j < this.field.length; j++)
                if (chars[j] != 'X' && chars[j] != '*')
                    freeCells++;
        return freeCells;
    }

    private void verticalLabels(Ship ship, String label) {
        for (int i = 0; i < ship.getRank() + 2; i++) {
            if (Validations.isCordsValid(ship.getNose()[0] + i - 1, ship.getNose()[1] - 1))
                setCell(ship.getNose()[0] + i - 1, ship.getNose()[1] - 1, label);
            if (Validations.isCordsValid(ship.getNose()[0] + i - 1, ship.getNose()[1] + 1))
                setCell(ship.getNose()[0] + i - 1, ship.getNose()[1] + 1, label);
        }
        if (Validations.isCordsValid(ship.getNose()[0] - 1, ship.getNose()[1]))
            setCell(ship.getNose()[0] - 1, ship.getNose()[1], label);
        if (Validations.isCordsValid(ship.getNose()[0] + ship.getRank(), ship.getNose()[1]))
            setCell(ship.getNose()[0] + ship.getRank(), ship.getNose()[1], label);
    }
    private void horizontalLabels(Ship ship, String label) {
        for (int i = 0; i < ship.getRank() + 2; i++) {
            if (Validations.isCordsValid(ship.getNose()[0] - 1, ship.getNose()[1] + i - 1))
                setCell(ship.getNose()[0] - 1, ship.getNose()[1] + i - 1, label);
            if (Validations.isCordsValid(ship.getNose()[0] + 1, ship.getNose()[1] + i - 1))
                setCell(ship.getNose()[0] + 1, ship.getNose()[1] + i - 1, label);
        }
        if (Validations.isCordsValid(ship.getNose()[0], ship.getNose()[1] - 1))
            setCell(ship.getNose()[0], ship.getNose()[1] - 1, label);
        if (Validations.isCordsValid(ship.getNose()[0], ship.getNose()[1] + ship.getRank()))
            setCell(ship.getNose()[0], ship.getNose()[1] + ship.getRank(), label);
    }
    public void setLabels(Ship ship, String label) {
        if (ship.getIsVertical()) {
            verticalLabels(ship, label);
        } else {
            horizontalLabels(ship, label);
        }
    }
}
