package com.example.yeipos.ui.administrar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yeipos.MainActivity;
import com.example.yeipos.R;
import com.example.yeipos.RegistroDeVentas;
import com.example.yeipos.users.AgregarUsuarios;
import com.example.yeipos.users.ListElement;
import com.example.yeipos.viewholders.AdapterUsuario;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AdministrarFragment extends Fragment {

    private RecyclerView recyclerView;
    private AdapterUsuario adaptador;
    AppCompatActivity activity;

    private boolean user = true;
    private ArrayList<ListElement> elements;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_administrar, container, false);


        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.topAppBar);
        activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
//        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activity.getSupportActionBar().setTitle("Administrar usuarios");
        //setHasOptionsMenu(true);
        recyclerView = root.findViewById( R.id.listRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager( getActivity() ) );


        loadList();

        FloatingActionButton fab = root.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if( user ) {
                    Intent intent = new Intent(getActivity(), AgregarUsuarios.class);
                    startActivity(intent);
                }
            }
        });

        return root;
    }

    private void loadList() {

        elements = new ArrayList<ListElement>();
        final DatabaseReference prodReference = FirebaseDatabase.getInstance().getReference();
        prodReference.child("usuario").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if( snapshot.exists() ){
                    for (DataSnapshot dataS : snapshot.getChildren()) {
                        Log.e("Datos: ", "" + dataS.getValue());
                        String user = String.valueOf( dataS.child("name").getValue() );
                        String mail = String.valueOf( dataS.child("correo").getValue() );
                        Log.e("Nombre: ", "" + user );
                        Log.e("Email: ", "" + mail );
                        elements.add(new ListElement(user, mail));
                    }
                }
                adaptador = new AdapterUsuario( elements );
                recyclerView.setAdapter( adaptador );

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState){
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }
    @Override
    public void onCreateOptionsMenu(@Nullable Menu menu, MenuInflater inflater) {
        inflater.inflate( R.menu.menu_add, menu );
        super.onCreateOptionsMenu(menu, inflater);
    }

//    @SuppressLint("NonConstantResourceId")
//    public boolean onOptionsItemSelected(MenuItem menuItem ){
//        Intent intent;
//        switch ( menuItem.getItemId() ){
//            case R.id.itemUsers:
//                activity.setTitle( R.string.icUser);
//                break;
//            case R.id.itemProducts:
//                activity.setTitle( R.string.icProd);
//                break;
//            case R.id.itemVentas:
//                intent = new Intent(activity, RegistroDeVentas.class);
//                startActivity(intent);
//                break;
//            case android.R.id.home:
//                intent = new Intent(activity, MainActivity.class );
//                startActivity( intent );
//                break;
//        }
//        return true;
//    }
}