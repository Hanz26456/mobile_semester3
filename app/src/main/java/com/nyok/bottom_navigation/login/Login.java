package com.nyok.bottom_navigation.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.nyok.bottom_navigation.MainActivity;
import com.nyok.bottom_navigation.R;
import com.nyok.bottom_navigation.database.DatabaseHelperLogin;

public class Login extends AppCompatActivity {
    private TextView btn_edittext;
    private EditText Username, Password;
    private Button btnlogin;

    private DatabaseHelperLogin db;

    //shared pref
    public static final String SHARED_PREF_NAME = "myPref";
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

         Username = findViewById(R.id.username);
        Password = findViewById(R.id.passwordedittext);
        btnlogin = findViewById(R.id.btnlogin);
        btn_edittext = findViewById(R.id.textView7);

btn_edittext.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
       Register.newInstance().show(getSupportFragmentManager(), Register.TAG);
    }
});
 db = new DatabaseHelperLogin(this);

 sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);


        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getUsername = Username.getText().toString();
                String getPassword = Password.getText().toString();

                if (getUsername.isEmpty() || getPassword.isEmpty()) {  // Cek jika salah satu field kosong
                    Toast.makeText(getApplicationContext(), "Username atau password tidak boleh kosong!", Toast.LENGTH_SHORT).show();
                } else {
                    Boolean masuk = db.checkLogin(getUsername, getPassword);  // Perbaikan di sini
                    if (masuk == true) {
                        Boolean updateSession = db.upgradeSession("ada", 1);
                        if (updateSession == true) {
                            Toast.makeText(getApplicationContext(), "Berhasil Masuk", Toast.LENGTH_SHORT).show();
                            SharedPreferences.Editor editor = sharedPreferences.edit();

                            editor.putBoolean("masuk", true);
                            editor.apply();

                            Intent dashboard = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(dashboard);
                            finish();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Username atau password salah!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
