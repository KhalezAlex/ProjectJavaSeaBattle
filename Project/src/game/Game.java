package game;

import java.util.Objects;
import java.util.Scanner;
import util.Output;
import players.NPC;
import players.PC;

public class Game {
    public PC pc;
    public NPC npc;

    private void setPC() {
        System.out.println("""
                -Корабли вводить в формате xyz, где x: {a..j}, y: {1..10}, z: v(вертикальный) или h(горизонтальный)
                 Пример для корабля 2 ранга: d1v- вертикальный корабль: [d1, e1].
                -Координаты атаки вводить в формате xy, где x: {a..j}, y: {1..10}.
                -Под полем каждого игрока список кораблей по рангам.
                -После ввода уровня противника (от 0- rookie- до 3- nightmare)
                 выводится список кораблей игрока для проверки
                """);
        String name;
        Scanner sc = new Scanner(System.in);
        System.out.print("Введите свое имя: ");
        if (Objects.equals(name = sc.next(), "")) {
            setPC();
        } else {
            pc = new PC(name);
            Output.refreshConsole(this);
            System.out.print("Игрок " + pc.getName() + " готов к расстановке кораблей!\n" +
                    "Хотите сами расставить корабли (0), или сделать это автоматически (any key): ");
            String t = sc.next();
            if (t.equals("0"))
                pc.setListOfShips();
            else
                pc.autoSetListOfShips();
        }
    }
    private void setNpc() {
        int rank;
        Scanner sc = new Scanner(System.in);
        System.out.print("Введите ранг оппонента (0-3): ");
        rank = Integer.parseInt(sc.nextLine());
        if (rank < 0 || rank > 3) {
            System.out.println("Введен неверный ранг");
            setNpc();
        } else {
            npc = new NPC(rank);
            System.out.println("Игрок " + npc.getName() + " расставляет корабли.");
            npc.autoSetListOfShips();
        }
    }

    public Game() {
        setPC();
        setNpc();
        this.pc.setFieldOpp(npc.getField());
        this.npc.setFieldOpp(pc.getField());
        Output.refreshConsole(this);
    }

    private void round() {
        boolean shot = true;
        while (shot) {
            String str = pc.shot();
            shot = npc.getShot(str);
            if (npc.getListOfShips().isEmpty()) {
                npc.setAlive(false);
                return;
            }
            Output.refreshConsole(this);
        }
        shot = true;
        while (shot) {
            String str = npc.shot();
            shot = pc.getShot(str);
            if (pc.getListOfShips().isEmpty()) {
                pc.setAlive(false);
                break;
            }
            Output.refreshConsole(this);
        }
    }
    public String game() {
        while (pc.isAlive() && npc.isAlive())
            round();
        Output.refreshConsole(this);
        if(!pc.isAlive())
            return "Победил " + npc.getName();
        else
            return "Победил игрок!";
    }
}
