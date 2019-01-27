package game.ourmaze.role;

import game.ourmaze.Data;
import game.ourmaze.Init;

public class Monster {
    public int x, y;
    public int takedToolId;
    public int wisedom, blood, defence, beat; // property

    public Monster(int sx, int sy, int monId) {
        x = sx;
        y = sy;
        wisedom = Data.mon_pro[monId][0] * (Playground.man.level + 1);
        blood = Data.mon_pro[monId][1] * (Playground.man.level + 1);
        defence = Data.mon_pro[monId][2] * (Playground.man.level + 1);
        beat = Data.mon_pro[monId][3] * (Playground.man.level + 1);
        if (wisedom > Data.Wisedom) {
            wisedom = Data.Wisedom;
        }
        if (blood > Data.Blood) {
            blood = Data.Blood;
        }
        if (defence > Data.Defence) {
            defence = Data.Defence;
        }
        if (beat > Data.Beat) {
            beat = Data.Beat;
        }
        if (monId == 0) {
            takedToolId = 0;
        } else {
            takedToolId = Init.createRandom(2, 9);
            while (takedToolId == 2 || takedToolId == 7) {
                takedToolId = Init.createRandom(2, 9);
            }
        }
    }
}