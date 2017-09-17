package com.top_adv.myapplication1.DataVO;

import android.os.Parcel;
import android.os.Parcelable;

import com.top_adv.myapplication1.ListView.UserListData;

import java.util.ArrayList;

/**
 * Created by sst on 2016-11-14.
 */
public class ParcelList implements Parcelable {

    private ArrayList<ListVO> list_lists;
    private ArrayList<DetailVO> detail_lists;
    //private ArrayList<OrderVO> order_lists;
    //private ArrayList<UserListVO> userlists;
    //private ArrayList<UserCartVO> cartlists;

    public ParcelList() {
    }
    public ParcelList(Parcel in) {
        readFromParcel(in);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    private void readFromParcel(Parcel in) {
        list_lists = new ArrayList<>();
        detail_lists = new ArrayList<>();
        //order_lists = new ArrayList<>();

        in.readTypedList(list_lists,ListVO.CREATOR);
        in.readTypedList(detail_lists,DetailVO.CREATOR);
       // in.readTypedList(order_lists,OrderVO.CREATOR);

    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        //dest.writeTypedList(order_lists);
        dest.writeTypedList(list_lists);
        dest.writeTypedList(detail_lists);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {

        @Override
        public ParcelList createFromParcel(Parcel in) {
            return new ParcelList(in);
        }

        @Override
        public ParcelList[] newArray(int size) {
            // TODO Auto-generated method stub
            return new ParcelList[size];
        }

    };

    public ArrayList<ListVO> getList_lists() {
        return list_lists;
    }

    public void setList_lists(ArrayList<ListVO> lists) {
        this.list_lists = lists;
    }

    public ArrayList<DetailVO> getDetail_lists() {
        return detail_lists;
    }

    public void setDetail_lists(ArrayList<DetailVO> detail_lists) {
        this.detail_lists = detail_lists;
    }

    /*public ArrayList<OrderVO> getOrder_lists() {
        return order_lists;
    }

    public void setOrder_lists(ArrayList<OrderVO> order_lists) {
        this.order_lists = order_lists;
    }*/
}
