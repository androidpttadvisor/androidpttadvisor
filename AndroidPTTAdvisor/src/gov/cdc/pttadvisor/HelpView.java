package gov.cdc.pttadvisor;

import gov.cdc.pttadvisor.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;

/**
 * A simple activity to display the help.html page.  All this does is set the webView's content to assets/help.html.
 * @author Will
 *
 */
public class HelpView extends Activity {
	
	
	private WebView webView;
	
	// set the the layout's helpView to webView and set its content to assets/help.html.
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_help_view);
		
		webView = (WebView) findViewById(R.id.helpView);

		webView.loadUrl("file:///android_asset/html/help.html");
		
		// Registering the "finish" action to the "Done" button
				final Button doneButton = (Button)findViewById(R.id.doneButton);
		        doneButton.setOnClickListener(new View.OnClickListener() {
		            public void onClick(View view) {
		            	finish();
		            }
		        });
	}
	
	
}
