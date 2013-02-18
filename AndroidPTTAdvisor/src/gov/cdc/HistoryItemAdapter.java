package gov.cdc;

//Author: Paul Brown
//Originally created on 2013-01-29

import java.util.ArrayList;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class HistoryItemAdapter extends BaseAdapter {
	
	private Context mContext;
	public static ArrayList<PTTHistoryItem> mHistory;
	
	
	public HistoryItemAdapter(Context context) {
		this.mContext = context;
		// TODO Auto-generated constructor stub
		
		mHistory = MainView.mHistory;
		
	}
	
	
	

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		//Log.d("Adapter mHistory Size: ", Integer.toString(mHistory.size()));
		return mHistory.size();
		
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		
		TextView view = null;
		
		if (convertView == null) {
			// If it's not recycled, initialize a new view
			view = new TextView(this.mContext);
		} else {
			// Use the recycled view (convertView)
			view = (TextView)convertView;
			
		}
		
		
		view.setText("Q: " + mHistory.get(position).getNode().getQuestion() + "\nA: " + mHistory.get(position).getAnswerChosen().answer);
		view.setTextAppearance(mContext, R.style.blackMedium);
		
		return view;
	}

}
