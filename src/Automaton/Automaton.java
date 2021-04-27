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
import java.util.HashSet;

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

    public boolean isComplete() {
        if (this.isDeterminist() == true) {
            if (this.symbol * this.stateNumber == this.transitionList.size()) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    //return a state that is equal to an other one from the AFD states.
    public State checkArrivalState(ArrayList<Integer> recoverArrivalStates, char letter){
        for(int i = 0; i < this.getStateList().size(); i++){ // browse statelist size of  AFD
           
            ArrayList<Integer> recoverIndexStates = new ArrayList<>(); //creating new array to recover indexstate  AFD
            char recoverIndexStateChar[] = String.valueOf(this.getStateList().get(i).getIndex()).toCharArray(); //convertion
            
            for(int k = 0; k < String.valueOf(this.getStateList().get(i).getIndex()).length(); k++){
                recoverIndexStates.add(Character.getNumericValue(recoverIndexStateChar[k]));
            }
            //here we have created the recoverIndexStates that is an array containing index of AF int by int like this : [1,2,0] where 120 is an index
            //then we compare the arrival state we had with the new created above.
            //System.out.println("Compare : "+recoverIndexStates+"and" + recoverArrivalStates);
            if(recoverIndexStates.size() == recoverArrivalStates.size()){
                int count = 0;
                for(int l = 0; l < recoverIndexStates.size(); l++){
                    for(int m = 0; m < recoverArrivalStates.size(); m++){
                        if(recoverIndexStates.get(l) == recoverArrivalStates.get(m)){ 
                            count++;
                        }
                    }
                }
                // if count equal to recoverIndexStates size it means that these states are the same 
                // but they could like 130 and the other 301, it still same state.
                if(count == recoverIndexStates.size()){ 
                    return this.getStateList().get(i); // we return the good state wich from the AFD states.
                }
            }
        }
        return null;
}

    //method who merge all initial states to only one
    public State mergeInitialStates(ArrayList<State> InitialStates) { 
        State mergeState = new State();
        mergeState.setItsTransitions();
        mergeState.setIsInitial(true);
        String index = "";
        int count = 0;

        //browse between all initial states.
        for (int i = 0; i < InitialStates.size(); i++) {
            if (InitialStates.get(i).getIndex() == 0) { // resolve impossibility to get int starting with 0
                count++;
            } else {
                index += String.valueOf(InitialStates.get(i).getIndex()); // index is the new index of determinist
                                                                          // automaton
            }
            if (count == 1) { // add state 0 to the end avoiding 0 to get ignore
                index += "0";
            }
            if (InitialStates.get(i).getIsTerminal() == true) {
                mergeState.setIsTerminal(true);
            }
            for (int j = 0; j < InitialStates.get(i).getItsTransitions().size(); j++) {
                // we add all new itsTransitions to the new transitions
                mergeState.getItsTransitions().add(InitialStates.get(i).getItsTransitions().get(j)); 
            }
        }
        
        index = removeOccurrence(index, count);
        System.out.println("[from merge methode] index = "+index);
        mergeState.setIndex(Integer.parseInt(index));
        System.out.println(mergeState);
        return mergeState;
    }

    //static method that remove occurrence of an index.
    public static String removeOccurrence(String index, int zeroCount){
        ArrayList<Integer> indexList = new ArrayList<>();
        char indexChar[] = String.valueOf(index).toCharArray();
                
        for(int k = 0; k < String.valueOf(index).length(); k++){
            indexList.add(Character.getNumericValue(indexChar[k]));
        }
        Set<Integer> mySet = new HashSet<Integer>(indexList);
        ArrayList<Integer> indexList2 = new ArrayList<Integer>(mySet);
        if (zeroCount == 1) { // add state 0 to the end avoiding 0 to get ignore
            indexList2.add(0);
        }
        index = "";
        for(int l = 0; l < indexList2.size(); l++){
            index+= String.valueOf(indexList2.get(l));
        }
        return index;
    }

    //set itsTransitions of an AFD.
    public void setAutomatonItsTransitions(){
        for(int i = 0; i < this.getStateList().size(); i++){
            for(char k = 97; k< this.symbol+97; k++){
                ArrayList<Integer> recoverArrivalStates = new ArrayList<>();
                
                for(int j = 0; j< this.getStateList().get(i).getItsTransitions().size(); j++){
                    if(this.getStateList().get(i).getItsTransitions().get(j).getLetter() == k){
                        recoverArrivalStates.add(this.getStateList().get(i).getItsTransitions().get(j).getArrivalState().getIndex()); //recover arrival state int a char list     
                    }
                        
                }
                //here we remove occurrence, resolve occurrence issue.
                Set<Integer> mySet = new HashSet<Integer>(recoverArrivalStates);
                ArrayList<Integer> recoverArrivalState2 = new ArrayList<Integer>(mySet);
                
                State s1 = new State();
                s1 = this.checkArrivalState(recoverArrivalState2, k);
                //System.out.println("new State s1 : "+s1+" with current letter  : "+k);
                if(s1 != null){
                    Transition t1 = new Transition(this.getStateList().get(i), k, s1 );
                    this.getStateList().get(i).getItsTransitions().add(t1);
                }
            }
        }
    }

    //set transitions of any automaton
    public void setAutomatonTransitions(){
        this.setTransitionList();
        this.setTransitionNumber("0"); 
        for(int i = 0; i < this.stateList.size(); i++){
            for(int j = 0; j < this.getStateList().get(i).getItsTransitions().size() ;j++){
                this.getTransitionList().add(this.getStateList().get(i).getItsTransitions().get(j));
                this.transitionNumber++;
            }
        }
    }

    public void setAutomatonTerminalStates(Automaton AF){
        for (int i = 0; i < AF.getFinalStates().size(); i++){ // browse between terminal states of original AF
            for(int j = 0; j < this.stateNumber; j++){ // browse statelist of AFD
                char recoverStateListIndex[] = String.valueOf(this.getStateList().get(j).getIndex()).toCharArray();//recover an array of the index in order to transform 130 to [1,3,0]
                
                for(int k = 0; k < recoverStateListIndex.length; k++){ // browse between size of [1,3,0]
                    int recoverOneState = Character.getNumericValue(recoverStateListIndex[k]); // convert into int
                    if(recoverOneState == AF.getFinalStates().get(i).getIndex()){ // we check if the terminal index is equal to the current one
                        this.getStateList().get(j).setIsTerminal(true); // if its true we set it as terminal
                    }
                }
            }
        }
    }

    public Automaton determinisation(String textfile) {
        
        if (this.isDeterminist() == true) {
            System.out.println("Already determinist");
            return this;
        } else if (this.isAsynchronous() == true) {
            System.out.println("Your automaton is asynchronous, please use the right method.");
            return this;
        }

        Automaton AFD = new Automaton();
        ArrayList<State> newStateList = new ArrayList<>(); // List with wich we're gonna use for the loop
        AFD.readFile(textfile); // automaton copy

        if (this.getInitialStates().size() == 1) {
            //There is one initial state
            newStateList.add(this.getInitialStates().get(0)); // initial state is the first and unique one.
        } else {
            //There is multiple initial states
            newStateList.add(mergeInitialStates(this.getInitialStates())); // we add all merged initial states in only one state.
        }

        AFD.stateList.clear(); // clear all old states.
        AFD.stateList.add(newStateList.get(0)); // we add the new determinist inital state

        //in this loop we create all AFD states.
        while (newStateList.size() != 0) {
            for (int i = 97; i < this.symbol + 97; i++) {
                State newState = new State(); // create a new state for each letter
                String index = ""; // name of the new state, will be converted into int
                int zeroCount = 0; // 0 counter.
                for (int j = 0; j < newStateList.get(0).getItsTransitions().size(); j++) { // browse all itsTransitions
                                                                                           // to create a new one
                    if(i == newStateList.get(0).getItsTransitions().get(j).getLetter()){
                        // resolve the impossibility to get int starting with 0
                        if (newStateList.get(0).getItsTransitions().get(j).getArrivalState().getIndex() == 0) {
                            zeroCount++;
                        } else 
                            index += String.valueOf(newStateList.get(0).getItsTransitions().get(j).getArrivalState().getIndex());
                    }
                }
                //remove occurrence :
                index = removeOccurrence(index, zeroCount);

                //creating new state
                if(index != ""){
                    int intIndex = Integer.parseInt(index); // converting index to int
                    int isNotinAFD = 0; //counter to check current state is not already in the AFD 
                    for (int k = 0; k < AFD.stateList.size(); k++) {
                        if (AFD.stateList.get(k).getIndex() == intIndex) {
                            isNotinAFD++;
                        }
                    }
                    //if the state doesn't exist, we build it.
                    if(isNotinAFD == 0){
                        newState.setIndex(intIndex);
                        newState.setIsInitial(false);
                        newState.setIsInitial(false);
                        newState.setItsTransitions();
                        
                        // we set the itsTransitions of the new in a do-while loop
                        //those are not the final itsTransitions because they're made with old states but it
                        //is essential for the loop
                        int temp = intIndex;
                        do {
                            // range of number of int -> 124 = 3 int , 13 = 2 int in it etc.
                            for (int m = 0; m < String.valueOf(intIndex).length(); m++) { 
                                for (int n = 0; n < this.stateList.get(temp % 10).getItsTransitions().size(); n++) { //we browse the itsTransitions.
                                    newState.getItsTransitions().add(this.getStateList().get(temp % 10).getItsTransitions().get(n));
                                }
                                temp /= 10;
                            }
                        } while (temp != 0);
                        
                        AFD.stateList.add(newState); // new state added to AFD
                        newStateList.add(newState); // adding it too, to be treated next
                    }
                }
            }
            // remove first treated state 
            newStateList.remove(0);
        }
        //finally we're gonna update the new content of the AFD
        AFD.stateNumber = AFD.getStateList().size(); // set size of statelist
        
        //set terminal states 
        AFD.setAutomatonTerminalStates(this);
        
        //recovering size of each itsTransitions in order to get rid of them at the end
        ArrayList<Integer> sizeItsTransitions = new ArrayList<>();
        for(int i =0; i < AFD.getStateList().size(); i++){
            sizeItsTransitions.add(AFD.getStateList().get(i).getItsTransitions().size());
        }

        //initializing all itsTransitions of new states.
        AFD.setAutomatonItsTransitions();

        //removing old states.
        for(int i = 0; i < AFD.stateList.size(); i++){
            for(int j = 0; j < sizeItsTransitions.get(i); j++){
            AFD.getStateList().get(i).getItsTransitions().remove(0);
            }
        }

        //here we are updating the transition list
        AFD.setAutomatonTransitions();
        return AFD;
    }


    // display

    public void rawDisplay() {
        System.out.println(this.symbol + "\n" + this.stateNumber);
        System.out.println(this.stateList);
        System.out.println(this.transitionNumber);
        System.out.println(this.transitionList);
    }

    public void displayAutomaton() {
        if (this.getInitialStates().size() > 0) {
            System.out.print(ANSI_GREEN + "Initial states : " + ANSI_RESET);
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
            if (this.stateList.get(i).getIsInitial() == true && this.stateList.get(i).getIsTerminal() == true) {
                System.out.print(ANSI_YELLOW + this.stateList.get(i).getIndex() + ANSI_RESET + "            |");
            } else if (this.stateList.get(i).getIsInitial() == true) {
                System.out.print(ANSI_GREEN + this.stateList.get(i).getIndex() + ANSI_RESET + "            |");
            } else if (this.stateList.get(i).getIsTerminal() == true) {
                System.out.print(ANSI_RED + this.stateList.get(i).getIndex() + ANSI_RESET + "            |");
            } else {
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
        myAutomaton.displayTransitionTable();
    }
}
