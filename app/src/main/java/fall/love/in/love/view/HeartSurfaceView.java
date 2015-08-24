package fall.love.in.love.view;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.Xfermode;
import android.os.Build;
import android.os.Handler;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

import fall.love.in.love.R;
import fall.love.in.love.app.AppContact;
import fall.love.in.love.utils.BitmapCache;
import fall.love.in.love.utils.Utils;

/**
 * Description:
 * Author:    Oscar
 * Version    V1.0
 * Date:      2015/7/13
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 2015/7/13        Oscar           1.0                    1.0
 * Why & What is modified:
 */
public class HeartSurfaceView extends SurfaceView implements SurfaceHolder.Callback {

    private BitmapCache bitmapcache;
    private SurfaceHolder holder;
    private Context mContext;
    private boolean isallstop;
    private int yadd_1200 = 100;

    private int w;
    private int h;

    public HeartSurfaceView(Context context) {
        super(context);
    }

    public HeartSurfaceView(Context context, int s_w, int s_h, Handler handler) {
        super(context);
        // TODO 自动生成的构造函数存根
        this.setFocusable(true);
        this.setKeepScreenOn(true);
        this.w = s_w;
        this.h = s_h;
        this.mContext = context;
        this.bitmapcache = BitmapCache.getInstance();
        this.holder = getHolder();
        this.holder.addCallback(this);
        //透明
        setZOrderOnTop(true);
        holder.setFormat(PixelFormat.TRANSPARENT);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    public void showBackground() // 只是用来初始化，无多大意义
    {
        this.holder = getHolder();
        this.holder.addCallback(this);
    }

    // 画心
    public void showHeart() {
        new ShowHeart(this.holder, "showback", this.w, this.h).start();
    }

    //文字
    public void showText() {
        new ShowText(mContext.getString(R.string.content), w / 6 - 100, h / 8).start();
    }

    class ShowText extends Thread {
        String content;
        int sw;
        int sh;
        int size = 30;

        public ShowText(String content, int sw, int sh) {
            this.content = content;
            this.sw = sw;
            this.sh = sh;
        }

        @Override
        public void run() {
            super.run();
            drawText();
        }

        @TargetApi(Build.VERSION_CODES.HONEYCOMB)
        private void drawText() {
            Paint paint = new Paint();
            size = Utils.getScreenHeight((Activity) mContext) >= 1080 ? 35 : 20; //文字屏幕适配
            paint.setTextSize(size);
            Log.d("drawText", "ScreenWidth size == " + Utils.getScreenHeight((Activity) mContext) + " " + size);
            Xfermode xFermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER);
            paint.setXfermode(xFermode);
            String[] allt = content.split("\n");
            for (int i = 0; i < allt.length && !isallstop; i++) {
                int max = allt[i].length();
                for (int count = 1; count < max + 1 && !isallstop; count++) {
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    synchronized (holder) {
                        Canvas c = null;
                        try {
                            if (allt[i].contains("*") || allt[i].contains("//")) {
                                paint.setColor(Color.GRAY);
                            } else {
                                paint.setColor(Color.BLACK);
                            }
                            c = holder.lockCanvas(new Rect(0, sh - size * (i + 1) + 10 * i, w, sh + size * (i + 1) + 10 * i));
                            c.drawColor(Color.TRANSPARENT, android.graphics.PorterDuff.Mode.OVERLAY);
                            String tm_old = allt[i].substring(0, count - 1);
                            String tm = allt[i].substring(0, count);
                            c.drawText(tm_old, sw, sh + size * i + 10 * i, paint);
                            c.drawText(tm, sw, sh + size * i + 10 * i, paint);
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            try {
                                if (c != null) {
                                    holder.unlockCanvasAndPost(c);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                    }
                    synchronized (holder) {
                        Canvas c = null;
                        try {
                            c = holder.lockCanvas(new Rect(0, sh - size * (i + 1) + 10 * i, w, sh + size * (i + 1) + 10 * i));
                            c.drawColor(Color.TRANSPARENT, android.graphics.PorterDuff.Mode.OVERLAY);
                            String tm_old = allt[i].substring(0, count - 1);
                            String tm = allt[i].substring(0, count);
                            c.drawText(tm_old, sw, sh + size * i + 10 * i, paint);
                            c.drawText(tm, sw, sh + size * i + 10 * i, paint);
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            try {
                                if (c != null) {
                                    holder.unlockCanvasAndPost(c);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        }
    }

    class ShowHeart extends Thread {
        private SurfaceHolder holder;
        int sh;
        int sw;

        public ShowHeart(SurfaceHolder holder, String threadname, int sw, int sh) {
            this.holder = holder;
            setName(threadname);
            this.sw = sw;
            this.sh = sh;
        }

        int iStartX, iStartY, loveStartX, loveStartY, uStartX, uStartY;

        @Override
        public void run() {

            //屏幕适配
            iStartX = -50 + sw / 2;
            iStartY = 50;


            loveStartX = sw / 2 - 16;
            loveStartY = sh / 2 - 68;

            uStartX = -94 + sw / 2;
            uStartY = 150 + sh / 2;

            if (sh / 2 > 180 + 150 + 118 + 20) uStartY = 150 + sh / 2 + 20;
            if (sh / 2 > 180 + 150 + 118 + 40) uStartY = 150 + sh / 2 + 40;
            if (sh / 2 > 180 + 150 + 118 + 60) uStartY = 150 + sh / 2 + 60;
            if (sh >= 1200) {
                iStartY = iStartY + yadd_1200;
                uStartY = uStartY + yadd_1200;
            }

            System.out.println("create1");
            this.holder.setKeepScreenOn(true);
            runHuaHeart();
            runMM();
        }

        private void runMM() {
//            int start_x = sw/2-100;
//            int start_y = sh/2-90;
            int start_x = sw - 500;
            int start_y = sh / 2 - 90;
            Bitmap m_m = bitmapcache.getBitmap(R.drawable.h_m_m, mContext);
            int pic_w = m_m.getWidth();
            int pic_h = m_m.getHeight();
            Rect r = new Rect(start_x, start_y, start_x + pic_w, start_y + pic_h);
            Paint p = new Paint();
            for (int i = 1; i < 51 && !isallstop; i++) {
                try {
                    Thread.sleep(500); //画内心速度
                } catch (InterruptedException e) {
                    // TODO 自动生成的 catch 块
                    e.printStackTrace();
                }
                p.setAlpha(i * 2);
                synchronized (holder) {
                    Canvas c = null;
                    try {
                        c = holder.lockCanvas(r);
                        c.drawBitmap(m_m, start_x, start_y, p);  //画中间的心
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            if (c != null) {
                                holder.unlockCanvasAndPost(c);// 结束锁定画图，并提交改变。
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }

        private void runHuaHeart() {
            // TODO 自动生成的方法存根
//            int startx = sw / 2 - 16, starty = sh / 2 - 68;
            int startx = sw - 416, starty = sh / 2 - 68;
            int maxh = 100;
            int y_dao = starty;
            double begin = 10; // 起始位置
            Random rm = new Random();
            int old_num = -1;
            float old_xx = 0, old_yy = 0;
            for (int i = 0; i < maxh && !isallstop; i++) {
                try {
                    Thread.sleep(150); //speed for heart
                } catch (InterruptedException e1) {
                    // TODO 自动生成的 catch 块
                    e1.printStackTrace();
                }

                int hua_num = rm.nextInt(18);
                Bitmap bit = bitmapcache.getBitmap(AppContact.HEART_ALL[hua_num], mContext);
                begin = begin + 0.2;  //密度
                double b = begin / Math.PI;
                double a = 13.5 * (16 * Math.pow(Math.sin(b), 3));  //这里的13.5可以控制大小
                double d = -13.5
                        * (13 * Math.cos(b) - 5 * Math.cos(2 * b) - 2
                        * Math.cos(3 * b) - Math.cos(4 * b));
                synchronized (holder) {
                    Canvas c = null;
                    try {
                        float xx = (float) a;
                        float yy = (float) d;

                        c = holder.lockCanvas(new Rect(
                                (int) (startx + xx - 40),
                                (int) (starty + yy - 40),
                                (int) (startx + xx + 40),
                                (int) (starty + yy + 40)));
                        Paint p = new Paint(); // 创建画笔
                        p.setColor(Color.RED);
                        //画上一个，要不然会闪烁
                        if (old_num != -1) {
                            Bitmap bb = bitmapcache.getBitmap(AppContact.HEART_ALL[old_num], mContext);c.drawBitmap(bb, startx + old_xx, starty + old_yy, p);
                        }
                        c.drawBitmap(bit, startx + xx, starty + yy, p);
                        old_num = hua_num;
                        old_xx = xx;
                        old_yy = yy;
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            if (c != null) {
                                holder.unlockCanvasAndPost(c);// 结束锁定画图，并提交改变。
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }
}
