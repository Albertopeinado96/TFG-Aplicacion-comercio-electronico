package com.example.proyectofinal;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
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
 * Fragmento Inicio, muestra una barra de búsqueda y los productos recomendados
 */
public class HomeFragment extends Fragment {
    private ProductAdapter productAdapter;
    private List<Product> productList;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private SearchView searchView;

    /**
     * Identificamos elementos de la vista, manejamos la caja de búsqueda
     * y mostramos los productos en el recyclerView
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return retornamos la vista
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Progress Bar
        progressBar = view.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        // Search Box
        searchView = view.findViewById(R.id.searchView);
        searchView.setQueryHint("Buscar");

        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchFragment newFragment = new SearchFragment();

                Bundle args = new Bundle();
                args.putString("RequestFocus", "Yes");
                newFragment.setArguments(args);

                FragmentTransaction transaction = getFragmentManager().beginTransaction();

                // Replace whatever is in the fragment_container view with this fragment,
                // and add the transaction to the back stack so the user can navigate back
                transaction.replace(R.id.fragment_container, newFragment);
                transaction.addToBackStack(null);

                // Commit the transaction
                transaction.commit();
            }
        });

        // Recycler View
        recyclerView = view.findViewById(R.id.listRecyclerViewHome);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Lista de productos
        productList = new ArrayList<>();

        // Recuperamos los productos
        retrieveProductsBD();

        return view;
    }


    /**
     * Referenciamos a la Realtime Database de Firebase y recuperamos los productos
     */
    private void retrieveProductsBD() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Products");
        reference.addValueEventListener(new ValueEventListener() {

            /**
             * Obtenemos los productos de la base de datos y los almacenamos en un ArrayList
             * @param dataSnapshot Datos procedentes de la base de datos
             */
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds: dataSnapshot.getChildren()) {
                    Product product = ds.getValue(Product.class);
                    productList.add(product);
                }
                productAdapter = new ProductAdapter(getContext(), productList);
                recyclerView.setAdapter(productAdapter);
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(), "Algo falló al mostrar los productos.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}