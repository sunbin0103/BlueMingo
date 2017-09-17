package com.top_adv.myapplication1.ListView;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.top_adv.myapplication1.MainActivity;
import com.top_adv.myapplication1.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by sst on 2016-10-21.
 */
public class CartRecyclerAdapter extends RecyclerView.Adapter<CartRecyclerAdapter.ViewHolder> {

    public static final String TAG = MainActivity.TAG;
    public Context mContext = null;
    public ArrayList<CartListData> listData;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public LinearLayout item_image_linear;
        public TextView item_title;
        public TextView item_price;
        public TextView item_trans;
        public TextView res_time;
        public ProgressBar res_bar;
        public TextView res_status;
        public ImageView like_button;
        public ImageView direct_button;
        public ImageView share_button;
        public CheckBox check_box;
        //public TextView centerText;

        public ViewHolder(View view) {
            super(view);
            item_image_linear = (LinearLayout) view.findViewById(R.id.cart_image_linear);
            item_title = (TextView) view.findViewById(R.id.cart_item_title);
            item_price = (TextView) view.findViewById(R.id.cart_item_price);
            item_trans = (TextView) view.findViewById(R.id.cart_item_trans);
            res_time = (TextView) view.findViewById(R.id.cart_res_time);
            res_bar = (ProgressBar) view.findViewById(R.id.cart_res_bar);
            res_status = (TextView) view.findViewById(R.id.cart_res_status);
            like_button = (ImageView) view.findViewById(R.id.cart_like_button);
            direct_button = (ImageView) view.findViewById(R.id.cart_direct_button);
            share_button = (ImageView) view.findViewById(R.id.cart_share_button);
            check_box = (CheckBox) view.findViewById(R.id.cart_item_check);
            /*
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Snackbar.make(view, "Reservation Complete?", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            });*/
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public CartRecyclerAdapter(ArrayList<CartListData> listData) {
        this.listData = listData;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public CartRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                             int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cart_list_item, parent, false);
        // set the view's size, margins, paddings and layout parameters

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }


    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        CartListData lData = listData.get(position);
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        if (lData.adv_Image != 0) {
            //holder.backGround.setVisibility(View.VISIBLE);
            holder.item_image_linear.setBackgroundResource(lData.adv_Image);
        } else {
            holder.item_image_linear.setVisibility(View.INVISIBLE);
        }


        long  now_time = System.currentTimeMillis();
        Date date = new Date(now_time - lData.end_time);
        String str_resTime = (new SimpleDateFormat("HH:mm:ss").format(date));

        //int progress = (int) (now_time/lData.end_time*100);
        int progress = lData.min_quantity;

        holder.res_bar.setProgress(progress);
        holder.res_bar.setMax(100);

        holder.item_title.setText(lData.adv_title);
        holder.item_price.setText(lData.item_price);
        holder.item_trans.setText(lData.item_trans);
        holder.res_time.setText(str_resTime);
        holder.res_status.setText(lData.min_quantity + "/" + lData.max_quantity);
        holder.like_button.setImageResource(R.drawable.like_button);
        holder.direct_button.setImageResource(R.drawable.direct_button);
        holder.share_button.setImageResource(R.drawable.share_button);
        //holder.check_box.setChecked(lData.check_box);
    }

    public ArrayList<CartListData> getListData() {
        // TODO Auto-generated method stub
        return listData;
    }

    public int getItemCount() {
        // TODO Auto-generated method stub
        return listData.size();
    }

    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }


    /*
    public void remove(int position) {
        listData.remove(position);
        dataChange();
    }

    public void sort() {
        Collections.sort(listData, ListData.ALPHA_COMPARATOR);
        dataChange();
    }

    public void dataChange() {
        this.notifyDataSetChanged();
    }
    */
}