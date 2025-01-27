package com.ikaslea.komertzialakapp;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hasiera);

        // Menu-a kargatu
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);

        // Hasierako fragmentua kargatzen dugu, non "Home orrialdea izango da"
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.containerView, new HomeFragment())
                    .commit();
        }

        // Menu-arentzako gertakaria, fragment ezberdinen artean nabigatzeko
        bottomNavigationView.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;

            if (item.getItemId() == R.id.nav_partner) {
                selectedFragment = new PartnerFragment();
            } else if (item.getItemId() == R.id.nav_calendar) {
                selectedFragment = new CalendarFragment();
            } else if (item.getItemId() == R.id.nav_home) {
                selectedFragment = new HomeFragment();
            } else if (item.getItemId() == R.id.nav_pedido) {
                selectedFragment = new EskaerakFragment();
            } else if (item.getItemId() == R.id.nav_back) {
                onBackPressed();
                return true;
            }

            if (selectedFragment != null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.containerView, selectedFragment)
                        .commit();
            }
            return true;
        });
    }
}
