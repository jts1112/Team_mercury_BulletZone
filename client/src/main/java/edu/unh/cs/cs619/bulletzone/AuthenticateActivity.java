package edu.unh.cs.cs619.bulletzone;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.SystemClock;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_authenticate)
public class AuthenticateActivity extends AppCompatActivity {
    @ViewById
    EditText username_editText;

    @ViewById
    EditText password_editText;

    @ViewById
    TextView status_message;

    @Bean
    AuthenticationController controller;

    private SharedPreferences sharedPref;

    long userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Since we are using the @EActivity annotation, anything done past this point will
        //be overridden by the work AndroidAnnotations does. If you need to do more setup,
        //add to the methods under @AfterViews (for view items) or @AfterInject (for Bean items) below
    }

    @AfterViews
    protected void afterViewInjection() {
        //Put any view-setup code here (that you might normally put in onCreate)
    }

    @AfterInject
    void afterInject() {
        //Put any Bean-related setup code here (the you might normally put in onCreate)
    }

    /**
     * Registers a new user and logs them in
     */
    @Click(R.id.register_button)
    @Background
    protected void onButtonRegister() {
        String username = username_editText.getText().toString();
        String password = password_editText.getText().toString();

        boolean status = controller.register(username, password);

        if (!status) {
            setStatus("User " + username + " already exists or server error.\nPlease login or try with a different username.");
        } else { //register successful
            setStatus("Registration successful.");
            //Do you want to log in automatically, or force them to do it?
            userID = controller.login(username, password);
            if (userID < 0) {
                setStatus("Registration unsuccessful--inconsistency with server.");
            }
            // Update shared pref
            sharedPref = getSharedPreferences("UserAuthentication", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putBoolean("isLoggedIn", true);
            editor.apply();

            // Go back to TitleScreenActivity
            Intent intent = new Intent(AuthenticateActivity.this, TitleScreenActivity.class);
            startActivity(intent);
        }
    }

    /**
     * Logs in an existing user
     */
    @Click(R.id.login_button)
    @Background
    protected void onButtonLogin() {
        String username = username_editText.getText().toString();
        String password = password_editText.getText().toString();

        userID = controller.login(username, password);
        if (userID == -1) {
            setStatus("Invalid username and/or password.\nPlease try again.");
        } else if (userID == -2) {
            setStatus("Server Login Timeout.");
        } else { //register successful
            setStatus("Login successful.");
            //do other login things
            Intent intent = new Intent(AuthenticateActivity.this, TitleScreenActivity.class);
            startActivity(intent);
        }
    }

    @UiThread
    protected void setStatus(String message) {
        status_message.setText(message);
    }
}