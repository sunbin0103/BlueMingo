package com.top_adv.myapplication1.DataVO;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Time;

/**
 * Created by sst on 2016-10-19.
 */
public class UserVO implements Parcelable {

    Integer user_key;
    String user_id;
    String user_name;
    Integer user_phone;
    String user_password;
    Integer login_account =0;

    private JSONObject jsonObject;

    public JSONObject getJsonObject() {
        jsonObject = new JSONObject();
        try {
            jsonObject.put("user_id", user_id);
            jsonObject.put("user_password", user_password);
            jsonObject.put("user_name", user_name);
            jsonObject.put("user_phone", user_phone);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    public UserVO() {
    }
    public UserVO(Parcel in) {
        readFromParcel(in);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    private void readFromParcel(Parcel in) {
        user_key = in.readInt();
        user_id = in.readString();
        user_name = in.readString();
        user_phone = in.readInt();
        user_password = in.readString();
        login_account = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(user_key);
        dest.writeString(user_id);
        dest.writeString(user_name);
        dest.writeInt(user_phone);
        dest.writeString(user_password);
        dest.writeInt(login_account);
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

    public Integer getUser_key() {
        return user_key;
    }
    public void setUser_key(Integer user_key) {
        this.user_key = user_key;
    }
    public String getUser_id() {
        return user_id;
    }
    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
    public String getUser_name() {
        return user_name;
    }
    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }
    public Integer getUser_phone() {
        return user_phone;
    }
    public void setUser_phone(Integer user_phone) {
        this.user_phone = user_phone;
    }
    public String getUser_password() {
        return user_password;
    }
    public void setUser_password(String user_password) {
        this.user_password = user_password;
    }
    public Integer getLogin_account() {
        return login_account;
    }
    public void setLogin_account(Integer login_account) {
        this.login_account = login_account;
    }
}
