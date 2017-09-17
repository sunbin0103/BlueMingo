package com.top_adv.myapplication1.ListView;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.top_adv.myapplication1.DataVO.OptionVO;
import com.top_adv.myapplication1.R;

import java.util.ArrayList;

/**
 * Created by EunBin Lee on 2017-03-13.
 */

public class OptionListRecyclerAdapter extends RecyclerView.Adapter<OptionListRecyclerAdapter.ViewHolder> {

    private ArrayList<OptionVO> optionList;

    public OptionListRecyclerAdapter(ArrayList<OptionVO> optionList) {
        this.optionList = optionList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView list_option_name;
        public TextView list_option_number;
        public TextView list_option_price;

        public ViewHolder(View view) {
            super(view);
            list_option_name = (TextView) view.findViewById(R.id.list_option_name);
            list_option_number = (TextView) view.findViewById(R.id.list_option_number);
            list_option_price = (TextView) view.findViewById(R.id.list_option_price);
        }
    }

    @Override
    public OptionListRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.listview_order, parent, false);

        OptionListRecyclerAdapter.ViewHolder vh = new OptionListRecyclerAdapter.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        OptionVO optionVO = optionList.get(position);

        holder.list_option_name.setText(optionVO.getOption_list_value());
        holder.list_option_number.setText(String.valueOf(optionVO.getOption_number())+" 개");
        holder.list_option_price.setText(String.valueOf(optionVO.getOption_rprice())+" 원");
    }

    @Override
    public int getItemCount() {
        return optionList.size();
    }
    
}
