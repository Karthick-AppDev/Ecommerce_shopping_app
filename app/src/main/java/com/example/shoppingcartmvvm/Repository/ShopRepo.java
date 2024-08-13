package com.example.shoppingcartmvvm.Repository;

import android.net.Uri;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.shoppingcartmvvm.R;
import com.example.shoppingcartmvvm.models.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ShopRepo {
    private MutableLiveData<List<Product>> mutableProductList;

    public LiveData<List<Product>> getProducts(){
        if(mutableProductList == null){
            mutableProductList = new MutableLiveData<>();
            loadProducts();
        }
        return mutableProductList;
    }

    private void loadProducts() {
        List<Product> productList = new ArrayList<>();
        productList.add(new Product(UUID.randomUUID().toString(), "iMac 21", 1299, true, "https://support.apple.com/library/APPLE/APPLECARE_ALLGEOS/SP758/imac21inch2017.png" ));
        productList.add(new Product(UUID.randomUUID().toString(), "iPad Air", 799, true, getURLForResource(R.drawable.ipad_air)));
        productList.add(new Product(UUID.randomUUID().toString(), "iPad Pro", 999, true, getURLForResource(R.drawable.ipad)));
        productList.add(new Product(UUID.randomUUID().toString(), "iPhone 11", 699, false, getURLForResource(R.drawable.iphone10)));
        productList.add(new Product(UUID.randomUUID().toString(), "iPhone 11 Pro", 999, true, getURLForResource(R.drawable.iphone_13)));
        productList.add(new Product(UUID.randomUUID().toString(), "iPhone 11 Pro Max", 1099, true,getURLForResource(R.drawable.iphone_15) ));
        productList.add(new Product(UUID.randomUUID().toString(), "iPhone SE", 399, true,getURLForResource(R.drawable.iphone10) ));
        productList.add(new Product(UUID.randomUUID().toString(), "MacBook Air", 999, true, getURLForResource(R.drawable.macbook)));
        productList.add(new Product(UUID.randomUUID().toString(), "MacBook Pro 13", 1299, true,getURLForResource(R.drawable.macbook) ));
        productList.add(new Product(UUID.randomUUID().toString(), "MacBook Pro 16", 2399, true, getURLForResource(R.drawable.macbook)));
        mutableProductList.setValue(productList);
    }
    public String getURLForResource (int resourceId) {
        //use BuildConfig.APPLICATION_ID instead of R.class.getPackage().getName() if both are not same
        return Uri.parse("android.resource://"+ R.class.getPackage().getName()+"/" +resourceId).toString();
    }
}
