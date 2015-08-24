package fall.love.in.love.ui;

import android.app.Activity;
import android.app.Dialog;
import android.app.Instrumentation;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.InjectView;
import fall.love.in.love.R;
import fall.love.in.love.app.AppContact;
import fall.love.in.love.app.Words;
import fall.love.in.love.utils.MediaPlayUtils;
import fall.love.in.love.utils.SharedPreferencesHelper;
import fall.love.in.love.utils.Utils;
import fall.love.in.love.view.SnowView;

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
public class TextActivity extends Activity {
    @InjectView(R.id.snow_view)
    SnowView mSnowView;
    private MediaPlayUtils mMediaPlayUtils;
    private Dialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text);
        ButterKnife.inject(this);
        onPlayMusic();
        onSnowFly();
        showGuide();
        mSnowView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
//                onShowDialog();
                Intent intent = new Intent(TextActivity.this, HeartActivity.class);
                startActivity(intent);
                return false;
            }
        });
        Toast.makeText(this, "Don't Touch everywhere when input message\n键盘输入，请别触摸任何地方", Toast.LENGTH_LONG).show();
    }

    private void onPlayMusic(){
        mMediaPlayUtils = new MediaPlayUtils();
        mMediaPlayUtils.InitMediaPlay(this, R.raw.singtoyou);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMediaPlayUtils.play();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMediaPlayUtils.Pause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mMediaPlayUtils.Pause();
    }

    private void onSnowFly(){
        mSnowView.setEnabled(false);
        mSnowView.LoadSnowImage();
        mSnowView.setViewSize(Utils.getScreenWidth(this), Utils.getScreenHeight(this));
        updateSnowView();
    }

    private void updateSnowView(){
        mSnowView.addRandomSnow();
        mRedrawHandler.sleep(600);
    }

    /*
	 * 负责做界面更新工作 ，实现下雪
	 */
    private RefreshHandler mRedrawHandler = new RefreshHandler();

    class RefreshHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
            //snow.addRandomSnow();
            mSnowView.invalidate();
            sleep(100);
        }
        public void sleep(long delayMillis) {
            this.removeMessages(0);
            sendMessageDelayed(obtainMessage(0), delayMillis);
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMediaPlayUtils.stop();
        handler.removeMessages(0);
    }

    private void showGuide(){
        new Thread( new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                for( int keycode : Words.KEY_CODES){
                    try {
                        typeIn(keycode);
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                handler.sendEmptyMessage(0);
            }
        }).start();
    }

    private Handler handler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch(msg.what){
                case 0:
                    mSnowView.setEnabled(true);
//                    onShowDialog();
                    Toast.makeText(TextActivity.this, "触摸任何地方进入下一个页面", Toast.LENGTH_LONG).show();
                    break;
            }
        }
    };

    private void typeIn(final int KeyCode ){
        try {
            Instrumentation inst = new Instrumentation();
            inst.sendKeyDownUpSync(KeyCode);
        } catch (Exception e) {
            Log.e("Exception when sendKeyDownUpSync", e.toString());
        }
    }

    private void onShowDialog(){

        if (mDialog == null){
            mDialog = new Dialog(this);
        }
        View view = LayoutInflater.from(this).inflate(R.layout.item_dialog, null);
        TextView tvNo = (TextView) view.findViewById(R.id.no);
        TextView tvYes = (TextView) view.findViewById(R.id.yes);
        String isAgree = SharedPreferencesHelper.getInstance().getString(AppContact.IS_AGREE);
        switch (isAgree){
            case AppContact.NO:
                tvYes.setVisibility(View.GONE);
                tvNo.setTextSize(16);
                break;
            case AppContact.YES:
                tvNo.setVisibility(View.GONE);
                break;
        }

        tvNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
                Intent intent = new Intent(TextActivity.this, JokeActivity.class);
                startActivity(intent);
                SharedPreferencesHelper.getInstance().putString(AppContact.IS_AGREE, AppContact.NO);
            }
        });
        tvYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
                Intent intent = new Intent(TextActivity.this, HeartActivity.class);
                startActivity(intent);
                SharedPreferencesHelper.getInstance().putString(AppContact.IS_AGREE, AppContact.YES);
            }
        });

        mDialog.setTitle("Love from this time");
        mDialog.setContentView(view);
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.show();
    }
}
