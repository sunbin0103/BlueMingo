package com.top_adv.myapplication1;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.top_adv.myapplication1.DataVO.ListVO;
import com.top_adv.myapplication1.DataVO.ParcelList;

import java.util.ArrayList;

/**
 * Created by sst on 2016-10-07.
 */
public class ProgressBarAsync extends AsyncTask<
          Integer // .excute() 인수
        , String // 진행정보 publishProgress(), onProgressUpdate() 인수
        , ArrayList<ListVO> // doInBackground() 리턴 타입, onPostExcute() 인수
        > {
    private static String TAG = MainActivity.TAG;
    private static String ADVVO_TO_MAIN_VIEW_PAGE = "main_view_page.advVO";

    private ProgressDialog mDlg;
    private ProgressBar mPgb;
    private Context mContext;

    private ParcelList pLists;
    private ArrayList<ListVO> lists;
    //ListNetWork listConn;

    MainActivity activity = (MainActivity)MainActivity.activity;

    private static String MAIN_VIEW_PAGE = "com.top_adv.myapplication1.MainViewPage.go";

    public ProgressBarAsync(Context context, ProgressBar progressBar){
        mContext = context;
        mPgb = progressBar;
    }


    /* .excute() 실행시 doInBacground()실행전에 호출됨
     *  mDlg 기본 세팅, show()
     */
    @Override
    protected void onPreExecute() {
        //mDlg = new ProgressDialog(mContext);
        //mDlg.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        //mDlg.setMessage("Loading ...");
        //mDlg.setIndeterminate(true);
        //mDlg.setCancelable(false);
        //mDlg.show();
        //mDlg.setContentView(R.layout.ring_progress);

        //pAdvVO = new AdvVO();


        super.onPreExecute();
    }

    // 넘겨받은 인수 : 작업 개수
    @Override
    protected ArrayList<ListVO> doInBackground(Integer... params) {
        final int taskCnt = params[0];
        // 작업개수 -> mDlg의 Max값 publishProgress() 넘기면 onProgressUpdate()실행됨
        publishProgress("max",Integer.toString(taskCnt));
        /*
        if(advConn.setConnection() == null){
            Toast.makeText(mContext, "네트워크에 문제가 있습니다.", Toast.LENGTH_SHORT).show();
            android.os.Process.killProcess(android.os.Process.myPid());
        }*/

        //listConn.setURL("http://192.168.0.60:8080/android/getlist");

        //listConn = new ListNetWork();
        //lists = listConn.getResult_List();

        int i = 0;
        while(lists == null){
            if(i<taskCnt) {
                try{
                    Thread.sleep(100);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                publishProgress("progress",Integer.toString(i), "작업 번호 "+Integer.toString(i)+"번 수행중");
                i++;
            }
            else
                break;

        }
        i = taskCnt;
        publishProgress("progress",Integer.toString(i), "작업 번호 "+Integer.toString(i)+"번 수행중");
        /*
        // 작업진행(ProgressBar 진행구간) 작업개수 * 100 만큼 sleep()
        for(int i=0; i<taskCnt; ++i){
            try{
                Thread.sleep(100);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            // 작업이 하나씩 진행될 때마다 정보 갱신
            publishProgress("progress",Integer.toString(i), "작업 번호 "+Integer.toString(i)+"번 수행중");
        }
        // 완료된 작업 개수 리턴, onPostExcute()의 인수
        */

        return lists;
    }

    // publishProgress()로 넘겨준 데이터를 받아옴
    @Override
    protected void onProgressUpdate(String... progress) {

        if(progress[0].equals("progress")){
            mPgb.setProgress(Integer.parseInt(progress[1]));
            //mDlg.setMessage(progress[2]);
        }
        else if(progress[0].equals("max")){
            mPgb.setMax(Integer.parseInt(progress[1]));
        }

    }

    // doInBackground() 이후 실행됨
    @Override
    protected void onPostExecute(ArrayList<ListVO> result) {
        //mDlg.dismiss();
        Toast.makeText(mContext, "adv_title : " + result.get(0).getAdv_title(), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(mContext, MainViewPage.class);
        pLists = new ParcelList();
        pLists.setList_lists(result);
        intent.putExtra(ADVVO_TO_MAIN_VIEW_PAGE,pLists );

        try{

            //PendingIntent pIntent = PendingIntent.getActivity(mContext, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP| Intent.FLAG_ACTIVITY_CLEAR_TOP);
            mContext.startActivity(intent);
            activity.finish();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
