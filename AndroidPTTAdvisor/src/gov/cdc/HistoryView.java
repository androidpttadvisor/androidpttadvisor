package gov.cdc;

//Author: Paul Brown
//Originally created on 2013-01-29

import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.content.Intent;

public class HistoryView extends Activity {
	
	private ArrayList<PTTHistoryItem> mHistory;
	private ArrayAdapter<String> arrayAdapter;
	

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
     // Make the application Full-Screen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        
        setContentView(R.layout.activity_history_view);

        final Button doneButton = (Button)findViewById(R.id.doneButton);
        doneButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                //startActivity(new Intent(MyActivity.this,ActivityEins.class));
            	finish();
            }
        });
        
        final ListView myListView = (ListView)findViewById(R.id.my_list_view);
        
        // Create the adapter
        final HistoryItemAdapter historyItemAdapter = new HistoryItemAdapter(this);
        
        //arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, new String[]{"Dave","Satya","Dylan"});
        
        //bind adapter to the AdapterView
        myListView.setAdapter(historyItemAdapter);
        //myListView.setAdapter(arrayAdapter);
        
        myListView.setOnItemClickListener(new OnItemClickListener() {
        	
        	@Override
        	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        		Intent i = getIntent();
        		i.putExtra("nodeToNavigateTo", position);
        		setResult(1,i);
        		finish();
        	}
        });
        
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_history_view, menu);
        return true;
    }
}
