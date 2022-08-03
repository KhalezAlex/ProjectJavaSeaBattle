package players;

import java.util.ArrayList;
import field.Field;
import field.Ship;

public class Player {
    String name;
    boolean hasDamagedShip;
    Field field;
    private Field fieldOpp;
    ArrayList<Ship> listOfShips;
    boolean isAlive;

    public String getName() {
        return name;
    }
    public boolean isHasDamagedShip() {
        return hasDamagedShip;
    }
    public Field getField() {
        return field;
    }
    public Field getFieldOpp() {
        return fieldOpp;
    }
    public ArrayList<Ship> getListOfShips() {
        return listOfShips;
    }
    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    public void setFieldOpp(Field fieldOpp) {
        this.fieldOpp = fieldOpp;
    }
}
