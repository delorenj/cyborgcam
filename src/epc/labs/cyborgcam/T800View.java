package epc.labs.cyborgcam;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

public class T800View extends View {
  public static String TAG = "CyborgCam";
  protected Rect mCursor;
  protected Paint mModuleText;
  Context mContext;

  public T800View(Context context, AttributeSet attrs) {
    super(context, attrs);
    Log.e(TAG, "Instantiate T800View");
    mCursor = new Rect(0,0,5,8);
    mCursor.offsetTo(40, 40);    
    mModuleText = new Paint();
    mModuleText.setTypeface(Typeface.create(Typeface.MONOSPACE, Typeface.BOLD));
    mModuleText.setTextSize(12);
    mModuleText.setColor(getResources().getColor(R.color.text));
  }

  @Override
  protected void onDraw(Canvas c) {
    drawModuleCriteria(c);
  }

  private void drawModuleCriteria(Canvas c) {
    c.drawText("CRITERIA:", 20, 40, this.mModuleText);
    c.drawText("*******", 20, 55, this.mModuleText);
    c.drawText("380 VEHI 65707", 20, 70, this.mModuleText);
    c.drawText("390 SIZE 35665", 20, 85, this.mModuleText);
  }
}