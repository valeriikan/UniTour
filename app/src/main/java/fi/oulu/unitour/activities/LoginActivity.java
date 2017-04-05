package fi.oulu.unitour.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Arrays;

import fi.oulu.unitour.R;

public class LoginActivity extends AppCompatActivity {

    //declaration of variables for layout elements
    ImageView imgFacebook, imgTwitter, imgGoogle;
    TextView tvSwitch, tvSocial;
    Switch switchLogin;
    EditText etLoginEmail, etLoginPassword, etSignupEmail, etSignupPassword, etSignupFirstname, etSignupSecondname;
    Button btnLogin, btnSignup;
    LinearLayout layoutLogin, layoutSignup, layoutUnitour;
    ProgressDialog mProgress;

    //Authentication objects
    FirebaseAuth mAuth;
    DatabaseReference mDatabase;
    GoogleApiClient mGoogleApiClient;
    CallbackManager mCallbackManager;

    private static final int RC_SIGN_IN = 1;
    private static final int FB_SIGN_IN = 2;
    static final int MAX_LOCATIONS = 16;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_login);

        //Authentication elements declaration
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("users");
        mCallbackManager = CallbackManager.Factory.create();

        // GOOGLE Sign In integration
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        // GOOGLE Sign In integration
        mGoogleApiClient = new GoogleApiClient.Builder(getApplicationContext())
                .enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                        Toast.makeText(LoginActivity.this, "Login error", Toast.LENGTH_SHORT).show();
                    }
                })
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        // FACEBOOK Sign It integration
        LoginManager.getInstance().registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.wtf("myTag", "facebook:onSuccess:" + loginResult);
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Log.wtf("myTag", "facebook:onCancel");
                // ...
            }

            @Override
            public void onError(FacebookException error) {
                Log.wtf("myTag", "facebook:onError", error);
                // ...
            }
        });

        //attaching layout elements to variables
        imgFacebook = (ImageView) findViewById(R.id.imgFacebook);
        imgTwitter = (ImageView) findViewById(R.id.imgTwitter);
        imgGoogle = (ImageView) findViewById(R.id.imgGoogle);
        tvSocial = (TextView) findViewById(R.id.tvSocial);
        tvSwitch = (TextView) findViewById(R.id.tvSwitch);
        switchLogin = (Switch) findViewById(R.id.switchLogin);
        etLoginEmail = (EditText) findViewById(R.id.etLoginEmail);
        etLoginPassword = (EditText) findViewById(R.id.etLoginPassword);
        etSignupEmail = (EditText) findViewById(R.id.etSignupEmail);
        etSignupPassword = (EditText) findViewById(R.id.etSignupPassword);
        etSignupFirstname = (EditText) findViewById(R.id.etSignupFirstname);
        etSignupSecondname = (EditText) findViewById(R.id.etSignupSecondname);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnSignup = (Button) findViewById(R.id.btnSignup);
        layoutLogin = (LinearLayout) findViewById(R.id.layoutLogin);
        layoutSignup = (LinearLayout) findViewById(R.id.layoutSignup);
        layoutUnitour = (LinearLayout) findViewById(R.id.layoutUnitour);
        mProgress = new ProgressDialog(this);

        //attaching images to imageViews and applying listeners to them
        imgFacebook.setImageResource(R.drawable.ui_social_facebook);
        imgTwitter.setImageResource(R.drawable.ui_social_twitter);
        imgGoogle.setImageResource(R.drawable.ui_social_google);
        imgFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginManager.getInstance()
                        .logInWithReadPermissions(LoginActivity.this, Arrays.asList("email", "public_profile"));
            }
        });
        imgTwitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LoginActivity.this, "To be implemented", Toast.LENGTH_SHORT).show();
            }
        });
        imgGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mProgress = ProgressDialog.show(LoginActivity.this, "Please wait...",null,true,true);
                googleSignIn();
            }
        });

        switchLogin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    tvSocial.setText("You can sign in with your own accounts");
                    tvSwitch.setText("I have UniTour account");
                    layoutSignup.setVisibility(View.INVISIBLE);
                    btnSignup.setVisibility(View.INVISIBLE);
                    layoutLogin.setVisibility(View.VISIBLE);
                    btnLogin.setVisibility(View.VISIBLE);
                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT);
                    params.addRule(RelativeLayout.ABOVE, R.id.layoutLogin);
                    layoutUnitour.setLayoutParams(params);

                } else {
                    tvSocial.setText("You can sign up with your own accounts");
                    tvSwitch.setText("I don't have UniTour account");
                    layoutLogin.setVisibility(View.INVISIBLE);
                    btnLogin.setVisibility(View.INVISIBLE);
                    layoutSignup.setVisibility(View.VISIBLE);
                    btnSignup.setVisibility(View.VISIBLE);
                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT);
                    params.addRule(RelativeLayout.ABOVE, R.id.layoutSignup);
                    layoutUnitour.setLayoutParams(params);
                }
            }
        });

        //attaching listener to LogIn button
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emailSignup();
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

    //method to SIGN UP with email:password
    private void emailSignup() {

        String email = etSignupEmail.getText().toString();
        String password = etSignupPassword.getText().toString();
        final String firstname = etSignupFirstname.getText().toString();
        final String secondname = etSignupSecondname.getText().toString();

        if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(firstname) || TextUtils.isEmpty(secondname)) {
            Toast.makeText(LoginActivity.this, "Fill all the fields", Toast.LENGTH_SHORT).show();

        } else {
            mProgress = ProgressDialog.show(LoginActivity.this, "Please wait...",null,true,true);
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if(task.isSuccessful()) {
                        String userId = mAuth.getCurrentUser().getUid();
                        DatabaseReference currentUserDb = mDatabase.child(userId);
                        currentUserDb.child("name").setValue(firstname + " " + secondname);
                        currentUserDb.child("completed").setValue("0");
                        currentUserDb.child("score").setValue("0");
                        for (int i= 1; i<=MAX_LOCATIONS; i++) {currentUserDb.child("gamedata").child("loc"+i).setValue("0");}

                        Toast.makeText(LoginActivity.this, "Registration successful", Toast.LENGTH_SHORT).show();
                        mProgress.dismiss();
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);

                    } else {
                        Toast.makeText(LoginActivity.this, "Your email is already registered or your password is less than 6 symbols", Toast.LENGTH_SHORT).show();
                        mProgress.dismiss();
                    }
                }
            });
        }
    }

    //method to LOG IN with email:password
    private void emailLogin(){

        String email = etLoginEmail.getText().toString();
        String password = etLoginPassword.getText().toString();

        if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            Toast.makeText(LoginActivity.this, "Fill all the fields", Toast.LENGTH_SHORT).show();

        } else {
            mProgress = ProgressDialog.show(LoginActivity.this, "Please wait...",null,true,true);
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

    //method to sign in with GOOGLE account
    private void googleSignIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    // GOOGLE Sign In integration
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);
            } else {
                // Google Sign In failed, update UI appropriately
                // ...
            }
        } else {
            mCallbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }

    // GOOGLE Sign In integration
    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {

        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.wtf("myTag", "signInWithCredential:onComplete:" + task.isSuccessful());

                        String username = mAuth.getCurrentUser().getDisplayName();;
                        String userId = mAuth.getCurrentUser().getUid();
                        String imageUrl = String.valueOf(mAuth.getCurrentUser().getPhotoUrl());
                        imageUrl = imageUrl.replace("/s96-c/","/s450-c/");
                        DatabaseReference currentUserDb = mDatabase.child(userId);
                        currentUserDb.child("name").setValue(username);
                        currentUserDb.child("imageUrl").setValue(imageUrl);
                        currentUserDb.child("completed").setValue("0");
                        currentUserDb.child("score").setValue("0");
                        for (int i= 1; i<=MAX_LOCATIONS; i++) {
                            currentUserDb.child("gamedata").child("loc"+i).setValue("0");
                        }

                        mProgress.dismiss();
                        Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.wtf("myTag", "signInWithCredential", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                        }
                        // ...
                    }
                });
    }

    // FACEBOOK Sing In integration
    private void handleFacebookAccessToken(AccessToken token) {
        Log.wtf("myTag", "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.wtf("myTag", "signInWithCredential:onComplete:" + task.isSuccessful());

                        String username = mAuth.getCurrentUser().getDisplayName();;
                        String userId = mAuth.getCurrentUser().getUid();
                        String facebookId = "";
                        // find the Facebook profile and get the user's id
                        for(UserInfo profile : mAuth.getCurrentUser().getProviderData()) {
                            // check if the provider id matches "facebook.com"
                            if(profile.getProviderId().equals(getString(R.string.facebook_provider_id))) {
                                facebookId = profile.getUid();
                            }
                        }
                        String imageUrl = "https://graph.facebook.com/" + facebookId + "/picture?width=450&height=450";
                        DatabaseReference currentUserDb = mDatabase.child(userId);
                        currentUserDb.child("name").setValue(username);
                        currentUserDb.child("imageUrl").setValue(imageUrl);
                        currentUserDb.child("completed").setValue("0");
                        currentUserDb.child("score").setValue("0");
                        for (int i= 1; i<=MAX_LOCATIONS; i++) {
                            currentUserDb.child("gamedata").child("loc"+i).setValue("0");
                        }

                        Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);

                        if (!task.isSuccessful()) {
                            Log.wtf("myTag", "signInWithCredential", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                        }
                        // ...
                    }
                });
    }

}