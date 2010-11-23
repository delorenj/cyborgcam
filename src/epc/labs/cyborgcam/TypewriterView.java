package epc.labs.cyborgcam;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
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
  protected Paint mTextPaint;
  protected int numRows = 0;

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
	int numChars = this.getChildCount();
	Log.i(TAG, "Number of Typewrite Chars: "+numChars);
	for(int i=0; i<numChars; i++) {
		TypewriterElement c = (TypewriterElement)this.getChildAt(i);
		c.layout(CHAR_WIDTH*(i+1)+HPAD, c.getRow()*CHAR_HEIGHT+VPAD, CHAR_WIDTH*2*(i+1)+HPAD, c.getRow()*CHAR_HEIGHT*2+VPAD);
	}
  } 
}