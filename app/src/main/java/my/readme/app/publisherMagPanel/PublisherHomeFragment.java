package my.readme.app.publisherMagPanel;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
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
    private List<UpdateMagazineModel> updateMagazineModelList;
    private PublisherHomeAdapter adapter;
    DatabaseReference dataa;
    private String Town,City,Area;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_publisher_home,null);

        v.setBackground(null);


        getActivity().setTitle("Home");

        setHasOptionsMenu(true);

        Toast.makeText(getContext(),"Loading your magazines",Toast.LENGTH_SHORT).show();

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


       /* if(updateMagazineModelList.size()==0){

            v.setBackground(getResources().getDrawable(R.drawable.nomagazinehome));
        }else {

            v.setBackground(null);
        }*/

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
                        UpdateMagazineModel updateMagazineModel = snapshot1.getValue(UpdateMagazineModel.class);
                        updateMagazineModelList.add(updateMagazineModel);
                    }
                }
                adapter = new PublisherHomeAdapter(getContext(),updateMagazineModelList);
                recyclerView.setAdapter(adapter);




            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

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
