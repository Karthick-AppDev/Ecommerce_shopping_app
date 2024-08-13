package com.example.shoppingcartmvvm.views;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shoppingcartmvvm.viewmodel.ShopViewModel;
import com.example.shoppingcartmvvm.databinding.FragmentProductBinding;
import com.example.shoppingcartmvvm.models.Product;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProductFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProductFragment extends Fragment {
    ShopViewModel shopViewModel;


FragmentProductBinding fragmentProductBinding;
    public ProductFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentProductBinding = FragmentProductBinding.inflate(inflater,container,false);
        return fragmentProductBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        shopViewModel = new ViewModelProvider(requireActivity()).get(ShopViewModel.class);
      shopViewModel.getProduct().observe(getViewLifecycleOwner(), new Observer<Product>() {
          @Override
          public void onChanged(Product product) {
              fragmentProductBinding.setShopViewModel(shopViewModel);
          }
      });
      fragmentProductBinding.button.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {

          }
      });
    }
}