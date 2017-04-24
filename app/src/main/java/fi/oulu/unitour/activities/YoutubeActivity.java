package fi.oulu.unitour.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;

import fi.oulu.unitour.R;

public class YoutubeActivity extends AppCompatActivity {

    //declaration of variables for layout elements
    Button btnYoutube;

    //Firebase authentication object
    FirebaseAuth mAuth;
    DatabaseReference locRef, scoreRef, completedRef;

    public static String videoID;

    @Override
    public void onCreate( Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube);

        String placeId = getIntent().getStringExtra("LOCATION_ID");
        //Firebase elements declaration
        mAuth = FirebaseAuth.getInstance();
        String userId = mAuth.getCurrentUser().getUid();
        locRef = FirebaseDatabase.getInstance().getReference()
                .child("users").child(userId).child("gamedata").child("loc"+placeId);
        scoreRef = FirebaseDatabase.getInstance().getReference().child("users").child(userId).child("score");
        completedRef = FirebaseDatabase.getInstance().getReference().child("users").child(userId).child("completed");

        switch (placeId) {
            case "9":
                videoID = "yLVb0qxLrbc";
                break;
            case "10":
                videoID = "A1HW_0CJmu0";
                break;
            case "11":
                videoID = "ZM_9_ii1Uq4";
                break;
            case "14":
                videoID = "_Gw_7ffXxHE";
                break;
        }

        //calling Youtube player
        YoutubeFragment fragment = new YoutubeFragment();
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().add(R.id.youtube_layout,fragment).commit();

        //attaching layout elements to variables
        btnYoutube = (Button) findViewById(R.id.btnYoutube);
        btnYoutube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recordData();
                Intent map = new Intent(YoutubeActivity.this, QuestMapActivity.class);
                startActivity(map);
                Toast.makeText(YoutubeActivity.this, "You gained 1 UniTour point", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void recordData() {
        locRef.setValue("1");
        scoreRef.runTransaction(new Transaction.Handler() {
            @Override
            public Transaction.Result doTransaction(MutableData mutableData) {
                if (mutableData.getValue() == null) {
                } else {
                    int score = mutableData.getValue(int.class) + 5;
                    mutableData.setValue(score);
                }
                return Transaction.success(mutableData);
            }

            @Override
            public void onComplete(DatabaseError databaseError, boolean b, DataSnapshot dataSnapshot) {

            }
        });
        completedRef.runTransaction(new Transaction.Handler() {
            @Override
            public Transaction.Result doTransaction(MutableData mutableData) {
                if (mutableData.getValue() == null) {
                } else {
                    int completed = mutableData.getValue(int.class) + 1;
                    mutableData.setValue(completed);
                }
                return Transaction.success(mutableData);
            }

            @Override
            public void onComplete(DatabaseError databaseError, boolean b, DataSnapshot dataSnapshot) {

            }
        });
    }
}