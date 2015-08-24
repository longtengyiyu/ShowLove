package fall.love.in.love.ui;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import fall.love.in.love.R;
import fall.love.in.love.app.AppContact;
import fall.love.in.love.utils.MediaPlayUtils;
import fall.love.in.love.utils.Utils;
import fall.love.in.love.view.HeartSurfaceView;

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
public class HeartActivity extends Activity {

    @InjectView(R.id.frame_layout)
    FrameLayout mFrameLayout;
    private MediaPlayUtils mMediaPlayUtils;
    private HeartSurfaceView mHeartSurfaceView;
    private Dialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_heart);

        ButterKnife.inject(this);
        setSurfaceView();
        onPlayMusic();

        handler.sendEmptyMessageDelayed(AppContact.SHOW_HEART, 3000L);
        handler.sendEmptyMessageDelayed(AppContact.SHOW_TEXT, 1000L);
//        handler.sendEmptyMessageDelayed(AppContact.SHOW_ARROW, 10000L);

        mFrameLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
//                onCallMe();
                return false;
            }
        });
    }

    private void setSurfaceView(){
        mHeartSurfaceView = new HeartSurfaceView(this, Utils.getScreenWidth(this), Utils.getScreenHeight(this), handler);
        mFrameLayout.addView(mHeartSurfaceView, new ViewGroup.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT));
        mHeartSurfaceView.showBackground();
    }

    private void onPlayMusic(){
        mMediaPlayUtils = new MediaPlayUtils();
        mMediaPlayUtils.InitMediaPlay(this, R.raw.sugar);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMediaPlayUtils.stop();
        handler.removeCallbacksAndMessages(null);
    }

    private Handler handler = new Handler(){
        public void handleMessage(Message paramMessage)
        {
            switch (paramMessage.what){
                default:
                case AppContact.SHOW_HEART:
                    mHeartSurfaceView.showHeart();
                    break;
                case AppContact.SHOW_TEXT:
                    mHeartSurfaceView.showText();
                    break;
            }
        }
    };

    private void onCallMe(){
        if (mDialog == null) {
            mDialog = new Dialog(this);
        }
        View view = LayoutInflater.from(this).inflate(R.layout.item_call_phone, null);
        TextView tvQuit = (TextView) view.findViewById(R.id.quit);
        TextView tvCallNow = (TextView) view.findViewById(R.id.call_now);
        tvQuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });
        tvCallNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("android.intent.action.CALL", Uri.parse("tel:" + "15821763935"));
                startActivity(intent);
                mDialog.dismiss();
            }
        });
        mDialog.setContentView(view);
        mDialog.setTitle("Call me");
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.show();
    }
}
