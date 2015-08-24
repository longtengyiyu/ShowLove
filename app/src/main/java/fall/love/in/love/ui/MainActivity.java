package fall.love.in.love.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import fall.love.in.love.R;
import fall.love.in.love.utils.MediaPlayUtils;
import fall.love.in.love.view.Fireworks;


public class MainActivity extends Activity {

    private Fireworks mFireworks;
    private MediaPlayUtils mMediaPlayUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mFireworks = new Fireworks(this,"生日快乐",80);
        setContentView(mFireworks);

        onPlayMusic();
        mFireworks.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Intent intent = new Intent(MainActivity.this, TextActivity.class);
                startActivity(intent);
                return false;
            }
        });

        Toast.makeText(this, "Touch everywhere Continue\n触摸任何地方进入下一个页面", Toast.LENGTH_LONG).show();
    }

    private void onPlayMusic(){
        AudioManager am = (AudioManager)getSystemService(Context.AUDIO_SERVICE); //get AudioManager
        am.setStreamVolume(AudioManager.STREAM_MUSIC, am.getStreamMaxVolume(AudioManager.STREAM_MUSIC) / 2, AudioManager.FLAG_PLAY_SOUND); //set Volume

        mMediaPlayUtils = new MediaPlayUtils();
        mMediaPlayUtils.InitMediaPlay(this, R.raw.birthday);
        handler.sendEmptyMessageDelayed(0, mMediaPlayUtils.getDuration());
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
        handler.removeMessages(0);
        mMediaPlayUtils.Pause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMediaPlayUtils.stop();
        handler.removeMessages(0);
    }

    private Handler handler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    Intent intent = new Intent(MainActivity.this, TextActivity.class);
                    startActivity(intent);
                    break;
            }
        }
    };
}
