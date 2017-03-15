package fi.oulu.unitour.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import fi.oulu.unitour.R;

import static android.widget.Toast.makeText;

/**
 * Created by kmendezt on 15.3.2017.
 */

public class RightAnswerActivity extends Activity implements View.OnClickListener {
    TextView question = (TextView) findViewById(R.id.question);
    String questionString = "This is a sample Question?";
    int answer = 1;
    int press;
    Button answer1 = (Button) findViewById(R.id.answer1);
    Button answer2 = (Button) findViewById(R.id.answer2);
    Button answer3 = (Button) findViewById(R.id.answer3);
    Button answer4 = (Button) findViewById(R.id.answer4);
    Toast message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.right_answer);
        question.setText("questionString");

        answer1.setOnClickListener(this);
        answer2.setOnClickListener(this);
        answer3.setOnClickListener(this);
        answer4.setOnClickListener(this);

        if (answer == press){
            message = Toast.makeText(getApplicationContext(),"Get points",Toast.LENGTH_SHORT);
            message.show();
        }

    }
    public void onClick(View v) {
        Toast message;

        switch(v.getId()){
            case R.id.answer1:
                press = 1;
                message = Toast.makeText(getApplicationContext(),"Answer1",Toast.LENGTH_SHORT);
                message.show();
                break;
            case R.id.answer2:
                press = 2;
                message = Toast.makeText(getApplicationContext(),"Answer2",Toast.LENGTH_SHORT);
                message.show();
                break;
            case R.id.answer3:
                press = 3;
                message = Toast.makeText(getApplicationContext(),"Answer3",Toast.LENGTH_SHORT);
                message.show();
                break;
            case R.id.answer4:
                press = 4;
                message = Toast.makeText(getApplicationContext(),"Answer4",Toast.LENGTH_SHORT);
                message.show();
                break;
        }
}
}
