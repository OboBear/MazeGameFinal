package game.ourmaze.role;

import android.os.Handler;

import game.ourmaze.Data;
import game.ourmaze.Init;
import game.ourmaze.Tool;
import game.ourmaze.activities.GameActivity;
import game.ourmaze.activities.UiInterface;

public class Hero {
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

    private UiInterface uiInterface;

    public Hero(int sx, int sy) {
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
                fight(Data.mon[x][y]);
            }
            return true;
        }
        return false;
    }

    Handler handler = new Handler();

    private void fight(int monId) {
        int dMan = Playground.monster[monId].beat - defence;
        int dMon = beat - Playground.monster[monId].defence;
        if (dMan < 0 && Playground.monster[monId].beat < defence / 2) {
            dMan = 0;
        } else if (dMan < 0) {
            dMan = 1;
        }
        if (dMon < 0 && beat < Playground.monster[monId].defence / 2) {
            dMon = 0;
        } else if (dMon < 0) {
            dMon = 1;
        }
        while (blood > 0 && Playground.monster[monId].blood > 0) {
            blood -= dMan;
            Playground.monster[monId].blood -= dMon;
            if (blood < 0) {
                blood = 0;
            }
            if (Playground.monster[monId].blood < 0) {
                Playground.monster[monId].blood = 0;
            }
        }

        if (Playground.monster[monId].blood == 0) {
            Data.mon[Playground.monster[monId].x][Playground.monster[monId].y] = -1;
            Tool.get_tool(Playground.monster[monId].takedToolId);
            wisedom += 5;
        }

        handler.post(() -> {
            if (uiInterface != null) {
                uiInterface.invalidate();
            }
        });
    }

    public void setUiInterface(UiInterface uiInterface) {
        this.uiInterface = uiInterface;
    }
}
