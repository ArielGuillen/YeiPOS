package com.example.yeipos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.yeipos.users.AgregarUsuarios;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class login_actividad extends AppCompatActivity implements View.OnClickListener{

    Button buttonLogin, buttonSignin;
    EditText txtCorreo, txtPassword;
    String email, psw;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_actividad);

        this.buttonLogin = findViewById( R.id.buttonLogin );
        this.buttonSignin = findViewById( R.id.buttonSignin );
        this.txtCorreo = findViewById( R.id.txtUserMail );
        this.txtPassword = findViewById( R.id.txtPassword );

        mAuth = FirebaseAuth.getInstance();

        this.buttonLogin.setOnClickListener( this) ;
        this.buttonSignin.setOnClickListener( this );
    }

    public void loginUser(){

        email = txtCorreo.getText( ).toString( );
        psw = txtPassword.getText( ).toString( );
        if( validaCamposVacios() ) {
            email = String.valueOf(txtCorreo.getText()).trim();
            psw = String.valueOf(txtPassword.getText()).trim();

            mAuth.signInWithEmailAndPassword(email, psw)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Intent intent = new Intent(login_actividad.this, MainActivity.class);
                                startActivity(intent);
                                Toast.makeText(login_actividad.this, "Bienvenido", Toast.LENGTH_SHORT).show();
                                finish();

                            } else {
                                Toast.makeText(login_actividad.this, "No se pudo iniciar sesión",
                                        Toast.LENGTH_SHORT).show();
                            }

                        }
                    });


        }
        else{
            AlertDialog.Builder alert = new AlertDialog.Builder( login_actividad.this );
            alert.setMessage("Debe llenar los campos Correo y Contraseña")
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

    public boolean validaCamposVacios(){
        String c = String.valueOf( txtCorreo.getText() ).trim();
        String p = String.valueOf( txtPassword.getText() ).trim();
        if( !c.isEmpty() && !p.isEmpty()  )
            return true;
        else
            return false;
    }

    @Override
    public void onClick(View v) {
        switch ( v.getId() ) {
            case R.id.buttonLogin:
                loginUser();
                break;
            case R.id.buttonSignin:
                Intent intent = new Intent(login_actividad.this, AgregarUsuarios.class);
                startActivity(intent);
                break;
        }
    }
}