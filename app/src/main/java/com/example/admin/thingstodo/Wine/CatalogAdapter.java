package com.example.admin.thingstodo.Wine;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.resource.bitmap.StreamBitmapDecoder;
import com.example.admin.thingstodo.Classes.CatalogClass;
import com.example.admin.thingstodo.R;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * MAIN ADAPTER
 */

public class CatalogAdapter extends RecyclerView.Adapter<CatalogAdapter.MyViewHolder> implements Filterable {

    private Activity context;
    List<CatalogClass> catalogList, catalogss;
    private Activity applicationContext;
    private CatalogClass catalog;

    public CatalogAdapter(Activity context, List<CatalogClass> catalogList) {
        this.context = context;
        this.catalogList = catalogList;
        this.catalogss=catalogList;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_model,null);

        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final CatalogClass catalogClass = catalogList.get(position);

        holder.tvDate.setText(catalogClass.getDate());
        holder.tvArea.setText(catalogClass.getLocation());
        holder.tvEventTitle.setText(catalogClass.getEventTitle());
        holder.tvDescription.setText(catalogClass.getDescription());
        holder.tvPrice.setText(catalogClass.getPrice());
        holder.tvDiscount.setText(catalogClass.getDiscount());

        int radius = 30; // corner radius, higher value = more rounded
        int margin = 10;
        Glide.with(context)
                .load(catalogClass.getImageurl())
                .bitmapTransform(new RoundedCornersTransformation(context, radius, margin))
                .placeholder(android.R.drawable.ic_menu_revert)
                .into(holder.ibImage);




        holder.ibImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CatalogClass c = catalogClass;
                Intent intent = new Intent(context,WineDescription
                        .class);
                intent.putExtra("select", c);
                context.startActivity(intent);




            }
        });

    }

    @Override
    public int getItemCount() {
        return (null != catalogList ? catalogList.size() : 0);
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvDate, tvArea, tvEventTitle, tvDescription, tvRating, tvPrice, tvDiscount;
        ImageButton ibImage, ibSave;
        Button btn_Add;
        ImageButton image_View;


        public MyViewHolder(View itemView) {
            super(itemView);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvArea = itemView.findViewById(R.id.tvArea);
            tvEventTitle = itemView.findViewById(R.id.tvEventTitle);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            tvRating = itemView.findViewById(R.id.tvRating);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvDiscount = itemView.findViewById(R.id.tvDiscount);
            btn_Add = itemView.findViewById(R.id.btn_Add);
            ibImage = itemView.findViewById(R.id.ibImage);
            ibSave = itemView.findViewById(R.id.ibSave);
            image_View = itemView.findViewById(R.id.image_View);


            /**
             * FUEL ICON COLOR CHANGE
             */


        }
    }



    @Override
    public Filter getFilter() {


        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                String charString = charSequence.toString();

                if (charString.isEmpty()) {

                    catalogList = catalogss;
                } else {

                    ArrayList<CatalogClass> filteredList = new ArrayList<>();

                    for (CatalogClass androidVersion : catalogss) {

                        /**
                         * SEARCHES USING LOCATION/
                         */
                        if (androidVersion.getLocation().toLowerCase().contains(charString) ) {

                            filteredList.add(androidVersion);
                        }
                    }

                    catalogList = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = catalogList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                catalogList = (ArrayList<CatalogClass>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
}