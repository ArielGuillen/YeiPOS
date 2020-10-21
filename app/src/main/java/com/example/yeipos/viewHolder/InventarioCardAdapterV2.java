package com.example.yeipos.viewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yeipos.R;
import com.example.yeipos.interfaces.ItemClickListener;
import com.example.yeipos.model.CardItem;

import java.util.ArrayList;

//Esta clase es para ser usada con el metodo de FIREBASEUI
public class InventarioCardAdapterV2 extends RecyclerView.Adapter<InventarioCardAdapterV2.ViewHolder> {
    ArrayList<CardItem> mCardItemsList;;

    public InventarioCardAdapterV2(ArrayList<CardItem> cardItemsList) {
        mCardItemsList = cardItemsList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.inventario_car_item, parent,false);
        //InventarioCardAdapter.ExampleViewHolder evh = new InventarioCardAdapter.ExampleViewHolder(v, mListener);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CardItem currentItem = mCardItemsList.get(position);
        holder.mTxtViewName.setText(currentItem.getNombre());
        holder.mTxtViewCantidad.setText(currentItem.getCantidad());
        holder.mTxtViewIngreso.setText(currentItem.getGuardarFecha());
    }

    @Override
    public int getItemCount() {
        return mCardItemsList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView mTxtViewName,mTxtViewIngreso,mTxtViewCantidad;
        public ImageView mDeleteImage, mEditImage;
        public ItemClickListener mListener;

        public ViewHolder(View view){
            super(view);

            mTxtViewName = itemView.findViewById(R.id.cardNombre);
            mTxtViewIngreso = itemView.findViewById(R.id.cardIngreso);
            mTxtViewCantidad = itemView.findViewById(R.id.cardCantidad);
            mDeleteImage = itemView.findViewById(R.id.image_delete);
            mEditImage = itemView.findViewById(R.id.image_edit);

            mDeleteImage.setOnClickListener(new View.OnClickListener() {
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
            mEditImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mListener.onEditClick(view, position);
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
