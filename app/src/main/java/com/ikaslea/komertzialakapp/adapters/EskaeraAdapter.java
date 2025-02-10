package com.ikaslea.komertzialakapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ikaslea.komertzialakapp.R;
import com.ikaslea.komertzialakapp.models.Bazkidea;
import com.ikaslea.komertzialakapp.models.Eskaera;

import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Adapter hau eskaerak editatzeko lista batean erakusteko erabiltzen da, non editatu botoiari klikatutakoan
 * OnEditButtonClickListener interfazea deituko du eta eskaera bat pasatuko dio.
 */
public class EskaeraAdapter extends RecyclerView.Adapter<EskaeraAdapter.EskaeraViewHolder> {

    private List<Eskaera> eskaerak;
    private EskaeraViewHolder.OnEditButtonClickListener onEditButtonClickListener;

    public void setEskaerak(List<Eskaera> eskaerak) {
        this.eskaerak = eskaerak;
        notifyDataSetChanged();
    }

    public EskaeraAdapter(List<Eskaera> eskaerak, EskaeraViewHolder.OnEditButtonClickListener onEditButtonClickListener) {
        this.eskaerak = eskaerak;
        this.onEditButtonClickListener = onEditButtonClickListener;
    }

    @NonNull
    @Override
    public EskaeraViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_eskaera, parent, false);
        return new EskaeraViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EskaeraViewHolder holder, int position) {
        Eskaera eskaera = eskaerak.get(position);
        holder.idKonzeptuaText.setText(eskaera.getKontzeptua());

        // Data formateatu ordua ez azaltzeko pantailan
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String formattedDate = eskaera.getEskaeraData().format(dateFormatter);
        holder.dataText.setText(formattedDate);

        holder.egoeraText.setText(eskaera.getEgoera().name());
        holder.bazkideaText.setText(eskaera.getBazkidea().getIzena());

        holder.editatuButton.setOnClickListener(v -> {
            onEditButtonClickListener.onEditButtonClick(eskaera);
        });
    }


    @Override
    public int getItemCount() {
        return eskaerak.size();
    }


    public static class EskaeraViewHolder extends RecyclerView.ViewHolder {

        TextView idKonzeptuaText, dataText, egoeraText, bazkideaText;

        Button editatuButton;

        public EskaeraViewHolder(@NonNull View itemView) {
            super(itemView);

            idKonzeptuaText = itemView.findViewById(R.id.konzeptuaText);
            dataText = itemView.findViewById(R.id.eskeraDataText);
            egoeraText = itemView.findViewById(R.id.estadoText);
            bazkideaText = itemView.findViewById(R.id.bazkideaText);
            editatuButton = itemView.findViewById(R.id.editatuButton);
        }

        public interface OnEditButtonClickListener {
            void onEditButtonClick(Eskaera eskaera);
        }
    }
}
