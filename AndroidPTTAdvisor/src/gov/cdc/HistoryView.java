package gov.cdc;

// Author: Paul Brown
// I hate eclipse!

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.Window;
import android.widget.ListView;

public class HistoryView extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
     // Make the application Full-Screen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        
        
        setContentView(R.layout.activity_history_view);
        
        final ListView myListView = (ListView)findViewById(R.id.my_list_view);
        
        // Create the adapter
        final HistoryItemAdapter historyItemAdapter = new HistoryItemAdapter(this);
        
        //bind adapter to the AdapterView
        myListView.setAdapter(historyItemAdapter);
        
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_history_view, menu);
        return true;
    }
}
