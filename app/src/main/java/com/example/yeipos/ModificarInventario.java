package com.example.yeipos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yeipos.model.ItemInventario;
import com.example.yeipos.model.Producto;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ModificarInventario extends AppCompatActivity implements AdapterView.OnItemSelectedListener, com.google.android.gms.tasks.OnCompleteListener<Void> {

    private TextView textMessage;
    ItemInventario itemInventario;
    private EditText editTextNombre, editTextCantidad,editTextPrecio;
    private Spinner spinnerCategoria;
    private String intentMessage;

    private Button modificarBoton;

    private String nombre;
    private String cantidad;
    private String precio;
    private String guardarFecha;
    private String guardarHora;
    private String categoria;
    private String id, oldStringKey;

    private DatabaseReference productoReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_inventario);
        try {
            intentMessage = getIntent().getStringExtra("message");
        } catch (Exception e){
            e.printStackTrace();
        }

        productoReference = FirebaseDatabase.getInstance().getReference();

        modificarBoton = (Button) findViewById(R.id.modificar_boton);
        editTextNombre = (EditText) findViewById(R.id.txt_nombreProducto);
        editTextCantidad = (EditText) findViewById(R.id.txt_cantidadProd);
        editTextPrecio = (EditText) findViewById(R.id.txt_precioProducto);
        spinnerCategoria = (Spinner) findViewById(R.id.spinner_categoria);

        itemInventario = new ItemInventario();
        //get seleccion del spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.categorias, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinnerCategoria.setAdapter(adapter);
        spinnerCategoria.setOnItemSelectedListener(this);

        //textMessage.setText(intentMessage);
        
        getProductDetails(intentMessage);

        modificarBoton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validarDatos();
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        categoria = adapterView.getItemAtPosition(i).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private void getProductDetails(String intentMessage) {
        DatabaseReference productRef = FirebaseDatabase.getInstance().getReference().child("inventario");
        productRef.child(intentMessage).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    ItemInventario item = snapshot.getValue(ItemInventario.class);
                    editTextNombre.setText(item.getNombre());
                    editTextCantidad.setText(item.getCantidad());
                    editTextPrecio.setText(item.getPrecio());
                    oldStringKey = item.getId();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void validarDatos() {
        nombre = editTextNombre.getText().toString();
        cantidad = editTextCantidad.getText().toString();
        precio = editTextPrecio.getText().toString();

        if(TextUtils.isEmpty(nombre)){
            Toast.makeText(this, "Nombre requerido", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(cantidad)) {
            Toast.makeText(this, "Cantidad requerida", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(precio)) {
            Toast.makeText(this, "Precio requerido", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(categoria)||categoria.equals("")||categoria.equals("Seleccionar")) {
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

        productoReference.child("inventario").child(itemInventario.getId()).setValue(itemInventario).addOnCompleteListener(this);
    }

    @Override
    public void onComplete(@NonNull Task<Void> task) {
        if(task.isSuccessful()){
            productoReference.child("inventario").child(oldStringKey).removeValue();
            Intent intent = new Intent(ModificarInventario.this, MainActivity.class);
            startActivity(intent);
            Toast.makeText(ModificarInventario.this, "Producto actualizado exitosamente..", Toast.LENGTH_SHORT).show();
        }else{
            String message = task.getException().toString();
            Toast.makeText(ModificarInventario.this, "Error: " + message, Toast.LENGTH_SHORT).show();
        }
    }
}