package com.poscustomer.Adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.poscustomer.Model.ListItem;
import com.poscustomer.R;

import java.util.ArrayList;
import java.util.List;

public class DAdapter extends RecyclerView.Adapter<DAdapter.DataHolder> {


    private List<ListItem> listdata;
    private LayoutInflater inflater;
    private ItemClickCallback itemclickcallback;

    public interface ItemClickCallback {
        void onItemClick(int p);

        void onSecondaryIconClick(int p);

    }

    public void SetItemClickCallback(final ItemClickCallback itemClickCallback) {
        this.itemclickcallback = itemClickCallback;
    }


    public DAdapter(List<ListItem> listdata, Context c) {
        this.inflater = LayoutInflater.from(c);
        this.listdata = listdata;
    }

    @Override
    public DataHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.card_view, parent, false);
        return new DataHolder(view);

    }

    @Override
    public void onBindViewHolder(DataHolder holder, int position) {
        ListItem item = listdata.get(position);
        holder.title.setText(item.getTitle());
//        holder.icon.setImageResource(item.getImageResId());
        holder.subTitle.setText(item.getSubTitle());
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


    class DataHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView title;
        ImageView icon;
        View container;
        TextView subTitle;
        TextView load;
        //ImageView secondaryIcon;

        public DataHolder(View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.merchant);
            container = itemView.findViewById(R.id.cont_item_root);
            subTitle = (TextView) itemView.findViewById(R.id.address_merchant);
            container.setOnClickListener(this);
            load = (TextView) itemView.findViewById(R.id.timestamp);
            load.setOnClickListener(this);
            //secondaryIcon = (ImageView) itemView.findViewById(R.id.im_item_icon_secondary);
            //secondaryIcon.setOnClickListener(this);


        }

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.timestamp) {
                itemclickcallback.onItemClick(getAdapterPosition());
            } else {
                //itemclickcallback.onSecondaryIconClick(getAdapterPosition());
            }
        }
    }

    public void setListData(ArrayList<ListItem> exerciseList) {
        this.listdata.clear();
        this.listdata.addAll(exerciseList);
    }
}
