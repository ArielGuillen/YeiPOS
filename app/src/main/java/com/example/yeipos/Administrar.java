package com.example.yeipos;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;

import java.util.ArrayList;
import java.util.List;

public class Administrar extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ListAdapter listAdapter;

    private boolean user = true;
    private List<ListElement> elements;

    //---------------------------------------Methods Override------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administrar);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled( true );

        init();


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if( user ) {
                    Intent intent = new Intent(Administrar.this, AgregarUsuarios.class);
                    startActivity(intent);
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate( R.menu.menu_add, menu );
        return true;
    }


    @SuppressLint("NonConstantResourceId")
    public boolean onOptionsItemSelected(MenuItem menuItem ){
        Intent intent;
        switch ( menuItem.getItemId() ){
            case R.id.itemUsers:
                this.setTitle( R.string.icUser);
                break;
            case R.id.itemProducts:
                this.setTitle( R.string.icProd);
                break;
            case R.id.itemVentas:
                intent = new Intent(Administrar.this, RegistroDeVentas.class);
                startActivity(intent);
                break;
            case android.R.id.home:
                intent = new Intent(Administrar.this, MainActivity.class );
                startActivity( intent );
                break;
        }
        return true;
    }


    //-------------------------------Activity Methods-------------------------------
    public void init(){

        loadList();
        buildRecyclerView();
    }


    private void buildRecyclerView() {

        listAdapter = new ListAdapter( elements, this);
        recyclerView = findViewById( R.id.listRecyclerView);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager( this ) );
        recyclerView.setAdapter( listAdapter );

        listAdapter.setOnClickListener(new ListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                changeItem( position );
            }

            @Override
            public void onDeleteClick(int position) {

                removeItem( position );
            }

            @Override
            public void onEditClick(int position) {

                editItem( position );
            }


        });


    }

    public void loadList(){

        elements = new ArrayList<>();
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        db.getReference().child("usuario").addValueEventListener(new ValueEventListener() {
                  @Override
                  public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                      if( dataSnapshot.exists() ) {
                          elements = new ArrayList<>();
                          for (DataSnapshot snapshot : dataSnapshot.getChildren() ) {
                              String nu = String.valueOf( snapshot.child("name").getValue() );
                              String em = String.valueOf( snapshot.child("correo").getValue() );
                              ListElement usuario = new ListElement( nu, em );
                              elements.add(usuario);
                          }
                          listAdapter.notifyDataSetChanged();
                      }
                  }

                  @Override
                  public void onCancelled(@NonNull DatabaseError error) {

                  }

        });
        /*
        elements.add( new ListElement("#c62828",  "Pedro1@correo.com"));
        elements.add( new ListElement("#c62828","Julio12@correo.com"));
        elements.add( new ListElement("#c62828",  "MaríaDB@correo.com"));*/

    }


    //---------------------------RecyclerView Events--------------------------------------

    public void removeItem(final int position  ){
        AlertDialog.Builder alert = new AlertDialog.Builder( Administrar.this );
        alert.setMessage("¿Desea eliminar el usuario " +elements.get( position ).getEmail() + "?")
        .setCancelable(false)
        .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                elements.remove(position);
                listAdapter.notifyItemRemoved( position );
            }
        })
        .setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog titulo = alert.create();
        titulo.setTitle("Eliminar Usuario");
        titulo.show();
    }
    public void changeItem( int position ){
        AlertDialog.Builder alert = new AlertDialog.Builder( Administrar.this );
        alert.setMessage("Nombre: " + elements.get( position ).getName() +
                        "\nCorreo electrónico: " + elements.get( position ).getEmail() )
                .setCancelable(false)
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        AlertDialog titulo = alert.create();
        titulo.setTitle("Datos de Usuario");
        titulo.show();
    }


    public void editItem( final int position ){
        AlertDialog.Builder alert = new AlertDialog.Builder( Administrar.this );
        alert.setMessage("¿Desea modificar los datos del usuario " +elements.get( position ).getName() + "?")
                .setCancelable(false)
                .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String name = elements.get( position ).getName();
                        String email = elements.get( position ).getEmail();
                        Intent intent = new Intent(Administrar.this, AgregarUsuarios.class);
                        intent.putExtra("email",  email );
                        intent.putExtra("name",  name );
                        startActivity(intent);
                        listAdapter.notifyItemChanged( position );
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        AlertDialog titulo = alert.create();
        titulo.setTitle("Modificar Usuario");
        titulo.show();
    }

}