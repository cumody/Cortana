package com.mahmoudshaaban.cortana.ViewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mahmoudshaaban.cortana.ItemClickLisnter;
import com.mahmoudshaaban.cortana.R;

public class CartViewholder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView txtproductname , txtproductprice , txtproductQuantity;
    private ItemClickLisnter itemClickLisnter;

    public CartViewholder(@NonNull View itemView) {
        super(itemView);

        txtproductname = itemView.findViewById(R.id.card_product_name);
        txtproductprice = itemView.findViewById(R.id.card_product_price);
        txtproductQuantity = itemView.findViewById(R.id.card_product_quantity);
    }


    @Override
    public void onClick(View view) {

        itemClickLisnter.onclick(view,getAdapterPosition(),false);

    }

    public void setItemClickLisnter(ItemClickLisnter itemClickLisnter) {
        this.itemClickLisnter = itemClickLisnter;
    }
}
