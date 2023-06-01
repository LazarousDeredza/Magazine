package my.readme.app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.namespace.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import my.readme.app.customerMagPanel.CustomerCartFragment;
import my.readme.app.customerMagPanel.CustomerHomeFragment;
import my.readme.app.customerMagPanel.CustomerOrdersFragment;
import my.readme.app.customerMagPanel.CustomerProfileFragment;
import my.readme.app.customerMagPanel.CustomerTrackFragment;

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
                loadfragment(new CustomerCartFragment());
            } else if (name.equalsIgnoreCase("DeliveryOrderpage")) {
                loadfragment(new CustomerOrdersFragment());
            } else if (name.equalsIgnoreCase("Thankyou")) {
                loadfragment(new CustomerTrackFragment());
            }
        } else {
            loadfragment(new CustomerProfileFragment());
        }
    }

    private void loadfragment(CustomerProfileFragment customerProfileFragment) {
    }

    private void loadfragment(CustomerOrdersFragment customerOrdersFragment) {

    }

    private void loadfragment(CustomerCartFragment customerCartFragment) {

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
                fragment = new Fragment(R.layout.fragment_customerprofile);
                break;
            case R.id.Cust_order:
                fragment = new Fragment(R.layout.fragment_customerorders);
                break;
            case R.id.cart:
                fragment = new Fragment(R.layout.fragment_customercart);
                break;

            case R.id.logout:

                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(this, MainMenu.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
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


