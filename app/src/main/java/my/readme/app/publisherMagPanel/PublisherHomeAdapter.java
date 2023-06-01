package my.readme.app.publisherMagPanel;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.namespace.R;

import java.util.List;

import my.readme.app.customerMagPanel.UpdateMagazineModel;

public class PublisherHomeAdapter extends RecyclerView.Adapter<PublisherHomeAdapter.ViewHolder> {

    private Context mcont;
    private List<MagazineDetails> updateMagazineModelList;

    public PublisherHomeAdapter(Context context , List<MagazineDetails>updateMagazineModelList){
        this.updateMagazineModelList = updateMagazineModelList;
        this.mcont = context;
    }

    @NonNull
    @Override
    public PublisherHomeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mcont).inflate(R.layout.publishercatalog_update_delete,parent,false);
        return new PublisherHomeAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PublisherHomeAdapter.ViewHolder holder, int position) {

        final MagazineDetails updateMagazineModel = updateMagazineModelList.get(position);
        holder.magazines.setText(updateMagazineModel.getTitle());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mcont,UpdateDelete_Magazine.class);
                intent.putExtra("title",updateMagazineModel.getTitle());
                intent.putExtra("model",updateMagazineModel.toString());
                mcont.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return updateMagazineModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView magazines;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            magazines = itemView.findViewById(R.id.magazine_title);
        }
    }
}


