package fi.oulu.unitour;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        AlertDialog welcomeDialog = new AlertDialog.Builder(this).create();
        welcomeDialog.setTitle("Welcome");
        welcomeDialog.setMessage("This application is created to help you to get acknowledged with the university");
        welcomeDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Got it", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        welcomeDialog.show();

    }
}
