package fi.oulu.unitour.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.bluejamesbond.text.DocumentView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.Map;

import fi.oulu.unitour.R;
import fi.oulu.unitour.helpers.RoundedCornersTransform;

public class ExploreActivity extends AppCompatActivity{

    //declaration of variables for layout elements
    DocumentView textExplore;
    ImageView locImageIV;

    String placeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explore);

        //attaching layout elements to variables
        textExplore = (DocumentView) findViewById(R.id.textExplore);
        locImageIV = (ImageView) findViewById(R.id.locImageIV);

        //getting values according to place id
        placeId = getIntent().getStringExtra("LOCATION_ID");
        int titleId = getResources().getIdentifier("loc_name_" + placeId, "string", getPackageName());
        int descriptionId = getResources().getIdentifier("loc_description_" + placeId, "string", getPackageName());
        int imgId = getResources().getIdentifier("location_" + placeId, "drawable", getPackageName());
        String description = getString(descriptionId);
        String title = getString(titleId);

        //applying values to layout elements
        setTitle(title);
        textExplore.setText(description);
        Picasso.with(ExploreActivity.this).load(imgId).fit().centerCrop().transform(new RoundedCornersTransform(50,10)).into(locImageIV);
    }
}