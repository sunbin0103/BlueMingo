package com.top_adv.myapplication1.ListView;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.media.Image;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.kakao.kakaolink.KakaoLink;
import com.kakao.kakaolink.KakaoTalkLinkMessageBuilder;
import com.kakao.util.KakaoParameterException;
import com.top_adv.myapplication1.DataVO.ListVO;
import com.top_adv.myapplication1.DataVO.OrderVO;
import com.top_adv.myapplication1.DataVO.ParcelList;
import com.top_adv.myapplication1.MainActivity;
import com.top_adv.myapplication1.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by sst on 2016-10-07.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    public static final String TAG = MainActivity.TAG;
    public static final String ORDERVO_TO_PURCHASE = "OrderPurchase.Activity";
    public static final String IMG_URL = "http://www.bluemingo.xyz/bluemingo/image/";

    public ArrayList<ListVO> listData;
    public ArrayList<ViewHolder> viewHolderList;

    private Handler handler =  new Handler();
    private Runnable updateRemainingTimeRunnabler = new Runnable() {
        @Override
        public void run() {
            synchronized (viewHolderList) {
                for(ViewHolder holder : viewHolderList){
                    holder.resTimeCal();
                }
            }
        }
    };

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public LinearLayout item_center_linear;
        public ImageView item_image;
        public TextView item_title;
        public TextView item_price;
        public TextView item_trans;
        public TextView res_time;
        public ProgressBar res_bar;
        public LinearLayout share_button;

        ListVO lData;
        OrderVO orderVO;

        public ViewHolder(View view) {
            super(view);
            item_center_linear = (LinearLayout) view.findViewById(R.id.item_center_linear);
            item_image = (ImageView) view.findViewById(R.id.item_image);
            item_title = (TextView) view.findViewById(R.id.item_title);
            //item_title.setOnClickListener(this);
            item_price = (TextView) view.findViewById(R.id.item_price);
            item_trans = (TextView) view.findViewById(R.id.item_trans);
            res_time = (TextView) view.findViewById(R.id.res_time);
            res_bar = (ProgressBar) view.findViewById(R.id.res_bar);
            share_button = (LinearLayout) view.findViewById(R.id.share_button);
            //다이얼로그 널로 잡힙 값이 안넘어와진거? 객체 전달은 힘든가? 리스트에서 함수는 제대로 실행됨.
            //progressDialog = lData.getProgressDialog();
        }

        public void orderPurchase() {
            Integer user_key = 3;
            Integer res_quantity = 1;
            //주문 객체 생성
            //orderVO.setPurchase(user_key, lData.getTrade_key(), lData.getProduct_key(), res_quantity, lData.getPeriod());
            ArrayList<OrderVO> list = new ArrayList<>();
            list.add(orderVO);

            Intent intent = new Intent(itemView.getContext(), OrderPurchase.class);
            ParcelList pLists = new ParcelList();
            //pLists.setOrder_lists(list);
            intent.putExtra(ORDERVO_TO_PURCHASE, pLists);
            //intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            itemView.getContext().startActivity(intent);
            //RecyclerView 도 결국 view이고 이는 Context를 가지고 있으며 이를 호출하여 여러모로 사용 가능!!!
        }

        public void setData(ListVO lData){
            this.lData = lData;
            orderVO = new OrderVO();
        }

        public void resTimeCal(){
            TimeZone zone = TimeZone.getTimeZone("Asia/Seoul");
            Calendar cal = new GregorianCalendar(zone).getInstance();
            long  now_time = cal.getTimeInMillis();

            Long res = lData.getPeriod()-now_time;
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
            res_time.setText(timer);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public RecyclerViewAdapter(ArrayList<ListVO> listData) {
        viewHolderList = new ArrayList<>();
        startUpdateTimer();
        this.listData = listData;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                             int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_list_item, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }


    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final ListVO lData = listData.get(position);

        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        if (lData.getAdv_image() != null) {
            //holder.backGround.setVisibility(View.VISIBLE);
            //holder.item_image_linear.setBackgroundResource(R.mipmap.img01);
            holder.item_image.setImageResource(R.mipmap.img02);
        } else {
            new DownloadImageTask(holder.item_image).execute(IMG_URL+lData.getAdv_image());
        }

        //int progress = (int) (now_time/lData.end_time*100);
        holder.res_bar.setProgress(lData.getTrade_rescount());
        holder.res_bar.setMax(lData.getProduct_max_count());
        holder.item_title.setText(lData.getAdv_title());
        holder.item_price.setText(String.valueOf(lData.getProduct_sale_price()));
        holder.item_price.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        holder.item_trans.setText(String.valueOf(lData.getProduct_naver_price()));

        holder.item_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startItemDetailActivity(view, lData);
            }
        });

        holder.item_center_linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startItemDetailActivity(view, lData);
            }
        });

        holder.share_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowShareDialog(view, lData);
            }
        });

        synchronized (viewHolderList){
            holder.setData(lData);
            viewHolderList.add(holder);
            //if(viewHolderList.size()< (list.size()-2)) viewHolderList.add(holder);
        }
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
    }

    public ArrayList<ListVO> getListData() {
        // TODO Auto-generated method stub
        return listData;
    }

    public int getItemCount() {
        // TODO Auto-generated method stub
        return listData.size();
    }

    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    /*
    public void remove(int position) {
        listData.remove(position);
        dataChange();
    }

    public void sort() {
        Collections.sort(listData, ListData.ALPHA_COMPARATOR);
        dataChange();
    }

    public void dataChange() {
        this.notifyDataSetChanged();
    }
    */

    public void setFilter(ArrayList<ListVO> lists) {
        listData.clear();
        listData.addAll(lists);
        notifyDataSetChanged();
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

    private void startItemDetailActivity(View view, ListVO lData) {
        Intent intent = new Intent(view.getContext(), ItemDetailPage.class);
        intent.putExtra("PRODUCT_ID", lData.getProduct_id());
        intent.putExtra("PRODUCT_PERIOD", lData.getPeriod());
        intent.putExtra("PRODUCT_MAX_COUNT", lData.getProduct_max_count());
        intent.putExtra("PRODUCT_RES_COUNT", lData.getTrade_rescount());

        view.getContext().startActivity(intent);

        Log.d(TAG, lData.getProduct_id().toString());
    }

    private void ShowShareDialog(View view, final ListVO lData)
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
        share_kakao_linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareKakao(view, lData);
            }
        });

        btn_share_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myDialog.cancel();
            }
        });

    }

    public void shareKakao(View view, ListVO lData) {
        try {
            final KakaoLink kakaoLink  = KakaoLink.getKakaoLink(view.getContext());
            final KakaoTalkLinkMessageBuilder kakaoBuilder = kakaoLink.createKakaoTalkLinkMessageBuilder();

            // 메세지 추가
            kakaoBuilder.addText(lData.getAdv_title());

            // 이미지 추가
            String url = "https://www.google.co.kr/images/branding/googlelogo/2x/googlelogo_color_272x92dp.png"; // 이거 나중에 getImage 로 바꾸어야함
            kakaoBuilder.addImage(url, 272, 92);

            // 앱 실행버튼 추가
            kakaoBuilder.addAppButton("앱 실행");

            // 메세지 발송
            kakaoLink.sendMessage(kakaoBuilder.build(), view.getContext());

        } catch (KakaoParameterException e) {
            e.printStackTrace();
        }
    }
}
