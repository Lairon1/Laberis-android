package ru.lairon.laberis;

import android.os.Build;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

public class ProductsActivity extends LaberisActivity {


    private ListView productsView;



    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);

        productsView = findViewById(R.id.productListView);

        getDataProvider().loadAllProductsAsync(products -> {

            productsView.setAdapter(new ProductItemAdapter(getApplicationContext(), products));

        }, exception -> toast(exception.getMessage()));

    }
}