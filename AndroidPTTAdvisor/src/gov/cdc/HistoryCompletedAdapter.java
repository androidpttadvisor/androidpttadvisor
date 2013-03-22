package gov.cdc;

// Author: Paul Brown
// Originally created on 2013-01-29
// Serious amount of help from this blog post: http://www.androidhive.info/2012/02/android-custom-listview-with-image-and-text/
// This List Adapter takes care of the nodes in the HistoryView that have already been answered

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
	
	public static ArrayList<PTTHistoryItem> mHistory;
	private Activity activity;
	private static LayoutInflater inflater=null;
	
	public HistoryCompletedAdapter(Activity a, ArrayList<PTTHistoryItem> h) {
		activity = a;
		inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mHistory = h;
		
	}
	
	
	

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
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
		View view = convertView;
		
		if (convertView == null) {
			// If it's not recycled, initialize a new view
			view = inflater.inflate(R.layout.list_row, null);
		}
		
		// Grab and assign references to the elements from the layout file (list_row.xml)
		TextView questionAnswered = (TextView)view.findViewById(R.id.questionAnswered);
		TextView answer = (TextView)view.findViewById(R.id.answer);
		ImageView imageView = (ImageView)view.findViewById(R.id.list_image);
		
		// Set the questionAnswered and answer fields to the question/answer from the history
		questionAnswered.setText(Integer.toString(position) + ". " + mHistory.get(position).getNode().getQuestion());
		answer.setText(mHistory.get(position).getAnswerChosen().answer);
		
		// Grab the number of answers for the particular question (to determine if its a question or info node)
		int numberOfAnswersForQuestion = mHistory.get(position).getNode().getAnswers().size();
		
		// Determine which ends (top or bottom) shoudl be rounded, based on if anything comes before/after this node
		if ( (position == 0) && (position == getCount() - 1) ) {
			view.setBackgroundResource(R.drawable.history_row_selector_both);
		}
		else if (position == 0) {
			view.setBackgroundResource(R.drawable.history_row_selector_rounded_top);
		}
		else if (position == getCount() - 1) {
			view.setBackgroundResource(R.drawable.history_row_selector_rounded_bottom);
		}
		else {
			view.setBackgroundResource(R.drawable.history_row_selector_middle);
		}
		
		// Assign the appropriate icon based on what type of question it is
		if ( (numberOfAnswersForQuestion == 1) || (numberOfAnswersForQuestion == 0) ) {
			imageView.setImageResource(R.drawable.history_statement_icon);
		} else if (numberOfAnswersForQuestion == 2) {
			imageView.setImageResource(R.drawable.history_question_icon);
		}
		
		return view;
	}

}
