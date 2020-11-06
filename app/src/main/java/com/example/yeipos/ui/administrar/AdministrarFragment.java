package com.example.yeipos.ui.administrar;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yeipos.R;
import com.example.yeipos.users.AgregarUsuarios;
import com.example.yeipos.users.ListElement;
import com.example.yeipos.viewholders.AdapterUsuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AdministrarFragment extends Fragment {

    private RecyclerView recyclerView;
    private AdapterUsuario adaptador;
    private DatabaseReference dbReference;

    AppCompatActivity activity;

    private final boolean user = true;
    private ArrayList<ListElement> elements;

    public AdministrarFragment() {
    }


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_administrar, container, false);
        dbReference = FirebaseDatabase.getInstance().getReference();
        activity = (AppCompatActivity) getActivity();
        activity.getSupportActionBar().setTitle(R.string.adminUsr);
        recyclerView = root.findViewById( R.id.listRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager( getActivity() ) );
        setHasOptionsMenu(true);

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
        elements = new ArrayList<>();
        dbReference.child("usuario").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                elements.clear();
                if( snapshot.exists() ){
                    for (DataSnapshot dataS : snapshot.getChildren()) {
                        String id = String.valueOf( dataS.child("id").getValue() );
                        String user = String.valueOf( dataS.child("name").getValue() );
                        String mail = String.valueOf( dataS.child("email").getValue() );
                        String psw = String.valueOf( dataS.child("password").getValue() );
                        elements.add(new ListElement( id, user, mail, psw ));
                    }
                }
                adaptador = new AdapterUsuario( elements );
                recyclerView.setAdapter( adaptador );
                adaptador.setOnItemClickListener(new AdapterUsuario.OnItemClickListener() {
                    @Override
                    public void onItemLongClick(int position) {
                        longClick( position );
                    }

                    @Override
                    public void onDeleteClick(int position) {
                        removeItem( position );
                    }

                    @Override
                    public void onEditClick(int position) {
                        editItem( position ) ;
                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }


    public void longClick( int position ){
        ListElement element = elements.get( position );
        AlertDialog.Builder alert = new AlertDialog.Builder( getContext() );
        alert.setMessage(" -Nombre: " + element.getName()
                        + "\n -Correo Eletrónico: "+ element.getEmail() )
                .setCancelable(false)
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        AlertDialog titulo = alert.create();
        titulo.setTitle("Información de Usuario");
        titulo.show();
    }
    /*public void prueba( int position, String s){
        elements.get(position).setName( s );
        adaptador.notifyItemChanged( position );
    }*/
    
    public void editItem( int position ){
        String id = elements.get(position).getId();
        String name = elements.get(position).getName();
        String email = elements.get(position).getEmail();
        String pswd = elements.get(position).getPassword();
        Intent intent = new Intent( getContext(), AgregarUsuarios.class);
        intent.putExtra("id",id );
        intent.putExtra("name",name );
        intent.putExtra("email",email );
        intent.putExtra("pswd", pswd );
        startActivity(intent);
    }
    public void removeItem(final int position ){
        final ListElement element = elements.get( position );
        Log.e("Email1: ", "" + element.getEmail() );
        Log.e("Pass1: ", "" + element.getPassword());
        AlertDialog.Builder alert = new AlertDialog.Builder( getContext() );
        alert.setMessage(" ¿Desea eliminar el usuario " +elements.get( position ).getName() +"?" )
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dbReference.child("usuario").child(element.getId() ).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
//                                Log.e("Email2: ", "" + element.getEmail() );
//                                Log.e("Pass2: ", "" + element.getPassword());
//                                FirebaseAuth mAuth = FirebaseAuth.getInstance();
//                                FirebaseUser user = mAuth.getCurrentUser();
//                                String email = user.getEmail();
//                                String password = buscarUsuario(email);
//                                mAuth.signOut();
//
//                                mAuth.signInWithEmailAndPassword(element.getEmail(), element.getPassword());
//                                user = mAuth.getCurrentUser();
//                                if(user!=null){
//                                    user.delete();
//                                }
                                Toast.makeText(getContext(), "Usuario eliminado", Toast.LENGTH_SHORT).show();
                                //mAuth.signInWithEmailAndPassword(email, password);
                            }
                        });
                    }
                });
        AlertDialog titulo = alert.create();
        titulo.setTitle("Confirmar eliminación");
        titulo.show();
    }

    private String buscarUsuario(String email) {
        String psw = "";
        ListElement aux = new ListElement();

        for(int i = 0; i < elements.size(); i++){
            aux = elements.get(i);
            if(aux.getEmail().equals(email)){
                psw = aux.getPassword();
                break;
            }
        }
        return psw;
    }
}