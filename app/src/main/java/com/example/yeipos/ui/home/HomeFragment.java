package com.example.yeipos.ui.home;

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
import com.example.yeipos.R;
import com.example.yeipos.interfaces.ItemClickListener;
import com.example.yeipos.model.OrderCardViewHome;
import com.example.yeipos.viewHolder.OrdenesCardAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class HomeFragment extends Fragment implements ItemClickListener {
    ArrayList<OrderCardViewHome> ordenCards = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private OrdenesCardAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private DatabaseReference ordenesRef;

    private HomeViewModel homeViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                            @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        ordenesRef = FirebaseDatabase.getInstance().getReference();

        mRecyclerView = root.findViewById(R.id.recycler_home);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);

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

//    public void onStart() {
//        super.onStart();
//
//        FirebaseRecyclerOptions<Orden> options =
//                new FirebaseRecyclerOptions.Builder<Orden>()
//                        .setQuery(ordenesRef.child("orden"), Orden.class)
//                        .build();
//
//        FirebaseRecyclerAdapter adapter =
//                new FirebaseRecyclerAdapter<Orden, OrdenesCardAdapter.ViewHolderOrden>(options) {
//                    @Override
//                    protected void onBindViewHolder(@NonNull OrdenesCardAdapter.ViewHolderOrden holder,
//                                                    int position, @NonNull final Orden model) {
//                        holder.textViewMesa.setText(model.getNumMesa());
//                        holder.textViewFecha.setText(model.getTime());
//                        //holder.listV = ArrayAdapter
////                        holder.mDeleteImage.setOnClickListener(new View.OnClickListener() {
////                            @Override
////                            public void onClick(View view) {
////                                String id = model.getId();
////                                inventarioRef.child("inventario").child(id).removeValue();
////                                Toast.makeText(getActivity(),"Producto eliminado",Toast.LENGTH_SHORT).show();
////                            }
////                        });
////                        holder.mEditImage.setOnClickListener(new View.OnClickListener() {
////                            @Override
////                            public void onClick(View view) {
////                                String id = model.getId();
////                                Intent intent = new Intent(getActivity(), ModificarInventario.class);
////                                intent.putExtra("message", id);
////                                startActivity(intent);
////                            }
////                        });
//                    }
//                    @NonNull
//                    @Override
//                    public OrdenesCardAdapter.ViewHolderOrden onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.orden_card_item, parent, false);
//                        OrdenesCardAdapter.ViewHolderOrden holder = new  OrdenesCardAdapter.ViewHolderOrden(view);
//                        return holder;
//                    }
//                };
//        mRecyclerView.setAdapter(adapter);
//        adapter.startListening();
//    }

    @Override
    public void onDeleteClick(View view, int position) {

    }

    @Override
    public void onEditClick(View view, int position) {

    }
}