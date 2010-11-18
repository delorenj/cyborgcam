/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package epc.labs.cyborgcam;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.ImageView;

/**
 *
 * @author delorenj
 */
public class CyCam extends Activity {
  private static String TAG = "CyborgCam";
  private CamView mCV;
  private ImageView mFilter;
  private T800View mOverlay;
  /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle icicle) {
        Log.e(TAG, "Instantiate CyCam");
        super.onCreate(icicle);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main);
        mCV = (CamView)findViewById(R.id.cam_surface);
        mOverlay = (T800View)this.findViewById(R.id.overlay);
        mFilter = (ImageView)this.findViewById(R.id.colorfilter);
    }
}
