Yizhou Wang
1013411

1. This folder should have the following files: 
        PuzzleAgent.java
        Board.java
        PuzzleProblem.java
        search.jar (Download form profossor website)
        InitGoalFile
        ReadMe.txt
        makefile
    1.1 Inside makefile:
        Xlint:
	        javac -Xlint -cp .:search.jar *.java
        all:
	        javac -cp .:search.jar *.java
        run:
	        java -cp .:search.jar PuzzleAgent InitGoalFile
        clean:
	        rm -f Lexer.java *.class *~

2. This program will solve 8-puzzle problem with A* search tree using 
Manhattan Distance strategy. After each node insert to the fringe, it will 
sort the LinkedList using bubble sorted strategy. Therefore, the program will 
be really slow when the fringe size reach 2000ish.

3. This program is developed from Mac OS, and also been tested
on the socs server. However, "javac -cp .;search.jar *java" is 
not vaild on both enviornments. Therefore, if the program unable
to start, please try "javac -cp .:search.jar *.java" instead.

4. when run "javac -cp .:search.jar *.java", this warning might occurs:
    javac -cp .:search.jar *.java
    Note: PuzzleAgent.java uses unchecked or unsafe operations.
    Note: Recompile with -Xlint:unchecked for details.
after run with "javac -Xlint -cp .:search.jar *.java", this will occus:
    javac -Xlint -cp .:search.jar *.java
    PuzzleAgent.java:49: warning: [unchecked] unchecked conversion
            LinkedList<Node> solution = super.solution;
                                         ^
        required: LinkedList<Node>
        found:    LinkedList
    1 warning

This is due to in search.jar source code, under SearchAgent class, the profossor
declear "solution" as "LinkedList solution" instead "LinkedList<Node> soltuion".
Therefore, not my problem. 

5. showTree method not really work, I tried. :(   

