package com.example.yeipos.viewHolder;

import androidx.annotation.NonNull;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.yeipos.R;
import com.example.yeipos.interfaces.ItemClickListener;
import com.example.yeipos.model.OrderCardViewHome;

import java.util.ArrayList;

public class OrdenesCardAdapter extends RecyclerView.Adapter<OrdenesCardAdapter.ViewHolderOrden>{
    ArrayList<OrderCardViewHome> orderCardViewHomeItems;

    public OrdenesCardAdapter(ArrayList<OrderCardViewHome> orderCardViewHomes) {
        orderCardViewHomeItems = orderCardViewHomes;
    }

    @NonNull
    @Override
    public ViewHolderOrden onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.orden_card_item, parent,false);
        return new ViewHolderOrden(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderOrden holder, int position) {
        OrderCardViewHome currentItem = orderCardViewHomeItems.get(position);
        holder.textViewMesa.setText(currentItem.getMesa());
        holder.textViewFecha.setText(currentItem.getTime());
    }

    @Override
    public int getItemCount() {
        return orderCardViewHomeItems.size();
    }

    public static class ViewHolderOrden extends  RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView textViewMesa, textViewFecha;
        public Button terminar, editar;
        public ListView listV;
        public ItemClickListener mListener;

        public ViewHolderOrden(View itemView) {
            super(itemView);
            textViewFecha = itemView.findViewById(R.id.txt_hora_card);
            textViewMesa = itemView.findViewById(R.id.txt_mesa_card);
            listV = itemView.findViewById(R.id.list_orden_items);

            terminar = itemView.findViewById(R.id.terminar_boton);
            editar = itemView.findViewById(R.id.editar_boton);
            terminar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mListener.onDeleteClick(view, position);
                        }
                    }
                }
            });
            editar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mListener.onDeleteClick(view, position);
                        }
                    }
                }
            });
        }

        public void ItemClickListener(ItemClickListener mListener){
            this.mListener = mListener;
        }
        @Override
        public void onClick(View view) {
            mListener.onDeleteClick(view,getAdapterPosition());
            mListener.onEditClick(view,getAdapterPosition());
        }
    }

}

