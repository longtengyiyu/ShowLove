package fall.love.in.love.view;

import java.util.Random;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.View;

import fall.love.in.love.R;

public class SnowView extends View {
    // 雪花图片
    Bitmap bitmap_snows[] = new Bitmap[6];
    // 画笔
    private final Paint mPaint = new Paint();
    // 随即生成器
    private static final Random RNG = new Random();
    // 花的位置
    private Coordinate[] snows = new Coordinate[10];
    // 屏幕的高度和宽度
    int view_height = 0;
    int view_width = 0;

    /**
     * 构造器
     *
     *
     */
    public SnowView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public SnowView(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    /**
     * 加载天女散花的花图片到内存中
     *
     */
    public void LoadSnowImage() {
        Resources r = this.getContext().getResources();
        bitmap_snows[0] = ((BitmapDrawable) r.getDrawable(R.drawable.snow0))
                .getBitmap();
        bitmap_snows[1] = ((BitmapDrawable) r.getDrawable(R.drawable.snow1))
                .getBitmap();
        bitmap_snows[2] = ((BitmapDrawable) r.getDrawable(R.drawable.snow2))
                .getBitmap();
        bitmap_snows[3] = ((BitmapDrawable) r.getDrawable(R.drawable.snow3))
                .getBitmap();
        bitmap_snows[4] = ((BitmapDrawable) r.getDrawable(R.drawable.snow4))
                .getBitmap();
        bitmap_snows[5] = ((BitmapDrawable) r.getDrawable(R.drawable.snow5))
                .getBitmap();
    }

    /**
     * 设置当前窗体的实际高度和宽度
     *
     */
    public void setViewSize(int height, int width) {
        view_height = height - 100;
        view_width = width - 50;

    }

    /**
     * 随机的生成花朵的位置
     *
     */
    public void addRandomSnow() {
        snows[0] = new Coordinate(RNG.nextInt(view_width), 0);
        snows[1] = new Coordinate(RNG.nextInt(view_width), 0);
        snows[2] = new Coordinate(RNG.nextInt(view_width), 0);
        snows[3] = new Coordinate(RNG.nextInt(view_width), 0);
        snows[4] = new Coordinate(RNG.nextInt(view_width), 0);
        snows[5] = new Coordinate(RNG.nextInt(view_width), 0);
        snows[6] = new Coordinate(RNG.nextInt(view_width), 0);
        snows[7] = new Coordinate(RNG.nextInt(view_width), 0);
        snows[8] = new Coordinate(RNG.nextInt(view_width), 0);
        snows[9] = new Coordinate(RNG.nextInt(view_width), 0);
    }

    /* 内嵌坐标对象 */
    private class Coordinate {
        public int x;
        public int y;

        public Coordinate(int newX, int newY) {
            x = newX;
            y = newY;
        }

        // public boolean equals(Coordinate other) {
        // if (x == other.x && y == other.y) {
        // return true;
        // }
        // return false;
        // }

        @Override
        public String toString() {
            return "Coordinate: [" + x + "," + y + "]";
        }
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int x = 0; x < 10; x += 1) {
            if (snows[x].y >= view_height) {
                snows[x].y = 0;
                addRandomSnow();
            }
            // 雪花下落的速度
            snows[x].y += 5;
            //雪花飘动的效果
            if (RNG.nextBoolean()) {
                // 随机产生一个数字，让雪花有水平移动的效果
                int rng = RNG.nextInt(3);
                snows[x].x += 2 - rng;
            }
            canvas.drawBitmap(bitmap_snows[x / 6], ((float) snows[x].x),
                    ((float) snows[x].y), mPaint);
        }

    }

}
