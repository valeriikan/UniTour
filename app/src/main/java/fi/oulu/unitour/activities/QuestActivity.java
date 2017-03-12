package fi.oulu.unitour.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import fi.oulu.unitour.R;

/**
 * Created by Majid on 3/5/2017.
 */

public class QuestActivity extends AppCompatActivity {
    private static String placeID;
    private static ImageView questImage;
    private static TextView questText;
    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.quest_part1);

        placeID = getIntent().getStringExtra("LOCATION_ID");
        questImage = (ImageView) findViewById(R.id.questPicIV);
        questText = (TextView) findViewById(R.id.questTV);
        setTitle("Finding Locations");
        switch (placeID)
        {
            case "1":
                questImage.setImageResource(R.drawable.kastari_art);
                        questText.setText("Go and find the angle shown in the picture and scan the QR code near there. " +
                                "Then you will find a clue to the next location");
                /*Animation anim = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.myanimation);
                questText.startAnimation(anim);
                Animation anim1 = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.clockwise);
                questImage.startAnimation(anim1);
                */
                break;
            case "2":
                        questImage.setImageResource(R.drawable.datagarage);
                        questText.setText("Go and find the angle shown in the picture and scan the QR code near there. " +
                        "Then you will find a clue to the next location");
                break;
            case "3":
                        questImage.setImageResource(R.drawable.datagarage);
                        questText.setText("Go and find the location shown in the picture and scan the QR over there. " +
                        "Then you will find a clue to the next location");
                break;
            case "4":
                        questImage.setImageResource(R.drawable.tietotalo);
                        questText.setText("Go and find the location shown in the picture and scan the QR over there. " +
                        "Then you will find a clue to the next location");
                break;
        }
    }
}
