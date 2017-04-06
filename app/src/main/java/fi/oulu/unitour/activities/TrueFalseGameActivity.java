package fi.oulu.unitour.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import fi.oulu.unitour.R;

public class TrueFalseGameActivity extends AppCompatActivity {

    //declaration of variables for layout elements
    TextView trueFalseQuestion;
    Button trueFalseBtn1, trueFalseBtn2;

    //Firebase authentication object
    FirebaseAuth mAuth;
    DatabaseReference mReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_true_false_game);

        String placeId = getIntent().getStringExtra("LOCATION_ID");
        //Firebase elements declaration
        mAuth = FirebaseAuth.getInstance();
        String userId = mAuth.getCurrentUser().getUid();
        mReference = FirebaseDatabase.getInstance().getReference()
                .child("users").child(userId).child("gamedata").child("loc"+placeId);

        trueFalseQuestion = (TextView) findViewById(R.id.trueFalseQuestion);
        trueFalseBtn1 = (Button) findViewById(R.id.trueFalseBtn1);
        trueFalseBtn2 = (Button) findViewById(R.id.trueFalseBtn2);

        if (placeId.equals("6")) {
            trueFalseQuestion.setText("Can you change the password of your university’s user account in IT service desk?");
            trueFalseBtn1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mReference.setValue("1");
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
                    mReference.setValue("1");
                    Intent map = new Intent(TrueFalseGameActivity.this, QuestMapActivity.class);
                    startActivity(map);
                    Toast.makeText(TrueFalseGameActivity.this, "Correct answer! Balance closes at 13:30. You gained 1 UniTour point", Toast.LENGTH_LONG).show();
                }
            });
        }
    }
}