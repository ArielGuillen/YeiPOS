package com.example.yeipos.viewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yeipos.R;
import com.example.yeipos.interfaces.ItemClickListener;

public class ProductViewHolder extends RecyclerView.ViewHolder{

    public TextView txtProductName, txtProductCantidad;

    public ProductViewHolder(@NonNull View itemView) {
        super(itemView);
        txtProductName = (TextView) itemView.findViewById(R.id.txt_nombrePlatillo);
        txtProductCantidad = (TextView) itemView.findViewById(R.id.txt_cantidadPlatillo);
    }
}