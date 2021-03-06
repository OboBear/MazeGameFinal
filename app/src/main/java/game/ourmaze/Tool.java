package game.ourmaze;

import android.graphics.Color;

import java.util.ArrayList;
import java.util.List;

import game.ourmaze.equipment.ToolBean;
import game.ourmaze.role.Playground;

/**
 * @author obo
 */
public class Tool {

    public static void get_tool(int tool_id) {
        Playground.man.man_tool[tool_id]++;
    }

    public static void paint_map() {
        int size = Data.scr_height / 35;
        int place_y = (Data.scr_height - size * Data.maze_b) / 2;
        int place_x = (Data.scr_width - size * Data.maze_a) / 2;
        for (int j = 1; j <= Data.maze_b; j++) {
            int i = 1;
            for (; i <= Data.maze_a; i++) {

                if (Data.maze[i][j] == 0) {
                    Data.paint.setColor(Color.GRAY);
                }

                if (Data.maze[i][j] == 1) {
                    Data.paint.setColor(Color.WHITE);
                }
                if (Data.fog[i][j] == 1 && Data.maze[i][j] == 3) {
                    Data.paint.setColor(Color.LTGRAY);
                }
                if (Data.fog[i][j] == 1 && Data.maze[i][j] == 4) {
                    Data.paint.setColor(Color.BLUE);
                }
                if (Data.maze_start[Playground.man.level][0] == i && Data.maze_start[Playground.man.level][1] == j) {
                    Data.paint.setColor(Color.RED);
                }
                if (Data.maze_end[Playground.man.level][0] == i && Data.maze_end[Playground.man.level][1] == j) {
                    Data.paint.setColor(Color.BLACK);
                }
                Init.bar(place_x + size * (i - 1),
                        place_y + size * (j - 1),
                        place_x + size * i,
                        place_y + size * j);
            }
        }
    }


    public static void escape() {
        while (true) {
            int x = Init.createRandom(1, Data.maze_a);
            int y = Init.createRandom(1, Data.maze_b);
            if (Data.mon[x][y] == -1 && Data.maze[x][y] == 1 && Playground.man.x != x && Playground.man.y != y) {
                Playground.man.x = x;
                Playground.man.y = y;
                Init.disfog(x, y, Playground.man.view);
                return;
            }
        }
    }

    // 地图绘制算法
//    public static void paint_map(int map_size_x, int map_size_y) {
//        int unit_t = 10;
//        for (int j = 1; j <= (map_size_y + 1); j++) {
//            for (int i = 1; i <= (map_size_x + 1); i++) {
//                if (Data.maze[i][j] == 0 && Data.fog[i][j] == 1) {
//                    Data.paint.setColor(Color.BLUE);
//                    Init.bar(unit_t * (i - 1), unit_t * (j - 1), unit_t * i, unit_t * j);
//                }
//                if (Data.maze[i][j] == 1 && Data.fog[i][j] == 1) {
//                    Data.paint.setColor(Color.DKGRAY);
//                    Init.bar(unit_t * (i - 1), unit_t * (j - 1), unit_t * i, unit_t * j);
//                }
//                if (Data.maze[i][j] == 3 && Data.fog[i][j] == 1) {
//                    Data.paint.setColor(Color.RED);
//                    Init.bar(unit_t * (i - 1), unit_t * (j - 1), unit_t * i, unit_t * j);
//                }
//                if (Data.maze[i][j] == 4 && Data.fog[i][j] == 1) {
//                    Data.paint.setColor(Color.YELLOW);
//                    Init.bar(unit_t * (i - 1), unit_t * (j - 1), unit_t * i, unit_t * j);
//                }
//                if (Playground.man.x == i && Playground.man.y == j) {
//                    Data.paint.setColor(Color.CYAN);
//                    Init.bar(unit_t * (i - 1), unit_t * (j - 1), unit_t * i, unit_t * j);
//                }
//            }
//        }
//    }

    // 答案路绘制算法
    public static void paint_road(int map_size_x, int map_size_y) {
        int unit_t = 10;
        for (int j = 1; j <= (map_size_y + 1); j++) {
            for (int i = 1; i <= (map_size_x + 1); i++) {
                if (Data.vst[i][j] == 1) {
                    Data.paint.setColor(Color.BLUE);
                    Init.bar(unit_t * (i - 1), unit_t * (j - 1), unit_t * i, unit_t * j);
                }
                if (Data.maze[i][j] == 3) {
                    Data.paint.setColor(Color.RED);
                    Init.bar(unit_t * (i - 1), unit_t * (j - 1), unit_t * i, unit_t * j);
                }
                if (Data.maze[i][j] == 4) {
                    Data.paint.setColor(Color.YELLOW);
                    Init.bar(unit_t * (i - 1), unit_t * (j - 1), unit_t * i, unit_t * j);
                }
                if (Playground.man.x == i && Playground.man.y == j) {
                    Data.paint.setColor(Color.CYAN);
                    Init.bar(unit_t * (i - 1), unit_t * (j - 1), unit_t * i, unit_t * j);
                }
            }
        }
    }


    public static List<ToolBean> generalTool() {
        final int []toolIconEmp = {R.drawable.k0, R.drawable.m0, R.drawable.j0, R.drawable.f0,
                R.drawable.r0, R.drawable.p0, R.drawable.t0, R.drawable.a0,
                R.drawable.g0, R.drawable.d0};
        final int []toolIcon = {R.drawable.k1, R.drawable.m1, R.drawable.j1, R.drawable.f1,
                R.drawable.r1, R.drawable.p1, R.drawable.t1, R.drawable.a1,
                R.drawable.g1, R.drawable.d1};
        final String[]toolName = {"钥匙", "地图", "照妖", "驱雾", "复活", "生命", "未知", "未知", "攻击", "防御"};

        List<ToolBean> toolBeans = new ArrayList<>();
        for (int i = 0; i<toolIcon.length; i ++) {
            ToolBean toolBean = new ToolBean();
            toolBean.iconResource = toolIcon[i];
            toolBean.iconResourceEmp = toolIconEmp[i];
            toolBean.name = toolName[i];
            toolBean.count = 1;
            toolBeans.add(toolBean);
        }
        return toolBeans;
    }
}
