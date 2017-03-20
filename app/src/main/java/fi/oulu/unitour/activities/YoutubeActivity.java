package fi.oulu.unitour.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import fi.oulu.unitour.R;

/**
 * Created by Kevin on 19.3.2017.
 */

public class YoutubeActivity extends AppCompatActivity {



    @Override
    public void onCreate( Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube);

        YoutubeFragment fragment = new YoutubeFragment();
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction()
                .add(R.id.youtube_layout,fragment).commit();



        }


}


