package com.example.shoppingcartmvvm.views;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shoppingcartmvvm.R;
import com.example.shoppingcartmvvm.adapters.ShopListAdapter;
import com.example.shoppingcartmvvm.databinding.FragmentShopBinding;
import com.example.shoppingcartmvvm.viewmodel.ShopViewModel;
import com.example.shoppingcartmvvm.models.Product;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;


public class ShopFragment extends Fragment implements ShopListAdapter.ShopInterface {
    private static final String TAG = "ShopFragment";
FragmentShopBinding fragmentShopBinding;
ShopListAdapter shopListAdapter;
NavController navController;
ShopViewModel shopViewModel;

    public ShopFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       // return inflater.inflate(R.layout.fragment_shop, container, false);
        fragmentShopBinding = FragmentShopBinding.inflate(inflater,container,false);
        return fragmentShopBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        shopListAdapter = new ShopListAdapter(this);
        fragmentShopBinding.shopRecyclerView.setAdapter(shopListAdapter);
        shopViewModel = new ViewModelProvider(requireActivity()).get(ShopViewModel.class);
        shopViewModel.getProducts().observe(getViewLifecycleOwner(), new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> products) {
                shopListAdapter.submitList(products);
            }
        });
        fragmentShopBinding.shopRecyclerView.addItemDecoration(new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL));
        fragmentShopBinding.shopRecyclerView.addItemDecoration(new DividerItemDecoration(requireContext(), DividerItemDecoration.HORIZONTAL));
        navController = Navigation.findNavController(view);
    }

    @Override
    public void addItem(Product product) {
      boolean isAdded = shopViewModel.addCartItem(product);
       if(isAdded){
           Snackbar.make(requireView(),product.getName() + " added to the card",Snackbar.LENGTH_LONG)
                   .setAction("Checkout", new View.OnClickListener() {
                       @Override
                       public void onClick(View v) {
                           navController.navigate(R.id.action_shopFragment_to_cartFragment);
                       }
                   }).show();
       }else {
           Snackbar.make(requireView(),"Already added max quantity to the card",Snackbar.LENGTH_LONG).show();
       }
    }

    @Override
    public void onItemClick(Product product) {
        shopViewModel.setProduct(product);
        navController.navigate(R.id.action_shopFragment_to_productFragment);
    }
}