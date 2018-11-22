package hw8;

import hw6.MarvelParser;

import java.util.List;
import java.util.Scanner;

public class CampusPathsVC {
    private static Scanner scanner;

    public static void main(String[] args) throws MarvelParser.MalformedDataException {
        scanner = new Scanner(System.in);
        action("m"); // start
    }

    public static void action(String choice) throws MarvelParser.MalformedDataException {
        while(!choice.equals("q")) {
            if (choice.equals("m")) {
                menu();
            } else if (choice.equals("r")) {
                findPath();
            } else if (choice.equals("b")) {
                printBuildings();
            } else {
                System.out.println("Unknown Option");
            }
            System.out.print("\nEnter an option ('m' to see the menu): ");
            choice = scanner.next();
        }
    }

    public static void menu() {
        System.out.println(
                "Menu:\n"
                        + "\tr to find a route\n"
                        + "\tb to see a list of all buildings\n"
                        + "\tq to quit");
    }

    public static void printBuildings() throws MarvelParser.MalformedDataException {
        List<String> buildingList = CampusPathModel.buildings();
        System.out.println("Buildings:");
        for (String s : buildingList) {
            System.out.println("\t" + s);
        }
    }

    public static void findPath() throws MarvelParser.MalformedDataException {
        System.out.print("Abbreviated name of starting building: ");
        String source = scanner.next();

        System.out.print("Abbreviated name of destination building: ");
        String destination = scanner.next();

        List<String> path = CampusPathModel.findShortestPath(source, destination);

        if (path != null) {
            for (String s : path) {
                System.out.println("\t" + s);
            }
        }
    }
}