package fi.oulu.unitour.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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

    }
    public void onClick(View v) {
        Toast message;

        switch(v.getId()){
            case R.id.button_true:
                press = 1;
                message = Toast.makeText(getApplicationContext(),"True",Toast.LENGTH_SHORT);
                message.show();
                break;
            case R.id.button_false:
                press = 0;
                message = Toast.makeText(getApplicationContext(),"False",Toast.LENGTH_SHORT);
                message.show();
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
