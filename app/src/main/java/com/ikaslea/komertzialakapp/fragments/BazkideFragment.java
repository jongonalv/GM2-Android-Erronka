package com.ikaslea.komertzialakapp.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ikaslea.komertzialakapp.EditBazkideaActivity;
import com.ikaslea.komertzialakapp.R;
import com.ikaslea.komertzialakapp.adapters.BazkideaEditAdapter;
import com.ikaslea.komertzialakapp.models.Bazkidea;
import com.ikaslea.komertzialakapp.models.enums.BazkideMota;
import com.ikaslea.komertzialakapp.utils.DBManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class BazkideFragment extends Fragment {

    private Spinner bazkideMotaSpinner;

    private EditText telefonoaInput,
            izenaInput,
            emailInput;

    private Button berriaButton;

    private RecyclerView bazkideakRecyclerView;

    private BazkideaEditAdapter adapter;

    private String lastMota = "";

    public BazkideFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bazkidea, container, false);

        bazkideMotaSpinner = view.findViewById(R.id.staduaSpinner);

        telefonoaInput = view.findViewById(R.id.telefonoInput);
        izenaInput = view.findViewById(R.id.izenaInput);
        emailInput = view.findViewById(R.id.emialInput);

        berriaButton = view.findViewById(R.id.berriaButton);

        bazkideakRecyclerView = view.findViewById(R.id.listaBazkidea);

        bazkideakRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        estaduaSpinnerConf();
        telefonoaInputConf();
        izenaInputConf();
        emailInputConf();

        List<Bazkidea> bazkideaList = DBManager.getInstance().getAll(Bazkidea.class);

        adapter = new BazkideaEditAdapter(getContext(), bazkideaList, bazkidea -> {
            Intent intent = new Intent(getContext(), EditBazkideaActivity.class);
            intent.putExtra("bazkidea", bazkidea);

            startActivity(intent);
        });


        bazkideakRecyclerView.setAdapter(adapter);

        berriaButton.setOnClickListener(v -> {
            Bazkidea bazkidea = new Bazkidea();
            Intent intent = new Intent(getContext(), EditBazkideaActivity.class);
            intent.putExtra("bazkidea", bazkidea);

            startActivity(intent);
        });

        return view;
    }

    private void estaduaSpinnerConf() {

        List<String> bazkideMotaList = new ArrayList<>();

        bazkideMotaList.add("");

        Arrays.stream(BazkideMota.values())
                .forEach(bazkideMota -> bazkideMotaList.add(bazkideMota.name()));

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, bazkideMotaList);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        bazkideMotaSpinner.setAdapter(adapter);

        bazkideMotaSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String mota = (String) parent.getItemAtPosition(position);
                filterBazkideaList(telefonoaInput.getText().toString(),
                        izenaInput.getText().toString(), emailInput.getText().toString(), mota);
                lastMota = mota;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }

    private void telefonoaInputConf() {
        telefonoaInput.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                String telefonoa = telefonoaInput.getText().toString();
                filterBazkideaList( telefonoa,
                        izenaInput.getText().toString(), emailInput.getText().toString(), lastMota);
            }
        });
    }

    private void izenaInputConf() {
        izenaInput.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                String izena = izenaInput.getText().toString();
                filterBazkideaList( telefonoaInput.getText().toString(),
                        izena, emailInput.getText().toString(), lastMota);
            }
        });
    }

    private void emailInputConf() {
        emailInput.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                String email = emailInput.getText().toString();
                filterBazkideaList( telefonoaInput.getText().toString(),
                        izenaInput.getText().toString(), email, lastMota);
            }
        });
    }

    private void filterBazkideaList(String telefonoa, String izena, String email, String mota) {
        List<Bazkidea> bazkideaList = DBManager.getInstance().getAll(Bazkidea.class);

        if (telefonoa != null && !telefonoa.isEmpty()) {
            bazkideaList = bazkideaList.stream()
                    .filter(
                            bazkidea -> bazkidea.getTelefonoa().toLowerCase()
                                    .contains(telefonoa.toLowerCase())
                    ).collect(Collectors.toList());
        }

        if (izena != null && !izena.isEmpty()) {
            bazkideaList = bazkideaList.stream()
                    .filter(
                            bazkidea -> bazkidea.getIzena().toLowerCase()
                                    .contains(izena.toLowerCase())
                    ).collect(Collectors.toList());
        }

        if (email != null && !email.isEmpty()) {
            bazkideaList = bazkideaList.stream()
                    .filter(
                            bazkidea -> bazkidea.getEmail().toLowerCase()
                                    .contains(email.toLowerCase())
                    ).collect(Collectors.toList());
        }

        if (mota != null) {
            bazkideaList = bazkideaList.stream()
                    .filter(
                            bazkidea -> bazkidea.getBazkideMota().name().contains(mota)
                    ).collect(Collectors.toList());
        }

        adapter.updateData(bazkideaList);
    }
}