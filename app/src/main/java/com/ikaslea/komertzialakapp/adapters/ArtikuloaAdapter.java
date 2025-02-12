package com.ikaslea.komertzialakapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ikaslea.komertzialakapp.R;
import com.ikaslea.komertzialakapp.models.Artikuloa;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ArtikuloaAdapter extends RecyclerView.Adapter<ArtikuloaAdapter.ArtikuloaViewHolder> {

    // Artikuluen zerrenda gordetzeko
    private List<Artikuloa> artikuloaList;
    private Context context;
    private View.OnFocusChangeListener onFocusChangeListener;

    // Hautatutako produktuen mapa gordetzeko
    private Map<Artikuloa, Integer> selectedProducts;

    // Eraikitzailea
    public ArtikuloaAdapter(List<Artikuloa> artikuloaList, Context context, View.OnFocusChangeListener onFocusChangeListener) {
        this.artikuloaList = artikuloaList;
        this.context = context;
        this.onFocusChangeListener = onFocusChangeListener;
        this.selectedProducts = new HashMap<>();
    }

    @NonNull
    @Override
    public ArtikuloaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Item-aren diseinua kargatu eta ViewHolder bat sortu
        View view = LayoutInflater.from(context).inflate(R.layout.item_artikuloa, parent, false);
        return new ArtikuloaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ArtikuloaViewHolder holder, int position) {
        // Uneko artikulua lortu
        Artikuloa artikuloa = artikuloaList.get(position);

        // Artikuluaren informazioa ezarri testuetan
        holder.artikuloa.setText(artikuloa.getIzena());
        holder.kategoriaText.setText(artikuloa.getKategoria());
        holder.prezioa.setText(artikuloa.getPrezioa() + "€");
        holder.stock.setText(artikuloa.getStock() + " uds");

        // Kantitate editatzeko aukera egonez gero, gorde hautatutako produktuak
        if (onFocusChangeListener != null) {
            holder.kantitatea.setVisibility(View.VISIBLE);
            holder.stock.setVisibility(View.VISIBLE);
            holder.kantitatea.setOnFocusChangeListener((v, hasFocus) -> {
                if (!hasFocus) {
                    int cantidad = Integer.parseInt(holder.kantitatea.getText().toString());
                    selectedProducts.put(artikuloa, cantidad);
                }
            });
        } else {
            holder.kantitatea.setVisibility(View.GONE);
            holder.stock.setVisibility(View.GONE);
        }

        // Irudia kargatu Glide erabiliz
        if (artikuloa.getImageUrl() != null && !artikuloa.getImageUrl().isEmpty()) {
            Glide.with(context)
                    .load(artikuloa.getImageUrl()) // Irudiaren URL-a
                    .placeholder(R.drawable.placeholder_image) // Kargatzen ari den bitartean erakutsiko den irudia
                    .error(R.drawable.error_image) // Errorea izanez gero erakutsiko den irudia
                    .into(holder.imageView);
        } else {
            holder.imageView.setImageResource(R.drawable.default_image); // Irudirik ez badago, lehenetsitakoa erakutsi
        }
    }

    @Override
    public int getItemCount() {
        return artikuloaList.size(); // Zerrendako elementu kopurua bueltatu
    }

    // Artikuluen zerrenda eguneratzeko metodoa
    public void setArtikuloaList(List<Artikuloa> collect) {
        this.artikuloaList = collect;
        notifyDataSetChanged(); // RecyclerView eguneratu
    }

    // Hautatutako produktuak lortzeko metodoa
    public Map<Artikuloa, Integer> getSelectedProducts() {
        return selectedProducts;
    }

    // ViewHolder klasea (item bakoitzeko elementuak gordetzeko)
    public static class ArtikuloaViewHolder extends RecyclerView.ViewHolder {

        // UI elementuak definitu
        TextView artikuloa, kategoriaText, prezioa, stock;
        EditText kantitatea;
        ImageView imageView;

        public ArtikuloaViewHolder(@NonNull View itemView) {
            super(itemView);

            // XML fitxategiko elementuak lotu
            artikuloa = itemView.findViewById(R.id.artikuloaText);
            kategoriaText = itemView.findViewById(R.id.kategoriaText);
            prezioa = itemView.findViewById(R.id.prezioaText);
            stock = itemView.findViewById(R.id.stockText);
            kantitatea = itemView.findViewById(R.id.cantidadText);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }
}
