package com.example.proyectofinal;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/*
 * RecyclerView.Adapter
 * RecyclerView.Holder
 *
 */
public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private Context context;
    private List<Product> cartList;

    public CartAdapter(Context context, List<Product> productList) {
        this.context = context;
        this.cartList = productList;
    }


    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.layout_cart_product, null);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CartViewHolder holder, final int position) {
        final Product product = cartList.get(position);
        DecimalFormat df = new DecimalFormat("#.00");

        int unidades = 1;
        Picasso.get().load(product.getImage()).into(holder.imageView);
        holder.textViewTitle.setText(product.getNombre());
        holder.textViewPrice.setText(context.getResources().getString(R.string.string_precio, df.format(product.getPrecio())));
        holder.itemRatingBar.setRating(product.getValoracion());

        holder.textViewTitle.setContentDescription("Producto " + holder.textViewTitle.getText().toString());
        holder.textViewPrice.setContentDescription("Precio del producto" + holder.textViewPrice.getText().toString());
        holder.itemRatingBar.setContentDescription("Valoracion del producto" + String.valueOf(holder.itemRatingBar.getRating()));
        holder.textViewUnidades.setContentDescription("Unidades del producto" + holder.textViewUnidades.getText().toString());

        holder.add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Se ha añadido una unidad", Toast.LENGTH_SHORT).show();
            }
        });

        holder.minus_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Se ha eliminado una unidad", Toast.LENGTH_SHORT).show();

            }
        });

        holder.delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                loadData();

                for(int i = 0; i < cartList.size(); i++)
                {
                    if (cartList.get(i).getId() == product.getId()) {
                        cartList.remove(i);
                        notifyItemRemoved(i);
                        break;
                    }
                }

                saveData();

                Intent intent = new Intent(context, MainActivity.class);
                intent.putExtra("key", "CartDelete"); // Le pasamos un dato al intent.
                context.startActivity(intent);
                Toast.makeText(context, "El producto ha sido eliminado", Toast.LENGTH_LONG).show();
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        holder.textViewUnidades.setText(String.valueOf(unidades));
    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }

    public class CartViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView textViewTitle, textViewPrice, textViewUnidades;
        RatingBar itemRatingBar;
        Button add_button, minus_button, delete_button;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewPrice = itemView.findViewById(R.id.textViewPrice);
            imageView = itemView.findViewById(R.id.productImageView);
            itemRatingBar = itemView.findViewById(R.id.itemRatingBar);
            add_button = itemView.findViewById(R.id.add_button);
            minus_button = itemView.findViewById(R.id.minus_button);
            delete_button = itemView.findViewById(R.id.delete_button);
            textViewUnidades = itemView.findViewById(R.id.num_unidades);



        }


    }

    /**
     * Cargamos los datos de Shared Preferences
     */
    private void loadData() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        Gson gson = new Gson();
        String json = prefs.getString("Products", null);

        if (json == null) {
            // No hacemos nada
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
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
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
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        prefs.edit().clear().commit();
    }
}