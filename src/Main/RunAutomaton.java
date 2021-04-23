package Main;

import Automaton.*;
import java.util.Scanner;
import java.util.ArrayList;

public abstract class RunAutomaton {

    public static void displayMenu() {
        System.out.println();
        System.out.println("------------------- Welcome -----------------");
        System.out.println();
    }

    public static void fillAutomatonList(ArrayList<String> AutomatonList) {
        AutomatonList.add("text/automaton1.txt");
        AutomatonList.add("text/automaton2.txt");
        AutomatonList.add("text/automaton3.txt");
        AutomatonList.add("text/automaton4.txt");
    }

    public static String selectAutomaton(int choice, String textfile) {
        switch (choice) {
        case 1:
            textfile = "text/automaton1.txt";
            break;

        case 2:
            textfile = "text/automaton2.txt";
            break;

        case 3:
            textfile = "text/automaton3.txt";
            break;

        case 4:
            textfile = "text/automaton4.txt";
            break;

        default:
            System.out.print("An error occured with the choice :/");
            break;
        }
        return textfile;
    }

    public static void main(String[] args) {
        int run = 1; // starting condition
        ArrayList<String> AutomatonList = new ArrayList<>(); // list of all automatons
        fillAutomatonList(AutomatonList);
        displayMenu();
        Scanner input = new Scanner(System.in);
        int choice;
        String textfile = "text/automaton1.txt";
        while (run == 1) {
            do {
                System.out.print("Please select an automaton by pressing a number between 1 to 4 : ");
                choice = input.nextInt();
            } while (choice < 1 || choice > 4);

            textfile = selectAutomaton(choice, textfile);

            Automaton myAutomaton = new Automaton();
            Automaton AFD = new Automaton();
            myAutomaton.readFile(textfile);
            myAutomaton.displayAutomaton();
            AFD = myAutomaton.determinisation(textfile);
            do {
                System.out.println("Do you want to exit the program ? (1 = Yes, 2 = No)");
                choice = input.nextInt();
                if (choice == 1) {
                    run = 0;
                }
            } while (choice < 1 || choice > 2);
        }
        input.close();

    }
}
