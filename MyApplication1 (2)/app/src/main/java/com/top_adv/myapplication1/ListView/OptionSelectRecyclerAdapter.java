package com.top_adv.myapplication1.ListView;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.top_adv.myapplication1.DataVO.OptionVO;
import com.top_adv.myapplication1.MainActivity;
import com.top_adv.myapplication1.R;

import java.util.ArrayList;

/**
 * Created by EunBin Lee on 2017-03-12.
 */

public class OptionSelectRecyclerAdapter extends RecyclerView.Adapter<OptionSelectRecyclerAdapter.ViewHolder> {

    public static final String TAG = MainActivity.TAG;

    private ArrayList<OptionVO> optionVO;
    private boolean existOption;

    public OptionSelectRecyclerAdapter(ArrayList<OptionVO> optionVO) { this.optionVO = optionVO; this.existOption = false; }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView option_select_name;
        public TextView option_number;
        public TextView option_select_price;
        public Button btn_option_number_minus;
        public Button btn_option_number_plus;
        public Button btn_option_del;

        public ViewHolder(View view) {
            super(view);
            option_select_name = (TextView) view.findViewById(R.id.option_select_name);
            option_number = (TextView) view.findViewById(R.id.option_number);
            option_select_price = (TextView) view.findViewById(R.id.option_select_price);
            btn_option_number_minus = (Button) view.findViewById(R.id.btn_option_number_minus);
            btn_option_number_plus = (Button) view.findViewById(R.id.btn_option_number_plus);
            btn_option_del = (Button) view.findViewById(R.id.btn_option_del);
        }
    }

    @Override
    public OptionSelectRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.option_select, parent, false);
        // set the view's size, margins, paddings and layout parameters

        OptionSelectRecyclerAdapter.ViewHolder vh = new OptionSelectRecyclerAdapter.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final OptionSelectRecyclerAdapter.ViewHolder holder, int position) {
        final OptionVO IData = optionVO.get(position);

        if(IData != null) {
            holder.option_select_name.setText(IData.getOption_list_value());
            holder.option_number.setText("1");
            holder.option_select_price.setText(String.valueOf(IData.getOption_price())+" 원");

            holder.btn_option_number_minus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(IData.getOption_number() > 1) {
                        IData.subOption_number();
                        holder.option_number.setText(String.valueOf(IData.getOption_number()));
                        holder.option_select_price.setText(String.valueOf(IData.getOption_rprice())+" 원");
                    }
                }
            });

            holder.btn_option_number_plus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(IData.getOption_number() < IData.getOption_value()) {
                        IData.addOption_number();
                        holder.option_number.setText(String.valueOf(IData.getOption_number()));
                        holder.option_select_price.setText(String.valueOf(IData.getOption_rprice())+" 원");
                    }
                }
            });

            holder.btn_option_del.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int p = holder.getAdapterPosition();

                    if(p != RecyclerView.NO_POSITION) {
                        optionVO.remove(p);
                        notifyItemRemoved(p);
                    }
                }
            });

            if(existOption)
                holder.btn_option_del.setVisibility(View.VISIBLE);
            else
                holder.btn_option_del.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return optionVO.size();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    public void setDelButtonEnabled() { existOption = true; }
    public void setDelButtonDisabled() { existOption = false; }
}
