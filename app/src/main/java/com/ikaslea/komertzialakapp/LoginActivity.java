package com.ikaslea.komertzialakapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.ikaslea.komertzialakapp.models.Komerziala;
import com.ikaslea.komertzialakapp.utils.AuthManager;
import com.ikaslea.komertzialakapp.utils.DBManager;

public class LoginActivity extends AppCompatActivity {

    private EditText editTextIzena, editTextPasahitza;
    private Button buttonLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Testu kanpoak
        editTextIzena = findViewById(R.id.username);
        editTextPasahitza = findViewById(R.id.password);
        buttonLogin = findViewById(R.id.loginButton);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }

    /*
        Login logika metodo batean inplementatu
     */
    private void login() {
        String izenaInput = editTextIzena.getText().toString().trim();
        String pasahitzaInput = editTextPasahitza.getText().toString().trim();

        if (izenaInput.isEmpty() || pasahitzaInput.isEmpty()) {
            Toast.makeText(this, "Mesedez, sartu kanpo guztiak", Toast.LENGTH_SHORT).show();
            return;
        }

        // Autentikazioa egin datu basearekin
        Komerziala erabiltzailea = AuthManager.login(izenaInput, pasahitzaInput);
        System.out.println("Erabiltzailea: " + erabiltzailea);

        if (erabiltzailea != null) {
            SharedPreferences prefs = getSharedPreferences("USER_SESSION", MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("IZENA", erabiltzailea.getIzena());
            editor.apply();

            // **Iniciar MainActivity**
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "Erabiltzailea edo pasahitza okerrak", Toast.LENGTH_SHORT).show();
        }
    }

}