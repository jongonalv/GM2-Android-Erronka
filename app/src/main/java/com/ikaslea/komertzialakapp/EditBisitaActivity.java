package com.ikaslea.komertzialakapp;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ikaslea.komertzialakapp.adapters.BazkideaAdapter;
import com.ikaslea.komertzialakapp.adapters.BisitaAdapter;
import com.ikaslea.komertzialakapp.models.Bazkidea;
import com.ikaslea.komertzialakapp.models.Bisita;
import com.ikaslea.komertzialakapp.utils.DBManager;

import java.util.List;

/**
 * Bisitak kudeatzeko klasea.
 * Bisitako datuak editatzeko aukera ematen du:
 * - Data eta ordua aukeratu
 * - Bazkide bat esleitu
 * - Ekintzak burutu: gordetzea, ezabatzea edo bisita osorik eginda markatzea.
 */
public class EditBisitaActivity extends AppCompatActivity {

    // UI elementuak definitu
    private TextView idText;
    private EditText helbideaText, helburuaText, obserbazioakText, dateText, hasieraOrduaText, bukaeraOrduaText, bazkideaEditText;
    private Button gordeButton, ezabatuButton, egindaButton;
    private Bisita bisita;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_bisita);

        // UI elementuak lotu layout-arekin
        idText = findViewById(R.id.idText);
        helbideaText = findViewById(R.id.helbideaText);
        helburuaText = findViewById(R.id.helburaText);
        obserbazioakText = findViewById(R.id.obserbazioakText);
        dateText = findViewById(R.id.dateText);
        hasieraOrduaText = findViewById(R.id.hasieraOrduaText);
        bukaeraOrduaText = findViewById(R.id.bukaeraOrduaText);
        bazkideaEditText = findViewById(R.id.bazkideaEditText);

        // Editatzeko ezarri ezin diren eremuak desgaitu
        bazkideaEditText.setFocusable(false);
        dateText.setFocusable(false);
        hasieraOrduaText.setFocusable(false);
        bukaeraOrduaText.setFocusable(false);

        // Intent-etik bisitaren datuak eskuratu
        Intent intent = getIntent();
        bisita = (Bisita) intent.getSerializableExtra("bisita");

        // Bisitaren datuak UI elementuetan bistaratu
        idText.setText(String.valueOf("Bisita zenbakia: " + bisita.getId()));
        helbideaText.setText(bisita.getHelbidea());
        helburuaText.setText(bisita.getBisitarenHelburua());
        obserbazioakText.setText(bisita.getObserbazioak());

        dateText.setText(String.valueOf(bisita.getHasieraData().toLocalDate()));
        hasieraOrduaText.setText(String.valueOf(bisita.getHasieraData().toLocalTime()));
        bukaeraOrduaText.setText(String.valueOf(bisita.getBukaeraData().toLocalTime()));

        // Botoiak lotu
        gordeButton = findViewById(R.id.gordeButton);
        ezabatuButton = findViewById(R.id.ezabatuButton);
        egindaButton = findViewById(R.id.egindaButton);

        // Data hautatzeko klik-ekitaldia
        dateText.setOnClickListener(v -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, year, month, dayOfMonth) -> {
                bisita.setHasieraData(bisita.getHasieraData().withYear(year).withMonth(month + 1).withDayOfMonth(dayOfMonth));
                bisita.setBukaeraData(bisita.getBukaeraData().withYear(year).withMonth(month + 1).withDayOfMonth(dayOfMonth));

                dateText.setText(String.valueOf(bisita.getHasieraData().toLocalDate()));

            },
                    bisita.getHasieraData().getYear(),
                    bisita.getHasieraData().getMonthValue() - 1,
                    bisita.getHasieraData().getDayOfMonth());
            datePickerDialog.show();
        });

        // Hasierako ordua hautatzeko klik-ekitaldia
        hasieraOrduaText.setOnClickListener(v -> {
            TimePickerDialog timePickerDialog = new TimePickerDialog(this, (view, hourOfDay, minute) -> {
                bisita.setHasieraData(bisita.getHasieraData().withHour(hourOfDay).withMinute(minute));
                hasieraOrduaText.setText(String.valueOf(bisita.getHasieraData().toLocalTime()));
            },
                    bisita.getHasieraData().getHour(),
                    bisita.getHasieraData().getMinute(),
                    true);
            timePickerDialog.show();
        });

        // Amaierako ordua hautatzeko klik-ekitaldia
        bukaeraOrduaText.setOnClickListener(v -> {
            TimePickerDialog timePickerDialog = new TimePickerDialog(this, (view, hourOfDay, minute) -> {
                bisita.setBukaeraData(bisita.getBukaeraData().withHour(hourOfDay).withMinute(minute));
                bukaeraOrduaText.setText(String.valueOf(bisita.getBukaeraData().toLocalTime()));
            },
                    bisita.getBukaeraData().getHour(),
                    bisita.getBukaeraData().getMinute(),
                    true);
            timePickerDialog.show();
        });

        // Bazkidea hautatzeko klik-ekitaldia
        bazkideaEditText.setOnClickListener(v -> {
            showBazkideakDialog();
        });

        // Bisita gordetzeko botoiaren ekintza
        gordeButton.setOnClickListener(v -> {
            bisita.setHelbidea(helbideaText.getText().toString());
            bisita.setBisitarenHelburua(helburuaText.getText().toString());
            bisita.setObserbazioak(obserbazioakText.getText().toString());

            DBManager.getInstance().save(bisita); // Datu-basean gorde
            finish(); // Aktibitatea itxi
        });

        // Bisita ezabatzeko botoiaren ekintza
        ezabatuButton.setOnClickListener(v -> {
            DBManager.getInstance().delete(bisita); // Datu-baseko erregistroa ezabatu
            finish();
        });

        // Bisita osorik eginda markatzeko botoiaren ekintza
        egindaButton.setOnClickListener(v -> {
            bisita.setEginda(true);
            DBManager.getInstance().save(bisita);
            finish();
        });
    }

    /**
     * Bazkideak aukeratzeko elkarrizketa-koadroa erakusten du.
     */
    private void showBazkideakDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_bisita_list, null);
        builder.setView(dialogView);

        RecyclerView recyclerView = dialogView.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        AlertDialog alertDialog = builder.create();

        // Datu-baseko bazkideak lortu
        List<Bazkidea> bazkideaList = DBManager.getInstance().getAll(Bazkidea.class);
        BazkideaAdapter bazkideaAdapter = new BazkideaAdapter(this, bazkideaList, bazkidea -> {
            bazkideaEditText.setText(bazkidea.getIzena()); // Hautatutako bazkidearen izena eguneratu
            bisita.setBazkidea(bazkidea); // Bisitari bazkidea esleitu
            alertDialog.dismiss(); // Elkarrizketa-koadroa itxi
        });

        recyclerView.setAdapter(bazkideaAdapter);

        alertDialog.show();
    }
}
