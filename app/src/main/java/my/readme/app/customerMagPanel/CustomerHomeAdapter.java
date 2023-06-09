package my.readme.app.customerMagPanel;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.view.ActionProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.List;

import com.example.namespace.R;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import my.readme.app.publisherMagPanel.MagazineDetails;
import my.readme.app.publisherMagPanel.publisher_postMagazine;

public class CustomerHomeAdapter extends RecyclerView.Adapter<CustomerHomeAdapter.ViewHolder> {

    private Context mcontext;
    private List<UpdateMagazineModel> updateMagazineModelList;
    DatabaseReference databaseReference;

    String userid = FirebaseAuth.getInstance().getCurrentUser().getUid();

    public CustomerHomeAdapter(Context context, List<UpdateMagazineModel> updateMagazineModelList) {

        this.updateMagazineModelList = updateMagazineModelList;
        this.mcontext = context;
    }


    @NonNull
    @Override
    public CustomerHomeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mcontext).inflate(R.layout.customer_menumagazine, parent, false);
        return new CustomerHomeAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomerHomeAdapter.ViewHolder holder, int position) {

        final UpdateMagazineModel updateMagazineModel = updateMagazineModelList.get(position);
        //Glide.with(mcontext).load(updateMagazineModel.getImageURL()).into(holder.imageView);
        holder.Title.setText(updateMagazineModel.getTitle());
        String title = updateMagazineModel.getTitle();
        String publisherId = updateMagazineModel.getPublisherid();

        String price = updateMagazineModel.getPrice();
        String description = updateMagazineModel.getDescription();

        String sImage = updateMagazineModel.getImageURL();
        String quantity = updateMagazineModel.getQuantity();
        holder.Price.setText("Price: $ " + updateMagazineModel.getPrice());
        //initialise byte array from encoded string
        byte[] bytes = Base64.decode(updateMagazineModel.getImageURL(), Base64.DEFAULT);
        //Initialize bitmap
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        //Set bitmap on image view
        holder.imageView.setImageBitmap(bitmap);


        final boolean[] liked = {false};


        holder.like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!liked[0]) {
                    holder.like.setImageDrawable(v.getResources().getDrawable(R.drawable.fav_marked));
                    liked[0] =true;
                } else {
                    holder.like.setImageDrawable(v.getResources().getDrawable(R.drawable.fav_unmarked));
                    liked[0]=false;
                }
            }
        });


        databaseReference = FirebaseDatabase.getInstance().getReference("Customer").child(userid);

        holder.btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toast.makeText(v.getContext(), "Making your order please wait !!",Toast.LENGTH_SHORT).show();

                ProgressDialog p = new ProgressDialog(v.getContext());
                p.setMessage("Adding to cart \nplease wait");
                p.setCancelable(false);
                p.show();

                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {



                        MagazineDetails info = new MagazineDetails(title, quantity, price, description, sImage, publisherId);

                        databaseReference.child("cart").child(title).setValue(info);
                        p.dismiss();
                        Toast.makeText(v.getContext(),  "Magazine added to cart Successfully!", Toast.LENGTH_SHORT).show();



                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

        holder.btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toast.makeText(v.getContext(), "Making your order please wait !!",Toast.LENGTH_SHORT).show();

                ProgressDialog p = new ProgressDialog(v.getContext());
                p.setMessage("Placing your order please wait");
                p.setCancelable(false);
                p.show();

                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {



                        MagazineDetails info = new MagazineDetails(title, quantity, price, description, sImage, publisherId,"Pending");

                        databaseReference.child("orders").child(title).setValue(info);

                        p.dismiss();
                        Toast.makeText(v.getContext(),  "Order placed Successfully!", Toast.LENGTH_SHORT).show();



                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

    }

    @Override
    public int getItemCount() {
        if (updateMagazineModelList != null) {
            return updateMagazineModelList.size();
        } else {
            return 0;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView, like;
        TextView Title, Price;

        Button btnOrder, btnAddToCart;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.menu_image);
            Title = itemView.findViewById(R.id.title);
            Price = itemView.findViewById(R.id.price);
            btnOrder = itemView.findViewById(R.id.btnOder);
            btnAddToCart = itemView.findViewById(R.id.btnAddToCart);
            like = itemView.findViewById(R.id.fav);


        }
    }

}