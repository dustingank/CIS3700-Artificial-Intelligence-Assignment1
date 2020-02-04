Xlint:
	javac -Xlint -cp .:search.jar *.java
all:
	javac -cp .:search.jar *.java
run:
	java -cp .:search.jar PuzzleAgent InitGoalFile
clean:
	rm -f Lexer.java *.class *~

