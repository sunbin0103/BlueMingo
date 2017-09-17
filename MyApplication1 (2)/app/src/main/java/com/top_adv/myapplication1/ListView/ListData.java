package com.top_adv.myapplication1.ListView;

import java.text.Collator;
import java.util.Comparator;
import java.util.List;

import android.widget.GridLayout;
import android.widget.RelativeLayout;

public class ListData {

	//data
	public int adv_Image;
	public String adv_title;
	public int item_price;
	public String item_trans;
	public int max_quantity;
	public int min_quantity;
	public long end_time;

	public ListData (int adv_Image, String adv_title, Integer item_price, String item_trans, int min_quantity, int max_quantity, long end_time) {
		this.adv_Image = adv_Image;
		this.adv_title = adv_title;
		this.max_quantity = max_quantity;
		this.min_quantity = min_quantity;
		this.item_price = item_price;
		this.item_trans = item_trans;
		this.end_time = end_time;
	}
	
	
	//data compare
	public static final Comparator<ListData> ALPHA_COMPARATOR = new Comparator<ListData>() {
		private final Collator sCollator = Collator.getInstance();
		
		@Override
		public int compare(ListData listData1, ListData listData2) {
			return sCollator.compare(listData1.adv_title, listData2.adv_title);
		};
	};
	
	
}
