package Automaton;

import StatePackage.*;
import TransitionPackage.*;

//Array
import java.util.ArrayList;
//ReadFile
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

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
        if (Integer.parseInt(line) <= 26) { // check if in the alphabet (a->z)
            this.symbol = Integer.parseInt(line);
        }
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

    public int getTransitionNumber() {
        return this.transitionNumber;
    }

    public ArrayList<Transition> getTransitionList() {
        return this.transitionList;
    }

    public ArrayList<State> getInitialStates() {
        ArrayList<State> InitialStatesList = new ArrayList<>();
        for (int i = 0; i < this.stateList.size(); i++) {
            if (this.stateList.get(i).getIsInitial() == true) {
                InitialStatesList.add(this.stateList.get(i));
            }
        }
        return InitialStatesList;
    }

    public ArrayList<State> getFinalStates() {
        ArrayList<State> FinalStatesList = new ArrayList<>();
        for (int i = 0; i < this.stateList.size(); i++) {
            if (this.stateList.get(i).getIsTerminal() == true) {
                FinalStatesList.add(this.stateList.get(i));
            }
        }
        return FinalStatesList;
    }

    // methods

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_YELLOW = "\u001B[33m";

    public void implementIs(String line) {
        char[] array = line.toCharArray();
        int numOfIs = Character.getNumericValue(array[0]); // number of initial states
        if (numOfIs >= 2) {
            for (int i = 2; i <= array.length; i += 2) { // we skip the odd char that are space char. and start at i=2
                                                         // because it's the first initial state
                int numState = Character.getNumericValue(array[i]); // we recover the int in the array
                stateList.get(numState).setIsInitial(true); // the int is the index of the state, so we change his
                                                            // state.
            }
        } else if (numOfIs == 1) {
            int numState = Character.getNumericValue(array[2]); // we recover the int in the array
            getStateList().get(numState).setIsInitial(true);
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
        } else if (numOfFs == 1) {
            int numState = Character.getNumericValue(array[2]); // we recover the int in the array
            getStateList().get(numState).setIsTerminal(true);
        } else {
            System.out.println("There is no final state !");
        }
    }

    public void implementTransition(String line) {
        char[] array = line.toCharArray();
    
        // recover char value into int. entryS and arrivalS are the index of Entry
        int entryS = Character.getNumericValue(array[0]);
        int arrivalS = Character.getNumericValue(array[2]);
        
        Transition newTransition = new Transition(getStateList().get(entryS), array[1], getStateList().get(arrivalS));
        transitionList.add(newTransition);
        stateList.get(entryS).getItsTransitions().add(newTransition); // we implement at the same time itsTransitions
                                                                      // list of every state
    }

    //Verification

    public boolean isAsynchronous() { // check if an automaton is asynchronous
        int count = 0;
        for (int i = 0; i < this.transitionList.size(); i++) {
            if (this.transitionList.get(i).getLetter() == '*') {
                break;
            } else {
                count++;
            }
        }
        if (count == this.transitionList.size()) {
            return false;
        } else {
            return true;
        }
    }

    public boolean isDeterminist() {
        if (this.getInitialStates().size() == 1) {
            for (int i = 0; i < this.stateList.size(); i++) {
                for (char j = 97; j < this.symbol + 97; j++) {
                    int count = 0;
                    for (int k = 0; k < this.stateList.get(i).getItsTransitions().size(); k++) {
                        if (j == this.stateList.get(i).getItsTransitions().get(k).getLetter()) {
                            count++;
                        }
                        if (count > 1) { // if we have more than 1 transition that goes to 2 differents states
                            return false;
                        }

                    }

                }
            }

        } else if (this.getInitialStates().size() > 1) {
            return false;
        }
        return true;
    }

    public boolean isComplete(){
        if(this.isDeterminist() == true){
            if(this.symbol*this.stateNumber == this.transitionList.size()){ 
                return true;
            }
            else{
                return false;
            }
        }
        else{
            return false;
        }
    }

    //FUNCTIONS
    //Standardisation
    public Automaton Standardisation(String filename){
        System.out.println("ftest: OK");
        //Copy automaton text file
        Automaton AFS = new Automaton();
        AFS.readFile(filename);
        //check if initial state is terminal too
        ArrayList<State> InitialStatesList = new ArrayList<>();
        InitialStatesList = AFS.getInitialStates();
        System.out.println("liste etats initiaux:"+InitialStatesList);
        boolean emptyWord = false;
        for(int i=0; i<InitialStatesList.size();i++){
            if(InitialStatesList.get(i).getIsTerminal() == true){
                emptyWord = true;
            }
        }
        //Create the new initial state : I
        System.out.println("mot vide:"+emptyWord);
        System.out.println("liste etats AFS"+AFS.stateList);
        if(emptyWord == false){
            State StateI = new State();
            //StateI.setIndex(this.stateList.size());
            StateI.setIndex(Character.valueOf('I'));
            StateI.setIsInitial(true);
            StateI.setItsTransitions();
            AFS.stateList.add(StateI);
            AFS.stateNumber++;
        }
        else{
            State StateI = new State();
            //StateI.setIndex(this.stateList.size());
            StateI.setIndex(Character.valueOf('I'));
            StateI.setIsInitial(true);
            StateI.setIsTerminal(true);
            StateI.setItsTransitions();
            AFS.stateList.add(StateI);
            AFS.stateNumber++;
        }
        System.out.println("ftest: add I");
        System.out.println("liste etats AFS"+AFS.stateList);
        //for each initial states, we duplicate there transition to the new state I

        for(int i=0;i<InitialStatesList.size();i++){
            for(int j =0; j<InitialStatesList.get(i).getItsTransitions().size();j++){
                Transition ITransition = new Transition(AFS.stateList.get(this.stateList.size()),InitialStatesList.get(i).getItsTransitions().get(j).getLetter(),InitialStatesList.get(i).getItsTransitions().get(j).getArrivalState());
                AFS.stateList.get(this.stateList.size()).getItsTransitions().add(ITransition);
            }
        }

        //Remove initial condition of the AFS initial state
        for(int i=0;i<AFS.stateList.size();i++){
            if(AFS.stateList.get(i).getIndex() != 73){
                AFS.stateList.get(i).setIsInitial(false);
            }
        }
        //Add State I transitions in Transitions List
        for(int k=0;k<AFS.stateList.get(this.stateList.size()).getItsTransitions().size();k++){
            AFS.transitionList.add(AFS.stateList.get(this.stateList.size()).getItsTransitions().get(k));
            AFS.transitionNumber++;
        }
        return AFS;
    }

    public boolean recognize_word_automaton_determinist(String mot){
        State actual_State = new State();
        for(int i=0;i<this.stateList.size();i++){
            if(this.stateList.get(i).getIsInitial() == true){
                actual_State = this.stateList.get(i);
            }
        }
        
        //etablir un etat courant <- prend etat initial
        /*do
        {
            //prendre la lettre
            //la comparer aux transitions de l etat
            //ajouter les etats suivants possibles a une pile
            //l etat courant prend le premier de la pile

            Conditions
            si lettre = eof -> true
            
        }while(); tq la pile n'est pas vide*/
        /*int i;
        State curant_state = new State();
        curant_state = this.stateList.get(i);
        
        do{

        }while();*/
        return false;
    }

    public void switch_complementary_language(){
        //Check if an automaton is determinist and complete
        if(this.isDeterminist() || this.isComplete() == false){
            return("ERROR: this automaton must be determinist and complete");
        }
        //For each states we reverse the terminal status
        for(int i=0;i<this.stateList.size();i++){
            if(this.stateList.get(i).getIsTerminal() == true){
                this.stateList.get(i).setIsTerminal(false);
            }
            else{
                this.stateList.get(i).setIsTerminal(true);
            }
        }   
    }

    // Display

    public void rawDisplay() {
        System.out.println(this.symbol + "\n" + this.stateNumber);
        System.out.println(this.stateList);
        System.out.println(this.transitionNumber);
        System.out.println(this.transitionList);
    }

    public void displayAutomaton() {
        if (this.getInitialStates().size() > 0) {
            System.out.print(ANSI_GREEN + "Initial states : "+ ANSI_RESET);
            for (int i = 0; i < this.getInitialStates().size(); i++) {
                System.out.print("(" + this.getInitialStates().get(i).getIndex() + ")");
            }
            System.out.println();
        } else {
            System.out.println("There is not initial states.");
        }

        if (this.getFinalStates().size() > 0) {
            System.out.print(ANSI_RED + "Final states : " + ANSI_RESET);
            for (int i = 0; i < this.getFinalStates().size(); i++) {
                System.out.print("(" + this.getFinalStates().get(i).getIndex() + ")");
            }
            System.out.println();
        } else {
            System.out.println("There is not final states.");
        }
        displayTransitionTable();
    }

    public void displayTransitionTable() {
        System.out.println();
        System.out.print("Automaton");
        for (char i = 97; i < (this.symbol) + 97; i++) {
            System.out.print("    |    " + i); // we print all letters.
        }
        System.out.print("    |");
        System.out.println();

        for (int i = 0; i < this.stateList.size(); i++) { // print state by state and line by line
            if(this.stateList.get(i).getIsInitial() == true && this.stateList.get(i).getIsTerminal() == true){
                System.out.print(ANSI_YELLOW+this.stateList.get(i).getIndex() + ANSI_RESET+"            |");
            }
            else if(this.stateList.get(i).getIsInitial() == true){
                System.out.print(ANSI_GREEN+this.stateList.get(i).getIndex() + ANSI_RESET+"            |");
            }
            else if(this.stateList.get(i).getIsTerminal() == true){
                System.out.print(ANSI_RED+this.stateList.get(i).getIndex() + ANSI_RESET+"            |");
            }
            else{
                System.out.print(this.stateList.get(i).getIndex() + "            |");
            }
            for (char j = 97; j < (this.symbol) + 97; j++) { // we go all over symbol alphabet.
                int count = 0;
                for (int k = 0; k < this.stateList.get(i).getItsTransitions().size(); k++) { // we check for each
                                                                                             // itsTransition if the
                                                                                             // letters match with the
                                                                                             // current symbol
                    if (this.stateList.get(i).getItsTransitions().get(k).getLetter() == j) {
                        count++;
                        if (count >= 2) { // display "," to separate each state.
                            System.out.print(",");
                        }
                        System.out.print(this.stateList.get(i).getItsTransitions().get(k).getArrivalState().getIndex());
                    }
                }
                if (count == 0) {
                    System.out.print("-");
                    count = 1;
                }
                switch (count) { // for a dynamic display
                case 0:
                    System.out.print(" ".repeat(9) + "|"); // repeat 9 times a space " "
                    break;
                case 1:
                    System.out.print(" ".repeat(8) + "|");
                    break;
                case 2:
                    System.out.print(" ".repeat(6) + "|");
                    break;
                case 3:
                    System.out.print(" ".repeat(4) + "|");
                    break;
                case 4:
                    System.out.print(" ".repeat(5) + "|");
                    break;
                }

                count = 0;
            }
            System.out.println();
        }
        System.out.println();
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
                    implementFs(line); // implement Final states
                }
                if (lineIndex == 4) {
                    setTransitionNumber(line);
                    setTransitionList(); // so we create a new transition list (that is empty)
                }
                if (lineIndex >= 5) {
                    implementTransition(line); // implement transition and List of Transitions from a State
                                               // (itsTransitions)
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
        myAutomaton.readFile("text/automaton1.txt");
        System.out.println("Avant standardisation");
        myAutomaton.rawDisplay();
        Automaton AFS = new Automaton();
        AFS = myAutomaton.Standardisation("text/automaton1.txt");
        System.out.println("Après standardisation");
        AFS.displayAutomaton();
        System.out.println("AF originel");
        myAutomaton.displayAutomaton();
        System.out.println("Passage Complémentaire");
        myAutomaton.switch_complementary_language();
        myAutomaton.recognize_word_automaton_determinist("abba");

    }
}
