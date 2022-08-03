package players;
import field.Field;
import field.Ship;
import game.IGame;
import util.Validations;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;

public class NPC extends Player implements IGame {
    int rank;

    public NPC(int rank) {
        if (rank == 0) this.name = "rookie";
        if (rank == 1) this.name = "amateur";
        if (rank == 2) this.name = "professional";
        if (rank == 3) this.name = "nightmare";
        this.field = new Field();
        this.setFieldOpp(new Field());
        this.hasDamagedShip = false;
        this.listOfShips = new ArrayList<>();
        this.isAlive = true;
        this.rank = rank;
    }

    private String genStrFromNoseCords(int i, int j) {
        String str = "";
        str = str.concat(Character.toString((char) (i + 97)));
        str = str.concat(Integer.toString(j + 1));
        if (Math.random() > 0.5)
            str = str.concat("v");
        else str = str.concat("h");
        return str;
    }
    private String cordsFromCell(int noseCords) {
        int cords = 0;
        for (int i = 0; i < this.field.getField().length; i++)
            for (int j = 0; j < this.field.getField().length; j++)
                if (this.field.getField()[i][j] == '~') {
                    cords++;
                    if (cords == noseCords)
                        return genStrFromNoseCords(i, j);
                }
        return "u100t";
    }
    @Override
    public void setDots(Ship ship) {
        this.field.setLabels(ship, ".");
    }

    private void setVerticalShip(Ship ship) {
        if (Validations.isShipValid(ship, this.field)) {
            for (int i = 0; i < ship.getRank(); i++) {
                field.setCell(ship.getNose()[0] + i, ship.getNose()[1], "ship");
            }
            setDots(ship);
        }
    }
    private void setHorizontalShip(Ship ship) {
        if (Validations.isShipValid(ship, this.field)) {
            for (int i = 0; i < ship.getRank(); i++) {
                field.setCell(ship.getNose()[0], ship.getNose()[1] + i, "ship");
            }
            setDots(ship);
        }
    }
    private void setShip(Ship ship) {
        if (ship.getIsVertical()) {
            setVerticalShip(ship);
        } else {
            setHorizontalShip(ship);
        }
        listOfShips.add(ship);
    }
    private void createShip(int rank, int freeCells) {
        int noseCords = (int) (Math.random() * freeCells);
        String str = cordsFromCell(noseCords);
        if (Validations.isStrValid(str) || !Validations.isShipValid(new Ship(rank, str), this.field) ||
                Validations.listOfShipsContains(new Ship(rank, str), this.listOfShips) == 1) {
            createShip(rank, freeCells);
        } else {
            Ship ship = new Ship(rank,str);
            setShip(ship);
            System.out.println(Arrays.toString(ship.getShipCords().toArray()));
        }
    }
    @Override
    public void autoSetListOfShips() {
        int freeCells = field.getFreeCellsForShip();
        for (int i = 4; i > 0; i--) {
            for (int j = 0; j <= 4 - i; j++) {
                createShip(i, freeCells);
                freeCells = field.getFreeCellsForShip();
            }
        }
    }

    private String cordsFromCellFight(int cords) {
        int cord = 0;
        for (int i = 0; i < this.field.getField().length; i++)
            for (int j = 0; j < this.field.getField().length; j++)
                if (this.getFieldOpp().getField()[i][j] != 'X' && this.getFieldOpp().getField()[i][j] != '*') {
                    cord++;
                    if (cord == cords)
                        return genStrFromNoseCords(i, j);
                }
        System.out.println(cord);
        return "";
    }
    @Override
    public String shot() {
        int freeCells = this.getFieldOpp().getFreeCellsForShot();
        int cell = (int) (Math.random() * freeCells + 1);
        String str = cordsFromCellFight(cell).substring(0,cordsFromCellFight(cell).length()- 1);
        int[] cords = new int[] {(int) str.charAt(0) - 97, Integer.parseInt(str.substring(1)) - 1};
        if ((getFieldOpp().getField()[cords[0]][cords[1]] == '/')) {
            getFieldOpp().setCell(cords[0], cords[1],"hit");
        }
        else getFieldOpp().setCell(cords[0], cords[1],"miss");
        System.out.println("Противник атаковал поле " + str);
        return str;
    }

    @Override
    public boolean getShot(String str) {
        AtomicBoolean shot = new AtomicBoolean(false);
        listOfShips.forEach(ship -> ship.getShipCords().forEach(cords -> {
            if (cords.equals(str))
                shot.set(true);
        }));
        listOfShips.forEach(ship -> ship.getShipCords().removeIf(cords -> cords.equals(str)));
        listOfShips.forEach(ship -> {
            if (ship.getShipCords().isEmpty())
                if (ship.getIsVertical())
                    this.field.setLabels(ship, "miss");
                else
                    this.field.setLabels(ship, "miss");
        });
        listOfShips.removeIf(ship -> ship.getShipCords().isEmpty());
        return shot.get();
    }
}
