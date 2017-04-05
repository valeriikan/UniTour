package fi.oulu.unitour.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import fi.oulu.unitour.R;
import fi.oulu.unitour.helpers.RoundedCornersTransform;

public class ImageGameActivity extends AppCompatActivity {

    ImageView img1, img2, img3, img4, frame1, frame2, frame3, frame4;
    ProgressBar img1Loading,img2Loading,img3Loading,img4Loading;
    TextView question;
    Button imageButton;

    String imageUrl1;
    String imageUrl2;
    String imageUrl3;
    String imageUrl4;

    int answer = 0;

    //Firebase authentication object
    FirebaseAuth mAuth;
    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_game);

        String placeId = getIntent().getStringExtra("LOCATION_ID");

        //Firebase elements declaration
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("users");
        String userId = mAuth.getCurrentUser().getUid();
        final DatabaseReference locRefference = mDatabase.child(userId).child("gamedata").child("loc"+placeId);

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
        img1Loading = (ProgressBar) findViewById(R.id.img1Loading);
        img2Loading = (ProgressBar) findViewById(R.id.img2Loading);
        img3Loading = (ProgressBar) findViewById(R.id.img3Loading);
        img4Loading = (ProgressBar) findViewById(R.id.img4Loading);
        question = (TextView) findViewById(R.id.question);
        imageButton = (Button) findViewById(R.id.imageButton);



        if (placeId.equals("1")) {
            imageUrl1 = "https://firebasestorage.googleapis.com/v0/b/unitour-7b1ed.appspot.com/o/gamepictures%2Fkastari1.JPG?alt=media&token=3cf26172-61f3-42bf-94d9-c788f8d7e2fb";
            imageUrl2 = "https://firebasestorage.googleapis.com/v0/b/unitour-7b1ed.appspot.com/o/gamepictures%2Fkastari2.JPG?alt=media&token=fe9fc6e2-4448-4aa5-900c-4c1cd48fec94";
            imageUrl3 = "https://firebasestorage.googleapis.com/v0/b/unitour-7b1ed.appspot.com/o/gamepictures%2Fkastari3.JPG?alt=media&token=6f3cc41d-8e9d-4fe6-9cb4-0e7fe2ac5d78";
            imageUrl4 = "https://firebasestorage.googleapis.com/v0/b/unitour-7b1ed.appspot.com/o/gamepictures%2Fkastari4.jpg?alt=media&token=9806ffc8-e3ce-4bed-82fa-3e252fafdbd3";
            question.setText("Which artwork is in Kastari");
        }

        if (placeId.equals("13")) {
            imageUrl1 = "https://firebasestorage.googleapis.com/v0/b/unitour-7b1ed.appspot.com/o/gamepictures%2Fmuseum1.JPG?alt=media&token=2fed7d4d-6902-4751-bc5b-5e94bb0880fe";
            imageUrl2 = "https://firebasestorage.googleapis.com/v0/b/unitour-7b1ed.appspot.com/o/gamepictures%2Fmuseum2.JPG?alt=media&token=dad721b5-181a-45ef-864a-02205c585820";
            imageUrl3 = "https://firebasestorage.googleapis.com/v0/b/unitour-7b1ed.appspot.com/o/gamepictures%2Fmuseum3.JPG?alt=media&token=833d49ac-d39b-4488-97ba-39eceb84af98";
            imageUrl4 = "https://firebasestorage.googleapis.com/v0/b/unitour-7b1ed.appspot.com/o/gamepictures%2Fmuseum4.jpg?alt=media&token=7519181c-9cf4-40da-9de8-5215984b0315";
            question.setText("Which of the following animals doesn’t belong to the Zoological Museum?");
        }

        Callback callback1 = new Callback() {
            @Override
            public void onSuccess() {
                img1Loading.setVisibility(View.GONE);
            }

            @Override
            public void onError() {
            }
        };

        Callback callback2 = new Callback() {
            @Override
            public void onSuccess() {
                img2Loading.setVisibility(View.GONE);
            }

            @Override
            public void onError() {
            }
        };

        Callback callback3 = new Callback() {
            @Override
            public void onSuccess() {
                img3Loading.setVisibility(View.GONE);
            }

            @Override
            public void onError() {
            }
        };

        Callback callback4 = new Callback() {
            @Override
            public void onSuccess() {
                img4Loading.setVisibility(View.GONE);
            }

            @Override
            public void onError() {
            }
        };

        Picasso.with(ImageGameActivity.this).load(imageUrl1).fit().centerCrop().transform(new RoundedCornersTransform()).into(img1, callback1);
        Picasso.with(ImageGameActivity.this).load(imageUrl2).fit().centerCrop().transform(new RoundedCornersTransform()).into(img2, callback2);
        Picasso.with(ImageGameActivity.this).load(imageUrl3).fit().centerCrop().transform(new RoundedCornersTransform()).into(img3, callback3);
        Picasso.with(ImageGameActivity.this).load(imageUrl4).fit().centerCrop().transform(new RoundedCornersTransform()).into(img4, callback4);

        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                frame1.setVisibility(View.VISIBLE);
                frame2.setVisibility(View.INVISIBLE);
                frame3.setVisibility(View.INVISIBLE);
                frame4.setVisibility(View.INVISIBLE);
                answer = 1;
                locRefference.setValue("1");
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
                locRefference.setValue("1");
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
                locRefference.setValue("1");
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
                locRefference.setValue("1");
            }
        });

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (answer==0) {
                    Toast.makeText(ImageGameActivity.this, "Select an answer", Toast.LENGTH_LONG).show();

                }

                switch (answer) {
                    case 0:
                        Toast.makeText(ImageGameActivity.this, "Select an answer", Toast.LENGTH_LONG).show();
                        break;
                    case 1: case 2: case 3:
                        Intent map123 = new Intent(ImageGameActivity.this, QuestMapActivity.class);
                        startActivity(map123);
                        Toast.makeText(ImageGameActivity.this, "Wrong answer. You do not receive UniTour point", Toast.LENGTH_LONG).show();
                        break;
                    case 4:
                        Intent map4 = new Intent(ImageGameActivity.this, QuestMapActivity.class);
                        startActivity(map4);
                        Toast.makeText(ImageGameActivity.this, "Correct answer! You gained 1 UniTour point", Toast.LENGTH_LONG).show();
                        break;
                }

            }
        });

    }
}
