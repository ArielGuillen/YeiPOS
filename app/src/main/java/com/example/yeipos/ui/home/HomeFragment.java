package com.example.yeipos.ui.home;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yeipos.AgregarOrden;
import com.example.yeipos.ModificarOrden;
import com.example.yeipos.R;
import com.example.yeipos.TerminarOrden;
import com.example.yeipos.model.Orden;
import com.example.yeipos.model.OrdenItem;
import com.example.yeipos.model.OrderCardViewHome;
import com.example.yeipos.viewHolder.OrdenesCardAdapter;
import com.example.yeipos.viewHolder.ProductViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


public class HomeFragment extends Fragment {
    ArrayList<OrderCardViewHome> ordenCards = new ArrayList<>();
    private OrdenesCardAdapter mAdapter;

    private RecyclerView mRecyclerView;
    private DatabaseReference ordenesRef;
    private FirebaseRecyclerAdapter adapter;

    //private DatabaseReference dbRefItems = FirebaseDatabase.getInstance().getReference().child("orden").child("ordenItems");
    private FirebaseRecyclerAdapter adapter2;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                            @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        ordenesRef = FirebaseDatabase.getInstance().getReference();

        mRecyclerView = root.findViewById(R.id.recycler_home);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);

        poblarFirebaseRecycler();

        FloatingActionButton fab = root.findViewById(R.id.fabOrdenes);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //OpenAgregarOrden();
                Intent intent=new Intent(getActivity(), AgregarOrden.class);
                startActivity(intent);
            }
        });

        return root;
//        return inflater.inflate(R.layout.fragment_home, container, false);
    }

//    private void getFromFirebase(){
//        ordenesRef.child("orden").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if(snapshot.exists()){
//                    for(DataSnapshot ds: snapshot.getChildren()){
//                        String numMesa = ds.child("numMesa").getValue().toString();
//                        String date = ds.child("date").getValue().toString();
//                        String status = ds.child("status").getValue().toString();
//                        String time = ds.child("time").getValue().toString();
//                        ordenCards.add(new OrderCardViewHome(numMesa,time,cantidad));
//                    }
//
//                    mAdapter = new InventarioCardAdapter(cardItemList);
//                    mRecyclerView.setAdapter(mAdapter);
//                    buildRecyclerView();
//
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//    }

    //    public void buildRecyclerView() {
//
//        mAdapter.setOnItemClickListener(new InventarioCardAdapter.OnItemClickListener() {
//            @Override
//            public void onDeleteClick(int position) {
//
//                Toast.makeText(getActivity(), "Producto eliminado", Toast.LENGTH_SHORT).show();
//                //Toast.makeText(this, "Seleccione mesa", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onEditClick(int position) {
//                Toast.makeText(getActivity(), "editado", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }

    private void poblarFirebaseRecycler() {
        //        FirebaseListAdapter
        FirebaseRecyclerOptions<Orden> options =
                new FirebaseRecyclerOptions.Builder<Orden>()
                        .setQuery(ordenesRef.child("orden"), Orden.class)
                        .build();

        adapter =
                new FirebaseRecyclerAdapter<Orden, OrdenesCardAdapter.ViewHolderOrden>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull OrdenesCardAdapter.ViewHolderOrden holder,
                                                    int position, @NonNull final Orden model) {
                        holder.textViewMesa.setText(model.getNumMesa());
                        holder.textViewFecha.setText(model.getTime());

                        FirebaseRecyclerOptions<OrdenItem> options2 =
                                new FirebaseRecyclerOptions.Builder<OrdenItem>()
                                        .setQuery(ordenesRef.child("orden").child(model.getKeyID()).child("ordenItems"), OrdenItem.class)
                                        .build();
                        adapter2=new FirebaseRecyclerAdapter<OrdenItem, ProductViewHolder>(options2){
                                    @NonNull
                                    @Override
                                    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                                        //View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.orden_row_items, parent, false);
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

                        holder.editar.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                //Toast.makeText(getActivity(), "Editado click", Toast.LENGTH_SHORT).show();
                                String id = model.getKeyID();
                                String numMesa = model.getNumMesa();
                                String fecha = model.getDate();
                                String hora = model.getTime();
                                Intent intent = new Intent(getActivity(), ModificarOrden.class);
                                intent.putExtra("message", id);
                                intent.putExtra("numMesa", numMesa);
                                intent.putExtra("fecha", fecha);
                                intent.putExtra("hora", hora);
                                startActivity(intent);
                            }
                        });
                        holder.terminar.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                //Toast.makeText(getActivity(), "Orden Terminada", Toast.LENGTH_SHORT).show();
                               MaterialAlertDialogBuilder alerta = new MaterialAlertDialogBuilder(getActivity());
                               alerta.setTitle("Alerta");
                               alerta.setMessage("¿Esta seguro que desea terminar la orden?")
                                        .setCancelable(false)
                                        .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                //getActivity().finish();
                                                String id = model.getKeyID();
                                                Intent intent = new Intent(getActivity(), TerminarOrden.class);
                                                intent.putExtra("message", id);
                                                startActivity(intent);
                                            }
                                        })
                                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                dialogInterface.cancel();
                                            }
                                        });
                                alerta.show();
                            }
                        });
                    }

                    @NonNull
                    @Override
                    public OrdenesCardAdapter.ViewHolderOrden onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.orden_card_item, parent, false);
                        OrdenesCardAdapter.ViewHolderOrden holder = new  OrdenesCardAdapter.ViewHolderOrden(view);
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