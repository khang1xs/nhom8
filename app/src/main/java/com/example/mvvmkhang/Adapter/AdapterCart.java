package com.example.mvvmkhang.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mvvmkhang.R;
import com.example.mvvmkhang.model.DataCart;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterCart extends RecyclerView.Adapter<AdapterCart.ViewHolder>{

    Context context;
    List<DataCart> dataCarts;

    public  AdapterCart(Context context,List<DataCart> modelCarts){
        this.context=context;
        this.dataCarts = modelCarts;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate((R.layout.cartcontrainer),parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        String image = dataCarts.get(position).getDataImage();
        String name = dataCarts.get(position).getDataTitle();
        String price = dataCarts.get(position).getDataLang();

        holder.cartprice.setText(price);
         holder.cartname.setText(name);

         try {
             Picasso.get().load(image).into(holder.cartimage);
         }
         catch (Exception e){

         }
    }

    @Override
    public int getItemCount() {
        return dataCarts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView cartimage ;
        TextView cartname,cartprice;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            cartimage = itemView.findViewById(R.id.cartimage);
            cartname = itemView.findViewById(R.id.cartproductname);
            cartprice = itemView.findViewById(R.id.cartproductprice);
        }
    }
}
