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

import fi.oulu.unitour.R;

public class TrueFalseGameActivity extends AppCompatActivity {

    //declaration of variables for layout elements
    TextView trueFalseQuestion;
    Button trueFalseBtn1, trueFalseBtn2;
    ImageView imgTrueFalse;

    //Firebase authentication object
    FirebaseAuth mAuth;
    DatabaseReference locRef, scoreRef, completedRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_true_false_game);

        String placeId = getIntent().getStringExtra("LOCATION_ID");
        //Firebase elements declaration
        mAuth = FirebaseAuth.getInstance();
        String userId = mAuth.getCurrentUser().getUid();
        locRef = FirebaseDatabase.getInstance().getReference()
                .child("users").child(userId).child("gamedata").child("loc"+placeId);
        scoreRef = FirebaseDatabase.getInstance().getReference().child("users").child(userId).child("score");
        completedRef = FirebaseDatabase.getInstance().getReference().child("users").child(userId).child("completed");

        trueFalseQuestion = (TextView) findViewById(R.id.trueFalseQuestion);
        trueFalseBtn1 = (Button) findViewById(R.id.trueFalseBtn1);
        trueFalseBtn2 = (Button) findViewById(R.id.trueFalseBtn2);
        imgTrueFalse = (ImageView) findViewById(R.id.imgTrueFalse);

        if (placeId.equals("6")) {
            imgTrueFalse.setImageResource(R.drawable.game_6);
            trueFalseQuestion.setText("Can you change the password of your university’s user account in IT service desk?");
            trueFalseBtn1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    recordData();
                    Intent map = new Intent(TrueFalseGameActivity.this, QuestMapActivity.class);
                    startActivity(map);
                    Toast.makeText(TrueFalseGameActivity.this, "Correct answer! You gained 1 UniTour point", Toast.LENGTH_LONG).show();
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
            imgTrueFalse.setImageResource(R.drawable.game_15);
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
                    recordData();
                    Intent map = new Intent(TrueFalseGameActivity.this, QuestMapActivity.class);
                    startActivity(map);
                    Toast.makeText(TrueFalseGameActivity.this, "Correct answer! Balance closes at 13:30. You gained 1 UniTour point", Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    private void recordData() {
        locRef.setValue("1");
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

            }
        });
    }
}