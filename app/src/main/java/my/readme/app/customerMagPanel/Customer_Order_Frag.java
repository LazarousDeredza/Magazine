package my.readme.app.customerMagPanel;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.namespace.R;
import com.google.firebase.auth.FirebaseAuth;

import my.readme.app.MainMenu;

public class Customer_Order_Frag extends Fragment {



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_customerorders, null);
        getActivity().setTitle("Welcome to ReadMe");


        return v;
    }


 /*   private void loadData() {

        swipeRefreshLayout.setRefreshing(true);


        databaseReference = FirebaseDatabase.getInstance().getReference("MagazineDetails");

        databaseReference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                updateMagazineModelList.clear();
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    for (DataSnapshot snapshot2 : snapshot1.getChildren()) {
                        UpdateMagazineModel updateMagazineModel = snapshot2.getValue(UpdateMagazineModel.class);

                        Log.e("Magazines title", updateMagazineModel.getTitle());
                        Log.e("Magazines description", updateMagazineModel.getDescription());

                        updateMagazineModel.setPublisherId(snapshot2.child("publisherid").getValue().toString());


                        updateMagazineModelList.add(updateMagazineModel);

                    }
                }

                Log.e("Magazines retrieved", String.valueOf(updateMagazineModelList.size()));


                adapter.notifyDataSetChanged();


                if (updateMagazineModelList.size() == 0) {
                    loadingMagazine.setVisibility(View.GONE);
                    noMagazineFound.setVisibility(View.VISIBLE);
                } else {
                    recyclerView.setVisibility(View.VISIBLE);
                    loadingMagazine.setVisibility(View.GONE);
                    noMagazineFound.setVisibility(View.GONE);
                }


                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                swipeRefreshLayout.setRefreshing(false);

            }
        });


    }*/


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.logout, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int idd = item.getItemId();
        if (idd == R.id.LOGOUT) {
            Logout();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void Logout() {

        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(getActivity(), MainMenu.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}