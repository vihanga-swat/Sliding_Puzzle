/*
Name - Vihanga Palihakkara
IIT id - 2019770
uow id - w1839048
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class SlidingPuzzle {

    class Point {
        int row_pos;
        int col_pos;
        int weight;
        Point left;
        Point right;
        Point up;
        Point down;

        public Point(int row_pos, int col_pos) {
            this.row_pos = row_pos;
            this.col_pos = col_pos;
        }
    }

    //counting the number of line in the string
    private int numberOfLines(String name) throws FileNotFoundException {
        File file = new File(name);
        Scanner reader = new Scanner(file);
        int rowCount = 0;


        while (reader.hasNextLine()) {
            reader.nextLine();
            rowCount++;
        }
        return rowCount;
    }

    // Reading the string maze from the txt file and create a 2Darray
    public String[][] readFile(String fileName) {

        try {
            File file = new File(fileName);
            Scanner reader = new Scanner(file);

            int lineCount = numberOfLines(fileName);

            String[][] puzzleText = new String[lineCount][lineCount];
            int lineNum = 0;
            while (reader.hasNextLine()) {
                String line = reader.nextLine();
                String[] lineData = line.split("");
                puzzleText[lineNum] = lineData;
                lineNum++;

            }

            return puzzleText;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;

    }

    // Find the starting point of the puzzle
    public int[] getStartPointIndex(String[][] puzzleText, int numOfRows, int numOfCols) {
        int[] index = new int[2];
        for (int row = 0; row < numOfRows; row++) {
            for (int column = 0; column < numOfCols; column++) {
                if (puzzleText[row][column].equals("S")) {
                    puzzleText[row][column] = ".";
                    index[0] = row;
                    index[1] = column;
                    return index;
                }
            }
        }
        return null;
    }

    // Find the end point from the string puzzle
    public int[] getEndPointIndex(String[][] puzzleText, int numOfRows, int numOfCols) {
        int[] index = new int[2];
        for (int row = 0; row < numOfRows; row++) {
            for (int column = 0; column < numOfCols; column++) {
                if (puzzleText[row][column].equals("F")) {
                    puzzleText[row][column] = ".";
                    index[0] = row;
                    index[1] = column;
                    return index;
                }
            }
        }
        return null;
    }

    // Building the puzzel from string array
    public Point[][] buildPuzzle(String[][] puzzleText, int numOfRows, int numOfCols) {
        Point[][] puzzle = new Point[numOfRows][numOfCols];

        for (int row = 0; row < numOfRows; row++) {
            for (int column = 0; column < numOfCols; column++) {
                if (puzzleText[row][column].equals(".")) {
                    Point cur_point = new Point(row, column);
                    if (column - 1 >= 0 && puzzleText[row][column - 1].equals(".")) {
                        cur_point.left = puzzle[row][column - 1];
                        if (puzzle[row][column - 1] != null) {
                            puzzle[row][column - 1].right = cur_point;
                        }
                    }

                    if (column + 1 < numOfCols && puzzleText[row][column + 1].equals(".")) {
                        cur_point.right = puzzle[row][column + 1];
                        if (puzzle[row][column + 1] != null) {
                            puzzle[row][column + 1].left = cur_point;
                        }
                    }

                    if (row - 1 >= 0 && puzzleText[row - 1][column].equals(".")) {
                        cur_point.up = puzzle[row - 1][column];
                        if (puzzle[row - 1][column] != null) {
                            puzzle[row - 1][column].down = cur_point;
                        }
                    }

                    if (row + 1 < numOfRows && puzzleText[row + 1][column].equals(".")) {
                        cur_point.down = puzzle[row + 1][column];
                        if (puzzle[row + 1][column] != null) {
                            puzzle[row + 1][column].up = cur_point;
                        }
                    }
                    puzzle[row][column] = cur_point;
                }
            }
        }
        return puzzle;
    }

    // Do the slidng when choosing a direction
    private Point slide(Point source_point, Point end_point, String direction) {

        source_point.weight = 0;
        Point cur_point = source_point;

        if (direction.equals("left")) {
            while (cur_point.left != null && cur_point != end_point) {
                cur_point = cur_point.left;
                source_point.weight++;
            }
        }
        if (direction.equals("right")) {
            while (cur_point.right != null && cur_point != end_point) {
                cur_point = cur_point.right;
                source_point.weight++;
            }
        }

        if (direction.equals("up")) {
            while (cur_point.up != null && cur_point != end_point) {
                cur_point = cur_point.up;
                source_point.weight++;
            }
        }
        if (direction.equals("down")) {
            while (cur_point.down != null && cur_point != end_point) {
                cur_point = cur_point.down;
                source_point.weight++;
            }
        }
        if (source_point.weight != 0) {
            return cur_point;
        } else {
            return null;
        }
    }

    // back tracking the shortest path
    private void backtrack(Point start, Point current, HashMap<Point, Point> previous) {
        ArrayList<Point> path = new ArrayList<>();
        path.add(current);
        while (current != start) {
            Point temp = previous.get(current);
            path.add(temp);
            current = temp;
        }
        int i = path.size() - 2;
        System.out.println("1. Start at (" + (start.col_pos + 1) + "," + (start.row_pos + 1) + ")");
        while (i >= 0) {
            if (path.get(i).col_pos == path.get(i + 1).col_pos) {
                if (path.get(i).row_pos < path.get(i + 1).row_pos) {
                    System.out.println((path.size() - i) + ". Move up to (" + (path.get(i).col_pos + 1) + "," + (path.get(i).row_pos + 1) + ")");
                } else {
                    System.out.println((path.size() - i) + ". Move down to (" + (path.get(i).col_pos + 1) + "," + (path.get(i).row_pos + 1) + ")");
                }
            } else {
                if (path.get(i).col_pos < path.get(i + 1).col_pos) {
                    System.out.println((path.size() - i) + ". Move left to (" + (path.get(i).col_pos + 1) + "," + (path.get(i).row_pos + 1) + ")");
                } else {
                    System.out.println((path.size() - i) + ". Move right to (" + (path.get(i).col_pos + 1) + "," + (path.get(i).row_pos + 1) + ")");
                }
            }
            i--;
        }
        System.out.println((path.size() - i) + ". Done");
    }

    public void dijkstra(Point start_point, Point end_point) {
        HashMap<Point, Integer> distance = new HashMap<>();
        distance.put(start_point, 0);

        String[] directions = {"left", "right", "down", "up"};

        HashMap<Point, Point> previous = new HashMap<>();

        ArrayList<Point> q = new ArrayList<>();
        q.add(start_point);

        while (q.size() > 0) {
            Point current = q.get(0);
            q.remove(0);

            for (int i = 0; i < 4; i++) {
                Point temp = slide(current, end_point, directions[i]);
                if (temp != null && !distance.containsKey(temp)) {
                    distance.put(temp, distance.get(current) + current.weight);
                    previous.put(temp, current);
                    q.add(temp);
                } else if (temp != null && distance.get(current) + current.weight <= distance.get(temp)) {
                    distance.put(temp, distance.get(current) + current.weight);
                    previous.put(temp, current);
                }
            }

        }
        if (distance.containsKey(end_point)) {
            backtrack(start_point, end_point, previous);
        }
    }
}