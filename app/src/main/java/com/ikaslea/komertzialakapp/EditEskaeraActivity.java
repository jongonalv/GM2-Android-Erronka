package com.ikaslea.komertzialakapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.ikaslea.komertzialakapp.models.Eskaera;
import com.ikaslea.komertzialakapp.models.enums.BazkideMota;
import com.ikaslea.komertzialakapp.models.enums.Egoera;
import com.ikaslea.komertzialakapp.utils.DBManager;

public class EditEskaeraActivity extends AppCompatActivity {

    private EditText bazkideaEditText,
        dataEditText,
        totalaEditText,
        konzeptuaEditText;

    private Spinner egoerakSpinner;

    private Button gordeButton,
        gehituButton,
        ezabatuButton,
        entregatutaButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_eskaera);

        Intent intent = getIntent();
        Eskaera eskaera = (Eskaera) intent.getSerializableExtra("eskaera");

        egoerakSpinner = findViewById(R.id.egoerakSpinner);
        bazkideaEditText = findViewById(R.id.bazkideaEditText);
        dataEditText = findViewById(R.id.dataEditText);
        totalaEditText = findViewById(R.id.totalaEditText);
        konzeptuaEditText = findViewById(R.id.konzeptuaEditText);
        gordeButton = findViewById(R.id.gordeButton);
        gehituButton = findViewById(R.id.gehituButton);
        ezabatuButton = findViewById(R.id.ezabatuButton);
        entregatutaButton = findViewById(R.id.entregatutaButton);

        bazkideaEditText.setFocusable(false);
        dataEditText.setFocusable(false);
        totalaEditText.setFocusable(false);

        ArrayAdapter<Egoera> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, Egoera.values());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        egoerakSpinner.setAdapter(adapter);

        bazkideaEditText.setText(eskaera.getBazkidea() != null ? eskaera.getBazkidea().getIzena() : "");
        dataEditText.setText(eskaera.getEskaeraData() != null ? eskaera.getEskaeraData().toString() : "");
        totalaEditText.setText(String.valueOf(eskaera.getGuztira()));
        konzeptuaEditText.setText(eskaera.getKontzeptua());

        egoerakSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Egoera selectedEgoera = (Egoera) parent.getItemAtPosition(position);
                eskaera.setEgoera(selectedEgoera);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        gordeButton.setOnClickListener(v -> {
           eskaera.setKontzeptua(konzeptuaEditText.getText().toString());

            DBManager.getInstance().save(eskaera);
            finish();
        });



    }
}