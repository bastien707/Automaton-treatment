package Automaton;

import StatePackage.*;
import TransitionPackage.*;

//Array
import java.util.ArrayList;
//ReadFile
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Set;

public class Automaton {
    private int symbol; // nbr of symbols, e.g if equal 3 -> a,b,c
    private int stateNumber; // number of States
    private ArrayList<State> stateList;
    private int transitionNumber; // Number of transitions
    private ArrayList<Transition> transitionList; // Array of transitions

    // constructors
    public Automaton(final int symbol, final int stateNumber, final ArrayList<State> stateList,
            final int transitionNumber, final ArrayList<Transition> transitionList) {
        this.symbol = symbol;
        this.stateNumber = stateNumber;
        this.stateList = new ArrayList<>();
        this.transitionNumber = transitionNumber;
        this.transitionList = new ArrayList<>();
    }

    public Automaton() {
    }

    // setters
    public void setSymbol(String line) {
        this.symbol = Integer.parseInt(line);
    }

    public void setStateNumber(String line) {
        this.stateNumber = Integer.parseInt(line);
    }

    public void setStateList(int stateNumber) {
        stateList = new ArrayList<>();
        for (int i = 0; i < stateNumber; i++) {
            ArrayList<Transition> ItsTransitions = new ArrayList<>();
            State state = new State(i, false, false, ItsTransitions); // create a state with its index
            stateList.add(i, state);
        }
    }

    public void setTransitionNumber(String line) {
        this.transitionNumber = Integer.parseInt(line);
    }

    public void setTransitionList() {
        transitionList = new ArrayList<>();
    }


    // getters
    public int getSymbol() {
        return this.symbol;
    }

    public int getStateNumber() {
        return this.stateNumber;
    }

    public ArrayList<State> getStateList() {
        return this.stateList;
    }

    public int getTransitionNumber(){
        return this.transitionNumber;
    }

    public ArrayList<Transition> getTransitionList() {
        return this.transitionList;
    }

    // methods

    public void implementIs(String line) {
        char[] array = line.toCharArray();
        int numOfIs = Character.getNumericValue(array[0]); // number of initial states
        if (numOfIs > 0) {
            for (int i = 2; i <= array.length; i += 2) { // we skip the odd char that are space char. and start at i=2
                                                         // because it's the first initial state
                int numState = Character.getNumericValue(array[i]); // we recover the int in the array
                getStateList().get(numState).setIsInitial(true); // the int is the index of the state, so we change his
                                                                 // state.
            }
        } else {
            System.out.print("There is no initial state !");
        }
    }

    public void implementFs(String line) {
        char[] array = line.toCharArray();
        int numOfFs = Character.getNumericValue(array[0]); // number of final states
        if (numOfFs > 0) {
            for (int i = 2; i <= array.length; i += 2) { // we skip the odd cells that are space char. and start at i=2
                                                         // because it's the first final state
                int numState = Character.getNumericValue(array[i]); // we recover the int in the array
                getStateList().get(numState).setIsTerminal(true); // the int is the index of the state, so we change his
                                                                 // state.
            }
        } else {
            System.out.print("There is no final state !");
        }
    }

    public void implementTransition(String line){
        char[] array = line.toCharArray();
        
        //recover char value into int. entryS and arrivalS are the index of Entry en Arrival states
        int entryS= Character.getNumericValue(array[0]);
        int arrivalS = Character.getNumericValue(array[2]);

        Transition newTransition = new Transition(getStateList().get(entryS), array[1], getStateList().get(arrivalS));
        transitionList.add(newTransition);
        stateList.get(entryS).getItsTransitions().add(newTransition); //we implement at the same time itsTransitions list of every state
    }

    public void displayAutomate(){
        System.out.println(this.symbol + "\n" + this.stateNumber);
        System.out.println(this.stateList);
        System.out.println(this.transitionNumber);
        System.out.println(this.transitionList);
    }

    public void readFile(String filename) { // read an automaton text file
        try {
            Scanner scanner = new Scanner(new File(filename)); // recover file
            int lineIndex = 0;
            while (scanner.hasNextLine()) { // loop until EOF
                String line = scanner.nextLine(); // recover a line with a String
                if (lineIndex == 0) { // if line 1 then...
                    setSymbol(line); // fill Symbol
                }
                if (lineIndex == 1) { // if line 2 then...
                    setStateNumber(line); // fill StateNumber
                    setStateList(getStateNumber()); // create all states with default value false.
                }
                if (lineIndex == 2) {
                    implementIs(line); // implement initial States
                }
                if (lineIndex == 3) {
                    implementFs(line); //implement Final states
                }
                if (lineIndex == 4) {
                    setTransitionNumber(line);
                    setTransitionList(); // so we create a new transition list (that is empty)
                }
                if (lineIndex >= 5) {
                    implementTransition(line); //implement transition and List of Transitions from a State (itsTransitions)
                }
                lineIndex++;
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void main(String argvs[]) {
        Automaton myAutomaton = new Automaton();
        myAutomaton.readFile("text/automate1.txt");
        myAutomaton.displayAutomate();
        //System.out.println(myAutomaton.stateList.get(0).getItsTransitions().get(0).getEntryState().getItsTransitions().get(1).getArrivalState());
    }
}
