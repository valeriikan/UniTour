package fi.oulu.unitour.helpers;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import fi.oulu.unitour.activities.LocationDescriptor;
import fi.oulu.unitour.activities.LoginActivity;
import fi.oulu.unitour.activities.MapActivity;

// sending request to server and getting answer
// Login stage: check if username:password are correct; if yes, get user data from server
public class GetLocationInfo extends AsyncTask<String,Void,String> {
    Context context;
    ProgressDialog loading;
    public LocationDescriptor source = null;
    public static String jsonResult = null;
    public static Bitmap image = null;

    public GetLocationInfo (LocationDescriptor activity, Context ctx) {
        source = activity;
        context = ctx;
    }

    @Override
    protected String doInBackground(String... params) {
        String get_loc_info_url = "http://unitour.000webhostapp.com/getlocationinfo.php";
        String get_loc_image_url = "http://unitour.000webhostapp.com/getlocationimage.php";

        try {
            String id = params[0];
            URL url = new URL(get_loc_info_url);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.connect();

            // sending a request to server to check is username:password pair correct
            OutputStream outputStream = connection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            String post_data = URLEncoder.encode("id","UTF-8")+"="+URLEncoder.encode(id,"UTF-8");
            bufferedWriter.write(post_data);
            bufferedWriter.flush();
            bufferedWriter.close();
            outputStream.close();

            // reading answer from server
            InputStream inputStream = connection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
            jsonResult = bufferedReader.readLine();
            bufferedReader.close();
            inputStream.close();

            connection.disconnect();
            //jsonResult = BitmapFactory.decodeStream(inputStream);
            //return jsonResult;
        } catch (IOException e) {
            // Log exception
            //return null;
        }
        try {
            String id = params[0];
            URL url = new URL(get_loc_image_url);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.connect();

            // sending a request to server to check is username:password pair correct
            OutputStream outputStream = connection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            String post_data = URLEncoder.encode("id","UTF-8")+"="+URLEncoder.encode(id,"UTF-8");
            bufferedWriter.write(post_data);
            bufferedWriter.flush();
            bufferedWriter.close();
            outputStream.close();

            // reading answer from server
            InputStream inputStream = connection.getInputStream();
            image = BitmapFactory.decodeStream(inputStream);

            connection.disconnect();
        } catch (IOException e) {
            // Log exception
            //return null;
        }
        return jsonResult;
    }

    @Override
    protected void onPreExecute() {
        loading = ProgressDialog.show(context, "Please wait...",null,true,true);
    }

    @Override
    protected void onPostExecute(String jsonResult) {
        loading.dismiss();
        source.fetchInfoPostExecute();
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}