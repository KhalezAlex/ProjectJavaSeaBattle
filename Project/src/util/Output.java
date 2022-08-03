package util;

import field.Ship;
import game.Game;
import players.Player;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class Output {
    private static int rankShipAmount(ArrayList<Ship> listOfShips, int rank) {
        AtomicInteger amount = new AtomicInteger();
        listOfShips.forEach(ship -> {
            if (ship.getRank() == rank)
                amount.getAndIncrement();
        });
        return amount.get();
    }
    private static String shipsOwned(ArrayList<Ship> listOfShips) {
        String shipsOwned = "";
        for (int i = 4; i > 0; i--) {
            if (i == 1)
                shipsOwned = shipsOwned.concat("R" + i + ":" + Output.rankShipAmount(listOfShips, i));
            else
                shipsOwned = shipsOwned.concat("R" + i + ":" + Output.rankShipAmount(listOfShips, i) + ", ");
        }
        return shipsOwned;
    }
    private static String[] pcString(Player pc) {
        String[] pcStr = new String[14];
        pcStr[0] = "Поле игрока ".concat(pc.getName());
        pcStr[1] = "   1 2 3 4 5 6 7 8 9 10";
        for (int i = 0; i < 10; i++) {
            pcStr[i + 2] = Character.toString((char) 97 + i).concat("  ");
            for (int j = 0; j < 10; j++) {
                pcStr[i + 2] = pcStr[i + 2].concat(Character.toString(pc.getField().getField()[i][j]).concat(" "));
            }
            pcStr[i + 2] = pcStr[i + 2].concat(" ".concat(Character.toString((char) 97 + i)));
        }
        pcStr[12] = pcStr[1];
        pcStr[13] = shipsOwned(pc.getListOfShips());
        return pcStr;
    }
    private static String[] oppString(Game game) {
        String[] npcStr = new String[14];
        if (game.npc != null) {
            npcStr[0] = "";
            for (int i = 0; i < 28 - game.pc.getName().length(); i++)
                npcStr[0] = npcStr[0].concat(" ");
            npcStr[0] = npcStr[0].concat("Оппонент: ".concat(game.npc.getName()));
            npcStr[1] = "                    1 2 3 4 5 6 7 8 9 10";
            for (int i = 0; i < 10; i++) {
                npcStr[i + 2] = "               ".concat(Character.toString((char) 97 + i).concat("  "));
                for (int j = 0; j < 10; j++) {
                    if (game.pc.getFieldOpp().getField()[i][j] == '*')
                        npcStr[i + 2] = npcStr[i + 2].concat("*").concat(" ");
                    else if (game.pc.getFieldOpp().getField()[i][j] == 'X')
                        npcStr[i + 2] = npcStr[i + 2].concat("X").concat(" ");
                    else
                        npcStr[i + 2] = npcStr[i + 2].concat(".").concat(" ");
//                    npcStr[i + 2] = npcStr[i + 2].concat(Character.toString(game.pc.fieldOpp.getField()[i][j]).concat(" "));
                }
                npcStr[i + 2] = npcStr[i + 2].concat(" ".concat(Character.toString((char) 97 + i)));
            }
            npcStr[12] = npcStr[1];
            npcStr[13] = "                  ".concat(shipsOwned(game.npc.getListOfShips()));
        } else
            for (int i = 0; i < 14; i++)
                npcStr[i] = "";
        return npcStr;
    }

    public static void refreshConsole(Game game) {
        String[] pcField = pcString(game.pc);
        String[] npcField = oppString(game);
        for (int i = 0; i < 14; i++)
            System.out.println(pcField[i].concat(npcField[i]));
    }
    public static void refreshConsole(Player pc) {
        String[] pcField = pcString(pc);
        for (int i = 0; i < 14; i++)
            System.out.println(pcField[i]);
    }
}