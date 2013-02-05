package gov.cdc;

import gov.cdc.R;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;

public class HelpView extends Activity {
	
	private WebView webView;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_help_view);
		
		webView = (WebView) findViewById(R.id.helpView);

		webView.loadUrl("file:///android_asset/html/help.html");
	}
}
