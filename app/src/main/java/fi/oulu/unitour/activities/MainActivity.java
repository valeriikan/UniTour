package fi.oulu.unitour.activities;

import android.content.Intent;
import android.support.multidex.MultiDex;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import fi.oulu.unitour.R;

public class MainActivity extends AppCompatActivity {

    //declaration of variables for layout elements
    ImageView imgProfile, imgTour, imgExplore;

    //Firebase authentication objects
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MultiDex.install(this);

        //Firebase elements declaration
        mAuth = FirebaseAuth.getInstance();

        //attaching layout elements to variables
        imgProfile = (ImageView) findViewById(R.id.imgProfile);
        imgTour = (ImageView) findViewById(R.id.imgTour);
        imgExplore = (ImageView) findViewById(R.id.imgExplore);

        //attaching images to imageViews and applying listeners to them
        imgProfile.setImageResource(R.drawable.ui_userprofile);
        imgTour.setImageResource(R.drawable.ui_quest);
        imgExplore.setImageResource(R.drawable.ui_explore_green);
        imgProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,ProfileActivity.class);
                startActivity(intent);
            }
        });
        imgTour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,QuestMapActivity.class);
                startActivity(intent);
                MainActivity.this.finish();
            }
        });
        imgExplore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,ExploreMapActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        // menu items
        switch (id) {
            case R.id.action_logout:
                // sign out
                mAuth.signOut();
                Toast.makeText(this, "Logged out successfully", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, WelcomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                break;

        }
        return super.onOptionsItemSelected(item);
    }
}