package fi.oulu.unitour.helpers;

import android.support.annotation.NonNull;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import fi.oulu.unitour.R;

import static com.google.android.gms.maps.model.BitmapDescriptorFactory.fromResource;

/**
 * Created by Majid on 2/15/2017.
 */

public class CheckPointMaker {
    private static final LatLng KASTARI = new LatLng(65.057089, 25.467710);
    private static final LatLng DATAGARAGE = new LatLng(65.057985, 25.468475);
    private static final LatLng ITEE = new LatLng(65.057855, 25.464484);
    private static final LatLng STORIES = new LatLng(65.058154, 25.466716);
    private static final LatLng TELLUS = new LatLng(65.058602, 25.465740);
    private static final LatLng FABLAB = new LatLng(65.058996, 25.468047);
    private static final LatLng OYY = new LatLng(65.059023, 25.465515);
    private static final LatLng CENTRALSTATION = new LatLng(65.059218, 25.466481);
    private static final LatLng STUDENTCENTER = new LatLng(65.059888, 25.465022);
    private static final LatLng AVA = new LatLng(65.060512, 25.466470);
    private static final LatLng ZOOLOGICALMUSEUM = new LatLng(65.060612, 25.467339);
    private static final LatLng BALANCE = new LatLng(65.061110, 25.468036);
    private static final LatLng PEGASUSLIBRARY = new LatLng(65.061400, 25.466598);

    private static final LatLng[] checkpoints = {KASTARI,DATAGARAGE,ITEE,STORIES,TELLUS,FABLAB,OYY,CENTRALSTATION,STUDENTCENTER,AVA,ZOOLOGICALMUSEUM,BALANCE,PEGASUSLIBRARY};
    private static final Marker[] uniMarkers = new Marker[13];

    private static final BitmapDescriptor unfinishedCheckpoint = BitmapDescriptorFactory.fromResource(R.drawable.red_star);
    private static final BitmapDescriptor finishedCheckpoint = BitmapDescriptorFactory.fromResource(R.drawable.green_action);

    public CheckPointMaker() {


    }
    private Marker addMarker(GoogleMap map, LatLng coordination, String title, String snippet, BitmapDescriptor icon)
    {
        return map.addMarker(new MarkerOptions()
                .position(coordination)
                .title(title)
                .snippet(snippet)
                .icon(icon));
    }
    public Marker[] addCheckpoints(GoogleMap map)
    {
        for (int i = 0; i < 13; i++)
        {
            LatLng ltlg = checkpoints[i];
            String id = Integer.toString(i+1);
             uniMarkers[i] = addMarker(map,ltlg,ltlg.toString(),id, unfinishedCheckpoint);
        }
        return uniMarkers;
    }
}
