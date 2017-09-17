package com.top_adv.myapplication1.DataVO;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;

public class AdvVO implements Parcelable{

	Integer adv_key;
	String adv_title;
	String adv_ticker;
	String adv_message;
	String adv_resource;
	Integer product_product_key;
	Long adv_time;
	//광고 알람할 시간값

	public AdvVO() {
	}
	public AdvVO(Parcel in) {
		readFromParcel(in);
	}

	@Override
	public int describeContents() {
		return 0;
	}

	private void readFromParcel(Parcel in) {
		adv_key = in.readInt();
		adv_title = in.readString();
		adv_ticker = in.readString();
		adv_message = in.readString();
		adv_resource = in.readString();
		product_product_key = in.readInt();
		adv_time = in.readLong();
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(adv_key);
		dest.writeString(adv_title);
		dest.writeString(adv_ticker);
		dest.writeString(adv_message);
		dest.writeString(adv_resource);
		dest.writeInt(product_product_key);
		dest.writeLong(adv_time);
	}

	public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {

		@Override
		public AdvVO createFromParcel(Parcel in) {
			return new AdvVO(in);
		}

		@Override
		public AdvVO[] newArray(int size) {
			// TODO Auto-generated method stub
			return new AdvVO[size];
		}

	};

	public String getAdv_title() {
		return adv_title;
	}
	public void setAdv_title(String adv_title) {
		this.adv_title = adv_title;
	}
	public Integer getProduct_product_key() {
		return product_product_key;
	}
	public void setProduct_product_key(Integer product_product_key) {
		this.product_product_key = product_product_key;
	}
	public Integer getAdv_key() {
		return adv_key;
	}
	public void setAdv_key(Integer adv_key) {
		this.adv_key = adv_key;
	}
	public String getAdv_ticker() {
		return adv_ticker;
	}
	public void setAdv_ticker(String adv_ticker) {
		this.adv_ticker = adv_ticker;
	}
	public String getAdv_message() {
		return adv_message;
	}
	public void setAdv_message(String adv_message) {
		this.adv_message = adv_message;
	}
	public String getAdv_resource() {
		return adv_resource;
	}
	public void setAdv_resource(String adv_resource) {
		this.adv_resource = adv_resource;
	}
	public Long getAdv_time() {
		return adv_time;
	}
	public void setAdv_time(Long adv_time) {
		this.adv_time = adv_time;
	}
	
}
