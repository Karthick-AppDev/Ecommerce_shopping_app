package com.example.shoppingcartmvvm.views;

import static androidx.fragment.app.FragmentManager.TAG;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shoppingcartmvvm.R;
import com.example.shoppingcartmvvm.adapters.CartListAdapter;
import com.example.shoppingcartmvvm.databinding.FragmentCartBinding;
import com.example.shoppingcartmvvm.models.CartItem;
import com.example.shoppingcartmvvm.viewmodel.ShopViewModel;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;


public class CartFragment extends Fragment implements CartListAdapter.CartInterface {

    private static final String TAG = "CartFragment";
    ShopViewModel shopViewModel;
    FragmentCartBinding fragmentCartBinding;
    NavController navController;

    public CartFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentCartBinding = FragmentCartBinding.inflate(inflater,container,false);
        return fragmentCartBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        shopViewModel = new ViewModelProvider(requireActivity()).get(ShopViewModel.class);
        CartListAdapter cartListAdapter = new CartListAdapter(this);
        fragmentCartBinding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        fragmentCartBinding.recyclerView.setAdapter(cartListAdapter);

        fragmentCartBinding.recyclerView.addItemDecoration(new DividerItemDecoration(requireActivity(),DividerItemDecoration.VERTICAL));
        navController = Navigation.findNavController(view);
        shopViewModel.getCart().observe(requireActivity(), new Observer<List<CartItem>>() {
            @Override
            public void onChanged(List<CartItem> cartItems) {
                cartListAdapter.submitList(cartItems);
                fragmentCartBinding.placeOrder.setEnabled(cartItems.size() > 0);
            }
        });
        shopViewModel.getTotalPrice().observe(requireActivity(), new Observer<Double>() {
            @Override
            public void onChanged(Double aDouble) {
                fragmentCartBinding.totalPriceTextView.setText("Total : " + aDouble);
            }
        });
        fragmentCartBinding.placeOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(shopViewModel.CheckTotalPriceForPlaceOrder()){
                    navController.navigate(R.id.action_cartFragment_to_orderFragment);
                }else {
                    Snackbar.make(requireView(),"Add product to cart",Snackbar.LENGTH_LONG).show();
                }


            }
        });
    }

    @Override
    public void deleteItem(CartItem cartItem) {
        shopViewModel.deleteItemFromCart(cartItem);
    }

    @Override
    public void changeQuantity(CartItem cartItem, int quantity) {
        shopViewModel.changeQuality(cartItem, quantity);
    }
}