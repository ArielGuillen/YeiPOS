package com.example.yeipos.viewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.yeipos.R;

public class ComidasViewHolder extends RecyclerView.ViewHolder{
    public TextView txtNombre;
    public ElegantNumberButton elegantNumberButton;

    public ComidasViewHolder(@NonNull View itemView) {
        super(itemView);
        txtNombre = itemView.findViewById(R.id.txt_mod_platillo);
        elegantNumberButton = (ElegantNumberButton) itemView.findViewById(R.id.mod_elegant_button);
    }
}
