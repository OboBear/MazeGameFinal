package game.ourmaze.views;

import android.content.Context;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import game.ourmaze.Data;
import game.ourmaze.Function;
import game.ourmaze.Init;
import game.ourmaze.role.ManClass;
import game.ourmaze.R;
import game.ourmaze.Tool;

public class GameView extends View {
    /*---------------------------constructor---------------------------------*/
    private static AttributeSet attrs;

    public GameView(Context context) {
        super(context, attrs);
    }

    // constructor
    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    Paint paint = new Paint();

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Data.drawcanvas = canvas;
        ////////////////////////////////////
        // 图片源
        ////////////////////////////////////////////////////////
        int ii, jj;

        if (Data.place_x + Data.maze_l > Data.area_x) {
            ii = (Data.area_x - Data.place_x) / Data.unit_l;
        } else {
            ii = 2 * Data.maze_a + 1;
        }
        if (Data.place_y + Data.maze_h > Data.area_y) {
            jj = (Data.area_y - Data.place_y) / Data.unit_l;
        } else {
            jj = 2 * Data.maze_b + 1;
        }
        for (int j = 0; j <= jj + Data.scr_height / 30; j++) {
            for (int i=0; i <= ii + Data.scr_width / 30; i++) {
                // 迷雾覆盖的格子
                if (Data.fog[i][j] == 0) {
                    Data.paint.setColor(Color.WHITE);
                    Init.bar(Data.unit_l * (i - 1) + Data.place_x, Data.unit_l * (j - 1) + Data.place_y, Data.unit_l * i + Data.place_x, Data.unit_l * j + Data.place_y);
                }
                // 以下皆为未被迷雾覆盖的格子
                // 墙
                if (Data.fog[i][j] == 1 && Data.maze[i][j] == 0) {
                    canvas.drawBitmap(Data.wall, Data.unit_l * (i - 1) + Data.place_x, Data.unit_l * (j - 1) + Data.place_y, paint);
                }

                // 起点
                if (Data.fog[i][j] == 1 && Data.maze[i][j] == 3) {
                    Data.matrix.postRotate(1, Data.door.getWidth() / 2 + 100, Data.door.getHeight() / 2 + 100);
                    canvas.drawBitmap(Bitmap.createBitmap(BitmapFactory.decodeStream(getResources().openRawResource(R.drawable.start)),
                            0, 0, Data.width, Data.height, Data.matrix, true),
                            Data.unit_l * (i - 1) + Data.place_x, Data.unit_l * (j - 1) + Data.place_y, paint);
                }

                // 终点
                if (Data.fog[i][j] == 1 && Data.maze[i][j] == 4) {
                    canvas.drawBitmap(Data.door, Data.unit_l * (i - 1) + Data.place_x, Data.unit_l * (j - 1) + Data.place_y, paint);
                }
                // 小怪
                if (Data.fog[i][j] == 1 && Data.mon[i][j] >= 0) {

                    if (Data.monster_move == 0) {
                        canvas.drawBitmap(Data.monst1, Data.unit_l * (i - 1) + Data.place_x, Data.unit_l * (j - 1) + Data.place_y, paint);
                    } else {
                        canvas.drawBitmap(Data.monst2, Data.unit_l * (i - 1) + Data.place_x, Data.unit_l * (j - 1) + Data.place_y, paint);
                    }
                }
            }
        }


        /////////////////////////////////////////////////////////////
        /////paint man

        if (Data.start_move_or_not) {
            switch (ManClass.man.direct) {
                case 1:
                    canvas.drawBitmap(Data.forw[Math.abs(Data.x_man_move_time_state + Data.y_man_move_time_state)], Data.unit_l * (ManClass.man.x) + Data.place_x - (4 - Data.x_man_move_time_state) * Data.unit_l / 4, Data.place_y + Data.unit_l * (ManClass.man.y) - (4 - Data.y_man_move_time_state) * Data.unit_l / 4, paint);
                    break;
                case 3:
                    canvas.drawBitmap(Data.up[Math.abs(Data.x_man_move_time_state + Data.y_man_move_time_state)], Data.unit_l * (ManClass.man.x) + Data.place_x - (4 - Data.x_man_move_time_state) * Data.unit_l / 4, Data.place_y + Data.unit_l * (ManClass.man.y) - (4 - Data.y_man_move_time_state) * Data.unit_l / 4, paint);

                    break;
                case 2:
                    canvas.drawBitmap(Data.left[Math.abs(Data.x_man_move_time_state + Data.y_man_move_time_state)], Data.unit_l * (ManClass.man.x) + Data.place_x - (4 - Data.x_man_move_time_state) * Data.unit_l / 4, Data.place_y + Data.unit_l * (ManClass.man.y) - (4 - Data.y_man_move_time_state) * Data.unit_l / 4, paint);

                    break;
                case 0:
                    canvas.drawBitmap(Data.right[Math.abs(Data.x_man_move_time_state + Data.y_man_move_time_state)], Data.unit_l * (ManClass.man.x) + Data.place_x - (4 - Data.x_man_move_time_state) * Data.unit_l / 4, Data.place_y + Data.unit_l * (ManClass.man.y) - (4 - Data.y_man_move_time_state) * Data.unit_l / 4, paint);

                    break;
                default:
                    canvas.drawBitmap(Data.forw[Math.abs(Data.x_man_move_time_state + Data.y_man_move_time_state)], Data.unit_l * (ManClass.man.x) + Data.place_x - (4 - Data.x_man_move_time_state) * Data.unit_l / 4, Data.place_y + Data.unit_l * (ManClass.man.y) - (4 - Data.y_man_move_time_state) * Data.unit_l / 4, paint);

            }
            if (Data.y_man_move_time_state == 4 || Data.y_man_move_time_state == -4 || Data.x_man_move_time_state == 4 || Data.x_man_move_time_state == -4) {


                switch (ManClass.man.direct) {
                    case 1:
                        canvas.drawBitmap(Data.forw[Math.abs(Data.x_man_move_time_state + Data.y_man_move_time_state)], Data.unit_l * (ManClass.man.x - 1) + Data.place_x, Data.place_y + Data.unit_l * (ManClass.man.y - 1) - (4 - Data.y_man_move_time_state) * Data.unit_l / 4, paint);

                        break;
                    case 3:
                        canvas.drawBitmap(Data.up[Math.abs(Data.x_man_move_time_state + Data.y_man_move_time_state)], Data.unit_l * (ManClass.man.x - 1) + Data.place_x, Data.place_y + Data.unit_l * (ManClass.man.y - 1) - (4 - Data.y_man_move_time_state) * Data.unit_l / 4, paint);

                        break;
                    case 2:
                        canvas.drawBitmap(Data.left[Math.abs(Data.x_man_move_time_state + Data.y_man_move_time_state)], Data.unit_l * (ManClass.man.x - 1) + Data.place_x, Data.place_y + Data.unit_l * (ManClass.man.y - 1) - (4 - Data.y_man_move_time_state) * Data.unit_l / 4, paint);

                        break;
                    case 0:
                        canvas.drawBitmap(Data.right[Math.abs(Data.x_man_move_time_state + Data.y_man_move_time_state)], Data.unit_l * (ManClass.man.x - 1) + Data.place_x, Data.place_y + Data.unit_l * (ManClass.man.y - 1) - (4 - Data.y_man_move_time_state) * Data.unit_l / 4, paint);

                        break;
                    default:
                        canvas.drawBitmap(Data.forw[Math.abs(Data.x_man_move_time_state + Data.y_man_move_time_state)], Data.unit_l * (ManClass.man.x - 1) + Data.place_x, Data.place_y + Data.unit_l * (ManClass.man.y - 1) - (4 - Data.y_man_move_time_state) * Data.unit_l / 4, paint);


                }

                Data.start_move_or_not = false;
                Data.x_man_move_time_state = 0;
                Data.y_man_move_time_state = 0;

            }
        } else {
            Data.x_man_move_time_state = 0;
            Data.y_man_move_time_state = 0;

            switch (ManClass.man.direct) {
                case 1:
                    canvas.drawBitmap(Data.forw[Math.abs(Data.x_man_move_time_state + Data.y_man_move_time_state)], Data.unit_l * (ManClass.man.x - 1) + Data.place_x, Data.place_y + Data.unit_l * (ManClass.man.y - 1), paint);

                    break;
                case 3:
                    canvas.drawBitmap(Data.up[Math.abs(Data.x_man_move_time_state + Data.y_man_move_time_state)], Data.unit_l * (ManClass.man.x - 1) + Data.place_x, Data.place_y + Data.unit_l * (ManClass.man.y - 1), paint);

                    break;
                case 2:
                    canvas.drawBitmap(Data.left[Math.abs(Data.x_man_move_time_state + Data.y_man_move_time_state)], Data.unit_l * (ManClass.man.x - 1) + Data.place_x, Data.place_y + Data.unit_l * (ManClass.man.y - 1), paint);

                    break;
                case 0:
                    canvas.drawBitmap(Data.right[Math.abs(Data.x_man_move_time_state + Data.y_man_move_time_state)], Data.unit_l * (ManClass.man.x - 1) + Data.place_x, Data.place_y + Data.unit_l * (ManClass.man.y - 1), paint);

                    break;
                default:
                    canvas.drawBitmap(Data.forw[Math.abs(Data.x_man_move_time_state + Data.y_man_move_time_state)], Data.unit_l * (ManClass.man.x - 1) + Data.place_x, Data.place_y + Data.unit_l * (ManClass.man.y - 1), paint);


            }
        }


        /////////////
        //arrive
        if (Data.flag_arrive) {
            canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.arrive), Data.unit_l * (ManClass.man.x - 1) + Data.place_x - Data.unit_l / 2, Data.unit_l * (ManClass.man.y - 2) + Data.place_y - Data.unit_l / 3, paint);
        }


        ////////////////////////
        //paint tool

        paint.setTextSize(Data.unit_l / 6);

        canvas.drawBitmap(Data.toolbox, 0, 0, paint);
        int h1 = Data.scr_height / 15, h2 = h1 + Data.scr_height / 19, h3 = h2 + Data.scr_height / 29, h4 = h3 + Data.scr_height / 29;
        int l1;
        l1 = Data.pro_l * ManClass.man.blood / Data.Blood;

        Data.paint.setColor(Color.BLACK);
        //Init.bar(_left, _top, _right, _bottom, deep)
        Init.bar(Data.bar_x - 10, h1 - 10, Data.bar_x + Data.pro_l + 10, h2 + 10, 50);
        Init.bar(Data.bar_x - 10, h3 - 10, Data.bar_x + Data.pro_l + 10, h4 + 10, 50);
        Data.paint.setColor(Color.BLACK);
        Init.bar(Data.bar_x, h1, Data.bar_x + Data.pro_l, h1 + Data.pro_h, 150);
        Data.paint.setColor(Color.RED);
        Init.bar(Data.bar_x, h1, Data.bar_x + l1, h1 + Data.pro_h);

        paint.setColor(Color.WHITE);
        paint.setTextSize(Data.unit_l / 5);
        canvas.drawText("HP：" + ManClass.man.blood, Data.bar_x, h2, paint);
        canvas.drawText("攻击：" + ManClass.man.beat, Data.bar_x, h4, paint);
        canvas.drawText("防御：" + ManClass.man.defence, (Data.bar_x + Data.bar_x + Data.pro_l) / 2, h4, paint);
        //canvas.drawText("财富："+ManClass.man.wisedom,Data.bar_x,h5, paint);
        paint.setColor(Color.RED);
        canvas.drawText("当前等级：" + (ManClass.man.level + 1), Data.bar_x, Data.scr_height / 30, paint);

        canvas.drawBitmap(Data.circle, Data.x_circlepoint - Data.scr_height / 4, Data.y_circlepoint - Data.scr_height / 4, paint);
        canvas.drawBitmap(Data.button, Data.x_button - Data.r_button / 2, Data.y_button - Data.r_button / 2, paint);
        if (Data.using_tool) {
            Tool.paint_map();
        }

        if (Data.pass) {
            Data.paint.setColor(Color.GRAY);
            Init.bar(Data.scr_width / 4, Data.scr_height / 4, Data.scr_width * 3 / 4, Data.scr_height * 3 / 4);
            Data.paint.setColor(Color.YELLOW);
            paint.setTextSize(Data.unit_l / 2);
            canvas.drawText("恭喜你进入下一关!", Data.scr_width / 4 * 5 / 4, Data.scr_height / 2, paint);
        }

        if (Data.death_flag) {
            Data.paint.setColor(Color.GRAY);
            Init.bar(0, 0, Data.scr_width, Data.scr_height);

            Matrix matrix = new Matrix();
            int width = BitmapFactory.decodeStream(getResources().openRawResource(R.drawable.death)).getWidth();
            int height = BitmapFactory.decodeStream(getResources().openRawResource(R.drawable.death)).getHeight();
            float scaleWidth = ((float) Data.scr_width / 4) / width;
            float scaleHeight = ((float) Data.scr_height / 4 * 5 / 3) / height;

            matrix.postScale(scaleWidth, scaleHeight);
            Bitmap cover = Bitmap.createBitmap(BitmapFactory.decodeStream(getResources().openRawResource(R.drawable.death)), 0, 0, width, height, matrix, true);

            canvas.drawBitmap(cover, Data.scr_width / 4 * 5 / 3, Data.scr_height / 6, paint);
            paint.setColor(Color.RED);
            paint.setTextSize(Data.unit_l / 3 * 2);
            if (Data.deep_word <= 250) {
                paint.setAlpha(Data.deep_word += 3);
            }
            canvas.drawText("你已经在迷宫中迷失了自我!", Data.scr_width / 8, Data.scr_height / 4 * 3, paint);

            Data.stop_event = false;
            /*canvas.drawBitmap(Data.death,Data.scr_width/4, Data.scr_height/4, paint);*/
        }
        if (Data.AK) {
            Data.paint.setColor(Color.GRAY);
            Init.bar(0, 0, Data.scr_width, Data.scr_height);
            Matrix matrix = new Matrix();
            int width = BitmapFactory.decodeStream(getResources().openRawResource(R.drawable.man)).getWidth();
            int height = BitmapFactory.decodeStream(getResources().openRawResource(R.drawable.man)).getHeight();
            float scaleWidth = ((float) Data.scr_width / 4) / width;
            float scaleHeight = ((float) Data.scr_height / 4 * 5 / 3) / height;
            matrix.postScale(scaleWidth, scaleHeight);
            Bitmap cover = Bitmap.createBitmap(BitmapFactory.decodeStream(getResources().openRawResource(R.drawable.man)), 0, 0, width, height, matrix, true);
            canvas.drawBitmap(cover, Data.scr_width / 4 * 5 / 3, Data.scr_height / 6, paint);
            paint.setColor(Color.RED);
            paint.setTextSize(Data.unit_l / 3 * 2);
            if (Data.deep_word <= 250) {
                paint.setAlpha(Data.deep_word += 3);
            }
            canvas.drawText("你已经征服了迷宫！！！", Data.scr_width / 6, Data.scr_height / 4 * 3, paint);
            Data.stop_event = false;
        }
    }


    ////////
    //触屏
    private float x_movescreen = 0;
    private float y_movescreen = 0;
    public static float x_button = 0;
    public static float y_button = 0;


    ///////// on click
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (Data.stop_event) {
            float x_new = event.getX();
            float y_new = event.getY();
            ///
            //button function to control man move
            if (Function.distance(x_new, y_new, Data.x_circlepoint, Data.y_circlepoint) < Data.scr_height / 4 + Data.r_button / 2) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        x_button = x_new;
                        y_button = y_new;
                        break;
                    case MotionEvent.ACTION_MOVE:
                        x_button = x_new;
                        y_button = y_new;
                        ///////x move
                        if (Math.abs(Data.x_button - Data.x_circlepoint) > Math.abs(Data.y_button - Data.y_circlepoint)) {
                            if (Data.button_man_move) {
                                if (Data.x_button - Data.x_circlepoint > 0) {
                                    if (Function.distance(Data.x_button, Data.y_button, Data.x_circlepoint, Data.y_circlepoint) > Data.scr_height / 12) {
                                        Data.direct = 0;
                                        ManClass.man.direct = Data.direct = 0;
                                        if (Init.move(Data.direct)) {
                                            Data.button_man_move = false;
                                            Data.start_move_or_not = true;
                                            ButtonTime bt = new ButtonTime(Data.stop_time, -1, 0);
                                            bt.start();
                                        }
                                    } else {
                                        Data.direct = 0;
                                    }
                                } else {
                                    if (Function.distance(Data.x_button, Data.y_button, Data.x_circlepoint, Data.y_circlepoint) > Data.scr_height / 12) {
                                        Data.direct = 2;
                                        ManClass.man.direct = Data.direct = 2;
                                        if (Init.move(Data.direct)) {
                                            Data.button_man_move = false;
                                            Data.start_move_or_not = true;
                                            ButtonTime bt = new ButtonTime(Data.stop_time, 1, 0);
                                            bt.start();
                                        }
                                    } else {
                                        Data.direct = 0;
                                    }
                                }
                            }
                        }
                        //////y move
                        else {
                            if (Data.button_man_move) {
                                if (Data.y_button - Data.y_circlepoint > 0) {

                                    if (Function.distance(Data.x_button, Data.y_button, Data.x_circlepoint, Data.y_circlepoint) > Data.scr_height / 12) {
                                        Data.direct = 1;
                                        ManClass.man.direct = Data.direct = 1;
                                        if (Init.move(Data.direct)) {
                                            Data.button_man_move = false;
                                            Data.start_move_or_not = true;
                                            ButtonTime bt = new ButtonTime(Data.stop_time, 0, -1);
                                            bt.start();
                                        }
                                    } else {
                                        Data.direct = 0;
                                    }
                                } else {
                                    if (Function.distance(Data.x_button, Data.y_button, Data.x_circlepoint, Data.y_circlepoint) > Data.scr_height / 12) {
                                        Data.direct = 3;
                                        ManClass.man.direct = Data.direct = 3;
                                        if (Init.move(Data.direct)) {
                                            Data.button_man_move = false;
                                            Data.start_move_or_not = true;
                                            ButtonTime bt = new ButtonTime(Data.stop_time, 0, 1);
                                            bt.start();
                                        }
                                    } else {
                                        Data.direct = 0;
                                    }
                                }
                            }
                        }

                        /////// button move
                        if (Function.distance(x_new, y_new, Data.x_button, Data.y_button) <= Data.r_button / 2) {
                            if (Function.distance(x_button, y_button, Data.x_circlepoint, Data.y_circlepoint) < Data.scr_height / 4) {
                                Data.x_button = (int) x_new;
                                Data.y_button = (int) y_new;
                            } else {
                                Function.newPoint(x_button, y_button, Data.x_circlepoint, Data.y_circlepoint, Data.scr_height / 4);
                            }
                        }

                        Function.screenMove();
                        postInvalidate();
                        break;
                    case MotionEvent.ACTION_UP:
                        Data.x_button = Data.x_circlepoint;
                        Data.y_button = Data.y_circlepoint;
                        postInvalidate();
                        break;
                }
            }

            ////////
            //screen move
            else {
                Data.x_button = Data.x_circlepoint;
                Data.y_button = Data.y_circlepoint;
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        x_movescreen = x_new;
                        y_movescreen = y_new;
                        break;
                    case MotionEvent.ACTION_MOVE:
                        Data.place_x += x_new - x_movescreen;
                        Data.place_y += y_new - y_movescreen;
                        y_movescreen = y_new;
                        x_movescreen = x_new;
                        Function.screenMove();
                        postInvalidate();
                        break;
                    case MotionEvent.ACTION_UP:
                        postInvalidate();
                        break;
                }
            }
        }
        return true;
    }


    class ButtonTime extends Thread {
        private int sleep_time;
        private int x, y;

        public ButtonTime(int st, int xt, int yt) {
            sleep_time = st;
            x = xt;
            y = yt;
        }
        @Override
        public void run() {
            try {
                sleep(sleep_time / 4);
                Data.x_man_move_time_state -= x;
                Data.y_man_move_time_state -= y;
                Function.screenMove();
                postInvalidate();
                sleep(sleep_time / 4);
                Data.x_man_move_time_state -= x;
                Data.y_man_move_time_state -= y;
                Function.screenMove();
                postInvalidate();
                sleep(sleep_time / 4);
                Data.x_man_move_time_state -= x;
                Data.y_man_move_time_state -= y;
                Function.screenMove();
                postInvalidate();

                sleep(sleep_time / 4);
                ManClass.man.move(Data.direct);
                Data.x_man_move_time_state -= x;
                Data.y_man_move_time_state -= y;
                Function.screenMove();
                postInvalidate();

            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            Data.x_man_move_time_state = 0;
            Data.y_man_move_time_state = 0;
            Data.button_man_move = true;
        }
    }
}