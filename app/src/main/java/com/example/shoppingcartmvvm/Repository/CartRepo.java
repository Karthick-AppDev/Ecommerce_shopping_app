package com.example.shoppingcartmvvm.Repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.shoppingcartmvvm.models.CartItem;
import com.example.shoppingcartmvvm.models.Product;

import java.util.ArrayList;
import java.util.List;

public class CartRepo {
    MutableLiveData<List<CartItem>> mutableCartItem = new MutableLiveData<>();
    MutableLiveData<Double> mutableTotalPrice = new MutableLiveData<>();


    public LiveData<List<CartItem>> getCart() {
        if (mutableCartItem.getValue() == null) {
            initCartItem();
        }
        return mutableCartItem;
    }

    private void initCartItem() {
        mutableCartItem.setValue(new ArrayList<CartItem>());
        calculateTotalPrice();
    }

    public boolean addItemToCart(Product product) {
        if (mutableCartItem.getValue() == null) {
            initCartItem();
        }
        List<CartItem> cartItemList = new ArrayList<>(mutableCartItem.getValue());
        for (CartItem cartItem : cartItemList) {

            if (cartItem.getProduct().getId().equals(product.getId())) {

                if (cartItem.getQuantity() == 5) {

                    return false;
                }
                int index = cartItemList.indexOf(cartItem);
                cartItem.setQuantity(cartItem.getQuantity() + 1);
                cartItemList.set(index, cartItem);
                mutableCartItem.setValue(cartItemList);
                calculateTotalPrice();
                return true;
            }
        }

        CartItem cartItem = new CartItem(product, 1);
        cartItemList.add(cartItem);
        mutableCartItem.setValue(cartItemList);
        calculateTotalPrice();
        return true;
    }

    public void deleteItemFromCart(CartItem cartItem) {
        List<CartItem> cartItems = new ArrayList<>(mutableCartItem.getValue());
        cartItems.remove(cartItem);
        mutableCartItem.setValue(cartItems);
        calculateTotalPrice();
    }

    public void changeQuality(CartItem cartItem, int quality) {
        if (mutableCartItem.getValue() == null) {
            return;
        }
        List<CartItem> cartItems = new ArrayList<>(mutableCartItem.getValue());

        CartItem updateItem = new CartItem(cartItem.getProduct(), quality);
        cartItems.set(cartItems.indexOf(cartItem), updateItem);
        mutableCartItem.setValue(cartItems);
        calculateTotalPrice();
    }

    public void calculateTotalPrice() {
        List<CartItem> cartItems = new ArrayList<>(mutableCartItem.getValue());
        Double totalPrice = 0.0;
        for (CartItem cartItem:cartItems){
            totalPrice += cartItem.getProduct().getPrice() * cartItem.getQuantity();
        }
        mutableTotalPrice.setValue(totalPrice);

    }

    public LiveData<Double> getTotalPrice() {
        if (mutableTotalPrice.getValue() == null) {
            mutableTotalPrice.setValue(0.0);
        }
        return mutableTotalPrice;
    }

    public Boolean CheckTotalPriceForPlaceOrder(){
        if(mutableTotalPrice.getValue() > 0.0){
            return true;
        }
        return false;
    }
public void resetCart(){
        initCartItem();
}
}
