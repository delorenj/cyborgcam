package epc.labs.cyborgcam;

import android.content.Context;
import android.hardware.Camera;
import android.hardware.Camera.Size;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import java.io.IOException;
import java.util.List;

class CamView extends SurfaceView implements SurfaceHolder.Callback {
  SurfaceHolder mHolder;
  Camera mCamera;
  private static String TAG="CyborgCam";

  public CamView(Context context) {
    super(context);
    // Install a SurfaceHolder.Callback so we get notified when the
    // underlying surface is created and destroyed.
    Log.i(TAG, "Instantiate CamView");
    mHolder = getHolder();
    mHolder.addCallback(this);
    mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
  }

  public CamView(Context context, AttributeSet attrs) {
    super(context);
    // Install a SurfaceHolder.Callback so we get notified when the
    // underlying surface is created and destroyed.
    Log.i(TAG, "Instantiate CamView");
    mHolder = getHolder();
    mHolder.addCallback(this);
    mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
  }

  public CamView(Context context, AttributeSet attrs, int defStyle) {
    super(context);
    // Install a SurfaceHolder.Callback so we get notified when the
    // underlying surface is created and destroyed.
    Log.i(TAG, "Instantiate CamView");
    mHolder = getHolder();
    mHolder.addCallback(this);
    mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
  }

  public void surfaceCreated(SurfaceHolder holder) {
    // The Surface has been created, acquire the camera and tell it where
    // to draw.
    Log.i(TAG, "surfaceCreated");
    mCamera = Camera.open();
    try {
       mCamera.setPreviewDisplay(holder);
    } catch (IOException exception) {
        mCamera.release();
        mCamera = null;
        // TODO: add more exception handling logic here
    }
  }

  public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
    // Now that the size is known, set up the camera parameters and begin
    // the preview.
    Log.i(TAG, "surfaceChanged");
    Camera.Parameters parameters = mCamera.getParameters();
    List<String> colorEffects = parameters.getSupportedColorEffects();
    for (String colorEffect : colorEffects) {
      if(colorEffect == null ? "EFFECT_SOLARIZE" == null : colorEffect.equals("EFFECT_SOLARIZE")){
        parameters.setColorEffect(colorEffect);
      }
    }

    List<Size> sizes = parameters.getSupportedPreviewSizes();
    Size optimalSize = getOptimalPreviewSize(sizes, w, h);
    parameters.setPreviewSize(optimalSize.width, optimalSize.height);

    mCamera.setParameters(parameters);
    mCamera.startPreview();
  }

  public void surfaceDestroyed(SurfaceHolder holder) {
    // Surface will be destroyed when we return, so stop the preview.
    // Because the CameraDevice object is not a shared resource, it's very
    // important to release it when the activity is paused.
    Log.i(TAG, "surfaceDestroyed");
    mCamera.stopPreview();
    mCamera.release();
    mCamera = null;
  }

  private Size getOptimalPreviewSize(List<Size> sizes, int w, int h) {
    final double ASPECT_TOLERANCE = 0.05;
    double targetRatio = (double) w / h;
    Log.i(TAG, "getOptimalPreviewSize");
    if (sizes == null) return null;

    Size optimalSize = null;
    double minDiff = Double.MAX_VALUE;

    int targetHeight = h;

    // Try to find an size match aspect ratio and size
    for (Size size : sizes) {
        double ratio = (double) size.width / size.height;
        if (Math.abs(ratio - targetRatio) > ASPECT_TOLERANCE) continue;
        if (Math.abs(size.height - targetHeight) < minDiff) {
            optimalSize = size;
            minDiff = Math.abs(size.height - targetHeight);
        }
    }

    // Cannot find the one match the aspect ratio, ignore the requirement
    if (optimalSize == null) {
        minDiff = Double.MAX_VALUE;
        for (Size size : sizes) {
            if (Math.abs(size.height - targetHeight) < minDiff) {
                optimalSize = size;
                minDiff = Math.abs(size.height - targetHeight);
            }
        }
    }
    return optimalSize;
  }
}