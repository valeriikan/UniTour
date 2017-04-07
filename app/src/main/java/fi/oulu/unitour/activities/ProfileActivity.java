package fi.oulu.unitour.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.Map;

import fi.oulu.unitour.R;
import fi.oulu.unitour.helpers.CircleTransform;

public class ProfileActivity extends AppCompatActivity {

    //declaration of variables for layout elements
    RelativeLayout profileContent;
    ProgressBar profileLoading;
    TextView tvMainName;
    ImageView mainUserpic;

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
                Map<String, String> map = (Map) dataSnapshot.getValue();
                String name = map.get("name");
                String imageUrl = map.get("imageUrl");
                tvMainName.setText(name);
                Picasso.with(ProfileActivity.this).load(imageUrl).fit().centerCrop()
                        .transform(new CircleTransform()).into(mainUserpic, callback);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}