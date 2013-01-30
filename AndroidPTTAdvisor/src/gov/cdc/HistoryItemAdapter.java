package gov.cdc;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class HistoryItemAdapter extends BaseAdapter {
	
	private Context mContext;
	PTTController controller;
	
	public HistoryItemAdapter(Context context) {
		this.mContext = context;
		// TODO Auto-generated constructor stub
		
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		
		return 3;
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
		
		view.setText("  Row "+ position);
		
		return view;
	}

}
