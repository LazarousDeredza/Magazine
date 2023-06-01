package my.readme.app.customerMagPanel;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.namespace.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import my.readme.app.MainMenu;

public class CustomerHomeFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    RecyclerView recyclerView;
    private List<UpdateMagazineModel> updateMagazineModelList=new ArrayList<>();
    private CustomerHomeAdapter adapter;
    String Town, City, Area;
    DatabaseReference dataa, databaseReference;
    SwipeRefreshLayout swipeRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_customerhome, null);
        getActivity().setTitle("Home");

        recyclerView = v.findViewById(R.id.recycle_menu);
        recyclerView.setHasFixedSize(true);
        Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.move);
        recyclerView.startAnimation(animation);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        for (int i = 0; i < 10; i++) {
            UpdateMagazineModel updateMagazineModel=new UpdateMagazineModel();
            updateMagazineModel.setTitle("Magazine "+i);
            updateMagazineModel.setDescription("Fashion Meg");
            updateMagazineModel.setPrice(i+".20");
            updateMagazineModel.setQuantity("10");
            updateMagazineModel.setImageURL("......");
            updateMagazineModel.setPublisherId("uugyww4141");

            updateMagazineModelList.add(updateMagazineModel);
        }



        adapter = new CustomerHomeAdapter(getContext(), updateMagazineModelList);
        recyclerView.setAdapter(adapter);

        updateMagazineModelList = new ArrayList<>();
        swipeRefreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.swipelayout);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimaryDark, R.color.Red);

       /* swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
                String userid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                dataa = FirebaseDatabase.getInstance().getReference("User").child(userid);
                dataa.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        Customer custo = snapshot.getValue(Customer.class);
                        City = custo.getCity();
                        Town = custo.getTown();
                        Area = custo.getArea();
                        customermenu();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });*/


        return v;
    }

    @Override
    public void onRefresh() {
        customermenu();
    }

    private void customermenu() {

        swipeRefreshLayout.setRefreshing(true);

        if (City != null && Town != null && Area != null) {

            databaseReference = FirebaseDatabase.getInstance().getReference("MagazineDetails");
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    updateMagazineModelList.clear();
                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                        for (DataSnapshot snapshot2 : snapshot1.getChildren()) {
                            UpdateMagazineModel updateMagazineModel = snapshot2.getValue(UpdateMagazineModel.class);
                            updateMagazineModelList.add(updateMagazineModel);
                        }
                    }


                    Log.e("Magazines retrieved", String.valueOf(updateMagazineModelList.size()));
                    adapter.notifyDataSetChanged();

                    swipeRefreshLayout.setRefreshing(false);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    swipeRefreshLayout.setRefreshing(false);

                }
            });

        } else {

            swipeRefreshLayout.setRefreshing(false);
        }
    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.logout,menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int idd = item.getItemId();
        if(idd == R.id.LOGOUT){
            Logout();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void Logout() {

        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(getActivity(), MainMenu.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}