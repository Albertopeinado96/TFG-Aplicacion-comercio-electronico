package com.example.proyectofinal;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Actividad que muestra los productos de la categoria seleccionada
 * Mostramos un toolbar con la categoria seleccionada y un recycler View con los productos
 */
public class CategoryActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<Product> productList;
    private String categoria_producto;
    private ProgressBar progressBar;

    /**
     * Método para volver hacia la actividad anterior
     * @return la actividad previa
     */
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    /**
     * Manejamos los elementos con los que interacciona el usuario
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        // Obtenemos la categoria seleccionada por el usuario
        categoria_producto = getIntent().getExtras().get("categoria").toString();

        // Barra superior
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle(categoria_producto);
        }

        // Recicler View
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Barra de progreso
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        // Lista de productos
        productList = new ArrayList<>();

        // Obtenemos los productos dentro de ArrayList
        retrieveProductsBD();
    }

    /**
     * Recuperamos los productos y los almacenamos en el ArrayList
     */
    private void retrieveProductsBD() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Products");
        reference.addValueEventListener(new ValueEventListener() {

            /**
             * El método se ejecuta si la consulta fue exitosa
             * @param dataSnapshot Snapsho de los datos consultados
             */
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds: dataSnapshot.getChildren())
                {
                    Product product = ds.getValue(Product.class);
                    if(product.getCategoria().equals(categoria_producto)) {
                        productList.add(product);
                    }

                    if(categoria_producto.equals("Todos los productos")) {
                        productList.add(product);
                    }
                }

                // Adapter
                ProductAdapter productAdapter = new ProductAdapter(getBaseContext(), productList);
                recyclerView.setAdapter(productAdapter);
                progressBar.setVisibility(View.GONE);
            }

            /**
             * El metodo se ejecuta si la consulta no fue exitosa
             * @param databaseError Error surgido
             */
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(CategoryActivity.this, databaseError.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
