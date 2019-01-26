package game.ourmaze.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;

import game.ourmaze.Data;
import game.ourmaze.Function;
import game.ourmaze.Init;
import game.ourmaze.R;
import game.ourmaze.Tool;
import game.ourmaze.adapter.ToolAdapter;
import game.ourmaze.role.ManClass;
import game.ourmaze.role.ManClass.Man;
import game.ourmaze.views.GameView;

public class GameActivity extends Activity {

    public static GameView gameView = null;
    RecyclerView rvToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Data.maze_a = Data.maze_size[0][0];
        Data.maze_b = Data.maze_size[0][1];
        Init.init_data();
        init();
        setContentView(R.layout.gameactive);
        gameView = findViewById(R.id.gameview);
        new monster_move(1000).start();
        new Invalidate(5).start();
        rvToolbar = findViewById(R.id.rv_toolbar);
        rvToolbar.setLayoutManager(new LinearLayoutManager(this));
        rvToolbar.setAdapter(new ToolAdapter(Tool.generalTool()));
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK || keyCode == KeyEvent.KEYCODE_HOME) {
            new AlertDialog.Builder(this).setTitle("").setMessage("退出游戏?").setPositiveButton("确认", (dialog, whichButton) -> {
                setResult(RESULT_OK);
                Data.init();
                ManClass.man.level = 0;
                ManClass.man = new Man(Data.maze_start[ManClass.man.level][0], Data.maze_start[ManClass.man.level][1]);
                Data.deep_word = 20;
                GameActivity.this.finish();
            }).setNegativeButton("取消", null).show();
        }
        return super.onKeyDown(keyCode, event);
    }

    private void init() {
        Data.num = 0;
        Data.choose_num = 0;
        Data.unit_l = Data.scr_width / 10;
        Data.maze_l = Data.maze_a * Data.unit_l;
        Data.maze_h = Data.maze_b * Data.unit_l;
    }

    class monster_move extends Thread {
        private int sleep_time;

        public monster_move(int st) {
            sleep_time = st;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    sleep(sleep_time / 6);
                    Data.monster_move = 0;
                    sleep(sleep_time / 6);
                    Data.monster_move = 1;
                    sleep(sleep_time / 6);
                    Data.monster_move = 0;
                    sleep(sleep_time / 6);
                    sleep(sleep_time);
                    Data.monster_move = 1;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    class Invalidate extends Thread {
        private int sleep_time;

        public Invalidate(int st) {
            sleep_time = st;
        }

        @Override
        public void run() {
            int a = 0;
            while (true) {
                try {
                    if (a == 0 && Data.pass) {
                        a++;
                        Function.pass();
                    }
                    if (Data.pass == false) {
                        a = 0;
                    }
                    if (ManClass.man.blood == 0) {
                        if (ManClass.man.man_tool[4] == 0) {
                            Data.death_flag = true;
                            Data.stop_event = false;

                            sleep(4000);
                            Data.death_flag = false;
                            Data.stop_event = true;
                            Data.init();
                            ManClass.man.level = 0;
                            Data.stop_event = true;
                            GameActivity.this.finish();

                        } else {
                            while (ManClass.man.blood <= 20) {
                                sleep(10);
                                gameView.postInvalidate();
                                ManClass.man.blood += 1;

                            }
                            ManClass.man.man_tool[4]--;
                            if (ManClass.man.man_tool[4] <= 0) {
                                ManClass.man.man_tool[4] = 0;
                            }
                        }
                    }

                    if (ManClass.man.level == 10) {
                        Data.AK = true;
                        Data.stop_event = false;
                        sleep(4000);
                        Data.AK = false;
                        Data.stop_event = true;
                        Data.init();
                        ManClass.man.level = 0;
                        Data.stop_event = true;
                        finish();
                    }

                    if (Data.maze[ManClass.man.x][ManClass.man.y] == 4 && ManClass.man.man_tool[0] > 0) {
                        Data.flag_arrive = true;
                    } else {
                        Data.flag_arrive = false;
                    }
                    sleep(sleep_time);
                    gameView.postInvalidate();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
