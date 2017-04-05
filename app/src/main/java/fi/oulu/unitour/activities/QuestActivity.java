package fi.oulu.unitour.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import fi.oulu.unitour.R;

public class QuestActivity extends AppCompatActivity {

    //declaration of variables for layout elements
    ImageView imgQuest;
    TextView tvQuest;
    Button btnScanQR;
    String placeId;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_quest);

        //attaching layout elements to variables
        imgQuest = (ImageView) findViewById(R.id.imgQuest);
        tvQuest = (TextView) findViewById(R.id.tvQuest);
        btnScanQR = (Button) findViewById(R.id.btnScanQR);

        //getting values according to place id
        placeId = getIntent().getStringExtra("LOCATION_ID");
        int titleId = getResources().getIdentifier("loc_name_" + placeId, "string", getPackageName());
        int descriptionId = getResources().getIdentifier("loc_description_" + placeId, "string", getPackageName());
        int imgId = getResources().getIdentifier("location_" + placeId, "drawable", getPackageName());
        String description = getString(descriptionId);
        String title = getString(titleId);

        //applying values to layout elements
        setTitle(title);
        tvQuest.setText(description);
        imgQuest.setImageResource(imgId);

        //scan button
        btnScanQR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(QuestActivity.this, QrScannerActivity.class);
                intent.putExtra("LOCATION_ID", placeId);
                startActivity(intent);
            }
        });
    }
}