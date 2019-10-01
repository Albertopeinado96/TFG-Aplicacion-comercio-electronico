package com.example.proyectofinal;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

/*
* RecyclerView.Adapter
* RecyclerView.Holder
*
 */

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private Context context;
    private List<Product> productList;

    public ProductAdapter(Context context, List<Product> productList) {
        this.context = context;
        this.productList = productList;
    }


    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.layout_product, null);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, final int position) {
        final Product product = productList.get(position);
        DecimalFormat df = new DecimalFormat("#.00");

        holder.textViewTitle.setText(product.getNombre());
        holder.textViewPrice.setText(context.getResources().getString(R.string.string_precio, df.format(product.getPrecio())));
        Picasso.get().load(product.getImage()).into(holder.imageView);
        holder.itemRatingBar.setRating(product.getValoracion());

        holder.textViewTitle.setContentDescription("Producto " + holder.textViewTitle.getText().toString());
        holder.textViewPrice.setContentDescription("Precio del producto" + holder.textViewPrice.getText().toString());
        holder.itemRatingBar.setContentDescription("Valoracion del producto" + String.valueOf(holder.itemRatingBar.getRating()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ProductActivity.class);
                intent.putExtra("Product", productList.get(position));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textViewTitle, textViewPrice;
        RatingBar itemRatingBar;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewPrice = itemView.findViewById(R.id.textViewPrice);
            imageView = itemView.findViewById(R.id.productImageView);
            itemRatingBar = itemView.findViewById(R.id.itemRatingBar);
        }
    }
}

