package com.example.proyectofinal;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;

/**
 * Fragmento que maneja el fragmento del perfil
 */
public class ProfileFragment extends Fragment {

    /**
     * Mostramos el menú del perfil
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return la vista
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        // Barra superior
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);


        // add back arrow to toolbar
        if (((AppCompatActivity) getActivity()).getSupportActionBar() != null){
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Mi Cuenta");
        }

        // Comprobamos si el usuario esta logeado
        if(mAuth.getCurrentUser() != null) {
            String[] menu_perfil_opciones1 = {"Mi Perfil",
                    "Mis Pedidos",
                    "Cerrar Sesión",
                    "Politica de Privacidad",
                    "Términos y condiciones",
                    "Version 1.01"};

            ListView menu_perfil = view.findViewById(R.id.menu_perfil);

            ArrayAdapter<String> listViewAdapter = new ArrayAdapter<String>(
                    getActivity(),
                    android.R.layout.simple_expandable_list_item_1,
                    menu_perfil_opciones1
            );

            menu_perfil.setAdapter(listViewAdapter);
            menu_perfil.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    switch (i) {
                        case 0:
                            Intent intent_account = new Intent(getActivity(), MainActivity.class);
                            startActivity(intent_account);
                            break;
                        case 1:
                            Intent intent_pedidos = new Intent(getActivity(), MisPedidosActivity.class);
                            startActivity(intent_pedidos);
                            break;
                        case 2:
                            Intent intent_logout = new Intent(getActivity(), LogoutActivity.class);
                            startActivity(intent_logout);
                            break;
                    }
                }
            });
        } else {
            // Usuario no logeado
            String[] menu_perfil_opciones2 = {"Mis Pedidos",
                    "Iniciar Sesión",
                    "Registrar Usuario",
                    "Politica de Privacidad",
                    "Términos y condiciones",
                    "Version 1.01"};

            ListView menu_perfil = view.findViewById(R.id.menu_perfil);

            ArrayAdapter<String> listViewAdapter = new ArrayAdapter<String>(
                    getActivity(),
                    android.R.layout.simple_expandable_list_item_1,
                    menu_perfil_opciones2
            );

            menu_perfil.setAdapter(listViewAdapter);
            menu_perfil.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    switch (i) {
                        case 0:
                            Toast.makeText(getActivity(), "Debes estar logeado para ver tus pedidos", Toast.LENGTH_LONG).show();
                            Intent intent_login = new Intent(getActivity(), LoginActivity.class);
                            startActivity(intent_login);
                            break;
                        case 1:
                            Intent intent_pedidos = new Intent(getActivity(), LoginActivity.class);
                            startActivity(intent_pedidos);
                            break;
                        case 2:
                            Intent intent_registro = new Intent(getActivity(), RegisterActivity.class);
                            startActivity(intent_registro);
                            break;
                    }
                }
            });
        }
        return view;
    }
}