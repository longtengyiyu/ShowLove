package fall.love.in.love.utils;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Log;

public class MediaPlayUtils {
    private int mDuration;

//	private static MediaPlayUtils mp;
//	public static MediaPlayUtils init(){
//		if(mp == null)
//			mp = new MediaPlayUtils();
//		return mp;
//	}


    public MediaPlayUtils(){

    }

    public int getDuration() {
        return mDuration;
    }

    MediaPlayer mediaPlayer01 = null;
	public void InitMediaPlay(Context context , int resource){
		mediaPlayer01 = MediaPlayer.create(context, resource);
//        mediaPlayer01.prepare();
		mediaPlayer01.setLooping(true);
        mDuration = mediaPlayer01.getDuration();
        Log.d("tang", "csdc " + mediaPlayer01.getDuration());
		//mediaPlayer01.setOnCompletionListener();
		//mediaPlayer01.
	}
	
	public void InitMediaPlay(Context context ,String fpath){
		Uri u = Uri.parse(fpath);
		try {
			mediaPlayer01 = MediaPlayer.create(context, u);
			mediaPlayer01.setLooping(false);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} 
	}
	
	public void play(){
		if(mediaPlayer01 != null)
			mediaPlayer01.start();
	}

    public void Pause(){
        if(mediaPlayer01 != null)
            mediaPlayer01.pause();
    }
	
	public void stop(){
		if(mediaPlayer01 != null)
			mediaPlayer01.stop();
	}
}
