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
        Scanner kb = new Scanner(System.in);
        int choice;
        int option;
        String textfile = "text/D3-1.txt"; //default

        while (run == 1) {
            do {
                System.out.print("Please select an automaton by pressing a number between 1 to 44 : ");
                choice = input.nextInt();
            } while (choice < 1 || choice > 44);

            textfile = selectAutomaton(choice, textfile);
            Scanner input1 = new Scanner(System.in);
            do {
                Automaton AF = new Automaton();

                AF.readFile(textfile);
                option = displayOptions(AF, input1);
                switch (option) {
                    case 1:
                        treatAF(AF, choice, kb);
                        break;
                    case 2:
                        treatAFDC(AF, choice, kb, textfile);
                        break;
                    case 3:
                        treatAFDCM(AF, choice, kb, textfile);
                        break;
                    case 4:
                        treatAFS(AF, textfile, kb);
                        break;
                    case 5:
                        treatComplementary(AF, choice, kb, textfile);
                        break;
                    case 6:
                        Scanner wordInput = new Scanner(System.in);
                        treatReadWord(AF, choice, kb, textfile, wordInput);
                        break;
                    case 7:
                        Scanner complementarywordInput = new Scanner(System.in);
                        treatReadComplementaryWord(AF, choice, kb, textfile, complementarywordInput);
                    default:
                        break;
                }
            } while (option != 9 && option != 8);

            if (option == 9) {
                do {
                    System.out.println("Are you sure to leave the program ? (1 = Yes, 2 = No)");
                    choice = input.nextInt();
                    if (choice == 1) {
                        displayGoodbye();
                        run = 0;
                    }
                } while (choice < 1 || choice > 2);
            }
        }
        kb.close();
        input.close();
    }

    public static void treatAF(Automaton AF,int choice, Scanner kb){
        System.out.println("------------------- Automaton " + choice + " selected -----------------");
        AF.displayAutomaton();
        System.out.println("------------------- Press ENTER to continue -----------------");
        kb.nextLine();
    }

    public static void treatAFDC(Automaton AF,int choice, Scanner kb, String textfile){
        Automaton AFDC = new Automaton();
        Automaton AFD = new Automaton();
        if (!AF.isAsynchronous()) {
            if (AF.isDeterminist()) {
                AFDC = AF.completion();
            } else {
                AFD = AF.determinisation(textfile);
                AFDC = AFD.completion();
            }
            System.out.println("------------------- AFDC -----------------");
            AFDC.displayAutomaton();
            System.out.println("------------------- Press ENTER to continue -----------------");
            kb.nextLine();
        } else if (AF.isAsynchronous()) {
            System.out.println("We're working on asychronous automaton... please try again later.");
            System.out.println("------------------- Press ENTER to continue -----------------");
            kb.nextLine();
        }
    }

    public static void treatAFDCM(Automaton AF,int choice, Scanner kb, String textfile){
        Automaton AFDCM = new Automaton();
        Automaton AFDC = new Automaton();
        Automaton AFD = new Automaton();
        if (!AF.isAsynchronous()) {
            if (AF.isDeterminist()) {
                AFDC = AF.completion();
            } else {
                AFD = AF.determinisation(textfile);
                AFDC = AFD.completion();
            }
            System.out.println("------------------- AFDCM -----------------");
            AFDCM = AFDC.minimizing();
            AFDCM.displayAutomaton();
            System.out.println("------------------- Press ENTER to continue -----------------");
            kb.nextLine();
        } else if (AF.isAsynchronous()) {
            System.out.println("We're working on asychronous automaton... please try again later.");
            System.out.println("------------------- Press ENTER to continue -----------------");
            kb.nextLine();
        }
    }

    public static void treatAFS(Automaton AF, String textfile, Scanner kb){
        Automaton AFS = new Automaton();
        System.out.println("------------------- AFS -----------------");
        AFS = AF.Standardisation(textfile);
        AFS.displayAutomaton();
        System.out.println("------------------- Press ENTER to continue -----------------");
        kb.nextLine();
    }

    public static void treatComplementary(Automaton AF,int choice, Scanner kb, String textfile){
        Automaton AFDCM = new Automaton();
        Automaton AFDC = new Automaton();
        Automaton AFD = new Automaton();
        if (!AF.isAsynchronous()) {
            if (AF.isDeterminist()) {
                AFDC = AF.completion();
            } else {
                AFD = AF.determinisation(textfile);
                AFDC = AFD.completion();
            }
            System.out.println("------------------- Complementary -----------------");
            AFDCM = AFDC.minimizing();
            AFDCM.switch_complementary_language();
            AFDCM.displayAutomaton();
            System.out.println("------------------- Press ENTER to continue -----------------");
            kb.nextLine();
        } else if (AF.isAsynchronous()) {
            System.out.println("We're working on asynchronous automaton... please try again later.");
            System.out.println("------------------- Press ENTER to continue -----------------");
            kb.nextLine();
        }
    }

    public static void treatReadWord(Automaton AF,int choice, Scanner kb, String textfile, Scanner wordInput){
        Automaton AFDCM = new Automaton();
        Automaton AFDC = new Automaton();
        Automaton AFD = new Automaton();
        if (!AF.isAsynchronous()) {
            if (AF.isDeterminist()) {
                AFDC = AF.completion();
            } else {
                AFD = AF.determinisation(textfile);
                AFDC = AFD.completion();
            }
            System.out.println("------------------- Reading a Word -----------------");
            AFDCM = AFDC.minimizing();
            int stop = 0;
            do{
                String word="";
                System.out.println("Please type a word");
                word = wordInput.nextLine();
                System.out.println("------------------- Reading "+word+" -----------------");
                System.out.println("recognize : "+AFDCM.recognize_word_automaton_determinist(word));
                
                System.out.println("Do you want to type another word ? (1 = Yes, 2 = No)");
                stop = kb.nextInt();
            }while(stop <= 1);

            System.out.println("------------------- Press ENTER to continue -----------------");
            kb.nextLine();
        } else if (AF.isAsynchronous()) {
            System.out.println("We're working on asychronous automaton... please try again later.");
            System.out.println("------------------- Press ENTER to continue -----------------");
            kb.nextLine();
        }
    }

    public static void treatReadComplementaryWord(Automaton AF,int choice, Scanner kb, String textfile, Scanner wordInput){
        Automaton AFDCM = new Automaton();
        Automaton AFDC = new Automaton();
        Automaton AFD = new Automaton();
        if (!AF.isAsynchronous()) {
            if (AF.isDeterminist()) {
                AFDC = AF.completion();
            } else {
                AFD = AF.determinisation(textfile);
                AFDC = AFD.completion();
            }
            System.out.println("------------------- Read Complementary Word -----------------");
            AFDCM = AFDC.minimizing();
            AFDCM.switch_complementary_language();
            int stop = 0;
            do{
                String word="";
                System.out.println("Please type a word");
                word = wordInput.nextLine();
                System.out.println("------------------- Reading "+word+" -----------------");
                System.out.println("Recognize : "+AFDCM.recognize_word_automaton_determinist(word));
                
                System.out.println("Do you want to type another word ? (1 = Yes, 2 = No)");
                stop = kb.nextInt();
            }while(stop <= 1);
            
            System.out.println("------------------- Press ENTER to continue -----------------");
            kb.nextLine();
        } else if (AF.isAsynchronous()) {
            System.out.println("We're working on asynchronous automaton... please try again later.");
            System.out.println("------------------- Press ENTER to continue -----------------");
            kb.nextLine();
        }
    }

    public static int displayOptions(Automaton A, Scanner input1) {
        int selected;

        do {
            System.out.println("What do you want to do ?");
            System.out.println("(1) Show me AF.");
            System.out.println("(2) Show me AFDC.");
            System.out.println("(3) Show me AFDCM.");
            System.out.println("(4) Show me AFS.");
            System.out.println("(5) Show me complementary.");
            System.out.println("(6) I want to read a word.");
            System.out.println("(7) I want to read complementary word.");
            System.out.println("(8) Select another automaton.");
            System.out.println("(9) Quit.");

            selected = input1.nextInt();
        } while (selected < 1 || selected > 9);
        return selected;
    }

    public static void displayMenu() {
        System.out.println();
        System.out.println("------------------- Welcome -----------------");
        System.out.println();
    }

    public static void displayGoodbye() {
        System.out.println();
        System.out.println("------------------- Goodbye ! -----------------");
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
                textfile = "text/D3-1.txt"; // depend of where we're compiling
                break;
            case 2:
                textfile = "text/D3-2.txt"; // depend of where we're compiling
                break;
            case 3:
                textfile = "text/D3-3.txt"; // depend of where we're compiling
                break;
            case 4:
                textfile = "text/D3-4.txt"; // depend of where we're compiling
                break;
            case 5:
                textfile = "text/D3-5.txt"; // depend of where we're compiling
                break;
            case 6:
                textfile = "text/D3-6.txt"; // depend of where we're compiling
                break;
            case 7:
                textfile = "text/D3-7.txt"; // depend of where we're compiling
                break;
            case 8:
                textfile = "text/D3-8.txt"; // depend of where we're compiling
                break;
            case 9:
                textfile = "text/D3-9.txt"; // depend of where we're compiling
                break;
            case 10:
                textfile = "text/D3-10.txt"; // depend of where we're compiling
                break;
            case 11:
                textfile = "text/D3-11.txt"; // depend of where we're compiling
                break;
            case 12:
                textfile = "text/D3-12.txt"; // depend of where we're compiling
                break;
            case 13:
                textfile = "text/D3-13.txt"; // depend of where we're compiling
                break;
            case 14:
                textfile = "text/D3-14.txt"; // depend of where we're compiling
                break;
            case 15:
                textfile = "text/D3-15.txt"; // depend of where we're compiling
                break;
            case 16:
                textfile = "text/D3-16.txt"; // depend of where we're compiling
                break;
            case 17:
                textfile = "text/D3-17.txt"; // depend of where we're compiling
                break;
            case 18:
                textfile = "text/D3-18.txt"; // depend of where we're compiling
                break;
            case 19:
                textfile = "text/D3-19.txt"; // depend of where we're compiling
                break;
            case 20:
                textfile = "text/D3-20.txt"; // depend of where we're compiling
                break;
            case 21:
                textfile = "text/D3-21.txt"; // depend of where we're compiling
                break;
            case 22:
                textfile = "text/D3-12.txt"; // depend of where we're compiling
                break;
            case 23:
                textfile = "text/D3-23.txt"; // depend of where we're compiling
                break;
            case 24:
                textfile = "text/D3-24.txt"; // depend of where we're compiling
                break;
            case 25:
                textfile = "text/D3-25.txt"; // depend of where we're compiling
                break;
            case 26:
                textfile = "text/D3-26.txt"; // depend of where we're compiling
                break;
            case 27:
                textfile = "text/D3-27.txt"; // depend of where we're compiling
                break;
            case 28:
                textfile = "text/D3-28.txt"; // depend of where we're compiling
                break;
            case 29:
                textfile = "text/D3-29.txt"; // depend of where we're compiling
                break;
            case 30:
                textfile = "text/D3-30.txt"; // depend of where we're compiling
                break;
            case 31:
                textfile = "text/D3-31.txt"; // depend of where we're compiling
                break;
            case 32:
                textfile = "text/D3-32.txt"; // depend of where we're compiling
                break;
            case 33:
                textfile = "text/D3-33.txt"; // depend of where we're compiling
                break;
            case 34:
                textfile = "text/D3-34.txt"; // depend of where we're compiling
                break;
            case 35:
                textfile = "text/D3-35.txt"; // depend of where we're compiling
                break;
            case 36:
                textfile = "text/D3-36.txt"; // depend of where we're compiling
                break;
            case 37:
                textfile = "text/D3-37.txt"; // depend of where we're compiling
                break;
            case 38:
                textfile = "text/D3-38.txt"; // depend of where we're compiling
                break;
            case 39:
                textfile = "text/D3-39.txt"; // depend of where we're compiling
                break;
            case 40:
                textfile = "text/D3-40.txt"; // depend of where we're compiling
                break;
            case 41:
                textfile = "text/D3-41.txt"; // depend of where we're compiling
                break;
            case 42:
                textfile = "text/D3-42.txt"; // depend of where we're compiling
                break;
            case 43:
                textfile = "text/D3-43.txt"; // depend of where we're compiling
                break;
            case 44:
                textfile = "text/D3-42.txt"; // depend of where we're compiling
                break;
            default:
                System.out.print("An error occured with the choice :/");
                break;
        }
        return textfile;
    }

}
