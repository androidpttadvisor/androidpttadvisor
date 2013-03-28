package gov.cdc.pttadvisor;

import java.util.ArrayList;
import gov.cdc.pttadvisor.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View.OnClickListener;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.method.ScrollingMovementMethod;

public class MainView extends Activity {

    int position = 0;
    int farthestPositionReached = 0;
    public static PTTController controller;
    
    //Make a public static variable for the controller.history
    public static ArrayList<PTTHistoryItem> mHistory;
    
    private ImageButton navButtonPrev; 
    private ImageButton navButtonNext; 
    private ImageButton navButtonLast; 
    private ImageButton navButtonRestart; 
    private ImageButton navButtonHistory;
    private Button footnotesButton;
    
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	
        super.onCreate(savedInstanceState);
        
        /**
         * Display the EULA.  Eula.show() will only show the EULA if
         * the user has not previously agreed to the current version's EULA.
         * the false here indicates not to force it-- only show if the version
         * has been updated or they haven't already agreed to it.
         */
        new Eula(this).show(false);
        
        new JsonUpdaterTask(this).execute();
        // TODO: Answer me this: Are we creating an entirely new controller here? Or wait, is this our only controller?
        controller = new PTTController(this.getApplicationContext());
        
        //Make a new member variable for the history. We'll use this to update it. Or maybe not needed.
        mHistory = controller.history;
        
        // Hide the title bar
        //requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.main);
                
        PTTNode currentNode = controller.currentNode;
        
        //Get image path and put image into node header image
        ImageView headerImageView = new ImageView(this);
        headerImageView = (ImageView)findViewById(R.id.nodeHeaderImage);
        String imageString = "drawable/" + currentNode.getHeaderImage();
        //Log.d("IMAGESTRING", imageString);
        int imageResource = getResources().getIdentifier(imageString,null,getPackageName());
        Drawable image = getResources().getDrawable(imageResource);
        headerImageView.setImageDrawable(image);
        
        
        //Get question text and put it on the screen
        TextView tv = new TextView(this);
    	tv = (TextView)findViewById(R.id.questionTextView);
    	// set the TextView as scrollable, just in case it's too big to fit
    	tv.setMovementMethod(new ScrollingMovementMethod()); 
    	tv.setText(currentNode.getQuestion());
    	
        // update the "Step X" line at the bottom of the screen
        TextView stepNumberLabel = (TextView) findViewById(R.id.stepNumberLabel);
        stepNumberLabel.setText("Step " + (position + 1));
    	
    	//Get answers and put them on the buttons.
    	updateButtons();


        navButtonPrev = (ImageButton)findViewById(R.id.navButtonPrev);
        navButtonNext = (ImageButton)findViewById(R.id.navButtonNext);
        navButtonLast = (ImageButton)findViewById(R.id.navButtonLast);
        navButtonRestart = (ImageButton)findViewById(R.id.navButtonRestart);
        navButtonHistory = (ImageButton)findViewById(R.id.navButtonHistory);




        navButtonPrev.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
            	Log.d("NAV","navButtonPrev");
            	navigateBackToPreviousNode();

            }
        });

        navButtonNext.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
            	Log.d("NAV","navButtonNext");
            	navigateForwardToNode();
            }
        });

        navButtonLast.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
            	Log.d("NAV","navButtonLast");
            	//I'm putting the logic all right here. I don't see (yet) any reason to put it in function
            	
            	int farthestNodeId = mHistory.get(mHistory.size()-1).getAnswerChosen().getNodeId();
            	Log.d("farthestNodeId",Integer.toString(farthestNodeId));
            	
            	position = farthestPositionReached;
            	controller.setCurrentNode(farthestNodeId);
            	navigateToAnotherNode(farthestNodeId);
            	
            }
        });

        
        final Context context = this;
        
        navButtonHistory.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

            	Log.d("NAV","navButtonHistory");
                controller.logHistoryItems();
                Intent historyViewIntent = new Intent(context, HistoryView.class);
        		startActivityForResult(historyViewIntent, 1);
                //startActivityForResult(historyViewIntent,1);
            }
        });
        
        // TODO: maybe turn this into an intializeNavButtons() method??
        disableAllNavButtons();
        updateNavButtons();

        Button footnotesButton = (Button) findViewById(R.id.footnotesButton);
        footnotesButton.setOnClickListener(new View.OnClickListener() {
        	public void onClick(View view) {
        		Intent intent = new Intent(context, FootnotesView.class);
        		startActivity(intent);
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


    public void navigateToAnotherNode(int nodeId) {
    	
    	if (position > farthestPositionReached) {
    		farthestPositionReached = position;
    	}
    	
    	
    	
        //Get question text and put it on the screen
        TextView tv = (TextView)findViewById(R.id.questionTextView);
        tv.setText(controller.currentNode.getQuestion());
        
        //Get image path and put image into node header image
        ImageView headerImageView = (ImageView)findViewById(R.id.nodeHeaderImage);
        
        String imageString = "drawable/" + controller.currentNode.getHeaderImage();
        //Log.d("IMAGESTRING", imageString);
        int imageResource = getResources().getIdentifier(imageString,null,getPackageName());
        Drawable image = getResources().getDrawable(imageResource);
        headerImageView.setImageDrawable(image);
        
        // update the "Step X" line at the bottom of the screen
        TextView stepNumberLabel = (TextView) findViewById(R.id.stepNumberLabel);
        stepNumberLabel.setText("Step " + (position + 1));

        updateButtons();
        //enableAllNavButtons();
        updateNavButtons();
        
        
        Log.d("navToNutherNode,CurrNodeId",Integer.toString(controller.currentNode.getId()));
        
    }

    

    public void disableAllNavButtons() {
    	navButtonPrev.setEnabled(false);
    	navButtonPrev.setImageResource(R.drawable.nav_button_back_disabled);
    	navButtonNext.setEnabled(false);
    	navButtonNext.setImageResource(R.drawable.nav_button_next_disabled);
    	navButtonLast.setEnabled(false);
    	navButtonLast.setImageResource(R.drawable.nav_button_back_to_last_disabled);
    	navButtonRestart.setEnabled(false);
    	navButtonRestart.setImageResource(R.drawable.nav_button_restart_disabled);
    	navButtonHistory.setEnabled(false);
    	navButtonHistory.setImageResource(R.drawable.nav_button_review_disabled);
    }
    
    public void enableAllNavButtons() {
    	navButtonPrev.setEnabled(true);
    	navButtonPrev.setImageResource(R.drawable.nav_button_back);
    	navButtonNext.setEnabled(true);
    	navButtonNext.setImageResource(R.drawable.nav_button_next);
    	navButtonLast.setEnabled(true);
    	navButtonLast.setImageResource(R.drawable.nav_button_back_to_last);
    	navButtonRestart.setEnabled(true);
    	navButtonRestart.setImageResource(R.drawable.nav_button_restart);
    	navButtonHistory.setEnabled(true);
    	navButtonHistory.setImageResource(R.drawable.nav_button_review);
    }
    
    
    public void updateNavButtons() {
    	Log.d("updateNavButtons","position: " + position + ", farthest: " + farthestPositionReached);
    	
    	if (position > 0) {
    		navButtonPrev.setEnabled(true);
    		navButtonPrev.setImageResource(R.drawable.nav_button_back);
    		navButtonRestart.setEnabled(true);
        	navButtonRestart.setImageResource(R.drawable.nav_button_restart);
        	navButtonHistory.setEnabled(true);
        	navButtonHistory.setImageResource(R.drawable.nav_button_review);
    	}
    	
    	//this is wrong. what if user goes all the way back to beginning? still need active buttons
    	if (farthestPositionReached > 0) {
    		navButtonHistory.setEnabled(true);
        	navButtonHistory.setImageResource(R.drawable.nav_button_review);
    	}
    	
    	
    	
    	if (position < farthestPositionReached) {
    		//activate the forward buttons
    		navButtonNext.setEnabled(true);
        	navButtonNext.setImageResource(R.drawable.nav_button_next);
    		navButtonLast.setEnabled(true);
        	navButtonLast.setImageResource(R.drawable.nav_button_back_to_last);
    	} else if (position == farthestPositionReached) {
    		navButtonNext.setEnabled(false);
        	navButtonNext.setImageResource(R.drawable.nav_button_next_disabled);
        	navButtonLast.setEnabled(false);
        	navButtonLast.setImageResource(R.drawable.nav_button_back_to_last_disabled);
    	}
    	
    	if (position == 0) {
    		navButtonPrev.setEnabled(false);
        	navButtonPrev.setImageResource(R.drawable.nav_button_back_disabled);
        	
    	}
    }
    
    
    
    public void navigateBackToPreviousNode() {
    	Log.d("Position before going back",Integer.toString(position));
    	Log.d("mHistory size:",Integer.toString(mHistory.size()));
    	
    	int prevNodeId = mHistory.get(position-1).getNode().getId();
 
    	Log.d("Question for prev question:",controller.getQuestionForNodeNumber(prevNodeId));
    	Log.d("Navigating back to node:",Integer.toString(prevNodeId));
    	
    	
    	position--;
    	controller.setCurrentNode(prevNodeId);
    	navigateToAnotherNode(prevNodeId);
    }
    
    
    public void navigateForwardToNode() {
    	Log.d("position",Integer.toString(position));
    	Log.d("mHistory size:",Integer.toString(mHistory.size()));
    	
    	int nextNodeId = mHistory.get(position).getAnswerChosen().getNodeId();
    	
    	Log.d("Question for next question:",controller.getQuestionForNodeNumber(nextNodeId));
    	Log.d("Navigating forward to node:",Integer.toString(mHistory.get(position).getAnswerChosen().getNodeId()));
    	
    	
    	position++;
    	controller.setCurrentNode(nextNodeId);
    	navigateToAnotherNode(nextNodeId);
    	
    }
    

    public void restart(View view) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setIcon(R.drawable.nav_button_restart);
        builder.setTitle("Restart Patient Evaluation");
        builder.setMessage("Are you sure you want to restart the patient evaluation?");
        builder.setInverseBackgroundForced(true);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getBaseContext(),
                        "Restarting", Toast.LENGTH_SHORT).show();
                position=0;
                farthestPositionReached = 0;
                controller.setCurrentNode(0);
                mHistory.clear();
                navigateToAnotherNode(0);
                disableAllNavButtons();

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
    
    
    public void restartDone(View view) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle("Patient Evaluation Complete");
        builder.setMessage("Would you like to begin a new patient evaluation?");
        builder.setInverseBackgroundForced(true);
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                position=0;
                farthestPositionReached = 0;
                controller.setCurrentNode(0);
                mHistory.clear();
                navigateToAnotherNode(0);
                disableAllNavButtons();

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
        ImageButton doneButton = (ImageButton)findViewById(R.id.doneButton);
    	Button b0 = (Button)findViewById(R.id.answerButton0);
    	Button b1 = (Button)findViewById(R.id.answerButton1);
    	//Log.d("DEBUG", "About to make buttons");
    	//Log.d("DEBUG", "Answers size is " + answers.size());
    	switch (answers.size()) {
    	case 0:
    		doneButton.setVisibility(View.VISIBLE);
    		b0.setVisibility(View.INVISIBLE);
    		b1.setVisibility(View.INVISIBLE);
    		break;
    	case 1:
    		doneButton.setVisibility(View.GONE);
    		b0.setVisibility(View.VISIBLE);
    		b1.setVisibility(View.INVISIBLE);
    		break;
    	default:
    		doneButton.setVisibility(View.GONE);
    		b0.setVisibility(View.VISIBLE);
    		b1.setVisibility(View.VISIBLE);
    	}
    	
    	
    	
    	if (answers.size() > 0) {
    		String s = answers.get(0).answer;
    		
    		b0.setText(s);
			final int answer0NodeId = answers.get(0).nodeId;
			//Log.d("Making button", "b0, '" + s + "', points to node " + answer0Node + ".  Current node is " + controller.currentNode.getId());
			b0.setOnClickListener(new View.OnClickListener() {
				public void onClick(View view) {
                    PTTNode n = controller.getCurrentNode();
                    didSelectAnswer(n,answer0NodeId,0);
                    //controller.storeHistoryItem(n, n.getAnswers().get(0));
                    //controller.setCurrentNode(answer0NodeId);
                    //position++;
                    //navigateToAnotherNode(answer0NodeId);
                   }
            });
    	} 
    	if (answers.size() == 2) {
            String s1 = answers.get(1).answer;
            
            b1.setText(s1);
            final int answer1NodeId = answers.get(1).nodeId;
            //Log.d("Making button", "b1, " + s1 + ", points to node " + answer1Node + ".  Current node is " + controller.currentNode.getId());
            b1.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    PTTNode n = controller.getCurrentNode();
                    didSelectAnswer(n,answer1NodeId,1);
                    //controller.storeHistoryItem(n, n.getAnswers().get(1));
                    //controller.setCurrentNode(answer1NodeId);
                    //position++;
                    //navigateToAnotherNode(answer1NodeId);
                }
            });
    	}
    	
    	footnotesButton = (Button)findViewById(R.id.footnotesButton);
    	if (controller.currentNode.getFootnotes().size() > 0) {
    		footnotesButton.setEnabled(true);
    		footnotesButton.setTextAppearance(this, R.style.whiteSmall);
    	} else {
    		footnotesButton.setEnabled(false);
    		footnotesButton.setTextAppearance(this, R.style.graySmall);
    	}
    	adjustButtonColorsBasedOnHistory(answers);
    }
    
    /*
     * This method will handle all answer actions. This is to help modularize the answer selection process, especially when previously-answered nodes are answered
     */
    public void didSelectAnswer(PTTNode currentNode, int answerNodeId, int answerSelected) {
    	
    	final PTTNode mCurrentNode = currentNode;
    	final int mAnswerNodeId = answerNodeId;
    	final int mAnswerSelected = answerSelected;
    	
    	// Set default option for b
    	Button b = (Button)findViewById(R.id.answerButton0);
    	boolean buttonIsSelected = false;
    	
    	// Figure out which button was selected
    	if (answerSelected == 0) {
    		b = (Button)findViewById(R.id.answerButton0);
    	}
    	else if (answerSelected == 1) {
    		b = (Button)findViewById(R.id.answerButton1);
    		
    	}
    	
    	// Get that button's state (selected or not)
    	buttonIsSelected = b.isSelected();
    	
    	
    	
    	// If we are selecting an answer that has been answered before
    	if ( (position != farthestPositionReached) && !buttonIsSelected) {
    		// derive step numbers (position is zero-indexed)
    		String curStep = Integer.toString(position + 1);
    		String nextStep = Integer.toString(position + 2);
    		
    		// Display dialog to let user know [s]he's changing history
    		AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setCancelable(true);
            builder.setIcon(R.drawable.nav_button_restart);
            builder.setTitle("Change Decision Warning");
            builder.setMessage("You are about to change your answer to Step " + curStep + ".  " +
            		"Therefore any previous responses past Step " + curStep + " will be " +
            		"discarded and you will be presented with new questions and " +
            		"information at Step " + nextStep + ". Would you like to continue?");
            		          		
            builder.setInverseBackgroundForced(true);
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                	// Remove all items past the question being answered here, and reset the farthest position
            		controller.truncateHistory(position);
            		farthestPositionReached = position;
            		

                	//Store the new answer in the history, advance the current node and position and navigate to the new node
                	controller.storeHistoryItem(mCurrentNode, mCurrentNode.getAnswers().get(mAnswerSelected));
                    controller.setCurrentNode(mAnswerNodeId);
                    position++;
                    navigateToAnotherNode(mAnswerNodeId);
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
    	//No worries about changing previously-answered questions
    	else {
        	// Store the new answer in the history, advance the current node and position and navigate to the new node
        	// If the button was not the selected one, then we need to store the answer in the history 
    		if (!buttonIsSelected) {
        		controller.storeHistoryItem(currentNode, currentNode.getAnswers().get(answerSelected));        		
        	}
            controller.setCurrentNode(answerNodeId);
            position++;
            navigateToAnotherNode(answerNodeId);    		
    	}
    	
    }
    
    
    
    public void adjustButtonColorsBasedOnHistory(ArrayList<PTTAnswer> answers) {
    	// set buttons appearance if it has been selected
    	Button b0 = (Button)findViewById(R.id.answerButton0);
    	Button b1 = (Button)findViewById(R.id.answerButton1);
    	b0.setBackgroundResource(R.drawable.button_style);
    	b1.setBackgroundResource(R.drawable.button_style);
    	b0.setSelected(false);
    	b1.setSelected(false);
    	
    	
    	if (position < farthestPositionReached) {
    		Log.d("BUTTON DECOR","we should decorate a button");
    		Log.d("answer of button to decorate:",mHistory.get(position).getAnswerChosen().getAnswer());
    		switch (answers.size()) {
        	case 0:
        		break;
        	case 1:
        		if (answers.get(0).getNodeId() == mHistory.get(position).getAnswerChosen().getNodeId()) {
    				//b0.setText(b0.getText()+ "  --  The Chosen One!");
    				//b0.setBackgroundResource(R.drawable.button_down);
    				b0.setSelected(true);
    			} else {
    				//b0.setBackgroundColor(android.graphics.Color.LTGRAY);
    				//b0.setBackgroundResource(R.drawable.button_regular);
    				b0.setSelected(false);
    			}
        		break;
        	default:
        		if (answers.get(0).getNodeId() == mHistory.get(position).getAnswerChosen().getNodeId()) {
        			//b0.setBackgroundResource(R.drawable.button_down);
        			//b1.setBackgroundResource(R.drawable.button_regular);
        			b0.setSelected(true);
        			b1.setSelected(false);
    			} else if (answers.get(1).getNodeId() == mHistory.get(position).getAnswerChosen().getNodeId()) {
    				//b0.setBackgroundResource(R.drawable.button_regular);
        			//b1.setBackgroundResource(R.drawable.button_down);
    				b0.setSelected(false);
        			b1.setSelected(true);
    			}
        		break;
        	}
    	}
    	else if ( (position == farthestPositionReached) && (position == 0)) {
    		//Account for when the reset button was pressed
    		//b0.setBackgroundResource(R.drawable.button_regular);
    		//b1.setBackgroundResource(R.drawable.button_regular);
    		//b0.setSelected(false);
			//b1.setSelected(false);
    	}
    	
    	
    }
    
    
    public PTTController getController() {
    	return controller;
    }

    
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	Log.d("CheckStartActivity","onActivityResult and resultCode = "+resultCode);
    	// TODO Auto-generated method stub
    	super.onActivityResult(requestCode, resultCode, data);
    	if(resultCode==1){
    		// TODO: there are problems with this if we use this call when tapping the "Current Step/Recommendation" thingie from the History List
    		int positionToNavigateTo = data.getIntExtra("nodeToNavigateTo",0);
    		Log.d("RETURNED. GOTO POS: ",Integer.toString(positionToNavigateTo));
    		Log.d("Quest to goto: ", mHistory.get(positionToNavigateTo).getNode().getQuestion());
    		int nodeIdToGoTo = mHistory.get(positionToNavigateTo).getNode().getId();
    		Log.d("nodeIdToGoTo:",Integer.toString(nodeIdToGoTo));
    		
    		position = positionToNavigateTo;
        	controller.setCurrentNode(nodeIdToGoTo);
    		navigateToAnotherNode(nodeIdToGoTo);
    		
    		
    	}
    	//TODO: probably should implement this for hitting the Curr Step option"
    	if(resultCode==2) {
    		Log.d("HIT CURR STEP","the user just hit the Current Step option on the history view");
    	}
    	else{
    		// This means that the user hit the "Done" button, and thus did not select a question to return to.
    	}
    }

}
