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

        bazkideaEditText.setFocusable(false);
        dataEditText.setFocusable(false);
        totalaEditText.setFocusable(false);

        ArrayAdapter<Egoera> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, Egoera.values());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        egoerakSpinner.setAdapter(adapter);

        // Establecer la opción seleccionada basada en la base de datos
        if (eskaera.getEgoera() != null) {
            int position = adapter.getPosition(eskaera.getEgoera());
            egoerakSpinner.setSelection(position);
        }


        dataEditText.setText(String.valueOf(eskaera.getEskaeraData().toLocalDate()));


        dataEditText.setOnClickListener( v -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, year, month, dayOfMonth) -> {
                eskaera.setEskaeraData(eskaera.getEskaeraData().withYear(year).withMonth(month+1).withDayOfMonth(dayOfMonth));


                dataEditText.setText(String.valueOf(eskaera.getEskaeraData().toLocalDate()));

            },
                    eskaera.getEskaeraData().getYear(),
                    eskaera.getEskaeraData().getMonthValue()-1,
                    eskaera.getEskaeraData().getDayOfMonth());
            datePickerDialog.show();
        });

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

        ezabatuButton.setOnClickListener(v -> {
            DBManager.getInstance().delete(eskaera);
            finish();
        });

        bazkideaEditText.setOnClickListener(v -> {showBazkideakDialog();});


        entregatutaButton.setOnClickListener(view -> {
            // Asegurar que solo se ejecute si la egoera es BIDALITA
            if (eskaera.getEgoera() == Egoera.BIDALITA) {
                DBManager.getInstance().save(eskaera);
                finish();
            }
        });

        }


    private void showBazkideakDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_bisita_list, null);
        builder.setView(dialogView);

        RecyclerView recyclerView = dialogView.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        AlertDialog alertDialog = builder.create();

        List<Bazkidea> bazkideaList = DBManager.getInstance().getAll(Bazkidea.class);
        BazkideaAdapter bazkideaAdapter = new BazkideaAdapter(this, bazkideaList, bazkidea -> {
            bazkideaEditText.setText(bazkidea.getIzena()); // Actualiza el EditText con el nombre del Bazkidea seleccionado
            eskaera.setBazkidea(bazkidea); // Actualiza el Bazkidea de la Bisitabazkidea
            alertDialog.dismiss(); // Cierra el diálogo
        });

        recyclerView.setAdapter(bazkideaAdapter);

        alertDialog.show();
    }



}