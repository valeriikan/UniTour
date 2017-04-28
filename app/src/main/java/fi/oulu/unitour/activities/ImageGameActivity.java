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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.squareup.picasso.Picasso;

import fi.oulu.unitour.R;
import fi.oulu.unitour.helpers.RoundedCornersTransform;

public class ImageGameActivity extends AppCompatActivity {

    //declaration of variables for layout elements
    ImageView img1, img2, img3, img4, frame1, frame2, frame3, frame4;
    TextView question;
    Button imageButton;

    String placeId, userId;
    int answer = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_game);

        //Firebase authentication object declaration
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        userId = mAuth.getCurrentUser().getUid();
        placeId = getIntent().getStringExtra("LOCATION_ID");

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
            Picasso.with(ImageGameActivity.this).load(R.drawable.game_1_1).fit().centerCrop()
                    .transform(new RoundedCornersTransform(50,0)).into(img1);
            Picasso.with(ImageGameActivity.this).load(R.drawable.game_1_2).fit().centerCrop()
                    .transform(new RoundedCornersTransform(50,0)).into(img2);
            Picasso.with(ImageGameActivity.this).load(R.drawable.game_1_3).fit().centerCrop()
                    .transform(new RoundedCornersTransform(50,0)).into(img3);
            Picasso.with(ImageGameActivity.this).load(R.drawable.game_1_4).fit().centerCrop()
                    .transform(new RoundedCornersTransform(50,0)).into(img4);
            question.setText("Which artwork is in Kastari?");
        }

        if (placeId.equals("13")) {
            Picasso.with(ImageGameActivity.this).load(R.drawable.game_13_1).fit().centerCrop()
                    .transform(new RoundedCornersTransform(50,0)).into(img1);
            Picasso.with(ImageGameActivity.this).load(R.drawable.game_13_2).fit().centerCrop()
                    .transform(new RoundedCornersTransform(50,0)).into(img2);
            Picasso.with(ImageGameActivity.this).load(R.drawable.game_13_3).fit().centerCrop()
                    .transform(new RoundedCornersTransform(50,0)).into(img3);
            Picasso.with(ImageGameActivity.this).load(R.drawable.game_13_4).fit().centerCrop()
                    .transform(new RoundedCornersTransform(50,0)).into(img4);
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
                        Toast.makeText(ImageGameActivity.this, "Select an answer", Toast.LENGTH_SHORT).show();
                        break;
                    case 1: case 2: case 3:
                        Toast.makeText(ImageGameActivity.this, "Wrong answer. Try again", Toast.LENGTH_SHORT).show();
                        break;
                    case 4:
                        imageButton.setEnabled(false);
                        recordData();
                        Toast.makeText(ImageGameActivity.this, "Correct answer! You gained 8 UniTour points", Toast.LENGTH_LONG).show();
                        break;
                }
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
                //back to map when all the data is updated
                Intent map = new Intent(ImageGameActivity.this, QuestMapActivity.class);
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