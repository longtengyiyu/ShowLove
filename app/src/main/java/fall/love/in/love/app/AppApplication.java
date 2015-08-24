package fall.love.in.love.app;

import android.app.Application;
import android.content.Context;

import fall.love.in.love.utils.SharedPreferencesHelper;

/**
 * Description:
 * Author:    Oscar
 * Version    V1.0
 * Date:      2015/7/14
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 2015/7/14        Oscar           1.0                    1.0
 * Why & What is modified:
 */
public class AppApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        setUpSharePreference(getApplicationContext());
    }

    private void setUpSharePreference(Context context){
        SharedPreferencesHelper mSharedPreferencesHelper = SharedPreferencesHelper.getInstance();
        mSharedPreferencesHelper.Builder(context);
    }
}
