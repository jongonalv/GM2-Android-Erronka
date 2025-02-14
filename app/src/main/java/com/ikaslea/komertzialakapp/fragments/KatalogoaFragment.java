package com.ikaslea.komertzialakapp.fragments;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.ikaslea.komertzialakapp.FileSaver;
import com.ikaslea.komertzialakapp.R;
import com.ikaslea.komertzialakapp.adapters.ArtikuloaAdapter;
import com.ikaslea.komertzialakapp.models.Artikuloa;
import com.ikaslea.komertzialakapp.utils.DBManager;
import com.ikaslea.komertzialakapp.utils.FileToString;
import com.ikaslea.komertzialakapp.utils.XMLManager;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Artikuluen zerrenda kargatzen du eta artikuluak filtratzeko aukera.
 */

public class KatalogoaFragment extends Fragment {

    private Spinner spinnerKategoria;

    private Button btnKargatu;

    private RecyclerView recyclerView;
    private boolean activateFields = false;
    private Map<Artikuloa, Integer> selectedProducts;



    public KatalogoaFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_katalogoa, container, false);


        // view-ko kontrolak lortu
        spinnerKategoria = view.findViewById(R.id.kategoriakSpinner);
        btnKargatu = view.findViewById(R.id.kargatuButton);
        recyclerView = view.findViewById(R.id.artikuloakList);

        // kategoriak lortu eta spinnerrean gehitu
        List<Artikuloa> artikuloaList = DBManager.getInstance().getAll(Artikuloa.class);

        List<String> kategoriak = artikuloaList.stream().map(Artikuloa::getKategoria).distinct().collect(Collectors.toList());
            kategoriak.add(0, "Guztiak");


        spinnerKategoria.setAdapter(new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, kategoriak));
        spinnerKategoria.setSelection(0);

        Bundle bundle = getArguments();
        if (bundle != null) {
            activateFields = bundle.getBoolean("activateFields", false);
        }


        // Artikulonetzako adaptadorea sortu
        ArtikuloaAdapter artikuloaAdapter = new ArtikuloaAdapter(artikuloaList, getContext(), activateFields ? new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
            }
        } : null);

        Button confirmButton = view.findViewById(R.id.confirmButton);
        confirmButton.setOnClickListener(v -> {
            selectedProducts = artikuloaAdapter.getSelectedProducts();
            returnSelectedProducts();
        });

        recyclerView.setAdapter(artikuloaAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        spinnerKategoria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (kategoriak.get(i).equals("Guztiak")) {
                    artikuloaAdapter.setArtikuloaList(artikuloaList);
                } else {
                    artikuloaAdapter.setArtikuloaList(
                            artikuloaList.stream()
                                    .filter(artikuloa -> artikuloa.getKategoria().equals(kategoriak.get(i)))
                                    .collect(Collectors.toList())
                    );
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });




        //XML fitxategia kargatzeko logika guztia botoiari klik egiten diogunean
        btnKargatu.setOnClickListener(v -> {
            throw new UnsupportedOperationException("Fuck");

        });

        return view;
    }

    private void returnSelectedProducts() {
        if (selectedProducts != null && !selectedProducts.isEmpty()) {
            Intent resultIntent = new Intent();
            resultIntent.putExtra("selectedProducts", new HashMap<>(selectedProducts));
            requireActivity().setResult(RESULT_OK, resultIntent);
            requireActivity().finish();
        } else {
            Toast.makeText(getContext(), "Ez dituzu aukeratu fragmentuak", Toast.LENGTH_SHORT).show();
        }
    }
}