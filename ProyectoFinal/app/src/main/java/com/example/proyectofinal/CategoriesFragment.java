package com.example.proyectofinal;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

/**
 * Fragmento que permite al usuario selecionar las categorias de las que
 * desea buscar productos
 */
public class CategoriesFragment extends Fragment {
    /**
     * Mostramos las categorias y manejamos los clicks en los items
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return retornamos la vista
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_categories, container, false);

        // Toolbar
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.setTitle("Categorias");

        // Categorias
        String[] categorias = {"Placas Base",
                                        "Procesadores",
                                        "Discos Duros",
                                        "Tarjetas Gráficas",
                                        "Memoria RAM",
                                        "Tarjetas de Sonido",
                                        "Todos los productos"};

        // List View
        ListView menu_categorias = view.findViewById(R.id.list_categorias);

        ArrayAdapter<String> listViewAdapter = new ArrayAdapter<String>(
                getActivity(),
                android.R.layout.simple_expandable_list_item_1,
                categorias
        );

        // Controlamos los clicks en el menu y navegamos a las actividades
        menu_categorias.setAdapter(listViewAdapter);
        menu_categorias.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0:
                        Intent intent1 = new Intent(getActivity(), CategoryActivity.class);
                        intent1.putExtra("categoria", "Placas Base");
                        startActivity(intent1);
                        break;
                    case 1:
                        Intent intent2 = new Intent(getActivity(), CategoryActivity.class);
                        intent2.putExtra("categoria", "Procesadores");
                        startActivity(intent2);
                        break;
                    case 2:
                        Intent intent3 = new Intent(getActivity(), CategoryActivity.class);
                        intent3.putExtra("categoria", "Discos Duros");
                        startActivity(intent3);
                        break;
                    case 3:
                        Intent intent4 = new Intent(getActivity(), CategoryActivity.class);
                        intent4.putExtra("categoria", "Tarjetas Gráficas");
                        startActivity(intent4);
                        break;
                    case 4:
                        Intent intent5 = new Intent(getActivity(), CategoryActivity.class);
                        intent5.putExtra("categoria", "Memoria RAM");
                        startActivity(intent5);
                        break;
                    case 5:
                        Intent intent6 = new Intent(getActivity(), CategoryActivity.class);
                        intent6.putExtra("categoria", "Tarjetas de Sonido");
                        startActivity(intent6);
                        break;
                    case 6:
                        Intent intent7 = new Intent(getActivity(), CategoryActivity.class);
                        intent7.putExtra("categoria", "Todos los productos");
                        startActivity(intent7);
                        break;
                }
            }
        });
        return view;
    }
}