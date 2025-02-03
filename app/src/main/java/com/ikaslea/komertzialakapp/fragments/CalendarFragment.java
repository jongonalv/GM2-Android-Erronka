package com.ikaslea.komertzialakapp.fragments;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;

import com.ikaslea.komertzialakapp.EditBisitaActivity;
import com.ikaslea.komertzialakapp.R;
import com.ikaslea.komertzialakapp.adapters.BisitaAdapter;
import com.ikaslea.komertzialakapp.models.Bazkidea;
import com.ikaslea.komertzialakapp.models.Bisita;
import com.ikaslea.komertzialakapp.models.enums.BazkideMota;
import com.ikaslea.komertzialakapp.utils.DBManager;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CalendarFragment extends Fragment {

    private RecyclerView recyclerView;
    private BisitaAdapter bisitaAdapter;
    CalendarView calendarView;
    Button berriaButton;
    View view;
    DBManager dbManager = DBManager.getInstance();

    public CalendarFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_calendar, container, false);

        sortuBazkideLista();
        sortuCalendarView();

        berriaButton = view.findViewById(R.id.berriaButton);


        berriaButton.setOnClickListener(v -> {
            Bisita bisita = new Bisita();
            bisita.setHasieraData(LocalDateTime.now().withSecond(0).withNano(0));
            bisita.setBukaeraData(LocalDateTime.now().withSecond(0).withNano(0).plusHours(1));

            Intent intent = new Intent(getContext(), EditBisitaActivity.class);

            intent.putExtra("bisita", bisita);
            startActivity(intent);
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateBistaList();
    }

    private void sortuBazkideLista() {

        recyclerView = view.findViewById(R.id.partnerListView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        // Datuak hemen lortuko dira datu basetik
        List<Bisita> bisitaList = dbManager.getAll(Bisita.class);

        // Adapter-a sortu eta datuak sartu, eta baita ere onclick listener-a botoiari aplikatzeko
        bisitaAdapter = new BisitaAdapter(getContext(), bisitaList, bisita -> {
            Intent intent = new Intent(getContext(), EditBisitaActivity.class);
            intent.putExtra("bisita", bisita);
            startActivity(intent);
        });

        recyclerView.setAdapter(bisitaAdapter);

    }

    private void sortuCalendarView() {

        calendarView = view.findViewById(R.id.calendarView);

        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) ->
                {
                    updateBistaList(year, month, dayOfMonth);
                });
    }

    private void updateBistaList(int year, int month, int day) {
        List<Bisita> bisitaList = dbManager.getAll(Bisita.class);

        bisitaList = bisitaList.stream()
                .filter(bisita -> bisita.getHasieraData().getYear() == year &&
                        bisita.getHasieraData().getMonthValue() == month + 1 &&
                        bisita.getHasieraData().getDayOfMonth() == day)
                .collect(Collectors.toList());

        bisitaAdapter.updateData(bisitaList);
    }

    private void updateBistaList() {
        List<Bisita> bisitaList = dbManager.getAll(Bisita.class);

        bisitaAdapter.updateData(bisitaList);
    }


}
