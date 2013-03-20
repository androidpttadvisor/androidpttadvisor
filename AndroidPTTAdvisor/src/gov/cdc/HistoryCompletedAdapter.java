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
import android.widget.ImageView;
import android.widget.TextView;
import android.view.LayoutInflater;
import android.app.Activity;

public class HistoryCompletedAdapter extends BaseAdapter {
	
	private Context mContext;
	public static ArrayList<PTTHistoryItem> mHistory;
	private Activity activity;
	private static LayoutInflater inflater=null;
	
	public HistoryCompletedAdapter(Activity a, ArrayList<PTTHistoryItem> h) {
		activity = a;
		inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		// TODO Auto-generated constructor stub
		
		mHistory = h;
		Log.d("MAKE ADAPTER w/ count:",Integer.toString(mHistory.size()));
		
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
		
		ImageView iv = (ImageView)vi.findViewById(R.id.list_image);
		
		
		title.setText("Q: " + mHistory.get(position).getNode().getQuestion());
		answer.setText(mHistory.get(position).getAnswerChosen().answer);
		int numOfAns = mHistory.get(position).getNode().getAnswers().size();
		//view.setTextAppearance(mContext, R.style.blackMedium);
		
		if ( (position == 0) && (position == getCount() - 1) ) {
			vi.setBackgroundResource(R.drawable.history_row_selector_both);
		}
		else if (position == 0) {
			vi.setBackgroundResource(R.drawable.history_row_selector_rounded_top);
		} else if (position == getCount() - 1) {
			vi.setBackgroundResource(R.drawable.history_row_selector_rounded_bottom);
			
			
		} else {
			vi.setBackgroundResource(R.drawable.history_row_selector_middle);
			
		}
		
		if (numOfAns == 1) {
			iv.setImageResource(R.drawable.history_statement_icon);
		} else if (numOfAns == 2) {
			iv.setImageResource(R.drawable.history_question_icon);
		}
		
		return vi;
	}

}
