package com.ikaslea.komertzialakapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ikaslea.komertzialakapp.R;
import com.ikaslea.komertzialakapp.models.Bisita;
import com.ikaslea.komertzialakapp.models.Bazkidea;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

public class BisitaAdapter extends RecyclerView.Adapter<BisitaAdapter.BisitaViewHolder> {

    private Context context;
    private List<Bisita> bisitaList;
    private OnEditButtonClickListener onEditButtonClickListener;

    public BisitaAdapter(Context context, List<Bisita> bisitaList, OnEditButtonClickListener listener) {
        this.context = context;
        this.bisitaList = bisitaList;
        this.onEditButtonClickListener = listener;
    }

    @NonNull
    @Override
    public BisitaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_bisita, parent, false);
        return new BisitaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BisitaViewHolder holder, int position) {
        Bisita bisita = bisitaList.get(position);
        Bazkidea bazkidea = bisita.getBazkidea();
        holder.partnerName.setText(bazkidea.getIzena());
        holder.partnerType.setText(bazkidea.getBazkideMota().name());
        holder.partnerObjective.setText(bisita.getBisitarenHelburua());


        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm", Locale.getDefault());
        String visitDateTime = bisita.getHasieraData().format(formatter);
        holder.visitDateTime.setText(visitDateTime);

        holder.editButton.setOnClickListener(v -> {
            onEditButtonClickListener.onEditButtonClick(bisita);
        });
    }

    @Override
    public int getItemCount() {
        return bisitaList.size();
    }


    /**
     * Bisiak eguneratzeko metodoa
     * @param bisitaList Bisiak
     */
    public void updateData(List<Bisita> bisitaList) {
        this.bisitaList = bisitaList;
        notifyDataSetChanged();
    }

    public static class BisitaViewHolder extends RecyclerView.ViewHolder {

        TextView partnerName;
        TextView partnerType;
        TextView partnerObjective;
        TextView visitDateTime;
        Button editButton;

        public BisitaViewHolder(@NonNull View itemView) {
            super(itemView);
            partnerName         = itemView.findViewById(R.id.partnerName);
            partnerType         = itemView.findViewById(R.id.partnerType);
            partnerObjective    = itemView.findViewById(R.id.partnerObjective);
            visitDateTime       = itemView.findViewById(R.id.visitDateTime);
            editButton          = itemView.findViewById(R.id.editButton);
        }
    }

    public interface OnEditButtonClickListener {
        void onEditButtonClick(Bisita bisita);
    }
}