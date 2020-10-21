package com.example.yeipos.viewHolder;

import androidx.annotation.NonNull;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yeipos.R;
import com.example.yeipos.interfaces.ItemClickListener;

public class OrdenesListHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView txtProductName, txtProductDescription, txtProductPrice;
    public ImageView imageView;
    public ItemClickListener listener;

    public OrdenesListHolder(@NonNull View itemView) {
        super(itemView);
//        imageView = (ImageView) itemView.findViewById(R.id.product_image);
//        txtProductName = (TextView) itemView.findViewById(R.id.product_name);
//        txtProductDescription = (TextView) itemView.findViewById(R.id.product_description);
//        txtProductPrice = (TextView) itemView.findViewById(R.id.product_price);
    }

    public void ItemClickListener(ItemClickListener listener){
        this.listener = listener;
    }

    @Override
    public void onClick(View view) {

    }

}

