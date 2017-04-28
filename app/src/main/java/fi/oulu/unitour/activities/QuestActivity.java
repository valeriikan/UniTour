package fi.oulu.unitour.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bluejamesbond.text.DocumentView;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import fi.oulu.unitour.R;
import fi.oulu.unitour.helpers.RoundedCornersTransform;

public class QuestActivity extends AppCompatActivity {

    //declaration of variables for layout elements
    ImageView imgQuest;
    DocumentView textQuest;
    Button btnScanQR;
    String placeId;

    //Firebase authentication objects
    FirebaseAuth mAuth;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_quest);

        //Firebase elements declaration
        mAuth = FirebaseAuth.getInstance();

        //attaching layout elements to variables
        imgQuest = (ImageView) findViewById(R.id.imgQuest);
        btnScanQR = (Button) findViewById(R.id.btnScanQR);
        textQuest = (DocumentView) findViewById(R.id.textQuest);

        //getting values according to place id
        placeId = getIntent().getStringExtra("LOCATION_ID");
        int titleId = getResources().getIdentifier("loc_name_" + placeId, "string", getPackageName());
        int descriptionId = getResources().getIdentifier("loc_description_" + placeId, "string", getPackageName());
        int imgId = getResources().getIdentifier("location_" + placeId, "drawable", getPackageName());
        String description = getString(descriptionId);
        String title = getString(titleId);

        //applying values to layout elements
        setTitle(title);
        textQuest.setText(description);
        Picasso.with(QuestActivity.this).load(imgId).fit().centerCrop().transform(new RoundedCornersTransform(50,10)).into(imgQuest);

        //scan button
        btnScanQR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(QuestActivity.this, Manifest.permission.CAMERA)
                        == PackageManager.PERMISSION_GRANTED) {
                    Intent intent = new Intent(QuestActivity.this, QrScannerActivity.class);
                    intent.putExtra("LOCATION_ID", placeId);
                    startActivity(intent);
                } else {
                    ActivityCompat.requestPermissions(QuestActivity.this,
                            new String[]{Manifest.permission.CAMERA},
                            1);
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent intent = new Intent(QuestActivity.this, QrScannerActivity.class);
                    intent.putExtra("LOCATION_ID", placeId);
                    startActivity(intent);
                } else {
                    Toast.makeText(QuestActivity.this, "You cannot continue the quest because you have denied access to device camera. Now you can update it manually in application settings", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
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