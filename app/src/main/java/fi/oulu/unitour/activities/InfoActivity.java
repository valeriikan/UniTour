package fi.oulu.unitour.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import fi.oulu.unitour.R;

public class InfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        ImageView img = (ImageView) findViewById(R.id.infoApplogo);
        img.setImageResource(R.drawable.ui_applogo);

        TextView tvInfoActivity = (TextView) findViewById(R.id.tvInfoActivity);
        tvInfoActivity.setText(R.string.infoActivity);
    }
}
