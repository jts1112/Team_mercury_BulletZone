package edu.unh.cs.cs619.bulletzone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class ReplayActivity extends AppCompatActivity {


    GridView gridView;
    Button leaveButton;
    Button playPauseButton;
    SeekBar speedAdjustmentBar;
    ProgressBar progressBar;
    TextView speedSliderTextView;
    TextView timestampTextView;

    boolean playing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_replay);

        playing = false;

        leaveButton = findViewById(R.id.leaveButton);
        playPauseButton = findViewById(R.id.playPauseButton);
        speedAdjustmentBar = findViewById(R.id.speedAdjustmentBar);
        progressBar = findViewById(R.id.progressBar);
        speedSliderTextView = findViewById(R.id.speedSliderTextView);
        timestampTextView = findViewById(R.id.timestampTextView);

        playPauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (playing) {
                    Toast.makeText(ReplayActivity.this, "Pausing Replay", Toast.LENGTH_SHORT).show();
                    playPauseButton.setText("PLAY");
                    playing = false;
                } else {
                    Toast.makeText(ReplayActivity.this, "Playing Replay", Toast.LENGTH_SHORT).show();
                    playPauseButton.setText("PAUSE");
                    playing = true;
                }

            }
        });


        leaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ReplayActivity.this,
                        TitleScreenActivity.class);
                startActivity(intent);
            }
        });

        // Add listener to speedAdjustmentBar
        speedAdjustmentBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                speedSliderTextView.setText(" " + String.valueOf(progress + 1) + "X Speed");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        // Add listener to progressBar
//        progressBar.setProgress(new SeekBar.OnSeekBarChangeListener() {
//            @Override
//            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//                timestampTextView.setText(String.valueOf(progress));
//            }
//
//            @Override
//            public void onStartTrackingTouch(SeekBar seekBar) {}
//
//            @Override
//            public void onStopTrackingTouch(SeekBar seekBar) {}
//        });

    }

}