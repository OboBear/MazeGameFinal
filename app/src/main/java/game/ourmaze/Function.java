package game.ourmaze;

import android.graphics.Color;

import game.ourmaze.activities.GameActivity;
import game.ourmaze.role.ManClass;
import game.ourmaze.role.MonsterClass;

public class Function {
    public static float distance(float x1, float y1, float x2, float y2) {
        return (float) Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
    }

    public static void newpoint(float x1, float y1, float x2, float y2, int dis) {
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
            float l1;
            float p;
            l1 = distance(x1, y1, x2, y2);
            p = dis / l1;
            Data.x_button = (int) (x2 + (x1 - x2) * p);
            Data.y_button = (int) (y2 + (y1 - y2) * p);
        }
    }

    // fight
    public static boolean fight_flag = true;

    public static void fight(int monId) {
        int dMan = MonsterClass.monster[monId].beat - ManClass.man.defence;
        int dMon = ManClass.man.beat - MonsterClass.monster[monId].defence;
        if (dMan < 0 && MonsterClass.monster[monId].beat < ManClass.man.defence / 2) {
            dMan = 0;
        } else if (dMan < 0) {
            dMan = 1;
        }
        if (dMon < 0 && ManClass.man.beat < MonsterClass.monster[monId].defence / 2) {
            dMon = 0;
        } else if (dMon < 0) {
            dMon = 1;
        }
        while (ManClass.man.blood != 0 && MonsterClass.monster[monId].blood != 0) {
            if (fight_flag) {
                ManClass.man.blood -= dMan;
            }
            MonsterClass.monster[monId].blood -= dMon;
            if (ManClass.man.blood < 0) {
                ManClass.man.blood = 0;
            }
            if (MonsterClass.monster[monId].blood < 0) {
                MonsterClass.monster[monId].blood = 0;
            }
            fight_flag = false;
            new sleep(500, 2).start();
        }
        if (MonsterClass.monster[monId].blood == 0) {
            Data.mon[MonsterClass.monster[monId].x][MonsterClass.monster[monId].y] = -1;
            Tool.get_tool(MonsterClass.monster[monId]._toolid);
            ManClass.man.wisedom += 5;
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


    public static void pass() {
        Data.paint.setColor(Color.GRAY);
        Init.bar(Data.scr_width / 4, Data.scr_height / 4, Data.scr_width * 3 / 4, Data.scr_height * 3 / 4);
        Data.stop_event = false;
        new sleep(2000, 1).start();
    }


}

class sleep extends Thread {
    private int sleep_time;
    private int flag;

    public sleep(int st, int flag) {
        sleep_time = st;
        this.flag = flag;
    }

    @Override
    public void run() {
        if (flag == 1) {
            try {
                sleep(sleep_time);
                Data.stop_event = true;
                ManClass.man.level++;
                Data.Blood = Data.MaxBlood[ManClass.man.level];
                Init.init_data();
                Data.num = 0;
                Data.choose_num = 0;
                // the size of bar ( pixel )
                Data.unit_l = Data.scr_width / 10;
                GameActivity.gameView.postInvalidate();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Data.using_tool = false;
            Data.stop_event = true;
            Data.pass = false;
            GameActivity.gameView.postInvalidate();
        } else {
            try {
                sleep(sleep_time);
                Data.stop_event = true;

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Function.fight_flag = true;
            GameActivity.gameView.postInvalidate();
        }

    }
}

