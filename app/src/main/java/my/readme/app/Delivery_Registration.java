package my.readme.app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.namespace.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hbb20.CountryCodePicker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;

public class Delivery_Registration extends AppCompatActivity {

    String[] Lefke = {"Gemikonagi", "Yedidalga", "Yesilyurt"};
    String[] Lefkosa = {"Gonyeli", "Hamitkoy", "Haspolat"};

    TextInputLayout Fname, Lname, Email, Pass, cpass, mobileno, houseno, area, pincode;
    Spinner Cityspin, Townspin;
    Button signup, Emaill, Phone;
    CountryCodePicker Cpp;
    FirebaseAuth FAuth;
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;
    String fname, lname, emailid, password, confpassword, mobile, house, Area, Pincode, role = "DeliveryPerson", cityy, townn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_registration);

        Fname = (TextInputLayout) findViewById(R.id.fname);
        Lname = (TextInputLayout) findViewById(R.id.lastname);
        Email = (TextInputLayout) findViewById(R.id.Emailid);
        Pass = (TextInputLayout) findViewById(R.id.password);
        cpass = (TextInputLayout) findViewById(R.id.confirmpassword);
        mobileno = (TextInputLayout) findViewById(R.id.mobileno);
        houseno = (TextInputLayout) findViewById(R.id.Houseno);
        pincode = (TextInputLayout) findViewById(R.id.Pincodee);
        Cityspin = (Spinner) findViewById(R.id.city);
        Townspin = (Spinner) findViewById(R.id.town);
        area = (TextInputLayout) findViewById(R.id.Areaa);

        signup = (Button)findViewById(R.id.Signupp);
        Emaill = (Button)findViewById(R.id.emaillid);
        Phone = (Button)findViewById(R.id.Phonenumber);

        Cpp = (CountryCodePicker)findViewById(R.id.ctrycode);

        Cityspin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Object value = parent.getItemAtPosition(position);
                cityy = value.toString().trim();
                if(cityy.equals("Lefke")){
                    ArrayList<String> list = new ArrayList<>();
                    for (String cities : Lefke) {
                        list.add(cities);
                    }
                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(Delivery_Registration.this,android.R.layout.simple_spinner_item,list);
                    Townspin.setAdapter(arrayAdapter);
                }
                if(cityy.equals("Lefkosa")){
                    ArrayList<String> list = new ArrayList<>();
                    for (String cities : Lefkosa) {
                        list.add(cities);
                    }
                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(Delivery_Registration.this,android.R.layout.simple_spinner_item,list);
                    Townspin.setAdapter(arrayAdapter);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Townspin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Object value = parent.getItemAtPosition(position);
                townn = value.toString().trim();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        databaseReference = FirebaseDatabase.getInstance().getReference("Publisher");
        FAuth = FirebaseAuth.getInstance();

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fname = Objects.requireNonNull(Fname.getEditText()).getText().toString().trim();
                lname = Objects.requireNonNull(Lname.getEditText()).getText().toString().trim();
                emailid = Objects.requireNonNull(Email.getEditText()).getText().toString().trim();
                mobile = Objects.requireNonNull(mobileno.getEditText()).getText().toString().trim();
                password = Objects.requireNonNull(Pass.getEditText()).getText().toString().trim();
                confpassword = Objects.requireNonNull(cpass.getEditText()).getText().toString().trim();
                Area = Objects.requireNonNull(area.getEditText()).getText().toString().trim();
                house = Objects.requireNonNull(houseno.getEditText()).getText().toString().trim();
                Pincode = Objects.requireNonNull(pincode.getEditText()).getText().toString().trim();

                if (isValid()){
                    final ProgressDialog mDialog = new ProgressDialog(Delivery_Registration.this);
                    mDialog.setCancelable(false);
                    mDialog.setCanceledOnTouchOutside(false);
                    mDialog.setMessage("Registration in progress please wait......");
                    mDialog.show();

                    FAuth.createUserWithEmailAndPassword(emailid,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()){
                                String useridd = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
                                databaseReference = FirebaseDatabase.getInstance().getReference("User").child(useridd);
                                final HashMap<String , String> hashMap = new HashMap<>();
                                hashMap.put("Role",role);
                                databaseReference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {

                                        HashMap<String , String> hashMap1 = new HashMap<>();
                                        hashMap1.put("Mobile No",mobile);
                                        hashMap1.put("First Name",fname);
                                        hashMap1.put("Last Name",lname);
                                        hashMap1.put("EmailId",emailid);
                                        hashMap1.put("Town",townn);
                                        hashMap1.put("Area",Area);
                                        hashMap1.put("Password",password);
                                        hashMap1.put("Pincode",Pincode);
                                        hashMap1.put("City",cityy);
                                        hashMap1.put("Confirm Password",confpassword);
                                        hashMap1.put("House",house);

                                        FirebaseDatabase.getInstance().getReference("DeliveryPerson")
                                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                .setValue(hashMap1).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        mDialog.dismiss();

                                                        Objects.requireNonNull(FAuth.getCurrentUser()).sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {

                                                                if(task.isSuccessful()){
                                                                    AlertDialog.Builder builder = new AlertDialog.Builder(Delivery_Registration.this);
                                                                    builder.setMessage("You Have Registered! Make Sure To Verify Your Email");
                                                                    builder.setCancelable(false);
                                                                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                                                        @Override
                                                                        public void onClick(DialogInterface dialog, int which) {

                                                                            dialog.dismiss();

                                                                            String phonenumber = Cpp.getSelectedCountryCodeWithPlus() + mobile;
                                                                            Intent b = new Intent(Delivery_Registration.this, PublisherVerifyPhone.class);
                                                                            b.putExtra("phonenumber",phonenumber);
                                                                            startActivity(b);

                                                                        }
                                                                    });
                                                                    AlertDialog Alert = builder.create();
                                                                    Alert.show();
                                                                }else{
                                                                    mDialog.dismiss();
                                                                    ReusableCodeForAll.ShowAlert(Delivery_Registration.this,"Error", Objects.requireNonNull(task.getException()).getMessage());
                                                                }
                                                            }
                                                        });

                                                    }
                                                });

                                    }
                                });
                            }else{
                                mDialog.dismiss();
                                ReusableCodeForAll.ShowAlert(Delivery_Registration.this,"Error", Objects.requireNonNull(task.getException()).getMessage());

                            }
                        }
                    });
                }
            }
        });

        Emaill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(Delivery_Registration.this,Delivery_Login.class));
                finish();
            }
        });
        Phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Delivery_Registration.this,Delivery_Loginphone.class));
                finish();
            }
        });
    }
    String emailpattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    public boolean isValid(){
        Email.setErrorEnabled(false);
        Email.setError("");
        Fname.setErrorEnabled(false);
        Fname.setError("");
        Lname.setErrorEnabled(false);
        Lname.setError("");
        Pass.setErrorEnabled(false);
        Pass.setError("");
        mobileno.setErrorEnabled(false);
        mobileno.setError("");
        cpass.setErrorEnabled(false);
        cpass.setError("");
        area.setErrorEnabled(false);
        area.setError("");
        houseno.setErrorEnabled(false);
        houseno.setError("");
        pincode.setErrorEnabled(false);
        pincode.setError("");

        boolean isValid=false,isValidhouseno=false,isValidlname=false,isValidname=false,isValidemail=false,isValidpassword=false,isValidconfpassword=false,isValidmobilenum=false,isValidarea=false,isValidpincode=false;
        if(TextUtils.isEmpty(fname)){
            Fname.setErrorEnabled(true);
            Fname.setError("Enter First Name");
        }else{
            isValidname = true;
        }
        if(TextUtils.isEmpty(lname)){
            Lname.setErrorEnabled(true);
            Lname.setError("Enter Last Name");
        }else{
            isValidlname = true;
        }
        if(TextUtils.isEmpty(emailid)){
            Email.setErrorEnabled(true);
            Email.setError("Email Is Required");
        }else{
            if(emailid.matches(emailpattern)){
                isValidemail = true;
            }else{
                Email.setErrorEnabled(true);
                Email.setError("Enter a Valid Email Id");
            }
        }
        if(TextUtils.isEmpty(password)){
            Pass.setErrorEnabled(true);
            Pass.setError("Enter Password");
        }else{
            if(password.length()<8){
                Pass.setErrorEnabled(true);
                Pass.setError("Password is Weak");
            }else{
                isValidpassword = true;
            }
        }
        if(TextUtils.isEmpty(confpassword)){
            cpass.setErrorEnabled(true);
            cpass.setError("Enter Password Again");
        }else{
            if(!password.equals(confpassword)){
                cpass.setErrorEnabled(true);
                cpass.setError("Password Dosen't Match");
            }else{
                isValidconfpassword = true;
            }
        }
        if(TextUtils.isEmpty(mobile)){
            mobileno.setErrorEnabled(true);
            mobileno.setError("Mobile Number Is Required");
        }else{
            if(mobile.length()<10){
                mobileno.setErrorEnabled(true);
                mobileno.setError("Invalid Mobile Number");
            }else{
                isValidmobilenum = true;
            }
        }
        if(TextUtils.isEmpty(Area)){
            area.setErrorEnabled(true);
            area.setError("Area Is Required");
        }else{
            isValidarea = true;
        }
        if(TextUtils.isEmpty(Pincode)){
            pincode.setErrorEnabled(true);
            pincode.setError("Please Enter Pincode");
        }else{
            isValidpincode = true;
        }
        if(TextUtils.isEmpty(house)){
            houseno.setErrorEnabled(true);
            houseno.setError("Fields Can't Be Empty");
        }else{
            isValidhouseno = true;
        }

        isValid = isValidarea && isValidconfpassword && isValidpassword && isValidpincode && isValidemail && isValidmobilenum && isValidname && isValidhouseno && isValidlname;
        return isValid;


    }
}


















