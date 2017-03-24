package fi.oulu.unitour.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import fi.oulu.unitour.R;

/**
 * Created by kmendezt on 15.3.2017.
 */

public class TrueFalseActivity extends Activity implements View.OnClickListener {
    String questionString = "Is the student restaurant 'Balance' open after 16.00?";
    TextView question;
    TextView points;
    TextView button_true;
    TextView button_false;
    int answer = 0;
    Toast message;
    int press;
    int score;

    //Firebase authentication object
    FirebaseAuth mAuth;
    DatabaseReference mDatabase;

    String placeId;

    DatabaseReference locRefference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_true_false);
        question = (TextView) findViewById(R.id.question);
        points=(TextView)findViewById(R.id.points);
        button_true = (TextView)findViewById(R.id.button_true);
        button_false = (TextView)findViewById(R.id.button_false);

        question.setText(questionString);
        button_true.setOnClickListener(this);
        button_false.setOnClickListener(this);

        placeId = getIntent().getStringExtra("LOCATION_ID");
        //Firebase elements declaration
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("users");
        String userId = mAuth.getCurrentUser().getUid();
        locRefference = mDatabase.child(userId).child("gamedata").child("loc"+placeId);

    }
    public void onClick(View v) {
        Toast message;

        switch(v.getId()){
            case R.id.button_true:
                press = 1;
                message = Toast.makeText(getApplicationContext(),"Wrong answer, it closes at 13:30",Toast.LENGTH_SHORT);
                message.show();
                locRefference.setValue("1");
                Intent map1 = new Intent(TrueFalseActivity.this, QuestMapActivity.class);
                startActivity(map1);
                break;
            case R.id.button_false:
                press = 0;
                message = Toast.makeText(getApplicationContext(),"You are right. It closes at 13:30. You earned one point",Toast.LENGTH_SHORT);
                message.show();
                locRefference.setValue("1");
                Intent map2 = new Intent(TrueFalseActivity.this, QuestMapActivity.class);
                startActivity(map2);
                break;
        }
        if (answer == press){
            score++;
            points.setText(Integer.toString(score));
            /*
            message = Toast.makeText(getApplicationContext(),"Get points",Toast.LENGTH_SHORT);
            message.show();
            Intent intent = new Intent(this, Test.class);
            startActivity(intent);
            */
            button_true.setOnClickListener(null);
            button_false.setOnClickListener(null);


        }
}
}
