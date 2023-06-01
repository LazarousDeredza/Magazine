package my.readme.app.customerMagPanel;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.namespace.R;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hbb20.CountryCodePicker;

import java.util.ArrayList;
import java.util.List;

import my.readme.app.MainMenu;

public class Customer_Profile_Frag extends Fragment {

    String[] Lefke = {"Gemikonagi", "Yedidalga", "Yesilyurt"};
    String[] Lefkosa = {"Gonyeli", "Hamitkoy", "Haspolat"};

    TextInputLayout Fname,Lname,Email,Pass,cpass,mobileno,localaddress,area,pincode;
    Spinner Townspin,Cityspin;
    Button update;
    CountryCodePicker Cpp;
    FirebaseAuth FAuth;
    DatabaseReference databaseReference;

    String fname,lname,emailid,password,confpassword,mobile,Localaddress,Area,Pincode,townn,cityy;
    String role="Customer";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_customerprofile, null);
        getActivity().setTitle("Welcome to ReadMe");



        Fname = (TextInputLayout)v.findViewById(R.id.Fname);
        Lname = (TextInputLayout)v.findViewById(R.id.Lname);
        Email = (TextInputLayout)v.findViewById(R.id.Emailid);
        Pass = (TextInputLayout)v.findViewById(R.id.Password);
        cpass = (TextInputLayout)v.findViewById(R.id.confirmpass);
        mobileno = (TextInputLayout)v.findViewById(R.id.Mobilenumber);
        localaddress = (TextInputLayout)v.findViewById(R.id.Localaddress);
        pincode = (TextInputLayout)v.findViewById(R.id.Pincodee);
        Cityspin = (Spinner) v.findViewById(R.id.Cityy);
        Townspin = (Spinner)v. findViewById(R.id.Towns);
        area = (TextInputLayout)v.findViewById(R.id.Area);

        update = (Button)v.findViewById(R.id.button);


        Cpp = (CountryCodePicker)v.findViewById(R.id.CountryCode);



       /* name = v.findViewById(R.id.Fname);


        reference= FirebaseDatabase.getInstance().getReference("Customer").child(FirebaseAuth.getInstance().getCurrentUser().getUid());


        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                    String Name = snapshot.child("First Name").getValue().toString();
                    name.setText(Name);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });*/

        databaseReference= FirebaseDatabase.getInstance().getReference().child("Customer").child(FirebaseAuth.getInstance().getCurrentUser().getUid());

        ProgressDialog pd = new ProgressDialog(getActivity());
        pd.setTitle("Please wait...");
        pd.setMessage("Getting your Profile");
        pd.setCancelable(false);
        pd.show();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String Fnamee = snapshot.child("First Name").getValue().toString();
                String Lnamee = snapshot.child("Last Name").getValue().toString();
                String Emailid = snapshot.child("EmailId").getValue().toString();
                String Password = snapshot.child("Password").getValue().toString();
                String Confpassword = snapshot.child("Confirm Password").getValue().toString();
                String Mobilenumber = snapshot.child("Mobile No").getValue().toString();
                String Localaddresss = snapshot.child("Local Address").getValue().toString();
                String Areaa = snapshot.child("Area").getValue().toString();
                String Pincodee = snapshot.child("Pincode").getValue().toString();
                String Cityy = snapshot.child("City").getValue().toString();
              //  String Townn = snapshot.child("Town").getValue().toString();

                Fname.getEditText().setText(Fnamee);
                Lname.getEditText().setText(Lnamee);
                Email.getEditText().setText(Emailid);
                Pass.getEditText().setText(Password);
                cpass.getEditText().setText(Confpassword);
                mobileno.getEditText().setText(Mobilenumber);
                localaddress.getEditText().setText(Localaddresss);
                area.getEditText().setText(Areaa);
                pincode.getEditText().setText(Pincodee);
               // Townspin.setPrompt(Townn);
                Cityspin.setPrompt(Cityy);


                Log.e("First Name",Fnamee);
                Log.e("Last Name",Lnamee);
                Log.e("Email",Emailid);
                Log.e("Password",Password);
                Log.e("Mobile Number",Mobilenumber);
                Log.e("Local Address",Localaddresss);
                Log.e("Area",Areaa);
                Log.e("Pincode",Pincodee);
                Log.e("City",Cityy);
               // Log.e("Town",Townn);

                pd.dismiss();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

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