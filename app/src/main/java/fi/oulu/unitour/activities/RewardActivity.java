package fi.oulu.unitour.activities;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;

import fi.oulu.unitour.R;
import fi.oulu.unitour.helpers.CircleTransform;

public class RewardActivity extends AppCompatActivity {

    //declaration of variables for layout elements
    RelativeLayout layoutScreenshot;
    LinearLayout layoutScreenshotButtons;
    ProgressBar rewardLoading;
    ImageView imgReward, imgRewardUser;
    Button btnSave, btnShare;

    File imgFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reward);

        //Firebase authentication object declaration
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        String userId = mAuth.getCurrentUser().getUid();
        DatabaseReference mRef = FirebaseDatabase.getInstance().getReference().child("users").child(userId);

        //attaching layout elements to variables
        layoutScreenshot = (RelativeLayout) findViewById(R.id.layoutScreenshot);
        layoutScreenshotButtons = (LinearLayout) findViewById(R.id.layoutScreenshotButtons);
        rewardLoading = (ProgressBar) findViewById(R.id.rewardLoading);
        imgReward = (ImageView) findViewById(R.id.imgReward);
        imgReward.setImageResource(R.drawable.ui_reward);
        imgRewardUser = (ImageView) findViewById(R.id.imgRewardUser);
        btnSave = (Button) findViewById(R.id.btnSave);
        btnShare = (Button) findViewById(R.id.btnShare);

        //show content when user image is loaded from the web
        final Callback callback = new Callback() {
            @Override
            public void onSuccess() {
                rewardLoading.setVisibility(View.GONE);
                layoutScreenshot.setVisibility(View.VISIBLE);
                layoutScreenshotButtons.setVisibility(View.VISIBLE);
            }

            @Override
            public void onError() {
            }
        };

        //apply user photo to the reward image
        mRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String imageUrl = dataSnapshot.child("imageUrl").getValue(String.class);
                Picasso.with(RewardActivity.this).load(imageUrl).fit().centerCrop()
                        .transform(new CircleTransform()).into(imgRewardUser, callback);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                storeImage(getScreenshot(layoutScreenshot));
                Toast.makeText(RewardActivity.this, "Reward image is saved to yours device. You can find it in Galery app or in Screenshot folder", Toast.LENGTH_SHORT).show();
            }
        });

        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                storeImage(getScreenshot(layoutScreenshot));
                shareImage();
            }
        });
    }

    //method to get screenshot
    private Bitmap getScreenshot(View view) {
        view.setDrawingCacheEnabled(true);
        Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache());
        view.setDrawingCacheEnabled(false);
        return bitmap;
    }

    //method to save screenshot to device memory
    private void storeImage(Bitmap bm){
        String dirPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Screenshots";
        File dir = new File(dirPath);
        if(!dir.exists()) {dir.mkdirs();}
        imgFile = new File(dirPath, "UniTour.png");
        try {
            FileOutputStream fOut = new FileOutputStream(imgFile);
            bm.compress(Bitmap.CompressFormat.PNG, 100, fOut);
            fOut.flush();
            fOut.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //method to run share activity
    private void shareImage(){
        Uri uri = Uri.fromFile(imgFile);
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("image/*");

        intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "UniTour quest completed");
        intent.putExtra(android.content.Intent.EXTRA_TEXT, "I have successfully completed UniTour quest and earned 100 points!");
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        try {
            startActivity(Intent.createChooser(intent, "Share your reward!"));
        } catch (ActivityNotFoundException e) {
            Toast.makeText(RewardActivity.this, "No App Available", Toast.LENGTH_SHORT).show();
        }
    }
}