package com.top_adv.myapplication1.ListView;

import com.top_adv.myapplication1.DataVO.ListVO;
import com.top_adv.myapplication1.DataVO.UserListVO;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by sst on 2016-11-18.
 */
public interface retroTest {

    @GET("android/orderPurchase")
    Call<UserListVO> getListVO(@Query("AndroidVO") UserListVO AndroidVO);
}
