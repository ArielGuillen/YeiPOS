package com.example.yeipos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class Ayuda extends AppCompatActivity {

    private Toolbar myToolBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ayuda);

        //--------Barra de Herramientas--------
        myToolBar = findViewById( R.id.topAppBar );
        setSupportActionBar( myToolBar );
    }

    public boolean onCreateOptionsMenu ( Menu menu ) {
        getMenuInflater().inflate(R.menu.menu_add, menu);

        return true;
    }
    @SuppressLint("NonConstantResourceId")
    public boolean onOptionsItemSelected(MenuItem menuItem ){
        Intent intent;
        switch ( menuItem.getItemId() ){
            case R.id.itemHelp:
                intent = new Intent(Ayuda.this, Ayuda.class );
                startActivity( intent );
                break;
            case android.R.id.home:
                intent = new Intent(Ayuda.this, MainActivity.class );
                startActivity( intent );
                break;
        }
        return true;
    }
}