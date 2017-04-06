package fi.oulu.unitour.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import fi.oulu.unitour.R;

public class RightAnswerGameActivity extends AppCompatActivity {

    //declaration of variables for layout elements
    TextView rightAnswerQuestion;
    Button rightAnswerBtn1,rightAnswerBtn2,rightAnswerBtn3,rightAnswerBtn4;

    //Firebase authentication object
    FirebaseAuth mAuth;
    DatabaseReference mReference;

    String placeId;

    DatabaseReference locRefference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_right_answer_game);

        String placeId = getIntent().getStringExtra("LOCATION_ID");
        //Firebase elements declaration
        mAuth = FirebaseAuth.getInstance();
        String userId = mAuth.getCurrentUser().getUid();
        mReference = FirebaseDatabase.getInstance().getReference()
                .child("users").child(userId).child("gamedata").child("loc"+placeId);

        rightAnswerQuestion = (TextView) findViewById(R.id.rightAnswerQuestion);
        rightAnswerBtn1 = (Button) findViewById(R.id.rightAnswerBtn1);
        rightAnswerBtn2 = (Button) findViewById(R.id.rightAnswerBtn2);
        rightAnswerBtn3 = (Button) findViewById(R.id.rightAnswerBtn3);
        rightAnswerBtn4 = (Button) findViewById(R.id.rightAnswerBtn4);

        if (placeId.equals("8")) {
            rightAnswerQuestion.setText("On which day is Fablab open to the public?");
            rightAnswerBtn1.setText("Monday");
            rightAnswerBtn2.setText("Wednesday");
            rightAnswerBtn3.setText("Friday");
            rightAnswerBtn4.setText("Sunday");
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

        rightAnswerBtn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mReference.setValue("1");
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
}
