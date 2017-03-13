package fi.oulu.unitour.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.Map;

import fi.oulu.unitour.R;

public class LocationDescriptor extends AppCompatActivity{

    //declaration of variables for layout elements
    TextView locDescripTxt;
    ImageView locImageIV;
    ProgressBar loadingBar;

    //Firebase authentication objects
    DatabaseReference mDatabase;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location_descriptor);

        //attaching layout elements to variables
        locDescripTxt = (TextView) findViewById(R.id.locDescripTxt);
        locImageIV = (ImageView) findViewById(R.id.locImageIV);
        loadingBar = (ProgressBar) findViewById(R.id.loadingBar);

        //retrieving place data from Firebase database
        String placeId = getIntent().getStringExtra("LOCATION_ID");
        mDatabase = FirebaseDatabase.getInstance().getReference().child("locations").child(placeId);
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map<String, String> map = (Map) dataSnapshot.getValue();
                String name = map.get("name");
                String description = map.get("description");
                String imageUrl = map.get("imageUrl");
                setTitle(name);
                locDescripTxt.setText(description);

                Callback loadingCallback = new Callback() {
                    @Override
                    public void onSuccess() {
                        loadingBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError() {
                    }
                };

                Picasso.with(LocationDescriptor.this).load(imageUrl).fit().centerCrop().into(locImageIV, loadingCallback);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}