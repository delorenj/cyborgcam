/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package epc.labs.cyborgcam;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TextView;

/**
 *
 * @author delorenj
 */
public class CyCam extends Activity {
  private static String TAG = "CyborgCam";
  private CamView mCV;
  private ImageView mFilter;
  private T800View mOverlay;
  private TypewriterView mCriteria;

  /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle icicle) {
        Log.i(TAG, "Instantiate CyCam");
        super.onCreate(icicle);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main);
        mCV = (CamView)findViewById(R.id.cam_surface);
        mFilter = (ImageView)this.findViewById(R.id.colorfilter);
        mCriteria = (TypewriterView)this.findViewById(R.id.mod_criteria);
        mCriteria.addLine("CRITERIA:");
        mCriteria.addLine("*******");

//        criteria.setText("This is ass");
//        mOverlay = (T800View)this.findViewById(R.id.overlay);
//        final Animation in = new AlphaAnimation(0.0f, 1.0f);
//        in.setDuration(250);
//        in.setFillAfter(true);
//        in.setStartOffset(3000);
//        mOverlay.clearAnimation();
//        mOverlay.startAnimation(in);
    }
}
