package com.example.yeipos.viewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yeipos.R;

public class PlatilloViewHolder extends RecyclerView.ViewHolder {
    public TextView txtProductName, txtProductCantidad, txtProductPrecio;

    public PlatilloViewHolder(@NonNull View itemView) {
        super(itemView);
        txtProductName = (TextView) itemView.findViewById(R.id.nombre_row_item);
        txtProductCantidad = (TextView) itemView.findViewById(R.id.cantidad_row_item);
        txtProductPrecio = (TextView) itemView.findViewById(R.id.precio_row_item);
    }
}
