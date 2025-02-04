package com.ikaslea.komertzialakapp.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.ikaslea.komertzialakapp.R;
import com.ikaslea.komertzialakapp.adapters.ArtikuloaAdapter;
import com.ikaslea.komertzialakapp.models.Artikuloa;
import com.ikaslea.komertzialakapp.utils.DBManager;

import java.util.List;
import java.util.stream.Collectors;


public class KatalogoaFragment extends Fragment {

    private Spinner spinnerKategoria;

    private Button btnKargatu;

    private RecyclerView recyclerView;

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

        spinnerKategoria = view.findViewById(R.id.kategoriakSpinner);
        btnKargatu = view.findViewById(R.id.kargatuButton);
        recyclerView = view.findViewById(R.id.artikuloakList);

        List<Artikuloa> artikuloaList = DBManager.getInstance().getAll(Artikuloa.class);

        List<String> kategoriak = artikuloaList.stream().map(Artikuloa::getKategoria).distinct().collect(Collectors.toList());
        kategoriak.add("");

        spinnerKategoria.setAdapter(new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, kategoriak));
        spinnerKategoria.setSelection(kategoriak.size() - 1);


        ArtikuloaAdapter adapter = new ArtikuloaAdapter(artikuloaList, getContext(), null);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        spinnerKategoria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (kategoriak.get(i).isEmpty()) {
                    adapter.setArtikuloaList(artikuloaList);
                } else {
                    adapter.setArtikuloaList(artikuloaList.stream().filter(artikuloa -> artikuloa.getKategoria().equals(kategoriak.get(i))).collect(Collectors.toList()));
                }
                }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



        return view;
    }
}