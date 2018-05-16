package sandip.example.socallogin;

import android.app.Dialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import dmax.dialog.SpotsDialog;

public class SignupActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText nameTxt, emailTxt, passwordTxt;
    private TextInputLayout nameLayout, emailLayout, passwordLayout;

    private Button signUp;
    private TextView login;
    private RelativeLayout relativeLayout;
    private FirebaseAuth auth;
    private DatabaseReference mDatabase;
    private ProgressBar progressBar;
    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference("user");

        dialog = new SpotsDialog(this);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);




        nameLayout = findViewById(R.id.name_layout);
        emailLayout = findViewById(R.id.email_layout);
        passwordLayout = findViewById(R.id.password_layout);

        nameTxt = findViewById(R.id.name);
        emailTxt = findViewById(R.id.email);
        passwordTxt = findViewById(R.id.password);
        relativeLayout = findViewById(R.id.RelativeLayoutPassword);

        if (getIntent().hasExtra("email")) {
            emailTxt.setText(getIntent().getStringExtra("email"));
            emailTxt.setEnabled(false);
        }

        if (getIntent().hasExtra("name")) {
            nameTxt.setText(getIntent().getStringExtra("name"));
            nameTxt.setEnabled(false);
        }

        if (getIntent().hasExtra("type")) {
            String type = getIntent().getStringExtra("type");
            if (type.equalsIgnoreCase("gmail")) {

                relativeLayout.setVisibility(View.GONE);
                passwordTxt.setText("gmail1");
            } else if (type.equalsIgnoreCase("facebook")) {

                relativeLayout.setVisibility(View.GONE);
                passwordTxt.setText("facebook");
            }
        }

        signUp = findViewById(R.id.signup);

        signUp.setOnClickListener(this);
        login = findViewById(R.id.login);
        login.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        if (view == signUp) {

            if (nameTxt.getText().toString().length() > 0 && emailTxt.getText().toString().length() > 0 && passwordTxt.getText().toString().length() > 0) {

                if(passwordTxt.getText().toString().length()>=6) {

                    dialog.show();

                    auth.createUserWithEmailAndPassword(emailTxt.getText().toString(), passwordTxt.getText().toString())
                            .addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    //Toast.makeText(SignupActivity.this, "createUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();
                                    // If sign in fails, display a message to the user. If sign in succeeds
                                    // the auth state listener will be notified and logic to handle the
                                    // signed in user can be handled in the listener.
                                    dialog.dismiss();
                                    if (!task.isSuccessful()) {
//
                                        Toast.makeText(SignupActivity.this, "Oops! Something Went Wrong!", Toast.LENGTH_LONG).show();

                                    } else {
                                        onAuthSuccess(task.getResult().getUser());
                                    }
                                }
                            });
                }else
                    Toast.makeText(SignupActivity.this,"Password must be 6 character long",Toast.LENGTH_LONG).show();



            }else {
                Toast.makeText(SignupActivity.this,"All fields are required",Toast.LENGTH_LONG).show();

            }

        } else if (view == login) {

            Intent intent = new Intent(SignupActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
    }


    private void onAuthSuccess(FirebaseUser user) {
        String username = usernameFromEmail(user.getEmail());

        // Write new user
        writeNewUser(user.getUid(), username, user.getEmail());

        // Go to MainActivity
        //startActivity(new Intent(SignupActivity.this, MainActivity.class));
        Intent intent = new Intent(SignupActivity.this,MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);

        finish();
    }

    private String usernameFromEmail(String email) {
        if (email.contains("@")) {
            return email.split("@")[0];
        } else {
            return email;
        }
    }

    private void writeNewUser(String userId, String name, String email) {
        User user = new User(name, email);

        mDatabase.child("user_profile").setValue(user);
    }
}
