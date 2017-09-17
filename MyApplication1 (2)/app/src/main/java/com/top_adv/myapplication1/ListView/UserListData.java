package com.top_adv.myapplication1.ListView;

import java.text.Collator;
import java.util.Comparator;

/**
 * Created by sst on 2016-10-20.
 */
public class UserListData {

    public int adv_key;
    public int adv_Image;
    public String adv_title;
    public int purchase_type;
    public String purchase_date;

    public UserListData (int adv_key, int adv_Image, String adv_title, int purchase_type, String purchase_date) {
        this.adv_key = adv_key;
        this.adv_Image = adv_Image;
        this.adv_title = adv_title;
        this.purchase_type = purchase_type;
        this.purchase_date = purchase_date;
    }



    //data compare
    public static final Comparator<ListData> ALPHA_COMPARATOR = new Comparator<ListData>() {
        private final Collator sCollator = Collator.getInstance();

        @Override
        public int compare(ListData listData1, ListData listData2) {
            return sCollator.compare(listData1.adv_title, listData2.adv_title);
        }
    };
}
