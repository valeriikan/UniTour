package fi.oulu.unitour.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import fi.oulu.unitour.R;

/**
 * Created by Kevin on 20.3.2017.
 */

public class Test extends AppCompatActivity implements View.OnClickListener{
    Button youTube;
    Button rightAnswer;
    Button trueFalse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);
        youTube = (Button)findViewById(R.id.youtube);
        rightAnswer = (Button)findViewById(R.id.rightanswer);
        trueFalse = (Button)findViewById(R.id.truefalse);

        youTube.setOnClickListener(this);
        rightAnswer.setOnClickListener(this);
        trueFalse.setOnClickListener(this);

    }
    public void onClick(View v){
        switch(v.getId()){
            case R.id.youtube:
                Intent intent = new Intent(this,YoutubeActivity.class);
                startActivity(intent);
                break;
            case R.id.rightanswer:
                Intent intent2 = new Intent(this,RightAnswerGameActivity.class);
                startActivity(intent2);
                break;
            case R.id.truefalse:
                Intent intent3 = new Intent(this,TrueFalseGameActivity.class);
                startActivity(intent3);

        }
    }
}