package com.top_adv.myapplication1.DataVO;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.top_adv.myapplication1.NetWork.JsonParsing;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by sst on 2016-11-11.
 */
public class DetailVO implements Parcelable {

    // company table
    private Integer company_key;
    private String company_name;
    private String company_president;
    private String company_address;
    private String company_phone;;
    private String company_prephone;
    private String company_serial;
    private String company_homepage;
    private Integer company_type;
    private String company_detail;

    // item table
    private Integer item_key;
    private String item_id;
    private String item_category_top;
    private String item_category_mid;
    private String item_category_bot;
    private String item_name;
    private Integer item_direct_price;
    private Integer item_sale_price;
    private Integer item_naver_price;
    private String item_inform;
    private String item_detail_inform;
    private String item_image;
    private String item_detail_image;

    // item-option
    //private String option_list; // 옵션 관련한 내용 다 들어있음
    private ArrayList<Ref_listVO> option_list;

    // product-item
    private Integer option_value; // 묶음-ref_list-제품 설정된 각 제품별 최대 수량

    public DetailVO() {}
    protected DetailVO(Parcel in) {
        company_name = in.readString();
        company_president = in.readString();
        company_address = in.readString();
        company_phone = in.readString();
        company_prephone = in.readString();
        company_serial = in.readString();
        company_homepage = in.readString();
        company_detail = in.readString();
        item_id = in.readString();
        item_category_top = in.readString();
        item_category_mid = in.readString();
        item_category_bot = in.readString();
        item_name = in.readString();
        item_inform = in.readString();
        item_detail_inform = in.readString();
        item_image = in.readString();
        item_detail_image = in.readString();
        in.readTypedList(option_list, Ref_listVO.CREATOR);
        option_value = in.readInt();
    }

    public static final Creator<DetailVO> CREATOR = new Creator<DetailVO>() {
        @Override
        public DetailVO createFromParcel(Parcel in) {
            return new DetailVO(in);
        }

        @Override
        public DetailVO[] newArray(int size) {
            return new DetailVO[size];
        }
    };

    public Integer getCompany_key() {
        return company_key;
    }
    public void setCompany_key(Integer company_key) {
        this.company_key = company_key;
    }
    public String getCompany_name() {
        return company_name;
    }
    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }
    public String getCompany_president() {
        return company_president;
    }
    public void setCompany_president(String company_president) {
        this.company_president = company_president;
    }
    public String getCompany_address() {
        return company_address;
    }
    public void setCompany_address(String company_address) {
        this.company_address = company_address;
    }
    public String getCompany_phone() {
        return company_phone;
    }
    public void setCompany_phone(String company_phone) {
        this.company_phone = company_phone;
    }
    public String getCompany_prephone() {
        return company_prephone;
    }
    public void setCompany_prephone(String company_prephone) {
        this.company_prephone = company_prephone;
    }
    public String getCompany_serial() {
        return company_serial;
    }
    public void setCompany_serial(String company_serial) {
        this.company_serial = company_serial;
    }
    public String getCompany_homepage() {
        return company_homepage;
    }
    public void setCompany_homepage(String company_homepage) {
        this.company_homepage = company_homepage;
    }
    public Integer getCompany_type() {
        return company_type;
    }
    public void setCompany_type(Integer company_type) {
        this.company_type = company_type;
    }
    public String getCompany_detail() {
        return company_detail;
    }
    public void setCompany_detail(String company_detail) {
        this.company_detail = company_detail;
    }

    public Integer getItem_key() {
        return item_key;
    }
    public void setItem_key(Integer item_key) {
        this.item_key = item_key;
    }
    public String getItem_id() {
        return item_id;
    }
    public void setItem_id(String item_id) {
        this.item_id = item_id;
    }
    public String getItem_category_top() {
        return item_category_top;
    }
    public void setItem_category_top(String item_category_top) {
        this.item_category_top = item_category_top;
    }
    public String getItem_category_mid() {
        return item_category_mid;
    }
    public void setItem_category_mid(String item_category_mid) {
        this.item_category_mid = item_category_mid;
    }
    public String getItem_category_bot() {
        return item_category_bot;
    }
    public void setItem_category_bot(String item_category_bot) {
        this.item_category_bot = item_category_bot;
    }
    public String getItem_name() {
        return item_name;
    }
    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }
    public Integer getItem_direct_price() {
        return item_direct_price;
    }
    public void setItem_direct_price(Integer item_direct_price) {
        this.item_direct_price = item_direct_price;
    }
    public Integer getItem_sale_price() {
        return item_sale_price;
    }
    public void setItem_sale_price(Integer item_sale_price) {
        this.item_sale_price = item_sale_price;
    }
    public Integer getItem_naver_price() {
        return item_naver_price;
    }
    public void setItem_naver_price(Integer item_naver_price) {
        this.item_naver_price = item_naver_price;
    }
    public String getItem_inform() {
        return item_inform;
    }
    public void setItem_inform(String item_inform) {
        this.item_inform = item_inform;
    }
    public String getItem_detail_inform() {
        return item_detail_inform;
    }
    public void setItem_detail_inform(String item_detail_inform) {
        this.item_detail_inform = item_detail_inform;
    }
    public String getItem_image() {
        return item_image;
    }
    public void setItem_image(String item_image) {
        this.item_image = item_image;
    }
    public String getItem_detail_image() {
        return item_detail_image;
    }
    public void setItem_detail_image(String item_detail_image) {
        this.item_detail_image = item_detail_image;
    }

    public ArrayList<Ref_listVO> getOption_list() {
        return option_list;
    }
    public void setOption_list(String param) {
        System.out.println(param);
        JsonParsing jsonParsing = new JsonParsing(Ref_listVO.class);
        try {
            this.option_list = jsonParsing.getResult(param);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException ee) {
            ee.printStackTrace();
        }
    }
    public Integer getOption_value() {
        return option_value;
    }
    public void setOption_value(Integer option_value) {
        this.option_value = option_value;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(company_name);
        parcel.writeString(company_president);
        parcel.writeString(company_address);
        parcel.writeString(company_phone);
        parcel.writeString(company_prephone);
        parcel.writeString(company_serial);
        parcel.writeString(company_homepage);
        parcel.writeString(company_detail);
        parcel.writeString(item_id);
        parcel.writeString(item_category_top);
        parcel.writeString(item_category_mid);
        parcel.writeString(item_category_bot);
        parcel.writeString(item_name);
        parcel.writeString(item_inform);
        parcel.writeString(item_detail_inform);
        parcel.writeString(item_image);
        parcel.writeString(item_detail_image);
        parcel.writeTypedList(option_list);
        parcel.writeInt(option_value);
    }
}
