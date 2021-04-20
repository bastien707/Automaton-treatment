package TransitionPackage;

import StatePackage.*;
import java.util.ArrayList;

public class Transition {
    private State EntryState; //initial State
    private State ArrivalState; //final State
    char letter; //transition letter

    public Transition(final State EntryState, final char letter, final State ArrivalState){
        this.EntryState = EntryState;
        this.letter = letter;
        this.ArrivalState = ArrivalState;
    }

    //getters

    public State getEntryState() {
        return EntryState;
    }

    public State getArrivalState() {
        return ArrivalState;
    }

    public char getLetter() {
        return letter;
    }


    @Override
    public String toString() {
        return EntryState.getIndex() +""+""+ letter +""+""+ArrivalState.getIndex();
    }

    public static void main(String[] args) {
        ArrayList<Transition> ItsTransitions = new ArrayList<>();
        State Es = new State(0,true,false,ItsTransitions);
        State Fs = new State(1,false,true, ItsTransitions);
        Transition t1 = new Transition(Es,'a',Fs);
        System.out.println(t1);
    }
}


