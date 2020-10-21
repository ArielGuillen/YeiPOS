package com.example.yeipos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yeipos.modelos.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AgregarUsuarios extends AppCompatActivity implements View.OnClickListener {

    private Button buttonAddUser;
    private TextView txtAddUserN;
    private TextView txtAddEmail;
    private TextView txtAddPsw1;
    private TextView txtAddPsw2;
    

    public FirebaseAuth mAuth;
    public ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_usuarios);
        this.setTitle(R.string.addUser);

        buttonAddUser = findViewById(R.id.buttonAddUser);
        txtAddUserN = findViewById(R.id.txtAddUserN);
        txtAddEmail = findViewById(R.id.txtAddUserEmail);
        txtAddPsw1 = findViewById(R.id.txtAddPassword);
        txtAddPsw2 = findViewById(R.id.txtConfirmPassword);

        mAuth = FirebaseAuth.getInstance();

        //Obtener valores de modificación
        String name = getIntent().getStringExtra("name");
        if ( name != null)
            txtAddUserN.setText(name);
        String email = getIntent().getStringExtra("email");
        if ( email != null)
            txtAddEmail.setText(email);

        buttonAddUser.setOnClickListener( this);
    }
    
        public void registrarUsuario( ) {
            if( validaCamposVacios() ) {

                final String psw1 = String.valueOf(txtAddPsw1.getText()).trim();
                String psw2 = String.valueOf(txtAddPsw2.getText()).trim();
                if (psw1.length() > 5){
                    if (psw1.equals(psw2)) {
                        final String uName = String.valueOf(txtAddUserN.getText()).trim();
                        final String email = String.valueOf(txtAddEmail.getText()).trim();

                        mAuth.createUserWithEmailAndPassword(email, psw1)
                                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {

                                            Toast.makeText(AgregarUsuarios.this, "Se ha registrado el usuario", Toast.LENGTH_SHORT).show();
                                            DatabaseReference prodReference = FirebaseDatabase.getInstance().getReference();
                                            Usuario usuario = new Usuario(uName, email, psw1);
                                            prodReference.child("usuario").child(uName).setValue(usuario);
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

    @Override
    public void onClick( View view ){
        registrarUsuario();
    }

    public boolean validaCamposVacios(){

        String [] campos = new String[4];
        campos[0] = txtAddPsw2.getText().toString();
        campos[1] = txtAddEmail.getText().toString();
        campos[2] = txtAddPsw1.getText().toString();
        campos[3] = txtAddUserN.getText().toString();

        return !campos[0].equals("") && !campos[1].equals("") && !campos[2].equals("") && !campos[3].equals("");

    }

}