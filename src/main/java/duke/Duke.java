package duke;


public class Duke {

    public void run() {
        UI.start();
        Parser.parseInput();
    }


    public static void main(String[] args) {
        new Duke().run();
    }
}