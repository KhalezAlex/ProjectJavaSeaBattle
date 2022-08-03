package players;
import field.Field;
import field.Ship;
import util.Output;
import util.Validations;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

public class PC extends Player implements game.IGame {
    public PC(String name) {
        this.name = name;
        this.field = new Field();
        this.setFieldOpp(new Field());
        this.hasDamagedShip = false;
        this.listOfShips = new ArrayList<>();
        this.isAlive = true;
    }


    private void setVerticalShip(Ship ship) {
        for (int i = 0; i < ship.getRank(); i++) {
            field.setCell(ship.getNose()[0] + i, ship.getNose()[1], "ship");
        }
        setDots(ship);
    }
    private void setHorizontalShip(Ship ship) {
        for (int i = 0; i < ship.getRank(); i++) {
            field.setCell(ship.getNose()[0], ship.getNose()[1] + i, "ship");
        }
        setDots(ship);
    }
    private void setShip(Ship ship) {
        if (ship.getIsVertical()) {
            setVerticalShip(ship);
        } else {
            setHorizontalShip(ship);
        }
        listOfShips.add(ship);
        System.out.println(Arrays.toString(ship.getShipCords().toArray()));
    }
    private void createShip(int rank) {
        Scanner sc = new Scanner(System.in);
        String str = sc.next();
        if (Validations.isStrValid(str) || !Validations.isShipValid(new Ship(rank, str), this.field) ||
                Validations.listOfShipsContains(new Ship(rank, str), this.listOfShips) == 1) {
            Output.refreshConsole(this);
            System.out.print("Неверные данные! Установите снова корабль " + rank + " ранга: ");
            createShip(rank);
        } else {
            setShip(new Ship(rank, str));
            Output.refreshConsole(this);
        }
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
    private String noseCordsFromCell(int noseCords) {
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
    private void createShip(int rank, int freeCells) {
        int noseCords = (int) (Math.random() * freeCells);
        String str = noseCordsFromCell(noseCords);
        if (Validations.isStrValid(str) || !Validations.isShipValid(new Ship(rank, str), this.field) ||
                Validations.listOfShipsContains(new Ship(rank, str), this.listOfShips) == 1) {
            createShip(rank, freeCells);
        } else {
            Ship ship = new Ship(rank,str);
            setShip(ship);
            Output.refreshConsole(this);
        }
    }
    public void setListOfShips() {
        for (int i = 4; i > 0; i--) {
            for (int j = 0; j <= 4 - i; j++) {
                System.out.print("Установите корабль " + i + " ранга: ");
                createShip(i);
            }
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

    @Override
    public void setDots(Ship ship) {
        this.field.setLabels(ship, ".");
    }

    @Override
    public String shot() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Введите координаты выстрела: ");
        String str = sc.nextLine();
        if (Validations.isShotValid(str)) {
//            System.out.println(Validations.isShotValid(str));
            int[] cords = new int[] {(int) str.charAt(0) - 97, Integer.parseInt(str.substring(1)) - 1};
            if ((getFieldOpp().getField()[cords[0]][cords[1]] == '/')) {
                getFieldOpp().setCell(cords[0], cords[1],"hit");
            }
            else getFieldOpp().setCell(cords[0], cords[1],"miss");
            return str;
        }
        else {
            System.out.print("Неверные координаты!\n");
            return shot();
        }
    }

    @Override
    public boolean getShot(String str) {
        AtomicBoolean shot = new AtomicBoolean(false);
        listOfShips.forEach(ship -> ship.getShipCords().forEach(cords -> {
            if (cords.equals(str)) {
                shot.set(true);
            }
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
