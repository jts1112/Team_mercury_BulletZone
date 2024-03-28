package edu.unh.cs.cs619.bulletzone.util;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class ShakeDetector implements SensorEventListener {

    // Minimum acceleration needed to register as a shake event
    private static final float MIN_ACCELERATION = 15.0f;

    // Minimum time interval between shake events
    private static final long MIN_INTERVAL = 500; // in milliseconds

    private OnShakeListener shakeListener;

    private long lastShakeTime;

    public interface OnShakeListener {
        void onShake();
    }

    // creates sensorManager using clientActivity context
    public ShakeDetector(Context context) {
        SensorManager sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        Sensor accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void setOnShakeListener(OnShakeListener listener) {
        shakeListener = listener;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];

        double acceleration = Math.sqrt(x * x + y * y + z * z);

        if (acceleration > MIN_ACCELERATION) {
            long currentTime = System.currentTimeMillis();
            if (currentTime - lastShakeTime > MIN_INTERVAL) {
                lastShakeTime = currentTime;

                if (shakeListener != null) {
                    shakeListener.onShake();
                }
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Not needed
    }

    // Method to simulate a shake event
    public void simulateShake() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastShakeTime > MIN_INTERVAL) {
            lastShakeTime = currentTime;
            if (shakeListener != null) {
                shakeListener.onShake();
            }
        }
    }
}
