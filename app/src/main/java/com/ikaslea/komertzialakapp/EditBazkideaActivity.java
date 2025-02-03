package com.ikaslea.komertzialakapp;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.ikaslea.komertzialakapp.models.Bazkidea;
import com.ikaslea.komertzialakapp.models.enums.BazkideMota;
import com.ikaslea.komertzialakapp.utils.DBManager;

public class EditBazkideaActivity extends AppCompatActivity {

    private TextView idText;

    private EditText izenaEditText,
        emailEditText,
        telefonoaEditText;

    private Spinner motaSpinner;

    private Button gordeButton,
        ezabatuButton;

    private Bazkidea bazkidea;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_bazkidea);

        idText = findViewById(R.id.idText);
        izenaEditText = findViewById(R.id.izenaEdittext);
        emailEditText = findViewById(R.id.emailEditText);
        telefonoaEditText = findViewById(R.id.telefonoaEditText);
        motaSpinner = findViewById(R.id.motaSpinner);
        gordeButton = findViewById(R.id.gordeButton);
        ezabatuButton = findViewById(R.id.ezabatuButton);

        bazkidea = (Bazkidea) getIntent().getSerializableExtra("bazkidea");

        ArrayAdapter<BazkideMota> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, BazkideMota.values());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        motaSpinner.setAdapter(adapter);

        idText.setText(String.valueOf(bazkidea.getId()));
        izenaEditText.setText(bazkidea.getIzena());
        emailEditText.setText(bazkidea.getEmail());
        telefonoaEditText.setText(bazkidea.getTelefonoa());
        int spinnerPosition = adapter.getPosition(bazkidea.getBazkideMota());
        motaSpinner.setSelection(spinnerPosition);

        gordeButton.setOnClickListener(v -> {
            bazkidea.setIzena(izenaEditText.getText().toString());
            bazkidea.setEmail(emailEditText.getText().toString());
            bazkidea.setTelefonoa(telefonoaEditText.getText().toString());
            bazkidea.setBazkideMota((BazkideMota) motaSpinner.getSelectedItem());
            DBManager.getInstance().save(bazkidea);
            finish();
        });

        ezabatuButton.setOnClickListener(v -> {
            DBManager.getInstance().delete(bazkidea);
            finish();
        });


    }
}