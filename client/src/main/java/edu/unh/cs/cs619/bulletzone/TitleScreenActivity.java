package edu.unh.cs.cs619.bulletzone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.androidannotations.annotations.EActivity;

@EActivity(R.layout.activity_title_screen)
public class TitleScreenActivity extends AppCompatActivity {

    public Button playGameButton;
    // Doubles as sign in button for logged out players
    public Button accountButton;
    public Button inventoryButton;
    public Button storeButton;

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
                Toast.makeText(TitleScreenActivity.this, "Changing to play game", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(TitleScreenActivity.this, ClientActivity.class);
                startActivity(intent);
            }
        });

        accountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(TitleScreenActivity.this, "Not Yet Implemented", Toast.LENGTH_SHORT).show();
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
}