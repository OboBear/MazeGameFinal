package game.ourmaze.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;

import game.ourmaze.Data;
import game.ourmaze.Init;
import game.ourmaze.R;
import game.ourmaze.views.BeginView;

public class BeginActivity extends Activity implements View.OnClickListener {

    BeginView beginview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        setContentView(R.layout.beginview);
        beginview = findViewById(R.id.beginview);
        findViewById(R.id.iv_start).setOnClickListener(this);
        findViewById(R.id.iv_quit).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.iv_start) {
            Intent intent = new Intent();
            intent.setClass(BeginActivity.this, GameActivity.class);
            startActivity(intent);
        } else if (id == R.id.iv_quit) {
            finish();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            finish();
            Data.choose_num = 0;
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void init() {
        Data.init();
        Init.init_data();
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        Data.scr_height = dm.heightPixels;
        Data.scr_width = dm.widthPixels;
        Data.place_y = 0;
        Data.place_x = 0;
        Data.num = 0;
        Data.choose_num = 0;
        Data.start_move_or_not = false;
        Data.direct = 3;
        ////////////
        Data.num = 0;
        Data.choose_num = 0;
        // the size of bar ( pixel )
        Data.unit_l = (Data.scr_width / 10);
        Data.maze_l = Data.maze_a * Data.unit_l;
        // the size of maze ( pixel )
        Data.maze_h = Data.maze_b * Data.unit_l;

        Data.y_toolset = 0;
        Data.x_circlepoint = Data.scr_width - Data.scr_height / 4;
        Data.y_circlepoint = Data.scr_height * 3 / 4;
        Data.x_button = Data.x_circlepoint;
        Data.y_button = Data.y_circlepoint;
        Data.r_button = Data.unit_l * 5 / 4;
        ///man move state;
        Data.x_man_move_time_state = 0;
        Data.y_man_move_time_state = 0;

        ////////////////////////
        //load picture
        Data.bitmap = BitmapFactory.decodeStream(getResources().openRawResource(R.drawable.floor));
        Data.width = Data.bitmap.getWidth();
        Data.height = Data.bitmap.getHeight();
        Data.scaleWidth = ((float) Data.unit_l) / Data.width;
        Data.scaleHeight = ((float) Data.unit_l) / Data.height;
        Matrix matrix = new Matrix();
        matrix.postScale(Data.scaleWidth, Data.scaleHeight);
        Data.matrix = matrix;
        Data.floor = Bitmap.createBitmap(Data.bitmap, 0, 0, Data.width, Data.height, matrix, true);
        Data.bitmap = BitmapFactory.decodeStream(getResources().openRawResource(R.drawable.wall));
        Data.wall = Bitmap.createBitmap(Data.bitmap, 0, 0, Data.width, Data.height, matrix, true);
        Data.bitmap = BitmapFactory.decodeStream(getResources().openRawResource(R.drawable.door));
        Data.door = Bitmap.createBitmap(Data.bitmap, 0, 0, Data.width, Data.height, matrix, true);
        Data.bitmap = BitmapFactory.decodeStream(getResources().openRawResource(R.drawable.start));
        Data.start = Bitmap.createBitmap(Data.bitmap, 0, 0, Data.width, Data.height, matrix, true);
        Data.bitmap = BitmapFactory.decodeStream(getResources().openRawResource(R.drawable.monst1));
        Data.monst1 = Bitmap.createBitmap(Data.bitmap, 0, 0, Data.width, Data.height, matrix, true);
        Data.bitmap = BitmapFactory.decodeStream(getResources().openRawResource(R.drawable.monst2));
        Data.monst2 = Bitmap.createBitmap(Data.bitmap, 0, 0, Data.width, Data.height, matrix, true);
        //////load man

        Data.bitmap = BitmapFactory.decodeStream(getResources().openRawResource(R.drawable.forw0));
        Data.forw[0] = Bitmap.createBitmap(Data.bitmap, 0, 0, Data.width, Data.height, matrix, true);
        Data.forw[2] = Bitmap.createBitmap(Data.bitmap, 0, 0, Data.width, Data.height, matrix, true);
        Data.forw[4] = Bitmap.createBitmap(Data.bitmap, 0, 0, Data.width, Data.height, matrix, true);
        Data.bitmap = BitmapFactory.decodeStream(getResources().openRawResource(R.drawable.forw1));
        Data.forw[1] = Bitmap.createBitmap(Data.bitmap, 0, 0, Data.width, Data.height, matrix, true);
        Data.bitmap = BitmapFactory.decodeStream(getResources().openRawResource(R.drawable.forw2));
        Data.forw[3] = Bitmap.createBitmap(Data.bitmap, 0, 0, Data.width, Data.height, matrix, true);

        Data.bitmap = BitmapFactory.decodeStream(getResources().openRawResource(R.drawable.up0));
        Data.up[0] = Bitmap.createBitmap(Data.bitmap, 0, 0, Data.width, Data.height, matrix, true);
        Data.up[2] = Bitmap.createBitmap(Data.bitmap, 0, 0, Data.width, Data.height, matrix, true);
        Data.up[4] = Bitmap.createBitmap(Data.bitmap, 0, 0, Data.width, Data.height, matrix, true);
        Data.bitmap = BitmapFactory.decodeStream(getResources().openRawResource(R.drawable.up1));
        Data.up[1] = Bitmap.createBitmap(Data.bitmap, 0, 0, Data.width, Data.height, matrix, true);
        Data.bitmap = BitmapFactory.decodeStream(getResources().openRawResource(R.drawable.up2));
        Data.up[3] = Bitmap.createBitmap(Data.bitmap, 0, 0, Data.width, Data.height, matrix, true);

        Data.bitmap = BitmapFactory.decodeStream(getResources().openRawResource(R.drawable.left0));
        Data.left[0] = Bitmap.createBitmap(Data.bitmap, 0, 0, Data.width, Data.height, matrix, true);
        Data.left[2] = Bitmap.createBitmap(Data.bitmap, 0, 0, Data.width, Data.height, matrix, true);
        Data.left[4] = Bitmap.createBitmap(Data.bitmap, 0, 0, Data.width, Data.height, matrix, true);
        Data.bitmap = BitmapFactory.decodeStream(getResources().openRawResource(R.drawable.left1));
        Data.left[1] = Bitmap.createBitmap(Data.bitmap, 0, 0, Data.width, Data.height, matrix, true);
        Data.bitmap = BitmapFactory.decodeStream(getResources().openRawResource(R.drawable.left2));
        Data.left[3] = Bitmap.createBitmap(Data.bitmap, 0, 0, Data.width, Data.height, matrix, true);

        Data.bitmap = BitmapFactory.decodeStream(getResources().openRawResource(R.drawable.right0));
        Data.right[0] = Bitmap.createBitmap(Data.bitmap, 0, 0, Data.width, Data.height, matrix, true);
        Data.right[2] = Bitmap.createBitmap(Data.bitmap, 0, 0, Data.width, Data.height, matrix, true);
        Data.right[4] = Bitmap.createBitmap(Data.bitmap, 0, 0, Data.width, Data.height, matrix, true);
        Data.bitmap = BitmapFactory.decodeStream(getResources().openRawResource(R.drawable.right1));
        Data.right[1] = Bitmap.createBitmap(Data.bitmap, 0, 0, Data.width, Data.height, matrix, true);
        Data.bitmap = BitmapFactory.decodeStream(getResources().openRawResource(R.drawable.right2));
        Data.right[3] = Bitmap.createBitmap(Data.bitmap, 0, 0, Data.width, Data.height, matrix, true);

        /////load tools picture
        Data.scaleWidth = ((float) Data.scr_width / 14) / Data.width;
        Data.scaleHeight = ((float) Data.scr_width / 14) / Data.height;
        matrix = new Matrix();
        matrix.postScale(Data.scaleWidth, Data.scaleHeight);

        ////////load buttonbox
        Data.bitmap = BitmapFactory.decodeStream(getResources().openRawResource(R.drawable.circle));
        Data.width = Data.bitmap.getWidth();
        Data.height = Data.bitmap.getHeight();
        Data.scaleWidth = ((float) Data.scr_height / 2) / Data.width;
        Data.scaleHeight = ((float) Data.scr_height / 2) / Data.height;
        matrix = new Matrix();
        matrix.postScale(Data.scaleWidth, Data.scaleHeight);
        Data.circle = Bitmap.createBitmap(Data.bitmap, 0, 0, Data.width, Data.height, matrix, true);

        ////load button
        Data.bitmap = BitmapFactory.decodeStream(getResources().openRawResource(R.drawable.button));
        Data.width = Data.bitmap.getWidth();
        Data.height = Data.bitmap.getHeight();
        Data.scaleWidth = ((float) Data.r_button) / Data.width;
        Data.scaleHeight = ((float) Data.r_button) / Data.height;
        matrix = new Matrix();
        matrix.postScale(Data.scaleWidth, Data.scaleHeight);
        Data.button = Bitmap.createBitmap(Data.bitmap, 0, 0, Data.width, Data.height, matrix, true);
    }

}
