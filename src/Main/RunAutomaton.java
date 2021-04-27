package Main;

import Automaton.*;
import java.util.Scanner;
import java.util.ArrayList;

public abstract class RunAutomaton {
    public static void main(String[] args) {
        int run = 1; // starting condition
        ArrayList<String> AutomatonList = new ArrayList<>(); // list of all automatons
        fillAutomatonList(AutomatonList);
        displayMenu();
        Scanner input = new Scanner(System.in);
        int choice;
        String textfile = "text/D3-1.txt";
        while (run == 1) {    
            do {
                System.out.print("Please select an automaton by pressing a number between 1 to 44 : ");
                choice = input.nextInt();
            } while (choice < 1 || choice > 44);

            textfile = selectAutomaton(choice, textfile);

            Automaton myAutomaton = new Automaton();
            Automaton AFD = new Automaton();
            myAutomaton.readFile(textfile);
            myAutomaton.displayAutomaton();
            AFD = myAutomaton.determinisation(textfile);
            AFD.displayAutomaton();
            System.out.println("Automaton is Asynchronous : "+ AFD.isAsynchronous());
            System.out.println("Automaton is Determinist : "+ AFD.isDeterminist());
            System.out.println("Automaton is Complete : "+ AFD.isComplete());
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

    public static void displayMenu() {
        System.out.println();
        System.out.println("------------------- Welcome -----------------");
        System.out.println();
    }

    public static void fillAutomatonList(ArrayList<String> AutomatonList) {
        AutomatonList.add("text/D3-1.txt");
        AutomatonList.add("text/D3-2.txt");
        AutomatonList.add("text/D3-3.txt");
        AutomatonList.add("text/D3-4.txt");
        AutomatonList.add("text/D3-5.txt");
        AutomatonList.add("text/D3-6.txt");
        AutomatonList.add("text/D3-7.txt");
        AutomatonList.add("text/D3-8.txt");
        AutomatonList.add("text/D3-9.txt");
        AutomatonList.add("text/D3-10.txt");
        AutomatonList.add("text/D3-11.txt");
        AutomatonList.add("text/D3-12.txt");
        AutomatonList.add("text/D3-13.txt");
        AutomatonList.add("text/D3-14.txt");
        AutomatonList.add("text/D3-15.txt");
        AutomatonList.add("text/D3-16.txt");
        AutomatonList.add("text/D3-17.txt");
        AutomatonList.add("text/D3-18.txt");
        AutomatonList.add("text/D3-19.txt");
        AutomatonList.add("text/D3-20.txt");
        AutomatonList.add("text/D3-21.txt");
        AutomatonList.add("text/D3-22.txt");
        AutomatonList.add("text/D3-23.txt");
        AutomatonList.add("text/D3-24.txt");
        AutomatonList.add("text/D3-25.txt");
        AutomatonList.add("text/D3-26.txt");
        AutomatonList.add("text/D3-27.txt");
        AutomatonList.add("text/D3-28.txt");
        AutomatonList.add("text/D3-29.txt");
        AutomatonList.add("text/D3-30.txt");
        AutomatonList.add("text/D3-31.txt");
        AutomatonList.add("text/D3-32.txt");
        AutomatonList.add("text/D3-33.txt");
        AutomatonList.add("text/D3-34.txt");
        AutomatonList.add("text/D3-35.txt");
        AutomatonList.add("text/D3-36.txt");
        AutomatonList.add("text/D3-37.txt");
        AutomatonList.add("text/D3-38.txt");
        AutomatonList.add("text/D3-39.txt");
        AutomatonList.add("text/D3-40.txt");
        AutomatonList.add("text/D3-41.txt");
        AutomatonList.add("text/D3-42.txt");
        AutomatonList.add("text/D3-43.txt");
        AutomatonList.add("text/D3-44.txt");
    }

    public static String selectAutomaton(int choice, String textfile) {
        switch (choice) {
        case 1:
            textfile = "text/D3-1.txt"; //depend of where we're compiling
            break;
        case 2:
            textfile = "text/D3-2.txt"; //depend of where we're compiling
            break;
        case 3:
            textfile = "text/D3-3.txt"; //depend of where we're compiling
            break;
        case 4:
            textfile = "text/D3-4.txt"; //depend of where we're compiling
            break;
        case 5:
            textfile = "text/D3-5.txt"; //depend of where we're compiling
            break;
        case 6:
            textfile = "text/D3-6.txt"; //depend of where we're compiling
            break;
        case 7:
            textfile = "text/D3-7.txt"; //depend of where we're compiling
            break;
        case 8:
            textfile = "text/D3-8.txt"; //depend of where we're compiling
            break;
        case 9:
            textfile = "text/D3-9.txt"; //depend of where we're compiling
            break;
        case 10:
            textfile = "text/D3-10.txt"; //depend of where we're compiling
            break;
        case 11:
            textfile = "text/D3-11.txt"; //depend of where we're compiling
            break;
        case 12:
            textfile = "text/D3-12.txt"; //depend of where we're compiling
            break;
        case 13:
            textfile = "text/D3-13.txt"; //depend of where we're compiling
            break;
        case 14:
            textfile = "text/D3-14.txt"; //depend of where we're compiling
            break;
        case 15:
            textfile = "text/D3-15.txt"; //depend of where we're compiling
            break;
        case 16:
            textfile = "text/D3-16.txt"; //depend of where we're compiling
            break;
        case 17:
            textfile = "text/D3-17.txt"; //depend of where we're compiling
            break;
        case 18:
            textfile = "text/D3-18.txt"; //depend of where we're compiling
            break;
        case 19:
            textfile = "text/D3-19.txt"; //depend of where we're compiling
            break;
        case 20:
            textfile = "text/D3-20.txt"; //depend of where we're compiling
            break;
        case 21:
            textfile = "text/D3-21.txt"; //depend of where we're compiling
            break;
        case 22:
            textfile = "text/D3-12.txt"; //depend of where we're compiling
            break;
        case 23:
            textfile = "text/D3-23.txt"; //depend of where we're compiling
            break;
        case 24:
            textfile = "text/D3-24.txt"; //depend of where we're compiling
            break;
        case 25:
            textfile = "text/D3-25.txt"; //depend of where we're compiling
            break;
        case 26:
            textfile = "text/D3-26.txt"; //depend of where we're compiling
            break;
        case 27:
            textfile = "text/D3-27.txt"; //depend of where we're compiling
            break;
        case 28:
            textfile = "text/D3-28.txt"; //depend of where we're compiling
            break;
        case 29:
            textfile = "text/D3-29.txt"; //depend of where we're compiling
            break;
        case 30:
            textfile = "text/D3-30.txt"; //depend of where we're compiling
            break;
        case 31:
            textfile = "text/D3-31.txt"; //depend of where we're compiling
            break;
        case 32:
            textfile = "text/D3-32.txt"; //depend of where we're compiling
            break;
        case 33:
            textfile = "text/D3-33.txt"; //depend of where we're compiling
            break;
        case 34:
            textfile = "text/D3-34.txt"; //depend of where we're compiling
            break;
        case 35:
            textfile = "text/D3-35.txt"; //depend of where we're compiling
            break;
        case 36:
            textfile = "text/D3-36.txt"; //depend of where we're compiling
            break;
        case 37:
            textfile = "text/D3-37.txt"; //depend of where we're compiling
            break;
        case 38:
            textfile = "text/D3-38.txt"; //depend of where we're compiling
            break;
        case 39:
            textfile = "text/D3-39.txt"; //depend of where we're compiling
            break;
        case 40:
            textfile = "text/D3-40.txt"; //depend of where we're compiling
            break;
        case 41:
            textfile = "text/D3-41.txt"; //depend of where we're compiling
            break;
        case 42:
            textfile = "text/D3-42.txt"; //depend of where we're compiling
            break;
        case 43:
            textfile = "text/D3-43.txt"; //depend of where we're compiling
            break;
        case 44:
            textfile = "text/D3-42.txt"; //depend of where we're compiling
            break;
        default:
            System.out.print("An error occured with the choice :/");
            break;
        }
        return textfile;
    }

    
}
