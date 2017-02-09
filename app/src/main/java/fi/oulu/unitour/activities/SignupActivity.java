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

import fi.oulu.unitour.R;
import fi.oulu.unitour.helpers.SignupHelper;

public class SignupActivity extends AppCompatActivity {

    //declaration of variables for layout elements
    ImageView imgSignupFacebook, imgSignupTwitter, imgSignupSnapchat;
    EditText etSignupUsername, etSignupPassword, etSignupFirstname, etSignupSecondname;
    Button btnSignUp;

    String username, password, firstname, secondname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        //attaching layout elements to variables
        imgSignupFacebook = (ImageView) findViewById(R.id.imgSignupFacebook);
        imgSignupTwitter = (ImageView) findViewById(R.id.imgSignupTwitter);
        imgSignupSnapchat = (ImageView) findViewById(R.id.imgSignupSnapchat);
        etSignupUsername = (EditText) findViewById(R.id.etSignupUsername);
        etSignupPassword = (EditText) findViewById(R.id.etSignupPassword);
        etSignupFirstname = (EditText) findViewById(R.id.etSignupFirstname);
        etSignupSecondname = (EditText) findViewById(R.id.etSignupSecondname);
        btnSignUp = (Button) findViewById(R.id.btnSignup);

        //attaching images to imageViews and applying listeners to them
        imgSignupFacebook.setImageResource(R.drawable.img_facebook);
        imgSignupTwitter.setImageResource(R.drawable.img_twitter);
        imgSignupSnapchat.setImageResource(R.drawable.img_snapchat);
        imgSignupFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SignupActivity.this, "To be implemented", Toast.LENGTH_SHORT).show();
            }
        });
        imgSignupTwitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SignupActivity.this, "To be implemented", Toast.LENGTH_SHORT).show();
            }
        });
        imgSignupSnapchat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SignupActivity.this, "To be implemented", Toast.LENGTH_SHORT).show();
            }
        });

        //attaching listener to SignUp button
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = etSignupUsername.getText().toString();
                password = etSignupPassword.getText().toString();
                firstname = etSignupFirstname.getText().toString();
                secondname = etSignupSecondname.getText().toString();

                if (!username.equals("") && !password.equals("") && !firstname.equals("") && !secondname.equals("")) {
                    SignupHelper signupHelper= new SignupHelper(SignupActivity.this, SignupActivity.this);
                    signupHelper.execute(username, password, firstname, secondname);

                } else {
                    Toast.makeText(SignupActivity.this, "Fill all the fields", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    //if registration was successful - save user data to sharedPreferences and open MainActivity
    public void signupPostExecute(){
        SharedPreferences sPref = getSharedPreferences("session", MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putString("username", username);
        ed.putString("firstname", firstname);
        ed.putString("secondname", secondname);
        ed.commit();

        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);

    }

}
