package com.example.proyectofinal;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Fragmento que maneja los productos añadidos a la cesta de la compra
 */
public class CartFragment extends Fragment {
    private Button boton_realizar_pedido, boton_seguir_comprando, button_ir_comprar;
    private TextView text_subtotal, precio_subtotal, text_envio, precio_envio, text_total, precio_total, texto_cesta_vacia;
    private List<Product> cartList;

    /**
     * Manejamos los botones y los textView a mostrar
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return la vista creada
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);

        ProgressBar progressBar = view.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        // Barra superior
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);


        // add back arrow to toolbar
        if (((AppCompatActivity) getActivity()).getSupportActionBar() != null){
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Cesta");
        }

        /* Botones con cesta no vacia */
        boton_realizar_pedido = view.findViewById(R.id.boton_realizar_pedido);
        boton_seguir_comprando = view.findViewById(R.id.boton_seguir_comprando);

        /* Botones con cesta vacia */
        button_ir_comprar = view.findViewById(R.id.button_ir_comprar);
        texto_cesta_vacia = view.findViewById(R.id.texto_cesta_vacia);

        /* Elementos de texto */
        text_subtotal = view.findViewById(R.id.text_subtotal);
        precio_subtotal = view.findViewById(R.id.text_precio_subtotal);
        text_envio = view.findViewById(R.id.text_envio);
        precio_envio = view.findViewById(R.id.precio_envio);
        text_total = view.findViewById(R.id.text_total);
        precio_total = view.findViewById(R.id.text_precio_total);


        /* Boton de realizar pedido */
        boton_realizar_pedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth mAuth = FirebaseAuth.getInstance();
                // Si estoy logeado
                if(mAuth.getCurrentUser() != null) {
                    Intent intent = new Intent(getContext(), OrderActivity.class);
                    startActivity(intent);
                } else {
                    // Si no estoy logeado
                    Intent intent = new Intent(getContext(), GuestActivity.class);
                    startActivity(intent);
                }
            }
        });

        /* Boton de seguir comprando */
        boton_seguir_comprando.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        /* Boton de ir a comprar si la cesta esta vacía */
        button_ir_comprar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        /* Recycler View */
        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewCart);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        /* Cargamos los productos en la cesta de compra de Shared Preferences */
        loadData();

        /* Si la cesta no tiene productos */
        if(cartList.isEmpty()) {
            // inflate el layout vacio para el carro de compra
            recyclerView.setVisibility(View.GONE);
            boton_realizar_pedido.setVisibility(View.GONE);
            boton_seguir_comprando.setVisibility(View.GONE);
            text_subtotal.setVisibility(View.GONE);
            precio_subtotal.setVisibility(View.GONE);
            text_envio.setVisibility(View.GONE);
            precio_envio.setVisibility(View.GONE);
            text_total.setVisibility(View.GONE);
            precio_total.setVisibility(View.GONE);
        } else {
            button_ir_comprar.setVisibility(View.GONE);
            texto_cesta_vacia.setVisibility(View.GONE);
        }

        /* Adaptador que muestra los productos */
        CartAdapter adapter = new CartAdapter(getContext(), cartList);
        recyclerView.setAdapter(adapter);

        /* Obtenemos el precio total de los productos */
        obtenerPrecioTotal();

        progressBar.setVisibility(View.GONE);

        return view;
    }

    /**
     * Metodo para obtener el precio total de la cesta de la compra
     */
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


        text_subtotal.setContentDescription("Precio subtotal" + precio_subtotal.getText().toString() + "euros");
        text_envio.setContentDescription("Precio envio " + text_envio.getText().toString() + "euros");
        text_total.setContentDescription("Precio total" + text_total.getText().toString() + "euros");

    }

    /**
     * Metodo para cargar en el ArrayList los productos de la cesta
     */
    private void loadData() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        Gson gson = new Gson();
        String json = prefs.getString("Products", null);

        if (json == null) {
            Toast.makeText(getContext() ,"El Json cargado está vacio",Toast.LENGTH_LONG).show();
        } else {
            Type type = new TypeToken<ArrayList<Product>>() {}.getType();
            cartList  = gson.fromJson(json, type);
        }

        if (cartList == null) {
            cartList = new ArrayList<>();
        }
    }
}