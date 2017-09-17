package com.top_adv.myapplication1.ListView;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.top_adv.myapplication1.DataVO.DetailVO;
import com.top_adv.myapplication1.DataVO.OptionVO;
import com.top_adv.myapplication1.DataVO.Ref_listVO;
import com.top_adv.myapplication1.MainActivity;
import com.top_adv.myapplication1.NetWork.AsyncCallBack;
import com.top_adv.myapplication1.NetWork.JsonParsing;
import com.top_adv.myapplication1.NetWork.NetWorkConnectionImpl;
import com.top_adv.myapplication1.R;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by sst on 2016-12-16.
 */
public class ItemDetailPage extends AppCompatActivity {

    private static String TAG = MainActivity.TAG;
    private static String IMG_URL = "http://www.bluemingo.xyz/bluemingo/image/";
    private static Long LONG_E = -9223372036854775808L;
    private static Integer INT_E = -2147483648;

    private ArrayList<DetailVO> details;
    private Ref_listVO refListVO;
    private DetailVO detailVO;
    private ArrayList<OptionVO> options;
    private ArrayList<String> imgURL;

    private OptionSelectRecyclerAdapter mAdapter;
    private ItemDetailImgAdapter imgAdapter;

    private String productID;
    private Long productPeriod;
    private Integer productResCount;
    private Integer productMaxCount;

    private Handler handler;
    private Runnable updateRemainingTimeRunnabler = new Runnable() {
        @Override
        public void run() {
            resTimeCal();
        }
    };

    private JsonParsing jsonParsing;

    private TextView prod_detail_res_time;
    private ProgressBar prod_detail_res_bar;
    private TextView prod_detail_res_status;
    private ViewPager viewpager_detail_image;
    private ImageView imgview_detail_image;
    private TextView prod_detail_detail;
    private TextView prod_detail_rprice;
    private TextView prod_detail_sprice;
    private TextView prod_detail_nprice;
    private Spinner quantity_spinner;
    private RecyclerView list_option_select;
    private ImageView prod_detail_image;
    private ImageView btn_prod_detail_res;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
        //setTheme(android.R.style.Theme_Holo_Light_NoActionBar_TranslucentDecor);
        setContentView(R.layout.activity_item_detail);

        prod_detail_res_time = (TextView) findViewById(R.id.prod_detail_res_time);
        prod_detail_res_bar = (ProgressBar) findViewById(R.id.prod_detail_res_bar);
        prod_detail_res_status = (TextView) findViewById(R.id.prod_detail_res_status);
        viewpager_detail_image = (ViewPager) findViewById(R.id.viewpager_detail_image);
        imgview_detail_image = (ImageView) findViewById(R.id.imgview_detail_image);
        prod_detail_detail = (TextView) findViewById(R.id.prod_detail_detail);
        prod_detail_rprice = (TextView) findViewById(R.id.prod_detail_rprice);
        prod_detail_sprice = (TextView) findViewById(R.id.prod_detail_sprice);
        prod_detail_nprice = (TextView) findViewById(R.id.prod_detail_nprice);
        quantity_spinner = (Spinner) findViewById(R.id.quantity_spinner);
        list_option_select = (RecyclerView) findViewById(R.id.list_option_select);
        prod_detail_image = (ImageView) findViewById(R.id.prod_detail_image);
        btn_prod_detail_res = (ImageView) findViewById(R.id.btn_prod_detail_res);
    }

    @Override
    protected void onStart() {
        super.onStart();

        Intent intent = getIntent();
        productID = intent.getStringExtra("PRODUCT_ID");
        productPeriod = intent.getLongExtra("PRODUCT_PERIOD", LONG_E);
        productResCount = intent.getIntExtra("PRODUCT_RES_COUNT", INT_E);
        productMaxCount = intent.getIntExtra("PRODUCT_MAX_COUNT", INT_E);

        createListView();
        createViewPager();
        getDetailData();
    }

    protected void getDetailData() {
        details = new ArrayList<>();
        detailVO = new DetailVO();
        jsonParsing = new JsonParsing(DetailVO.class);

        NetWorkConnectionImpl network = new NetWorkConnectionImpl(new AsyncCallBack() {
            @Override
            public void onTaskDone(Object... params) {
                try {
                    if(params != null) {
                        details = (ArrayList<DetailVO>) jsonParsing.getResult(params);
                        detailVO = details.get(0);
                        setDetailData(detailVO);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException ee) {
                    ee.printStackTrace();
                }
            }
        }, ItemDetailPage.this) {};
        network.setMethod("android/getItemDetail?product_id="+productID,false,false,"GET");
        network.getNetWorkResult();

    }

    protected void setDetailData(final DetailVO detailVO) {
        // item_image 띄우기 + 무한 슬라이드
        /*if(detailVO.getItem_image() !=null) {
            for(int i=0; i<detailVO.getItem_image().length(); i++) {
                imgURL.add(detailVO.getItem_image());
            }
        }*/

        if (detailVO.getItem_image().equals("이미지")) {
            imgURL.add("https://www.google.co.kr/images/branding/googlelogo/2x/googlelogo_color_120x44dp.png");
            imgURL.add("http://img.naver.net/static/www/u/2013/0731/nmms_224940510.gif");
        } else
            imgURL.add(IMG_URL + detailVO.getItem_image());

        if(imgURL.size() > 1) {
            imgview_detail_image.setVisibility(View.GONE);
            viewpager_detail_image.setAdapter(imgAdapter);
            viewpager_detail_image.setCurrentItem(imgURL.size());
            viewpager_detail_image.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                    if (position < imgURL.size())
                        viewpager_detail_image.setCurrentItem(position + imgURL.size(), false);
                    else if (position >= imgURL.size() * 2)
                        viewpager_detail_image.setCurrentItem(position - imgURL.size(), false);
                }

                @Override
                public void onPageSelected(int position) {

                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
        } else if (imgURL.size() == 1){
            viewpager_detail_image.setVisibility(View.GONE);
            new DownloadImageTask(imgview_detail_image).execute(imgURL.get(0));
        }

        // 남은 거래 시간
        handler = new Handler();
        startUpdateTimer();

        // 정보 입력
        prod_detail_res_bar.incrementProgressBy(productResCount);
        prod_detail_res_bar.setMax(productMaxCount);
        prod_detail_res_status.setText(productResCount + "/" + productMaxCount);

        prod_detail_detail.setText(detailVO.getItem_inform());
        prod_detail_rprice.setText(detailVO.getItem_direct_price() + " 원  ▶");
        prod_detail_rprice.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        prod_detail_sprice.setText(detailVO.getItem_sale_price() + " 원");
        prod_detail_nprice.setText("최저가 : " + detailVO.getItem_naver_price() + " 원");

        // 옵션 선택 리스트 구현
        ArrayList<String> option_list; // Selected option list
        option_list = new ArrayList<String>();

        if(detailVO.getOption_list().size() > 1) {
            option_list.add("옵 션");

            for (int i = 0; i < detailVO.getOption_list().size(); i++) {
                option_list.add(detailVO.getOption_list().get(i).getOption_value());
            }

            ArrayAdapter<String> listAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, option_list);
            quantity_spinner.setAdapter(listAdapter);
            quantity_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {

                    boolean check = false;
                    if (position != 0) {

                        for (int i = 0; i < options.size(); i++) {
                            if (options.get(i).getOption_list().getRef_list_key().equals(detailVO.getOption_list().get(position - 1).getRef_list_key()))
                                check = true;
                        }

                        if (check)
                            Toast.makeText(ItemDetailPage.this, "이미 선택된 옵션입니다.", Toast.LENGTH_LONG).show();
                        else {
                            options.add(new OptionVO(detailVO.getOption_list().get(position - 1), detailVO.getOption_value()));
                            mAdapter.notifyItemInserted(options.size());
                        }

                        quantity_spinner.setSelection(0);
                        mAdapter.setDelButtonEnabled();
                    }

                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                }
            });
        } else {
            option_list.add(detailVO.getOption_list().get(0).getOption_value());
            options.add(new OptionVO(detailVO.getOption_list().get(0), detailVO.getOption_value()));
            mAdapter.notifyItemInserted(0);

            mAdapter.setDelButtonDisabled();
            quantity_spinner.setVisibility(View.GONE);
        }

        if (detailVO.getItem_detail_image().equals("상세이미지"))
            prod_detail_image.setImageResource(R.drawable.no_detail_image);
        else
            new DownloadImageTask(prod_detail_image).execute(IMG_URL + detailVO.getItem_detail_image());

        btn_prod_detail_res.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(detailVO.getOption_list() == null || !options.isEmpty())
                    ShowOrderDialog();
                else
                    Toast.makeText(ItemDetailPage.this, "옵션을 선택해주세요.", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void createListView() {
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        options = new ArrayList<OptionVO>();
        options.clear();
        list_option_select.setLayoutManager(mLayoutManager);
        mAdapter = new OptionSelectRecyclerAdapter(options);
        list_option_select.setAdapter(mAdapter);
        list_option_select.setItemAnimator(null);
    }

    private void createViewPager() {
        imgURL = new ArrayList<String>();
        imgAdapter = new ItemDetailImgAdapter(getLayoutInflater(), imgURL);
    }

    private void startUpdateTimer(){
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(updateRemainingTimeRunnabler);
            }
        }, 1000, 1000);
    }

    public void resTimeCal(){
        TimeZone zone = TimeZone.getTimeZone("Asia/Seoul");
        Calendar cal = new GregorianCalendar(zone).getInstance();
        long  now_time = cal.getTimeInMillis();

        Long res = productPeriod-now_time;
        String timer = "00:00:00";
        if(res > 0) {
            int hours = (int) (res / (1000 * 60 * 60));
            int minutes = (int) (res % (1000 * 60 * 60)) / (1000 * 60);
            int seconds = (int) ((res % (1000 * 60 * 60)) % (1000 * 60) / 1000);
            if (hours > 23) {
                timer = String.format("%2d일 %02d:%02d:%02d", hours / 24, hours - (hours / 24) * 24, minutes, seconds);
            } else {
                timer = String.format("%02d:%02d:%02d", hours, minutes, seconds);
            }
        }

        prod_detail_res_time.setText(timer);
    }

    private void ShowOrderDialog()
    {
        int final_price = 0;

        LayoutInflater dialog = LayoutInflater.from(this);
        final View dialogLayout = dialog.inflate(R.layout.dialog_order, null);
        final Dialog myDialog = new Dialog(this);

        myDialog.setContentView(dialogLayout);
        myDialog.show();

        RecyclerView list_order = (RecyclerView) dialogLayout.findViewById(R.id.list_order);
        TextView order_final_price = (TextView) dialogLayout.findViewById(R.id.order_final_price);
        TextView order_info = (TextView) dialogLayout.findViewById(R.id.order_info);
        Button btn_order_purchase = (Button)dialogLayout.findViewById(R.id.btn_order_purchase);
        Button btn_order_cancel = (Button)dialogLayout.findViewById(R.id.btn_order_cancel);

        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        list_order.setLayoutManager(manager);
        OptionListRecyclerAdapter mAdapter = new OptionListRecyclerAdapter(options);
        list_order.setAdapter(mAdapter);

        for(int i=0; i<options.size(); i++)
            final_price += options.get(i).getOption_rprice();

        order_final_price.setText(String.valueOf(final_price)+" 원");
        order_info.setText("구매전 주의! 꼭 읽어주세요!\n" +
                           "\n" +
                           "1. 공동구매가 종료되면 단순변심 주문취소가 불가합니다.\n" +
                           "\n" +
                           "2. 공동구매가 성사되지 않으면 결제는 자동 취소됩니다.\n");

        btn_order_purchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ItemDetailPage.this, OrderPurchase.class);
                //intent.putExtra("ORDER_OPTION", options); // 선택된 option_list

                startActivity(intent);
                /* 이제 여기서 결제로 넘어간다!!! */
            }
        });

        btn_order_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.cancel();
            }
        });
    }

    public void detailTopClick(View view) {
        switch (view.getId()) {
            case R.id.btn_detail_back:
                finish();
                break;
            case R.id.btn_detail_share:
                ShowShareDialog(view);
                break;
        }
    }

    private void ShowShareDialog(View view)
    {
        LayoutInflater dialog = LayoutInflater.from(view.getContext());
        final View dialogLayout = dialog.inflate(R.layout.dialog_share, null);
        final Dialog myDialog = new Dialog(view.getContext());

        myDialog.setContentView(dialogLayout);
        myDialog.show();

        LinearLayout share_kakao_linear = (LinearLayout) dialogLayout.findViewById(R.id.share_kakao_linear);
        TextView share_kakao_text = (TextView) dialogLayout.findViewById(R.id.share_kakao_text);
        LinearLayout share_link_linear = (LinearLayout) dialogLayout.findViewById(R.id.share_link_linear);
        TextView share_link_text = (TextView) dialogLayout.findViewById(R.id.share_link_text);
        Button btn_share_cancel = (Button) dialogLayout.findViewById(R.id.btn_share_cancel);

        share_kakao_text.setText("카카오톡");
        share_link_text.setText("URL 복사");
        btn_share_cancel.setText("취소");

        /* share_kakao_linear랑 share_link_linear에 각각 setOnClickListener 생성해주기! */

        btn_share_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myDialog.cancel();
            }
        });

    }
}
