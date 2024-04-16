import java.util.ArrayList;
import java.util.Scanner;
import java.io.PrintWriter;
import java.io.File;
import java.io.FileNotFoundException;

public class Lab13_FileListMaker {
    private static ArrayList<String> list = new ArrayList<>();
    private static boolean needsToBeSaved = false;
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            displayMenu();
            String choice = scanner.nextLine().toUpperCase();

            switch (choice) {
                case "A":
                    addItem(scanner);
                    break;
                case "D":
                    deleteItem(scanner);
                    break;
                case "V":
                    viewList();
                    break;
                case "O":
                    openList(scanner);
                    break;
                case "S":
                    saveList(scanner);
                    break;
                case "C":
                    clearList();
                    break;
                case "Q":
                    quitProgram(scanner);
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void displayMenu() {
        System.out.println("\nMENU:");
        System.out.println("A -Add an item to the list");
        System.out.println("D -Delete an item from the list");
        System.out.println("V -View the list");
        System.out.println("O -Open a list file from disk");
        System.out.println("S -Save the current list file to disk");
        System.out.println("C -Clear the list");
        System.out.println("Q -Quit the program");
        System.out.print("Enter your choice: ");
    }

    private static void addItem(Scanner scanner) {
        System.out.print("Enter item to add: ");
        String item = scanner.nextLine();
        list.add(item);
        needsToBeSaved = true;
    }

    private static void deleteItem(Scanner scanner) {
        if (list.isEmpty()) {
            System.out.println("List is empty.");
            return;
        }
        System.out.print("Enter index to delete: ");
        int index = scanner.nextInt();
        scanner.nextLine(); 
        if (index >= 0 && index < list.size()) {
            list.remove(index);
            needsToBeSaved = true;
        } else {
            System.out.println("Invalid index.");
        }
    }

    private static void viewList() {
        if (list.isEmpty()) {
            System.out.println("List is empty.");
        } else {
            System.out.println("List:");
            for (int i = 0; i < list.size(); i++) {
                System.out.println(i + ": " + list.get(i));
            }
        }
    }

    private static void openList(Scanner scanner) {
        if (needsToBeSaved) {
            System.out.println("You have unsaved changes. Save before opening another list.");
            return;
        }
        System.out.print("Enter filename to open: ");
        String filename = scanner.nextLine();
        try {
            Scanner fileScanner = new Scanner(new File(filename + ".txt"));
            list.clear();
            while (fileScanner.hasNextLine()) {
                list.add(fileScanner.nextLine());
            }
            fileScanner.close();
            needsToBeSaved = false;
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
        }
    }

    private static void saveList(Scanner scanner) {
        if (list.isEmpty()) {
            System.out.println("List is empty. Nothing to save.");
            return;
        }
        System.out.print("Enter filename to save: ");
        String filename = scanner.nextLine();
        try {
            PrintWriter writer = new PrintWriter(filename + ".txt");
            for (String item : list) {
                writer.println(item);
            }
            writer.close();
            needsToBeSaved = false;
        } catch (FileNotFoundException e) {
            System.out.println("Error saving file.");
        }
    }

    private static void clearList() {
        list.clear();
        needsToBeSaved = true;
    }

    private static void quitProgram(Scanner scanner) {
        if (needsToBeSaved) {
            System.out.println("You have unsaved changes. Save before quitting.");
            System.out.print("Do you want to save before quitting? (Y/N): ");
            String choice = scanner.nextLine().toUpperCase();
            if (choice.equals("Y")) {
                saveList(scanner);
            }
        }
    }
}
