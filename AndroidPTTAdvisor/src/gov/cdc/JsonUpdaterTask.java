package gov.cdc;

import java.io.FileInputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.io.BufferedInputStream;

import android.os.AsyncTask;
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
		String webJsonString = null;
		String localJsonString = null;
		try {
			/**
			 * Pull the remote JSON and save a copy locally
			 */
			URL url = new URL(UPDATEURL);
			URLConnection urlConn = url.openConnection();
			BufferedInputStream inStream = new BufferedInputStream(urlConn.getInputStream());
			byte[] data = new byte[512];
	        int bytesRead = 0;
			OutputStream outStream = this.context.openFileOutput("jsonFromWeb.json", Context.MODE_PRIVATE);
	        while((bytesRead = inStream.read(data, 0, data.length)) >= 0) {
	            outStream.write(data, 0, bytesRead);
	        }
			inStream.close();
			outStream.close();
			Log.d("JSON Updater", "Pulled new JSON from " + UPDATEURL);
			 
			/**
			 * Read this new JSON as a String
			 */
	        webJsonString = fileToString("jsonFromWeb.json");
			
	        /**
	         * ditto for the local, "Active" json file
	         */
	        localJsonString = fileToString("DTNode.json");
	        
	        if (webJsonString != null && webJsonString.equals(localJsonString)) {
	        	Log.d("JSON Updater", "Web version matches local version!");
	        } else {
	        	Log.d("JSON Updater", "Web version does not match local version!");
	        	Log.d("JSON Updater", "Replacing local version with version from web!");
	        	replaceLocalJson("jsonFromWeb.json", "DTNode.json");
	        }
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * Read a file and spit it out as a String
	 * @param filename Name of file to read
	 * @return The String version of that file
	 */
	private String fileToString(String filename) {
		String outString = null;
		FileInputStream file;
        try {
        	file = this.context.openFileInput(filename);
            byte[] data = new byte[file.available()];
            file.read(data);
            file.close();
            outString = new String(data);
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        } 
		return outString;
	}

	private void replaceLocalJson(String source, String target) {
		File sourceFile = context.getFileStreamPath(source);
		File targetFile = new File(sourceFile.getParent(), target);
	    if (targetFile.exists()) {
	        context.deleteFile(target);        
	    }
	    sourceFile.renameTo(targetFile);
		
	}
}
