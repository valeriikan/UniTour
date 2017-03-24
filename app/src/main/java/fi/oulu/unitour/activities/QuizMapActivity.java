package fi.oulu.unitour.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import fi.oulu.unitour.R;
import fi.oulu.unitour.helpers.QuestPointMaker;

public class QuizMapActivity extends AppCompatActivity
        implements OnMapReadyCallback, OnMarkerClickListener, OnGroundOverlayClickListener {

    //the boundary of uni map to make ground overlay
    private LatLngBounds uniBound = new LatLngBounds(
            new LatLng(65.056704, 25.463102),       // South west corner
            new LatLng(65.061842, 25.470193));      // North east corner

    //Firebase authentication objects
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    //default position of university of Oulu on Google map
    private static final CameraPosition uniOulu =
            new CameraPosition.Builder().target(new LatLng(65.0593, 25.4663))
                    .zoom(16.0f)
                    .bearing(0)
                    .tilt(0)
                    .build();
    private Marker[] uniCheckpoints = new Marker[13];

    private Bitmap image;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.quiz_map);

        //defines a map fragment and links it with the fragment on quiz map layout and sets the callback on this object
        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.quizFragment);
        mapFragment.getMapAsync(this);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("users");
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onMapReady(GoogleMap gameMap) {

        //goes to Linnanmaa campus location by an animation and sets a marker on it named Linnanmaa campus
        gameMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        gameMap.setIndoorEnabled(false);
        gameMap.setOnMarkerClickListener(this);

        //adds markers to map to show the quiz points
        QuestPointMaker questMaker = new QuestPointMaker();
        uniCheckpoints = questMaker.addCheckpoints(gameMap);
        //questMaker.makeRoute(gameMap);

        //Adding ground overlay to google map on university of Oulu LatLng
        GroundOverlayOptions overlayOptions = new GroundOverlayOptions()
                .image(BitmapDescriptorFactory.fromResource(R.drawable.map))
                .positionFromBounds(uniBound);
        GroundOverlay uniOverlay = gameMap.addGroundOverlay(overlayOptions);
        uniOverlay.setClickable(true);

        gameMap.moveCamera(CameraUpdateFactory.newCameraPosition(uniOulu));

        //if the user grants the application his location access, the maps automatically adds user's location on the top right position
        //of the map and user can clicks on it and see his current location, otherwise no
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            gameMap.setMyLocationEnabled(true);
        } else {
            this.requestPermissions(new String[] {Manifest.permission.ACCESS_FINE_LOCATION},0);
            //Toast.makeText(this,"No permission granted to access your location! you can give the permission in your phone setting",Toast.LENGTH_LONG);
        }
    }
    @Override
    public boolean onMarkerClick(final Marker marker)
    {
        Intent quest = new Intent(this,QuestActivity.class);
        quest.putExtra("LOCATION_ID",marker.getSnippet());
        startActivity(quest);
        return true;
    }
    @Override
    public void onGroundOverlayClick(GroundOverlay groundOverlay)
    {

    }
    private void getActiveCheckpoints()
    {

    }
}