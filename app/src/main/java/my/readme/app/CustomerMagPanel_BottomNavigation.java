package my.readme.app;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.namespace.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;


import my.readme.app.customerMagPanel.CustomerHomeFragment;

import my.readme.app.customerMagPanel.CustomerTrackFragment;
import my.readme.app.customerMagPanel.Customer_Cart_Frag;
import my.readme.app.customerMagPanel.Customer_Order_Frag;
import my.readme.app.customerMagPanel.Customer_Profile_Frag;

public class CustomerMagPanel_BottomNavigation extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_mag_panel_bottom_navigation);
        BottomNavigationView navigationView = findViewById(R.id.bottom_navigation);
        navigationView.setOnNavigationItemSelectedListener(this);

        String name = getIntent().getStringExtra("Page");
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (name != null) {
            if (name.equalsIgnoreCase("Homepage")) {
                loadfragment(new CustomerHomeFragment());
            } else if (name.equalsIgnoreCase("Preparingpage")) {
                loadfragment(new Customer_Cart_Frag());
            } else if (name.equalsIgnoreCase("DeliveryOrderpage")) {
                loadfragment(new Customer_Order_Frag());
            } else if (name.equalsIgnoreCase("Thankyou")) {
                loadfragment(new CustomerTrackFragment());
            }
        } else {
            loadfragment(new Customer_Profile_Frag());
        }
    }

    private void loadfragment(Customer_Profile_Frag customerProfileFragment) {
    }

    private void loadfragment(Customer_Order_Frag customerOrdersFragment) {

    }

    private void loadfragment(Customer_Cart_Frag customerCartFragment) {

    }

    private void loadfragment(CustomerTrackFragment customerTrackFragment) {
    }

    private void loadfragment(CustomerHomeFragment customerHomeFragment) {
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        Fragment fragment = null;

        switch (item.getItemId()) {
            case R.id.cust_Home:
                fragment = new CustomerHomeFragment();
                break;

            case R.id.cust_profile:
                fragment = new Customer_Profile_Frag();
                break;
            case R.id.Cust_order:
                fragment = new Customer_Order_Frag();
                break;
            case R.id.cart:
                fragment = new Customer_Cart_Frag();
                break;

            case R.id.logout:


                AlertDialog.Builder builder = new AlertDialog.Builder(CustomerMagPanel_BottomNavigation.this);
                builder.setMessage("Are you sure you want to Logout");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseAuth.getInstance().signOut();
                        Intent intent = new Intent(getApplicationContext(), MainMenu.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();






                break;
                /*

            case R.id.track:
                fragment= new Fragment();
                break;*/
        }
        return loadfragment(fragment);

    }

    private boolean loadfragment(Fragment fragment) {

        if (fragment != null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
            return true;
        }
        return false;
    }
}


