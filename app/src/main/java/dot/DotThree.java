package dot;

import java.util.Random;
import java.util.Vector;

import android.content.Context;
import android.graphics.Canvas;

public class DotThree extends Dot {

	public DotThree(Context context, int color, int endX, int entY) {
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
		final int EVERY = 40;
		Random rand = new Random();
		if (circle >= maxCircle) {

			for (LittleDot aLd : ld) {
				int red = (int) (Math.random() * 255);
				int green = (int) (Math.random() * 255);
				int blue = (int) (Math.random() * 255);
				int col = 0xff000000 | red << 16 | green << 8 | blue;
				aLd.color = col;
			}
		}

		for (LittleDot aLd : ld) {
			aLd.x += rand.nextInt(EVERY) - EVERY / 2;
			aLd.y += rand.nextInt(EVERY) - EVERY / 2;

		}
		return ld;
	}

	public void myPaint(Canvas canvas, Vector<Dot> lList) {
		super.myPaint(canvas, lList);
	}

}
