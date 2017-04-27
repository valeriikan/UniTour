package fi.oulu.unitour.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class SpecificQuestionGameActivity extends AppCompatActivity{

    //declaration of variables for layout elements
    ImageView boxImage;
    TextView questionTV;
    EditText answerET;
    Button submitBtn;

    String placeId, userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specific_question_game);

        //Firebase authentication object declaration
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        userId = mAuth.getCurrentUser().getUid();
        placeId = getIntent().getStringExtra("LOCATION_ID");

        //attaching layout elements to variables
        questionTV = (TextView) findViewById(R.id.questionTV);
        answerET = (EditText) findViewById(R.id.answerET);
        boxImage = (ImageView) findViewById(R.id.boxImage);
        submitBtn = (Button) findViewById(R.id.submitBtn);

        if (placeId.equals("5")) {
            questionTV.setText("On what floor is AISEC office located?");
            Picasso.with(SpecificQuestionGameActivity.this).load(R.drawable.game_5).fit().centerCrop().transform(new RoundedCornersTransform(50,10)).into(boxImage);


            submitBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String str = answerET.getText().toString();
                    if (str.equals("2")) {
                        recordData();
                        Intent map = new Intent(SpecificQuestionGameActivity.this, QuestMapActivity.class);
                        startActivity(map);
                        finish();
                        Toast.makeText(SpecificQuestionGameActivity.this, "Correct answer! You gained 8 UniTour points", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(SpecificQuestionGameActivity.this, "Wrong answer. Try again", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

        if (placeId.equals("7")) {
            questionTV.setText("How many boxes are there inside Tellus Innovation Arena?");
            Picasso.with(SpecificQuestionGameActivity.this).load(R.drawable.game_7).fit().centerCrop().transform(new RoundedCornersTransform(50,10)).into(boxImage);


            submitBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String str = answerET.getText().toString();
                    if (str.equals("6")) {
                        recordData();
                        Intent map = new Intent(SpecificQuestionGameActivity.this, QuestMapActivity.class);
                        startActivity(map);
                        finish();
                        Toast.makeText(SpecificQuestionGameActivity.this, "Correct answer! You gained 8 UniTour points", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(SpecificQuestionGameActivity.this, "Wrong answer. Try again", Toast.LENGTH_SHORT).show();
                    }
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

            }
        });
    }
}