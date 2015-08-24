package dot;

import java.util.Random;
import java.util.Vector;

import android.content.Context;
import android.graphics.Canvas;

public class DotFour extends Dot {

	public DotFour(Context context, int color, int endX, int entY) {
		super(context, color, endX, entY);
		// TODO Auto-generated constructor stub
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
		if (circle >= maxCircle - 1) {
			for (LittleDot aLd : ld) {
				aLd.setPlace();
			}
		} else {
			for (int i = 0; i < ld.length; i++) {
				// ��Ȧ����黨�Ϳ�ʼ��׹
				ld[i].setPace(ld[i].horizontal, GRAVITY);
				ld[i].setPlace();
			}
		}
		if (circle >= maxCircle) {

			for (int i = 0; i < ld.length; i++) {
				int red = (int) (Math.random() * 255);
				int green = (int) (Math.random() * 255);
				int blue = (int) (Math.random() * 255);
				int col = 0xff000000 | red << 16 | green << 8 | blue;
				ld[i].color = col;
			}
		}
		return ld;
	}

	public void myPaint(Canvas canvas, Vector<Dot> lList) {

		super.myPaint(canvas, lList);
	}
}
