package fi.oulu.unitour.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import fi.oulu.unitour.R;

public class WelcomeActivity extends AppCompatActivity {

    //declaration of variables for layout elements
    ImageView imgWelcomeSignup, imgWelcomeExplore, imgWelcomeAbout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        //attaching layout elements to variables
        imgWelcomeSignup = (ImageView) findViewById(R.id.imgWelcomeSignup);
        imgWelcomeExplore = (ImageView) findViewById(R.id.imgWelcomeExplore);
        imgWelcomeAbout = (ImageView) findViewById(R.id.imgWelcomeAbout);

        //attaching images to imageViews and applying listeners to them
        imgWelcomeSignup.setImageResource(R.drawable.ui_signup);
        imgWelcomeExplore.setImageResource(R.drawable.ui_explore_blue);
        imgWelcomeAbout.setImageResource(R.drawable.ui_about);
        imgWelcomeSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WelcomeActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        imgWelcomeExplore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WelcomeActivity.this, ExploreMapActivity.class);
                startActivity(intent);
            }
        });
        imgWelcomeAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WelcomeActivity.this, InfoActivity.class);
                startActivity(intent);
            }
        });

        // welcome info dialog
        AlertDialog welcomeDialog = new AlertDialog.Builder(this).create();
        welcomeDialog.setTitle("Welcome");
        welcomeDialog.setMessage("This application is created to help you to get acquainted with the University of Oulu. " +
                "Sign up to play a QR quest or just explore the university map without any registration");
        welcomeDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Got it", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        welcomeDialog.show();
    }
}
