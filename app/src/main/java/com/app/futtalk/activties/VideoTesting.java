package com.app.futtalk.activties;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.Toast;
import android.widget.VideoView;

import com.app.futtalk.R;

public class VideoTesting extends Activity implements Runnable {

    private VideoView videoScreen;
    private ImageView loadVideo,plus,minus, paly_pause;
    private static final String isPlaying = "Media is Playing";
    private static final String notPlaying = "Media has stopped Playing";

    private SeekBar seekBar;
    private Uri selectedUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_testing);

        loadVideo = findViewById(R.id.iv_select_video);
        videoScreen = findViewById(R.id.videoView);
        plus =findViewById(R.id.iv_minus);
        minus = findViewById(R.id.iv_plus);
        paly_pause = this.findViewById(R.id.iv_play_pause);
        seekBar = findViewById(R.id.seekBar1);

        seekBar.setVisibility(ProgressBar.VISIBLE);
        seekBar.setProgress(0);

        seekBar.setMax(videoScreen.getDuration());

        paly_pause.setImageResource(R.drawable.stop);

        paly_pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actionPlayStop(v);
            }
        });

        plus.setOnClickListener(new View.OnClickListener()   {
            @Override
            public void onClick(View v) {
                if (selectedUri!=null)
                {
//                    int cu = videoScreen.getCurrentPosition()-5000;
//                    videoScreen.seekTo(cu);
//                    seekBar.setProgress(cu);
                }

            }});
        minus.setOnClickListener(new View.OnClickListener()  {
            @Override
            public void onClick(View v) {
                if (selectedUri!=null) {
//                    int cu = videoScreen.getCurrentPosition()+5000;
//                    videoScreen.seekTo(cu );
//                    seekBar.setProgress(cu);
                }
            }});

        loadVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, 0);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            selectedUri = data.getData();
            String[] columns = { MediaStore.Images.Media.DATA,
                    MediaStore.Images.Media.MIME_TYPE };

            Cursor cursor = getContentResolver().query(selectedUri, columns, null, null, null);
            cursor.moveToFirst();

            int pathColumnIndex     = cursor.getColumnIndex( columns[0] );
            int mimeTypeColumnIndex = cursor.getColumnIndex( columns[1] );

            String contentPath = cursor.getString(pathColumnIndex);
            String mimeType    = cursor.getString(mimeTypeColumnIndex);
            cursor.close();

            if(mimeType.startsWith("image")) {
                Toast.makeText(VideoTesting.this,"You selected Image",Toast.LENGTH_LONG).show();
            }
            else if(mimeType.startsWith("video")) {
                //It's a video
                try{
                    videoScreen.setVideoURI(selectedUri);
//                videoField.start();
                }catch(Exception e){
                    e.printStackTrace();
                }
            }

        }
    }

    private void actionPlayStop(View v)
    {
        if(selectedUri!=null)
        {
            if (v.getId() == R.id.iv_play_pause) {
                if(videoScreen.isPlaying())  {
                    videoScreen.pause();

                    Toast.makeText(this, notPlaying, Toast.LENGTH_LONG).show();
                    ImageView img1=this.findViewById(R.id.iv_play_pause);
                    img1.setImageResource(R.drawable.stop);

                }
                else
                {
                    videoScreen.start();

                    Toast.makeText(this, isPlaying, Toast.LENGTH_LONG).show();
                    ImageView img1= this.findViewById(R.id.iv_play_pause);
                    img1.setImageResource(R.drawable.start);
                }
            }
        }

    }

    @Override
    public void run() {
        int currentPosition= 0; String s;
        int total = videoScreen.getDuration();
        while (videoScreen!=null && currentPosition<total) {
            try {
                Thread.sleep(1000);
                currentPosition= videoScreen.getCurrentPosition();
                seekBar.setProgress(currentPosition);
            } catch (InterruptedException e) {
                return;
            } catch (Exception e) {
                return;
            }

        }


    }
}

