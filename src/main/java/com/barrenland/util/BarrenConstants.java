package com.barrenland.util;

import com.barrenland.model.Coordinates;

public class BarrenConstants {

    public static final int TILE_SIZE = 1;
    public static final int WIDTH = 400;
    public static final int HEIGHT = 600;

    public static final int TILES_X = WIDTH / TILE_SIZE;
    public static final int TILES_Y = HEIGHT / TILE_SIZE;

    public static Coordinates[][] grid = new Coordinates[TILES_X][TILES_Y];
}
