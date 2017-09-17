package com.top_adv.myapplication1.ListView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.top_adv.myapplication1.DataVO.FaqVO;
import com.top_adv.myapplication1.R;

import java.util.ArrayList;

/**
 * Created by EunBin Lee on 2017-03-31.
 * FaQ 리스트를 위한 ExpandListAdapter
 */

public class FaqExpandListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private ArrayList<FaqVO> faqData;

    private LayoutInflater inflater;

    public FaqExpandListAdapter(Context context, ArrayList<FaqVO> faqData) {
        this.context = context;
        this.faqData = faqData;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getGroupCount() {
        if(faqData != null)
            return faqData.size();
        else
            return 0;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return faqData.get(groupPosition).getTitle();
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return faqData.get(groupPosition).getDetail();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return groupPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        View view = convertView;

        if(convertView == null)
            view = inflater.inflate(R.layout.faq_list_row, parent, false);

        TextView faq_list_title = (TextView) view.findViewById(R.id.faq_list_title);

        faq_list_title.setText(faqData.get(groupPosition).getTitle());

        return view;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        View view = convertView;

        if(convertView == null)
            view = inflater.inflate(R.layout.faq_list_detail_row, null);

        TextView faq_list_detail = (TextView) view.findViewById(R.id.faq_list_detail);

        faq_list_detail.setText(faqData.get(groupPosition).getDetail());

        return view;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
