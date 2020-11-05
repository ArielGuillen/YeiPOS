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
    private AdapterUsuario.OnItemClickListener mListener;

    public void setOnItemClickListener( OnItemClickListener listener ){
        mListener = listener;
    }

    public AdapterUsuario(ArrayList<ListElement> lista) {
        this.lista = lista;
    }


    //-----------------------------Interface--------------------------
    public interface OnItemClickListener {
        void onItemLongClick( int position );
        void onDeleteClick(int position);
        void onEditClick(int position);
    }

    //--------------------------Métodos---------------------------------------

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from( parent.getContext() ).inflate( R.layout.list_element, parent, false );
        ViewHolder viewH = new ViewHolder( v, mListener );
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

    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView nombre;
        TextView correo;
        ImageView btnEdit;
        ImageView btnDel;
        public ItemClickListener mListener;


        public ViewHolder(@NonNull View itemView, final OnItemClickListener listener ) {

            super(itemView);

            nombre = itemView.findViewById( R.id.userTextviewName );
            correo = itemView.findViewById( R.id.userTextviewEmail );
            btnEdit = itemView.findViewById( R.id.userEdit );
            btnDel = itemView.findViewById( R.id.userDelete );

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if( listener != null ){
                        int position = getAdapterPosition();
                        if( position != RecyclerView.NO_POSITION ){
                            listener.onItemLongClick( position );
                        }
                    }
                    return true;
                }
            });

            btnDel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if( listener != null ){
                        int position = getAdapterPosition();
                        if( position != RecyclerView.NO_POSITION ){
                            listener.onDeleteClick( position );
                        }
                    }
                }
            });
            btnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if ( listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onEditClick( position );
                        }
                    }
                }
            });
        }

        public void ItemClickListener(ItemClickListener mListener){
            this.mListener = mListener;
        }

    }

}
