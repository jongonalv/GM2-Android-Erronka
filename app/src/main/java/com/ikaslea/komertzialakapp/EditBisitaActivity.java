package com.ikaslea.komertzialakapp;



import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.ikaslea.komertzialakapp.models.Bisita;
import com.ikaslea.komertzialakapp.utils.DBManager;

public class EditBisitaActivity extends AppCompatActivity {

    private TextView idText;
    private EditText
            helbideaText,
            helburuaText,
            obserbazioakText,
            dateText,
            hasieraOrduaText,
            bukaeraOrduaText,
            bazkideaEditText;
    private Button gordeButton, ezabatuButton, egindaButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_bisita);

        idText = findViewById(R.id.idText);
        helbideaText = findViewById(R.id.helbideaText);
        helburuaText = findViewById(R.id.helburaText);
        obserbazioakText = findViewById(R.id.obserbazioakText);
        dateText = findViewById(R.id.dateText);
        hasieraOrduaText = findViewById(R.id.hasieraOrduaText);
        bukaeraOrduaText = findViewById(R.id.bukaeraOrduaText);
        bazkideaEditText = findViewById(R.id.bazkideaEditText);

        bazkideaEditText.setFocusable(false);
        dateText.setFocusable(false);
        hasieraOrduaText.setFocusable(false);
        bukaeraOrduaText.setFocusable(false);

        Intent intent = getIntent();

        Bisita bisita = (Bisita) intent.getSerializableExtra("bisita");

        idText.setText(String.valueOf(bisita.getId()));
        helbideaText.setText(bisita.getHelbidea());
        helburuaText.setText(bisita.getBisitarenHelburua());
        obserbazioakText.setText(bisita.getObserbazioak());

        dateText.setText(String.valueOf(bisita.getHasieraData().toLocalDate()));

        hasieraOrduaText.setText(String.valueOf(bisita.getHasieraData().toLocalTime()));
        bukaeraOrduaText.setText(String.valueOf(bisita.getBukaeraData().toLocalTime()));

        gordeButton = findViewById(R.id.gordeButton);
        ezabatuButton = findViewById(R.id.ezabatuButton);
        egindaButton = findViewById(R.id.egindaButton);


        dateText.setOnClickListener( v -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, year, month, dayOfMonth) -> {
                bisita.setHasieraData(bisita.getHasieraData().withYear(year).withMonth(month+1).withDayOfMonth(dayOfMonth));
                bisita.setBukaeraData(bisita.getBukaeraData().withYear(year).withMonth(month+1).withDayOfMonth(dayOfMonth));

                dateText.setText(String.valueOf(bisita.getHasieraData().toLocalDate()));

            },
                    bisita.getHasieraData().getYear(),
                    bisita.getHasieraData().getMonthValue()-1,
                    bisita.getHasieraData().getDayOfMonth());
            datePickerDialog.show();
        });

        hasieraOrduaText.setOnClickListener( v -> {
            TimePickerDialog timePickerDialog = new TimePickerDialog(this, (view, hourOfDay, minute) -> {
                bisita.setHasieraData(bisita.getHasieraData().withHour(hourOfDay).withMinute(minute));
                hasieraOrduaText.setText(String.valueOf(bisita.getHasieraData().toLocalTime()));
            },
                    bisita.getHasieraData().getHour(),
                    bisita.getHasieraData().getMinute(),
                    true);
            timePickerDialog.show();
        });

        bukaeraOrduaText.setOnClickListener( v -> {
            TimePickerDialog timePickerDialog = new TimePickerDialog(this, (view, hourOfDay, minute) -> {
                bisita.setBukaeraData(bisita.getBukaeraData().withHour(hourOfDay).withMinute(minute));
                bukaeraOrduaText.setText(String.valueOf(bisita.getBukaeraData().toLocalTime()));
            },
                    bisita.getBukaeraData().getHour(),
                    bisita.getBukaeraData().getMinute(),
                    true);
            timePickerDialog.show();
        });

        bazkideaEditText.setOnClickListener( v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Aukeratu Bazkidea");

        });


        gordeButton.setOnClickListener( v -> {
            bisita.setHelbidea(helbideaText.getText().toString());
            bisita.setBisitarenHelburua(helburuaText.getText().toString());
            bisita.setObserbazioak(obserbazioakText.getText().toString());



            DBManager.getInstance().save(bisita);
            finish();
        });

        ezabatuButton.setOnClickListener( v -> {
            DBManager.getInstance().delete(bisita);
            finish();
        });

        egindaButton.setOnClickListener( v -> {
            bisita.setEginda(true);
            DBManager.getInstance().save(bisita);
            finish();
        });



    }
}