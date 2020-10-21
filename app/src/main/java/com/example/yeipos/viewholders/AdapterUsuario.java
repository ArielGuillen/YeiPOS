package com.example.yeipos.viewholders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yeipos.users.ListElement;
import com.example.yeipos.R;

import java.util.ArrayList;

public class AdapterUsuario extends RecyclerView.Adapter<AdapterUsuario.ViewHolder>{

    ArrayList<ListElement> lista;

    public AdapterUsuario(ArrayList<ListElement> lista) {
        this.lista = lista;
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
    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView nombre;
        TextView correo;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nombre = itemView.findViewById( R.id.userTextviewName );
            correo = itemView.findViewById( R.id.userTextviewEmail );


        }
    }

}
