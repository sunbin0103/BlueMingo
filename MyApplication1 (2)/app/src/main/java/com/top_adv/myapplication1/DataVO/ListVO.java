package com.top_adv.myapplication1.DataVO;

import android.app.ProgressDialog;
import android.os.Parcel;
import android.os.Parcelable;

import java.sql.Date;
import java.sql.Time;

/**
 * Created by sst on 2016-11-11.
 */
public class ListVO implements Parcelable {

    Integer adv_key;
    String adv_image;
    String adv_title;
    String product_id;
    Integer product_direct_price;
    Integer product_sale_price;
    Integer product_naver_price;
    Long period;
    Integer product_max_count;
    Integer trade_key;
    String trade_status;
    Integer trade_rescount;
    Integer deliver_company;
    Integer deliver_price;
    Integer product_min;

    ProgressDialog progressDialog;

    public ListVO() {
    }
    public ListVO(Parcel in) {
        readFromParcel(in);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    private void readFromParcel(Parcel in) {
        adv_key = in.readInt();
        adv_image = in.readString();
        adv_title = in.readString();
        product_id = in.readString();
        product_direct_price = in.readInt();
        product_sale_price = in.readInt();
        product_naver_price = in.readInt();
        period = in.readLong();
        product_max_count = in.readInt();
        trade_key = in.readInt();
        trade_status = in.readString();
        trade_rescount = in.readInt();
        deliver_company = in.readInt();
        deliver_price = in.readInt();
        product_min = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(adv_key);
        dest.writeString(adv_image);
        dest.writeString(adv_title);
        dest.writeString(product_id);
        dest.writeInt(product_direct_price);
        dest.writeInt(product_sale_price);
        dest.writeInt(product_naver_price);
        dest.writeLong(period);
        dest.writeInt(product_max_count);
        dest.writeInt(trade_key);
        dest.writeString(trade_status);
        dest.writeInt(trade_rescount);
        dest.writeInt(deliver_company);
        dest.writeInt(deliver_price);
        dest.writeInt(product_min);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {

        @Override
        public ListVO createFromParcel(Parcel in) {
            return new ListVO(in);
        }

        @Override
        public ListVO[] newArray(int size) {
            // TODO Auto-generated method stub
            return new ListVO[size];
        }

    };

    public Integer getDeliver_company() {
        return deliver_company;
    }

    public void setDeliver_company(Integer deliver_company) {
        this.deliver_company = deliver_company;
    }

    public Integer getDeliver_price() {
        return deliver_price;
    }

    public void setDeliver_price(Integer deliver_price) {
        this.deliver_price = deliver_price;
    }

    public Integer getProduct_min() {
        return product_min;
    }

    public void setProduct_min(Integer product_min) {
        this.product_min = product_min;
    }

    public Integer getAdv_key() {
        return adv_key;
    }

    public void setAdv_key(Integer adv_key) {
        this.adv_key = adv_key;
    }

    public String getAdv_image() {
        return adv_image;
    }

    public void setAdv_image(String adv_image) {
        this.adv_image = adv_image;
    }

    public String getAdv_title() {
        return adv_title;
    }

    public void setAdv_title(String adv_title) {
        this.adv_title = adv_title;
    }

    public String getProduct_id() { return product_id; }

    public void setProduct_id(String product_id) { this.product_id = product_id; }

    public Integer getProduct_direct_price() {
        return product_direct_price;
    }

    public void setProduct_direct_price(Integer product_direct_price) {
        this.product_direct_price = product_direct_price;
    }

    public Integer getProduct_sale_price() {
        return product_sale_price;
    }

    public void setProduct_sale_price(Integer product_sale_price) {
        this.product_sale_price = product_sale_price;
    }

    public Integer getProduct_naver_price() {
        return product_naver_price;
    }

    public void setProduct_naver_price(Integer product_naver_price) {
        this.product_naver_price = product_naver_price;
    }

    public Long getPeriod() {
        return period;
    }

    public void setPeriod(Long period) {
        this.period = period;
    }

    public Integer getProduct_max_count() {
        return product_max_count;
    }

    public void setProduct_max_count(Integer product_max_count) {
        this.product_max_count = product_max_count;
    }

    public Integer getTrade_key() {
        return trade_key;
    }

    public void setTrade_key(Integer trade_key) {
        this.trade_key = trade_key;
    }

    public String getTrade_status() {
        return trade_status;
    }

    public void setTrade_status(String trade_status) {
        this.trade_status = trade_status;
    }

    public Integer getTrade_rescount() {
        return trade_rescount;
    }

    public void setTrade_rescount(Integer trade_rescount) {
        this.trade_rescount = trade_rescount;
    }

    public ProgressDialog getProgressDialog() {
        return progressDialog;
    }
    public void setProgressDialog(ProgressDialog progressDialog) {
        this.progressDialog = progressDialog;
    }
}
