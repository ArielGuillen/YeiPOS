package com.example.yeipos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.yeipos.ui.home.HomeFragment;
import com.example.yeipos.ui.inventario.InventarioFragment;
import com.example.yeipos.ui.administrar.AdministrarFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView navView;
    private Toolbar myToolBar;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(navListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();

//        fab = findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                OpenAgregarOrden();
//            }
//        });

        myToolBar = (Toolbar) findViewById(R.id.topAppBar);
        setSupportActionBar(myToolBar);
        myToolBar.setNavigationOnClickListener(myClickListener);
    }

    private View.OnClickListener myClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
        }
    };
    public void OpenAgregarOrden(){
        Intent intent=new Intent(this, AgregarOrden.class);
        startActivity(intent);
    }

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
}
