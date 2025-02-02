package com.ikaslea.komertzialakapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ikaslea.komertzialakapp.R;
import com.ikaslea.komertzialakapp.models.Bazkidea;

import java.util.List;

public class BazkideaAdapter extends RecyclerView.Adapter<BazkideaAdapter.BazkideaViewHolder> {

    private Context context;
    private List<Bazkidea> bazkideaList;
    private OnItemClickListener onItemClickListener;

    public BazkideaAdapter(Context context, List<Bazkidea> bazkideaList, OnItemClickListener onItemClickListener) {
        this.context = context;
        this.bazkideaList = bazkideaList;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public BazkideaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_bazkidea, parent, false);
        return new BazkideaViewHolder(view);
    }

    public interface OnItemClickListener {
        void onItemClick(Bazkidea bazkidea);
    }

    @Override
    public void onBindViewHolder(@NonNull BazkideaAdapter.BazkideaViewHolder holder, int position) {
        Bazkidea bazkidea = bazkideaList.get(position);
        holder.id.setText(String.valueOf(bazkidea.getId()));
        holder.izena.setText(bazkidea.getIzena());
        holder.bazMota.setText(bazkidea.getBazkideMota().name());

        holder.itemView.setOnClickListener(v -> onItemClickListener.onItemClick(bazkidea));
    }

    @Override
    public int getItemCount() {
        return bazkideaList.size();
    }

    public static class BazkideaViewHolder extends RecyclerView.ViewHolder {

        TextView id;
        TextView izena;
        TextView bazMota;

        public BazkideaViewHolder(@NonNull View itemView) {
            super(itemView);

            id = itemView.findViewById(R.id.id);
            izena = itemView.findViewById(R.id.izena);
            bazMota = itemView.findViewById(R.id.bazMota);
        }
    }
}
