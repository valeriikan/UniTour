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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import fi.oulu.unitour.R;

public class SpecificQuestionGameActivity extends AppCompatActivity{

    //declaration of variables for layout elements
    ImageView boxImage;
    TextView questionTV;
    EditText answerET;
    Button submitBtn;

    //Firebase authentication object
    FirebaseAuth mAuth;
    DatabaseReference mReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specific_question_game);

        String placeId = getIntent().getStringExtra("LOCATION_ID");
        //Firebase elements declaration
        mAuth = FirebaseAuth.getInstance();
        String userId = mAuth.getCurrentUser().getUid();
        mReference = FirebaseDatabase.getInstance().getReference()
                .child("users").child(userId).child("gamedata").child("loc"+placeId);

        //attaching layout elements to variables
        questionTV = (TextView) findViewById(R.id.questionTV);
        answerET = (EditText) findViewById(R.id.answerET);
        boxImage = (ImageView) findViewById(R.id.boxImage);
        submitBtn = (Button) findViewById(R.id.submitBtn);

        if (placeId.equals("7")) {
            questionTV.setText("How many boxes are there inside Tellus Innovation Arena?");
            boxImage.setImageResource(R.drawable.game_7);
        }

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = answerET.getText().toString();
                if (str.equals("6")) {
                    mReference.setValue("1");
                    Intent map = new Intent(SpecificQuestionGameActivity.this, QuestMapActivity.class);
                    startActivity(map);
                    Toast.makeText(SpecificQuestionGameActivity.this, "Correct answer! You gained 1 UniTour point", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(SpecificQuestionGameActivity.this, "Wrong answer. Try again", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}