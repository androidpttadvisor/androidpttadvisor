package gov.cdc;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class HistoryView extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_view);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_history_view, menu);
        return true;
    }
}
