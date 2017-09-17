package com.top_adv.myapplication1.ListView;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.top_adv.myapplication1.DataVO.OrderVO;
import com.top_adv.myapplication1.DataVO.ParcelList;
import com.top_adv.myapplication1.NetWork.AsyncCallBack;
import com.top_adv.myapplication1.R;

import java.util.ArrayList;

/**
 * Created by sst on 2016-12-16.
 */
public class OrderPurchase extends AppCompatActivity {

    public static final String ORDERVO_TO_PURCHASE = "OrderPurchase.Activity";
    private static final int SEARCH_ADDRESS_ACTIVITY = 10000;

    private Intent intent;

    private TextView order_product_text;
    private GridLayout order_product_list;
    private ArrayList<TextView> order_product;
    private TextView order_total_price_text;
    private TextView order_total_price;

    private TextView order_delivery_info;
    private EditText order_recipient_edit;
    private EditText order_phone_edit;
    private EditText order_address1_edit;
    private EditText order_address2_edit;

    private TextView order_payment;
    private RadioGroup order_payment_select;
    private RadioButton order_payment_credit;
    private RadioButton order_payment_transfer;
    private RadioButton order_payment_kakaopay;

    private TextView order_purchase_agree_text;
    private Button btn_order_purchase_complete;

    private ParcelList pLists;
    private ArrayList<OrderVO> order_lists;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_purchase);

        order_product_text = (TextView) findViewById(R.id.order_product_text);
        order_product_list = (GridLayout) findViewById(R.id.order_product_list);
        order_total_price_text = (TextView) findViewById(R.id.order_total_price_text);
        order_total_price = (TextView) findViewById(R.id.order_total_price);

        order_delivery_info = (TextView) findViewById(R.id.order_delivery_info);
        order_recipient_edit = (EditText) findViewById(R.id.order_recipient_edit);
        order_phone_edit = (EditText) findViewById(R.id.order_phone_edit);
        order_address1_edit = (EditText) findViewById(R.id.order_address1_edit);
        order_address2_edit = (EditText) findViewById(R.id.order_address2_edit);

        order_payment = (TextView) findViewById(R.id.order_payment);
        order_payment_select = (RadioGroup) findViewById(R.id.order_payment_select);
        order_payment_credit = (RadioButton) findViewById(R.id.order_payment_credit);
        order_payment_transfer = (RadioButton) findViewById(R.id.order_payment_transfer);
        order_payment_kakaopay = (RadioButton) findViewById(R.id.order_payment_kakaopay);

        order_purchase_agree_text = (TextView) findViewById(R.id.order_purchase_agree_text);
        btn_order_purchase_complete = (Button) findViewById(R.id.btn_order_purchase_complete);

        order_product_text.setText("구매 상품");
        order_total_price_text.setText("총 결제금액");

        order_delivery_info.setText("배송 정보");
        order_recipient_edit.setHint("수령인");
        order_phone_edit.setHint("연락처");
        order_address1_edit.setHint("주소");
        order_address2_edit.setHint("상세 주소");

        order_payment.setText("결제 방법");
        order_payment_credit.setText("신용카드");
        order_payment_transfer.setText("계좌이체");
        order_payment_kakaopay.setText("카카오페이");

        order_purchase_agree_text.setText("결제 대행 서비스 약관에 동의합니다.");
        btn_order_purchase_complete.setText("결제하기");

        order_address1_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OrderPurchase.this, AddressSearchActivity.class);
                startActivityForResult(intent, SEARCH_ADDRESS_ACTIVITY);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        ActionBar actionBar = getSupportActionBar();

        // Custom Actionbar를 사용하기 위해 CustomEnabled을 true 시키고 필요 없는 것은 false 시킨다
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(false);         //액션바 아이콘을 업 네비게이션 형태로 표시합니다.
        actionBar.setDisplayShowTitleEnabled(false);        //액션바에 표시되는 제목의 표시유무를 설정합니다.
        actionBar.setDisplayShowHomeEnabled(false);         //홈 아이콘을 숨김처리합니다.
        actionBar.setElevation(0);                          //그림자 없애기.
        actionBar.setBackgroundDrawable(new ColorDrawable(0x00000000));

        //layout을 가지고 와서 actionbar에 포팅을 시킵니다.
        LayoutInflater inflater = (LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);
        View actionbar = inflater.inflate(R.layout.action_bar, null);

        TextView title = (TextView) actionbar.findViewById(R.id.action_bar_title);
        title.setVisibility(View.GONE);

        actionBar.setCustomView(actionbar);
        return true;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent){
        super.onActivityResult(requestCode, resultCode, intent);

        switch(requestCode){
            case SEARCH_ADDRESS_ACTIVITY:
                if(resultCode == RESULT_OK){
                    String data = intent.getExtras().getString("data");
                    if (data != null)
                        order_address1_edit.setText(data);
                }
                break;
        }

    }

    public void actionBarClick (View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                finish();
                break;
        }
    }

    public OrderVO getIntentOrderVO() {
        OrderVO orderVO = null;
        Intent intent = OrderPurchase.this.getIntent();
        if(intent != null) {
            Bundle bundle = intent.getExtras();
            pLists = bundle.getParcelable(ORDERVO_TO_PURCHASE);
            //order_lists = pLists.getOrder_lists();
            orderVO = order_lists.get(0);
        }
        return orderVO;
    }
}
