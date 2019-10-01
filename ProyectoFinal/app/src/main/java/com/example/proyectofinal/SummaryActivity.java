package com.example.proyectofinal;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class SummaryActivity extends AppCompatActivity {
    private ArrayList<Product> cartList;
    private TextView precio_subtotal, precio_envio, precio_total, text_name, text_apellidos;
    private TextView text_direccion, text_direccion2, text_codigopostal, text_pais, text_phone;
    private TextView text_email;
    private Button button_pay;
    private FirebaseAuth mAuth;

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);

        // Barra superior
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("Resumen del pedido");
        }

        precio_subtotal = findViewById(R.id.text_precio_subtotal);
        precio_envio = findViewById(R.id.text_precio_envio);
        precio_total = findViewById(R.id.text_precio_total);

        text_name = findViewById(R.id.textView_nombre);
        text_apellidos = findViewById(R.id.textView_apellidos);
        text_direccion = findViewById(R.id.textView_direccion);
        text_direccion2 = findViewById(R.id.textView_direccion2);
        text_codigopostal = findViewById(R.id.textView_cp);
        text_pais = findViewById(R.id.textView_country);
        text_phone = findViewById(R.id.textView_phone);
        text_email = findViewById(R.id.textView_email);

        button_pay = findViewById(R.id.button_pay);

        Intent i = getIntent();
        final Address address = (Address) i.getSerializableExtra("Address");

        loadData();
        obtenerPrecioTotal();

        text_name.setText(address.getNombre());
        text_apellidos.setText(address.getApellidos());
        text_direccion.setText(address.getDireccion());
        text_direccion2.setText(address.getDireccion2());
        text_codigopostal.setText(address.getCP());
        text_pais.setText(address.getCountry());
        text_phone.setText(address.getPhone());

        mAuth = FirebaseAuth.getInstance();
        if(mAuth.getCurrentUser() != null) {
            // Usuario logeado, utilizamos el correo del usuario
            text_email.setText(mAuth.getCurrentUser().getEmail());
        } else if(getIntent().getExtras() != null) {
            // Usuario no logeado, cogemos el correo del intent
            text_email.setText(getIntent().getExtras().get("guest_email").toString());
        }


        button_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth = FirebaseAuth.getInstance();

                if(mAuth.getCurrentUser() != null) {
                    // Usuario logeado, utilizamos el correo del usuario
                    text_email.setText(mAuth.getCurrentUser().getEmail());
                } else {
                    // Usuario no logeado, cogemos el correo del intent

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
            // do nothing
        } else {
            Type type = new TypeToken<ArrayList<Product>>() {}.getType();
            cartList  = gson.fromJson(json, type);
        }

        if (cartList == null) {
            cartList = new ArrayList<>();
        }
    }

    private void obtenerPrecioTotal() {
        DecimalFormat df = new DecimalFormat("#.00");
        int unidad = 1; // Unidades del producto, hay que controlarlo.
        float coste_subtotal = 0.0f;
        float coste_envio = 3.5f;
        float coste_total = 0.0f;

        for (int i = 0; i < cartList.size(); i++) {
            coste_subtotal += ((cartList.get(i).getPrecio()) * unidad);
        }
        coste_total = coste_subtotal + coste_envio;

        precio_subtotal.setText(getResources().getString(R.string.string_precio, df.format(coste_subtotal)));
        precio_envio.setText(getResources().getString(R.string.string_precio, df.format(coste_envio)));
        precio_total.setText(getResources().getString(R.string.string_precio, df.format(coste_total)));

    }
}
