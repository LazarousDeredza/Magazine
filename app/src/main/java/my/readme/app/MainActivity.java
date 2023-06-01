package my.readme.app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.namespace.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    ImageView imageView;
    TextView textView;
    FirebaseAuth Fauth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        checkPermissions();
        imageView = (ImageView) findViewById(R.id.imageView);
        textView = (TextView) findViewById(R.id.textView7);

        imageView.animate().alpha(0f).setDuration(0);
        textView.animate().alpha(0f).setDuration(0);


        imageView.animate().alpha(1f).setDuration(1000).setListener(new AnimatorListenerAdapter() {

            @Override
            public void onAnimationEnd(Animator animation) {
                textView.animate().alpha(1f).setDuration(800);

            }
        });
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Fauth = FirebaseAuth.getInstance();
                if (Fauth.getCurrentUser() != null) {
                    if (Fauth.getCurrentUser().isEmailVerified()) {
                        Fauth = FirebaseAuth.getInstance();

                        databaseReference = FirebaseDatabase.getInstance().getReference("User").child(FirebaseAuth.getInstance().getUid() + "/Role");
                        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                String role = snapshot.getValue(String.class);
                                if (role.equals("Publisher")) {
                                    startActivity(new Intent(MainActivity.this, PublisherMagPanel_BottomNavigation.class));
                                    finish();

                                }
                                if (role.equals("Customer")) {
                                    startActivity(new Intent(MainActivity.this, CustomerMagPanel_BottomNavigation.class));
                                    finish();

                                }
                                if (role.equals("DeliveryPerson")) {
                                    startActivity(new Intent(MainActivity.this, DeliveryMagPanel_BottomNavigation.class));
                                    finish();

                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();

                            }
                        });
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setMessage("Check Whether You Have Verified Your Detail , Otherwise Please Verify");
                        builder.setCancelable(false);
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                Intent intent = new Intent(MainActivity.this, MainMenu.class);
                                startActivity(intent);
                                finish();
                            }
                        });
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                        Fauth.signOut();
                    }
                } else {


                    Intent intent = new Intent(MainActivity.this, MainMenu.class);
                    startActivity(intent);
                    finish();
                }

            }
        }, 3000);

    }


    public void checkPermissions() {
        Dexter.withContext(this)
                .withPermissions(


                Manifest.permission.INTERNET,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.RECEIVE_SMS,
                Manifest.permission.READ_SMS,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.ACCESS_WIFI_STATE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA


                ).withListener(new MultiplePermissionsListener() {
            @Override
            public void onPermissionsChecked(MultiplePermissionsReport report) {/* ... */}

            @Override
            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                token.continuePermissionRequest();
            }
        }).check();
    }
}
