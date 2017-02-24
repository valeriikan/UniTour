package fi.oulu.unitour.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

import fi.oulu.unitour.R;

import static android.R.attr.fingerprintAuthDrawable;
import static android.R.attr.onClick;
import static android.content.Context.MODE_PRIVATE;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sPref = getSharedPreferences("session", MODE_PRIVATE);
        String usernamePref = sPref.getString("username", "");
        String firstnamePref = sPref.getString("firstname", "");
        String secondnamePref = sPref.getString("secondname", "");

        TextView tvMainName = (TextView) findViewById(R.id.tvMainName);
        tvMainName.setText(firstnamePref + " " + secondnamePref);

        Button startTour = (Button) findViewById(R.id.startTourBtn);
        startTour.setOnClickListener(new View.OnClickListener() {
                                         @Override
                                         public void onClick(View view) {
                                             Intent mapIntent = new Intent(getApplicationContext(),MapActivity.class);
                                             startActivity(mapIntent);
                                         }
                                     }
        );

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // inflate the menu; adds items to the action bar
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        // menu items
        switch (id) {
            case R.id.action_logout:
                // clear sPref data, open WelcomeActivity
                SharedPreferences sPref = getSharedPreferences("session", MODE_PRIVATE);
                SharedPreferences.Editor ed = sPref.edit();
                ed.putString("username", "");
                ed.putString("firstname", "");
                ed.putString("secondname", "");
                ed.commit();

                Intent intent = new Intent(this, WelcomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                Toast.makeText(this, "Logged out successfully", Toast.LENGTH_SHORT).show();
                break;

            case R.id.action_exit:
                // close app
                finish();
                System.exit(0);
        }
        return super.onOptionsItemSelected(item);
    }

}
