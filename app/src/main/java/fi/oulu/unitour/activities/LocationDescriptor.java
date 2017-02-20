package fi.oulu.unitour.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import fi.oulu.unitour.R;
import fi.oulu.unitour.helpers.GetLocationInfo;


/**
 * Created by Majid on 2/13/2017.
 */

public class LocationDescriptor extends AppCompatActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location_descriptor);

        GetLocationInfo showImage = new GetLocationInfo(this, this);
        showImage.execute(getIntent().getStringExtra("LOCATION_ID"));
    }
    public void fetchInfoPostExecute(){
        //fetches location name and description form result set thrown by php script
        TextView locDescTV = (TextView) findViewById(R.id.locDescripTxt);
        ImageView locIV = (ImageView) findViewById(R.id.locImageIV);

        try
        {
            JSONObject jsonResult = new JSONObject(GetLocationInfo.jsonResult);
            JSONArray locationInfo = jsonResult.getJSONArray("result");
            JSONObject jsonObject = locationInfo.getJSONObject(0);
            setTitle(jsonObject.getString("name"));
            locDescTV.setText(jsonObject.getString("description"));
            locIV.setImageBitmap(GetLocationInfo.image);
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
    }

}
