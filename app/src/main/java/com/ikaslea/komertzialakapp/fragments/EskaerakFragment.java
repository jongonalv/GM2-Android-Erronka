package com.ikaslea.komertzialakapp.fragments;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.ikaslea.komertzialakapp.R;
import com.ikaslea.komertzialakapp.models.Bazkidea;
import com.ikaslea.komertzialakapp.models.enums.BazkideMota;
import com.ikaslea.komertzialakapp.models.enums.Egoera;
import com.ikaslea.komertzialakapp.utils.DBManager;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class EskaerakFragment extends Fragment {

    private Spinner estadoSpinner,
        bazkdieaSpinner;

    private EditText idKonzeptuaEditText,
        dataEditText;

    private Button berriaButton;

    private RecyclerView eskaerakRecyclerView;


    public EskaerakFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_eskaerak, container, false);

        estadoSpinner = view.findViewById(R.id.estadoSpinner);
        bazkdieaSpinner = view.findViewById(R.id.bazkideaSpinner);
        idKonzeptuaEditText = view.findViewById(R.id.idKonzeptuaEditText);
        dataEditText = view.findViewById(R.id.dataEditText);
        berriaButton = view.findViewById(R.id.berriaButton);
        eskaerakRecyclerView = view.findViewById(R.id.listEskaerak);

        dataEditText.setFocusable(false);



        return view;
    }

    private void dataEditConf() {
        dataEditText.setOnClickListener( v -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(requireContext(), (view, year, month, dayOfMonth) -> {
                dataEditText.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                LocalDate.of(year, month + 1, dayOfMonth);
                // Filtrar la lista con el localdate
            }, 2021, 0, 1);
        });
    }

    private void estadoSpinnerConf() {
        List<String> estaduakList = new ArrayList<>();

        estaduakList.add("");

        Arrays.stream(Egoera.values())
                .forEach(egoera -> estaduakList.add(egoera.name()));

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, estaduakList);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        estadoSpinner.setAdapter(adapter);

        estadoSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Filtrar la lista con el estado seleccionado
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void bazkideaSpinnerConf() {
        List<String> bazkideaList = new ArrayList<>();

        bazkideaList.add("");

        List<Bazkidea> bazkideak = DBManager.getInstance().getAll(Bazkidea.class);

        for (Bazkidea bazkidea : bazkideak) {
            bazkideaList.add(bazkidea.getIzena());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, bazkideaList);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        bazkdieaSpinner.setAdapter(adapter);

        bazkdieaSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                // Filtrar la lista con el Bazkidea seleccionado
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void berriaButtonConf() {
        berriaButton.setOnClickListener( v -> {
            // Crear una nueva Eskaera con los datos introducidos

        });
    }

    private void eskaeraRecyclerViewConf() {
        // Configurar el RecyclerView
    }
}