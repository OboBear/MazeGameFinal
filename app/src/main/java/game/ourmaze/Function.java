package game.ourmaze;

import android.graphics.Color;
import android.os.Handler;

import game.ourmaze.activities.GameActivity;
import game.ourmaze.role.ManClass;
import game.ourmaze.role.MonsterClass;

public class Function {
    public static float distance(float x1, float y1, float x2, float y2) {
        return (float) Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
    }

    public static void newPoint(float x1, float y1, float x2, float y2, int dis) {
        if (x1 == x2) {
            if (y1 > y2) {
                Data.x_button = (int) x1;
                Data.y_button = (int) (y2 + dis);
            } else {
                Data.x_button = (int) x1;
                Data.y_button = (int) (y2 - dis);
            }
        } else if (y1 == y2) {
            if (x1 > x2) {
                Data.x_button = (int) (x2 + dis);
                Data.y_button = (int) y1;
            } else {
                Data.x_button = (int) (x2 - dis);
                Data.y_button = (int) y1;
            }
        } else {
            float p = dis / distance(x1, y1, x2, y2);
            Data.x_button = (int) (x2 + (x1 - x2) * p);
            Data.y_button = (int) (y2 + (y1 - y2) * p);
        }
    }

    //////screen move
    public static void screenMove() {
        if (Data.place_y > 0) {
            Data.place_y = 0;
        }
        if (Data.place_x > 0) {
            Data.place_x = 0;
        }
        if (Data.place_x < -((Data.maze_a) * Data.unit_l - Data.scr_width)) {
            Data.place_x = -((Data.maze_a) * Data.unit_l - (Data.scr_width));
        }
        if (Data.place_y < -((Data.maze_b) * Data.unit_l - Data.scr_height)) {
            Data.place_y = -((Data.maze_b) * Data.unit_l - Data.scr_height);
        }

        ////man place
        if (Data.place_y < -(ManClass.man.y - 2) * Data.unit_l) {
            Data.place_y = -(ManClass.man.y - 2) * Data.unit_l;
        }
        if (Data.place_x < -(ManClass.man.x - 2) * Data.unit_l) {
            Data.place_x = -(ManClass.man.x - 2) * Data.unit_l;
        }
        if (Data.place_y > Data.scr_height - (ManClass.man.y + 1) * Data.unit_l) {
            Data.place_y = Data.scr_height - (ManClass.man.y + 1) * Data.unit_l;
        }
        if (Data.place_x > Data.scr_width - (ManClass.man.x + 1) * Data.unit_l) {
            Data.place_x = Data.scr_width - (ManClass.man.x + 1) * Data.unit_l;
        }
    }
}
