package com.example.yeipos.viewholders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yeipos.interfaces.ItemClickListener;
import com.example.yeipos.users.ListElement;
import com.example.yeipos.R;
import com.example.yeipos.viewHolder.InventarioCardAdapter;

import java.util.ArrayList;

public class AdapterUsuario extends RecyclerView.Adapter<AdapterUsuario.ViewHolder>{

    public ArrayList<ListElement> lista;

    public AdapterUsuario(ArrayList<ListElement> lista) {
        this.lista = lista;
    }

    public interface OnItemClickListener {
        void onDeleteClick(int position);
        void onEditClick(int position);
    }

    //--------------------------Métodos---------------------------------------

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from( parent.getContext() ).inflate( R.layout.list_element, parent, false );
        ViewHolder viewH = new ViewHolder( v );
        return viewH;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ListElement element = lista.get( position );
        holder.nombre.setText( element.getName() );
        holder.correo.setText( element.getEmail() );

    }


    @Override
    public int getItemCount() {
        return lista.size();
    }

    //------------------------------Clase --------------------------------------------
    public static class ViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener {

        TextView nombre;
        TextView correo;
        ImageView btnEdit;
        ImageView btnDel;
        public ItemClickListener mListener;


        public ViewHolder(@NonNull View itemView ) {
            super(itemView);
            nombre = itemView.findViewById( R.id.userTextviewName );
            correo = itemView.findViewById( R.id.userTextviewEmail );
            btnEdit = itemView.findViewById( R.id.userEdit );
            btnDel = itemView.findViewById( R.id.userDelete );

            btnEdit.setOnClickListener(new View.OnClickListener() {
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
            btnDel.setOnClickListener(new View.OnClickListener() {
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
