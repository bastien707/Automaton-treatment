package Main;
import Automaton.*;

public class RunAutomaton {
    public static void main(String[] args) {
        Automaton myAutomaton = new Automaton();
        String textfile = "text/automate1.txt";
        myAutomaton.readFile(textfile);
        myAutomaton.displayAutomate();
    } 
}


