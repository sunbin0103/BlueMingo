package com.top_adv.myapplication1.DataVO;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by sst on 2016-12-01.
 */
public class LoginVO {

    private String user_id;
    private Integer user_password;
    private String user_key;
    private String author;
    private Integer account;
    private Integer user_phone;
    private String user_name;

    private JSONObject jsonObject;

    public JSONObject getJsonObject() {
        jsonObject = new JSONObject();
        try {
            jsonObject.put("loginId", user_id);
            jsonObject.put("loginPassword", user_password);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public Integer getUser_password() {
        return user_password;
    }

    public void setUser_password(Integer user_password) {
        this.user_password = user_password;
    }

    public String getUser_key() {
        return user_key;
    }

    public void setUser_key(String user_key) {
        this.user_key = user_key;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Integer getUser_phone() {
        return user_phone;
    }

    public void setUser_phone(Integer user_phone) {
        this.user_phone = user_phone;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public Integer getAccount() {
        return account;
    }

    public void setAccount(Integer account) {
        this.account = account;
    }
}
