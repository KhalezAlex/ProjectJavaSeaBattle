package game;

import field.Ship;

public interface IGame {
    void autoSetListOfShips();
    void setDots(Ship ship);
    String shot();
    boolean getShot(String str);
}
