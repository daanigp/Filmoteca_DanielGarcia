package esp.daniel.filmoteca_danielgarcia;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.widget.MediaController;
import android.widget.VideoView;

public class MoreActivity extends AppCompatActivity implements MediaController.MediaPlayerControl{
    MediaController mc;
    MediaPlayer mp;

    VideoView v;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more);

        mc = new MediaController(this);
        mc.setMediaPlayer(this);
        mc.setAnchorView(v);

        v = (VideoView) findViewById(R.id.videoView);
        v.setMediaController(mc);
        v.setVideoURI(Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.videofilmoteca));

        Handler h = new Handler();
        v.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                h.post(new Runnable() {
                    @Override
                    public void run() {
                        mc.show();
                    }
                });
            }
        });

    }

    @Override
    public void start() {
        if(!mp.isPlaying()){
            mp.start();
        }
    }

    @Override
    public void pause() {
        if(mp.isPlaying()){
            mp.pause();
        }
    }

    @Override
    public int getDuration() {
        return mp.getDuration();
    }

    @Override
    public int getCurrentPosition() {
        return mp.getCurrentPosition();
    }

    @Override
    public void seekTo(int pos) {
        mp.seekTo(pos);
    }

    @Override
    public boolean isPlaying() {
        return mp.isPlaying();
    }

    @Override
    public int getBufferPercentage() {
        return 0;
    }

    @Override
    public boolean canPause() {
        return true;
    }

    @Override
    public boolean canSeekBackward() {
        return true;
    }

    @Override
    public boolean canSeekForward() {
        return true;
    }

    @Override
    public int getAudioSessionId() {
        return mp.getAudioSessionId();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN){
            if(mc.isShowing()){
                mc.hide();
            } else {
                mc.show();
            }
        }
        return false;
    }
}