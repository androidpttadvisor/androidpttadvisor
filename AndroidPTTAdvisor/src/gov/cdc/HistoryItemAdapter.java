package gov.cdc;

// Author: Paul Brown
// Originally created on 2013-01-29
// Serious amount of help from this blog post: http://www.androidhive.info/2012/02/android-custom-listview-with-image-and-text/

import java.util.ArrayList;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.view.LayoutInflater;
import android.app.Activity;

public class HistoryItemAdapter extends BaseAdapter {
	
	private Context mContext;
	public static ArrayList<PTTHistoryItem> mHistory;
	private Activity activity;
	private static LayoutInflater inflater=null;
	
	public HistoryItemAdapter(Activity a, ArrayList<PTTHistoryItem> h) {
		activity = a;
		inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		// TODO Auto-generated constructor stub
		
		mHistory = h;
		
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
		
		//TextView view = null;
		View vi = convertView;
		
		if (convertView == null) {
			// If it's not recycled, initialize a new view
			vi = inflater.inflate(R.layout.list_row, null);
		}
		
		TextView title = (TextView)vi.findViewById(R.id.title);
		TextView answer = (TextView)vi.findViewById(R.id.answer);
		
		
		title.setText("Q: " + mHistory.get(position).getNode().getQuestion());
		answer.setText(mHistory.get(position).getAnswerChosen().answer);
		//view.setTextAppearance(mContext, R.style.blackMedium);
		
		return vi;
	}

}
