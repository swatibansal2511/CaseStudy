package com.barrenland;

import com.barrenland.model.Coordinates;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;


import static com.barrenland.util.BarrenConstants.*;

public class BarrenLandAnalysis {

    private static final Logger Logger = LoggerFactory.getLogger(BarrenLandAnalysis.class);

    public static void main(String[] args) {
        String[] input = {"0 292 399 307"};

        findFertileLand(input);


    }

    public static void findFertileLand(String[] rectangleCornersArray) {
        List<Integer> fertileLand = new ArrayList<>();

        List<Integer[]> barrenLandEndPoints = getBarrenLandCoordinates(rectangleCornersArray);

        List<Coordinates> totalBarrenLand = new ArrayList<>();
        for (Integer[] rectangle : barrenLandEndPoints) {
            totalBarrenLand.addAll(findTotalBarrenLandForRectangle(rectangle));
        }

        for (int y = 0; y < TILES_Y; y++) {
            for (int x = 0; x < TILES_X; x++) {
                Coordinates co = new Coordinates(x, y);
                for (Coordinates c : totalBarrenLand) {
                    if (c.getX() == x && c.getY() == y) {
                        co.setIsBarren(true);
                        co.setVisited(true);
                        break;
                    } else {
                        co.setIsBarren(false);
                    }
                }
                grid[x][y] = co;
            }
        }

        fertileLand = checkForUnvisitedAreasAndCountFertileLand(fertileLand, 0, 0);

        Collections.sort(fertileLand);

        StringBuilder result = new StringBuilder();

        if (!fertileLand.isEmpty()) {
            for (Integer land : fertileLand) {
                result.append(land.toString()).append(" ");
            }
        } else {
            result.append("No fertile land available.");
        }
        Logger.info("result {}", result);

    }

    private static List<Integer[]> getBarrenLandCoordinates(String[] rectangleCornersArray) {

        List<Integer[]> rectanglePoints = new ArrayList<>();
        for (int h = 0; h < rectangleCornersArray.length; h++) {
            String[] strRectangleCorner = rectangleCornersArray[h].split(" ");
            Integer[] intRectangleCorner = new Integer[strRectangleCorner.length];
            for (int i = 0; i < strRectangleCorner.length; i++) {
                intRectangleCorner[i] = Integer.parseInt(strRectangleCorner[i]);
            }
            rectanglePoints.add(intRectangleCorner);
        }

        return rectanglePoints;
    }

    private static List<Coordinates> findTotalBarrenLandForRectangle(Integer[] bounds) {
        List<Coordinates> allBarrenLandCoordinates = new ArrayList<>();

        for (int i = bounds[0]; i <= bounds[2]; i++) {
            for (int j = bounds[1]; j <= bounds[3]; j++) {
                Coordinates coordinates = new Coordinates(i, j);
                allBarrenLandCoordinates.add(coordinates);
            }
        }
        return allBarrenLandCoordinates;
    }

    private static List<Integer> checkForUnvisitedAreasAndCountFertileLand(List<Integer> land, int xVal, int yVal) {
        for (int y = yVal; y < TILES_Y; y++) {
            for (int x = xVal; x < TILES_X; x++) {
                Coordinates tile = grid[x][y];
                if (!tile.isVisited()) {
                    int totalFertileArea = floodFill(grid, x, y);
                    land.add(totalFertileArea);
                    checkForUnvisitedAreasAndCountFertileLand(land, x, y);
                }
            }
        }
        return land;

    }

    private static int floodFill(Coordinates[][] grid, int x, int y) {
        int count = 0;

        Stack<Coordinates> stack = new Stack<>();
        stack.push(new Coordinates(x, y));

        while (!stack.isEmpty()) {
            Coordinates c = stack.pop();

            if (isCoordinatesUnvisited(grid, c)) {
                count += 1;
                if (c.getY() - 1 >= 0 && !grid[c.getX()][c.getY() - 1].isVisited()) {
                    stack.push(new Coordinates(c.getX(), c.getY() - 1));
                } else if (c.getY() + 1 < TILES_Y && !grid[c.getX()][c.getY() + 1].isVisited()) {
                    stack.push(new Coordinates(c.getX(), c.getY() + 1));
                } else if (c.getX() - 1 >= 0 && !grid[c.getX() - 1][c.getY()].isVisited()) {
                    stack.push(new Coordinates(c.getX() - 1, c.getY()));
                } else if (c.getX() + 1 < TILES_X && !grid[c.getX() + 1][c.getY()].isVisited()) {
                    stack.push(new Coordinates(c.getX() + 1, c.getY()));
                }
            }

        }
        return count;
    }

    private static boolean isCoordinatesUnvisited(Coordinates[][] grid, Coordinates c) {

        if (c.getX() < 0 || c.getY() < 0 || c.getX() >= TILES_X || c.getY() >= TILES_Y) {
            return false;
        }

        Coordinates coordinatesToCheck = grid[c.getX()][c.getY()];

        if (coordinatesToCheck.isVisited()) {
            return false;
        }

        coordinatesToCheck.setVisited(true);

        return true;
    }

}
