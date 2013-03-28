package gov.cdc.pttadvisor;

// Author: Paul Brown
// Originally created on 2013-01-29
// Serious amount of help from this blog post: http://www.androidhive.info/2012/02/android-custom-listview-with-image-and-text/
// This List Adapter takes care of the node in the HistoryView that the user is currently at

import gov.cdc.pttadvisor.R;

import java.util.ArrayList;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.LayoutInflater;
import android.app.Activity;

public class HistoryCurrentAdapter extends BaseAdapter {
	
	public static ArrayList<PTTHistoryItem> mHistory;
	private Activity activity;
	private static LayoutInflater inflater=null;
	public static PTTController controller;
	
	public HistoryCurrentAdapter(Activity a, int position) {
		activity = a;
		inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		// TODO Auto-generated constructor stub
		
		// This feels so wrong. It very well might be wrong
		controller = MainView.controller;
		
	}
	
	
	

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
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
		View view = convertView;
		
		if (convertView == null) {
			// If it's not recycled, initialize a new view
			view = inflater.inflate(R.layout.list_row, null);
		}
		
		// Grab and assign references to the elements from the layout file (list_row.xml)
		TextView questionAnswered = (TextView)view.findViewById(R.id.questionAnswered);
		ImageView imageView = (ImageView)view.findViewById(R.id.list_image);
		
		// Grab the answer view and then make it go away, to avoid having a blank space at the bottom,
		// because this question hasn't been answered yet
		TextView answer = (TextView)view.findViewById(R.id.answer);
		answer.setVisibility(View.GONE);
		
		// Grab the current node so we can then get the question the user is currently at
		PTTNode n = controller.currentNode;
		questionAnswered.setText(n.getQuestion());
		
		// Grab the number of answers for the particular question (to determine if its a question, info, or recommendation node)
		int numberOfAnswersForQuestion = n.getAnswers().size();
		
		// This element always stands alone, so it gets both top and bottom rounded
		view.setBackgroundResource(R.drawable.history_row_selector_both);
		
		// Assign the appropriate icon based on what type of question it is
		if ( (numberOfAnswersForQuestion == 1) || (numberOfAnswersForQuestion == 0) ) {
			imageView.setImageResource(R.drawable.history_statement_icon);
		} else if (numberOfAnswersForQuestion == 2) {
			imageView.setImageResource(R.drawable.history_question_icon);
		}
		
		return view;
	}

}
