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
    Button test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        //attaching buttons and its listeners
        Button btnWelcomeSignup = (Button) findViewById(R.id.btnWelcomeSignup);
        Button btnWelcomeLogin = (Button) findViewById(R.id.btnWelcomeLogin);
        test = (Button)findViewById(R.id.test);
        btnWelcomeSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentSignup = new Intent(WelcomeActivity.this, SignupActivity.class);
                startActivity(intentSignup);
            }
        });
        btnWelcomeLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentLogin = new Intent(WelcomeActivity.this, LoginActivity.class);
                startActivity(intentLogin);
            }
        });
        test.setOnClickListener(new View.OnClickListener() {
            @Override
                    public void onClick(View v) {
                Intent intentTest = new Intent(WelcomeActivity.this,Test.class);
                startActivity(intentTest);
            }
        });

        // welcome info dialog
        AlertDialog welcomeDialog = new AlertDialog.Builder(this).create();
        welcomeDialog.setTitle("Welcome");
        welcomeDialog.setMessage("This application is created to help you to get acquainted with the University of Oulu. Register firstly and discover!");
        welcomeDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Got it", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        welcomeDialog.show();
    }
}
