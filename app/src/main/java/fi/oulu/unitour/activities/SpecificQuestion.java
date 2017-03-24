package fi.oulu.unitour.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ButtonBarLayout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import fi.oulu.unitour.R;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

/**
 * Created by Majid on 3/22/2017.
 */

public class SpecificQuestion extends AppCompatActivity{
    private static final String question = "How many boxes are there inside Tellus Innovation Arena?";
    private static final String answer = "4";

    private static TextView questionTV;
    private static EditText answerET;
    private static ImageView boxImage;
    public void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        setContentView(R.layout.specific_question);
        questionTV = (TextView) findViewById(R.id.questionTV);
        answerET = (EditText) findViewById(R.id.answerET);
        boxImage = (ImageView) findViewById(R.id.boxImage);

        questionTV.setText(question);
        boxImage.setImageResource(R.drawable.box);

        Button submit = (Button) findViewById(R.id.submitBtn);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = answerET.getText().toString();
                if ((str.equals("4") || str.toLowerCase().equals("four")) & !str.equals("")) {
                    //TODO user answered correctly so this quiz is finished and game data should be updated
                }
            }
        });

    }
}
