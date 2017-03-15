package fi.oulu.unitour.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import fi.oulu.unitour.R;

/**
 * Created by kmendezt on 15.3.2017.
 */

public class TrueFalseActivity extends Activity implements View.OnClickListener {
    TextView question = (TextView) findViewById(R.id.question);
    String questionString = "This is a sample Question?";
    int answer = 0;
    int press;
    Button button_true = (Button) findViewById(R.id.button_true);
    Button button_false = (Button) findViewById(R.id.button_false);

    Toast message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_true_false);
        question.setText(questionString);

        button_true.setOnClickListener(this);
        button_false.setOnClickListener(this);


        if (answer == press){
            message = Toast.makeText(getApplicationContext(),"Get points",Toast.LENGTH_SHORT);
            message.show();
        }

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
}
}
