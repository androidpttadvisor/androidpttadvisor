package gov.cdc;

// Author: Paul Brown
// I hate eclipse!

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.Window;

public class HistoryView extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
     // Make the application Full-Screen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        
        
        setContentView(R.layout.activity_history_view);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_history_view, menu);
        return true;
    }
}
