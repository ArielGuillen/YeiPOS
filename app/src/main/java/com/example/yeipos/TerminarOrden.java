package com.example.yeipos;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yeipos.model.Producto;
import com.example.yeipos.viewHolder.PlatilloViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseError;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class TerminarOrden extends AppCompatActivity implements OnCompleteListener<Void> {
    private String intentMessage;
    private Double totalFinal = 0.0;

    private DatabaseReference databaseReference;
    private FirebaseRecyclerAdapter adapter;
    private RecyclerView recyclerView;

    private TextView txtTotalFinal;
    private HashMap<String, Double> hashMapPrecios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terminar_orden);
        try {
            intentMessage = getIntent().getStringExtra("message");
        } catch (Exception e){
            e.printStackTrace();
        }
        databaseReference = FirebaseDatabase.getInstance().getReference();

        hashMapPrecios = new HashMap<>();

        txtTotalFinal = findViewById(R.id.txt_total);
        Button finalizarOrden = findViewById(R.id.finalizar_button_order);

        Toolbar mToolBar = findViewById(R.id.terminarOrdenToolBar);
        setSupportActionBar(mToolBar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        recyclerView = findViewById(R.id.recycler_terminar_orden);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(mLayoutManager);

        poblarRecycler();

        finalizarOrden.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Log.i("PRECIOSFINALES", hashMapPrecios.toString());
//                Log.i("TOTAL", totalFinal.toString());
                databaseReference.child("orden").child(intentMessage).child("status").setValue(false);
                databaseReference.child("orden").child(intentMessage).child("total").setValue(totalFinal.toString());
                moveFirebaseRecord();
            }
        });
    }

    private void poblarRecycler() {
        FirebaseRecyclerOptions<Producto> options = new FirebaseRecyclerOptions.Builder<Producto>()
                .setQuery(databaseReference.child("orden").child(intentMessage).child("ordenItems"), Producto.class)
                .build();
        adapter = new FirebaseRecyclerAdapter<Producto, PlatilloViewHolder>(options){

            @NonNull
            @Override
            public PlatilloViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.terminar_orden_row_items,parent,false);
                return new PlatilloViewHolder(v);
            }

            @Override
            protected void onBindViewHolder(@NonNull PlatilloViewHolder holder, int position, @NonNull Producto model) {
                holder.txtProductName.setText(model.getNombre());
                holder.txtProductCantidad.setText(model.getCantidad());
                double precioAcumuladoDeProducto = Double.parseDouble(model.getCantidad()) * Double.parseDouble(model.getPrecio());
                hashMapPrecios.put(model.getNombre(), precioAcumuladoDeProducto);
                //Log.i("PRECIOS2", hashMapPrecios.toString());
                totalFinal += precioAcumuladoDeProducto;
                txtTotalFinal.setText(String.format("%.2f", totalFinal));
                holder.txtProductPrecio.setText(String.format("%.2f", precioAcumuladoDeProducto));
            }
        };
        recyclerView.setAdapter(adapter);
    }

    public void moveFirebaseRecord()
    {
        databaseReference.child("orden").child(intentMessage).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                databaseReference.child("ordenesTerminadas").child(intentMessage).setValue(dataSnapshot.getValue()).addOnCompleteListener(TerminarOrden.this);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                }
        });
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

    @Override
    public void onComplete(@NonNull Task<Void> task) {
        if(task.isSuccessful()){
//            Intent intent = new Intent(TerminarOrden.this, MainActivity.class);
//            startActivity(intent);
            Toast.makeText(TerminarOrden.this, "Orden finalizada exitosamente..", Toast.LENGTH_SHORT).show();
            databaseReference.child("orden").child(intentMessage).removeValue();
            finish();
        }else{
            String message = task.getException().toString();
            Toast.makeText(TerminarOrden.this, "Error: " + message, Toast.LENGTH_SHORT).show();
        }
    }
}