package edu.unh.cs.cs619.bulletzone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Since we are using the @EActivity annotation, anything done past this point will
        //be overridden by the work AndroidAnnotations does. If you need to do more setup,
        //add to the methods under @AfterViews (for view items) or @AfterInject (for Bean items) below



        setContentView(R.layout.activity_title_screen);

        playGameButton = findViewById(R.id.playGameButton);
        accountButton = findViewById(R.id.accountButton);
        inventoryButton = findViewById(R.id.inventoryButton);
        storeButton = findViewById(R.id.storeButton);

        playGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TitleScreenActivity.this, ClientActivity_.class);
                startActivity(intent);
            }
        });

        accountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TitleScreenActivity.this, AuthenticateActivity_.class);
                startActivity(intent);
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
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}