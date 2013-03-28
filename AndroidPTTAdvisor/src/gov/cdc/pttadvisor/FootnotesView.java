package gov.cdc.pttadvisor;

import gov.cdc.pttadvisor.R;
import android.os.Bundle;
import android.app.ListActivity;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.ListView;
import java.util.ArrayList;

/**
 * Just a basic view for footnotes.  It uses the built-in ArrayAdapter for the grunt work.
 * @author Will
 *
 */
public class FootnotesView extends ListActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_footnotes_view);
		
		// Registering the "finish" action to the "Done" button
		final Button doneButton = (Button)findViewById(R.id.doneButton);
        doneButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
            	finish();
            }
        });

        // Get an ArrayList of footnote strings for this node and pass it off to the arrayAdapter
		ArrayList<String> footnotes = MainView.controller.currentNode.getFootnotes();
		String [] footnotes_array = footnotes.toArray(new String[footnotes.size()]);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.footnote_item, footnotes_array);
		setListAdapter(adapter);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_footnotes_view, menu);
		return true;
	}

}
