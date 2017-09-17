package com.top_adv.myapplication1.DataVO;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by EunBin Lee on 2017-03-31.
 * Faq에서 사용할 질문/답변 리스트 VO
 * @title : 질문
 * @detail : 답변
 */

public class FaqVO{
    private String title;
    private String detail;

    public FaqVO(String title, String detail) {
        this.title = title;
        this.detail = detail;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
