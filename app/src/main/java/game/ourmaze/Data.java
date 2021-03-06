package game.ourmaze;

import game.ourmaze.role.Hero;
import game.ourmaze.role.Playground;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;

public class Data {

    /* #define */
    public final static int maxmaze = 100;  // the max size of maze ( bar )
    public final static int area_y = 350;
    public final static int area_x = 350;
    public final static int maze_size[][] = {{13, 15}, {15, 13}, {15, 15}, {15, 19}, {19, 23}, {23, 21}, {23, 23}, {25, 25}, {27, 25}, {27, 27}}; // the maze_x and maze_y of each level
    public static int Blood = 200, Beat = 200, Defence = 200, Wisedom = 20000; // the max value of wisedom/blood/defence/beat
    public final static int blood_t = 20, beat_t = 3, defence_t = 2, wisedom_t = 30; // the original wisedom/blood/defence/beat of man
    public final static int mon_pro[][] = {{0, 50, 1, 5}, {0, 50, 1, 5}, {0, 50, 1, 5}, {0, 50, 1, 5}, {0, 50, 1, 5}, {0, 50, 1, 5}, {0, 50, 1, 5}, {0, 50, 1, 5}, {0, 50, 1, 5}, {0, 50, 1, 5}, {0, 50, 1, 5}, {0, 50, 1, 5}, {0, 50, 1, 5}, {0, 50, 1, 5}, {0, 50, 1, 5}, {0, 50, 1, 5}};   // the original wisedom/blood/defence/beat of each monster
    /* direct */
    public final static int man_front = 1;

    public static int place_x = 30, place_y = 30; // the coordination of maze ( pixel )
    public static int maze_a = maze_size[0][0], maze_b = maze_size[0][1]; // the size of maze ( bar )
    public static int unit_l = 30; // the size of bar ( pixel )
    public static int maze_l = maze_a * unit_l, maze_h = maze_b * unit_l;   // the size of maze ( pixel )
    public static int[][] maze = new int[maxmaze][maxmaze]; // maze 1 is road
    public static int[][] fog = new int[maxmaze][maxmaze]; // fog 0 is fog
    public static int vst[][] = new int[maxmaze][maxmaze];    // 1 is the answer road
    public static int mon[][] = new int[maxmaze][maxmaze];    // bigger than 0 is the index of monster
    public static int drct[][] = {{1, 0}, {0, 1}, {-1, 0}, {0, -1}, {-1, -1}, {-1, 1}, {1, -1}, {1, 1}}; // 8 directions
    public static Paint paint = new Paint();
    public static Canvas drawcanvas;

    /////////////////////////////////////
    public static int num = 0;
    public static int choose_num = 0;
    public static int scr_height; // screen height
    public static int scr_width; //screen width

    //pictures
    public static Bitmap bitmap;
    public static Bitmap floor;
    public static Bitmap door;
    public static Bitmap wall;
    public static Bitmap start;
    public static Bitmap monst1;
    public static Bitmap monst2;
    public static Bitmap button;
    public static Bitmap circle;
    public static Bitmap forw[] = new Bitmap[5];
    public static Bitmap up[] = new Bitmap[5];
    public static Bitmap left[] = new Bitmap[5];
    public static Bitmap right[] = new Bitmap[5];
    public static int width;
    public static int height;
    public static int y_toolset = 0;
    public static int x_circlepoint;
    public static int y_circlepoint;
    public static int x_button;
    public static int y_button;
    public static int r_button;
    public static float scaleWidth;
    public static float scaleHeight;
    public static int direct = 3;

    public static int stop_time = 400;//stop time
    public static int x_man_move_time_state;  //man move animation
    public static int y_man_move_time_state;
    public static boolean button_man_move = true;
    public static boolean start_move_or_not = false;  // BeginView  && GameView
    ////matrix
    public static Matrix matrix = new Matrix();

    ///
    public static final int maze_start[][] = {{2, 2}, {4, 4}, {2, 8}, {2, 8}, {2, 2}, {12, 12}, {14, 14}, {2, 16}, {20, 20}, {2, 8}, {22, 22}};
    public static final int maze_end[][] = {{8, 8}, {12, 12}, {12, 14}, {14, 14}, {2, 8}, {2, 4}, {14, 14}, {2, 18}, {26, 16}, {2, 4}};
    public static final int monst_num[] = {5, 6, 7, 8, 9, 10, 11, 12, 13, 14};
    public static final int MaxBlood[] = {40, 60, 80, 100, 120, 140, 160, 180, 190, 200};

    /////////////////////
    public static boolean stop_event = true;  //stop event
    public static boolean shouldShowMap = false;   // tool flag choose or not
    public static boolean death_flag = false;

    /////////////////monster move flag
    public static int monster_move = 0;

    ////////////////////
    //deepth out word
    public static int deep_word = 20;
    //是否到达终点
    public static boolean flag_arrive = false;
    public static boolean AK = false;

    public static void init() {
        stop_event = true;
        shouldShowMap = false;
        Playground.man = new Hero(2, 2);
        num = 0;
        choose_num = 0;
        direct = 3;
    }
}
