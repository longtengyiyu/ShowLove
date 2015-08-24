package dot;

import java.util.Random;
import java.util.Vector;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

public class DotOne extends Dot {

	public DotOne(Context context, int color, int endX, int entY) {
		super(context, color, endX, entY);
		pace = 20;
	}

	@Override
	public LittleDot[] initBlast() {
		// TODO Auto-generated method stub
		final int ONE = 20;

		int red = (int) (Math.random() * 255);
		int green = (int) (Math.random() * 255);
		int blue = (int) (Math.random() * 255);

		int col = 0xff000000 | red << 16 | green << 8 | blue;

		Random rand = new Random();
		int blastX = endPoint.x - ONE * circle;
		int blastY = endPoint.y - ONE * circle;

		if (Math.random() < 0.5) {
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
		if (circle <= 4) {
			for (LittleDot aLd : ld) {
				aLd.setPlace();
			}
		}
		return ld;
	}

	public void myPaint(Canvas canvas, Vector<Dot> lList) {
		super.myPaint(canvas,lList);
		Paint mPaint = new Paint();
		int col = 0xff000000 | 220 << 16 | 220 << 8 | 220;
		if (this.state == 3) {
			if (Math.random() < 0.5) {
				mPaint.setColor(col);
			} else {
				mPaint.setColor(color);
			}
			for (LittleDot aLd : ld) {
				canvas.drawLine(x, y, aLd.x, aLd.y, mPaint);
			}
		}
	}
}
