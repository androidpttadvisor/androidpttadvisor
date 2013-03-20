package gov.cdc;

import gov.cdc.R;
import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

/**
 * A simple activity to display the info.html page.  All this does is set the webView's content to assets/info.html.
 * @author Will
 *
 */
public class InfoView extends Activity {
	
	private WebView webView;
	
	// set the the layout's infoView to webView and set its content to assets/info.html.
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_info_view);
		
		webView = (WebView) findViewById(R.id.infoView);

		webView.loadUrl("file:///android_asset/html/info.html");
	}
}
