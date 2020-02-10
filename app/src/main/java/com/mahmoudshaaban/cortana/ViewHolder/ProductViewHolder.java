package com.mahmoudshaaban.cortana.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mahmoudshaaban.cortana.ItemClickLisnter;
import com.mahmoudshaaban.cortana.R;

public class ProductViewHolder  extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView txtproductName , txtproductDescription , txtproductprice;
    public ImageView imageView;
    public ItemClickLisnter  lisnter;


    public ProductViewHolder(@NonNull View itemView) {
        super(itemView);

        txtproductName = itemView.findViewById(R.id.product_name);
        txtproductDescription = itemView.findViewById(R.id.product_describtion2);
        imageView = itemView.findViewById(R.id.product_image);
        txtproductprice = itemView.findViewById(R.id.product_price);
    }

    public void setitemclicklistner(ItemClickLisnter lisnter){

        this.lisnter = lisnter;
    }

    @Override
    public void onClick(View view) {

        lisnter.onclick(view,getAdapterPosition(),false);

    }
}
