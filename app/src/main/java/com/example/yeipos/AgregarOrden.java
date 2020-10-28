package com.example.yeipos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.yeipos.model.Orden;
import com.example.yeipos.model.Producto;
import com.example.yeipos.ui.home.HomeFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class AgregarOrden extends AppCompatActivity implements ElegantNumberButton.OnClickListener, OnCompleteListener<Void>, AdapterView.OnItemSelectedListener {
    private String mesaString = "";
    private int num1;
    private int num2;
    private int num3;
    private int num4;
    private int num5;
    private int num6;
    private int num7;
    private int num8;
    private Spinner mesasDropdown;
    private Button addOrderButton,  comidasDropButton, bebidasDropButton;
    private ElegantNumberButton  button1, button2, button3, button4,button5, button6, button7, button8;
    private TextView platillo1, platillo2, platillo3, platillo4;
    private TextView bebida1, bebida2, bebida3, bebida4;
    private LinearLayout layoutComidas, layoutBebidas;
    boolean comidasVisible = false;
    boolean bebidasVisible = false;
    //datos para enviar a la database
    private DatabaseReference mDatabase, dbChildComida, dbChildBebidas;
    private Orden orden;
    private Producto producto1, producto2, producto3, producto4, producto5, producto6, producto7, producto8;
    private  String mesa;
    String[] mesas = new String[]{"Mesa 1", "Mesa 2", "Mesa 3", "Mesa 4"};
    private String GuardarFecha, GuardarHora,ordenKey;

    private RecyclerView recyclerView;
    private ArrayList<Producto> productoList, productoListDefault;
    private ArrayList<Integer> numbers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_orden);

        productoList = new ArrayList<>();
        productoListDefault = new ArrayList<>();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("orden");
        dbChildComida = FirebaseDatabase.getInstance().getReference().child("comidas");
        dbChildBebidas = FirebaseDatabase.getInstance().getReference().child("bebidas");

        Toolbar mToolBar = (Toolbar) findViewById(R.id.addOrderToolBar);
        setSupportActionBar(mToolBar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        comidasDropButton = findViewById(R.id.comidas_button);
        bebidasDropButton = findViewById(R.id.bebidas_button);
        layoutComidas = findViewById(R.id.comidas_layout);
        layoutComidas.setVisibility(View.GONE);

        layoutBebidas = findViewById(R.id.bebidas_layout);
        layoutBebidas.setVisibility(View.GONE);

        platillo1 = findViewById(R.id.txt_platillo1);
        platillo2 = findViewById(R.id.txt_platillo2);
        platillo3 = findViewById(R.id.txt_platillo3);
        platillo4 = findViewById(R.id.txt_platillo4);
        bebida1 = findViewById(R.id.txt_bebida1);
        bebida2 = findViewById(R.id.txt_bebida2);
        bebida3 = findViewById(R.id.txt_bebida3);
        bebida4 = findViewById(R.id.txt_bebida4);

        button1 = (ElegantNumberButton) findViewById(R.id.button_adder1);
        button2 = (ElegantNumberButton) findViewById(R.id.button_adder2);
        button3 = (ElegantNumberButton) findViewById(R.id.button_adder3);
        button4 = (ElegantNumberButton) findViewById(R.id.button_adder4);
        button5 = (ElegantNumberButton) findViewById(R.id.button_adder5);
        button6 = (ElegantNumberButton) findViewById(R.id.button_adder6);
        button7 = (ElegantNumberButton) findViewById(R.id.button_adder7);
        button8 = (ElegantNumberButton) findViewById(R.id.button_adder8);

        comidasDropButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                comidasBClicked(layoutComidas);
            }
        });

        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
        button5.setOnClickListener(this);
        button6.setOnClickListener(this);
        button7.setOnClickListener(this);
        button8.setOnClickListener(this);

        bebidasDropButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bebidasBClicked(layoutBebidas);
            }
        });

        addOrderButton = (Button) findViewById(R.id.add_button_order);
        mesasDropdown = findViewById(R.id.mesas_spinner);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, mesas);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mesasDropdown.setAdapter(adapter);
        mesasDropdown.setOnItemSelectedListener(this);
        //mesaString = mesasDropdown.getSelectedItem().toString();

        addOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    addProductDefault();
                    validarDatosOrden();
            }
        });


    }

    public void comidasBClicked(LinearLayout layoutComidas){
        if(comidasVisible == false){
            layoutComidas.setVisibility(View.VISIBLE);
            comidasVisible = true;
        }else if (comidasVisible){
            layoutComidas.setVisibility(View.GONE);
            comidasVisible = false;
        }
    }

    public void bebidasBClicked(LinearLayout layoutBebidas){
        if(bebidasVisible == false){
            layoutBebidas.setVisibility(View.VISIBLE);
            bebidasVisible = true;
        }else if (bebidasVisible){
            layoutBebidas.setVisibility(View.GONE);
            bebidasVisible = false;
        }
    }

    private void validarDatosOrden(){
        if (mesaString.equals("")) {
            Toast.makeText(this, "Seleccione mesa", Toast.LENGTH_SHORT).show();
        }else if(num1 == 0 && num2 == 0 && num3 == 0 && num4 == 0 && num5 == 0 && num6 == 0 && num7 == 0 && num8 == 0){
            Toast.makeText(this, "Seleccione algun platillo/bebida", Toast.LENGTH_SHORT).show();
        }
        else {
            addProductsToList();
            for (Producto member : productoListDefault){
                Log.i("orden name: ", member.getNombre());
                Log.i("orden cantidad: ", String.valueOf(member.getCantidad()));
            }
            for (Producto member : productoList){
                Log.i("orden name: ", member.getNombre());
            }
            subirDatosOrden();
        }
    }

    private void subirDatosOrden(){
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("dd-MM-yyyy");
        GuardarFecha = currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        GuardarHora = currentTime.format(calendar.getTime());

        ordenKey = GuardarFecha + GuardarHora;

        orden = new Orden();

        orden.setNumMesa(mesaString);
        orden.setDate(GuardarFecha);
        orden.setTime(GuardarHora);
        orden.setStatus(true);
        orden.setKeyID(ordenKey);
        orden.setOrdenItems(productoList);

        mDatabase.child(ordenKey).setValue(orden).addOnCompleteListener(this);
        //mDatabase.child(ordenKey).child("items").push();
        //mDatabase.push().setValue(producto);
    }

    private void addProductsToList(){
        //Log.d("MYARRAY", " arr: "+ Arrays.toString(cantidades));
        for(Producto item: productoListDefault){
            if( Integer.parseInt(item.getCantidad()) != 0){
                productoList.add(item);
            }
        }

    }

    private void addProductDefault(){
        producto1 = new Producto();
        producto1.setNombre(platillo1.getText().toString());
        producto1.setCantidad(Integer.toString(num1));
        producto1.setPrecio(Double.toString(0.0));
        productoListDefault.add(producto1);

        producto2 = new Producto();
        producto2.setNombre(platillo2.getText().toString());
        producto2.setCantidad(String.valueOf(num2));
        producto2.setPrecio(String.valueOf(0.0));
        productoListDefault.add(producto2);

        producto3 = new Producto();
        producto3.setNombre(platillo3.getText().toString());
        producto3.setCantidad(String.valueOf(num3));
        producto3.setPrecio(String.valueOf(0.0));
        productoListDefault.add(producto3);

        producto4 = new Producto();
        producto4.setNombre(platillo4.getText().toString());
        producto4.setCantidad(String.valueOf(num4));
        producto4.setPrecio(String.valueOf(0.0));
        productoListDefault.add(producto4);

        producto5 = new Producto();
        producto5.setNombre(bebida1.getText().toString());
        producto5.setCantidad(String.valueOf(num1));
        producto5.setPrecio(String.valueOf(0.0));
        productoListDefault.add(producto5);

        producto6 = new Producto();
        producto6.setNombre(bebida2.getText().toString());
        producto6.setCantidad(String.valueOf(num6));
        producto6.setPrecio(String.valueOf(0.0));
        productoListDefault.add(producto6);

        producto7 = new Producto();
        producto7.setNombre(bebida3.getText().toString());
        producto7.setCantidad(String.valueOf(num7));
        producto7.setPrecio(String.valueOf(0.0));
        productoListDefault.add(producto7);

        producto8 = new Producto();
        producto8.setNombre(bebida4.getText().toString());
        producto8.setCantidad(String.valueOf(num8));
        producto8.setPrecio(String.valueOf(0.0));
        productoListDefault.add(producto8);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.button_adder1:
                num1 = Integer.parseInt(button1.getNumber());
                break;
            case R.id.button_adder2:
                num2 = Integer.parseInt(button2.getNumber());
                //producto2.setCantidad(num2);
                break;
            case R.id.button_adder3:
                num3 = Integer.parseInt(button3.getNumber());
                //producto3.setCantidad(num3);
                break;
            case R.id.button_adder4:
                num4 = Integer.parseInt(button4.getNumber());
                //producto5.setCantidad(num5);
                break;
            case R.id.button_adder5:
                num5 = Integer.parseInt(button5.getNumber());
                //producto6.setCantidad(num6);
                break;
            case R.id.button_adder6:
                num6 = Integer.parseInt(button6.getNumber());
                //producto4.setCantidad(num4);
                break;
            case R.id.button_adder7:
                num7 = Integer.parseInt(button7.getNumber());
                //producto7.setCantidad(num6);
                break;
            case R.id.button_adder8:
                num8 = Integer.parseInt(button8.getNumber());
                //producto8.setCantidad(num8);
                //Toast.makeText(this, "ButtonAdder 8 "+num8, Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void onComplete(@NonNull Task<Void> task) {
        if(task.isSuccessful()){
            Toast.makeText(AgregarOrden.this, "Orden añadida con éxito..", Toast.LENGTH_SHORT).show();
            finish();
        }else{
            String message = task.getException().toString();
            Toast.makeText(AgregarOrden.this, "Error: " + message, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        mesaString = adapterView.getItemAtPosition(i).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}