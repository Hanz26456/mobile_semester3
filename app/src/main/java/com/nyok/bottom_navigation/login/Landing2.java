package com.nyok.bottom_navigation.login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;  // Untuk logging
import android.view.GestureDetector;
import android.view.MotionEvent;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GestureDetectorCompat;

import com.nyok.bottom_navigation.R;

public class Landing2 extends AppCompatActivity {
    private GestureDetectorCompat gestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing2);

        // Buat GestureDetector
        gestureDetector = new GestureDetectorCompat(this, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                Log.d("Gesture", "onFling detected"); // Logging untuk cek
                float SWIPE_THRESHOLD = 50;  // Sesuaikan agar lebih sensitif
                float SWIPE_VELOCITY_THRESHOLD = 50;  // Sesuaikan agar lebih sensitif

                try {
                    float diffX = e2.getX() - e1.getX();
                    if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                        if (diffX > 0) {
                            // Swipe ke kanan - Pindah ke Layout Landing1
                            Log.d("Gesture", "Swipe kanan detected");  // Logging swipe kanan
                            Intent intent = new Intent(Landing2.this, Landing1.class);
                            startActivity(intent);
                            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right); // Optional animation
                        } else if (diffX < 0) {
                            // Swipe ke kiri - Pindah ke Layout Landing3
                            Log.d("Gesture", "Swipe kiri detected");  // Logging swipe kiri
                            Intent intent = new Intent(Landing2.this, Landing3.class);
                            startActivity(intent);
                            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left); // Optional animation
                        }
                        return true;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return false;
            }
        });
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        // Kirim event touch ke GestureDetector
        if (gestureDetector != null) {
            gestureDetector.onTouchEvent(event);
        }
        return super.dispatchTouchEvent(event);
    }
}
