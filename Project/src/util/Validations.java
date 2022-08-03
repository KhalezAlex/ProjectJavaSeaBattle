package util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

import field.Ship;
import field.Field;

public class Validations {
    public static boolean isCordsValid(int x, int y) {
        return ((x < 10 && x > -1) && (y < 10 && y > -1));
    }

    public static boolean isCellValid(int x, int y, Field field) {
        return (field.getField()[x][y] == '~' || field.getField()[x][y] == '.');
    }

    public static boolean verticalValid(Ship ship, Field field) {
        if (ship.getNose()[0] + ship.getRank() > 10)
            return false;
        for (int i = 0; i < ship.getRank() + 2; i++) {
            if (isCordsValid(ship.getNose()[0] + i - 1, ship.getNose()[1] - 1))
                if (!isCellValid(ship.getNose()[0] + i - 1, ship.getNose()[1] - 1, field))
                    return false;
            if (isCordsValid(ship.getNose()[0] + i - 1, ship.getNose()[1] + 1))
                if (!isCellValid(ship.getNose()[0] + i - 1, ship.getNose()[1] + 1, field))
                    return false;
        }
        if (isCordsValid(ship.getNose()[0] - 1, ship.getNose()[1]))
            if (!isCellValid(ship.getNose()[0] - 1, ship.getNose()[1], field))
                return false;
        if (isCordsValid(ship.getNose()[0] + ship.getRank(), ship.getNose()[1]))
            return isCellValid(ship.getNose()[0] + ship.getRank(), ship.getNose()[1], field);
        return true;
    }

    public static boolean horizontalValid(Ship ship, Field field) {
        if (ship.getNose()[1] + ship.getRank() > 10)
            return false;
        for (int i = 0; i < ship.getRank() + 2; i++) {
            if (isCordsValid(ship.getNose()[0] - 1, ship.getNose()[1] + i - 1))
                if (!isCellValid(ship.getNose()[0] - 1, ship.getNose()[1] + i - 1, field))
                    return false;
            if (isCordsValid(ship.getNose()[0] + 1, ship.getNose()[1] + i - 1))
                if (!isCellValid(ship.getNose()[0] + 1, ship.getNose()[1] + i - 1, field))
                    return false;
        }
        if (isCordsValid(ship.getNose()[0], ship.getNose()[1] - 1))
            if (!isCellValid(ship.getNose()[0], ship.getNose()[1] - 1, field))
                return false;
        if (isCordsValid(ship.getNose()[0], ship.getNose()[1] + ship.getRank()))
            return isCellValid(ship.getNose()[0], ship.getNose()[1] + ship.getRank(), field);
        return true;
    }

    public static boolean isShipValid(Ship ship, Field field) {
        if (ship.getIsVertical()) {
            return verticalValid(ship, field);
        } else {
            return horizontalValid(ship, field);
        }
    }

    public static boolean isStrValid(String str) {
        if (str.equals("")) return false;
        if (str.charAt(0) >= 'a' && str.charAt(0) <= 'j') {
            str = str.substring(1);
            if (str.charAt(str.length() - 1) == 'v' || str.charAt(str.length() - 1) == 'h')
                str = str.substring(0, str.length() - 1);
            else return true;
            for (int i = 0; i < str.length(); i++)
                if (!Character.isDigit(str.charAt(i))) {
                    return true;
                }
            if (str.equals("")) return true;
            return Integer.parseInt(str) <= 0 || Integer.parseInt(str) >= 11;
        } else return true;
    }

    public static boolean isShotValid(String str) {
        if (str.equals("")) return false;
        if (str.charAt(0) < 'a' && str.charAt(0) > 'j') {
            return false;
        }
        str = str.substring(1);
//        System.out.println(str);
        for (int i = 0; i < str.length(); i++)
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        if (str.equals("")) return false;
        return Integer.parseInt(str) > 0 && Integer.parseInt(str) < 11;
    }

    public static int listOfShipsContains(Ship shipCheck, ArrayList<Ship> listOfShips) {
        AtomicInteger amount = new AtomicInteger();
        listOfShips.forEach(ship -> {
            if (Arrays.equals(ship.getNose(), shipCheck.getNose()))
                amount.getAndIncrement();
        });
        return amount.get();
    }
}
