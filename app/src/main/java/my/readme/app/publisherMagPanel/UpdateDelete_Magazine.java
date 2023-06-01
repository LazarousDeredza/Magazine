package my.readme.app.publisherMagPanel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import android.Manifest;

import com.example.namespace.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.UUID;

import my.readme.app.PublisherMagPanel_BottomNavigation;
import my.readme.app.customerMagPanel.UpdateMagazineModel;

public class UpdateDelete_Magazine extends AppCompatActivity {

    TextInputLayout tit, desc, qty, pri;
    TextView Title;
    ImageButton imageButton;

    boolean uploaded;

    String sImage;

    Uri imageuri;
    String dburi;
    private Uri mCropimageuri;
    Button Update_magazine, Delete_magazine;
    String description, quantity, price, magazines, PublisherId;
    String RandomUID;
    StorageReference ref;
    FirebaseStorage storage;
    StorageReference storageReference;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseAuth FAuth;
    String title;
    private ProgressDialog progressDialog;
    DatabaseReference dataa;
    String Town, City, Area;

    String oldTitle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_delete_magazine);


        uploaded = false;
        sImage = "";

        tit = (TextInputLayout) findViewById(R.id.title);
        desc = (TextInputLayout) findViewById(R.id.description);
        qty = (TextInputLayout) findViewById(R.id.Quantity);
        pri = (TextInputLayout) findViewById(R.id.price);
       // Title = (TextView) findViewById(R.id.magazine_title);
        imageButton = (ImageButton) findViewById(R.id.image_upload);
        Update_magazine = (Button) findViewById(R.id.Updatemagazine);
        Delete_magazine = (Button) findViewById(R.id.DeleteMagazine);

        title = getIntent().getStringExtra("title");

        final String userid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        dataa = firebaseDatabase.getInstance().getReference("Publisher").child(userid);
        dataa.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Publisher publisher = snapshot.getValue(Publisher.class);
                Town = publisher.getTown();
                City = publisher.getCity();
                Area = publisher.getArea();

                Update_magazine.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        description = desc.getEditText().getText().toString().trim();
                        quantity = qty.getEditText().getText().toString().trim();
                        price = pri.getEditText().getText().toString().trim();
                        title=tit.getEditText().getText().toString().trim();

                        if (isValid()) {
                            if (sImage == null) {
                               Toast.makeText(UpdateDelete_Magazine.this,"Please Choose an Image",Toast.LENGTH_SHORT).show();
                            } else {



                                final ProgressDialog progressDialog = new ProgressDialog(UpdateDelete_Magazine.this);
                                progressDialog.setTitle("Updating .....");
                                progressDialog.setCancelable(false);
                                progressDialog.show();


                                PublisherId = FirebaseAuth.getInstance().getCurrentUser().getUid();


                                MagazineDetails info = new MagazineDetails(title, quantity, price, description, sImage, PublisherId);

                                databaseReference.child(PublisherId).child(oldTitle).removeValue();
                                databaseReference.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {


                                        databaseReference.child(PublisherId).child(title).setValue(info);
                                        progressDialog.dismiss();
                                        Toast.makeText(UpdateDelete_Magazine.this, "Magazine Updated Successfully!", Toast.LENGTH_SHORT).show();

                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {
                                        Toast.makeText(UpdateDelete_Magazine.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });





                            }
                        }

                    }
                });
                Delete_magazine.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(UpdateDelete_Magazine.this);
                        builder.setMessage("Are you sure you want to Delete Magazine");
                        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                firebaseDatabase.getInstance().getReference("MagazineDetails")
                                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(title).removeValue();

                                AlertDialog.Builder builder = new AlertDialog.Builder(UpdateDelete_Magazine.this);
                                builder.setMessage("Your magazine has been deleted successfully");
                                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        startActivity(new Intent(UpdateDelete_Magazine.this, PublisherMagPanel_BottomNavigation.class));
                                    }
                                });
                                AlertDialog alert = builder.create();
                                alert.show();
                            }
                        });
                        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                        AlertDialog alert = builder.create();
                        alert.show();
                    }
                });

                String useridd = FirebaseAuth.getInstance().getCurrentUser().getUid();
                progressDialog = new ProgressDialog(UpdateDelete_Magazine.this);
                progressDialog.setMessage("Please wait");
                


                // databaseReference.child(PublisherId).child(title).setValue(info);


                databaseReference = FirebaseDatabase.getInstance().getReference("MagazineDetails").child(useridd).child(title);
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        MagazineDetails updateMagazineModel = snapshot.getValue(MagazineDetails.class);
                        desc.getEditText().setText(updateMagazineModel.getDescription());
                        qty.getEditText().setText(updateMagazineModel.getQuantity());
                        tit.getEditText().setText(updateMagazineModel.getTitle());

                        pri.getEditText().setText(updateMagazineModel.getPrice());

                        oldTitle=updateMagazineModel.getTitle();

                        /*Glide.with(UpdateDelete_Magazine.this).load(updateMagazineModel.getImageURL()).into(imageButton);
                        dburi = updateMagazineModel.getImageURL();
*/


                        sImage=updateMagazineModel.getImageURL();

                        //initialise byte array from encoded string
                        byte[] bytes = Base64.decode(updateMagazineModel.getImageURL(), Base64.DEFAULT);

                        //Initialize bitmap
                        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                        //Set bitmap on image view
                        imageButton.setImageBitmap(bitmap);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                FAuth = FirebaseAuth.getInstance();
                databaseReference = firebaseDatabase.getInstance().getReference("MagazineDetails");


                imageButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Check if permission to read storage
                        if (ContextCompat.checkSelfPermission(UpdateDelete_Magazine.this,
                                Manifest.permission.READ_EXTERNAL_STORAGE)
                                != PackageManager.PERMISSION_GRANTED) {
                            //When permission not granted , Request
                            ActivityCompat.requestPermissions(UpdateDelete_Magazine.this,
                                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 100);
                        } else {
                            //When permission is granted

                            selectImage();
                        }
                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void updatedesc(String buri) {

        PublisherId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        MagazineDetails info = new MagazineDetails(magazines, quantity, price, description, buri, PublisherId);
        firebaseDatabase.getInstance().getReference("MagazineDetails")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(title)
                .setValue(info).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        progressDialog.dismiss();
                        Toast.makeText(UpdateDelete_Magazine.this, "Magazine Updated Successfully!", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void uploadImage() {

        if (imageuri != null) {

            progressDialog.setTitle("Uploading....");
            progressDialog.show();
            RandomUID = UUID.randomUUID().toString();
            ref = storageReference.child(RandomUID);
            ref.putFile(imageuri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            updatedesc(String.valueOf(uri));
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.dismiss();
                    Toast.makeText(UpdateDelete_Magazine.this, "Failed:" + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                    double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                    progressDialog.setMessage("Upload " + (int) progress + "%");
                    progressDialog.setCanceledOnTouchOutside(false);
                }
            });
        }

    }

    private boolean isValid() {

        tit.setErrorEnabled(false);
        tit.setError("");
        desc.setErrorEnabled(false);
        desc.setError("");
        qty.setErrorEnabled(false);
        qty.setError("");
        pri.setErrorEnabled(false);
        pri.setError("");

        boolean isValidDescription = false, isValidPrice = false, isValidQuantity = false, isValid = false, isValidtitle = false;
        if (TextUtils.isEmpty(title)) {
            tit.setErrorEnabled(true);
            tit.setError("Enter Magazine title");
        } else {
            tit.setError(null);
            isValidtitle = true;
        }
        if (TextUtils.isEmpty(description)) {
            desc.setErrorEnabled(true);
            desc.setError("Description is Required");
        } else {
            desc.setError(null);
            isValidDescription = true;
        }
        if (TextUtils.isEmpty(quantity)) {
            qty.setErrorEnabled(true);
            qty.setError("Enter Quantity");
        } else {
            isValidQuantity = true;
        }
        if (TextUtils.isEmpty(price)) {
            pri.setErrorEnabled(true);
            pri.setError("Please Enter Price");
        } else {
            isValidPrice = true;
        }
        isValid = (isValidDescription && isValidQuantity && isValidPrice && isValidtitle) ? true : false;
        return isValid;
    }

    private void startCropImageActivity(Uri imageuri) {
        CropImage.activity(imageuri)
                .setGuidelines(CropImageView.Guidelines.ON)
                .setMultiTouchEnabled(true)
                .start(this);
    }

    private void onSelectImageclick(View v) {
        CropImage.startPickImageActivity(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
       /* if (mcropimageuri != null && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            startCropImageActivity(mcropimageuri);
        } else {
            Toast.makeText(this, "Cancelling! Permission Not Granted", Toast.LENGTH_SHORT).show();
        }*/

        if (requestCode == 100 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            //when permission granted
            selectImage();
        }
    }

    @Override
    @SuppressLint("NewApi")
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);



        //Check condition
        if (requestCode == 100 && resultCode == RESULT_OK && data != null) {
            //when result is ok ,initialize uri
            Uri uri = data.getData();

            try {
                //initialize bitmap
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                //set image view to the selected image
                imageButton.setImageBitmap(bitmap);
                //initialize byte array stream
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                //compress bitmap
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                //initialize byte array
                byte[] bytes = stream.toByteArray();

                //get base 64 encoded String

                sImage = Base64.encodeToString(bytes, Base64.DEFAULT);
                uploaded = true;
                //Set Encoded String to textView
                //Toast.makeText(AddObject.this, sImage, Toast.LENGTH_SHORT).show();


            } catch (IOException e) {
                throw new RuntimeException(e);
            }


        }else if(requestCode==100 && resultCode==RESULT_CANCELED ){
            //Clear previous data
            imageButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_camera_24));
            sImage=null;
        }


    }


    private void selectImage() {

        //initialise intent
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);

        //Set type
        intent.setType("image/*");


        startActivityForResult(Intent.createChooser(intent, "Select Image"),
                100);

    }

}
