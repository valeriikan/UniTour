package fi.oulu.unitour.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
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
import com.google.android.gms.maps.GoogleMap.OnGroundOverlayClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;

import fi.oulu.unitour.R;
import fi.oulu.unitour.helpers.ExploreMapPointMaker;

public class ExploreMapActivity extends AppCompatActivity
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

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_map_explore);

        //defines a map fragment and links it with the fragment on campus map layout and sets the callback on this object
        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapFragment);
        mapFragment.getMapAsync(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onMapReady(GoogleMap campusMap) {

        //goes to Linnanmaa campus location by an animation and sets a marker on it named Linnanmaa campus
        campusMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        campusMap.setIndoorEnabled(true);
        campusMap.setOnMarkerClickListener(this);

        //adds markers to map to show the checkpoints meaning different locations and levels
        ExploreMapPointMaker uniCheckpointMaker = new ExploreMapPointMaker();
        uniCheckpoints = uniCheckpointMaker.addCheckpoints(campusMap);

        //Adding ground overlay to google map on university of Oulu LatLng
        GroundOverlayOptions overlayOptions = new GroundOverlayOptions()
                .image(BitmapDescriptorFactory.fromResource(R.drawable.map))
                .positionFromBounds(uniBound);
        GroundOverlay uniOverlay = campusMap.addGroundOverlay(overlayOptions);
        uniOverlay.setClickable(true);

        campusMap.animateCamera(CameraUpdateFactory.newCameraPosition(uniOulu));

        //if the user grants the application his location access, the maps automatically adds user's location
        //on the top right position of the map and user can clicks on it and see his current location, otherwise no
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            campusMap.setMyLocationEnabled(true);
        } else {
            this.requestPermissions(new String[] {Manifest.permission.ACCESS_FINE_LOCATION},0);
            //Toast.makeText(this,"No permission granted to access your location! you can give the permission in your phone setting",Toast.LENGTH_LONG);
        }
    }

    @Override
    public boolean onMarkerClick(final Marker marker)
    {
        Intent showLocInfo = new Intent(this,ExploreActivity.class);
        showLocInfo.putExtra("LOCATION_ID",marker.getSnippet());
        startActivity(showLocInfo);
        return true;
    }

    @Override
    public void onGroundOverlayClick(GroundOverlay groundOverlay)
    {

    }
}