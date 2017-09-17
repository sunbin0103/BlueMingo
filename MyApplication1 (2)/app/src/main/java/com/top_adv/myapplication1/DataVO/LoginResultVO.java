package com.top_adv.myapplication1.DataVO;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by EunBin Lee on 2017-05-08.
 */

public class LoginResultVO implements Parcelable {

    private String messege;

    public LoginResultVO() {}
    protected LoginResultVO(Parcel in) {
        messege = in.readString();
    }

    public static final Creator<LoginResultVO> CREATOR = new Creator<LoginResultVO>() {
        @Override
        public LoginResultVO createFromParcel(Parcel in) {
            return new LoginResultVO(in);
        }

        @Override
        public LoginResultVO[] newArray(int size) {
            return new LoginResultVO[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(messege);
    }

    public String getMessege() {
        return messege;
    }

    public void setMessege(String messege) {
        this.messege = messege;
    }

}
