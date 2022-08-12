package field;

import java.util.ArrayList;

public class Ship {
    private final int rank;
    private final int[] nose;
    private final boolean isVertical;
    private final ArrayList<String> shipCords = new ArrayList<>();

    public ArrayList<String> getShipCords() {
        return shipCords;
    }
    public int getRank() {
        return rank;
    }
    public int[] getNose() {
        return nose;
    }
    public boolean getIsVertical() {
        return isVertical;
    }

    public Ship(int rank, String str) {
        this.rank = rank;
        isVertical = str.charAt(str.length() - 1) == 'v';
        str = str.substring(0, str.length() - 1);
        this.nose = new int[] {(int) str.charAt(0) - 97, Integer.parseInt(str.substring(1)) - 1};
        if (isVertical){
            for (int i = 0; i < rank; i++) {
                shipCords.add(str.replace(str.charAt(0), (char) ((int) str.charAt(0) + i)));
            }
        }
        else {
            for (int i = 0; i < rank; i++) {
                shipCords.add(str.replace(str.substring(1),
                        Integer.toString(Integer.parseInt(str.substring(1)) + i)));
            }
        }
    }
}