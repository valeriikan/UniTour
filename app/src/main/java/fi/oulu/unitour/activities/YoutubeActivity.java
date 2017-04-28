package fi.oulu.unitour.activities;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
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
import fi.oulu.unitour.helpers.YoutubeFragment;

public class YoutubeActivity extends AppCompatActivity {

    //declaration of variables for layout elements
    Button btnYoutube;

    String placeId, userId;
    public static String videoID;

    FirebaseAuth mAuth;

    @Override
    public void onCreate( Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube);

        //Firebase authentication object declaration
        mAuth = FirebaseAuth.getInstance();
        userId = mAuth.getCurrentUser().getUid();
        placeId = getIntent().getStringExtra("LOCATION_ID");

        switch (placeId) {
            case "9":
                videoID = "XMRF_aIuUik";
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
                if (isOnline()) {
                    btnYoutube.setEnabled(false);
                    recordData();
                    Toast.makeText(YoutubeActivity.this, "You gained 5 UniTour points", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(YoutubeActivity.this, R.string.noInternet, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void recordData() {
        DatabaseReference locRef = FirebaseDatabase.getInstance().getReference().child("users").child(userId)
                .child("gamedata").child("loc"+placeId);
        DatabaseReference scoreRef = FirebaseDatabase.getInstance().getReference().child("users").child(userId)
                .child("score");
        DatabaseReference completedRef = FirebaseDatabase.getInstance().getReference().child("users").child(userId)
                .child("completed");
        locRef.setValue(1);
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
                Intent map = new Intent(YoutubeActivity.this, QuestMapActivity.class);
                map.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(map);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent map = new Intent(this, QuestMapActivity.class);
        map.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(map);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        // menu items
        switch (id) {
            case R.id.action_logout:
                // sign out
                mAuth.signOut();
                Toast.makeText(this, "Logged out successfully", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, WelcomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}