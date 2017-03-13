package fi.oulu.unitour.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import fi.oulu.unitour.R;

public class LoginActivity extends AppCompatActivity {

    //declaration of variables for layout elements
    ImageView imgLoginFacebook, imgLoginTwitter, imgLoginGoogle;
    EditText etLoginEmail, etLoginPassword;
    Button btnLogin;

    //Firebase authentication object
    FirebaseAuth mAuth;

    ProgressDialog mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Firebase elements declaration
        mAuth = FirebaseAuth.getInstance();
        mProgress = new ProgressDialog(this);

        //attaching layout elements to variables
        imgLoginFacebook = (ImageView) findViewById(R.id.imgLoginFacebook);
        imgLoginTwitter = (ImageView) findViewById(R.id.imgLoginTwitter);
        imgLoginGoogle = (ImageView) findViewById(R.id.imgLoginGoogle);
        etLoginEmail = (EditText) findViewById(R.id.etLoginEmail);
        etLoginPassword = (EditText) findViewById(R.id.etLoginPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);

        //attaching images to imageViews and applying listeners to them
        imgLoginFacebook.setImageResource(R.drawable.img_facebook2);
        imgLoginTwitter.setImageResource(R.drawable.img_twitter2);
        imgLoginGoogle.setImageResource(R.drawable.img_google2);
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
        imgLoginGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LoginActivity.this, "To be implemented", Toast.LENGTH_SHORT).show();
            }
        });

        //attaching listener to LogIn button
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               emailLogin();
            }
        });
    }

    //method to sign in with email:password
    private void emailLogin(){

        String email = etLoginEmail.getText().toString();
        String password = etLoginPassword.getText().toString();

        if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            Toast.makeText(LoginActivity.this, "Fill all the fields", Toast.LENGTH_SHORT).show();

        } else {
            mProgress.setMessage("Please wait...");
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (task.isSuccessful()) {
                        Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                        mProgress.dismiss();
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);

                    } else {
                        Toast.makeText(LoginActivity.this, "Wrong email or password", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}