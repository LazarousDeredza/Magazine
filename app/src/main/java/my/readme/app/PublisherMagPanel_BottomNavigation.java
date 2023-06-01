package my.readme.app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.namespace.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import my.readme.app.publisherMagPanel.PublisherHomeFragment;
import my.readme.app.publisherMagPanel.PublisherProfileFragment;

public class PublisherMagPanel_BottomNavigation extends AppCompatActivity implements  BottomNavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publisher_mag_panel_bottom_navigation);

        BottomNavigationView navigationView = findViewById(R.id.publisher_bottom_navigation);
        navigationView.setOnNavigationItemSelectedListener(this);
        String name = getIntent().getStringExtra("PAGE");
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (name != null) {
            if (name.equalsIgnoreCase("Homepage")) {
                loadpublisherfragment(new PublisherProfileFragment());

            }
        } else {
            loadpublisherfragment(new PublisherHomeFragment());
        }
    }



    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;
        switch (item.getItemId()){
            case R.id.publisherHome:
                fragment=new PublisherHomeFragment();
                break;
            case R.id.publisherProfile:
                fragment=new PublisherProfileFragment();
                break;
        }
        return loadpublisherfragment(fragment);
    }

    private boolean loadpublisherfragment(Fragment fragment) {

        if (fragment != null){
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,fragment).commit();
            return true;
        }
        return false;
    }
}