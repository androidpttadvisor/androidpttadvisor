package gov.cdc;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import android.os.AsyncTask;
import android.widget.Toast;
import android.util.Log;
import android.content.Context;

public class JsonUpdaterTask extends AsyncTask {

	private String UPDATEURL = "https://raw.github.com/androidpttadvisor/androidpttadvisor/master/AndroidPTTAdvisor/assets/DTNode.json";
	private Context context;
	
	public JsonUpdaterTask (Context context) {
		this.context = context;
	}
	
	@Override
	protected Object doInBackground(Object... params) {
		try {
			/**
			 * Pull JSON from the update URL and cram it into webJsonString
			 */
			URL url = new URL(UPDATEURL);
			URLConnection urlConn = url.openConnection();
			InputStream inStream = urlConn.getInputStream();
			byte[] data = new byte[inStream.available()];
			inStream.read(data);
			inStream.close();
			String webJsonString = new String(data);
			Log.d("JSON Updater", "Pulled new JSON from " + UPDATEURL);
			
			/**
			 * Pull the local copy of the JSON and put it in localJsonString
			 */
			FileInputStream file;
	        try {
	        	file = this.context.openFileInput("DTNode.json");
	            data = new byte[file.available()];
	            file.read(data);
	            file.close();
	            String localJsonString = new String(data);
	        } catch (IOException e1) {
	            // TODO Auto-generated catch block
	            e1.printStackTrace();
	        } 
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

}
