package fi.oulu.unitour.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;

import fi.oulu.unitour.R;

public class PuzzleActivity extends AppCompatActivity {

    //declaration of variables for layout elements
    ImageView imgPuzzle1, imgPuzzle2, imgPuzzle3, imgPuzzle4;
    TextView tvPuzzle;
    Button btnPuzzle;

    String placeId, userId;
    public static int puz1, puz2, puz3, puz4; //puzzle helper markers

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puzzle);

        //Firebase authentication object declaration
        mAuth = FirebaseAuth.getInstance();
        userId = mAuth.getCurrentUser().getUid();
        placeId = getIntent().getStringExtra("LOCATION_ID");

        //attaching layout elements to variables
        imgPuzzle1 = (ImageView) findViewById(R.id.imgPuzzle1);
        imgPuzzle2 = (ImageView) findViewById(R.id.imgPuzzle2);
        imgPuzzle3 = (ImageView) findViewById(R.id.imgPuzzle3);
        imgPuzzle4 = (ImageView) findViewById(R.id.imgPuzzle4);
        tvPuzzle = (TextView) findViewById(R.id.tvPuzzle);
        btnPuzzle = (Button) findViewById(R.id.btnPuzzle);

        //attaching images to imageViews according to puzzle piece status
        if (puz1 == 0) {
            imgPuzzle1.setImageResource(R.drawable.game_2_0);
        } else {
            imgPuzzle1.setImageResource(R.drawable.game_2_1);
        }

        if (puz2 == 0) {
            imgPuzzle2.setImageResource(R.drawable.game_4_0);
        } else {
            imgPuzzle2.setImageResource(R.drawable.game_4_1);
        }

        if (puz3 == 0) {
            imgPuzzle3.setImageResource(R.drawable.game_12_0);
        } else {
            imgPuzzle3.setImageResource(R.drawable.game_12_1);
        }

        if (puz4 == 0) {
            imgPuzzle4.setImageResource(R.drawable.game_16_0);
        } else {
            imgPuzzle4.setImageResource(R.drawable.game_16_1);
        }

        //change text if all the pieces are collected
        if (puz1==1 && puz2==1 && puz3==1 && puz4==1) {
            tvPuzzle.setText("Puzzle completed! Well done!");
        }

        //attaching listener to LogIn button
        btnPuzzle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnPuzzle.setEnabled(false);
                recordData();
                Toast.makeText(PuzzleActivity.this, "You gained 4 UniTour points! Keep collecting puzzle pieces", Toast.LENGTH_LONG).show();
            }
        });
    }

    //method to update user game data
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
                    int score = mutableData.getValue(int.class) + 4;
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
                Intent map = new Intent(PuzzleActivity.this, QuestMapActivity.class);
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
}