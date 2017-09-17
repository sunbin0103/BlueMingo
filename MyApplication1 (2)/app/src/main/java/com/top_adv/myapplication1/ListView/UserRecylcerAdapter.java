package com.top_adv.myapplication1.ListView;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.top_adv.myapplication1.FragmentView.FragmentHome;
import com.top_adv.myapplication1.FragmentView.FragmentUser;
import com.top_adv.myapplication1.MainActivity;
import com.top_adv.myapplication1.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by sst on 2016-10-20.
 */
public class UserRecylcerAdapter extends RecyclerView.Adapter<UserRecylcerAdapter.ViewHolder> {

    public static final String TAG = MainActivity.TAG;
    public ArrayList<UserListData> listData;
    public Context mContext;

    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public LinearLayout user_top_linear;
        public LinearLayout user_image_linear;
        public LinearLayout user_right_relative;
        public TextView user_item_title;
        public TextView user_purchase_date;
        public TextView user_detail;
        public ImageView user_detail_button;
        public LinearLayout user_status_title;
        public LinearLayout user_status_bar;

        public ViewHolder(View view) {
            super(view);

            user_top_linear = (LinearLayout) view.findViewById(R.id.user_top_linear);
            user_image_linear = (LinearLayout) view.findViewById(R.id.user_image_linear);
            user_right_relative = (LinearLayout) view.findViewById(R.id.user_right_relative);
            user_item_title = (TextView) view.findViewById(R.id.user_item_title);
            user_purchase_date = (TextView) view.findViewById(R.id.user_purchase_date);
            user_detail = (TextView) view.findViewById(R.id.user_detail);
            user_detail_button = (ImageView) view.findViewById(R.id.user_detail_button);
            user_status_title = (LinearLayout) view.findViewById(R.id.user_status_title);
            user_status_bar = (LinearLayout) view.findViewById(R.id.user_status_bar);

            user_detail.setText("주문 상세 보기");
            user_detail_button.setImageResource(R.drawable.user_detail_button);

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
    public UserRecylcerAdapter(ArrayList<UserListData> listData) {
        this.listData = listData;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public UserRecylcerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                             int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_list_item, parent, false);
        // set the view's size, margins, paddings and layout parameters

        mContext = parent.getContext();

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }


    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        UserListData lData = listData.get(position);
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        if (lData.adv_Image != 0) {
            //holder.backGround.setVisibility(View.VISIBLE);
            holder.user_image_linear.setBackgroundResource(lData.adv_Image);
        } else {
            holder.user_image_linear.setVisibility(View.INVISIBLE);
        }

        holder.user_top_linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                Intent intent = new Intent(view.getContext(), ItemDetailPage.class);
                intent.putExtra("PRODUCT_ID", lData.getProduct_id());
                intent.putExtra("PRODUCT_PERIOD", lData.getPeriod());
                intent.putExtra("PRODUCT_MAX_COUNT", lData.getProduct_max_count());
                intent.putExtra("PRODUCT_RES_COUNT", lData.getTrade_rescount());

                view.getContext().startActivity(intent);

                Log.d(TAG, lData.getProduct_id().toString());
                */

                // 제품 상세 페이지 출력
            }
        });

        holder.user_right_relative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onButtonClick_user_detail(view);
            }
        });

        holder.user_item_title.setText(lData.adv_title);
        holder.user_purchase_date.setText(lData.purchase_date);
        holder.user_detail_button.setId(lData.adv_key);

        createStatus(holder.user_status_title, holder.user_status_bar, 2, true);
    }

    public ArrayList<UserListData> getListData() {
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

    public void createStatus(LinearLayout title, LinearLayout bar, int state, boolean isSuccess) {

        String[] status;
        TextView[] txt_status;
        int index;

        if(isSuccess)
            status = new String[]{"공구 중", "공구 완료", "배송 중", "배송 완료"};
        else
            status = new String[]{"공구 중", "공구 실패", "환불 완료"};

        index = status.length;
        title.setWeightSum(index);
        bar.setWeightSum(index);
        txt_status = new TextView[index*2];

        for(int i=0; i<index; i++) {
            txt_status[i] = new TextView(mContext);
            txt_status[i].setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1f));
            txt_status[i].setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
            txt_status[i].setId(i);

            txt_status[i+index] = new TextView(mContext);
            txt_status[i+index].setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1f));
            txt_status[i+index].setId(i);

            txt_status[i].setText(status[i]);
            txt_status[i+index].setText("");

            if(i == state-1) {
                txt_status[i].setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
            } else {
                txt_status[i].setTextColor(mContext.getResources().getColor(R.color.text_card_list_light));
            }

            if(i < state)
                txt_status[i+index].setBackgroundResource(R.color.colorPrimary);
            else
                txt_status[i+index].setBackgroundResource(R.color.text_card_list_light);

            title.addView(txt_status[i]);
            bar.addView(txt_status[i+index]);
        }
    }

    public void onButtonClick_user_detail(View view) {
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