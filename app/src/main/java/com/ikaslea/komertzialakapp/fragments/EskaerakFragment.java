package com.ikaslea.komertzialakapp.fragments;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.ikaslea.komertzialakapp.EditEskaeraActivity;
import com.ikaslea.komertzialakapp.R;
import com.ikaslea.komertzialakapp.adapters.EskaeraAdapter;
import com.ikaslea.komertzialakapp.models.Bazkidea;
import com.ikaslea.komertzialakapp.models.Eskaera;
import com.ikaslea.komertzialakapp.models.Komerziala;
import com.ikaslea.komertzialakapp.models.enums.BazkideMota;
import com.ikaslea.komertzialakapp.models.enums.Egoera;
import com.ikaslea.komertzialakapp.utils.DBManager;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class EskaerakFragment extends Fragment {

    private Spinner estadoSpinner,
        bazkdieaSpinner;

    private EditText idKonzeptuaEditText,
        dataEditText;

    private Button berriaButton;

    private RecyclerView eskaerakRecyclerView;

    private EskaeraAdapter adapter;

    private String erabiltzailea;

    public EskaerakFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_eskaerak, container, false);

        // view-ko elementuak lortu
        estadoSpinner = view.findViewById(R.id.estadoSpinner);
        bazkdieaSpinner = view.findViewById(R.id.bazkideaSpinner);
        idKonzeptuaEditText = view.findViewById(R.id.idKonzeptuaEditText);
        dataEditText = view.findViewById(R.id.dataEditText);
        berriaButton = view.findViewById(R.id.berriaButton);
        eskaerakRecyclerView = view.findViewById(R.id.listEskaerak);


        // Elementuak konfiguratu
        estadoSpinnerConf();
        bazkideaSpinnerConf();
        dataEditConf();
        eskaeraRecyclerViewConf();
        berriaButtonConf();

        dataEditText.setFocusable(false);

        // Konzeptua EditText-en testua aldatzean, filtroa aplikatu
        idKonzeptuaEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                filterEskaeraList(null,
                        idKonzeptuaEditText.getText().toString(),
                        null,
                        null);

            }
        });

        return view;
    }

    /**
     * MEtodo honek data aukeratzean data EditTextean filtroa aplikatzen du data hau baino beranduago egindako eskaerak erakusteko
     */
    private void dataEditConf() {
        dataEditText.setOnClickListener( v -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(requireContext(), (view, year, month, dayOfMonth) -> {
                dataEditText.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                LocalDateTime.of(year, month + 1, dayOfMonth, 0, 0);

                filterEskaeraList(null,
                        null,
                        LocalDateTime.of(year, month + 1, dayOfMonth, 0, 0),
                        null);


            }, 2021, 0, 1);
            datePickerDialog.show();
        });
    }

    /**
     * Metodo honek egoeramo mota guztiak lista batean lortzen ditu eta spinnerean jartzen ditu aukeratzeko,
     * hauetako batr aukeratutakoa filtratu egingo ditu estadu horretan dauden eskaera guztiak
     */
    private void estadoSpinnerConf() {
        List<String> estaduakList = new ArrayList<>();
        estaduakList.add("Guztiak"); // Opción predeterminada

        Arrays.stream(Egoera.values())
                .forEach(egoera -> estaduakList.add(egoera.name()));

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, estaduakList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        estadoSpinner.setAdapter(adapter);
        estadoSpinner.setSelection(0);

        estadoSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selected = estadoSpinner.getSelectedItem().toString();
                filterEskaeraList(selected.equals("Guztiak") ? null : selected, null, null, null);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }


    /**
     *  dataubestik bazkide guztiak lortzen ditue ta hoene arabera spinnerean bazkide hauek aukeratzeko
     *  auekra hemanten ditu eskaerak bazkide bidez filtratzeko
     */
    private void bazkideaSpinnerConf() {
        List<String> bazkideaList = new ArrayList<>();

        bazkideaList.add("");

        List<Bazkidea> bazkideakSpinner = DBManager.getInstance().getAll(Bazkidea.class);

        for (Bazkidea bazkidea : bazkideakSpinner) {
            bazkideaList.add(bazkidea.getIzena());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, bazkideaList);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        bazkdieaSpinner.setAdapter(adapter);

        bazkdieaSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                filterEskaeraList(null,
                        null,
                        null,
                        bazkdieaSpinner.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    /**
     * berria botoia konfiguratzen du klik egitean EditEskaeraAktivityra juateko sortutako eskera berriarekin
     */
    private void berriaButtonConf() {
        berriaButton.setOnClickListener( v -> {
            Eskaera eskaera = new Eskaera();
            Intent intent = new Intent(getContext(), EditEskaeraActivity.class);

            intent.putExtra("eskaera", eskaera);

            startActivity(intent);

        });
    }

    /**
     * eskaeraRecyclerView-a konfiguratzen du datuabseko eskerak erakusteko
     */
    private void eskaeraRecyclerViewConf() {

        if (getArguments() != null) {
            erabiltzailea = getArguments().getString("erabiltzailea");
        }

        Komerziala komerziala = DBManager.getInstance().getByIzena(erabiltzailea);
        List<Bazkidea> bazkideak = DBManager.getInstance().getBazkideByKomerzialaId(komerziala.getId());
        List<Integer> bazkideIds = bazkideak.stream().map(Bazkidea::getId).collect(Collectors.toList());
        List<Eskaera> eskaerak = DBManager.getInstance().getEskaerakByBazkideaIds(bazkideIds);


        adapter = new EskaeraAdapter(eskaerak, eskaera -> {
            Intent intent = new Intent(getContext(), EditEskaeraActivity.class);

            intent.putExtra("eskaera", eskaera);

            startActivity(intent);
        });

        eskaerakRecyclerView.setAdapter(adapter);
        eskaerakRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    /**
     * datuabseko eskaerak filtratzen ditu
     * @param estado eskeraren egoera
     * @param konzeptua eskaeraren konzeptua
     * @param data dataren konzeptua
     * @param bazkidea elbidearen bazkidearen izena
     */
    public void filterEskaeraList(String estado, String konzeptua, LocalDateTime data, String bazkidea) {

        if (getArguments() != null) {
            erabiltzailea = getArguments().getString("erabiltzailea");
        }

        Komerziala komerziala = DBManager.getInstance().getByIzena(erabiltzailea);
        List<Bazkidea> bazkideak = DBManager.getInstance().getBazkideByKomerzialaId(komerziala.getId());
        List<Integer> bazkideIds = bazkideak.stream().map(Bazkidea::getId).collect(Collectors.toList());
        List<Eskaera> eskaerak = DBManager.getInstance().getEskaerakByBazkideaIds(bazkideIds);

        if (estado != null) {
            eskaerak = eskaerak.stream()
                    .filter(
                            eskaera -> eskaera.getEgoera().name().contains(estado)
                    ).collect(Collectors.toList());
        }
        if (konzeptua != null) {
            eskaerak = eskaerak.stream()
                    .filter(
                            eskaera -> eskaera.getKontzeptua().contains(konzeptua)
                    ).collect(Collectors.toList());
        }

        if (data != null) {
            eskaerak= eskaerak.stream()
                    .filter(
                            eskaera -> eskaera.getEskaeraData().isAfter(data)
                    ).collect(Collectors.toList());
        }

        if (bazkidea != null) {
            eskaerak = eskaerak.stream()
                    .filter(
                            eskaera -> eskaera.getBazkidea().getIzena().contains(bazkidea)
                    ).collect(Collectors.toList());
        }

        // Actualizar el RecyclerView
       adapter.setEskaerak(eskaerak);
    }
}