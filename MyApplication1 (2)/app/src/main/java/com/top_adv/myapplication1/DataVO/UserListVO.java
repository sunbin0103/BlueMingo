package com.top_adv.myapplication1.DataVO;

/**
 * Created by sst on 2016-11-11.
 */
public class UserListVO {

    String item_image;
    String adv_title;
    Integer price;
    String deliver;
    Long period;
    Integer rescount;
    Integer max;
    Integer product_key;
    Integer trade_key;

    public String getItem_image() {
        return item_image;
    }

    public void setItem_image(String item_image) {
        this.item_image = item_image;
    }

    public String getAdv_title() {
        System.out.println("adv_title : "+adv_title);
        return adv_title;
    }

    public void setAdv_title(String adv_title) {
        System.out.println("adv_title : "+adv_title);
        this.adv_title = adv_title;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getDeliver() {
        return deliver;
    }

    public void setDeliver(String deliver) {
        this.deliver = deliver;
    }

    public Long getPeriod() {
        return period;
    }

    public void setPeriod(Long period) {
        this.period = period;
    }

    public Integer getRescount() {
        return rescount;
    }

    public void setRescount(Integer rescount) {
        this.rescount = rescount;
    }

    public Integer getMax() {
        return max;
    }

    public void setMax(Integer max) {
        this.max = max;
    }

    public Integer getProduct_key() {
        return product_key;
    }

    public void setProduct_key(Integer product_key) {
        this.product_key = product_key;
    }

    public Integer getTrade_key() {
        return trade_key;
    }

    public void setTrade_key(Integer trade_key) {
        this.trade_key = trade_key;
    }
}
