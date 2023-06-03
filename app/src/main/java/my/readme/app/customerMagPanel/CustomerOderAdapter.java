package my.readme.app.customerMagPanel;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.namespace.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import my.readme.app.publisherMagPanel.MagazineDetails;

public class CustomerOderAdapter extends RecyclerView.Adapter<CustomerOderAdapter.ViewHolder> {

    private Context mcontext;
    private List<UpdateMagazineModel> updateMagazineModelList;
    DatabaseReference databaseReference;


    String userid = FirebaseAuth.getInstance().getCurrentUser().getUid();

    public CustomerOderAdapter(Context context, List<UpdateMagazineModel> updateMagazineModelList) {

        this.updateMagazineModelList = updateMagazineModelList;
        this.mcontext = context;
    }


    @NonNull
    @Override
    public CustomerOderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mcontext).inflate(R.layout.order_customer_menumagazine, parent, false);
        return new CustomerOderAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomerOderAdapter.ViewHolder holder, int position) {

        final UpdateMagazineModel updateMagazineModel = updateMagazineModelList.get(position);
        //Glide.with(mcontext).load(updateMagazineModel.getImageURL()).into(holder.imageView);
        holder.Title.setText(updateMagazineModel.getTitle());
        updateMagazineModel.getPublisherid();
        holder.Price.setText("Price: $ " + updateMagazineModel.getPrice());

        String title = updateMagazineModel.getTitle();
        String publisherId = updateMagazineModel.getPublisherid();

        String price = updateMagazineModel.getPrice();
        String description = updateMagazineModel.getDescription();

        String sImage = updateMagazineModel.getImageURL();
        String quantity = updateMagazineModel.getQuantity();
        String status = updateMagazineModel.getStatus();

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


        holder.cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toast.makeText(v.getContext(), "Making your order please wait !!",Toast.LENGTH_SHORT).show();

                ProgressDialog p = new ProgressDialog(v.getContext());
                p.setMessage("Canceling Your order \nplease wait");
                p.setCancelable(false);
                p.show();

                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {


                        databaseReference.child("orders").child(title).child("status").setValue("Canceled");
                        p.dismiss();
                        Toast.makeText(v.getContext(), "Order cancelled Successfully!", Toast.LENGTH_SHORT).show();

                        notifyDataSetChanged();


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });


        holder.status.setText(status);
        if (status.equals("Pending")) {
            holder.status.setBackground(mcontext.getResources().getDrawable(R.drawable.btnblueish));


        } else if (status.equals("Canceled")) {
            holder.status.setBackground(mcontext.getResources().getDrawable(R.drawable.btnred));
        } else if (status.equals("Delivered")) {
            holder.status.setBackground(mcontext.getResources().getDrawable(R.drawable.button_shape));
        }


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
        TextView Title, Price, status;
        Button cancel;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.menu_image);
            Title = itemView.findViewById(R.id.title);
            Price = itemView.findViewById(R.id.price);
            status = itemView.findViewById(R.id.status);

            like = itemView.findViewById(R.id.like);
            cancel = itemView.findViewById(R.id.remove);


        }
    }

}