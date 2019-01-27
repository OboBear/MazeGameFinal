package game.ourmaze;


import java.util.Random;

import game.ourmaze.role.ManClass;
import game.ourmaze.role.MonsterClass;

public class Init {
    /*---------------------- plugin-------------------------*/
    public static void bar(int _left, int _top, int _right, int _bottom) {
        Data.paint.setAlpha(150);
        Data.drawcanvas.drawRect(_left, _top, _right, _bottom, Data.paint);
    }

    private static Random random = new Random(System.currentTimeMillis());
    public static int createRandom(int min, int max) {
        return random.nextInt(max - min + 1) + min;
    }

    /**
     * 清除迷雾
     * @param x
     * @param y
     * @param view
     */
    public static void disfog(int x, int y, int view) {
        int halfView = view / 2;
        for (int j = y - halfView; j <= y + halfView; j++) {
            for (int i = x - halfView; i <= x + halfView; i++) {
                if (i >= 0 && j >= 0 && x <= 2 * Data.maze_a + 1 && y <= 2 * Data.maze_b + 1) {
                    Data.fog[i][j] = 1;
                }
            }
        }
    }

    // 放置小怪算法
    private static void putmon(int nmon) {
        for (int i = 0; i < Data.maxmaze; i++) {
            for (int j = 0; j < Data.maxmaze; j++) {
                Data.mon[i][j] = -1;
            }
        }
        for (int i = 0; i < nmon; ) {
            int x = createRandom(1, Data.maze_a), y = createRandom(1, Data.maze_b);
            if (Data.mon[x][y] == -1 && Data.maze[x][y] == 1 && (x != ManClass.man.x && y != ManClass.man.y)) {
                Data.mon[x][y] = i;
                MonsterClass.monster[i] = new MonsterClass.Monster(x, y, i);
                i++;
            }
        }
    }

    /*----------------------------------maze------------------------------------*/
    // _maze:save the maze data a:leight of maze scale b:height of maze scale
    private static void dfs(int[][] _maze, int x, int y) {
        int px = 2 * x, py = 2 * y;
        int turn;
        int tran = createRandom(0, 1);
        _maze[px][py] = 1;
        if (tran == 1) {
            turn = 1;
        } else {
            turn = 3;
        }
        for (int i = 0, dfs_next = createRandom(0, 3); i < 4; i++, dfs_next = (dfs_next + turn) % 4) {
            if (_maze[px + 2 * Data.drct[dfs_next][0]][py + 2 * Data.drct[dfs_next][1]] == 0) {
                _maze[px + Data.drct[dfs_next][0]][py + Data.drct[dfs_next][1]] = 1;
                dfs(_maze, x + Data.drct[dfs_next][0], y + Data.drct[dfs_next][1]);
            }
        }
    }

    private static void make_maze(int[][] maze, int a, int b) {
        maze[0][0] = 0;
        for (int i = 0; i <= 2 * a + 2; i++) {
            maze[i][0] = maze[i][2 * b + 2] = 1;
        }
        for (int i = 0; i <= 2 * b + 2; i++) {
            maze[0][i] = maze[2 * a + 2][i] = 1;
        }
        for (int j = 1; j <= 2 * b + 1; j++) {
            for (int i = 1; i <= 2 * a + 1; i++) {
                maze[i][j] = 0;
            }
        }
        dfs(maze, createRandom(1, a), createRandom(1, b));
    }

    /*--------------------------------init---------------------------------------*/
    public static void init_data() {
        Data.maze_a = Data.maze_size[ManClass.man.level][0];
        Data.maze_b = Data.maze_size[ManClass.man.level][1];
        Data.maze_l = Data.maze_a * Data.unit_l;
        Data.maze_h = Data.maze_b * Data.unit_l;
        ManClass.man.x = Data.maze_start[ManClass.man.level][0];
        ManClass.man.y = Data.maze_start[ManClass.man.level][1];
        make_maze(Data.maze, Data.maze_a / 2, Data.maze_b / 2);
        Data.unit_l = Data.area_y / Math.max(Data.maze_a, Data.maze_b);
        for (int i = 0; i <= Data.maze_a + 1; i++) {
            for (int j = 0; j <= Data.maze_b + 1; j++) {
                Data.vst[i][j] = 0;

            }
        }
        // 1 is visited
        for (int i = 0; i <= Data.maze_a + 1; i++) {
            for (int j = 0; j <= Data.maze_b + 1; j++) {
                Data.fog[i][j] = 0;
            }
        }
        // 0 is fog
        putmon(Data.monst_num[ManClass.man.level]);
        Data.maze[Data.maze_start[ManClass.man.level][0]][Data.maze_start[ManClass.man.level][1]] = 3;
        Data.maze[Data.maze_end[ManClass.man.level][0]][Data.maze_end[ManClass.man.level][1]] = 4;
        disfog(Data.maze_start[ManClass.man.level][0], Data.maze_start[ManClass.man.level][1], ManClass.man.view);
    }

    public static boolean move(int direct) {
        return Data.maze[ManClass.man.x + Data.drct[direct][0]][ManClass.man.y + Data.drct[direct][1]] != 0;
    }

}

