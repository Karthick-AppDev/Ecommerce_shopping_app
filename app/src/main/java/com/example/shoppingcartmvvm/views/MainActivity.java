package com.example.shoppingcartmvvm.views;

import static androidx.fragment.app.FragmentManager.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.shoppingcartmvvm.R;
import com.example.shoppingcartmvvm.models.CartItem;
import com.example.shoppingcartmvvm.viewmodel.ShopViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {


    NavController navController;
    ShopViewModel shopViewModel;
    int cartQuality;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        navController = navHostFragment.getNavController();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        shopViewModel = new ViewModelProvider(this).get(ShopViewModel.class);
        shopViewModel.getCart().observe(this, new Observer<List<CartItem>>() {
            @Override
            public void onChanged(List<CartItem> cartItems) {
               int quality = 0;
               if(cartItems.size() > 0) {
                   for (CartItem cartItem : cartItems) {
                       quality += cartItem.getQuantity();
                       cartQuality = quality;
                       invalidateOptionsMenu();
                   }
               }else {
                   cartQuality = quality;
                   invalidateOptionsMenu();
               }
            }
        });

        //navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        // NavigationUI.setupActionBarWithNavController(this,navController);

        NavigationUI.setupActionBarWithNavController(this,navController);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu,menu);

        MenuItem menuItem = menu.findItem(R.id.cartFragment);
        View actionView = menuItem.getActionView();
        TextView cart_badge_text_view = actionView.findViewById(R.id.badge_textView);
        cart_badge_text_view.setText(String.valueOf(cartQuality));
        cart_badge_text_view.setVisibility(cartQuality == 0?View.GONE:View.VISIBLE);

        actionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOptionsItemSelected(menuItem);

            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return NavigationUI.onNavDestinationSelected(item,navController) || super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        return navController.navigateUp() ||super.onSupportNavigateUp();
    }
}