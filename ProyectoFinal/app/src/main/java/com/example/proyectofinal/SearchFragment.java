package com.example.proyectofinal;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Fragmento que permite realizar una busqueda de productos por sus nombres
 * y muestra los productos que coinciden con la busqueda en un List View
 */
public class SearchFragment extends Fragment {
    private SearchView searchView;
    private ListView listView;
    private ArrayList<String> nameList;
    private ArrayList<Product> productList;
    private ArrayAdapter<String> arrayAdapter;

    /**
     * Identificamos los elementos de la vista y manejamos las interacciones
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return retornamos la vista
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        // Progress Bar
        ProgressBar progressBar = view.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);

        // List View y lista de productos a mostrar
        listView = view.findViewById(R.id.listView);
        productList = new ArrayList<>();
        nameList = new ArrayList<>();

        // Obtenemos los productos
        retrieveProductsBD();

        // Search View
        searchView = view.findViewById(R.id.searchView);

        // Comprobamos si la búsqueda procede del fragmento de inicio y hacemos un focus
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            String busqueda = bundle.getString("RequestFocus");
            if(busqueda.equals("Yes")) {
                //searchView.setIconifiedByDefault(true);
                searchView.setFocusable(true);
                searchView.requestFocus();
            }
        }

        // Manejamos los clicks de la caja de busqueda
        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchView.setIconified(false);
            }
        });

        // Modificamos los productos mostrados en funcion de la busqueda
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                arrayAdapter.getFilter().filter(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                arrayAdapter.getFilter().filter(s);
                return false;
            }
        });

        // Manejamos el Listener para ver si se ha clickado algun producto
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), ProductActivity.class);
                intent.putExtra("Product", productList.get(i));
                startActivity(intent);
            }
        });

        return view;
    }

    /**
     * Referenciamos a la Realtime Database de Firebase y recuperamos los productos
     */
    private void retrieveProductsBD() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Products");
        reference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds: dataSnapshot.getChildren()) {
                    Product product = ds.getValue(Product.class);
                    productList.add(product);
                    nameList.add(product.getNombre());
                }

                arrayAdapter = new ArrayAdapter<>(getContext(),
                        android.R.layout.simple_list_item_1,
                        nameList);

                listView.setAdapter(arrayAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(), "Algo salió mal, vuelve a intentarlo...", Toast.LENGTH_SHORT).show();
            }
        });
    }
}