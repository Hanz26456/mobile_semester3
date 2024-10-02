package com.nyok.bottom_navigation.login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;  // Untuk logging
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GestureDetectorCompat;
import androidx.appcompat.widget.AppCompatButton; // Pastikan ini diimport

import com.nyok.bottom_navigation.R;

public class Landing3 extends AppCompatActivity {
    private GestureDetectorCompat gestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing3);

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
                            // Swipe ke kanan - Pindah ke Layout Landing2
                            Log.d("Gesture", "Swipe kanan detected");  // Logging swipe kanan
                            Intent intent = new Intent(Landing3.this, Landing2.class);
                            startActivity(intent);
                            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right); // Optional animation
                        }
                        return true;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return false;
            }
        });

        // Setup Button Listener
        AppCompatButton buttonLogin = findViewById(R.id.button1);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Arahkan ke Login
                Intent intent = new Intent(Landing3.this, Login.class); // Ganti dengan nama kelas login Anda
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left); // Optional animation
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
