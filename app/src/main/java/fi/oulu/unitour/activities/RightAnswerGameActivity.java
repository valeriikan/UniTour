package fi.oulu.unitour.activities;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
import com.squareup.picasso.Picasso;

import fi.oulu.unitour.R;
import fi.oulu.unitour.helpers.RoundedCornersTransform;

public class RightAnswerGameActivity extends AppCompatActivity {

    //declaration of variables for layout elements
    TextView rightAnswerQuestion;
    Button rightAnswerBtn1,rightAnswerBtn2,rightAnswerBtn3,rightAnswerBtn4;
    ImageView imgRightAnswer;

    String placeId, userId;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_right_answer_game);

        //Firebase authentication object declaration
        mAuth = FirebaseAuth.getInstance();
        userId = mAuth.getCurrentUser().getUid();
        placeId = getIntent().getStringExtra("LOCATION_ID");

        rightAnswerQuestion = (TextView) findViewById(R.id.rightAnswerQuestion);
        rightAnswerBtn1 = (Button) findViewById(R.id.rightAnswerBtn1);
        rightAnswerBtn2 = (Button) findViewById(R.id.rightAnswerBtn2);
        rightAnswerBtn3 = (Button) findViewById(R.id.rightAnswerBtn3);
        rightAnswerBtn4 = (Button) findViewById(R.id.rightAnswerBtn4);
        imgRightAnswer = (ImageView) findViewById(R.id.imgRightAnswer);

        if (placeId.equals("3")) {
            Picasso.with(RightAnswerGameActivity.this).load(R.drawable.game_3).fit().centerCrop().transform(new RoundedCornersTransform(50,10)).into(imgRightAnswer);
            rightAnswerQuestion.setText("Which one we cannot order at Datagarage?");
            rightAnswerBtn1.setText("Tea");
            rightAnswerBtn2.setText("Cofee");
            rightAnswerBtn3.setText("Lunch");
            rightAnswerBtn4.setText("Dinner");

            rightAnswerBtn3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(RightAnswerGameActivity.this, "Wrong answer. Try again", Toast.LENGTH_SHORT).show();
                }
            });
            rightAnswerBtn4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (isOnline()) {
                        rightAnswerBtn4.setEnabled(false);
                        recordData();
                        Toast.makeText(RightAnswerGameActivity.this, "Correct answer! You gained 8 UniTour points", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(RightAnswerGameActivity.this, R.string.noInternet, Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

        if (placeId.equals("8")) {
            Picasso.with(RightAnswerGameActivity.this).load(R.drawable.game_8).fit().centerCrop().transform(new RoundedCornersTransform(50,10)).into(imgRightAnswer);
            rightAnswerQuestion.setText("On which day is Fablab open to the public?");
            rightAnswerBtn1.setText("Monday");
            rightAnswerBtn2.setText("Wednesday");
            rightAnswerBtn3.setText("Friday");
            rightAnswerBtn4.setText("Sunday");

            rightAnswerBtn3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (isOnline()) {
                        rightAnswerBtn3.setEnabled(false);
                        recordData();
                        Toast.makeText(RightAnswerGameActivity.this, "Correct answer! You gained 8 UniTour point", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(RightAnswerGameActivity.this, R.string.noInternet, Toast.LENGTH_SHORT).show();
                    }
                }
            });
            rightAnswerBtn4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(RightAnswerGameActivity.this, "Wrong answer. Try again", Toast.LENGTH_SHORT).show();
                }
            });
        }

        rightAnswerBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(RightAnswerGameActivity.this, "Wrong answer. Try again", Toast.LENGTH_SHORT).show();
            }
        });

        rightAnswerBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(RightAnswerGameActivity.this, "Wrong answer. Try again", Toast.LENGTH_SHORT).show();
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
                    int score = mutableData.getValue(int.class) + 8;
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
                Intent map = new Intent(RightAnswerGameActivity.this, QuestMapActivity.class);
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