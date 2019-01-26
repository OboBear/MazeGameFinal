package game.ourmaze.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import game.ourmaze.Data;
import game.ourmaze.R;
import game.ourmaze.activities.BeginActivity;

public class BeginView extends View {
    private Bitmap maze;
    private Bitmap man;
    private Paint paint = new Paint();
    private Bitmap cover;
    private void initDatas() {
        Matrix matrix1 = new Matrix();
        Matrix matrix2 = new Matrix();
        Matrix matrix3 = new Matrix();
        Matrix matrix4 = new Matrix();
        int width = BitmapFactory.decodeStream(getResources().openRawResource(R.drawable.maze)).getWidth();
        int height = BitmapFactory.decodeStream(getResources().openRawResource(R.drawable.maze)).getHeight();
        float scaleWidth = ((float) Data.scr_width * 3 / 4) / width;
        float scaleHeight = ((float) Data.scr_height * 3 / 4) / height;
        matrix1.postScale(scaleWidth, scaleHeight);
        maze = Bitmap.createBitmap(BitmapFactory.decodeStream(getResources().openRawResource(R.drawable.maze)), 0, 0, width, height, matrix1, true);
        width = BitmapFactory.decodeStream(getResources().openRawResource(R.drawable.man)).getWidth();
        height = BitmapFactory.decodeStream(getResources().openRawResource(R.drawable.man)).getHeight();
        scaleWidth = ((float) Data.scr_width / 4) / width;
        scaleHeight = ((float) Data.scr_height / 4 * 11 / 6) / height;
        matrix2.postScale(scaleWidth, scaleHeight);
        man = Bitmap.createBitmap(BitmapFactory.decodeStream(getResources().openRawResource(R.drawable.man)), 0, 0, width, height, matrix2, true);

        ///////////////////////
        //button
        width = BitmapFactory.decodeStream(getResources().openRawResource(R.drawable.start1)).getWidth();
        height = BitmapFactory.decodeStream(getResources().openRawResource(R.drawable.start1)).getHeight();
        scaleWidth = ((float) Data.scr_width / 5) / width;
        scaleHeight = ((float) Data.scr_height / 8) / height;
        matrix3.postScale(scaleWidth, scaleHeight);
        width = BitmapFactory.decodeStream(getResources().openRawResource(R.drawable.cover)).getWidth();
        height = BitmapFactory.decodeStream(getResources().openRawResource(R.drawable.cover)).getHeight();
        scaleWidth = ((float) Data.scr_width) / width;
        scaleHeight = ((float) Data.scr_height) / height;
        matrix4.postScale(scaleWidth, scaleHeight);
        cover = Bitmap.createBitmap(BitmapFactory.decodeStream(getResources().openRawResource(R.drawable.cover)), 0, 0, width, height, matrix4, true);
        new AnimationThread(10, 1).start();
        man_x = Data.scr_width / 3 * 10 / 9;
    }

    public BeginView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initDatas();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(maze, Data.scr_width / 8, Data.scr_height / 12, paint);
        canvas.drawBitmap(man, right_man, Data.scr_height / 2 * 23 / 24, paint);
        if (isManDown) {
            canvas.drawBitmap(man, man_x, down, paint);
        }
        canvas.drawBitmap(man, left_man, Data.scr_height / 2 * 23 / 24, paint);
        canvas.drawBitmap(cover, left_to_right, 0, paint);
    }

    private int down = 0;
    private int left_to_right = 0;

    private int left_man = - 400;
    private int right_man = Data.scr_width;
    private boolean isManDown = false;
    private int man_x;

    class AnimationThread extends Thread {
        private int sleep_time;
        private int choose_animation;

        public AnimationThread(int st, int choose_anim) {
            sleep_time = st;
            this.choose_animation = choose_anim;
        }

        @Override
        public void run() {
            if (choose_animation == 1) {
                try {
                    while (left_to_right <= Data.scr_width) {
                        sleep(sleep_time);
                        left_to_right += 10;
                        postInvalidate();
                    }
                    isManDown = true;
                    while (down < Data.scr_height / 2 * 23 / 24) {
                        sleep(sleep_time);
                        down += 10;
                        postInvalidate();
                    }
                    down = Data.scr_height / 2 * 23 / 24;
                    while (left_man < man_x - 50) {
                        sleep(sleep_time / 5);
                        left_man += 20;
                        postInvalidate();
                    }

                    while (right_man > man_x + 50) {
                        sleep(sleep_time / 5);
                        right_man -= 20;
                        postInvalidate();
                    }
                    while (right_man > man_x || left_man < man_x) {
                        sleep(sleep_time);
                        right_man -= 1;
                        left_man += 1;
                        if (right_man <= man_x) {
                            right_man = man_x;
                        }
                        if (left_man >= man_x) {
                            left_man = man_x;
                        }
                        postInvalidate();
                    }
                    sleep(sleep_time * 50);
                    postInvalidate();
                    sleep(sleep_time * 50);
                    postInvalidate();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Data.x_man_move_time_state = 0;
                Data.y_man_move_time_state = 0;
                Data.button_man_move = true;
            } else if (choose_animation == 2) {
                try {
                    while (right_man < Data.scr_width || left_man > -400 || down > -400) {
                        sleep(sleep_time);
                        right_man += 20;
                        left_man -= 20;
                        down -= 20;
                        if (right_man >= Data.scr_width) {
                            right_man = Data.scr_width;
                        }
                        if (left_man <= -400) {
                            left_man = -400;
                        }
                        if (down <= -400) {
                            down = -400;
                        }
                        postInvalidate();
                    }
                    sleep(500);
                    right_man = man_x;
                    left_man = man_x;
                    down = Data.scr_height / 2 * 23 / 24;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}






