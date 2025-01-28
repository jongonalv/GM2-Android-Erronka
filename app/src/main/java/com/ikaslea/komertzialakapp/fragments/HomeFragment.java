package com.ikaslea.komertzialakapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.ikaslea.komertzialakapp.R;

public class HomeFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;

    public HomeFragment() {
        // Constructor vacío requerido para fragmentos
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Infla la vista del fragmento
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        // Obtiene el SupportMapFragment y lo prepara para la visualización
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        return rootView;
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        // Mapa hasieratu
        mMap = googleMap;

        // Zoom eta kontrolak erabiltzeko
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setRotateGesturesEnabled(true);

        // Sukursalaren posizioa ezarri
        LatLng location = new LatLng(38.184635, -3.683934);

        // Markadorea
        mMap.addMarker(new MarkerOptions().position(location).title("Kevin Todo Tuerkas"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 12));
    }
}
