package com.example.mvvmkhang.view.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.mvvmkhang.Adapter.AdapterProduct;
import com.example.mvvmkhang.Animation.CartCounter;
import com.example.mvvmkhang.R;
import com.example.mvvmkhang.model.DataClass;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    FloatingActionButton cart;
    List<DataClass> dataClasses;
    AdapterProduct adapterProduct;
    TextView cartcounter;
    private NavController navController;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        recyclerView = findViewById(R.id.productrecyclerview);
        cart =  findViewById(R.id.shopcart);

        dataClasses = new ArrayList<>();
        recyclerView.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(HomeActivity.this,2);

        recyclerView.setLayoutManager(gridLayoutManager);
        cartcounter = findViewById(R.id.cartcounter);
        getCartCount();

        getProduct();
    }

    public void getCartCount() {
        CartCounter cartCounter = new CartCounter(HomeActivity.this);
        int count = cartCounter.cartCount();
        cartcounter.setText(""+count);
    }

    public void getProduct() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Android Tutorials");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                for(DataSnapshot snapshot:datasnapshot.getChildren()){
                    dataClasses.clear();
                    DataClass dataClass = snapshot.getValue(DataClass.class);
                    dataClasses.add(dataClass);
                    adapterProduct = new AdapterProduct(HomeActivity.this,dataClasses,cart);
                    recyclerView.setAdapter(adapterProduct);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}