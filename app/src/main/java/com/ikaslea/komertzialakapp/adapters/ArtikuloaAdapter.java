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

import java.util.List;

public class ArtikuloaAdapter extends RecyclerView.Adapter<ArtikuloaAdapter.ArtikuloaViewHolder> {

    private List<Artikuloa> artikuloaList;
    private Context context;
    private View.OnFocusChangeListener onFocusChangeListener;

    public ArtikuloaAdapter(List<Artikuloa> artikuloaList, Context context, View.OnFocusChangeListener onFocusChangeListener) {
        this.artikuloaList = artikuloaList;
        this.context = context;
        this.onFocusChangeListener = onFocusChangeListener;
    }

    @NonNull
    @Override
    public ArtikuloaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_artikuloa, parent, false);
        return new ArtikuloaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ArtikuloaViewHolder holder, int position) {
        // Si no hay onFocusChangeListener, ocultar los campos de cantidad y stock
        if (onFocusChangeListener == null) {
            holder.kantitatea.setVisibility(View.GONE);
            holder.stock.setVisibility(View.GONE);
        }

        Artikuloa artikuloa = artikuloaList.get(position);

        // Asignar los datos a las vistas
        holder.artikuloa.setText(artikuloa.getIzena());
        holder.kategoriaText.setText(artikuloa.getKategoria());
        holder.prezioa.setText(artikuloa.getPrezioa() + "€");
        holder.stock.setText(artikuloa.getStock() + " uds");
        holder.kantitatea.setOnFocusChangeListener(onFocusChangeListener);

        // Cargar la imagen con Glide
        if (artikuloa.getImageUrl() != null && !artikuloa.getImageUrl().isEmpty()) {
            Glide.with(context)
                    .load(artikuloa.getImageUrl())
                    .placeholder(R.drawable.placeholder_image)
                    .error(R.drawable.error_image)
                    .into(holder.imageView);
        } else {
            holder.imageView.setImageResource(R.drawable.default_image);
        }
    }

    @Override
    public int getItemCount() {
        return artikuloaList.size();
    }

    public void setArtikuloaList(List<Artikuloa> collect) {
        this.artikuloaList = collect;
        notifyDataSetChanged();
    }

    public static class ArtikuloaViewHolder extends RecyclerView.ViewHolder {

        TextView artikuloa,
                kategoriaText,
                prezioa,
                stock;

        EditText kantitatea;
        ImageView imageView; // Nuevo campo para la imagen

        public ArtikuloaViewHolder(@NonNull View itemView) {
            super(itemView);

            artikuloa = itemView.findViewById(R.id.artikuloaText);
            kategoriaText = itemView.findViewById(R.id.kategoriaText);
            prezioa = itemView.findViewById(R.id.prezioaText);
            stock = itemView.findViewById(R.id.stockText);
            kantitatea = itemView.findViewById(R.id.cantidadText);
            imageView = itemView.findViewById(R.id.imageView); // Asignar el ImageView
        }
    }
}