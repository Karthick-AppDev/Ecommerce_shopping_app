package com.example.shoppingcartmvvm.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.shoppingcartmvvm.Repository.CartRepo;
import com.example.shoppingcartmvvm.Repository.ShopRepo;
import com.example.shoppingcartmvvm.models.CartItem;
import com.example.shoppingcartmvvm.models.Product;

import java.util.List;

public class ShopViewModel extends ViewModel {
    MutableLiveData<Product> mutableProduct = new MutableLiveData<>();

    ShopRepo shopRepo = new ShopRepo();
    CartRepo cartRepo = new CartRepo();
public LiveData<List<Product>> getProducts(){
    return shopRepo.getProducts();
}

public void setProduct(Product product){
    mutableProduct.setValue(product);
}
public LiveData<Product> getProduct(){
    return mutableProduct;
}
public LiveData<List<CartItem>> getCart(){
    return cartRepo.getCart();
}

public boolean addCartItem(Product product){
    return cartRepo.addItemToCart(product);
}
public void deleteItemFromCart(CartItem cartItem){
    cartRepo.deleteItemFromCart(cartItem);
}
public void changeQuality(CartItem cartItem,int quality){
    cartRepo.changeQuality(cartItem,quality);
}
public LiveData<Double> getTotalPrice(){
    return cartRepo.getTotalPrice();
}
public Boolean CheckTotalPriceForPlaceOrder(){
    return cartRepo.CheckTotalPriceForPlaceOrder();
}
public void resetCart(){
    cartRepo.resetCart();
}
}
