package us.paulbrown;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;

public class InfoScreen extends Activity {
	
	private WebView webView;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.infoscreen);
		
		webView = (WebView) findViewById(R.id.infoScreen);

		String html = "<html><body><p>Am I Not Informative?</p></body></html>";
		webView.loadData(html, "text/html", "utf-8");
	}
}
