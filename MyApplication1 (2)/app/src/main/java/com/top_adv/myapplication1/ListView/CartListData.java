package com.top_adv.myapplication1.ListView;

import android.text.BoringLayout;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Comparator;

/**
 * Created by sst on 2016-10-21.
 */
public class CartListData {

    //data
    public int adv_Image;
    public String adv_title;
    public String item_price;
    public String item_trans;
    public int max_quantity;
    public int min_quantity;
    public long end_time;
    //check된 물품,광고 리스트 좋아요를 누른 리스트가 장바구니가 된다고 가정하면 카트리스트는 무조건 로그인기준으로 해야한다.
    public boolean check_box;

    public CartListData(int adv_Image, String adv_title, String item_price, String item_trans, int min_quantity, int max_quantity, long end_time, Boolean check_box) {
        this.adv_Image = adv_Image;
        this.adv_title = adv_title;
        this.max_quantity = max_quantity;
        this.min_quantity = min_quantity;
        this.item_price = item_price;
        this.item_trans = item_trans;
        this.end_time = end_time;
        this.check_box = check_box;
    }


    //data compare
    public static final Comparator<ListData> ALPHA_COMPARATOR = new Comparator<ListData>() {
        private final Collator sCollator = Collator.getInstance();

        @Override
        public int compare(ListData listData1, ListData listData2) {
            return sCollator.compare(listData1.adv_title, listData2.adv_title);
        }

        ;
    };


}
