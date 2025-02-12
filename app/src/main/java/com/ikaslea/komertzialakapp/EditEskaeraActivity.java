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
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ikaslea.komertzialakapp.adapters.BazkideaAdapter;
import com.ikaslea.komertzialakapp.models.Artikuloa;
import com.ikaslea.komertzialakapp.models.Bazkidea;
import com.ikaslea.komertzialakapp.models.Eskaera;
import com.ikaslea.komertzialakapp.models.EskaeraArtikuloa;
import com.ikaslea.komertzialakapp.models.enums.Egoera;
import com.ikaslea.komertzialakapp.utils.DBManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EditEskaeraActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_SELECT_PRODUCTS = 1;
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

    private Map<Artikuloa, Integer> selectedProducts = new HashMap<>(); // Variable de clase para almacenar los productos seleccionados

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_eskaera);

        idText = findViewById(R.id.idText);

        Intent intent = getIntent();
        eskaera = (Eskaera) intent.getSerializableExtra("eskaera");

        // Eskaeraren ID-a edit textean ezarri
        idText.setText(String.valueOf("Eskaera zenbakia: " + eskaera.getId()));

        egoerakSpinner = findViewById(R.id.egoerakSpinner);
        bazkideaEditText = findViewById(R.id.bazkideaEditText);
        dataEditText = findViewById(R.id.dataEditText);
        totalaEditText = findViewById(R.id.totalaEditText);
        konzeptuaEditText = findViewById(R.id.konzeptuaEditText);
        gordeButton = findViewById(R.id.gordeButton);
        gehituButton = findViewById(R.id.gehituButton);
        ezabatuButton = findViewById(R.id.ezabatuButton);
        entregatutaButton = findViewById(R.id.entregatutaButton);

        // Zenbait elementu editagaitz bihurtu
        egoerakSpinner.setEnabled(false);
        bazkideaEditText.setFocusable(false);
        dataEditText.setFocusable(false);
        totalaEditText.setFocusable(false);

        // Egoeren spinner-a sortu
        ArrayAdapter<Egoera> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, Egoera.values());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        egoerakSpinner.setAdapter(adapter);

        // Produktuak gehitzeko botoia
        gehituButton.setOnClickListener(v -> {
            Intent intentHasiera = new Intent(this, MainActivity.class);
            intentHasiera.putExtra("activateFields", true);
            startActivityForResult(intentHasiera, REQUEST_CODE_SELECT_PRODUCTS); // Emaitza jasotzeko
        });

        // Aukeratutako egoera datu-basean oinarrituta ezartzea
        if (eskaera.getEgoera() != null) {
            int position = adapter.getPosition(eskaera.getEgoera());
            egoerakSpinner.setSelection(position);
        }

        // Eskaeraren data ezarri
        dataEditText.setText(String.valueOf(eskaera.getEskaeraData().toLocalDate()));

        // Data editatu ahal izateko DatePickerDialog
        dataEditText.setOnClickListener(v -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, year, month, dayOfMonth) -> {
                eskaera.setEskaeraData(eskaera.getEskaeraData().withYear(year).withMonth(month + 1).withDayOfMonth(dayOfMonth));
                dataEditText.setText(eskaera.getEskaeraData().toLocalDate().toString());
            }, eskaera.getEskaeraData().getYear(), eskaera.getEskaeraData().getMonthValue() - 1, eskaera.getEskaeraData().getDayOfMonth());
            datePickerDialog.show();
        });

        // Eskaeraren datuak erakutsi
        bazkideaEditText.setText(eskaera.getBazkidea() != null ? eskaera.getBazkidea().getIzena() : "");
        dataEditText.setText(eskaera.getEskaeraData() != null ? eskaera.getEskaeraData().toLocalDate().toString() : "");
        totalaEditText.setText(String.valueOf(eskaera.getGuztira()));
        konzeptuaEditText.setText(eskaera.getKontzeptua());

        // Egoera spinner-a hautatutako elementua aldatzean
        egoerakSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Egoera selectedEgoera = (Egoera) parent.getItemAtPosition(position);
                eskaera.setEgoera(selectedEgoera);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        // Gorde botoia, aldaketak gordetzeko
        gordeButton.setOnClickListener(v -> {
            // Kontzeptua eguneratu
            eskaera.setKontzeptua(konzeptuaEditText.getText().toString());

            // Eskaera datu-basean gorde
            DBManager.getInstance().save(eskaera);

            // Produktuak hautatu badira, EskaeraArtikuloa sortu
            if (!selectedProducts.isEmpty()) {
                for (Map.Entry<Artikuloa, Integer> entry : selectedProducts.entrySet()) {
                    Artikuloa artikuloa = entry.getKey();
                    int cantidad = entry.getValue();

                    // EskaeraArtikuloa sortu
                    EskaeraArtikuloa eskaeraArtikuloa = new EskaeraArtikuloa();
                    eskaeraArtikuloa.setArtikuloa(artikuloa);
                    eskaeraArtikuloa.setKantitatea(cantidad);
                    eskaeraArtikuloa.setEskaera(eskaera);

                    // Guztizko prezioa kalkulatu (prezioa * kantitatea)
                    double total = artikuloa.getPrezioa() * cantidad;
                    eskaeraArtikuloa.setGuztira(total);

                    // EskaeraArtikuloa datu-basean gorde
                    DBManager.getInstance().save(eskaeraArtikuloa);
                }

                // Eskaeraren guztizko prezioa eguneratu
                double totalEskaera = selectedProducts.entrySet().stream()
                        .mapToDouble(entry -> entry.getKey().getPrezioa() * entry.getValue())
                        .sum();
                eskaera.setGuztira(totalEskaera);

                // Eguneratutako eskaera berriz gorde
                DBManager.getInstance().save(eskaera);
            }

            // Arrakasta mezua erakutsi
            Toast.makeText(this, "Aldaketak ondo gorde dira!", Toast.LENGTH_SHORT).show();

            // Aktibitatea itxi
            finish();
        });

        // Ezabatu botoia, eskaera ezabatzeko
        ezabatuButton.setOnClickListener(v -> {
            DBManager.getInstance().delete(eskaera);
            finish();
        });

        // Bazkideak hautatzeko klik egitean
        bazkideaEditText.setOnClickListener(v -> showBazkideakDialog());

        // Entregatu botoia, egoera "BIDALITA" bada bakarrik
        entregatutaButton.setOnClickListener(view -> {
            if (eskaera.getEgoera() == Egoera.BIDALITA) {
                eskaera.setEgoera(Egoera.BUKATUTA);
                DBManager.getInstance().save(eskaera);
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_SELECT_PRODUCTS && resultCode == RESULT_OK) {
            selectedProducts = (Map<Artikuloa, Integer>) data.getSerializableExtra("selectedProducts");

            // Aukeratutako produktuak prozesatzen ditugu
            if (selectedProducts != null && !selectedProducts.isEmpty()) {
                double totalEskaera = selectedProducts.entrySet().stream()
                        .mapToDouble(entry -> entry.getKey().getPrezioa() * entry.getValue())
                        .sum();

                // Eskaeraren prezio totala jarri
                totalaEditText.setText(String.valueOf(totalEskaera) + "$");

                for (Map.Entry<Artikuloa, Integer> entry : selectedProducts.entrySet()) {
                    Artikuloa artikuloa = entry.getKey();
                    int cantidad = entry.getValue();
                    System.out.println("Producto: " + artikuloa.getIzena() + ", Cantidad: " + cantidad);
                }
            }
        }
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