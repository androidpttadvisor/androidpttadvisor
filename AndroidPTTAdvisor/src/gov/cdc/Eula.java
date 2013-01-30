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

         // Show the Eula
            String title = mActivity.getString(R.string.app_name) + " v" + versionInfo.versionName;
            
            //Includes the updates as well so users know what changed.
            String message = mActivity.getString(R.string.updates) + "\n\n" + mActivity.getString(R.string.eula);

            AlertDialog.Builder builder = new AlertDialog.Builder(mActivity)
                    .setTitle(title)
                    .setMessage(message)
                    .setPositiveButton(android.R.string.ok, new Dialog.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            // Mark this version as read.
                            SharedPreferences.Editor editor = prefs.edit();
                            editor.putBoolean(eulaKey, true);
                            editor.commit();
                            dialogInterface.dismiss();
                        }
                    })
                    .setNegativeButton(android.R.string.cancel, new Dialog.OnClickListener() {

@Override
public void onClick(DialogInterface dialog, int which) {
// Close the activity as they have declined the EULA
mActivity.finish();
}
                    
                    });
            builder.create().show();
        }
    }
	
}
