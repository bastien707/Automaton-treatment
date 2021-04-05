package Automaton;

import org.w3c.dom.Text;

import StatePackage.*;
import TransitionPackage.*;

public class automaton {
    private int symbol; // nbr of symbols, e.g if equal 3 -> a,b,c
    private int stateNumber; // number of States
    private State iStateArray[]; // Array of Initial State(s)
    private State fStateArray[]; // Array of Final State(s)
    private int transitionNumber; // Number of transitions
    private Transition transitionArray[]; // Array of transitions

    public automaton(final int symbol, final int stateNumber, final State iStateArray[], final State fStateArray[],
            final int transitionNumber, final Transition transitionArray[]) 
    {
        this.symbol = symbol;
        this.stateNumber = stateNumber;
        this.iStateArray = iStateArray;
        this.fStateArray = fStateArray;
        this.transitionNumber = transitionNumber;
        this.transitionArray = transitionArray;
    }
    
    public int recoverSymbol(){ //return number of symbol from text file
        return 0;
    }
    public static void main(String argvs[]){
        System.out.println("test");
    }
}
