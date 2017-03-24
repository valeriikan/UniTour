package fi.oulu.unitour.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import fi.oulu.unitour.R;

/**
 * Created by Kevin on 19.3.2017.
 */

public class YoutubeActivity extends AppCompatActivity {
private String title_string = "Video";
    Button youtubeButton;


    //Firebase authentication object
    FirebaseAuth mAuth;
    DatabaseReference mDatabase;
    @Override
    public void onCreate( Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube);

        String placeId = getIntent().getStringExtra("LOCATION_ID");

        //Firebase elements declaration
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("users");
        String userId = mAuth.getCurrentUser().getUid();
        final DatabaseReference locRefference = mDatabase.child(userId).child("gamedata").child("loc"+placeId);




        youtubeButton = (Button) findViewById(R.id.youtubeButton);

        YoutubeFragment fragment = new YoutubeFragment();
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction()
                .add(R.id.youtube_layout,fragment).commit();


        youtubeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                locRefference.setValue("1");
                Intent map4 = new Intent(YoutubeActivity.this, QuestMapActivity.class);
                startActivity(map4);
                Toast.makeText(YoutubeActivity.this, "You gained 1 UniTour point", Toast.LENGTH_LONG).show();

            }
        });

        }





}


