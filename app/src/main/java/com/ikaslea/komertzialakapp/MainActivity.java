package com.ikaslea.komertzialakapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.ikaslea.komertzialakapp.fragments.CalendarFragment;
import com.ikaslea.komertzialakapp.fragments.EskaerakFragment;
import com.ikaslea.komertzialakapp.fragments.HomeFragment;
import com.ikaslea.komertzialakapp.fragments.BazkideFragment;
import com.ikaslea.komertzialakapp.fragments.KatalogoaFragment;
import com.ikaslea.komertzialakapp.utils.DBManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences prefs = getSharedPreferences("USER_SESSION", MODE_PRIVATE);
        String erabiltzailea = prefs.getString("IZENA", null);
        System.out.println(erabiltzailea);

        // Erabiltzailea oraindik ez badago, login-a aktibatu
        if (erabiltzailea == null) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
            return;
        }

        // Bestela, activity hasierakoa
        setContentView(R.layout.activity_hasiera);

        // Menu-a kargatu
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.containerView, new HomeFragment())
                    .commit();
        }

        // Menuaren logika, fragment ezberdinetara bidaltzeko aukeraren arabera
        bottomNavigationView.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;

            if (item.getItemId() == R.id.nav_calendar) {
                selectedFragment = new CalendarFragment();
                Bundle args = new Bundle();
                args.putString("erabiltzailea", erabiltzailea);
                selectedFragment.setArguments(args);
            } else if (item.getItemId() == R.id.nav_partner) {
                selectedFragment = new BazkideFragment();
                Bundle args = new Bundle();
                args.putString("erabiltzailea", erabiltzailea);
                selectedFragment.setArguments(args);
            } else if (item.getItemId() == R.id.nav_home) {
                selectedFragment = new HomeFragment();
            } else if (item.getItemId() == R.id.nav_pedido) {
                selectedFragment = new EskaerakFragment();
                Bundle args = new Bundle();
                args.putString("erabiltzailea", erabiltzailea);
                selectedFragment.setArguments(args);
            } else if (item.getItemId() == R.id.nav_back) {
                selectedFragment = new KatalogoaFragment();
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