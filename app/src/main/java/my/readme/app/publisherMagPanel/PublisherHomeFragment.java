package my.readme.app.publisherMagPanel;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.namespace.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import my.readme.app.MainMenu;
import my.readme.app.customerMagPanel.UpdateMagazineModel;

public class PublisherHomeFragment extends Fragment {

    RecyclerView recyclerView;
    private List<MagazineDetails> updateMagazineModelList;
   // MagazineDetails info = new MagazineDetails(title, quantity, price, descrption, sImage, PublisherId);
    private PublisherHomeAdapter adapter;
    DatabaseReference dataa;
    ImageView noMagazine,loading;
    private String Town,City,Area;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_publisher_home,null);

        v.setBackground(null);

        getActivity().setTitle("Home");

        setHasOptionsMenu(true);



        noMagazine=v.findViewById(R.id.no_magazine);
        loading=v.findViewById(R.id.loading_magazines);


        noMagazine.setVisibility(View.GONE);
        loading.setVisibility(View.VISIBLE);

        recyclerView = v.findViewById(R.id.Recycle_catalog);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        updateMagazineModelList = new ArrayList<>();
        String userid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        dataa = FirebaseDatabase.getInstance().getReference("Publisher").child(userid);
        dataa.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Publisher publisherr = snapshot.getValue(Publisher.class);
                if (publisherr != null) {
                    Town = publisherr.getTown();
                    City = publisherr.getCity();
                    Area = publisherr.getArea();
                    publisherMagazines();
                }
                else{
                    Town = "Default Town";
                    City = "Default City";
                    Area = "Default Area";
                    publisherMagazines();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




        return v;
    }

    private void publisherMagazines() {

        String useridd = FirebaseAuth.getInstance().getCurrentUser().getUid();
       // DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("MagazineDetails").child(Town).child(City).child(Area).child(useridd);
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("MagazineDetails");


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                updateMagazineModelList.clear();

                if (snapshot.child(useridd).exists()) {
                    for (DataSnapshot snapshot1 : snapshot.child(useridd).getChildren()) {

                       Log.e("Magazine Id", snapshot1.getKey());

                           MagazineDetails updateMagazineModel = snapshot1.getValue(MagazineDetails.class);

                            Log.e("Magazine Title", snapshot1.child("title").getValue().toString());
                            Log.e("Magazine Quantity", snapshot1.child("quantity").getValue().toString());
                            Log.e("Magazine Price", snapshot1.child("price").getValue().toString());
                            Log.e("Magazine Description", snapshot1.child("description").getValue().toString());
                          //  Log.e("Magazine Image", snapshot1.child("imageURL").getValue().toString());
                            Log.e("Magazine PublisherId", snapshot1.child("publisherid").getValue().toString());


                           updateMagazineModelList.add(updateMagazineModel);

                    }
                }
                adapter = new PublisherHomeAdapter(getContext(),updateMagazineModelList);
                recyclerView.setAdapter(adapter);



                if (updateMagazineModelList.isEmpty()){
                    noMagazine.setVisibility(View.VISIBLE);
                    loading.setVisibility(View.GONE);
                }
                else{
                    noMagazine.setVisibility(View.GONE);
                    loading.setVisibility(View.GONE);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Error"+error.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("Error",error.getMessage());

            }
        });
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
