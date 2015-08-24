package dot;

import android.content.Context;

public class DotFactory {

	public DotFactory() {

	}

	public String fireworkText = "生日快乐";
	public int    fireworkSize = 30;
	
	public Dot makeDot(Context context, int kind, int x, int y) {

		int red = (int) (Math.random() * 255);
		int green = (int) (Math.random() * 255);
		int blue = (int) (Math.random() * 255);

		int col = 0xff000000 | red << 16 | green << 8 | blue;

		Dot dot = null;

		if (Math.random() < 0.5) {

			switch (kind % 6 + 1) {
			case 1:
				dot = new DotOne(context, col, x, y);
				break;
			case 2:
				dot = new DotTwo(context, col, x, y);
				break;
			case 3:
				dot = new DotThree(context, col, x, y);
				break;
			case 4:
				dot = new DotFour(context, col, x, y);
				break;
			case 5:
				dot = new DotFive(context, col, x, y);
				break;
			case 6:
				dot = new DotSix(context, col, x, y);
				break;
			}

			if (dot != null){
				dot.fireworkCharacter = String.valueOf(fireworkText.charAt((int) (Math.random() * fireworkText.length())));
				dot.fireworkSize = fireworkSize;
			}
		}
		else {
			dot = new DotAnimFW(context, col, x, y);
		}
		return dot;
	}
}
