package com.top_adv.myapplication1.DataVO;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by sst on 2016-11-16.
 */
public class OrderVO implements Parcelable {

    Integer user_key;
    Integer trade_key;
    Integer product_key;
    Integer res_type;
    Integer res_quantity;
    Long period;

    JSONObject jsonObject;

    public OrderVO() {
    }

    public void setPurchase(Integer user_key, Integer trade_key, Integer product_key, Integer res_quantity, Long period) {
        this.user_key = user_key;
        this.trade_key = trade_key;
        this.product_key = product_key;
        this.res_quantity = res_quantity;
        this.period = period;

        jsonObject = new JSONObject();
        try {
            jsonObject.put("user_key", user_key);
            jsonObject.put("trade_key", trade_key);
            jsonObject.put("product_key", product_key);
            jsonObject.put("res_quantity", res_quantity);
            jsonObject.put("res_type", res_type);
            jsonObject.put("period", period);
        }catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public OrderVO(Parcel in) {
        readFromParcel(in);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    private void readFromParcel(Parcel in) {
        user_key = in.readInt();
        trade_key = in.readInt();
        product_key = in.readInt();
        res_type = in.readInt();
        res_quantity = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(user_key);
        dest.writeInt(trade_key);
        dest.writeInt(product_key);
        dest.writeInt(res_type);
        dest.writeInt(res_quantity);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {

        @Override
        public OrderVO createFromParcel(Parcel in) {
            return new OrderVO(in);
        }

        @Override
        public OrderVO[] newArray(int size) {
            // TODO Auto-generated method stub
            return new OrderVO[size];
        }

    };

    public Integer getUser_key() {
        return user_key;
    }
    public void setUser_key(Integer user_key) {
        this.user_key = user_key;
    }
    public Integer getTrade_key() {
        return trade_key;
    }
    public void setTrade_key(Integer trade_key) {
        this.trade_key = trade_key;
    }
    public Integer getProduct_key() {
        return product_key;
    }
    public void setProduct_key(Integer product_key) {
        this.product_key = product_key;
    }
    public Integer getRes_type() {
        return res_type;
    }
    public void setRes_type(Integer res_type) {
        this.res_type = res_type;
    }
    public Integer getRes_quantity() {
        return res_quantity;
    }
    public void setRes_quantity(Integer res_quantity) {
        this.res_quantity = res_quantity;
    }
    public Long getPeriod() {
        return period;
    }
    public void setPeriod(Long period) {
        this.period = period;
    }

    public JSONObject getJsonObject() {
        return this.jsonObject;
    }
}
