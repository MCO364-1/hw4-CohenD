package mco364;

import java.util.ArrayList;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import jdk.nashorn.internal.runtime.arrays.ArrayLikeIterator;

public class GameOfLife {
    Board board;

    public GameOfLife(Board.Oscillator o) {
        board = new Board(o);
    }

    public int neighborCount(int row, int col) {
        int counter = 0;
        for (int i = row - 1; i <= row + 1; i++) { //the rows
            for (int j = col - 1; j <= col + 1; j++) { //the col
                if (i >= 0 && i <= board.getBlnBoard().length - 1
                        && j >= 0 && j <= board.getBlnBoard()[i].length - 1) { //its in bounds
                    if (board.getBlnBoard()[i][j]) { //it is true
                        ++counter;
                    }
                }
            }
        }

        if (board.getBlnBoard()[row][col] == true) {
            --counter;//this avoids counting the cell itself
        }
        return counter;
    }

    public boolean isAliveNextGeneration(int row, int col) {
        int counter = neighborCount(row, col);
        if (counter == 3 || (board.getBlnBoard()[row][col] == true && counter == 2)) {
            return true;
        } else {
            return false;
        }
    }

    public Board getBoard() {
        return board;
    }

    public void nextState() {
        ScheduledThreadPoolExecutor pool = new ScheduledThreadPoolExecutor(10);
        for (int threadNum = 0; threadNum < 25; threadNum++) {
            Thread t = new Thread(new NextState(threadNum, this));
            pool.execute(t);
        }
        pool.shutdown();

        while (!pool.isTerminated()) {
            //purpose is so thread will have time to complete
        }

        board.setBoard(NextState.nextBoard);

        board.printBoard();
    }
}

class NextState implements Runnable {

    static GameOfLife gol;
    static boolean[][] nextBoard;
    private int threadIndex;

    public NextState(int i, GameOfLife g) {
        threadIndex = i;
        gol = g;
        
        if (i == 0) {//only on the first one to avoid overwrite
           nextBoard = new boolean[gol.board.getBlnBoard().length][gol.board.getBlnBoard()[0].length]; 
        }
    }

    @Override
    public void run() {
        if (threadIndex < gol.board.getBlnBoard().length) {

            for (int i = 0; i < gol.board.getBlnBoard()[threadIndex].length ; i++) { //length-1
                nextBoard[threadIndex][i] = gol.isAliveNextGeneration(threadIndex, i);
            }
        }
    }
}

class Board {

    public enum Oscillator {
        BLINKER, TOAD, BEACON, PULSAR, PENTADECATHLON
    };

    private String[] strBoard;
    private boolean[][] blnBoard;

    protected Board(Oscillator o) {
        switch (o) {
            case BLINKER:
                strBoard = new String[]{
                    "00000",
                    "00000",
                    "01110",
                    "00000",
                    "00000"};
                break;
            case TOAD:
                strBoard = new String[]{
                    "000000",
                    "000000",
                    "001110",
                    "011100",
                    "000000",
                    "000000"};
                break;
            case BEACON:
                strBoard = new String[]{
                    "000000",
                    "011000",
                    "010000",
                    "000010",
                    "000110",
                    "000000"};
                break;
            case PULSAR:
                strBoard = new String[]{
                    "00000000000000000",
                    "00000000000000000",
                    "00001110001110000",
                    "00000000000000000",
                    "00100001010000100",
                    "00100001010000100",
                    "00100001010000100",
                    "00001110001110000",
                    "00000000000000000",
                    "00001110001110000",
                    "00100001010000100",
                    "00100001010000100",
                    "00100001010000100",
                    "00000000000000000",
                    "00001110001110000",
                    "00000000000000000",
                    "00000000000000000",};
                break;
            case PENTADECATHLON:
                strBoard = new String[]{
                    "00000000000",
                    "00000000000",
                    "00000000000",
                    "00000000000",
                    "00000100000",
                    "00000100000",
                    "00001010000",
                    "00000100000",
                    "00000100000",
                    "00000100000",
                    "00000100000",
                    "00001010000",
                    "00000100000",
                    "00000100000",
                    "00000000000",
                    "00000000000",
                    "00000000000",
                    "00000000000"};
                break;
        }

        createBoard();
    }

    private void createBoard() {
        blnBoard = new boolean[strBoard.length][strBoard[0].length()];

        for (int row = 0; row < strBoard.length; row++) {
            for (int col = 0; col < strBoard[0].length(); col++) {
                if (strBoard[row].charAt(col) == '1') {
                    blnBoard[row][col] = true;
                }
            }
        }
    }

    public void printBoard() {
        for (boolean[] bs : blnBoard) {
            for (boolean elt : bs) {
                if (elt) {
                    System.out.print("•");
                } else {
                    System.out.print(" ");
                }
            }
            System.out.println("");
        }
    }

    public void setBoard(boolean[][] oldBoard) {
        blnBoard = oldBoard;
    }

    public boolean[][] getBlnBoard() {
        return blnBoard;
    }
}
