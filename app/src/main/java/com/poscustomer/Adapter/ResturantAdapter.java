package com.poscustomer.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.poscustomer.Model.ResturantData;
import com.poscustomer.R;

import java.util.List;

/**
 * Created by Abhishek on 24-04-2017.
 */

public class ResturantAdapter extends RecyclerView.Adapter<ResturantAdapter.DataHolder> {
    private List<ResturantData.DataArray> listdata;
    private LayoutInflater inflater;






    public ResturantAdapter(List<ResturantData.DataArray> listdata, Context c) {
        this.inflater = LayoutInflater.from(c);
        this.listdata = listdata;
    }

    class DataHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView name,address, distance;

        //ImageView secondaryIcon;

        public DataHolder(View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.resturantname);
            address = (TextView) itemView.findViewById(R.id.address_resturant);
            distance=(TextView) itemView.findViewById(R.id.distance);

           ;
            //secondaryIcon = (ImageView) itemView.findViewById(R.id.im_item_icon_secondary);
            //secondaryIcon.setOnClickListener(this);


        }

        @Override
        public void onClick(View v) {
            //itemclickcallback.onSecondaryIconClick(getAdapterPosition());

        }
    }

    @Override
    public DataHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.resturantlist, parent, false);
        return new DataHolder(view);

    }

    @Override
    public void onBindViewHolder(DataHolder holder, int position) {
        ResturantData.DataArray item = listdata.get(position);
        holder.name.setText(item.getName());
//        holder.icon.setImageResource(item.getImageResId());
//        holder.address.setText("Capacity " +item.getAddress());
//        holder.distance.setText("Base Fare " +item.getRadius());


       /* if (item.isFavorite()) {
            holder.secondaryIcon.setImageResource(R.drawable.ic_favorite_black_24dp);
        } else {
            holder.secondaryIcon.setImageResource(R.drawable.ic_favorite_border_black_24dp);
        } */

    }


    @Override
    public int getItemCount() {
        return listdata.size();
    }




}
