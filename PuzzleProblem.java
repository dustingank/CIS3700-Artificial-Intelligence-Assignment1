/*
Name: Yizhou Wang
ID: 1013411
PuzzleProblem is subclass of Problem
*/




import java.util.ArrayList;
import java.util.LinkedList;

public class PuzzleProblem extends Problem {
    
    @Override
    LinkedList<Board> getSuccessor(ObjectPlus s) {
        LinkedList<Board> successorList = new LinkedList<Board>();
        ArrayList<String> moves = new ArrayList<String>();

        moves = ((Board)s).checkMove((Board)s);
        successorList = ((Board)s).makeSuccessors((Board)s, moves);

        /*
        System.out.println("--------------Test Start------------------");
        for (Board n: successorList) {
            n.show();
        }
        System.out.println("--------------Test End------------------");
        */
        return successorList;
    }

    @Override
    boolean isGoalState(ObjectPlus s) {
        return ((Board)s).checkIfGoal();
    }


}


