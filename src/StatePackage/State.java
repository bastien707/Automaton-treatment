package StatePackage;

public class State {
    private int index; // the reference of the state
    private boolean isTerminal; // 0 = not terminal, 1 = terminal
    private boolean isInitial; // 0 = not initial, 1 = initial

    public State(int index, boolean isInitial, boolean isTerminal) {
        this.index = index;
        this.isInitial = isInitial;
        this.isTerminal = isTerminal;
    }

    public void printState(){
        System.out.println("index : "+ index +"| isInitial : "+isInitial+"| isTerminal :"+isTerminal);
    }

    @Override
    public String toString() {
        return "index : "+ index +"| isInitial : "+isInitial+"| isTerminal :"+isTerminal;
    }
    public static void main(String argvs[]){
        State state1 = new State(0,true,false);
        state1.printState();
    }
}
