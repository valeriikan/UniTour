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

public class ImageGameActivity extends AppCompatActivity {

    //declaration of variables for layout elements
    ImageView img1, img2, img3, img4, frame1, frame2, frame3, frame4;
    TextView question;
    Button imageButton;

    //Firebase authentication object
    FirebaseAuth mAuth;
    DatabaseReference mRefference;

    int answer = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_game);

        String placeId = getIntent().getStringExtra("LOCATION_ID");
        //Firebase elements declaration
        mAuth = FirebaseAuth.getInstance();
        String userId = mAuth.getCurrentUser().getUid();
        mRefference = FirebaseDatabase.getInstance().getReference()
                .child("users").child(userId).child("gamedata").child("loc"+placeId);

        //attaching layout elements to variables
        img1 = (ImageView) findViewById(R.id.img1);
        img2 = (ImageView) findViewById(R.id.img2);
        img3 = (ImageView) findViewById(R.id.img3);
        img4 = (ImageView) findViewById(R.id.img4);
        frame1 = (ImageView) findViewById(R.id.frame1);
        frame2 = (ImageView) findViewById(R.id.frame2);
        frame3 = (ImageView) findViewById(R.id.frame3);
        frame4 = (ImageView) findViewById(R.id.frame4);
        frame1.setImageResource(R.drawable.view_frame);
        frame2.setImageResource(R.drawable.view_frame);
        frame3.setImageResource(R.drawable.view_frame);
        frame4.setImageResource(R.drawable.view_frame);
        question = (TextView) findViewById(R.id.question);
        imageButton = (Button) findViewById(R.id.imageButton);

        if (placeId.equals("1")) {
            img1.setImageResource(R.drawable.game_1_1);
            img2.setImageResource(R.drawable.game_1_2);
            img3.setImageResource(R.drawable.game_1_3);
            img4.setImageResource(R.drawable.game_1_4);
            question.setText("Which artwork is in Kastari?");
        }

        if (placeId.equals("13")) {
            img1.setImageResource(R.drawable.game_13_1);
            img2.setImageResource(R.drawable.game_13_2);
            img3.setImageResource(R.drawable.game_13_3);
            img4.setImageResource(R.drawable.game_13_4);
            question.setText("Which of the following animals doesn’t belong to the Zoological Museum?");
        }

        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                frame1.setVisibility(View.VISIBLE);
                frame2.setVisibility(View.INVISIBLE);
                frame3.setVisibility(View.INVISIBLE);
                frame4.setVisibility(View.INVISIBLE);
                answer = 1;
            }
        });

        img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                frame1.setVisibility(View.INVISIBLE);
                frame2.setVisibility(View.VISIBLE);
                frame3.setVisibility(View.INVISIBLE);
                frame4.setVisibility(View.INVISIBLE);
                answer = 2;
            }
        });

        img3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                frame1.setVisibility(View.INVISIBLE);
                frame2.setVisibility(View.INVISIBLE);
                frame3.setVisibility(View.VISIBLE);
                frame4.setVisibility(View.INVISIBLE);
                answer = 3;
            }
        });

        img4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                frame1.setVisibility(View.INVISIBLE);
                frame2.setVisibility(View.INVISIBLE);
                frame3.setVisibility(View.INVISIBLE);
                frame4.setVisibility(View.VISIBLE);
                answer = 4;
            }
        });

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (answer) {
                    case 0:
                        Toast.makeText(ImageGameActivity.this, "Select an answer", Toast.LENGTH_LONG).show();
                        break;
                    case 1: case 2: case 3:
                        Toast.makeText(ImageGameActivity.this, "Wrong answer. Try again", Toast.LENGTH_LONG).show();
                        break;
                    case 4:
                        mRefference.setValue("1");
                        Intent map = new Intent(ImageGameActivity.this, QuestMapActivity.class);
                        startActivity(map);
                        Toast.makeText(ImageGameActivity.this, "Correct answer! You gained 1 UniTour point", Toast.LENGTH_LONG).show();
                        break;
                }
            }
        });
    }

}