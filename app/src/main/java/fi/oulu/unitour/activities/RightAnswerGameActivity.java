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

public class RightAnswerGameActivity extends AppCompatActivity {

    //declaration of variables for layout elements
    TextView rightAnswerQuestion;
    Button rightAnswerBtn1,rightAnswerBtn2,rightAnswerBtn3,rightAnswerBtn4;
    ImageView imgRightAnswer;

    //Firebase authentication object
    FirebaseAuth mAuth;
    DatabaseReference locRef, scoreRef, completedRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_right_answer_game);

        String placeId = getIntent().getStringExtra("LOCATION_ID");
        //Firebase elements declaration
        mAuth = FirebaseAuth.getInstance();
        String userId = mAuth.getCurrentUser().getUid();
        locRef = FirebaseDatabase.getInstance().getReference()
                .child("users").child(userId).child("gamedata").child("loc"+placeId);
        scoreRef = FirebaseDatabase.getInstance().getReference().child("users").child(userId).child("score");
        completedRef = FirebaseDatabase.getInstance().getReference().child("users").child(userId).child("completed");

        rightAnswerQuestion = (TextView) findViewById(R.id.rightAnswerQuestion);
        rightAnswerBtn1 = (Button) findViewById(R.id.rightAnswerBtn1);
        rightAnswerBtn2 = (Button) findViewById(R.id.rightAnswerBtn2);
        rightAnswerBtn3 = (Button) findViewById(R.id.rightAnswerBtn3);
        rightAnswerBtn4 = (Button) findViewById(R.id.rightAnswerBtn4);
        imgRightAnswer = (ImageView) findViewById(R.id.imgRightAnswer);

        if (placeId.equals("3")) {
            imgRightAnswer.setImageResource(R.drawable.game_3);
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
                    recordData();
                    Intent map = new Intent(RightAnswerGameActivity.this, QuestMapActivity.class);
                    startActivity(map);
                    Toast.makeText(RightAnswerGameActivity.this, "Correct answer! You gained 1 UniTour point", Toast.LENGTH_LONG).show();

                }
            });
        }

        if (placeId.equals("8")) {
            imgRightAnswer.setImageResource(R.drawable.game_8);
            rightAnswerQuestion.setText("On which day is Fablab open to the public?");
            rightAnswerBtn1.setText("Monday");
            rightAnswerBtn2.setText("Wednesday");
            rightAnswerBtn3.setText("Friday");
            rightAnswerBtn4.setText("Sunday");

            rightAnswerBtn3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    recordData();
                    Intent map = new Intent(RightAnswerGameActivity.this, QuestMapActivity.class);
                    startActivity(map);
                    Toast.makeText(RightAnswerGameActivity.this, "Correct answer! You gained 1 UniTour point", Toast.LENGTH_LONG).show();

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