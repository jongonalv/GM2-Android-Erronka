package com.ikaslea.komertzialakapp;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.ikaslea.komertzialakapp.adapters.BazkideaAdapter;
import com.ikaslea.komertzialakapp.models.Bazkidea;
import com.ikaslea.komertzialakapp.models.Bisita;
import com.ikaslea.komertzialakapp.models.Eskaera;
import com.ikaslea.komertzialakapp.models.enums.BazkideMota;
import com.ikaslea.komertzialakapp.models.enums.Egoera;
import com.ikaslea.komertzialakapp.utils.DBManager;

import java.util.List;

public class EditEskaeraActivity extends AppCompatActivity {

    private TextView idText;
    private EditText bazkideaEditText,
            dataEditText,
            totalaEditText,
            konzeptuaEditText;

    private Spinner egoerakSpinner;
    private Eskaera eskaera;



    private Button gordeButton,
            gehituButton,
            ezabatuButton,
            entregatutaButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_eskaera);

        idText = findViewById(R.id.idText);

        Intent intent = getIntent();
        eskaera = (Eskaera) intent.getSerializableExtra("eskaera");

        // Eskaeraren Id-a edit textean ezarri
        idText.setText(String.valueOf(eskaera.getId()));


        egoerakSpinner = findViewById(R.id.egoerakSpinner);
        bazkideaEditText = findViewById(R.id.bazkideaEditText);
        dataEditText = findViewById(R.id.dataEditText);
        totalaEditText = findViewById(R.id.totalaEditText);
        konzeptuaEditText = findViewById(R.id.konzeptuaEditText);
        gordeButton = findViewById(R.id.gordeButton);
        gehituButton = findViewById(R.id.gehituButton);
        ezabatuButton = findViewById(R.id.ezabatuButton);
        entregatutaButton = findViewById(R.id.entregatutaButton);

        egoerakSpinner.setEnabled(false);
        bazkideaEditText.setFocusable(false);
        dataEditText.setFocusable(false);
        totalaEditText.setFocusable(false);

        // Egoeren spinner-a sortu
        ArrayAdapter<Egoera> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, Egoera.values());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        egoerakSpinner.setAdapter(adapter);

        //Aukeratutako aukera datu-basean oinarrituta ezartzea
        if (eskaera.getEgoera() != null) {
            int position = adapter.getPosition(eskaera.getEgoera());
            egoerakSpinner.setSelection(position);
        }

        // Data ezarri
        dataEditText.setText(String.valueOf(eskaera.getEskaeraData().toLocalDate()));

        // Data pickerraren klikaren kudeaketa
        dataEditText.setOnClickListener( v -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, year, month, dayOfMonth) -> {
                eskaera.setEskaeraData(eskaera.getEskaeraData().withYear(year).withMonth(month+1).withDayOfMonth(dayOfMonth));


                dataEditText.setText(eskaera.getEskaeraData().toLocalDate().toString());

            },
                    eskaera.getEskaeraData().getYear(),
                    eskaera.getEskaeraData().getMonthValue()-1,
                    eskaera.getEskaeraData().getDayOfMonth());
            datePickerDialog.show();
        });


        bazkideaEditText.setText(eskaera.getBazkidea() != null ? eskaera.getBazkidea().getIzena() : "");
        dataEditText.setText(eskaera.getEskaeraData() != null ? eskaera.getEskaeraData().toLocalDate().toString() : "");
        totalaEditText.setText(String.valueOf(eskaera.getGuztira()));
        konzeptuaEditText.setText(eskaera.getKontzeptua());

        //Egoera spinner-a onSelect listener-a
        egoerakSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Egoera selectedEgoera = (Egoera) parent.getItemAtPosition(position);
                eskaera.setEgoera(selectedEgoera);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });


        //Gorde botoia, datu basean aldaketak gordetzeko
        gordeButton.setOnClickListener(v -> {
            eskaera.setKontzeptua(konzeptuaEditText.getText().toString());

            DBManager.getInstance().save(eskaera);
            finish();
        });

        //Ezabatu botoia, aukeratutako eskaera ezabatzeko
        ezabatuButton.setOnClickListener(v -> {
            DBManager.getInstance().delete(eskaera);
            finish();
        });

        bazkideaEditText.setOnClickListener(v -> {showBazkideakDialog();});

        // Entregatu botoia, Egoera "BIDALITA" denean bakarrik exekutatu
        entregatutaButton.setOnClickListener(view -> {
            if (eskaera.getEgoera() == Egoera.BIDALITA) {
                eskaera.setEgoera(Egoera.BUKATUTA);
                DBManager.getInstance().save(eskaera);
                finish();
            }
        });


    }


    /**
     * Bazkideak aukeratzeko dialogoa erakusten du.
     */
    private void showBazkideakDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_bisita_list, null);
        builder.setView(dialogView);

        RecyclerView recyclerView = dialogView.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        AlertDialog alertDialog = builder.create();

        // Bazkideak lortzen dira eta adaptadorearen bidez erakusten dira
        List<Bazkidea> bazkideaList = DBManager.getInstance().getAll(Bazkidea.class);
        BazkideaAdapter bazkideaAdapter = new BazkideaAdapter(this, bazkideaList, bazkidea -> {
            bazkideaEditText.setText(bazkidea.getIzena()); // Aukeratutako Bazkidea EditText-ean jartzen da
            eskaera.setBazkidea(bazkidea); //  Eskaera objektua eguneratzen da
            alertDialog.dismiss(); // Dialogoa itxi
        });

        recyclerView.setAdapter(bazkideaAdapter);

        alertDialog.show();
    }



}