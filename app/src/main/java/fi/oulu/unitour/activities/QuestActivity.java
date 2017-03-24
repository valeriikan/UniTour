package fi.oulu.unitour.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
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

public class QuestActivity extends AppCompatActivity {

    //declaration of variables for layout elements
    ImageView imgQuest;
    TextView tvQuest;
    ProgressBar loadingBarQuest;
    Button btnScanQR;

    //Firebase authentication objects
    DatabaseReference mDatabase;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_quest);

        final String placeId = getIntent().getStringExtra("LOCATION_ID");

        //attaching layout elements to variables
        imgQuest = (ImageView) findViewById(R.id.imgQuest);
        tvQuest = (TextView) findViewById(R.id.tvQuest);
        btnScanQR = (Button) findViewById(R.id.btnScanQR);
        loadingBarQuest = (ProgressBar) findViewById(R.id.loadingBarQuest);

        btnScanQR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(QuestActivity.this, QrScannerActivity.class);
                intent.putExtra("LOCATION_ID", placeId);
                startActivity(intent);
            }
        });

        //retrieving place data from Firebase database
        //String placeId = getIntent().getStringExtra("LOCATION_ID");
        mDatabase = FirebaseDatabase.getInstance().getReference().child("locations").child(placeId);
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map<String, String> map = (Map) dataSnapshot.getValue();
                String name = map.get("name");
                String description = map.get("description");
                String imageUrl = map.get("imageUrl");
                setTitle(name);
                tvQuest.setText(description);

                Callback loadingCallback = new Callback() {
                    @Override
                    public void onSuccess() {
                        loadingBarQuest.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError() {
                    }
                };

                Picasso.with(QuestActivity.this).load(imageUrl).fit().centerCrop().into(imgQuest, loadingCallback);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
