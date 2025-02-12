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

/**
 * Erabiltzailearen saioa egiaztatzen du, eta behin saioa hasita, aplikazioko
 * nabigazio-menuaren bidez hainbat fragmentu kargatzen ditu.
 */

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Erabiltzailearen saioa egiaztatu
        SharedPreferences prefs = getSharedPreferences("USER_SESSION", MODE_PRIVATE);
        String erabiltzailea = prefs.getString("IZENA", null);
        boolean activateFields = getIntent().getBooleanExtra("activateFields", false);

        // Erabiltzailea saiatuta ez badago, LoginActivity-ra birbideratu
        if (erabiltzailea == null) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
            return;
        }

        // Hasierako diseinua kargatu
        setContentView(R.layout.activity_hasiera);

        // activateFields parametroa egia bada, zuzenean KatalogoaFragment kargatu
        if (activateFields) {
            KatalogoaFragment katalogoaFragment = new KatalogoaFragment();
            Bundle bundle = new Bundle();
            bundle.putBoolean("activateFields", true);
            katalogoaFragment.setArguments(bundle);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.containerView, katalogoaFragment)
                    .commit();

            // BottomNavigationView ez konfiguratu oraindik
            return;
        }

        // BottomNavigationView konfiguratu
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);

        // Hasierako pantaila: HomeFragment kargatu, baldin eta ez badago aurreko egoera gordeta
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.containerView, new HomeFragment())
                    .commit();
        }

        // BottomNavigationView-aren aukerak konfiguratu
        bottomNavigationView.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;

            // Nabigazio botoiak kudeatu
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

            // Hautatutako fragment-a bistaratzea
            if (selectedFragment != null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.containerView, selectedFragment)
                        .commit();
            }
            return true;
        });
    }
}
