package com.example.yeipos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.yeipos.model.Orden;
import com.example.yeipos.model.OrdenItem;
import com.example.yeipos.viewHolder.OrdenesCardAdapterV2;
import com.example.yeipos.viewHolder.ProductViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegistroDeOrdenesTerminadas extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private DatabaseReference ordenesRef;
    private FirebaseRecyclerAdapter<Orden, OrdenesCardAdapterV2.ViewHolderOrden> adapter;
    private FirebaseRecyclerAdapter<OrdenItem, ProductViewHolder> adapter2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_de_ordenes_terminadas);

        ordenesRef = FirebaseDatabase.getInstance().getReference();

        mRecyclerView = findViewById(R.id.recycler_ordenes_terminadas);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);

        poblarFirebaseRecycler();

        Toolbar mToolBar = (Toolbar) findViewById(R.id.ToolBar);
        setSupportActionBar(mToolBar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void poblarFirebaseRecycler() {
        FirebaseRecyclerOptions<Orden> options =
                new FirebaseRecyclerOptions.Builder<Orden>()
                        .setQuery(ordenesRef.child("ordenesTerminadas"), Orden.class)
                        .build();

        adapter = new FirebaseRecyclerAdapter<Orden, OrdenesCardAdapterV2.ViewHolderOrden>(options) {
            @Override
            protected void onBindViewHolder(@NonNull OrdenesCardAdapterV2.ViewHolderOrden holder, int position, @NonNull Orden model) {
                holder.textViewMesa.setText(model.getNumMesa());
                holder.textViewFecha.setText(model.getTime());
                holder.textTotal.setText(model.getTotal());

                FirebaseRecyclerOptions<OrdenItem> options2 =
                        new FirebaseRecyclerOptions.Builder<OrdenItem>()
                                .setQuery(ordenesRef.child("ordenesTerminadas").child(model.getKeyID()).child("ordenItems"), OrdenItem.class)
                                .build();
                adapter2=new FirebaseRecyclerAdapter<OrdenItem, ProductViewHolder>(options2){
                    @NonNull
                    @Override
                    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View v2 = LayoutInflater.from(parent.getContext()).inflate(R.layout.orden_row_items,parent,false);
                        return new ProductViewHolder(v2);
                    }

                    @Override
                    protected void onBindViewHolder(@NonNull ProductViewHolder productViewHolder, int position, @NonNull OrdenItem ordenItem) {
                        productViewHolder.txtProductName.setText(ordenItem.getNombre());
                        productViewHolder.txtProductCantidad.setText(ordenItem.getCantidad());
                    }
                };
                adapter2.startListening();
                adapter2.notifyDataSetChanged();
                holder.rowItemRecycler.setAdapter(adapter2);
            }

            @NonNull
            @Override
            public OrdenesCardAdapterV2.ViewHolderOrden onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.orden_historial_card, parent, false);
                OrdenesCardAdapterV2.ViewHolderOrden holder = new  OrdenesCardAdapterV2.ViewHolderOrden(view);
                return holder;
            }
        };
        mRecyclerView.setAdapter(adapter);
    }

    public void onStart() {
        super.onStart();
        adapter.startListening();
    }
    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}