/*
Name - Vihanga Palihakkara
IIT id - 2019770
uow id - w1839048
 */

import java.io.File;
import java.io.IOException;
import java.util.*;

public class Main {


    public static void main(String[] args) {

        // change the folder name to run the test cases in example (1)
        // should be changed as "test-cases\\examples (1)\\"
        String filePath = "test-cases/examples_2/";
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the file name in '"+filePath+ "' (eg: puzzle_x.txt): ");
        String fileName = sc.nextLine();


        System.out.println("File Name: " + fileName);
        System.out.println();

        SlidingPuzzle slidingPuzzle = new SlidingPuzzle();


        String[][] puzzleText = slidingPuzzle.readFile(filePath + fileName);
        long start = System.currentTimeMillis();

        int[] startPointIndex = slidingPuzzle.getStartPointIndex(puzzleText, puzzleText[0].length, puzzleText.length);
        int[] endPointIndex = slidingPuzzle.getEndPointIndex(puzzleText, puzzleText[0].length, puzzleText.length);

        SlidingPuzzle.Point[][] puzzle = slidingPuzzle.buildPuzzle(puzzleText, puzzleText[0].length, puzzleText.length);

        SlidingPuzzle.Point startPoint = puzzle[startPointIndex[0]][startPointIndex[1]];
        SlidingPuzzle.Point endPoint = puzzle[endPointIndex[0]][endPointIndex[1]];

        slidingPuzzle.dijkstra(startPoint, endPoint);
        long end = System.currentTimeMillis();
        long elapsedTime = end - start;
        System.out.println("-------------------------------------------------");

        System.out.println("           Time Taken for file (ms)              ");
        System.out.println("-------------------------------------------------");
        System.out.println(String.format("%20s %20s \r\n", fileName, elapsedTime));


    }
}