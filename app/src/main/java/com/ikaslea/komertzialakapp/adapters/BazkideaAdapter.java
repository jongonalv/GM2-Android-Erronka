package com.ikaslea.komertzialakapp.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ikaslea.komertzialakapp.models.Bazkidea;

import java.util.List;

public class BazkideaAdapter extends RecyclerView.Adapter<BazkideaAdapter.BazkideaViewHolder> {

    private Context context;
    private List<Bazkidea> bazkideaList;

    public BazkideaAdapter(Context context, List<Bazkidea> bazkideaList) {
        this.context = context;
        this.bazkideaList = bazkideaList;
    }

    @NonNull
    @Override
    public BazkideaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull BazkideaAdapter.BazkideaViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class BazkideaViewHolder extends RecyclerView.ViewHolder {
        public BazkideaViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
