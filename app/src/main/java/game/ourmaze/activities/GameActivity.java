package game.ourmaze.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import game.ourmaze.Data;
import game.ourmaze.Function;
import game.ourmaze.Init;
import game.ourmaze.R;
import game.ourmaze.Tool;
import game.ourmaze.equipment.ToolBean;
import game.ourmaze.equipment.adapter.ToolAdapter;
import game.ourmaze.role.ManClass;
import game.ourmaze.role.ManClass.Man;
import game.ourmaze.views.GameView;

public class GameActivity extends Activity {
    public static GameView gameView = null;
    private RecyclerView rvToolbar;
    private TextView tvCurrentRank;
    private TextView tvBlood;
    private TextView tvAttack;
    private TextView tvDefense;
    private ProgressBar pbBlood;
    private ToolAdapter toolAdapter;
    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int what = msg.what;
            if (what == 0) {
                updateProperty();
            }
        }
    };

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
        toolAdapter = new ToolAdapter(Tool.generalTool(), toolClickCallBack);
        rvToolbar.setAdapter(toolAdapter);

        tvCurrentRank = findViewById(R.id.tvCurrentRank);
        tvBlood = findViewById(R.id.tvBlood);
        tvAttack = findViewById(R.id.tvAttack);
        tvDefense = findViewById(R.id.tvDefense);
        pbBlood = findViewById(R.id.pbBlood);
        updateProperty();
    }

    ToolAdapter.ToolClickCallBack toolClickCallBack = (position, toolBean) -> {
        switch (position) {
            case 0:
                if (ManClass.man.x == Data.maze_end[ManClass.man.level][0] && ManClass.man.y == Data.maze_end[ManClass.man.level][1]) {
                    toolBean.count --;
                    updateProperty();
                    pass();
                }
                break;
            case 1:
                Data.using_tool = !Data.using_tool;
                break;
            case 2:
                break;
            case 3:
                toolBean.count --;
                Tool.dis_fog();
                break;
            case 4:
                if (ManClass.man.blood <= 0) {
                    while (ManClass.man.blood < Data.blood_t && ManClass.man.blood < Data.Blood) {
                        ManClass.man.blood += 10;
                    }
                    toolBean.count --;
                }
                break;
            case 5:
                if (ManClass.man.blood != Data.Blood) {
                    int t = ManClass.man.blood + 20;
                    while (ManClass.man.blood < t && ManClass.man.blood < Data.Blood) {
                        ManClass.man.blood++;
                    }
                    toolBean.count --;
                }
                break;
            case 6:
                Tool.escape();
                toolBean.count --;
                break;
            case 7:
                Tool.paint_road(Data.maze_size[ManClass.man.level][0], Data.maze_size[ManClass.man.level][1]);
                break;
            case 8:
                if (ManClass.man.beat != Data.Beat) {
                    int t = ManClass.man.beat + 3;
                    while (ManClass.man.beat < t && ManClass.man.beat < Data.Beat) {
                        ManClass.man.beat++;
                    }
                    toolBean.count --;
                }
                break;
            case 9:
                if (ManClass.man.defence != Data.Defence) {
                    int t = ManClass.man.defence + 2;
                    while (ManClass.man.defence < t && ManClass.man.defence < Data.Defence) {
                        ManClass.man.defence++;
                    }
                    toolBean.count --;
                }
                break;
        }
        toolAdapter.notifyDataSetChanged();
    };


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
            while (true) {
                try {
                    if (ManClass.man.blood <= 0) {
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

                    Data.flag_arrive = Data.maze[ManClass.man.x][ManClass.man.y] == 4 && ManClass.man.man_tool[0] > 0;
                    sleep(sleep_time);
                    gameView.postInvalidate();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void pass() {
        Data.paint.setColor(Color.GRAY);
        Data.stop_event = false;
        Toast.makeText(this, "恭喜你进入下一关", Toast.LENGTH_SHORT).show();
        handler.postDelayed(() -> {
            ManClass.man.level++;
            Data.Blood = Data.MaxBlood[ManClass.man.level];
            Init.init_data();
            Data.num = 0;
            Data.choose_num = 0;
            // the size of bar ( pixel )
            Data.unit_l = Data.scr_width / 10;
            Data.using_tool = false;
            updateProperty();
            Data.stop_event = true;
        }, 2000);
    }


    private void updateProperty() {
        tvCurrentRank.setText("当前等级：" + (ManClass.man.level + 1));
        tvBlood.setText("HP：" + ManClass.man.blood);
        tvAttack.setText("攻击：" + ManClass.man.beat);
        tvDefense.setText("防御：" + ManClass.man.defence);
    }



}
