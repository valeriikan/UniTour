package fi.oulu.unitour.helpers;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
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

import fi.oulu.unitour.activities.LoginActivity;

// sending request to server and getting answer
// Login stage: check if username:password are correct; if yes, get user data from server
public class LoginHelper extends AsyncTask<String,Void,String> {
    Context context;
    ProgressDialog loading;
    public LoginActivity source = null;
    public static String jsonResult = null;

    public LoginHelper(LoginActivity activity, Context ctx) {
        source = activity;
        context = ctx;
    }

    @Override
    protected String doInBackground(String... params) {
        String login_url = "http://unitour.000webhostapp.com/login.php";

        try {
            String username = params[0];
            String password = params[1];
            URL url = new URL(login_url);

            // creating an http connection to communicate with url
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);

            // sending a request to server to check is username:password pair correct
            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            String post_data = URLEncoder.encode("username","UTF-8")+"="+URLEncoder.encode(username,"UTF-8")+"&"
                    +URLEncoder.encode("password","UTF-8")+"="+URLEncoder.encode(password,"UTF-8");
            bufferedWriter.write(post_data);
            bufferedWriter.flush();
            bufferedWriter.close();
            outputStream.close();

            // reading answer from server
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
            jsonResult = bufferedReader.readLine();
            bufferedReader.close();
            inputStream.close();

            httpURLConnection.disconnect();
            return jsonResult;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        loading = ProgressDialog.show(context, "Please wait...",null,true,true);
    }

    @Override
    protected void onPostExecute(String jsonResult) {
        loading.dismiss();
        if (jsonResult.equals("Login error, please try again")) {
            Toast.makeText(context, jsonResult, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Login success", Toast.LENGTH_SHORT).show();
         //   source.loginPostExecute();
        }
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}