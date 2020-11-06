package com.example.yeipos.ui.inventario;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yeipos.AddToInventario;
import com.example.yeipos.ModificarInventario;
import com.example.yeipos.R;
import com.example.yeipos.TerminarOrden;
import com.example.yeipos.interfaces.ItemClickListener;
import com.example.yeipos.model.CardItem;
import com.example.yeipos.model.ItemInventario;
import com.example.yeipos.viewHolder.InventarioCardAdapter;
import com.example.yeipos.viewHolder.InventarioCardAdapterV2;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class InventarioFragment extends Fragment implements ItemClickListener {
    ArrayList<CardItem> cardItemList = new ArrayList<>();
    private RecyclerView mRecyclerView;
    //private RecyclerView.Adapter mAdapter;
    private InventarioCardAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private InventarioViewModel inventarioViewModel;

    private DatabaseReference inventarioRef;

    AppCompatActivity activity;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        inventarioViewModel =
                new ViewModelProvider(this).get(InventarioViewModel.class);
        View root = inflater.inflate(R.layout.fragment_inventario, container, false);

        activity = (AppCompatActivity) getActivity();
        activity.getSupportActionBar().setTitle(R.string.title_inventario);

        inventarioRef = FirebaseDatabase.getInstance().getReference();

//        cardItems = new ArrayList<>();
//        cardItems.add(new CardItem("Nombre", "20-10-2020", "10"));

        mRecyclerView = root.findViewById(R.id.recycler_inventario);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setHasFixedSize(true);//solo si sabemos que el recycler no cambiara de tamaño
        mRecyclerView.setLayoutManager(mLayoutManager);
        //getFromFirebase();
        //buildRecyclerView();

        FloatingActionButton fab = root.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //OpenAgregarOrden();
                Intent intent = new Intent(getActivity(), AddToInventario.class);
                startActivity(intent);
            }
        });

        return root;
    }

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
//
//    private void getFromFirebase(){
//        inventarioRef.child("inventario").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if(snapshot.exists()){
//                    for(DataSnapshot ds: snapshot.getChildren()){
//                        String nombre = ds.child("nombre").getValue().toString();
//                        String guardarFecha = ds.child("guardarFecha").getValue().toString();
//                        String cantidad = ds.child("cantidad").getValue().toString();
//                        cardItemList.add(new CardItem(nombre,guardarFecha,cantidad));
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

    @Override
    public void onDeleteClick(View view, int position) {
        Toast.makeText(getActivity(), "eliminado", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onEditClick(View view, int position) {
        Toast.makeText(getActivity(), "editado", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<ItemInventario> options =
                new FirebaseRecyclerOptions.Builder<ItemInventario>()
                        .setQuery(inventarioRef.child("inventario"), ItemInventario.class)
                        .build();

        FirebaseRecyclerAdapter adapter =
                new FirebaseRecyclerAdapter<ItemInventario, InventarioCardAdapterV2.ViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull InventarioCardAdapterV2.ViewHolder holder,
                                                    int position, @NonNull final ItemInventario model) {
                        holder.mTxtViewName.setText(model.getNombre());
                        holder.mTxtViewIngreso.setText(model.getGuardarFecha());
                        holder.mTxtViewCantidad.setText(model.getCantidad());
                        holder.mDeleteImage.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                MaterialAlertDialogBuilder alerta = new MaterialAlertDialogBuilder(getActivity());
                                alerta.setTitle("Alerta");
                                alerta.setMessage("¿Esta seguro que desea eliminar del inventario?")
                                        .setCancelable(false)
                                        .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                String id = model.getId();
                                                inventarioRef.child("inventario").child(id).removeValue();
                                                //inventarioRef.child(model.getCategoria()).child(id).removeValue();
                                                Toast.makeText(getActivity(),"Producto eliminado",Toast.LENGTH_SHORT).show();
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
                        holder.mEditImage.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                String id = model.getId();
                                Intent intent = new Intent(getActivity(), ModificarInventario.class);
                                intent.putExtra("message", id);
                                startActivity(intent);
                            }
                        });
                    }
                    @NonNull
                    @Override
                    public InventarioCardAdapterV2.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.inventario_car_item, parent, false);
                        InventarioCardAdapterV2.ViewHolder holder = new InventarioCardAdapterV2.ViewHolder(view);
                        return holder;
                    }
                };
        mRecyclerView.setAdapter(adapter);
        adapter.startListening();
    }
}