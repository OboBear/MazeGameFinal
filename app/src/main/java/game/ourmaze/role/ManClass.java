package game.ourmaze.role;

import android.os.Handler;

import game.ourmaze.Data;
import game.ourmaze.Function;
import game.ourmaze.Init;
import game.ourmaze.Tool;
import game.ourmaze.activities.GameActivity;

public class ManClass {

    /*---------------------- Man-------------------------*/
    public static class Man {
        /* charecter */
        private final static int Man1 = 1201;
        /* members */
        /**
         * coordination
         */
        public int x, y;
        public int view, direct, win, level;
        public int character;
        /**
         * property
         */
        public int wisedom, blood, defence, beat;
        /**
         * the quantity of each tool
         */
        public int man_tool[] = new int[15];

        public Man(int sx, int sy) {
            x = sx;
            y = sy;
            view = 3;
            direct = Data.man_front;
            win = 0;
            level = 0;
            character = Man1;
            wisedom = Data.wisedom_t;
            blood = Data.MaxBlood[level];
            beat = Data.beat_t;
            defence = Data.defence_t;
            Data.Blood = Data.MaxBlood[level];
            // the initial quantity of each tool is 0
            for (int i = 0; i < 15; i++) {
                if (i == 7 | i == 2) {
                    man_tool[i] = 0;
                } else {
                    man_tool[i] = 1;
                }
            }
        }

        public boolean move(int direct) {
            if (Data.maze[x + Data.drct[direct][0]][y + Data.drct[direct][1]] != 0) {
                Data.num++;
                x += Data.drct[direct][0];
                y += Data.drct[direct][1];
                this.direct = direct;
                Init.disfog(x, y, view);
                if (Data.mon[x][y] != -1) {
                    Data.stop_event = true;
                    fight(Data.mon[x][y]);
                }
                return true;
            }
            return false;
        }

        Handler handler = new Handler();
        private boolean fight_flag = true;

        public void fight(int monId) {
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
                handler.postDelayed(() -> {
                    Data.stop_event = true;
                    fight_flag = true;
                    GameActivity.gameView.postInvalidate();
                }, 500);
            }
            if (MonsterClass.monster[monId].blood == 0) {
                Data.mon[MonsterClass.monster[monId].x][MonsterClass.monster[monId].y] = -1;
                Tool.get_tool(MonsterClass.monster[monId]._toolid);
                ManClass.man.wisedom += 5;
            }
        }
    }

    public static Man man = new Man(2, 2);

}
