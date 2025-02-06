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
import com.ikaslea.komertzialakapp.models.Bazkidea;
import com.ikaslea.komertzialakapp.models.Bisita;

import java.util.List;

/**
 * Apdaptadore hau bazkideak erakusteko da eta botoiarai hemandakoan pasatutako OnEditButtonClickListener interfazea implementatzen du
 */
public class BazkideaEditAdapter extends RecyclerView.Adapter<BazkideaEditAdapter.BazkideaEditViewHolder> {

    private Context context;
    private List<Bazkidea> bazkideaList;
    private OnEditButtonClickListener onEditButtonClickListener;


    public BazkideaEditAdapter(Context context, List<Bazkidea> bazkideaList, OnEditButtonClickListener listener) {
        this.context = context;
        this.bazkideaList = bazkideaList;
        this.onEditButtonClickListener = listener;
    }


    @NonNull
    @Override
    public BazkideaEditViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_bazkidea_edit, parent, false);
        return new BazkideaEditViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BazkideaEditViewHolder holder, int position) {
        Bazkidea bazkidea = bazkideaList.get(position);
        holder.izenaText.setText(bazkidea.getIzena());
        holder.motaText.setText(bazkidea.getBazkideMota().name());
        holder.emailText.setText(bazkidea.getEmail());
        holder.telefonoaText.setText(bazkidea.getTelefonoa());

        holder.editatuButton.setOnClickListener(v -> {
            onEditButtonClickListener.onEditButtonClick(bazkidea);
        });
    }

    @Override
    public int getItemCount() {
        return bazkideaList.size();
    }

    /**
     * datuak eguneratzeko metodoa
     * @param bazkideaList bazkideen lista
     */
    public void updateData(List<Bazkidea> bazkideaList) {
        this.bazkideaList = bazkideaList;
        notifyDataSetChanged();
    }

    public static class BazkideaEditViewHolder extends RecyclerView.ViewHolder {

        TextView izenaText,
        motaText,
        emailText,
        telefonoaText;

        Button editatuButton;

        public BazkideaEditViewHolder(@NonNull View itemView) {
            super(itemView);
            izenaText = itemView.findViewById(R.id.izenaText);
            motaText = itemView.findViewById(R.id.motaText);
            emailText = itemView.findViewById(R.id.emailText);
            telefonoaText = itemView.findViewById(R.id.telefonoaText);
            editatuButton = itemView.findViewById(R.id.editatuButton);
        }
    }

    public interface OnEditButtonClickListener {
        void onEditButtonClick(Bazkidea bazkidea);
    }
}
