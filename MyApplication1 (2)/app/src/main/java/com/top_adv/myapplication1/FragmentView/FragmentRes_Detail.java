package com.top_adv.myapplication1.FragmentView;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.top_adv.myapplication1.DataVO.AdvVO;
import com.top_adv.myapplication1.MainActivity;
import com.top_adv.myapplication1.MainViewPage;
import com.top_adv.myapplication1.R;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

/**
 * Created by sst on 2016-10-18.
 */
public class FragmentRes_Detail extends Fragment {
    private static String TAG = MainActivity.TAG;
    private static String ADVVO_TO_MAIN_VIEW_PAGE = "main_view_page.advVO";

    private TextView res_order_date;
    private TextView res_order_number;

    private ImageView res_product_img;
    private TextView res_product_name;
    private TextView res_product_price_text;
    private TextView res_product_price;
    private TextView res_delivery_price_text;
    private TextView res_delivery_price;
    private TextView res_total_price_text;
    private TextView res_total_price;

    private TextView res_address_text;
    private TextView res_address;
    private Button btn_delivery_inquiry;
    private Button btn_change_defective_item;
    private Button btn_res_order_cancel;

    public FragmentRes_Detail() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_res_detail, container, false);

        //CreateActionBar();

        res_order_date = (TextView) layout.findViewById(R.id.res_order_date);
        res_order_number = (TextView) layout.findViewById(R.id.res_order_number);
        res_product_img = (ImageView) layout.findViewById(R.id.res_product_img);
        res_product_name = (TextView) layout.findViewById(R.id.res_product_name);
        res_product_price_text = (TextView) layout.findViewById(R.id.res_product_price_text);
        res_product_price = (TextView) layout.findViewById(R.id.res_product_price);
        res_delivery_price_text = (TextView) layout.findViewById(R.id.res_delivery_price_text);
        res_delivery_price = (TextView) layout.findViewById(R.id.res_delivery_price);
        res_total_price_text = (TextView) layout.findViewById(R.id.res_total_price_text);
        res_total_price = (TextView) layout.findViewById(R.id.res_total_price);
        res_address_text = (TextView) layout.findViewById(R.id.res_address_text);
        res_address = (TextView) layout.findViewById(R.id.res_address);
        btn_delivery_inquiry = (Button) layout.findViewById(R.id.btn_delivery_inquiry);
        btn_change_defective_item = (Button) layout.findViewById(R.id.btn_change_defective_item);
        btn_res_order_cancel = (Button) layout.findViewById(R.id.btn_res_order_cancel);

        res_product_price_text.setText("상품가격");
        res_delivery_price_text.setText("배송료");
        res_total_price_text.setText("총 결제금액");

        res_address_text.setText("배송 주소");

        btn_delivery_inquiry.setText("배송조회");
        btn_change_defective_item.setText("교환문의");
        btn_res_order_cancel.setText("주문취소");

        /* 배송 주문에 따른 데이터 입력 필요 */
        /* setOnClickListener 구현 필요 */

        return layout;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    public AdvVO getIntentAdvVO() {
        Intent intent = getActivity().getIntent();
        AdvVO fAdvVO = new AdvVO();
        if(intent != null) {
            Bundle bundle = intent.getExtras();
            fAdvVO = bundle.getParcelable(ADVVO_TO_MAIN_VIEW_PAGE);
            Log.i(TAG, "purchase. onCreateView AdvVO : " + fAdvVO.getAdv_title());
        }
        return fAdvVO;
    }

    public void CreateActionBar() {
        ActionBar actionBar = ((MainViewPage) getActivity()).getSupportActionBar();

        // Custom Actionbar를 사용하기 위해 CustomEnabled을 true 시키고 필요 없는 것은 false 시킨다
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(false);         //액션바 아이콘을 업 네비게이션 형태로 표시합니다.
        actionBar.setDisplayShowTitleEnabled(false);        //액션바에 표시되는 제목의 표시유무를 설정합니다.
        actionBar.setDisplayShowHomeEnabled(false);         //홈 아이콘을 숨김처리합니다.
        actionBar.setElevation(0);                          //그림자 없애기.
        actionBar.setBackgroundDrawable(new ColorDrawable(0x00000000));

        //layout을 가지고 와서 actionbar에 포팅을 시킵니다.
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(LAYOUT_INFLATER_SERVICE);
        View actionbar = inflater.inflate(R.layout.action_bar, null);

        TextView title = (TextView) actionbar.findViewById(R.id.action_bar_title);
        title.setVisibility(View.GONE);

        actionBar.setCustomView(actionbar);
    }
}
