package com.save.rakshak.rakshakrescue;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Anand on 11/28/2017.
 */

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ProductViewHolder> {
    double la, lo;

    private Context mCtx;
    private List<Product> productList;

    public ProductsAdapter(Context mCtx, List<Product> productList) {
        this.mCtx = mCtx;
        this.productList = productList;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.product_list, null);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        Product product = productList.get(position);


        holder.textViewLattitude.setText(String.valueOf(product.getLattitude()));
        holder.textViewLattitude.setText(String.valueOf(product.getLongitude()));
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }



    static class ProductViewHolder extends RecyclerView.ViewHolder {

        static TextView textViewLattitude;
        static TextView textViewLongitude;
        ImageView imageView;

        public ProductViewHolder(View itemView) {
            super(itemView);


            textViewLattitude = itemView.findViewById(R.id.textViewLattitude);
            textViewLongitude = itemView.findViewById(R.id.textViewLongitude);

        }




            static String  lat = textViewLattitude.getText().toString();
            static String lng = textViewLongitude.getText().toString();
            public static double latt = Double.parseDouble(lat);
            public static double lngi = Double.parseDouble(lng);
        public static double lat(){
            return  latt;
        }
        public static double lon(){
            return  lngi;
        }

    }

}