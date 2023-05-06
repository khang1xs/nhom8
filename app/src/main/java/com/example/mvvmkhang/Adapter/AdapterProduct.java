package com.example.mvvmkhang.Adapter;

import android.animation.Animator;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mvvmkhang.Animation.CircleAnimationUtil;
import com.example.mvvmkhang.R;
import com.example.mvvmkhang.model.DataClass;
import com.example.mvvmkhang.view.activity.HomeActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Random;

import p32929.androideasysql_library.Column;
import p32929.androideasysql_library.EasyDB;

public class AdapterProduct extends RecyclerView.Adapter<AdapterProduct.MyviewHolder> {

    Context context;
    List<DataClass> dataClasses;
    FloatingActionButton floatingActionButton;

    public AdapterProduct(Context context, List<DataClass> modelProducts, FloatingActionButton floatingActionButton) {
        this.context = context;
        this.dataClasses = modelProducts;
        this.floatingActionButton = floatingActionButton;
    }

    @NonNull
    @Override
    public MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.productcontainer,parent,false);
        return new MyviewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyviewHolder holder, int position) {

        String name = dataClasses.get(position).getDataTitle();
        String price = dataClasses.get(position).getDataLang();
        String image = dataClasses.get(position).getDataImage();

        holder.product_name.setText(name);
        holder.product_price.setText(price);
        try {
            Picasso.get().load(image).into(holder.product_image);
        }
        catch (Exception e){

        }
        holder.addtocarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addtoCart(name,price,image);
                new CircleAnimationUtil().attachActivity((Activity) context).setTargetView(holder.product_image)
                        .setMoveDuration(1000).setDestView(floatingActionButton).setAnimationListener(new Animator.AnimatorListener() {
                            @Override
                            public void onAnimationStart(@NonNull Animator animation) {

                            }

                            @Override
                            public void onAnimationEnd(@NonNull Animator animation) {
                                ((HomeActivity)context).getCartCount();
                            }

                            @Override
                            public void onAnimationCancel(@NonNull Animator animation) {

                            }

                            @Override
                            public void onAnimationRepeat(@NonNull Animator animation) {

                            }
                        }).startAnimation();
            }
            public  int itemid = 1;
            private void addtoCart(String name, String pirce, String image) {
                Random random = new Random();
                itemid = random.nextInt(200000);
                EasyDB easyDB = EasyDB.init(context,"ITEM_DB")
                        .setTableName("ITEM_TABLE")
                        .addColumn(new Column("item_id",new String[]{"text","unipe"}))
                        .addColumn(new Column("item_name",new String[]{"text","not null"}))
                        .addColumn(new Column("item_price",new String[]{"text","not null"}))
                        .addColumn(new Column("item_image",new String[]{"text","not null"}))
                        .doneTableColumn();

                Boolean b=easyDB.addData("item_id",itemid)
                        .addData("item_name",name)
                        .addData("item_price",pirce)
                        .addData("item_image",image)
                        .doneDataAdding();
                Toast.makeText(context,"add to cart... ",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataClasses.size();
    }
    public class MyviewHolder extends  RecyclerView.ViewHolder{

        ImageView product_image;
        TextView product_name,product_price;
        Button addtocarButton;
        public MyviewHolder(@NonNull View itemView) {
            super(itemView);
            product_image= itemView.findViewById(R.id.productimage);
            product_name = itemView.findViewById(R.id.producname);
            product_price = itemView.findViewById(R.id.producprice);
            addtocarButton = itemView.findViewById(R.id.cartbutton);

        }
    }

}
