package com.ikaslea.komertzialakapp.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.ikaslea.komertzialakapp.R;

/**
 *  Google Maps integratzen du eta onMapReady() metodoa erabiliz, sukursalaren kokapena mapa batean markatzen du.
 */
public class HomeFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;

    private LinearLayout telefonoLayout;

    private LinearLayout emailLayout;

    private TextView telefonoTextView;

    private TextView emailTextView;

    public HomeFragment() {
        // Constructor vacío requerido para fragmentos
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Infla la vista del fragmento
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        telefonoLayout = rootView.findViewById(R.id.telefonoLayout);
        emailLayout = rootView.findViewById(R.id.emailLayout);

        telefonoTextView = rootView.findViewById(R.id.telefonoTextView);
        emailTextView = rootView.findViewById(R.id.emailTextView);


        // Obtiene el SupportMapFragment y lo prepara para la visualización
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        telefonoLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                String telefono = telefonoTextView.getText().toString().trim();

                intent.setData(android.net.Uri.parse("tel:" + telefono));

                if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
                    startActivity(intent);
                } else {
                    Toast.makeText(getActivity(), "No se puede realizar la llamada", Toast.LENGTH_SHORT).show();
                }
            }
        });

        emailLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                String email = emailTextView.getText().toString().trim();

                intent.setType("message/rfc822");
                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{email});

                if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
                    startActivity(intent);
                } else {
                    Toast.makeText(getActivity(), "No se puede enviar el correo", Toast.LENGTH_SHORT).show();
                }
            }
        });



        return rootView;
    }


    @Override
    /**
     *  Mapa konfiguratu
     */
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
