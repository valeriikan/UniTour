package fi.oulu.unitour.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class QrScannerActivity extends Activity implements ZXingScannerView.ResultHandler {

    private ZXingScannerView mScannerView;

    @Override
    protected void onCreate(Bundle state) {
        super.onCreate(state);
        mScannerView = new ZXingScannerView(this);   // Programmatically initialize the scanner view
        setContentView(mScannerView);                // Set the scanner view as the content view
    }

    @Override
    protected void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
        mScannerView.startCamera();          // Start camera on resume
    }

    @Override
    protected void onPause() {
        super.onPause();
        mScannerView.stopCamera();           // Stop camera on pause
    }

    @Override
    public void handleResult(Result rawResult) {
        // Do something with the result here
        String placeId = getIntent().getStringExtra("LOCATION_ID");
        String result = rawResult.getText();

        switch (placeId) {
            case "1":
                if (result.equals("sfw2017-unitour-kastari")) {
                    Intent game = new Intent(this, ImageGameActivity.class);
                    game.putExtra("LOCATION_ID", placeId);
                    startActivity(game);
                } else {
                    Intent map = new Intent(this, QuestMapActivity.class);
                    startActivity(map);
                    Toast.makeText(this, "You are not in Kastari, get back to the map and find the place", Toast.LENGTH_LONG).show();
                }
                break;

            case "2":
                if (result.equals("sfw2017-unitour-tietotalo")) {
                    Intent game = new Intent(this, PuzzleActivity.class);
                    game.putExtra("LOCATION_ID", placeId);
                    PuzzleActivity.puz1 = 1;
                    startActivity(game);
                } else {
                    Intent map = new Intent(this, QuestMapActivity.class);
                    startActivity(map);
                    Toast.makeText(this, "You are not in Tietotalo, get back to the map and find the place", Toast.LENGTH_LONG).show();
                }
                break;

            case "3":
                if (result.equals("sfw2017-unitour-datagarage")) {
                    Intent game = new Intent(this, RightAnswerGameActivity.class);
                    game.putExtra("LOCATION_ID", placeId);
                    startActivity(game);
                } else {
                    Intent map = new Intent(this, QuestMapActivity.class);
                    startActivity(map);
                    Toast.makeText(this, "You are not in Datagarage, get back to the map and find the place", Toast.LENGTH_LONG).show();
                }
                break;

            case "4":
                if (result.equals("sfw2017-unitour-sgo")) {
                    Intent game = new Intent(this, PuzzleActivity.class);
                    game.putExtra("LOCATION_ID", placeId);
                    PuzzleActivity.puz2 = 1;
                    startActivity(game);
                } else {
                    Intent map = new Intent(this, QuestMapActivity.class);
                    startActivity(map);
                    Toast.makeText(this, "You are not in Sodankylä Geophysical Observatory, get back to the map and find the place", Toast.LENGTH_LONG).show();
                }
                break;

            case "5":
                if (result.equals("sfw2017-unitour-aisec")) {
                    Intent game = new Intent(this, SpecificQuestionGameActivity.class);
                    game.putExtra("LOCATION_ID", placeId);
                    startActivity(game);
                } else {
                    Intent map = new Intent(this, QuestMapActivity.class);
                    startActivity(map);
                    Toast.makeText(this, "You are not in AISEC, get back to the map and find the place", Toast.LENGTH_LONG).show();
                }
                break;

            case "6":
                if (result.equals("sfw2017-unitour-itservices")) {
                    Intent game = new Intent(this, TrueFalseGameActivity.class);
                    game.putExtra("LOCATION_ID", placeId);
                    startActivity(game);
                } else {
                    Intent map = new Intent(this, QuestMapActivity.class);
                    startActivity(map);
                    Toast.makeText(this, "You are not in IT services, get back to the map and find the place", Toast.LENGTH_LONG).show();
                }
                break;

            case "7":
                if (result.equals("sfw2017-unitour-tellus")) {
                    Intent game = new Intent(this, SpecificQuestionGameActivity.class);
                    game.putExtra("LOCATION_ID", placeId);
                    startActivity(game);
                } else {
                    Intent map = new Intent(this, QuestMapActivity.class);
                    startActivity(map);
                    Toast.makeText(this, "You are not in Tellus, get back to the map and find the place", Toast.LENGTH_LONG).show();
                }
                break;

            case "8":
                if (result.equals("sfw2017-unitour-fablab")) {
                    Intent game = new Intent(this, RightAnswerGameActivity.class);
                    game.putExtra("LOCATION_ID", placeId);
                    startActivity(game);
                } else {
                    Intent map = new Intent(this, QuestMapActivity.class);
                    startActivity(map);
                    Toast.makeText(this, "You are not in Fablab, get back to the map and find the place", Toast.LENGTH_LONG).show();
                }
                break;

            case "9":
                if (result.equals("sfw2017-unitour-activitywall")) {
                    Intent game = new Intent(this, YoutubeActivity.class);
                    game.putExtra("LOCATION_ID", placeId);
                    startActivity(game);
                } else {
                    Intent map = new Intent(this, QuestMapActivity.class);
                    startActivity(map);
                    Toast.makeText(this, "You are not near Activity wall, get back to the map and find the place", Toast.LENGTH_LONG).show();
                }
                break;

            case "10":
                if (result.equals("sfw2017-unitour-centralstation")) {
                    Intent game = new Intent(this, YoutubeActivity.class);
                    game.putExtra("LOCATION_ID", placeId);
                    startActivity(game);
                } else {
                    Intent map = new Intent(this, QuestMapActivity.class);
                    startActivity(map);
                    Toast.makeText(this, "You are not in Central Station, get back to the map and find the place", Toast.LENGTH_LONG).show();
                }
                break;

            case "11":
                if (result.equals("sfw2017-unitour-studentcenter")) {
                    Intent game = new Intent(this, YoutubeActivity.class);
                    game.putExtra("LOCATION_ID", placeId);
                    startActivity(game);
                } else {
                    Intent map = new Intent(this, QuestMapActivity.class);
                    startActivity(map);
                    Toast.makeText(this, "You are not in Student Center, get back to the map and find the place", Toast.LENGTH_LONG).show();
                }
                break;

            case "12":
                if (result.equals("sfw2017-unitour-aava")) {
                    Intent game = new Intent(this, PuzzleActivity.class);
                    game.putExtra("LOCATION_ID", placeId);
                    PuzzleActivity.puz3 = 1;
                    startActivity(game);
                } else {
                    Intent map = new Intent(this, QuestMapActivity.class);
                    startActivity(map);
                    Toast.makeText(this, "You are not in Aava, get back to the map and find the place", Toast.LENGTH_LONG).show();
                }
                break;

            case "13":
                if (result.equals("sfw2017-unitour-zoologicalmuseum")) {
                    Intent game = new Intent(this, ImageGameActivity.class);
                    game.putExtra("LOCATION_ID", placeId);
                    startActivity(game);
                } else {
                    Intent map = new Intent(this, QuestMapActivity.class);
                    startActivity(map);
                    Toast.makeText(this, "You are not in Zoological Museum, get back to the map and find the place", Toast.LENGTH_LONG).show();
                }
                break;

            case "14":
                if (result.equals("sfw2017-unitour-pegasuslibrary")) {
                    Intent game = new Intent(this, YoutubeActivity.class);
                    game.putExtra("LOCATION_ID", placeId);
                    startActivity(game);
                } else {
                    Intent map = new Intent(this, QuestMapActivity.class);
                    startActivity(map);
                    Toast.makeText(this, "You are not in Pegasus Library, get back to the map and find the place", Toast.LENGTH_LONG).show();
                }
                break;

            case "15":
                if (result.equals("sfw2017-unitour-balance")) {
                    Intent game = new Intent(this, TrueFalseGameActivity.class);
                    game.putExtra("LOCATION_ID", placeId);
                    startActivity(game);
                } else {
                    Intent map = new Intent(this, QuestMapActivity.class);
                    startActivity(map);
                    Toast.makeText(this, "You are not in Balance, get back to the map and find the place", Toast.LENGTH_LONG).show();
                }
                break;

            case "16":
                if (result.equals("sfw2017-unitour-facultyofeducation")) {
                    Intent game = new Intent(this, PuzzleActivity.class);
                    game.putExtra("LOCATION_ID", placeId);
                    PuzzleActivity.puz4 = 1;
                    startActivity(game);
                } else {
                    Intent map = new Intent(this, QuestMapActivity.class);
                    startActivity(map);
                    Toast.makeText(this, "You are not in Faculty of Education, get back to the map and find the place", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

}
