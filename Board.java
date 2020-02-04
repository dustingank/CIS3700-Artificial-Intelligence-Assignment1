/*
Name: Yizhou Wang
ID: 1013411
Board is subclass of ObjectPlus
*/


import java.util.*;
import java.io.*;

public class Board extends ObjectPlus {

    int boardStatus[][];
    int goalStatus[][];
    int boradValue;
    String action;

    public Board (String firstInput, String secondInput){  // initialize all the variable at the start
        setBoardStatus(firstInput, secondInput);
        boradValue = 0;
        action = null;
    }

    public Board(int[][] current, int[][] goal, String move) { // initialize all the variable during the search, well evaluate the board value
        this.boardStatus = new int[3][3];
        this.goalStatus = new int[3][3];

        boardStatus = deepCopy(current);
        goalStatus = deepCopy(goal);

        action = move;
        boradValue = searchStrategy(boardStatus, goalStatus, move);

       /* for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                this.boardStatus[i][j] = current[i][j];
                this.goalStatus[i][j] = goal[i][j];
            }
        } */
    }

    public void setBoardStatus (String boardStatus, String goalStatus) {
        this.boardStatus = convertStringArrayto2DArray(boardStatus, boardStatus.length());
        this.goalStatus = convertStringArrayto2DArray(goalStatus, goalStatus.length());
    }

    public int[][] getGoalStatus () { // geter for this class: return goal status
        return goalStatus;
    }

    public int[][] getBoardStatus () { // geter for this class: return current board status
        return boardStatus;
    }

    public int getBoardValue () {
        return boradValue;
    }

    private int[][] convertStringArrayto2DArray (String statusString, int size) {
        int[][] toBeReturn = new int[3][3];
        String[] stringArray = statusString.split("");
        int count = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                toBeReturn[i][j] = Integer.parseInt(stringArray[count]);
                count++;
            }
        }
        return toBeReturn;
    }

    public boolean checkIfGoal () { // check is the goal state reach or not
        if (Arrays.deepEquals(this.boardStatus, this.goalStatus)) {
            return true;
        }
        return false;
    }

    public ArrayList<String> checkMove (Board toBeDetermind) { // check which direction is available 
        //L: left; R: Right; U: Up; D: Down;
        ArrayList<String> legalMoves = new ArrayList<String>();
        int[][] tempBoard = toBeDetermind.getBoardStatus();
        int[] holeIndex = toBeDetermind.findHole(tempBoard);
        
        legalMoves.add("L");
        legalMoves.add("R");
        legalMoves.add("U");
        legalMoves.add("D");

        //System.out.println(tempBoard[0][1]);
        if (holeIndex[0] == 0) {
            legalMoves.remove("U");
        } else if (holeIndex[0] == 2) {
            legalMoves.remove("D");
        }

        if (holeIndex[1] == 0) {
            legalMoves.remove("L");
        } else if (holeIndex[1] == 2) {
            legalMoves.remove("R");
        }
       
        return legalMoves;
    }

    public LinkedList<Board> makeSuccessors (Board originalBoard, ArrayList<String> vaildMoves) {
        int[][] newBoardArray = deepCopy(originalBoard.getBoardStatus());
        int[][] tempBoardArray = deepCopy(originalBoard.getBoardStatus());
        int[][] goalBoardArray = deepCopy(originalBoard.getGoalStatus());
        int count = 0;

        int[] holeIndex = originalBoard.findHole(tempBoardArray);
        LinkedList<Board> toBeReturn = new LinkedList<Board>();

        //System.out.println("-------------Start---------------");
        for(String s: vaildMoves) {

            if (s.equals("L")) {
                newBoardArray = swap(tempBoardArray,holeIndex[0],holeIndex[1], holeIndex[0], (holeIndex[1] - 1));
            } else if (s.equals("R")) {
                newBoardArray = swap(tempBoardArray,holeIndex[0],holeIndex[1], holeIndex[0], (holeIndex[1] + 1));
            } else if (s.equals("U")) {
                newBoardArray = swap(tempBoardArray,holeIndex[0],holeIndex[1], (holeIndex[0] - 1), holeIndex[1]);
            } else if (s.equals("D")) {
                newBoardArray = swap(tempBoardArray,holeIndex[0],holeIndex[1], (holeIndex[0] + 1),holeIndex[1]);
            }


            //showBoard(newBoard);
            Board newBoard = new Board(newBoardArray, goalBoardArray, s);

            //newBoard.show();
            //System.out.println();

            toBeReturn.add(newBoard);
            newBoardArray = deepCopy(originalBoard.getBoardStatus());  // initial the newBoard 
            tempBoardArray = deepCopy(originalBoard.getBoardStatus()); // initial the tempBoard
            //System.out.println("-------------After " + s + "---------------");
        }

        return toBeReturn;
    }

    private static int[][] deepCopy(int[][] original) {  
        if (original.length < 1) {
            return null;
        }
    
        final int[][] result = new int[original.length][];
        for (int i = 0; i < original.length; i++) {
            result[i] = Arrays.copyOf(original[i], original[i].length);
        }
        return result;
    } 

    private void showBoard(int[][] board) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print(board[i][j]);
            }
            System.out.println("");
        }
    }

    private int[][] swap (int[][] board,int firstCol, int firstRow, int secondCol, int secondRow) {

        int temp = board[firstCol][firstRow];
        board[firstCol][firstRow] = board[secondCol][secondRow];
        board[secondCol][secondRow] = temp;

        return board;
    }

    private int[] findHole (int toBeSearch[][]) { // locate the index of the empty box
       int[] index = new int[2];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (toBeSearch[i][j] == 0) {
                    index[0] = i;
                    index[1] = j;
                    return index;
                }
            }
        }

        return index;
    }

    private int searchStrategy(int[][] current, int[][] goal, String move) {
        int toBeReturn = 0;
    
        toBeReturn += algoManhattan(current, goal);

       
       // showBoard(current);
       // System.out.println("--------------------------------------------");
       // showBoard(goal);
       // System.out.println("Weight: " + toBeReturn);
       // System.out.println("--------------------------------------------");
       // System.out.println();

        return toBeReturn;
    }

    private int algoManhattan(int[][] board, int[][] target) {
        int toBeReturn = 0;

        for (int x = 0; x < 3; x++) {   // loop through board
            for (int y = 0; y < 3; y++) { // loop through board
                if (board[x][y] != 0) {
                    for (int i = 0; i < 3; i++) { // loop through target
                        for (int j = 0; j < 3; j++) { // loop throug target
                            if (board[x][y] == target[i][j]) { // find the goal index
                                toBeReturn += Math.abs(x - i) + Math.abs(y - j); // Manhattan distance
                            }
                        }
                    }
                }
            }
        }
        //System.out.println("Manhattan distance: " + toBeReturn);
        return toBeReturn;
    }


    @Override
    void showPart(int dummy) { 
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print(" " + boardStatus[i][j] + " ");
            }
            System.out.println("");
        }
    } 

    @Override
    void show() { // show the whoel status of the board and the vaule function 
        String actionPrint = "";
        
        for (int i = 0; i < 3; i++) {
            System.out.println("-------------");
            for (int j = 0; j < 3; j++) {
                System.out.print("| " + boardStatus[i][j] + " ");
            }
            System.out.println("|");
        }

        if (action == null) {
            System.out.print("-------------\nThis is Initial State");
        } else {
            if (action.equals("L")) {
                actionPrint = "Left";
            } else if (action.equals("R")) {
                actionPrint = "Right";
            } else if (action.equals("U")) {
                actionPrint = "Up";
            } else if (action.equals("D")) {
                actionPrint = "Down";
            } 
            System.out.print("-------------\nMove Hole to " + actionPrint);
        }
       
    } 



}
