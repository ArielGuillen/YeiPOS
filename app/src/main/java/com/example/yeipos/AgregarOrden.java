package com.example.yeipos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.yeipos.model.Comidas;
import com.example.yeipos.model.Mesas;
import com.example.yeipos.model.Orden;
import com.example.yeipos.model.OrdenItem;
import com.example.yeipos.model.Producto;
import com.example.yeipos.ui.home.HomeFragment;
import com.example.yeipos.viewHolder.BebidasViewHolder;
import com.example.yeipos.viewHolder.ComidasViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class AgregarOrden extends AppCompatActivity implements OnCompleteListener<Void>, View.OnClickListener {
    private ListView listViewMesas;
    private final List<Mesas> listMesas = new ArrayList<Mesas>();
    ArrayAdapter<Mesas> arrayAdapterMesas;

    DatabaseReference databaseReference;
    private FirebaseRecyclerAdapter adapter, adapter2;
    private RecyclerView recyclerViewComidas, recyclerViewBebidas;

    private Button mesasDropButton;
    boolean comidasVisible = false,  bebidasVisible = false, mesasVisible = false;
    Mesas mesasSelectedObj;
    String mesaSeleccionada;
    String nombreDePlatilloEnOrden,nombreDePlatilloEnOrden2, num;
    private ArrayList<Producto> productoList;
    private HashMap<String, String> cantidadesComidas, cantidadesBebidas, precioPlatillos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_orden);

        Toolbar mToolBar = (Toolbar) findViewById(R.id.addOrdenToolBar);
        setSupportActionBar(mToolBar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        databaseReference = FirebaseDatabase.getInstance().getReference();
        productoList = new ArrayList<>();
        cantidadesComidas = new HashMap<>();
        cantidadesBebidas = new HashMap<>();
        precioPlatillos = new HashMap<>();

        //----------------BOTONES FINDED
        mesasDropButton = findViewById(R.id.mesas_button);
        Button comidasDropButton = findViewById(R.id.comidas_button);
        Button bebidasDropButton = findViewById(R.id.bebidas_button);
        Button addOrderButton = findViewById(R.id.add_button_order);

        //---------------RECYCLERVIEW COMIDAS
        recyclerViewComidas = findViewById(R.id.comidas_recycler);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerViewComidas.setHasFixedSize(true);
        recyclerViewComidas.setLayoutManager(mLayoutManager);
        recyclerViewComidas.setVisibility(View.GONE);

        //---------------RECYCLERVIEW BEBIDAS
        recyclerViewBebidas = findViewById(R.id.bebidas_recycler);
        RecyclerView.LayoutManager mLayoutManager2 = new LinearLayoutManager(this);
        recyclerViewBebidas.setHasFixedSize(true);
        recyclerViewBebidas.setLayoutManager(mLayoutManager2);
        recyclerViewBebidas.setVisibility(View.GONE);

        //---------------LISTVIEW MESAS
        listViewMesas = findViewById(R.id.list_mesas);
        listViewMesas.setVisibility(View.GONE);

        //----------------POBLAR COMPONENTES
        listarDatosMesas();
        poblarComidas();
        poblarBebidas();

        //---------------------BOTONES CLICK LISTENERS
        mesasDropButton.setOnClickListener(this);
        comidasDropButton.setOnClickListener(this);
        bebidasDropButton.setOnClickListener(this);
        addOrderButton.setOnClickListener(this);

        listViewMesas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mesasSelectedObj = (Mesas) parent.getItemAtPosition(position);
                mesaSeleccionada = mesasSelectedObj.getNumMesa();
                //Log.i("MESA_SELECCIONADA", mesaSeleccionada);
                Toast.makeText(AgregarOrden.this, "Mesa "+mesaSeleccionada+" seleccionada", Toast.LENGTH_SHORT).show();
                mesasDropButton.setText(mesaSeleccionada);
                listViewMesas.setVisibility(View.GONE);
                mesasVisible = false;
            }
        });
    }

    private void listarDatosMesas() {
        databaseReference.child("mesas").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listMesas.clear();
                if(dataSnapshot.exists()) {
                    for(DataSnapshot objSnapshot : dataSnapshot.getChildren()){
                        Mesas m = objSnapshot.getValue(Mesas.class);
                        listMesas.add(m);
                        //Log.d("mensaje",listMesas.toString());
                        arrayAdapterMesas = new ArrayAdapter<>(AgregarOrden.this, android.R.layout.simple_list_item_1, listMesas);
                        listViewMesas.setAdapter(arrayAdapterMesas);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void poblarComidas(){
        FirebaseRecyclerOptions<Comidas> options = new FirebaseRecyclerOptions.Builder<Comidas>()
                .setQuery(databaseReference.child("comidas"), Comidas.class)
                .build();

        adapter = new FirebaseRecyclerAdapter<Comidas, ComidasViewHolder>(options){
            @NonNull
            @Override
            public ComidasViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_platillos_items,parent,false);
                return new ComidasViewHolder(v);
            }

            @Override
            protected void onBindViewHolder(@NonNull final ComidasViewHolder holder,
                                            final int position, @NonNull final Comidas model) {
                holder.txtNombre.setText(model.getNombre());
                databaseReference.child("orden").child(model.getId()).child("ordenItems").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            for(DataSnapshot objSnapshot : snapshot.getChildren()){
                                OrdenItem ordenItem = objSnapshot.getValue(OrdenItem.class);
                                nombreDePlatilloEnOrden2 = ordenItem.getNombre();
                                if(model.getNombre().equals(nombreDePlatilloEnOrden2)){
                                    holder.elegantNumberButton.setNumber(ordenItem.getCantidad());
                                    cantidadesComidas.put(nombreDePlatilloEnOrden2, ordenItem.getCantidad());
                                }
                            }
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {}
                });
                holder.elegantNumberButton.setOnClickListener(new ElegantNumberButton.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        num = holder.elegantNumberButton.getNumber();
                        cantidadesComidas.put(model.getNombre(), num);
                    }
                });
                precioPlatillos.put(model.getNombre(), model.getPrecio());
            }
        };
        recyclerViewComidas.setAdapter(adapter);
    }

    private void poblarBebidas() {
        FirebaseRecyclerOptions<Comidas> options = new FirebaseRecyclerOptions.Builder<Comidas>()
                .setQuery(databaseReference.child("bebidas"), Comidas.class)
                .build();
        adapter2 = new FirebaseRecyclerAdapter<Comidas, BebidasViewHolder>(options){
            @NonNull
            @Override
            public BebidasViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_platillos_items,parent,false);
                return new BebidasViewHolder(v);
            }

            @Override
            protected void onBindViewHolder(@NonNull final BebidasViewHolder holder, int position, @NonNull final Comidas model) {
                holder.txtNombre.setText(model.getNombre());
                databaseReference.child("orden").child(model.getId()).child("ordenItems").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            for(DataSnapshot objSnapshot : snapshot.getChildren()){
                                OrdenItem ordenItem = objSnapshot.getValue(OrdenItem.class);
                                nombreDePlatilloEnOrden = ordenItem.getNombre();
                                if(model.getNombre().equals(nombreDePlatilloEnOrden)){
                                    holder.elegantNumberButton.setNumber(ordenItem.getCantidad());
                                    cantidadesBebidas.put(nombreDePlatilloEnOrden, ordenItem.getCantidad());
                                }
                            }
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {}
                });
                holder.elegantNumberButton.setOnClickListener(new ElegantNumberButton.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        num = holder.elegantNumberButton.getNumber();
                        cantidadesBebidas.put(model.getNombre(),num);
                    }
                });
                precioPlatillos.put(model.getNombre(), model.getPrecio());
            }
        };
        recyclerViewBebidas.setAdapter(adapter2);
    }

    //botones de mesas, comida y bebidas
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.mesas_button:
                changeMesasVisibilityState();
                break;
            case R.id.comidas_button:
                changeComidasVisibilityState();
                break;
            case R.id.bebidas_button:
                changeBebidasVisibilityState();
                break;
            case R.id.add_button_order:
                validarDatos();
                break;
        }
    }

    private void validarDatos() {
        addProductsToList();
        GuardarInformacionProducto();
    }

    private void addProductsToList() {
        for (String i : cantidadesComidas.keySet()) {
            if(!cantidadesComidas.get(i).equals("0")||!cantidadesComidas.get(i).isEmpty()){
                Producto producto = new Producto();
                producto.setNombre(i);
                producto.setCantidad(cantidadesComidas.get(i));
                producto.setPrecio(precioPlatillos.get(i));
                productoList.add(producto);
                //Log.i("PRODUCTOS-AGREGADO", producto.toString());
            }else{
                Toast.makeText(this,"Seleccione una comida", Toast.LENGTH_SHORT).show();
            }
        }
        for (String i : cantidadesBebidas.keySet()){
            if(!cantidadesBebidas.get(i).equals("0")||!cantidadesBebidas.get(i).isEmpty()){
                Producto producto = new Producto();
                producto.setNombre(i);
                producto.setCantidad(cantidadesBebidas.get(i));
                producto.setPrecio(precioPlatillos.get(i));
                productoList.add(producto);
                //Log.i("PRODUCTOS-AGREGADO", producto.toString());
            }else{
                Toast.makeText(this,"Seleccione una bebida", Toast.LENGTH_SHORT).show();
            }
        }
        //Log.i("PRODUCTOS-AGREGADOS", productoList.toString());
    }

    private void GuardarInformacionProducto() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("dd-MM-yyyy");
        String guardarFecha = currentDate.format(calendar.getTime());
        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        String guardarHora = currentTime.format(calendar.getTime());
        String id = guardarFecha + guardarHora;

        Orden orden = new Orden();
        orden.setNumMesa(mesaSeleccionada);
        orden.setDate(guardarFecha);
        orden.setTime(guardarHora);
        orden.setStatus(true);
        orden.setKeyID(id);
        orden.setOrdenItems(productoList);
        orden.setTotal("0.0");

        databaseReference.child("orden").child(id).setValue(orden).addOnCompleteListener(this);
    }

    @Override
    public void onComplete(@NonNull Task<Void> task) {
        if(task.isSuccessful()){
            Toast.makeText(AgregarOrden.this, "Orden agregada exitosamente!", Toast.LENGTH_SHORT).show();
            finish();
        }else{
            String message = task.getException().toString();
            Toast.makeText(AgregarOrden.this, "Error: " + message, Toast.LENGTH_SHORT).show();
        }
    }

    private void changeComidasVisibilityState() {
        if(!comidasVisible){
            recyclerViewComidas.setVisibility(View.VISIBLE);
            comidasVisible = true;
            recyclerViewBebidas.setVisibility(View.GONE);
            bebidasVisible = false;
            listViewMesas.setVisibility(View.GONE);
            mesasVisible = false;
        }else if(comidasVisible){
            recyclerViewComidas.setVisibility(View.GONE);
            comidasVisible = false;
        }
    }

    private void changeMesasVisibilityState() {
        if(!mesasVisible){
            listViewMesas.setVisibility(View.VISIBLE);
            mesasVisible = true;
            recyclerViewComidas.setVisibility(View.GONE);
            comidasVisible = false;
            recyclerViewBebidas.setVisibility(View.GONE);
            bebidasVisible = false;
        }else if(mesasVisible){
            listViewMesas.setVisibility(View.GONE);
            mesasVisible = false;
        }
    }
    public void changeBebidasVisibilityState(){
        if(!bebidasVisible){
            recyclerViewBebidas.setVisibility(View.VISIBLE);
            bebidasVisible = true;
            recyclerViewComidas.setVisibility(View.GONE);
            comidasVisible = false;
            listViewMesas.setVisibility(View.GONE);
            mesasVisible = false;
        }else if(bebidasVisible){
            recyclerViewBebidas.setVisibility(View.GONE);
            bebidasVisible = false;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
        adapter2.startListening();
    }
    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
        adapter2.stopListening();
    }
}