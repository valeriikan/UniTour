package fi.oulu.unitour.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.GroundOverlay;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.GoogleMap.OnGroundOverlayClickListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

import fi.oulu.unitour.R;
import fi.oulu.unitour.helpers.QuestMapPointMaker;

public class QuestMapActivity extends AppCompatActivity
        implements OnMapReadyCallback, OnMarkerClickListener, OnGroundOverlayClickListener {

    //the boundary of uni map to make ground overlay
    private LatLngBounds uniBound = new LatLngBounds(
            new LatLng(65.056704, 25.463102),       // South west corner
            new LatLng(65.061842, 25.470193));      // North east corner

    //default position of university of Oulu on Google map
    private static final CameraPosition uniOulu =
            new CameraPosition.Builder().target(new LatLng(65.0593, 25.4663))
                    .zoom(16.0f)
                    .bearing(0)
                    .tilt(30)
                    .build();

    //set of checkpoints markers
    Marker[] uniCheckpoints;
    Map<String, Long> mapClick; // and its clickability helpers

    //Firebase authentication objects
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_map_quest);

        //defines a map fragment and links it with the fragment on quiz map layout and sets the callback on this object
        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.quizFragment);
        mapFragment.getMapAsync(this);

    }

    @Override
    protected void onResume() {
        super.onResume();

        //Firebase authentication object declaration
        //checks each checkpoint status and clickability
        //runs reward activity if all checkpoints are completed
        mAuth = FirebaseAuth.getInstance();
        String userId = mAuth.getCurrentUser().getUid();
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("users").child(userId).child("gamedata");
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mapClick = (Map) dataSnapshot.getValue();
                int sum = 0;
                for (int i=1;i<17;i++) {
                    int value = dataSnapshot.child("loc"+i).getValue(int.class);
                    sum = sum + value;
                }

                if (sum == 16) {
                    Intent intent = new Intent(QuestMapActivity.this, RewardActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onMapReady(GoogleMap gameMap) {

        //goes to Linnanmaa campus location by an animation and sets a marker on it named Linnanmaa campus
        gameMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        gameMap.setIndoorEnabled(false);
        gameMap.setOnMarkerClickListener(this);

        //adds markers to map to show the quiz points
        QuestMapPointMaker questMaker = new QuestMapPointMaker();
        uniCheckpoints = questMaker.addCheckpoints(gameMap);

        //Adding ground overlay to google map on university of Oulu LatLng
        GroundOverlayOptions overlayOptions = new GroundOverlayOptions()
                .image(BitmapDescriptorFactory.fromResource(R.drawable.map))
                .positionFromBounds(uniBound);
        GroundOverlay uniOverlay = gameMap.addGroundOverlay(overlayOptions);
        uniOverlay.setClickable(true);

        gameMap.moveCamera(CameraUpdateFactory.newCameraPosition(uniOulu));

        //if the user grants the application his location access, the maps automatically adds user's location
        // on the top right position of the map and user can clicks on it and see his current location, otherwise no
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            gameMap.setMyLocationEnabled(true);
        } else {
            this.requestPermissions(new String[] {Manifest.permission.ACCESS_FINE_LOCATION},0);
            //Toast.makeText(this,"No permission granted to access your location! you can give the permission in your phone setting",Toast.LENGTH_LONG);
        }
    }

    @Override
    public boolean onMarkerClick(final Marker marker) {
        //disable clickability if checkpoint is completed; opens quest Activity if not
        String position = marker.getSnippet();
        Long status = mapClick.get("loc" + position);
        if (status==0) {
            Intent quest = new Intent(this,QuestActivity.class);
            quest.putExtra("LOCATION_ID",position);
            startActivity(quest);
            return true;
        } else {
            Toast.makeText(QuestMapActivity.this, "You have already completed this checkpoint", Toast.LENGTH_SHORT).show();
            return true;
        }
    }

    @Override
    public void onGroundOverlayClick(GroundOverlay groundOverlay)
    {

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(QuestMapActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
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