package com.example.mvvmkhang.view;

import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.mvvmkhang.Adapter.AdapterCart;
import com.example.mvvmkhang.R;
import com.example.mvvmkhang.model.DataCart;

import java.util.ArrayList;
import java.util.List;

import p32929.androideasysql_library.Column;
import p32929.androideasysql_library.EasyDB;


public class CartshopFragment extends Fragment {

    List<DataCart> modelCarts;
    AdapterCart adapterCart;
    RecyclerView recyclerView;
    Button catpirce;
    private NavController navController;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_cartshop, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);

        catpirce = view.findViewById(R.id.cartprice);
        recyclerView = view.findViewById(R.id.cartrecyclerview);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);

        recyclerView.setLayoutManager(linearLayoutManager);

        modelCarts = new ArrayList<>();
        getcartitem();
        catpirce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.newFragment);
            }
        });

    }

    private void getcartitem() {
        EasyDB easyDB = EasyDB.init(getContext(),"ITEM_DB")
                .setTableName("ITEM_TABLE")
                .addColumn(new Column("item_id",new String[]{"text","unipe"}))
                .addColumn(new Column("item_name",new String[]{"text","not null"}))
                .addColumn(new Column("item_price",new String[]{"text","not null"}))
                .addColumn(new Column("item_image",new String[]{"text","not null"}))
                .doneTableColumn();
        Cursor res = easyDB.getAllData();
        while (res.moveToNext()){
            String  id = res.getString(0);
            String  name = res.getString(1);
            String  price = res.getString(2);
            String  image = res.getString(3);

            DataCart modelCart = new DataCart(id,name,price,image);
            modelCarts.add(modelCart);
            adapterCart = new AdapterCart( getContext(),modelCarts);
            recyclerView.setAdapter(adapterCart);

        }
    }
}
