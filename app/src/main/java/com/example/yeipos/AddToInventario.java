package com.example.yeipos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.yeipos.model.ItemInventario;
import com.example.yeipos.model.Producto;
import com.example.yeipos.ui.home.HomeFragment;
import com.example.yeipos.ui.inventario.InventarioFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AddToInventario extends AppCompatActivity implements AdapterView.OnItemSelectedListener, com.google.android.gms.tasks.OnCompleteListener<Void> {
    //campos del objeto a guardar
    private String nombre, cantidad, precio, guardarFecha, guardarHora, categoria;
    //variables para obtener los elementos del xml activity_inventario
    private EditText inputNombreProd, inputCantidadProd, inputPrecioProd;
    private Spinner spinnerCategoria;
    //llave para asignar al child
    private String id;
    //boton
    private Button addToInv;
    //Referencia a la bd
    private DatabaseReference productoReference;
    ItemInventario itemInventario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_inventario);

        productoReference = FirebaseDatabase.getInstance().getReference();
        addToInv = (Button) findViewById(R.id.add_button_inventario);
        inputNombreProd = (EditText) findViewById(R.id.txt_nombreProducto);
        inputCantidadProd = (EditText) findViewById(R.id.txt_cantidadProd);
        inputPrecioProd = (EditText) findViewById(R.id.txt_precioProducto);
        spinnerCategoria = (Spinner) findViewById(R.id.spinner_categoria);

        //get seleccion del spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.categorias, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinnerCategoria.setAdapter(adapter);
        spinnerCategoria.setOnItemSelectedListener(this);
        //spinner donesso

        itemInventario = new ItemInventario();

        addToInv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validarDatos();
            }
        });

    }

    private void validarDatos() {
        nombre = inputNombreProd.getText().toString();
        cantidad = inputCantidadProd.getText().toString();
        precio = inputPrecioProd.getText().toString();
        
        if(TextUtils.isEmpty(nombre)){
            Toast.makeText(this, "Nombre requerido", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(cantidad)) {
            Toast.makeText(this, "Cantidad requerida", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(precio)) {
            Toast.makeText(this, "Precio requerido", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(categoria)||categoria == ""||categoria=="Seleccionar") {
            Toast.makeText(this, "Categoría requerida", Toast.LENGTH_SHORT).show();
        } else {
            GuardarInformacionProducto();
        }
    }

    private void GuardarInformacionProducto() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("dd-MM-yyyy");
        guardarFecha = currentDate.format(calendar.getTime());
        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        guardarHora = currentTime.format(calendar.getTime());
        id = guardarFecha+guardarHora;
        //productRandomKey = GuardarFecha + GuardarHora;
        itemInventario.setNombre(nombre);
        itemInventario.setCantidad(cantidad);
        itemInventario.setCategoria(categoria);
        itemInventario.setGuardarFecha(guardarFecha);
        itemInventario.setGuardarHora(guardarHora);
        itemInventario.setId(id);
        itemInventario.setPrecio(precio);

        //Log.i("categoria ", itemInventario.getCategoria());
        switch (categoria){
            case "comidas":
                productoReference.child("inventario").child(id).setValue(itemInventario);
                productoReference.child("comidas").child(id).setValue(itemInventario).addOnCompleteListener(this);
                //productoReference.child("comidas").push().setValue(itemInventario).addOnCompleteListener(this);
                //Log.i("comida: ",itemInventario.toString());
                break;
            case "bebidas":
                productoReference.child("inventario").child(id).setValue(itemInventario);
                productoReference.child("bebidas").child(id).setValue(itemInventario).addOnCompleteListener(this);
                //Log.i("bebida: ",itemInventario.toString());
                break;
            case "otros":
                productoReference.child("inventario").child(id).setValue(itemInventario).addOnCompleteListener(this);
                //Log.i("otro: ",itemInventario.toString());
                break;
        }

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        categoria = adapterView.getItemAtPosition(i).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onComplete(@NonNull Task<Void> task) {
        if(task.isSuccessful()){
            Intent intent = new Intent(AddToInventario.this, MainActivity.class);
            startActivity(intent);
            Toast.makeText(AddToInventario.this, "Producto añadido exitosamente..", Toast.LENGTH_SHORT).show();
        }else{
            String message = task.getException().toString();
            Toast.makeText(AddToInventario.this, "Error: " + message, Toast.LENGTH_SHORT).show();
        }

    }
}