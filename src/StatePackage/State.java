package StatePackage;

import TransitionPackage.Transition;
import java.util.ArrayList;

public class State {
    private int index; // the reference of the state
    private boolean isTerminal; // 0 = not terminal, 1 = terminal
    private boolean isInitial; // 0 = not initial, 1 = initial
    private ArrayList<Transition> itsTransitions;

    public State(final int index, boolean isInitial, boolean isTerminal, ArrayList<Transition> itsTransitions) {
        this.index = index;
        this.isInitial = isInitial;
        this.isTerminal = isTerminal;
        this.itsTransitions = itsTransitions;
    }

    public State(){}

    public void printState(){
        System.out.println("index : "+ index +"| isInitial : "+isInitial+"| isTerminal :"+isTerminal);
        System.out.println("ItsTransitions : "+itsTransitions);
    }

    @Override
    public String toString() {
        return "index : "+ index +"| isInitial : "+isInitial+"| isTerminal :"+isTerminal+"| ItsTransitions : "+itsTransitions+"\n";
        
    }

    //setters
    public void setIndex(int newIndex) {
        this.index = newIndex;
    }

    public void setIsInitial(boolean newBool) {
        this.isInitial = newBool;
    }

    public void setIsTerminal(boolean newBool) {
        this.isTerminal = newBool;
    }

    public void setItsTransitions(){
        itsTransitions = new ArrayList<>();
    }

    //getters
    public int getIndex() {
        return index;
    }

    public boolean getIsInitial(){
        return isInitial;
    }

    public boolean getIsTerminal(){
        return isTerminal;
    }

    public ArrayList<Transition> getItsTransitions() {
        return this.itsTransitions;
    }
}
