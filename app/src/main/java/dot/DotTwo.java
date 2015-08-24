package dot;

import java.util.Random;
import java.util.Vector;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

public class DotTwo extends Dot {

	public DotTwo(Context context, int color, int endX, int entY) {
		super(context, color, endX, entY);
		pace = 20;
	}

	@Override
	public LittleDot[] initBlast() {
		final int ONE = 20;

		int red = (int) (Math.random() * 255);
		int green = (int) (Math.random() * 255);
		int blue = (int) (Math.random() * 255);

		int col = 0xff000000 | red << 16 | green << 8 | blue;

		Random rand = new Random();
		int blastX = endPoint.x - ONE * circle;
		int blastY = endPoint.y - ONE * circle;

		if (Math.random() < 0.3) {
			for (int i = 0; i < ld.length; i++) {
				ld[i] = new LittleDot(rand.nextInt(ONE * circle * 2) + blastX,
						rand.nextInt(ONE * circle * 2) + blastY, col);
				ld[i].setPace(WALLOP, endPoint.x, endPoint.y);
			}
		} else {
			for (int i = 0; i < ld.length; i++) {
				ld[i] = new LittleDot(rand.nextInt(ONE * circle * 2) + blastX,
						rand.nextInt(ONE * circle * 2) + blastY, color);
				ld[i].setPace(WALLOP, endPoint.x, endPoint.y);
			}
		}

		color = 0xff000000 | 225 << 16 | 203 << 8 | 114;
		size = 10;
		state = 3;
		return ld;
	}

	@Override
	public LittleDot[] blast() {
		// TODO Auto-generated method stub
		final int EVERY = 25;
		Random rand = new Random();
		for (LittleDot aLd : ld) {
			if (circle <= maxCircle / 2) {
				if (aLd.x < endPoint.x) {
					aLd.x -= rand.nextInt(EVERY);
				} else {
					aLd.x += rand.nextInt(EVERY);
				}

				if (aLd.y < endPoint.y) {
					aLd.y -= rand.nextInt(EVERY);
				} else {
					aLd.y += rand.nextInt(EVERY);
				}
			}
			else {
				if (aLd.x < endPoint.x) {
					aLd.x -= rand.nextInt(EVERY);
				} else {
					aLd.x += rand.nextInt(EVERY);
				}

				aLd.y += rand.nextInt(EVERY);
			}
		}
		return ld;
	}

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
			if (circle < 5) {
				circle++;
				this.ld = blast();
				for (LittleDot aLd : ld) {
					mPaint.setColor(aLd.color);
					mPaint.setAlpha(1000);
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
}
