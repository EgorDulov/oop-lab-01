package demo;

import strategy.FlyingStrategy;
import strategy.Hero;
import strategy.HorseRidingStrategy;
import strategy.MovementStrategy;
import strategy.Point;
import strategy.WalkingStrategy;

import java.util.Locale;
import java.util.Scanner;

public final class Main {

    private Main() {
    }

    public static void main(String[] args) {
        Hero hero = new Hero(new Point(0, 0), new WalkingStrategy());

        try (Scanner scanner = new Scanner(System.in)) {
            boolean running = true;
            while (running && scanner.hasNextLine()) {
                printMenu(hero);
                switch (scanner.nextLine().trim()) {
                    case "1" -> changeStrategy(hero, new WalkingStrategy());
                    case "2" -> changeStrategy(hero, new HorseRidingStrategy());
                    case "3" -> changeStrategy(hero, new FlyingStrategy());
                    case "4" -> moveHero(hero, scanner);
                    case "0" -> running = false;
                    default -> System.out.println("Неизвестный пункт меню, попробуйте снова.");
                }
            }
        }
        System.out.println("Выход.");
    }

    private static void printMenu(Hero hero) {
        Point p = hero.getPosition();
        System.out.printf(Locale.ROOT, "%nПозиция героя: (%.1f; %.1f), способ: %s%n",
                p.x(), p.y(), hero.getMovementStrategy().getName());
        System.out.println("1. Идти пешком");
        System.out.println("2. Ехать на лошади");
        System.out.println("3. Лететь");
        System.out.println("4. Переместить героя");
        System.out.println("0. Выход");
        System.out.print("Ваш выбор: ");
    }

    private static void changeStrategy(Hero hero, MovementStrategy strategy) {
        hero.setMovementStrategy(strategy);
        System.out.println("Способ перемещения изменён: " + strategy.getName());
    }

    private static void moveHero(Hero hero, Scanner scanner) {
        System.out.print("Введите координаты цели через пробел (x y): ");
        if (!scanner.hasNextLine()) {
            return;
        }
        String[] parts = scanner.nextLine().trim().replace(',', '.').split("\\s+");
        if (parts.length != 2) {
            System.out.println("Нужно ввести ровно два числа.");
            return;
        }
        try {
            Point destination = new Point(Double.parseDouble(parts[0]), Double.parseDouble(parts[1]));
            System.out.println(hero.move(destination));
        } catch (NumberFormatException e) {
            System.out.println("Координаты должны быть числами.");
        }
    }
}