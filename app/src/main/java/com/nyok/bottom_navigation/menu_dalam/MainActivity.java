package com.nyok.bottom_navigation.menu_dalam;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.nyok.bottom_navigation.R;
import com.nyok.bottom_navigation.databinding.ActivityMainBinding;
import com.etebarian.meowbottomnavigation.MeowBottomNavigation;

public class MainActivity extends AppCompatActivity {

    private MeowBottomNavigation meowBottomNavigation;
    private String fragmentName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        //coba

        meowBottomNavigation = findViewById(R.id.meowbottom);

        meowBottomNavigation.add(new MeowBottomNavigation.Model(1, R.drawable.iconhome));
        meowBottomNavigation.add(new MeowBottomNavigation.Model(2, R.drawable.iconuser));
        meowBottomNavigation.add(new MeowBottomNavigation.Model(3, R.drawable.iconkeranjang));
        meowBottomNavigation.add(new MeowBottomNavigation.Model(4, R.drawable.support));
        meowBottomNavigation.add(new MeowBottomNavigation.Model(5, R.drawable.iconsetting));

        meowBottomNavigation.setOnShowListener(new MeowBottomNavigation.ShowListener() {
            @Override
            public void onShowItem(MeowBottomNavigation.Model item) {
                switch (item.getId()) {
                    case 1:
                        loadFragment(new HomeFragment());
                        fragmentName = "HOME";
                        break;
                    case 2:
                        loadFragment(new ProfilFragment());
                        fragmentName = "PROFIL";
                        break;
                    case 3:
                       loadFragment(new KeranjangFragment());
                       fragmentName = "KERANJANG";
                        break;
                    case 4:
                        loadFragment(new SupportFragment());
                        fragmentName = "SUPPORT";
                        break;
                    case 5:
                        loadFragment(new SettingFragment());
                        fragmentName = "SETTING";
                        break;
                }
            }
        });

        meowBottomNavigation.setOnClickMenuListener(new MeowBottomNavigation.ClickListener() {
            @Override
            public void onClickItem(MeowBottomNavigation.Model item) {
                Toast.makeText(getApplicationContext(), fragmentName, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, fragment).commit();
    }
}