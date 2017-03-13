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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import fi.oulu.unitour.R;

public class SignupActivity extends AppCompatActivity {

    //declaration of variables for layout elements
    ImageView imgSignupFacebook, imgSignupTwitter, imgSignupGoogle;
    EditText etSignupEmail, etSignupPassword, etSignupFirstname, etSignupSecondname;
    Button btnSignUp;

    //Firebase authentication objects
    FirebaseAuth mAuth;
    DatabaseReference mDatabase;

    ProgressDialog mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        //Firebase elements declaration
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("users");
        mProgress = new ProgressDialog(this);

        //attaching layout elements to variables
        imgSignupFacebook = (ImageView) findViewById(R.id.imgSignupFacebook);
        imgSignupTwitter = (ImageView) findViewById(R.id.imgSignupTwitter);
        imgSignupGoogle = (ImageView) findViewById(R.id.imgSignupGoogle);
        etSignupEmail = (EditText) findViewById(R.id.etSignupEmail);
        etSignupPassword = (EditText) findViewById(R.id.etSignupPassword);
        etSignupFirstname = (EditText) findViewById(R.id.etSignupFirstname);
        etSignupSecondname = (EditText) findViewById(R.id.etSignupSecondname);
        btnSignUp = (Button) findViewById(R.id.btnSignup);

        //attaching images to imageViews and applying listeners to them
        imgSignupFacebook.setImageResource(R.drawable.img_facebook2);
        imgSignupTwitter.setImageResource(R.drawable.img_twitter2);
        imgSignupGoogle.setImageResource(R.drawable.img_google2);
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
        imgSignupGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SignupActivity.this, "To be implemented", Toast.LENGTH_SHORT).show();
            }
        });

        //attaching listener to SignUp button
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emailSignIn();
            }
        });
    }

    //method to sign up with email:password
    private void emailSignIn() {

        String email = etSignupEmail.getText().toString();
        String password = etSignupPassword.getText().toString();
        final String firstname = etSignupFirstname.getText().toString();
        final String secondname = etSignupSecondname.getText().toString();

        if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(firstname) || TextUtils.isEmpty(secondname)) {
            Toast.makeText(SignupActivity.this, "Fill all the fields", Toast.LENGTH_SHORT).show();

        } else {
            mProgress.setMessage("Please wait...");
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if(task.isSuccessful()) {
                        String userId = mAuth.getCurrentUser().getUid();
                        DatabaseReference currentUserDb = mDatabase.child(userId);
                        currentUserDb.child("firstname").setValue(firstname);
                        currentUserDb.child("secondname").setValue(secondname);

                        Toast.makeText(SignupActivity.this, "Registration successful", Toast.LENGTH_SHORT).show();
                        mProgress.dismiss();
                        Intent intent = new Intent(SignupActivity.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);

                    } else {
                        Toast.makeText(SignupActivity.this, "Your email is already registered or your password is less than 6 symbols", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}