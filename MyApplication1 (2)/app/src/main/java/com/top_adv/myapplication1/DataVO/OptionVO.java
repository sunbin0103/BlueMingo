package com.top_adv.myapplication1.DataVO;

/**
 * Created by EunBin Lee on 2017-03-12.
 */

public class OptionVO {

    private Ref_listVO option_list; // 옵션 정보
    private Integer option_value; // 최대 갯수
    private Integer option_number; // 갯수

    public OptionVO() {}
    public OptionVO(Ref_listVO option_list, Integer option_value) {
        this.option_list = option_list;
        this.option_value = option_value;
        option_number = 1;
    }

    public Ref_listVO getOption_list() {
        return option_list;
    }

    public void setOption_list(Ref_listVO option_list) {
        this.option_list = option_list;
    }

    public Integer getOption_number() {
        return option_number;
    }

    public Integer getOption_value() {
        return option_value;
    }

    public String getOption_name() {
        return option_list.getOption_name();
    }

    public String getOption_list_value() { return option_list.getOption_value(); } // 옵션 내용

    public Integer getOption_price() {
        return option_list.getOption_price();
    }

    public Integer getOption_rprice() {
        int price = getOption_price();
        int num = getOption_number();

        return (price*num); // 개당 가격*개수
    }

    public void addOption_number() {
        this.option_number++;
    }

    public void subOption_number() {
        this.option_number--;
    }
}
