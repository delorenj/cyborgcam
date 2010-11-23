package epc.labs.cyborgcam;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TableLayout;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

public class ModCriteria extends TableLayout {
  public static String TAG = "CyborgCam";
  protected Context mContext;
  ArrayList<TextView> chars;

  public ModCriteria(Context context, AttributeSet attrs) {
    super(context, attrs);
    mContext = context;
    this.setShrinkAllColumns(true);
    Log.i(TAG, "Instantiated Module: Criteria");
  }

  public void setText(String t) {
    chars = new ArrayList<TextView>(t.length());
    for(int i=0; i<t.length(); i++) {
      TextView tv = new TextView(mContext);
      tv.setText(""+t.charAt(i));
      chars.add(tv);
      this.addView(tv);
    }
  }
  
  @Override
  protected void onLayout(boolean changed, int l, int t, int r, int b) {
	  Log.i(TAG, "onLayout Called *********************");
  }
}