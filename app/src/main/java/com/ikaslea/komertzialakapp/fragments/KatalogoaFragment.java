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
import android.widget.Toast;

import com.ikaslea.komertzialakapp.R;
import com.ikaslea.komertzialakapp.adapters.ArtikuloaAdapter;
import com.ikaslea.komertzialakapp.models.Artikuloa;
import com.ikaslea.komertzialakapp.utils.DBManager;
import com.ikaslea.komertzialakapp.utils.XMLManager;

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


        // view-ko kontrolak lortu
        spinnerKategoria = view.findViewById(R.id.kategoriakSpinner);
        btnKargatu = view.findViewById(R.id.kargatuButton);
        recyclerView = view.findViewById(R.id.artikuloakList);

        // kategoriak lortu eta spinnerrean gehitu
        List<Artikuloa> artikuloaList = DBManager.getInstance().getAll(Artikuloa.class);

        List<String> kategoriak = artikuloaList.stream().map(Artikuloa::getKategoria).distinct().collect(Collectors.toList());
        kategoriak.add("");

        spinnerKategoria.setAdapter(new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, kategoriak));
        spinnerKategoria.setSelection(kategoriak.size() - 1);

        // Artikulonetzako adaptadorea sortu
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

        /**
         *  XML fitxategia kargatzeko logika guztia botoiari klik egiten diogunean
         */
/**
 *  XML fitxategia kargatzeko logika guztia botoiari klik egiten diogunean
 */
        btnKargatu.setOnClickListener(v -> {
            try {
                String xml = XMLManager.getInstance().XMLKargatuFitxategitik(requireContext());
                List<Artikuloa> artikuloakFromXML = XMLManager.getInstance().fromXML(xml);

                // XML artikuloak irakurri eta eguneratu
                if (artikuloakFromXML != null) {
                    for (Artikuloa artikuloXML : artikuloakFromXML) {
                        Artikuloa existingArtikuloa = DBManager.getInstance().getArtikuloaByIzena(artikuloXML.getIzena());

                        if (existingArtikuloa != null) {
                            existingArtikuloa.setStock(artikuloXML.getStock());
                            existingArtikuloa.setPrezioa(artikuloXML.getPrezioa());
                            existingArtikuloa.setKategoria(artikuloXML.getKategoria());

                            DBManager.getInstance().save(existingArtikuloa);
                        } else {
                            DBManager.getInstance().save(artikuloXML);
                        }
                    }
                }

                // Datuak eguneratu XML fitxategiko datuekin
                List<Artikuloa> artikuloaListXML = DBManager.getInstance().getAll(Artikuloa.class);
                adapter.setArtikuloaList(artikuloaListXML);
                Toast.makeText(requireContext(), "XML fitxategia ondo kargatu da!", Toast.LENGTH_SHORT).show();

            } catch (Exception e) {
                e.printStackTrace();
            }
        });


        return view;
    }
}