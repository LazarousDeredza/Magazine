package my.readme.app.customerMagPanel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;

import java.util.List;

import com.example.namespace.R;

public class CustomerHomeAdapter extends RecyclerView.Adapter<CustomerHomeAdapter.ViewHolder> {

    private Context mcontext;
    private List<UpdateMagazineModel>updateMagazineModelList;
    DatabaseReference databaseReference;

    public CustomerHomeAdapter(Context context , List<UpdateMagazineModel>updateMagazineModelList){

        this.updateMagazineModelList = updateMagazineModelList;
        this.mcontext = context;
    }


    @NonNull
    @Override
    public CustomerHomeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mcontext).inflate(R.layout.customer_menumagazine,parent,false);
        return new CustomerHomeAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomerHomeAdapter.ViewHolder holder, int position) {

        final UpdateMagazineModel updateMagazineModel = updateMagazineModelList.get(position);
        //Glide.with(mcontext).load(updateMagazineModel.getImageURL()).into(holder.imageView);
        holder.Title.setText(updateMagazineModel.getTitle());
        updateMagazineModel.getPublisherId();
        holder.Price.setText("Price: $ "+updateMagazineModel.getPrice());

    }

    @Override
    public int getItemCount() {
        if (updateMagazineModelList != null) {
            return updateMagazineModelList.size();
        } else {
            return 0;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView Title,Price;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.menu_image);
            Title = itemView.findViewById(R.id.title);
            Price = itemView.findViewById(R.id.price);
        }
    }

}