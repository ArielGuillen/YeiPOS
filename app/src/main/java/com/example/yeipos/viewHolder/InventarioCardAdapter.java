package com.example.yeipos.viewHolder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yeipos.R;
import com.example.yeipos.model.CardItem;

import java.util.ArrayList;

//Esta clase es para ser usada con una peticion a firebase normal
//con los metodos getFromFirebase y buildRecyclerView de la clase InventarioFragment
public class InventarioCardAdapter extends RecyclerView.Adapter<InventarioCardAdapter.ExampleViewHolder> {
    ArrayList<CardItem> mCardItemsList;;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onDeleteClick(int position);
        void onEditClick(int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }
    public static class ExampleViewHolder extends RecyclerView.ViewHolder{
        public TextView mTxtViewName,mTxtViewIngreso,mTxtViewCantidad;
        public ImageView mDeleteImage, mEditImage;

        public  ExampleViewHolder(View itemView, final OnItemClickListener listener){
            super(itemView);

            mTxtViewName = itemView.findViewById(R.id.cardNombre);
            mTxtViewIngreso = itemView.findViewById(R.id.cardIngreso);
            mTxtViewCantidad = itemView.findViewById(R.id.cardCantidad);
            mDeleteImage = itemView.findViewById(R.id.image_delete);
            mEditImage = itemView.findViewById(R.id.image_edit);

            mDeleteImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onDeleteClick(position);
                        }
                    }
                }
            });
            mEditImage.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onEditClick(position);
                        }
                    }
                }
            });
        }
    }

    public InventarioCardAdapter(ArrayList<CardItem> cardItemsList) {
        mCardItemsList = cardItemsList;
    }
    @NonNull
    @Override
    public ExampleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.inventario_car_item,parent,false);
        ExampleViewHolder evh = new ExampleViewHolder(v, mListener);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull ExampleViewHolder holder, int position) {
        CardItem currentItem = mCardItemsList.get(position);
        holder.mTxtViewName.setText(currentItem.getNombre());
        holder.mTxtViewCantidad.setText(currentItem.getCantidad());
        holder.mTxtViewIngreso.setText(currentItem.getGuardarFecha());

    }

    @Override
    public int getItemCount() {
        return mCardItemsList.size();
    }
}
