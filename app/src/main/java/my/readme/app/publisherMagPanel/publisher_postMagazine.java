package my.readme.app.publisherMagPanel;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.namespace.R;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class publisher_postMagazine extends AppCompatActivity {

    ImageButton imageButton;
    Button post_magazine;
    boolean uploaded;

    String sImage;
    TextInputLayout tit, desc, qty, pri;
    String title, descrption, quantity, price, magazines;
    Uri imageuri;
    private Uri mcropimageuri;
    FirebaseStorage storage;
    StorageReference storageReference;

    DatabaseReference databaseReference, dataa;
    FirebaseAuth Fauth;
    StorageReference ref;
    String PublisherId, RandomUID, Town, City, Area;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publisher_post_magazine);

        uploaded = false;
        sImage = "";

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        //Magazines = (Spinner)findViewById(R.id.magazines);
        tit = (TextInputLayout) findViewById(R.id.title);
        desc = (TextInputLayout) findViewById(R.id.description);
        qty = (TextInputLayout) findViewById(R.id.Quantity);
        pri = (TextInputLayout) findViewById(R.id.price);
        post_magazine = (Button) findViewById(R.id.post);
        Fauth = FirebaseAuth.getInstance();

        databaseReference = FirebaseDatabase.getInstance().getReference("MagazineDetails");

        try {
            String userid = FirebaseAuth.getInstance().getCurrentUser().getUid();
            dataa = FirebaseDatabase.getInstance().getReference("Publisher");
            dataa.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    Log.e("User ID :", userid);


                    //L66uP8Yxb2ROHrYpdJ0Ob0zCAF92 publisher
                    //L66uP8Yxb2ROHrYpdJ0Ob0zCAF92 user


                    String fName = snapshot.child(userid).child("fname").getValue().toString();
                    Log.e("Publisher name : ", fName);

                    Publisher publisherr = snapshot.child(userid).getValue(Publisher.class);

                    assert publisherr != null;
                    System.out.println("User details : " + publisherr.getFname() + publisherr.getLname() + publisherr.getHouse() + publisherr.getArea() + publisherr.getTown() + publisherr.getCity() + publisherr.getPostcode() + publisherr.getMobile() + publisherr.getEmailid());

                    if (publisherr != null) {
                        Town = publisherr.getTown();
                        City = publisherr.getCity();
                        Area = publisherr.getArea();
                        imageButton = (ImageButton) findViewById(R.id.image_upload);

                        imageButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //Check if permission to read storage
                                if (ContextCompat.checkSelfPermission(publisher_postMagazine.this,
                                        Manifest.permission.READ_EXTERNAL_STORAGE)
                                        != PackageManager.PERMISSION_GRANTED) {
                                    //When permission not granted , Request
                                    ActivityCompat.requestPermissions(publisher_postMagazine.this,
                                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 100);
                                } else {
                                    //When permission is granted

                                    selectImage();
                                }
                            }
                        });
                        post_magazine.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //magazines = Magazines.getSelectedItem().toString().trim();
                                title = tit.getEditText().getText().toString().trim();
                                descrption = desc.getEditText().getText().toString().trim();
                                quantity = qty.getEditText().getText().toString().trim();
                                price = pri.getEditText().getText().toString().trim();

                                if (isValid()) {
                                    uploadImage();
                                }
                            }
                        });
                    } else {
                        Toast.makeText(publisher_postMagazine.this, "Error: Publisher is null", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                    Toast.makeText(publisher_postMagazine.this, "Database Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();

                }
            });
        } catch (Exception e) {
            Log.e("Error: ", e.getMessage());
            Toast.makeText(publisher_postMagazine.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    private void selectImage() {
        //Clear previous data
        imageButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_camera_24));
        //initialise intent
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);

        //Set type
        intent.setType("image/*");


        startActivityForResult(Intent.createChooser(intent, "Select Image"),
                100);

    }

    private void uploadImage() {

        if (uploaded) {
            final ProgressDialog progressDialog = new ProgressDialog(publisher_postMagazine.this);
            progressDialog.setTitle("Uploading.....");
            progressDialog.setCancelable(false);
            progressDialog.show();


            PublisherId = FirebaseAuth.getInstance().getCurrentUser().getUid();


            MagazineDetails info = new MagazineDetails(title, quantity, price, descrption, sImage, PublisherId);

            databaseReference.child(PublisherId).child(title).setValue(info);
            progressDialog.dismiss();
            Toast.makeText(publisher_postMagazine.this, "Magazine Posted Successfully!", Toast.LENGTH_SHORT).show();
            clear();


        } else {
            Toast.makeText(publisher_postMagazine.this, "Please provide an image", Toast.LENGTH_SHORT).show();
        }

    }

    private void clear() {
        imageButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_camera_24));
        uploaded = false;
        tit.getEditText().setText("");
        desc.getEditText().setText("");
        qty.getEditText().setText("");
        pri.getEditText().setText("");

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
        if (TextUtils.isEmpty(descrption)) {
            desc.setErrorEnabled(true);
            desc.setError("Description is Required");
        } else {
            desc.setError(null);
            isValidDescription = true;
        }
        if (TextUtils.isEmpty(quantity)) {
            qty.setErrorEnabled(true);
            qty.setError("Enter  quantity");
        } else {
            isValidQuantity = true;
        }
        if (TextUtils.isEmpty(price)) {
            pri.setErrorEnabled(true);
            pri.setError("Please Mention Price");
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
        // CropImage.startPickImageActivity(this);
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
      /*  if (requestCode == CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            imageuri = CropImage.getPickImageResultUri(this, data);
            if (CropImage.isReadExternalStoragePermissionsRequired(this, imageuri)) {
                mcropimageuri = imageuri;
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
            } else {
                startCropImageActivity(imageuri);

            }
        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                ((ImageButton) findViewById(R.id.image_upload)).setImageURI(result.getUri());
                Toast.makeText(this, "Cropped Successfully!", Toast.LENGTH_SHORT).show();
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Toast.makeText(this, "Failed To Crop" + result.getError(), Toast.LENGTH_SHORT).show();

            }
        }*/


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


        }


    }


    //Funtion to decode string back to image

    private void decode() {
        //initialise byte array from encoded string
        byte[] bytes = Base64.decode(sImage, Base64.DEFAULT);

        //Initialize bitmap
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        //Set bitmap on image view
        imageButton.setImageBitmap(bitmap);
    }
}