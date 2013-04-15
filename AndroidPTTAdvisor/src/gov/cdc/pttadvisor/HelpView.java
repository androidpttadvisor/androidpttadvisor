package gov.cdc.pttadvisor;

import gov.cdc.pttadvisor.R;
import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * A simple activity to display the help.html page.  All this does is set the webView's content to assets/help.html.
 * @author Will
 *
 */
public class HelpView extends Activity {
	
	// set the the layout's helpView to webView and set its content to assets/help.html.
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_help_view);
		
		String helpText = "<p><b>1. Back:</b> Go back one step.</p>";
		helpText += "<p><b>2. Next:</b> Go forward one step.</p>";
		helpText += "<p><b>3. Go to Last:</b> Go to the last step you were presented, but haven't yet responded to.</p>";
		helpText += "<p><b>4. Restart:</b> Restart a patient evaluation</p>";
        helpText += "<p><b>5. Evalution Review:</b> Presents a screen that lists the steps and responses so far, including the current step.  You may tap a step to edit your response.";
		
		TextView tv = (TextView)findViewById(R.id.helpView);
        
		tv.setText(Html.fromHtml(helpText));
		
		// Registering the "finish" action to the "Done" button
				final Button doneButton = (Button)findViewById(R.id.doneButton);
		        doneButton.setOnClickListener(new View.OnClickListener() {
		            public void onClick(View view) {
		            	finish();
		            }
		        });
	}
	
	
}
