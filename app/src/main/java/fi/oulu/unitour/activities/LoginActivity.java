package fi.oulu.unitour.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import fi.oulu.unitour.R;
import fi.oulu.unitour.helpers.LoginHelper;

public class LoginActivity extends AppCompatActivity {

    //declaration of variables for layout elements
    ImageView imgLoginFacebook, imgLoginTwitter, imgLoginSnapchat;
    EditText etLoginUsername, etLoginPassword;
    Button btnLogin;

    String username, password;

    //string for extracting data from JSON array answer from server
    private static final String JSON_ARRAY ="result";
    private static final String JSON_USERNAME = "username";
    private static final String JSON_FIRSTNAME= "firstname";
    private static final String JSON_SECONDNAME = "secondname";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //attaching layout elements to variables
        imgLoginFacebook = (ImageView) findViewById(R.id.imgLoginFacebook);
        imgLoginTwitter = (ImageView) findViewById(R.id.imgLoginTwitter);
        imgLoginSnapchat = (ImageView) findViewById(R.id.imgLoginSnapchat);
        etLoginUsername = (EditText) findViewById(R.id.etLoginUsername);
        etLoginPassword = (EditText) findViewById(R.id.etLoginPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);

        //attaching images to imageViews and applying listeners to them
        imgLoginFacebook.setImageResource(R.drawable.img_facebook);
        imgLoginTwitter.setImageResource(R.drawable.img_twitter);
        imgLoginSnapchat.setImageResource(R.drawable.img_snapchat);
        imgLoginFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LoginActivity.this, "To be implemented", Toast.LENGTH_SHORT).show();
            }
        });
        imgLoginTwitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LoginActivity.this, "To be implemented", Toast.LENGTH_SHORT).show();
            }
        });
        imgLoginSnapchat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LoginActivity.this, "To be implemented", Toast.LENGTH_SHORT).show();
            }
        });

        //attaching listener to LogIn button
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = etLoginUsername.getText().toString();
                password = etLoginPassword.getText().toString();

                if (!username.equals("") && !password.equals("")) {
                    LoginHelper loginHelper = new LoginHelper(LoginActivity.this, LoginActivity.this);
                    loginHelper.execute(username, password);

                } else {
                    Toast.makeText(LoginActivity.this, "Fill all the fields", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    //if log in was successful - parse user data from server response,
    //save it to sharedPreferences and open MainActivity
    public void loginPostExecute(){
        try {
            JSONObject jsonResult = new JSONObject(LoginHelper.jsonResult);
            JSONArray userinfo = jsonResult.getJSONArray(JSON_ARRAY);
            JSONObject jsonObject = userinfo.getJSONObject(0);

            SharedPreferences sPref = getSharedPreferences("session", MODE_PRIVATE);
            SharedPreferences.Editor ed = sPref.edit();
            ed.putString("username", jsonObject.getString(JSON_USERNAME));
            ed.putString("firstname", jsonObject.getString(JSON_FIRSTNAME));
            ed.putString("secondname", jsonObject.getString(JSON_SECONDNAME));
            ed.commit();


            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
