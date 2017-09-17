package com.top_adv.myapplication1.Alarm;

import android.os.Parcel;
import android.os.Parcelable;

public class AlarmData implements Parcelable{
	
	public int resource;
	public String uri;
	public String title;
	public String message;
	public String ticker;

	
	public AlarmData() {
	}
	public AlarmData(Parcel in) {
		readFromParcel(in);
	}
	
	private void readFromParcel(Parcel in) {
		resource = in.readInt();
		uri = in.readString();
		title = in.readString();
		message = in.readString();
		ticker = in.readString();		
	}

	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeInt(resource);
		dest.writeString(uri);
		dest.writeString(title);
		dest.writeString(message);
		dest.writeString(ticker);
	}
	
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {

		@Override
		public AlarmData createFromParcel(Parcel in) {
			return new AlarmData(in);
		}

		@Override
		public AlarmData[] newArray(int size) {
			// TODO Auto-generated method stub
			return new AlarmData[size];
		}
		
	};
	
	
	//get,set method
	public int getResource() {
		return resource;
	}

	public void setResource(int resource) {
		this.resource = resource;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getTicker() {
		return ticker;
	}

	public void setTicker(String ticker) {
		this.ticker = ticker;
	}
	
	
	
}
