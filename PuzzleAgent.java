/*
Name: Yizhou Wang
ID: 1013411
PuzzleAgent is subclass of searchAgent
Puzzle agent is the main of the program

Method to be implement:
showSolution()
showTree()
insertFringe()
 */

import java.util.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class PuzzleAgent extends SearchAgent{
    @Override
    void insertFringe(Node nd, LinkedList<Node>ll) {
        if (ll.isEmpty()) {
            ll.add(nd);
        } else {
            insertFringeSort(nd, ll);
        }

        /*
        System.out.println();
        System.out.println("----------------------Fringe at this time----------------------------");
        for (Node s: ll) {
            Board showBoard1 = (Board)s.getState();
            int weight = showBoard1.getBoardValue();
            int total = weight + s.getDepth();
            System.out.println("    Depth Value: " + s.getDepth() + " Weight Value: " + weight + " Total Value: " + total);
        }
        for (Node ss: ll) {
            Board showBoard = (Board)ss.getState();
            System.out.println();
            showBoard.show();
            System.out.println();
        }
        System.out.println("----------------------END----------------------------");
        */
    }

    @Override
    void showSolution() {
        LinkedList<Node> solution = super.solution;
        
        System.out.println("The Final Solution Path: ");
        
        for (Node n: solution) {
            Board showBoard = (Board)n.getState();
            showBoard.show();
            System.out.println();
        }

    }

    @Override
    void showTree() {
        int count = 0;
        LinkedList<Node> tree = super.tree;

        System.out.println("\n\n---------------------Tree Start-------------------------");
        System.out.println("Search Tree size = " + tree.size() + " nodes");
        for (Node n: tree) {
            Board showBoard = (Board)n.getState();
            showBoard.showPart(1);
            System.out.println("Depth(g): "+ n.getDepth() + " Manhattan Distance(h): " + showBoard.getBoardValue() + " ID: " + count);
            count++;
        }

        System.out.println("---------------------Tree END-------------------------\n");

    }

    public static void main (String argc[]) throws IOException {
        PuzzleAgent newAgent = new PuzzleAgent();
        PuzzleProblem newProblem = new PuzzleProblem();

        String[] fileInfo = new String[] {"null","null"};
        fileInfo = fileParser(argc[0]);

        // change the first fileInfo to 0
        Board initalBoard = new Board(fileInfo[0], fileInfo[1]); 
        Board goalBoard = new Board(fileInfo[1], fileInfo[1]);
        
        newProblem.setInitialState(initalBoard);
        newProblem.setGoalState(goalBoard);

        newAgent.setProblem(newProblem);
        newAgent.search();
        newAgent.showTree();
        newAgent.showSolution();

        //((Board)(newProblem.getGoalState())).show();
        /*
        if (newProblem.isGoalState(newProblem.getInitialState()) == false) {
            System.out.println("Not Goal");
        } 
        if (newProblem.isGoalState(newProblem.getGoalState())) {
            System.out.println("Goal Reach");
        }
        test */
        //newAgent.showSolution();

    }

    

    private static String[] fileParser(String filepath) {  // this function will parse the file to get the initial status and goal status
        String fileContent = "", initialStatus = "", goalStatus = "";
        String[] fileContentArray = new String[] {"null", "null"};
        String[] toBeReturn = new String[] {"null", "null"};


        try { // reading the file
            fileContent = new String(Files.readAllBytes(Paths.get(filepath)), StandardCharsets.US_ASCII);
        } catch (IOException e) {
           System.out.println("Error");
           e.printStackTrace();
           System.exit(1);
    
        }

        fileContentArray = fileContent.split("\\R+");
        initialStatus = fileContentArray[1] + fileContentArray[2] + fileContentArray[3]; // get the initial status
        initialStatus = initialStatus.replaceAll(" ", "");
        
        goalStatus = fileContentArray[5] + fileContentArray[6] + fileContentArray[7];  // get the goal status
        goalStatus = goalStatus.replaceAll(" ", "");
        
        toBeReturn[0] = initialStatus;
        toBeReturn[1] = goalStatus;

        return toBeReturn;
    }
    
    private void insertFringeSort(Node tobeInserted, LinkedList<Node> list){
        list.add(tobeInserted);
        int value = 0, secondValue = 0;
        int depth = 0, secondDepth = 0;
        int size = list.size();

        for(int i = 0; i < size - 1; i++) {
            for (int j = 0; j < size - i - 1; j++) {
                if (getValue(list.get(j)) > getValue(list.get(j + 1))) {
                    list.add(j, list.get(j+1));
                    list.remove(j+2);
                }
            }
        }
    }

    private int getValue (Node nd) {
        int tobeReturn = 0;
        Board temp = (Board)nd.getState();

        tobeReturn = temp.getBoardValue() + nd.getDepth();

        return tobeReturn;
    }


}