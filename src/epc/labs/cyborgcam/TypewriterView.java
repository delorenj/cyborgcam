package epc.labs.cyborgcam;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LayoutAnimationController;
import android.view.animation.Animation.AnimationListener;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TypewriterView extends ViewGroup {
	public class TypewriterElement extends TextView {
		public int mRow;
		
		public TypewriterElement(Context context) {
			super(context);
		}
		
		public void setRow(int r) {
			mRow = r;
		}
		
		public int getRow() {
			return mRow;
		}		
	};
	
  public static String TAG = "CyborgCam";
  protected Context mContext;
  public static final int CHAR_WIDTH = 8;
  public static final int CHAR_HEIGHT = 15;
  public static final int HPAD = 20;
  public static final int VPAD = 0;
  protected int numRows = 0;
  protected LayoutAnimationController animController;
  protected Animation.AnimationListener animListener;
  protected MediaPlayer mp;


  public TypewriterView(Context context) {
	    super(context);
	    mContext = context;
	    Log.i(TAG, "Instantiated Module: Criteria");
  }

  public TypewriterView(Context context, AttributeSet attrs) {
    super(context, attrs);
    mContext = context;
    Log.i(TAG, "Instantiated Module: Criteria");
  }

  public void setText(String t) {
    for(int i=0; i<t.length(); i++) {
      TypewriterElement tv = new TypewriterElement(mContext);
      tv.setTypeface(Typeface.create(Typeface.MONOSPACE, Typeface.BOLD));
      tv.setTextSize(12);
      tv.setText(""+t.charAt(i));
      tv.setRow(0);
      this.addView(tv);      
    }
  }

  public void addLine(String t) {
	numRows++;
    for(int i=0; i<t.length(); i++) {
      TypewriterElement tv = new TypewriterElement(mContext);
      tv.setTypeface(Typeface.create(Typeface.MONOSPACE, Typeface.BOLD));
      tv.setTextSize(12);
      tv.setText(""+t.charAt(i));
      tv.setRow(this.numRows);
      this.addView(tv);
    }
  }
  
  @Override
  protected void onLayout(boolean changed, int l, int t, int r, int b) {
	if(!changed) return;
	int numChars = this.getChildCount();
	if(numChars == 0) return;
	if(mp != null) {
		if(mp.isPlaying()) mp.stop();
		mp.release();
	}
	mp = MediaPlayer.create(mContext, R.raw.click);
	try {
		mp.prepare();
	} catch (IllegalStateException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	final Animation alphaAnim = new AlphaAnimation(0.0f, 1.0f);
	alphaAnim.setDuration(100);
	alphaAnim.setFillAfter(true);
	animController = new LayoutAnimationController(alphaAnim);
	animListener = new Animation.AnimationListener() {
		
		@Override
		public void onAnimationStart(Animation animation) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onAnimationRepeat(Animation animation) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onAnimationEnd(Animation animation) {
			if(mp.isPlaying()){
				mp.seekTo(0);
			}
			else {
				mp.start();
			}
		}
	};
	alphaAnim.setAnimationListener(animListener);
	int currChar = 0;
	Log.i(TAG, "Number of Typewrite Rows: "+ numRows);
	for(int currRow=1; currRow<=numRows; currRow++) {
	  Log.i(TAG, "Processing Row "+currRow+" of "+numRows);
	  int vertical_displacement = currRow*CHAR_HEIGHT+VPAD;
	  int horizontal_displacement = 1;
	  boolean doneRow = false;
	  while(!doneRow && (currChar < numChars)) {
	    TypewriterElement c = (TypewriterElement)this.getChildAt(currChar);
	    if(c.getRow() != currRow) {
	      doneRow = true;
	      continue;
	    }
	    c.layout(CHAR_WIDTH*horizontal_displacement+HPAD, 	//left 
	    		 vertical_displacement, 					//top
	    		 CHAR_WIDTH*2*horizontal_displacement+HPAD, //right
	    		 vertical_displacement+CHAR_HEIGHT);		//bottom
	    currChar++;
	    horizontal_displacement++;
	  }
	}

	this.setLayoutAnimation(animController);
	animController.start();
  } 
}