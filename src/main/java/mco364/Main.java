package mco364;

import java.awt.Robot;
import java.util.Arrays;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner kb = new Scanner(System.in);
        String inputOscillator, inputExecutionType;
        boolean b = false;

        do {

            System.out.println("Which of the following Oscillations would you like?"
                    + "\n BLINKER, TOAD, BEACON, PULSAR, PENTADECATHLON");
            inputOscillator = kb.nextLine().toUpperCase().trim();

            for (Board.Oscillator elt : Board.Oscillator.values()) {
                if (inputOscillator.equals(elt.toString())) {
                    b = true;
                }
            }

        } while (b == false);

        b = false;// resets b to be reused in next check

        do {
            System.out.println("How would you like to transition from one state to another?"
                    + "\n Please enter A to automate"
                    + "\n Please enter N to manually go to the next state");
            inputExecutionType = kb.nextLine().toUpperCase().trim();

            if (inputExecutionType.equals("A")) {
                b = true;
            }
            if (inputExecutionType.equals("N")) {
                b = true;
            }
        } while (b == false);

        GameOfLife play = new GameOfLife(Board.Oscillator.valueOf(inputOscillator));

        //play.board.printBoard();
        clearConsole();

        if (inputExecutionType.equals("N")) {
            inputExecutionType = "";
            while (inputExecutionType.equals("")) {
                play.nextState();
                System.out.println("Hit enter to go to the next state or any letter/number followed by enter to quit"
                        + "");
                inputExecutionType = kb.nextLine();
                clearConsole();
            }

        } else {
            for (int i = 0; i < 10; i++) {// generation counter 
                play.nextState();
                System.out.println(i + 1);
                sleep(1000);
                clearConsole();
            }
        }
    }



    public final static void clearConsole() {
        for (int i = 0; i < 100; i++) { // safety net since next code only works on console not Netbeans output
            System.out.println();
        }
        try {
            final String os = System.getProperty("os.name");
            if (os.contains("Windows")) {
                Runtime.getRuntime().exec("cls");
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                Runtime.getRuntime().exec("clear");
            }
        } catch (final Exception e) {
            //  Handle any exceptions.
        }
    }

    private static void sleep(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException ex) {
// ignore
        }
    }
}
