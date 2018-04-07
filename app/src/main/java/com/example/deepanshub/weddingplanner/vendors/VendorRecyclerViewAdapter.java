package com.example.deepanshub.weddingplanner.vendors;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.deepanshub.weddingplanner.R;
import com.example.deepanshub.weddingplanner.venueLocation.MapsActivity;

import java.util.List;

/**
 * Created by deepanshub on 23/3/18.
 */

public class VendorRecyclerViewAdapter extends RecyclerView.Adapter<VendorRecyclerViewAdapter.BindingHolder> {

    private List<Model> models;
    private Context context;

    public VendorRecyclerViewAdapter(List<Model> models, Context context) {
        this.models = models;
        this.context = context;
    }


    @Override
    public BindingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.vendor_recyclerview_cardview, parent, false);
        return new BindingHolder(view);
    }

    @Override
    public void onBindViewHolder(BindingHolder holder, final int position) {
        Model model = models.get(position);

        final int image = models.get(position).getmVendorImage();
        holder.vendortext.setText(model.getmVendorName());
        holder.vendorImage.setImageResource(image);


        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position == 0) {
                    Intent intent = new Intent(context, MapsActivity.class);
                    context.startActivity(intent);

                }
            }
        });


    }


    @Override
    public int getItemCount() {
        return models.size();
    }

    public class BindingHolder extends RecyclerView.ViewHolder {


        CardView cardView;
        TextView vendortext;
        ImageView vendorImage;

        public BindingHolder(View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardviewVendor);
            vendorImage = itemView.findViewById(R.id.vendorImage);
            vendortext = itemView.findViewById(R.id.vendorText);
        }
    }

}
