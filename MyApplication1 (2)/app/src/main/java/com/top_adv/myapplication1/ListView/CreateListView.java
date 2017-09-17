package com.top_adv.myapplication1.ListView;

import com.top_adv.myapplication1.DataVO.AdvVO;
import com.top_adv.myapplication1.DataVO.ListVO;
import com.top_adv.myapplication1.MainActivity;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;

public class CreateListView {
	
	private static String TAG = MainActivity.TAG;


	private Context mContext;
	private RecyclerView mRecyclerView;
	private RecyclerView.Adapter customAdapter;
	//private ProgressDialog progressDialog;
	private AdvVO advVO;
	private ArrayList<ListVO> listData;

	public CreateListView(Context context, RecyclerView recyclerView) {
		Log.i(TAG,"CreateListView, constructor");
		this.mContext = context;
		this.mRecyclerView = recyclerView;
		listData = new ArrayList<>();

		// Set LayoutManager for RecyclerView
		RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext);
		mRecyclerView.setLayoutManager(mLayoutManager);
		//progressDialog = new ProgressDialog(this.context);
	}

	public void setAdapter(){
		this.customAdapter = new RecyclerViewAdapter(listData);
		mRecyclerView.setAdapter(customAdapter);
		Log.i(TAG,"CreateListView, setAdapter");
	}

	public void getAdvData(){
		LoadDataTask load = new LoadDataTask(listData, advVO);
		Log.i(TAG,"CreateListView, getAdvData");
		load.execute();
	}

	private class LoadDataTask extends AsyncTask<String, Void, String>{

		public ArrayList<ListVO> mListData;
		private AdvVO advVO;
		//private ProgressDialog pd;

		public LoadDataTask(ArrayList<ListVO> listData, AdvVO advVO) {
			this.mListData = listData;
			this.advVO = advVO;
			//this.pd = pd;
		}

		@Override
		protected void onPreExecute() {
			//pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			//pd.setMessage("데이터를 불러오는중 입니다...");

			//pd.show();
			Log.i(TAG,"CreateListView, LoadDataTask.progressDialog");
			super.onPreExecute();
		}

		@Override
		protected void onProgressUpdate(Void... values) {
			// TODO Auto-generated method stub
			super.onProgressUpdate(values);
		}

		@Override
		protected String doInBackground(String... params) {
			/*AdvNetWork advConn = new AdvNetWork();
			Log.i(TAG,"CreateListView, LoadDataTask.doInBackground1");
			advConn.setConnection("GET");
			Log.i(TAG,"CreateListView, LoadDataTask.doInBackground2");
			this.advVO = advConn.jsonPassing_one(advConn.getJsonText());
			Log.i(TAG,"CreateListView, LoadDataTask.doInBackground3");*/
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			Log.i(TAG,"CreateListView, LoadDataTask.postExecute");
			//listData.add(new ListData(R.drawable.ohmygirl4, this.advVO.getAdv_title(), 10, 100));
			//addItem(R.drawable.ohmygirl4, this.advVO.getAdv_title(), 10, 100);
			//addItem(R.drawable.ohmygirl3, this.advVO.getAdv_message(), 10, 100);
			//addItem(R.drawable.ohmygirl, this.advVO.getAdv_ticker(), 10, 100);
			Log.i(TAG,"CheckListData : "+listData.get(0).getAdv_title());

			//pd.dismiss();
			super.onPostExecute(result);
		}
	}

	/*
	// data set Method
	public void addItem(int adv_Image, String adv_title, int minQuantity, int maxQuantity ) {
		Log.i(TAG,"adapter, addItem");
		ListData addInfo = new ListData();
		addInfo.adv_title = adv_title;
		addInfo.maxQuantity = maxQuantity;
		addInfo.minQuantity = minQuantity;
		addInfo.adv_Image = adv_Image;

		listData.add(addInfo);
	}
	*/
}
