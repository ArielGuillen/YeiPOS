package com.example.yeipos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.yeipos.ui.home.HomeFragment;
import com.example.yeipos.ui.inventario.InventarioFragment;
import com.example.yeipos.ui.administrar.AdministrarFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView navView;
    private Toolbar myToolBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(navListener);

        //--------Barra de Herramientas--------
        myToolBar = findViewById( R.id.topAppBar );
        setSupportActionBar( myToolBar );

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
    }


    private View.OnClickListener myClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
        }
    };

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            Fragment selectedFragment = null;

            switch(item.getItemId()){
                case R.id.navigation_ordenes:
                    selectedFragment = new HomeFragment();
                    break;
                case R.id.navigation_inventario:
                    selectedFragment = new InventarioFragment();
                    break;
                case R.id.navigation_administrar:
                    selectedFragment = new AdministrarFragment();
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
            return true;
        }
    };


    public boolean onCreateOptionsMenu ( Menu menu ) {
        getMenuInflater().inflate(R.menu.menu_add, menu);
        return true;
    }
    @SuppressLint("NonConstantResourceId")
    public boolean onOptionsItemSelected(MenuItem menuItem ){
        Intent intent;
        switch ( menuItem.getItemId() ){
            case R.id.itemHelp:
                intent = new Intent(MainActivity.this, Ayuda.class );
                startActivity( intent );
                break;
            case R.id.itemRegVent:
                intent = new Intent(MainActivity.this, RegistroDeVentas.class );
                startActivity( intent );
                break;
            case R.id.itemRegOrden:
                intent = new Intent(MainActivity.this, RegistroDeOrdenesTerminadas.class);
                startActivity(intent);
                break;
            case android.R.id.home:
                intent = new Intent(MainActivity.this, MainActivity.class );
                startActivity( intent );
                break;
        }
        return true;
    }
}
