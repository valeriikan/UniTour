package fi.oulu.unitour.activities;

import android.app.Activity;
import android.content.Intent;
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
    String questionString = "At what time fablab is open?";
    String answer1_string ="a)From 10-12";
    String answer2_string ="b)From 10-16";
    String answer3_string ="c)From 7-16";
    String answer4_string ="d)From 8-14";
    int answer = 1;
    int press;
    int score;
    TextView question;
    TextView points;
    TextView answer1;
    TextView answer2;
    TextView answer3;
    TextView answer4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.right_answer);

        question =(TextView)findViewById(R.id.question);
        points = (TextView)findViewById(R.id.points);
        answer1 = (TextView)findViewById(R.id.answer1);
        answer2 = (TextView)findViewById(R.id.answer2);
        answer3 = (TextView)findViewById(R.id.answer3);
        answer4 = (TextView)findViewById(R.id.answer4);

        question.setText(questionString);
        answer1.setText(answer1_string);
        answer2.setText(answer2_string);
        answer3.setText(answer3_string);
        answer4.setText(answer4_string);

        answer1.setOnClickListener(this);
        answer2.setOnClickListener(this);
        answer3.setOnClickListener(this);
        answer4.setOnClickListener(this);





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
            answer1.setOnClickListener(null);
            answer2.setOnClickListener(null);
            answer3.setOnClickListener(null);
            answer4.setOnClickListener(null);
            /*
            Intent intent = new Intent(this, Test.class);
            startActivity(intent);
            */
        }
}
}
