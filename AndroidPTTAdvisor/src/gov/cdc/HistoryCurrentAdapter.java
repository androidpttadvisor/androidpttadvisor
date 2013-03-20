package gov.cdc;

// Author: Paul Brown
// Originally created on 2013-01-29
// Serious amount of help from this blog post: http://www.androidhive.info/2012/02/android-custom-listview-with-image-and-text/

import java.util.ArrayList;
import java.util.ResourceBundle.Control;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.LayoutInflater;
import android.app.Activity;

public class HistoryCurrentAdapter extends BaseAdapter {
	
	private Context mContext;
	public static ArrayList<PTTHistoryItem> mHistory;
	private int mPosition;
	private Activity activity;
	private static LayoutInflater inflater=null;
	public static PTTController controller;
	
	public HistoryCurrentAdapter(Activity a, int position) {
		activity = a;
		inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		// TODO Auto-generated constructor stub
		
		mPosition = position;
		
		// This feels so wrong. It very well might be wrong
		controller = MainView.controller;
		
	}
	
	
	

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		//Log.d("Adapter mHistory Size: ", Integer.toString(mHistory.size()));
		return 1;
		
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
		
		ImageView iv = (ImageView)vi.findViewById(R.id.list_image);
		
		
		PTTNode n = controller.currentNode;
		
		title.setText(n.getQuestion());
		
		int numOfAns = n.getAnswers().size();
		//view.setTextAppearance(mContext, R.style.blackMedium);
		
		
		vi.setBackgroundResource(R.drawable.history_row_selector_both);
		
		if (numOfAns == 1) {
			iv.setImageResource(R.drawable.history_statement_icon);
		} else if (numOfAns == 2) {
			iv.setImageResource(R.drawable.history_question_icon);
		}
		
		return vi;
	}

}
