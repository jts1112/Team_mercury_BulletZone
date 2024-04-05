package edu.unh.cs.cs619.bulletzone;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.NonConfigurationInstance;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.rest.spring.annotations.RestService;
import org.androidannotations.api.BackgroundExecutor;
import org.greenrobot.eventbus.EventBus;

//@EActivity(R.layout.activity_client)
public class TitleScreenActivity extends AppCompatActivity {

    public Button playGameButton;
    // Doubles as sign in button for logged out players
    public Button accountButton;
    public Button inventoryButton;
    public Button storeButton;

    public static final int REQUEST_LOGIN = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_title_screen);

        playGameButton = findViewById(R.id.playGameButton);
        accountButton = findViewById(R.id.accountButton);
        inventoryButton = findViewById(R.id.inventoryButton);
        storeButton = findViewById(R.id.storeButton);


        playGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(TitleScreenActivity.this, "Please Sign In!", Toast.LENGTH_SHORT).show();
            }
        });

        accountButton.setText((CharSequence) "Sign in");
        accountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TitleScreenActivity.this, AuthenticateActivity_.class);
                startActivityForResult(intent, REQUEST_LOGIN);
            }
        });

        inventoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(TitleScreenActivity.this, "Not Yet Implemented", Toast.LENGTH_SHORT).show();
            }
        });

        storeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(TitleScreenActivity.this, "Not Yet Implemented", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_LOGIN) {
            if (resultCode == RESULT_OK) {
                // User Logged in Successfully, Update UI

                accountButton.setText((CharSequence) "Account Info");
                accountButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(TitleScreenActivity.this, "Not Yet Implemented", Toast.LENGTH_SHORT).show();
                    }
                });

                playGameButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(TitleScreenActivity.this, ClientActivity_.class);
                        startActivity(intent);
                    }
                });

            } else if (resultCode == RESULT_CANCELED) {
                // User login failed. No UI changes
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onBackPressed() {
        // super.onBackPressed();
        // Not calling **super**, disables back button in current screen.
    }
}