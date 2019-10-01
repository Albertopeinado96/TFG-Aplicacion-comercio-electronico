package com.example.proyectofinal;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import java.lang.reflect.Type;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Actividad que muestra el producto y permite añadirlo a la cesta de la compra
 */
public class ProductActivity extends AppCompatActivity {
    private TextView tipo_categoria, nombre_producto, precio_producto, marca_producto, envio_producto;
    private TextView disponibilidad_producto, descripcion_producto, caracteristicas_producto;
    private RatingBar itemRatingBar;
    private Button add_product_cart;
    private ImageView imageView;
    private ArrayList<Product> cartList;

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        // Barra superior
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /* Elementos de la vista */
        tipo_categoria = findViewById(R.id.tipo_categoria);
        nombre_producto = findViewById(R.id.nombre_producto);
        itemRatingBar = findViewById(R.id.itemRatingBar);
        precio_producto = findViewById(R.id.precio_producto);
        marca_producto = findViewById(R.id.marca_producto);
        envio_producto = findViewById(R.id.text_precio_envio);
        disponibilidad_producto = findViewById(R.id.disponibilidad_producto);
        descripcion_producto = findViewById(R.id.descripcion_producto);
        caracteristicas_producto = findViewById(R.id.caracteristicas_producto);
        imageView = findViewById(R.id.imageView4);
        add_product_cart = findViewById(R.id.button_add);

        cartList = new ArrayList<>();

        // Producto a mostrar
        Intent i = getIntent();
        final Product product = (Product)i.getSerializableExtra("Product");

        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        DecimalFormat df = new DecimalFormat("#.00");

        tipo_categoria.setText(product.getCategoria());
        nombre_producto.setText(product.getNombre());
        itemRatingBar.setRating(product.getValoracion());
        precio_producto.setText(getResources().getString(R.string.string_precio, df.format(product.getPrecio())));
        marca_producto.setText(product.getMarca());
        envio_producto.setText("Envio gratis - Envio disponible en 3-5 dias");
        descripcion_producto.setText(product.getDescripcion());
        caracteristicas_producto.setText(product.getCaracteristicas());
        Picasso.get().load(product.getImage()).into(imageView);

        nombre_producto.setContentDescription("Nombre del producto " + product.getNombre());
        itemRatingBar.setContentDescription("Valoracion del producto" + product.getValoracion());
        precio_producto.setContentDescription("Precio del producto" + product.getPrecio() + "euros");

        if(product.isDisponibilidad() == true) {
            disponibilidad_producto.setText("¡En stock!");
        } else {
            disponibilidad_producto.setText("Este producto no se encuentra en stock");
        }

        add_product_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(product.isDisponibilidad()) {

                    // Leer el arraylist de shared preferences
                    loadData();

                    // Añadimos el producto
                    cartList.add(product);
                    Snackbar.make(view, "Se ha añadido el producto a la cesta", Snackbar.LENGTH_LONG).show();

                    // Escribir el arraylist en shared preferences
                    saveData();
                } else {
                    Snackbar.make(view, "El producto no se encuentra disponible actualmente", Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }

    /**
     * Cargamos los datos de Shared Preferences
     */
    private void loadData() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        Gson gson = new Gson();
        String json = prefs.getString("Products", null);

        if (json == null) {

        } else {
            Type type = new TypeToken<ArrayList<Product>>() {}.getType();
            cartList  = gson.fromJson(json, type);
        }

        if (cartList == null) {
            cartList = new ArrayList<>();
        }
    }

    /**
     * Guardamos los datos de Shared Preferences
     */
    private void saveData() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(cartList);
        editor.putString("Products", json);
        editor.apply();
    }

    /**
     * Borramos todos los datos de Shared Preferences
     */
    private void deleteData() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        prefs.edit().clear().commit();
    }
}
