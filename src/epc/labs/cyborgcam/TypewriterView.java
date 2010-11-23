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
  public static String TAG = "CyborgCam";
  protected Context mContext;
  public static final int CHAR_WIDTH = 8;
  public static final int CHAR_HEIGHT = 15;
  public static final int HPAD = 20;
  public static final int VPAD = 0;
  protected Paint mTextPaint;

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
      TextView tv = new TextView(mContext);
      tv.setTypeface(Typeface.create(Typeface.MONOSPACE, Typeface.BOLD));
      tv.setTextSize(12);
      tv.setText(""+t.charAt(i));
      this.addView(tv);
    }
  }

  @Override
  protected void onLayout(boolean changed, int l, int t, int r, int b) {
	int numChars = this.getChildCount();
	Log.i(TAG, "Number of Typewrite Chars: "+numChars);
	for(int i=0; i<numChars; i++) {
		View c = this.getChildAt(i);
		c.layout(CHAR_WIDTH*(i+1)+HPAD, VPAD, CHAR_WIDTH*2*(i+1)+HPAD, CHAR_HEIGHT+VPAD);
	}
  } 
}