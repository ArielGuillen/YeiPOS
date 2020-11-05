package com.example.yeipos.viewHolder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yeipos.R;
import com.example.yeipos.interfaces.OnClickListenerOrdenItem;
import com.example.yeipos.model.OrderCardViewHome;

import java.util.ArrayList;

public class OrdenesCardAdapterV2 extends RecyclerView.Adapter<OrdenesCardAdapterV2.ViewHolderOrden>{
    ArrayList<OrderCardViewHome> orderCardViewHomeItems;

    public OrdenesCardAdapterV2(ArrayList<OrderCardViewHome> orderCardViewHomes) {
        orderCardViewHomeItems = orderCardViewHomes;
    }

    @NonNull
    @Override
    public ViewHolderOrden onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.orden_historial_card, parent,false);
        return new ViewHolderOrden(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderOrden holder, int position) {
        OrderCardViewHome currentItem = orderCardViewHomeItems.get(position);
        holder.textViewMesa.setText(currentItem.getMesa());
        holder.textViewFecha.setText(currentItem.getTime());
        holder.textTotal.setText(currentItem.getTotal());
    }

    @Override
    public int getItemCount() {
        return orderCardViewHomeItems.size();
    }

    public static class ViewHolderOrden extends RecyclerView.ViewHolder{
        public TextView textViewMesa, textViewFecha, textTotal;

        public RecyclerView rowItemRecycler;
        public RecyclerView.LayoutManager manager;

        public ViewHolderOrden(View itemView) {
            super(itemView);
            textViewFecha = itemView.findViewById(R.id.txt_hora_card);
            textViewMesa = itemView.findViewById(R.id.txt_mesa_card);
            textTotal = itemView.findViewById(R.id.txt_total);

            manager = new LinearLayoutManager(itemView.getContext(), LinearLayoutManager.VERTICAL, false);
            rowItemRecycler = itemView.findViewById(R.id.recycler_orden_item);
            rowItemRecycler.setLayoutManager(manager);
        }
    }
}

