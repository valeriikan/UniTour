package fi.oulu.unitour.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import fi.oulu.unitour.R;

public class PuzzleActivity extends AppCompatActivity {

    //declaration of variables for layout elements
    ImageView puzzleImg1, puzzleImg2, puzzleImg3, puzzleImg4;
    TextView puzzleText;
    Button puzzleButton;

    //Firebase authentication object
    FirebaseAuth mAuth;
    DatabaseReference mReference;

    public static int puz1 = 0;
    public static int puz2 = 0;
    public static int puz3 = 0;
    public static int puz4 = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puzzle);

        String placeId = getIntent().getStringExtra("LOCATION_ID");
        //Firebase elements declaration
        mAuth = FirebaseAuth.getInstance();
        String userId = mAuth.getCurrentUser().getUid();
        mReference = FirebaseDatabase.getInstance().getReference()
                .child("users").child(userId).child("gamedata").child("loc"+placeId);

        //attaching layout elements to variables
        puzzleImg1 = (ImageView) findViewById(R.id.puzzleImg1);
        puzzleImg2 = (ImageView) findViewById(R.id.puzzleImg2);
        puzzleImg3 = (ImageView) findViewById(R.id.puzzleImg3);
        puzzleImg4 = (ImageView) findViewById(R.id.puzzleImg4);
        puzzleText = (TextView) findViewById(R.id.puzzleText);
        puzzleButton = (Button) findViewById(R.id.puzzleButton);

        if (puz1==1 && puz2==1 && puz3==1 && puz4==1) {
            puzzleText.setText("Puzzle completed! Well done!");
        }

        if (puz1 == 0) {
            puzzleImg1.setImageResource(R.drawable.game_2_0);
        } else {
            puzzleImg1.setImageResource(R.drawable.game_2_1);
        }

        if (puz2 == 0) {
            puzzleImg2.setImageResource(R.drawable.game_4_0);
        } else {
            puzzleImg2.setImageResource(R.drawable.game_4_1);
        }

        if (puz3 == 0) {
            puzzleImg3.setImageResource(R.drawable.game_12_0);
        } else {
            puzzleImg3.setImageResource(R.drawable.game_12_1);
        }

        if (puz4 == 0) {
            puzzleImg4.setImageResource(R.drawable.game_16_0);
        } else {
            puzzleImg4.setImageResource(R.drawable.game_16_1);
        }

        puzzleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mReference.setValue("1");
                Intent map = new Intent(PuzzleActivity.this, QuestMapActivity.class);
                startActivity(map);
                Toast.makeText(PuzzleActivity.this, "Correct answer! You gained 1 UniTour point", Toast.LENGTH_LONG).show();
            }
        });

    }
}
