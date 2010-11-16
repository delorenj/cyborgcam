package epc.labs.cyborgcam;


import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

class T600View extends SurfaceView implements SurfaceHolder.Callback {
  class T600Thread extends Thread {
    /*
     * State-tracking constants
     */
    public static final int STATE_READY = 1;
    public static final int STATE_RUNNING = 2;
    public static final int STATE_PAUSE = 3;

    /** Message handler used by thread to interact with TextView */
    private Handler mHandler;

    /** Paint to draw the lines on screen. */
    private Paint mLinePaint;

    /** The state of the overlay. One of READY, RUNNING, PAUSE */
    private int mMode;

    /** Indicate whether the surface has been created & is ready to draw */
    private boolean mRun = false;

    /** Handle to the surface manager object we interact with */
    private SurfaceHolder mSurfaceHolder;
    private int mCanvasWidth = 1;
    private int mCanvasHeight = 1;

    public T600Thread(SurfaceHolder surfaceHolder, Context context, Handler handler) {
        // get handles to some important objects
        mSurfaceHolder = surfaceHolder;
        mHandler = handler;
        mContext = context;

        // Initialize paints for speedometer
        mLinePaint = new Paint();
        mLinePaint.setAntiAlias(true);
        mLinePaint.setARGB(255, 0, 255, 0);
    }

    public void doStart() {
      synchronized(mSurfaceHolder) {
        setState(STATE_RUNNING);
      }
    }

    @Override
    public void run() {
      while (mRun) {
        Canvas c = null;
        try {
          c = mSurfaceHolder.lockCanvas(null);
          synchronized (mSurfaceHolder) {
            if (mMode == STATE_RUNNING) {
              updatePhysics();
            }
            doDraw(c);
          }
        } finally {
          // do this in a finally so that if an exception is thrown
          // during the above, we don't leave the Surface in an
          // inconsistent state
          if (c != null) {
            mSurfaceHolder.unlockCanvasAndPost(c);
          }
        }
      }
    }

    public void setRunning(boolean b) {
      mRun = b;
    }

    public void setState(int mode) {
      synchronized(mSurfaceHolder) {
        setState(mode, null);
      }
    }

    public void setState(int mode, CharSequence msg) {
      //TODO
    }

    /* Callback invoked when the surface dimensions change. */
    public void setSurfaceSize(int width, int height) {
        // synchronized to make sure these all change atomically
        synchronized (mSurfaceHolder) {
            mCanvasWidth = width;
            mCanvasHeight = height;
        }
    }
    private void doDraw(Canvas canvas) {
      //canvas.drawBitmap(mBackgroundImage, 0, 0, null);
    }

    private void updatePhysics() {
      //TODO
    }
  }
  ////////////////////////////////////////////////////// END CLASS THREAD
  private static String TAG = "CyborgCam";
  private SurfaceHolder mHolder;
  private Context mContext;
  private T600Thread thread;
  private TextView mStatusText;
  
  public T600View(Context context, AttributeSet attrs) {
    super(context, attrs);
    Log.e(TAG, "Instantiated Overlay: T600");
    // Install a SurfaceHolder.Callback so we get notified when the
    // underlying surface is created and destroyed.
    mHolder = getHolder();
    mHolder.addCallback(this);
    mHolder.setFormat(PixelFormat.RGBA_8888);

    //mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    thread = new T600Thread(mHolder, context, new Handler() {
        public void handleMessage(Message m) {
            mStatusText.setVisibility(m.getData().getInt("viz"));
            mStatusText.setText(m.getData().getString("text"));
        }

      @Override
      public void close() {
        throw new UnsupportedOperationException("Not supported yet.");
      }

      @Override
      public void flush() {
        throw new UnsupportedOperationException("Not supported yet.");
      }

      @Override
      public void publish(LogRecord arg0) {
        throw new UnsupportedOperationException("Not supported yet.");
      }
    });
  }

  public T600Thread getThread() {
    return thread;
  }

  public void setTextView(TextView textView) {
    mStatusText = textView;
  }

  public void surfaceCreated(SurfaceHolder holder) {
    Log.e(TAG, "T600 Overlay: surfaceCreated");
    thread.setRunning(true);
    thread.start();
  }

  public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    Log.e(TAG, "T600 Overlay: surfaceChanged");
    thread.setSurfaceSize(width, height);
  }

  public void surfaceDestroyed(SurfaceHolder holder) {
    Log.e(TAG, "T600 Overlay: surfaceDestroyed");
  }

}