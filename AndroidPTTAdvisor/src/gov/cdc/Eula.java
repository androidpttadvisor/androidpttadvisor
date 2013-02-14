package gov.cdc;

/**
 * This code is based off the example given here:
 * http://blog.donnfelker.com/2011/02/17/android-a-simple-eula-for-your-android-apps/
 */

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.preference.PreferenceManager;
import android.webkit.WebView;

/**
 *  A class to display a EULA dialog if 1) it's never been agreed to before or 2) the app's version
 *  number was updated
 * @author Will
 *
 */
public class Eula {
	private Activity mActivity;
	
	/**
	 * Create the Eula object, with the app's context.
	 * @param context
	 */
	public Eula(Activity context) {
		mActivity = context;
	}
	
	/**
	 * just a string prepended to variables to denote them as used by the Eula class
	 */
	private String EULA_PREFIX = "eula_";
	
	/**
	 * Retrieve and return the PackageInfo of this app; this contains, among other things,
	 * the version number.
	 * @return
	 */
	private PackageInfo getPackageInfo() {
        PackageInfo pi = null;
        try {
             pi = mActivity.getPackageManager().getPackageInfo(mActivity.getPackageName(), PackageManager.GET_ACTIVITIES);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return pi;
    }
	
	/**
	 * Main method.  Decides whether or not to display the EULA dialog; if so, display it.  If the 
	 * user agrees, close the dialog and continue the app.  If the user cancels, kill the app. 
	 */
    public void show() {
        PackageInfo versionInfo = getPackageInfo();

        // the eulaKey changes every time you increment the version number in the AndroidManifest.xml
        final String eulaKey = EULA_PREFIX + versionInfo.versionCode;
        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mActivity);
        boolean hasBeenShown = prefs.getBoolean(eulaKey, false);
        if(hasBeenShown == false){

        	// create the WebView displaying the EULA html file
            WebView webView = new WebView(mActivity);
            webView.loadUrl("file:///android_asset/html/eula.html");
            
            // build the dialog box, setting the content as the webview above
            AlertDialog.Builder dialog = new AlertDialog.Builder(mActivity);
            dialog.setView(webView);
            dialog.setPositiveButton("Accept", new Dialog.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    // Mark this version as read.
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putBoolean(eulaKey, true);
                    editor.commit();
                    dialogInterface.dismiss();
                }
            });
            dialog.setNegativeButton(android.R.string.cancel, new Dialog.OnClickListener() {

            	@Override
            	public void onClick(DialogInterface dialog, int which) {
            		// Close the activity as they have declined the EULA
            		mActivity.finish();
        		} 
                    
            });
            dialog.show();
        } 
    }
	
}
