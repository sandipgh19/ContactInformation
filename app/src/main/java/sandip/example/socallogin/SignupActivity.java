package sandip.example.socallogin;

import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SignupActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText nameTxt, emailTxt, passwordTxt;
    private TextInputLayout nameLayout, emailLayout, passwordLayout;

    private Button signUp;
    private TextView login;
    private RelativeLayout relativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        nameLayout =findViewById(R.id.name_layout);
        emailLayout = findViewById(R.id.email_layout);
        passwordLayout = findViewById(R.id.password_layout);

        nameTxt  = findViewById(R.id.name);
        emailTxt = findViewById(R.id.email);
        passwordTxt = findViewById(R.id.password);
        relativeLayout = findViewById(R.id.RelativeLayoutPassword);

        if(getIntent().hasExtra("email")) {
            emailTxt.setText(getIntent().getStringExtra("email"));
            emailTxt.setEnabled(false);
        }

        if(getIntent().hasExtra("name")) {
            nameTxt.setText(getIntent().getStringExtra("name"));
            nameTxt.setEnabled(false);
        }

        if(getIntent().hasExtra("type")) {
            String type = getIntent().getStringExtra("type");
            if(type.equalsIgnoreCase("gmail")) {

                relativeLayout.setVisibility(View.GONE);
                passwordTxt.setText("gmail");
            }else if(type.equalsIgnoreCase("facebook")) {

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

        if(view==signUp) {


        }else if (view==login) {

        }
    }
}
