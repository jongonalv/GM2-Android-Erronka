package com.ikaslea.komertzialakapp.fragments;

import android.os.Build;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.TextView;

import com.ikaslea.komertzialakapp.R;
import com.ikaslea.komertzialakapp.adapters.BisitaAdapter;
import com.ikaslea.komertzialakapp.models.Bazkidea;
import com.ikaslea.komertzialakapp.models.Bisita;
import com.ikaslea.komertzialakapp.models.Komerziala;
import com.ikaslea.komertzialakapp.models.enums.BazkideMota;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CalendarFragment extends Fragment {

    private RecyclerView recyclerView;
    private BisitaAdapter bisitaAdapter;
    CalendarView calendarView;
    TextView partnerNameText;

    public CalendarFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calendar, container, false);

        recyclerView = view.findViewById(R.id.partnerListView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Datuak hemen lortuko dira datu basetik
        List<Bisita> bisitaList = createTestBisitaList();

        bisitaAdapter = new BisitaAdapter(getContext(), bisitaList, bisita -> {
            // HEMEN BISITA KUDEATZEKO AUKERA IZANGO DA
        });
        recyclerView.setAdapter(bisitaAdapter);

        calendarView = view.findViewById(R.id.calendarView);
        partnerNameText = view.findViewById(R.id.partnerNameText);

        Map<Long, String> dateToPartnerMap = new HashMap<>();
        Map<Long, LocalDate> dateWithVisitMap = new HashMap<>();

        for (Bisita bisita : bisitaList) {
            long timestamp = bisita.getHasieraData().toInstant(ZoneOffset.UTC).toEpochMilli();
            dateToPartnerMap.put(timestamp, bisita.getBazkidea().getIzena());
            dateWithVisitMap.put(timestamp, bisita.getHasieraData().toLocalDate());
        }

        calendarView.setOnDateChangeListener((v, year, month, dayOfMonth) -> {
            LocalDate selectedDate = LocalDate.of(year, month + 1, dayOfMonth);
            LocalDateTime selectedDateTime = selectedDate.atStartOfDay();
            long selectedDateTimestamp = selectedDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();

            String partnerName = dateToPartnerMap.get(selectedDateTimestamp);

            if (partnerName != null) {
                partnerNameText.setText("Bazkidea: " + partnerName);
            } else {
                partnerNameText.setText("Bazkidea ez da aurkitu");
            }
        });

        for (Map.Entry<Long, LocalDate> entry : dateWithVisitMap.entrySet()) {
            long timestamp = entry.getKey();
            LocalDate visitDate = entry.getValue();
            calendarView.setDate(visitDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli(), true, true);
        }

        return view;
    }

    // TEST BAT PROBATZEKO ADAPTER-A, DATUAK EZ DIRA HEMENDIK LORTUKO
    private List<Bisita> createTestBisitaList() {
        List<Bisita> bisitaList = new ArrayList<>();

        Bazkidea bazkidea1 = new Bazkidea();
        bazkidea1.setIzena("Partner 1");
        bazkidea1.setBazkideMota(BazkideMota.BERRIA);

        Bazkidea bazkidea2 = new Bazkidea();
        bazkidea2.setIzena("Partner 2");
        bazkidea2.setBazkideMota(BazkideMota.POTENZIALA);

        Bazkidea bazkidea3 = new Bazkidea();
        bazkidea3.setIzena("Partner 3");
        bazkidea3.setBazkideMota(BazkideMota.REKURRENTEA);

        Bazkidea bazkidea4 = new Bazkidea();
        bazkidea4.setIzena("Partner 4");
        bazkidea4.setBazkideMota(BazkideMota.POTENZIALA);

        Bazkidea bazkidea5 = new Bazkidea();
        bazkidea5.setIzena("Partner 5");
        bazkidea5.setBazkideMota(BazkideMota.BERRIA);

        Bazkidea bazkidea6 = new Bazkidea();
        bazkidea6.setIzena("Partner 6");
        bazkidea6.setBazkideMota(BazkideMota.REKURRENTEA);

        Bazkidea bazkidea7 = new Bazkidea();
        bazkidea7.setIzena("Partner 7");
        bazkidea7.setBazkideMota(BazkideMota.POTENZIALA);

        Bazkidea bazkidea8 = new Bazkidea();
        bazkidea8.setIzena("Partner 8");
        bazkidea8.setBazkideMota(BazkideMota.BERRIA);

        // Crear visitas con fechas diferentes
        Bisita bisita1 = new Bisita();
        bisita1.setHasieraData(LocalDateTime.of(2025, 1, 15, 10, 0));  // 15 Enero 2025
        bisita1.setBisitarenHelburua("Objetivo 1");
        bisita1.setBazkidea(bazkidea1);

        Bisita bisita2 = new Bisita();
        bisita2.setHasieraData(LocalDateTime.of(2025, 1, 18, 14, 30));  // 18 Enero 2025
        bisita2.setBisitarenHelburua("Objetivo 2");
        bisita2.setBazkidea(bazkidea2);

        Bisita bisita3 = new Bisita();
        bisita3.setHasieraData(LocalDateTime.of(2025, 1, 20, 9, 0));  // 20 Enero 2025
        bisita3.setBisitarenHelburua("Objetivo 3");
        bisita3.setBazkidea(bazkidea3);

        Bisita bisita4 = new Bisita();
        bisita4.setHasieraData(LocalDateTime.of(2025, 1, 22, 16, 0));  // 22 Enero 2025
        bisita4.setBisitarenHelburua("Objetivo 4");
        bisita4.setBazkidea(bazkidea4);

        Bisita bisita5 = new Bisita();
        bisita5.setHasieraData(LocalDateTime.of(2025, 1, 25, 11, 0));  // 25 Enero 2025
        bisita5.setBisitarenHelburua("Objetivo 5");
        bisita5.setBazkidea(bazkidea5);

        Bisita bisita6 = new Bisita();
        bisita6.setHasieraData(LocalDateTime.of(2025, 1, 28, 15, 0));  // 28 Enero 2025
        bisita6.setBisitarenHelburua("Objetivo 6");
        bisita6.setBazkidea(bazkidea6);

        Bisita bisita7 = new Bisita();
        bisita7.setHasieraData(LocalDateTime.of(2025, 1, 30, 12, 0));  // 30 Enero 2025
        bisita7.setBisitarenHelburua("Objetivo 7");
        bisita7.setBazkidea(bazkidea7);

        Bisita bisita8 = new Bisita();
        bisita8.setHasieraData(LocalDateTime.of(2025, 2, 2, 10, 0));  // 2 Febrero 2025
        bisita8.setBisitarenHelburua("Objetivo 8");
        bisita8.setBazkidea(bazkidea8);

        bisitaList.add(bisita1);
        bisitaList.add(bisita2);
        bisitaList.add(bisita3);
        bisitaList.add(bisita4);
        bisitaList.add(bisita5);
        bisitaList.add(bisita6);
        bisitaList.add(bisita7);
        bisitaList.add(bisita8);

        return bisitaList;
    }
}
