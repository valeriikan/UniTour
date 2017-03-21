package fi.oulu.unitour.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import fi.oulu.unitour.R;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        //attaching buttons and its listeners
        Button btnWelcomeLogin = (Button) findViewById(R.id.btnWelcomeLogin);
        Button btnWelcomeExplore = (Button) findViewById(R.id.btnWelcomeExplore);

        btnWelcomeLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WelcomeActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        btnWelcomeExplore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WelcomeActivity.this, CampusMapActivity.class);
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
