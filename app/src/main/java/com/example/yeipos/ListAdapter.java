package com.example.yeipos;


import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder>{
    private List<ListElement> mData;
    private LayoutInflater mInflater;
    private OnItemClickListener mListener;
    private Context context;

    public ListAdapter(List<ListElement> itemList, Context context) {
        this.mInflater = LayoutInflater.from( context );
        this.mData = itemList;
        this.context = context;
    }



    //----------------------------Interface OnCLick-----------------------------------------
    public interface OnItemClickListener{
        void onItemClick( int position );
        void onDeleteClick( int position );
        void onEditClick( int position );
    }

    public void setOnClickListener( OnItemClickListener listener ){
        mListener = listener;
    }
    //---------------------------------------Override-------------------------------------

    @Override
    public ListAdapter.ViewHolder onCreateViewHolder( ViewGroup parent, int viewType ){
        View view = mInflater.inflate( R.layout.list_element, parent,false );
        return new ListAdapter.ViewHolder( view, mListener );
    }

    @Override
    public void onBindViewHolder( final ListAdapter.ViewHolder holder, final int position ){
        holder.binData( mData.get( position ) );
    }
    @Override
    public int getItemCount(){
        return mData.size();
    }


    //------------------------------------Fin Override--------------------------------------


    //-----------------------------------Métodos Alternativos-------------------------------
    public void setItems( List<ListElement> items ){
        mData = items;
    }



    //-------------------------------Clase ViewHolder----------------------------------------

    public class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView iconImage;
        private ImageView delImage;
        private ImageView editImage;
        private TextView userEmail;
        private TextView userName;

        //-------------Constructor---------------
        public ViewHolder (View itemView, final OnItemClickListener listener ){
            super( itemView );
            //---------Obtener Views--------
            iconImage = itemView.findViewById(R.id.iconUser);
            delImage = itemView.findViewById( R.id.userDelete );
            editImage = itemView.findViewById( R.id.userEdit );
            userEmail = itemView.findViewById( R.id.userTextviewEmail );
            userName = itemView.findViewById( R.id.userTextviewName );

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int position = getAdapterPosition();
                    if( position != RecyclerView.NO_POSITION ){
                        listener.onItemClick( position);

                    }
                    return true;
                }
            });

            delImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if( listener != null ){
                        int position = getAdapterPosition();
                        if( position != RecyclerView.NO_POSITION){
                            listener.onDeleteClick(position);
                        }
                    }

                }
            });

            editImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if( listener != null ){
                        int position = getAdapterPosition();
                        if( position != RecyclerView.NO_POSITION){
                            listener.onEditClick(position);
                        }
                    }

                }
            });
        }

        void binData( final  ListElement item ){
            this.userName.setText( item.getName() );
            this.userEmail.setText( item.getEmail() );
        }

    }
    //------------------------------------Fin Clase ViewHolder----------------------------------

}
