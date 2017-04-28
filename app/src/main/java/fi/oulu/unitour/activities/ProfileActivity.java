package fi.oulu.unitour.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import fi.oulu.unitour.R;
import fi.oulu.unitour.helpers.CircleTransform;

public class ProfileActivity extends AppCompatActivity {

    //declaration of variables for layout elements
    RelativeLayout profileContent;
    ProgressBar profileLoading;
    TextView tvMainName, tvUnitourScore, tvCheckpoints;
    ImageView mainUserpic, imgProfileBackground;

    //Firebase authentication objects
    FirebaseAuth mAuth;
    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //attaching layout elements to variables
        profileContent = (RelativeLayout) findViewById(R.id.profileContent);
        profileLoading = (ProgressBar) findViewById(R.id.profileLoading);
        tvMainName = (TextView) findViewById(R.id.tvProfileName);
        tvUnitourScore = (TextView) findViewById(R.id.tvUnitourScore);
        tvCheckpoints = (TextView) findViewById(R.id.tvCheckpoints);
        imgProfileBackground = (ImageView) findViewById(R.id.imgProfileBackground);
        imgProfileBackground.setImageResource(R.drawable.ui_profile_background);
        mainUserpic = (ImageView) findViewById(R.id.profileUserpic);
        mainUserpic.setImageResource(R.drawable.ui_applogo);

        mAuth = FirebaseAuth.getInstance();

        final Callback callback = new Callback() {
            @Override
            public void onSuccess() {
                profileLoading.setVisibility(View.GONE);
                profileContent.setVisibility(View.VISIBLE);
            }

            @Override
            public void onError() {
            }
        };

        String userId = mAuth.getCurrentUser().getUid();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("users").child(userId);
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String name = dataSnapshot.child("name").getValue(String.class);
                String imageUrl = dataSnapshot.child("imageUrl").getValue(String.class);
                int score = dataSnapshot.child("score").getValue(int.class);
                int completed = dataSnapshot.child("completed").getValue(int.class);
                tvMainName.setText(name);
                tvUnitourScore.setText(String.valueOf(score));
                tvCheckpoints.setText(String.valueOf(completed));
                Picasso.with(ProfileActivity.this).load(imageUrl).fit().centerCrop()
                        .transform(new CircleTransform()).into(mainUserpic, callback);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        // menu items
        switch (id) {
            case R.id.action_logout:
                // sign out
                mAuth.signOut();
                Toast.makeText(this, "Logged out successfully", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, WelcomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}