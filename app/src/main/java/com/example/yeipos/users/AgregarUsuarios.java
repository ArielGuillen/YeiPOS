package com.example.yeipos.users;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yeipos.MainActivity;
import com.example.yeipos.R;
import com.example.yeipos.login_actividad;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AgregarUsuarios extends AppCompatActivity implements View.OnClickListener {

    private Button buttonAddUser;
    private TextView txtAddUserN;
    private TextView txtAddEmail;
    private TextView txtAddPsw1;
    private TextView txtAddPsw2;

    private boolean edit;
    private String changeName;
    private String changeId;
    private String changePsw;
    private String changeEmail;
    private String login;

    public FirebaseAuth mAuth;
    public ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_usuarios);
        this.setTitle(R.string.addUser);
        edit = false;
        buttonAddUser = findViewById(R.id.buttonAddUser);
        txtAddUserN = findViewById(R.id.txtAddUserN);
        txtAddEmail = findViewById(R.id.txtAddUserEmail);
        txtAddPsw1 = findViewById(R.id.txtAddPassword);
        txtAddPsw2 = findViewById(R.id.txtConfirmPassword);

        //Obtener valores de modificación
        login = getIntent().getStringExtra("login");
        if( login != null )
            login = "login";
        changeId = getIntent().getStringExtra("id");
        changePsw = getIntent().getStringExtra("pswd");
        if ( changeId != null ) {
            edit = true;
        }
        changeName = getIntent().getStringExtra("name");
        if ( changeName != null )
            txtAddUserN.setText(changeName);
        changeEmail = getIntent().getStringExtra("email");
        if ( changeEmail != null )
            txtAddEmail.setText(changeEmail);

        if ( edit ){
            buttonAddUser.setText( R.string.actUser );
            TextInputLayout layout1 = findViewById( R.id.layouttext );
            TextInputLayout layout2 = findViewById( R.id.layouttext2 );
            layout1.setVisibility( View.GONE );
            layout2.setVisibility( View.GONE );
        }
        mAuth = FirebaseAuth.getInstance();


        buttonAddUser.setOnClickListener( this );
    }
    
        public void registrarUsuario( ) {
            if( validaCamposVacios() ) {

                final String psw1 = String.valueOf(txtAddPsw1.getText()).trim();
                String psw2 = String.valueOf(txtAddPsw2.getText()).trim();
                if (psw1.length() > 5){
                    if (psw1.equals(psw2)) {
                        final String uName = String.valueOf(txtAddUserN.getText()).trim();
                        final String email = String.valueOf(txtAddEmail.getText()).trim().toLowerCase();

                        mAuth.createUserWithEmailAndPassword(email, psw1)
                                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {

                                            Toast.makeText(AgregarUsuarios.this, "Se ha registrado el usuario", Toast.LENGTH_LONG).show();
                                            DatabaseReference prodReference = FirebaseDatabase.getInstance().getReference();
                                            String id = UUID.randomUUID().toString();
                                            ListElement usuario = new ListElement( id,uName, email, psw1);

                                            prodReference.child("usuario").child(id).setValue(usuario);
                                            finish();
                                        }
                                        else {
                                            if (task.getException() instanceof FirebaseAuthUserCollisionException)
                                                Toast.makeText(AgregarUsuarios.this, "El usuario ya existe",
                                                        Toast.LENGTH_SHORT).show();
                                            Toast.makeText(AgregarUsuarios.this, "No se pudo registrar el usuario",
                                                    Toast.LENGTH_SHORT).show();
                                        }

                                    }
                                });

                    } else {
                        AlertDialog.Builder alert = new AlertDialog.Builder(AgregarUsuarios.this);
                        alert.setMessage("Verifique que las contraseñas sean iguales")
                                .setCancelable(false)
                                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                });
                        AlertDialog titulo = alert.create();
                        titulo.setTitle("Las contraseñas no coinciden");
                        titulo.show();
                    }
                }
                else{
                    AlertDialog.Builder alert = new AlertDialog.Builder( AgregarUsuarios.this );
                    alert.setMessage("La contraseña debe contener al menos 6 caracteres")
                            .setCancelable(false)
                            .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                    AlertDialog titulo = alert.create();
                    titulo.setTitle("Contraseña muy corta");
                    titulo.show();
                }
            }
            else{
                AlertDialog.Builder alert = new AlertDialog.Builder( AgregarUsuarios.this );
                alert.setMessage("Debe llenar todos los campos")
                        .setCancelable(false)
                        .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                AlertDialog titulo = alert.create();
                titulo.setTitle("Campos Vacíos");
                titulo.show();
            }
        }


    public void editarUsuario( ) {
        final String uName = String.valueOf(txtAddUserN.getText()).trim();
        final String email = String.valueOf(txtAddEmail.getText()).trim();
            if( !uName.isEmpty() && !email.isEmpty() ) {
                final DatabaseReference dbReference = FirebaseDatabase.getInstance().getReference().child("usuario");
                dbReference.child(changeId).child("name").setValue(uName);
                dbReference.child(changeId).child("email").setValue(email);

                mAuth.signInWithEmailAndPassword( changeEmail, changePsw );
                FirebaseUser user = mAuth.getCurrentUser();
                user.updateEmail( email ).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(AgregarUsuarios.this, "Se han actualizado datos del usuario", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(AgregarUsuarios.this, MainActivity.class);
                        startActivity(intent);
                    }
                });
        }
        else{
            AlertDialog.Builder alert = new AlertDialog.Builder( AgregarUsuarios.this );
            alert.setMessage("Debe llenar todos los campos")
                    .setCancelable(false)
                    .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
            AlertDialog titulo = alert.create();
            titulo.setTitle("Campos Vacíos");
            titulo.show();
        }
    }
    @Override
    public void onClick( View view ){
        if( edit )
            editarUsuario();
        else
            registrarUsuario();
    }

    public boolean validaCamposVacios(){

        String [] campos = new String[4];
        campos[0] = txtAddUserN.getText().toString();
        campos[1] = txtAddEmail.getText().toString();
        campos[2] = txtAddPsw1.getText().toString();
        campos[3] = txtAddPsw2.getText().toString();
        if ( campos[0].isEmpty() )
            txtAddUserN.setError("Requerido");
        if ( campos[1].isEmpty() )
            txtAddEmail.setError("Requerido");
        if ( campos[2].isEmpty() )
            txtAddPsw1.setError("Requerido");
        if ( campos[3].isEmpty() )
            txtAddPsw2.setError("Requerido");
        return !campos[0].equals("") && !campos[1].equals("") && !campos[2].equals("") && !campos[3].equals("");

    }

}