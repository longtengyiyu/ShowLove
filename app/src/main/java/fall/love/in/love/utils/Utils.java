package fall.love.in.love.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.text.InputType;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;

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
public class Utils {
    private static Utils util;
    public static Utils init() {
        if (util == null)
            util = new Utils();
        return util;
    }

    /**
     * 获取屏幕的宽度
     *
     * @param context activity
     * @return int
     */
    public static int getScreenWidth(Activity context) {
        DisplayMetrics dm = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
    }

    /**
     * 获取屏幕的高度
     *
     * @param context activity
     * @return int
     */
    public static int getScreenHeight(Activity context) {
        DisplayMetrics dm = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.heightPixels;
    }

    public static Bitmap getBitmap(String path) {

        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inPreferredConfig = Bitmap.Config.RGB_565;
        opt.inPurgeable = true;
        opt.inInputShareable = true;
        return new BitmapFactory().decodeFile(path, opt);
    }

    public InputStream getAssetsInputStream(Context paramContext, String paramString) throws IOException {
        return paramContext.getResources().getAssets().open(paramString);
    }
}
