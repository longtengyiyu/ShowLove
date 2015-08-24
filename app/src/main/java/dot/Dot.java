package dot;

import java.util.Random;
import java.util.Vector;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.Paint.FontMetrics;

import fall.love.in.love.R;
import fall.love.in.love.view.Animation;

public abstract class Dot {
	int x = 30;
	int y = 30;

	int color;

	int pace = 30;
	int size = 6;

	final Point endPoint = new Point();

	public int state = 1;
	public int circle = 1;

	public String fireworkCharacter = "";
	public int    fireworkSize = 30;
	
	LittleDot[] ld = new LittleDot[200];

	int WALLOP = 20;
	int GRAVITY = 20;

	Animation mFireAnim = null;
	Context mContext;
	
	int maxCircle;

	public Dot(Context context, int color, int endX, int endY) {
		circle = 1;
		state = 1;
		pace = 25;

		this.x = endX;
		this.y = 800;
		this.color = color;
		endPoint.x = endX;
		endPoint.y = endY;
		mContext = context;
		new loadFireSrc().start();

		maxCircle = new Random().nextInt(6) + 5;
	}

	class loadFireSrc extends Thread {
		public void run() {
			mFireAnim = new Animation(mContext, new int[] { R.drawable.trail1,
					R.drawable.trail2, R.drawable.trail3, R.drawable.trail4,
					R.drawable.trail5, R.drawable.trail6 }, true);
		}
	}


	public void rise() {
		if (mFireAnim != null) {
			y -= pace;
			x = x * 1;

			if (y <= endPoint.y) {
				y = endPoint.y;

			}

			if (x <= endPoint.x) {
				x = endPoint.x;
			}

		}
		
	}

	public boolean whetherBlast() {
		return y <= endPoint.y && x <= endPoint.x;
	}

	public abstract LittleDot[] initBlast();

	public abstract LittleDot[] blast();

	public void myPaint(Canvas canvas, Vector<Dot> lList) {
		Paint mPaint = new Paint();
		mPaint.setColor(color);
		RectF oval = new RectF(x, y, x + size, y + size);
		if (state == 1) {
			if (mFireAnim != null) {
				mFireAnim.DrawAnimation(canvas, mPaint, x, y);
			}
		}
		if (state == 2) {
			canvas.drawOval(oval, mPaint);
			LittleDot[] ld2 = initBlast();
			ld = new LittleDot[ld2.length];
			ld = ld2;

			for (int i = 0; i < ld.length / 4; i++) {
				mPaint.setColor(ld[i].color);
				oval = new RectF(ld[i].x, ld[i].y, ld[i].x + 2, ld[i].y + 2);
				canvas.drawOval(oval, mPaint);
			}
			
			//state = 3;
			state = 5;
		} 
		else if (state == 5) {
			if ((!fireworkCharacter.equals("")) && (fireworkSize > 10)){
				int[][] points = getCharacterMatrix(fireworkCharacter,fireworkSize);
				int scale = 1;
				
				while(scale < 5){
					int left = x - points.length * scale / 2;
					int top = y - points.length * scale / 2;
					
					mPaint.setColor(color);
					for(int i = 0;i < points.length;i ++){
						for(int j = 0;j < points[i].length;j ++){
							if (points[i][j] == 1){
								oval = new RectF(left + i * scale, top + j * scale, 
										left + (i + 1) * scale, top + (j + 1) * scale);
								canvas.drawOval(oval, mPaint);
							}
						}
					}
					scale ++;
				}
				
				mPaint.setAlpha(20 + (int) (Math.random() * 0xff));
				int left = x - points.length * scale / 2;
			    int top = y - points.length * scale / 2;
				for(int i = 0;i < points.length;i ++){
					for(int j = 0;j < points[i].length;j ++){
						if (points[i][j] == 1){
							oval = new RectF(left + i * scale, top + j * scale, 
										left + (i + 1) * scale, top + (j + 1) * scale);
							canvas.drawOval(oval, mPaint);
						}
					}
				}
			}
			
			state = 3;
		} 
		else if (state == 3) {
			if (circle <= maxCircle) {
				circle++;
				this.ld = blast();
				for (LittleDot aLd : ld) {
					mPaint.setColor(aLd.color);
					oval = new RectF(aLd.x, aLd.y, aLd.x + 2, aLd.y + 2);
					canvas.drawOval(oval, mPaint);
				}
			} else {
				for (LittleDot aLd : ld) {
					mPaint.setColor(aLd.color);
					mPaint.setAlpha(20 + (int) (Math.random() * 0xff));
					oval = new RectF(aLd.x, aLd.y, aLd.x + 2, aLd.y + 2);
					canvas.drawOval(oval, mPaint);
				}
			}

		} else if (state == 4) {
			synchronized (lList) {
				lList.remove(this);
			}
		}
	}

	public String toString() {
		return + x + ",y=" + y + "," + endPoint.x
				+ ",y=" + endPoint.y + color;
	}
	
	public int[][] getCharacterMatrix(String strCharacter,int intWidth){
		Bitmap cBmp = Bitmap.createBitmap(intWidth, intWidth, 
				Bitmap.Config.RGB_565);  
		Canvas c = new Canvas(cBmp);  
		c.drawColor(Color.BLACK);
		
		Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);  
		paint.setTextSize(intWidth - 2);  
		
		Typeface tf = Typeface.DEFAULT_BOLD;  
		paint.setTypeface(tf);  
		paint.setColor(Color.WHITE);  
		
		FontMetrics fm = paint.getFontMetrics();  
		c.drawText(strCharacter, 0, intWidth + fm.top - fm.ascent, paint);  
		c.save();

		int[][] result = new int[intWidth][intWidth];
		for (int i = 0; i < intWidth; i++) {
		    for (int j = 0; j < intWidth; j++) {
		    	int color = cBmp.getPixel(i, j);
		    	
		    	int r = Color.red(color);
		    	int g = Color.green(color);
		    	int b = Color.blue(color);
		    	
		    	if ((r == 0) && (g == 0) && (b == 0)){
		    		result[i][j] = 0;
		    	}
		    	else{
		    		result[i][j] = 1;
		    	}
		    }
		}
    
		return result;
	}
}
