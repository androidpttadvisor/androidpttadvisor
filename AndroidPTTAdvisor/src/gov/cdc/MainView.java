package gov.cdc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import gov.cdc.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.WindowManager;
import android.view.Window;
import android.widget.Toast;
import android.view.View.OnClickListener;
import android.content.Context;
import android.graphics.drawable.Drawable;


public class MainView extends Activity {
	
    /**
     * Called when the activity is first created.
     */

	
	

    // Set up some variables
    private int[] imagesArray = {
            R.drawable.obnoxious1,
            R.drawable.obnoxious2,
            R.drawable.obnoxious3,
            R.drawable.obnoxious4,
            R.drawable.obnoxious5,
    };
    


    int position = 0;
    int farthestPositionReached = 0;

    PTTController controller;
    
    //Make a public static variable for the controller.history
    public static ArrayList<PTTHistoryItem> mHistory;
    
    private String headerImage;
    
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	
        super.onCreate(savedInstanceState);
        
        /**
         * Display the EULA.  Eula.show() will only show the EULA if
         * the user has not previously agreed to the current version's EULA.
         */
        new Eula(this).show();
        
        controller = new PTTController(this.getApplicationContext());
        
        //Make a new member variable for the history. We'll use this to update it. Or maybe not needed.
        mHistory = controller.history;
        
        
        headerImage = controller.getHeaderImageForNodeNumber(0);

        
        //Let's Log out the text of the first node's question, and it's answers' text and node targets
        
        Log.d("QUESTION0",controller.getQuestionForNodeNumber(0));
        
        
        //Test for answers in nodes
        ArrayList<PTTAnswer> answers0 = controller.getAnswersForNodeNumber(0);
        Log.d("NODE0 ANSWER0 TEXT",answers0.get(0).getAnswer());
        Log.d("NODE0 ANSWER0 TARGET",Integer.toString(answers0.get(0).getNodeId()));
        Log.d("NODE0 ANSWER1 TEXT",answers0.get(1).getAnswer());
        Log.d("NODE0 ANSWER1 TARGET",Integer.toString(answers0.get(1).getNodeId()));
        
        //and let's do the same for the second node
        Log.d("QUESTION1",controller.getQuestionForNodeNumber(1));
        
        
        ArrayList<PTTAnswer> answers1 = controller.getAnswersForNodeNumber(1);
        Log.d("NODE1 ANSWER0 TEXT",answers1.get(0).getAnswer());
        Log.d("NODE1 ANSWER0 TARGET",Integer.toString(answers1.get(0).getNodeId()));
        //Log.d("NODE1 ANSWER1 TEXT",answers1.get(1).getAnswer());
        //Log.d("NODE1 ANSWER1 TARGET",Integer.toString(answers1.get(1).getNodeId()));
        

    	
        // Hide the title bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        // These two lines make it full screen. As in, it even hides the top status bar
        // getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        //        WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // end Full-Screen snippet

        setContentView(R.layout.main);
        
        PTTNode currentNode = controller.currentNode;
        
        
        //Get image path and put image into node header image
        ImageView headerImageView = new ImageView(this);
        headerImageView = (ImageView)findViewById(R.id.nodeHeaderImage);
        String headerImagePath = controller.getHeaderImageForNodeNumber(currentNode.getId());
        Log.d("IMAGE", headerImagePath);
        String imageString = "drawable/" + currentNode.getPathToHeaderImage();
        Log.d("IMAGESTRING", imageString);
        int imageResource = getResources().getIdentifier(imageString,null,getPackageName());
        Drawable image = getResources().getDrawable(imageResource);
        headerImageView.setImageDrawable(image);
        
        
        //Get question text and put it on the screen
        TextView tv = new TextView(this);
    	tv = (TextView)findViewById(R.id.questionTextView);
    	tv.setText(currentNode.getQuestion());
    	
    	//Get answers and put them on the buttons.
    	updateButtons();

        final ImageView iv = (ImageView)findViewById(R.id.imageview1);



        ImageButton button1 = (ImageButton)findViewById(R.id.tabBarButton1);
        ImageButton button2 = (ImageButton)findViewById(R.id.tabBarButton2);
        ImageButton button3 = (ImageButton)findViewById(R.id.tabBarButton3);
        ImageButton button4 = (ImageButton)findViewById(R.id.tabBarButton4);
        ImageButton button5 = (ImageButton)findViewById(R.id.tabBarButton5);




        button1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                navigateToAnotherImage("previous");
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                navigateToAnotherImage("next");
                
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                navigateToAnotherImage("farthest");
            }
        });

        
        final Context context = this;
        
        button5.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                controller.logHistoryItems();
                Intent historyViewIntent = new Intent(context, HistoryView.class);
        		startActivity(historyViewIntent);
            }
        });


        /**
         * Assign an action to the infoButton to display the info page
         */
        ImageButton infoButton = (ImageButton) findViewById(R.id.infoButton);
        infoButton.setOnClickListener(new OnClickListener() {
        	public void onClick(View arg0) {
        		Intent intent = new Intent(context, InfoView.class);
        		startActivity(intent);
        	}
        });
        
        /**
         * Assign an action to the helpButton to display the help page
         */
        ImageButton helpButton = (ImageButton) findViewById(R.id.helpButton);
        helpButton.setOnClickListener(new OnClickListener() {
        	public void onClick(View arg0) {
        		Intent intent = new Intent(context, HelpView.class);
        		startActivity(intent);
        	}
        });
        
    }


    public void navigateToAnotherImage(String whatImage) {
    	Log.d("navigateToAnotherImage", "whatImage: " + whatImage + "position: " + position);
        if ((whatImage.equals("next")) && position<4) {
            position++;
            final ImageView iv = (ImageView)findViewById(R.id.imageview1);
            iv.setImageResource(imagesArray[position]);
        }
        else if ((whatImage.equals("previous")) && position>0) {
            position--;
            final ImageView iv = (ImageView)findViewById(R.id.imageview1);
            iv.setImageResource(imagesArray[position]);
        }
        else if (whatImage.equals("restart")) {
            position=0;
            farthestPositionReached = 0;
            final ImageView iv = (ImageView)findViewById(R.id.imageview1);
            iv.setImageResource(imagesArray[position]);
        }
        else if (whatImage.equals("farthest")) {
            position=farthestPositionReached;
            final ImageView iv = (ImageView)findViewById(R.id.imageview1);
            iv.setImageResource(imagesArray[position]);
        }

        if (farthestPositionReached < position) {
            farthestPositionReached = position;
        }
    }


    public void navigateToAnotherNode(int nodeId) {
    	Log.d("CALLING navigateToAnotherNode","navigateToAnotherNode");
        //Get question text and put it on the screen
        TextView tv = (TextView)findViewById(R.id.questionTextView);
        tv.setText(controller.currentNode.getQuestion());
        
      //Get image path and put image into node header image
        ImageView headerImageView = (ImageView)findViewById(R.id.nodeHeaderImage);
        
        String headerImagePath = controller.getHeaderImageForNodeNumber(nodeId);
        Log.d("IMAGE", headerImagePath);
        String imageString = "drawable/" + controller.currentNode.getPathToHeaderImage();
        Log.d("IMAGESTRING", imageString);
        int imageResource = getResources().getIdentifier(imageString,null,getPackageName());
        Drawable image = getResources().getDrawable(imageResource);
        headerImageView.setImageDrawable(image);

        updateButtons();
    }




    public void restart(View view) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setIcon(R.drawable.redo);
        builder.setTitle("Do you really want to restart?");
        builder.setInverseBackgroundForced(true);
        builder.setPositiveButton("Yes, restart", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getBaseContext(),
                        "Restarting", Toast.LENGTH_SHORT).show();
                position=0;
                farthestPositionReached = 0;
                final ImageView iv = (ImageView)findViewById(R.id.imageview1);
                iv.setImageResource(imagesArray[position]);
                controller.setCurrentNode(0);
                navigateToAnotherNode(0);

            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    
    /**
     * Update the number, strings, and actions of the buttons to those
     * specified by the current node.
     */
    private void updateButtons() {
        ArrayList<PTTAnswer> answers = controller.currentNode.getAnswers();
    	Button b0 = (Button)findViewById(R.id.answerButton0);
    	Button b1 = (Button)findViewById(R.id.answerButton1);
    	Log.d("DEBUG", "About to make buttons");
    	Log.d("DEBUG", "Answers size is " + answers.size());
    	switch (answers.size()) {
    	case 0:
    		b0.setVisibility(View.GONE);
    		b1.setVisibility(View.GONE);
    		break;
    	case 1:
    		b0.setVisibility(View.VISIBLE);
    		b1.setVisibility(View.GONE);
    		break;
    	default:
    		b0.setVisibility(View.VISIBLE);
    		b1.setVisibility(View.VISIBLE);
    	}
    	
    	if (answers.size() > 0) {
    		String s = answers.get(0).answer;
    		b0.setText(s);
			final int answer0Node = answers.get(0).nodeId;
			Log.d("Making button", "b0, '" + s + "', points to node " + answer0Node + ".  Current node is " + controller.currentNode.getId());
			b0.setOnClickListener(new View.OnClickListener() {
				public void onClick(View view) {
                    Log.d("GOTO Node:",Integer.toString(answer0Node));
                    
                    //get the current node and store the history item based on it
                    Log.d("HISTLOG", "About to Log something");
                    PTTNode n = controller.getCurrentNode();
                    controller.storeHistoryItem(n, n.getAnswers().get(0));
                    controller.setCurrentNode(answer0Node);
                    navigateToAnotherNode(answer0Node);
                   }
            });
    	} 
    	if (answers.size() == 2) {
            String s1 = answers.get(1).answer;
            b1.setText(s1);
            final int answer1Node = answers.get(1).nodeId;
            Log.d("Making button", "b1, " + s1 + ", points to node " + answer1Node + ".  Current node is " + controller.currentNode.getId());
            b1.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    Log.d("GOTO Node:",Integer.toString(answer1Node));
                    
                    //get the current node and store the history item based on it
                    Log.d("HISTLOG", "About to Log something");
                    PTTNode n = controller.getCurrentNode();
                    controller.storeHistoryItem(n, n.getAnswers().get(1));
                    controller.setCurrentNode(answer1Node);
                    navigateToAnotherNode(answer1Node);
                }
            });
    	}
    }


}
