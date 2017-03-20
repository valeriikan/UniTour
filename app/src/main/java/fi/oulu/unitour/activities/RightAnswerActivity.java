package fi.oulu.unitour.activities;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import fi.oulu.unitour.R;

/**
 * Created by kmendezt on 15.3.2017.
 */

public class RightAnswerActivity extends Activity implements View.OnClickListener {
    TextView question;
    TextView points;
    String questionString = "This is a sample Question?";
    int answer = 1;
    int press;
    int score;
    Button answer1;
    Button answer2;
    Button answer3;
    Button answer4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.right_answer);

        question = (TextView) findViewById(R.id.question);
        points = (TextView)findViewById(R.id.points);
        answer1 = (Button) findViewById(R.id.answer1);
        answer2 = (Button) findViewById(R.id.answer2);
        answer3 = (Button) findViewById(R.id.answer3);
        answer4 = (Button) findViewById(R.id.answer4);

        answer1.setOnClickListener(this);
        answer2.setOnClickListener(this);
        answer3.setOnClickListener(this);
        answer4.setOnClickListener(this);
        question.setText(questionString);



    }
    public void onClick(View v) {
        Toast message;

        switch(v.getId()){
            case R.id.answer1:
                press = 1;
                Log.v("lol",Integer.toString(press));
                message = Toast.makeText(getApplicationContext(),"Answer1",Toast.LENGTH_SHORT);
                message.show();
                break;
            case R.id.answer2:
                press = 2;
                Log.v("lol",Integer.toString(press));
                message = Toast.makeText(getApplicationContext(),"Answer2",Toast.LENGTH_SHORT);
                message.show();
                break;
            case R.id.answer3:
                press = 3;
                Log.v("lol",Integer.toString(press));
                message = Toast.makeText(getApplicationContext(),"Answer3",Toast.LENGTH_SHORT);
                message.show();
                break;
            case R.id.answer4:
                press = 4;
                Log.v("lol",Integer.toString(press));
                message = Toast.makeText(getApplicationContext(),"Answer4",Toast.LENGTH_SHORT);
                message.show();
                break;
        }
        if ( press == answer){
            score++;
            points.setText(Integer.toString(score));
        }
}
}
