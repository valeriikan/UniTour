package fi.oulu.unitour.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

public class TrueFalseGameActivity extends AppCompatActivity {

    //declaration of variables for layout elements
    TextView trueFalseQuestion;
    Button trueFalseBtn1, trueFalseBtn2;
    ImageView imgTrueFalse;

    String placeId, userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_true_false_game);

        //Firebase authentication object declaration
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        userId = mAuth.getCurrentUser().getUid();
        placeId = getIntent().getStringExtra("LOCATION_ID");

        trueFalseQuestion = (TextView) findViewById(R.id.trueFalseQuestion);
        trueFalseBtn1 = (Button) findViewById(R.id.trueFalseBtn1);
        trueFalseBtn2 = (Button) findViewById(R.id.trueFalseBtn2);
        imgTrueFalse = (ImageView) findViewById(R.id.imgTrueFalse);

        if (placeId.equals("6")) {
            Picasso.with(TrueFalseGameActivity.this).load(R.drawable.game_6).fit().centerCrop()
                    .transform(new RoundedCornersTransform(50,10)).into(imgTrueFalse);
            trueFalseQuestion.setText("Can you change the password of your university’s user account in IT service desk?");
            trueFalseBtn1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    trueFalseBtn1.setEnabled(false);
                    recordData();
                    Toast.makeText(TrueFalseGameActivity.this, "Correct answer! You gained 8 UniTour points", Toast.LENGTH_LONG).show();
                }
            });
            trueFalseBtn2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(TrueFalseGameActivity.this, "Wrong answer. Try again", Toast.LENGTH_SHORT).show();
                }
            });
        }

        if (placeId.equals("15")) {
            Picasso.with(TrueFalseGameActivity.this).load(R.drawable.game_15).fit().centerCrop()
                    .transform(new RoundedCornersTransform(50,10)).into(imgTrueFalse);
            trueFalseQuestion.setText("Is Balance open at 16:00?");
            trueFalseBtn1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(TrueFalseGameActivity.this, "Wrong answer. Try again", Toast.LENGTH_SHORT).show();
                }
            });
            trueFalseBtn2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    trueFalseBtn2.setEnabled(false);
                    recordData();
                    Toast.makeText(TrueFalseGameActivity.this, "Correct answer! Balance closes at 13:30. You gained 8 UniTour points", Toast.LENGTH_LONG).show();
                }
            });
        }
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
                Intent map = new Intent(TrueFalseGameActivity.this, QuestMapActivity.class);
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
}