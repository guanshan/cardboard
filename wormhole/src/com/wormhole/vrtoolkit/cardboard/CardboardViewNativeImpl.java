package com.wormhole.vrtoolkit.cardboard;

import java.lang.reflect.Field;
import java.util.concurrent.CountDownLatch;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import android.annotation.SuppressLint;
import android.content.Context;
import android.opengl.EGL14;
import android.opengl.EGLDisplay;
import android.opengl.EGLExt;
import android.opengl.EGLSurface;
import android.opengl.GLSurfaceView;
import android.os.Build;
import android.os.Handler;
import android.os.Trace;
import android.util.Log;
import android.view.MotionEvent;
import com.wormhole.vr.cardboard.DisplaySynchronizer;
import com.wormhole.vr.cardboard.UiLayer;

@UsedByNative
public class CardboardViewNativeImpl
  implements CardboardViewApi
{
  private static final String TAG = CardboardViewNativeImpl.class.getSimpleName();
  private RendererHelper rendererHelper;
  private HeadMountedDisplayManager hmdManager;
  private CountDownLatch shutdownLatch;
  private DisplaySynchronizer displaySynchronizer;
  private UiLayer uiLayer;
  private boolean convertTapIntoTrigger = true;
  private int cardboardTriggerCount = 0;
  private Runnable cardboardTriggerListener;
  private Handler cardboardTriggerListenerHandler;
  private Runnable cardboardBackListener;
  private Handler cardboardBackListenerHandler;
  private final GLSurfaceView glSurfaceView;
  private boolean vrMode = true;
  private volatile boolean restoreGLStateEnabled = true;
  private volatile boolean distortionCorrectionEnabled = true;
  private volatile boolean lowLatencyModeEnabled = false;
  private volatile boolean chromaticAberrationCorrectionEnabled = false;
  private volatile boolean vignetteEnabled = true;

  private volatile boolean electronicDisplayStabilizationEnabled = false;
  private volatile boolean uiLayerAlignmentMarkerEnabled = true;
  private volatile boolean uiLayerAttached = false;
  private final long nativeCardboardView;

  public CardboardViewNativeImpl(Context context, GLSurfaceView view)
  {
    this.hmdManager = new HeadMountedDisplayManager(context);
    ScreenParams screenParams = this.hmdManager.getHeadMountedDisplay().getScreenParams();
    String nativeLibrary;
    try
    {
      String proxyClassName = String.valueOf(getClass().getPackage().getName()).concat(".NativeProxy");
      Class proxyClass = Class.forName(proxyClassName);
      Field proxyLibField = proxyClass.getDeclaredField("PROXY_LIBRARY");
      nativeLibrary = (String)proxyLibField.get(null);
    }
    catch (Exception e)
    {
      Log.d(TAG, "NativeProxy not found");
      nativeLibrary = "vrtoolkit";
    }
    String tmp157_154 = String.valueOf(nativeLibrary); 
    if (tmp157_154.length() != 0) 
    	tmpTernaryOp = "Loading native library ".concat(tmp157_154);
  }

  protected void finalize()
    throws Throwable
  {
    try
    {
      nativeDestroy(this.nativeCardboardView);

      super.finalize(); } finally { super.finalize(); }

  }

  public GLSurfaceView.Renderer setRenderer(CardboardView.Renderer renderer)
  {
    if (renderer == null) {
      return null;
    }

    this.rendererHelper.setRenderer(renderer);
    return this.rendererHelper;
  }

  public GLSurfaceView.Renderer setRenderer(CardboardView.StereoRenderer renderer)
  {
    if (renderer == null) {
      return null;
    }

    this.rendererHelper.setRenderer(renderer);
    return this.rendererHelper;
  }

  public void getCurrentEyeParams(HeadTransform head, Eye leftEye, Eye rightEye, Eye monocular, Eye leftEyeNoDistortionCorrection, Eye rightEyeNoDistortionCorrection)
  {
    this.rendererHelper.getCurrentEyeParams(head, leftEye, rightEye, monocular, leftEyeNoDistortionCorrection, rightEyeNoDistortionCorrection);
  }

  public void setVRModeEnabled(boolean enabled)
  {
    this.vrMode = enabled;
    this.rendererHelper.setVRModeEnabled(enabled);
  }

  public boolean getVRMode()
  {
    return this.vrMode;
  }

  public void setAlignmentMarkerEnabled(final boolean enabled)
  {
    this.uiLayerAlignmentMarkerEnabled = enabled;
    queueEvent(new Runnable()
    {
      public void run() {
        CardboardViewNativeImpl.this.nativeSetUiLayerAlignmentMarkerEnabled(CardboardViewNativeImpl.this.nativeCardboardView, enabled);
      }
    });
  }

  public boolean getAlignmentMarkerEnabled()
  {
    return this.uiLayerAlignmentMarkerEnabled;
  }

  public void setSettingsButtonEnabled(boolean enabled)
  {
    this.uiLayer.setSettingsButtonEnabled(enabled);
  }

  public boolean getSettingsButtonEnabled()
  {
    return this.uiLayer.getSettingsButtonEnabled();
  }

  public void setOnCardboardBackButtonListener(Runnable listener)
  {
    this.uiLayer.setBackButtonListener(listener);
  }

  public boolean getCardboardBackButtonEnabled()
  {
    return this.uiLayer.getBackButtonEnabled();
  }

  public HeadMountedDisplay getHeadMountedDisplay()
  {
    return this.hmdManager.getHeadMountedDisplay();
  }

  public void setRestoreGLStateEnabled(final boolean enabled)
  {
    this.restoreGLStateEnabled = enabled;
    queueEvent(new Runnable()
    {
      public void run() {
        CardboardViewNativeImpl.this.nativeSetRestoreGLStateEnabled(CardboardViewNativeImpl.this.nativeCardboardView, enabled);
      }
    });
  }

  public boolean getRestoreGLStateEnabled()
  {
    return this.restoreGLStateEnabled;
  }

  public void setChromaticAberrationCorrectionEnabled(final boolean enabled)
  {
    this.chromaticAberrationCorrectionEnabled = enabled;
    queueEvent(new Runnable()
    {
      public void run() {
        CardboardViewNativeImpl.this.nativeSetChromaticAberrationCorrectionEnabled(CardboardViewNativeImpl.this.nativeCardboardView, enabled);
      }
    });
  }

  public boolean getChromaticAberrationCorrectionEnabled()
  {
    return this.chromaticAberrationCorrectionEnabled;
  }

  public void setVignetteEnabled(final boolean enabled)
  {
    this.vignetteEnabled = enabled;
    queueEvent(new Runnable()
    {
      public void run() {
        CardboardViewNativeImpl.this.nativeSetVignetteEnabled(CardboardViewNativeImpl.this.nativeCardboardView, enabled);
      }
    });
  }

  public boolean getVignetteEnabled()
  {
    return this.vignetteEnabled;
  }

  public void setElectronicDisplayStabilizationEnabled(boolean enabled)
  {
    this.electronicDisplayStabilizationEnabled = enabled;
    queueEvent(new Runnable()
    {
      public void run() {
        CardboardViewNativeImpl.this.nativeSetElectronicDisplayStabilizationEnabled(CardboardViewNativeImpl.this.nativeCardboardView, 
          CardboardViewNativeImpl.this.electronicDisplayStabilizationEnabled);
      }
    });
  }

  public boolean getElectronicDisplayStabilizationEnabled()
  {
    return this.electronicDisplayStabilizationEnabled;
  }

  public void setNeckModelEnabled(boolean enabled)
  {
    nativeSetNeckModelEnabled(this.nativeCardboardView, enabled);
  }

  public float getNeckModelFactor()
  {
    return nativeGetNeckModelFactor(this.nativeCardboardView);
  }

  public void setNeckModelFactor(float factor)
  {
    nativeSetNeckModelFactor(this.nativeCardboardView, factor);
  }

  public void setGyroBiasEstimationEnabled(boolean enabled)
  {
    nativeSetGyroBiasEstimationEnabled(this.nativeCardboardView, enabled);
  }

  public boolean getGyroBiasEstimationEnabled()
  {
    return nativeGetGyroBiasEstimationEnabled(this.nativeCardboardView);
  }

  public void resetHeadTracker()
  {
    nativeResetHeadTracker(this.nativeCardboardView);
  }

  public void updateCardboardDeviceParams(CardboardDeviceParams cardboardDeviceParams)
  {
    if (this.hmdManager.updateCardboardDeviceParams(cardboardDeviceParams))
      setCardboardDeviceParams(getCardboardDeviceParams());
  }

  public CardboardDeviceParams getCardboardDeviceParams()
  {
    return this.hmdManager.getHeadMountedDisplay().getCardboardDeviceParams();
  }

  public void updateScreenParams(ScreenParams screenParams)
  {
    if (this.hmdManager.updateScreenParams(screenParams))
      setScreenParams(getScreenParams());
  }

  public ScreenParams getScreenParams()
  {
    return this.hmdManager.getHeadMountedDisplay().getScreenParams();
  }

  public float getInterpupillaryDistance()
  {
    return getCardboardDeviceParams().getInterLensDistance();
  }

  public void setDistortionCorrectionEnabled(final boolean enabled)
  {
    this.distortionCorrectionEnabled = enabled;
    queueEvent(new Runnable()
    {
      public void run() {
        CardboardViewNativeImpl.this.nativeSetDistortionCorrectionEnabled(CardboardViewNativeImpl.this.nativeCardboardView, enabled);
      }
    });
  }

  public boolean getDistortionCorrectionEnabled()
  {
    return this.distortionCorrectionEnabled;
  }

  public void setLowLatencyModeEnabled(boolean enabled)
  {
    this.lowLatencyModeEnabled = enabled;
  }

  public boolean getLowLatencyModeEnabled()
  {
    return this.lowLatencyModeEnabled;
  }

  public void undistortTexture(final int inputTexture)
  {
    queueEvent(new Runnable()
    {
      public void run() {
        CardboardViewNativeImpl.this.nativeUndistortTexture(CardboardViewNativeImpl.this.nativeCardboardView, inputTexture);
      }
    });
  }

  public void renderUiLayer()
  {
    if (!this.uiLayerAttached) {
      this.uiLayer.attachUiLayer(null);
      this.uiLayerAttached = true;
    }

    queueEvent(new Runnable()
    {
      public void run() {
        CardboardViewNativeImpl.this.nativeDrawUiLayer(CardboardViewNativeImpl.this.nativeCardboardView);
      }
    });
  }

  public void setDistortionCorrectionScale(final float scale)
  {
    queueEvent(new Runnable()
    {
      public void run() {
        CardboardViewNativeImpl.this.nativeSetDistortionCorrectionScale(CardboardViewNativeImpl.this.nativeCardboardView, scale);
      }
    });
  }

  public void onResume()
  {
    this.hmdManager.onResume();
    setScreenParams(getScreenParams());
    setCardboardDeviceParams(getCardboardDeviceParams());
    nativeStartTracking(this.nativeCardboardView);
  }

  public void onPause()
  {
    this.hmdManager.onPause();
    nativeStopTracking(this.nativeCardboardView); } 
  // ERROR //
  public void onDetachedFromWindow() { // Byte code:
    //   0: aload_0
    //   1: getfield 12	com/google/vrtoolkit/cardboard/CardboardViewNativeImpl:shutdownLatch	Ljava/util/concurrent/CountDownLatch;
    //   4: ifnonnull +76 -> 80
    //   7: aload_0
    //   8: new 134	java/util/concurrent/CountDownLatch
    //   11: dup
    //   12: iconst_1
    //   13: invokespecial 135	java/util/concurrent/CountDownLatch:<init>	(I)V
    //   16: putfield 12	com/google/vrtoolkit/cardboard/CardboardViewNativeImpl:shutdownLatch	Ljava/util/concurrent/CountDownLatch;
    //   19: aload_0
    //   20: getfield 70	com/google/vrtoolkit/cardboard/CardboardViewNativeImpl:rendererHelper	Lcom/google/vrtoolkit/cardboard/CardboardViewNativeImpl$RendererHelper;
    //   23: invokevirtual 136	com/google/vrtoolkit/cardboard/CardboardViewNativeImpl$RendererHelper:shutdown	()V
    //   26: aload_0
    //   27: getfield 12	com/google/vrtoolkit/cardboard/CardboardViewNativeImpl:shutdownLatch	Ljava/util/concurrent/CountDownLatch;
    //   30: invokevirtual 137	java/util/concurrent/CountDownLatch:await	()V
    //   33: goto +42 -> 75
    //   36: astore_1
    //   37: getstatic 8	com/google/vrtoolkit/cardboard/CardboardViewNativeImpl:TAG	Ljava/lang/String;
    //   40: ldc 139
    //   42: aload_1
    //   43: invokevirtual 140	java/lang/InterruptedException:toString	()Ljava/lang/String;
    //   46: invokestatic 47	java/lang/String:valueOf	(Ljava/lang/Object;)Ljava/lang/String;
    //   49: dup
    //   50: invokevirtual 60	java/lang/String:length	()I
    //   53: ifeq +9 -> 62
    //   56: invokevirtual 49	java/lang/String:concat	(Ljava/lang/String;)Ljava/lang/String;
    //   59: goto +12 -> 71
    //   62: pop
    //   63: new 54	java/lang/String
    //   66: dup_x1
    //   67: swap
    //   68: invokespecial 61	java/lang/String:<init>	(Ljava/lang/String;)V
    //   71: invokestatic 141	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;)I
    //   74: pop
    //   75: aload_0
    //   76: aconst_null
    //   77: putfield 12	com/google/vrtoolkit/cardboard/CardboardViewNativeImpl:shutdownLatch	Ljava/util/concurrent/CountDownLatch;
    //   80: return
    //
    // Exception table:
    //   from	to	target	type
    //   26	33	36	java/lang/InterruptedException 
	  } 
  public boolean onTouchEvent(MotionEvent e) { if (e.getActionMasked() == 0) {
      onCardboardTrigger();
      return true;
    }
    return false;
  }

  public synchronized void setOnCardboardTriggerListener(Runnable listener)
  {
    this.cardboardTriggerListener = listener;
  }

  public synchronized void setOnCardboardBackListener(Runnable listener)
  {
    this.cardboardBackListener = listener;
  }

  public void setConvertTapIntoTrigger(boolean enabled)
  {
    this.convertTapIntoTrigger = enabled;
  }

  public boolean getConvertTapIntoTrigger()
  {
    return this.convertTapIntoTrigger;
  }

  public boolean handlesMagnetInput()
  {
    return true;
  }

  public void runOnCardboardTriggerListener()
  {
    synchronized (this) {
      if (this.cardboardTriggerListener == null) {
        return;
      }
      if (this.cardboardTriggerListenerHandler.getLooper().getThread() == Thread.currentThread())
      {
        this.cardboardTriggerListener.run();
      }
      else
      {
        this.cardboardTriggerListenerHandler.post(new Runnable()
        {
          public void run() {
            synchronized (CardboardViewNativeImpl.this) {
              if (CardboardViewNativeImpl.this.cardboardTriggerListener != null)
                CardboardViewNativeImpl.this.cardboardTriggerListener.run();
            }
          }
        });
      }
    }
  }

  public void runOnCardboardBackListener()
  {
    synchronized (this) {
      if (this.cardboardBackListener == null) {
        return;
      }
      if (this.cardboardBackListenerHandler.getLooper().getThread() == Thread.currentThread())
      {
        this.cardboardBackListener.run();
      }
      else
      {
        this.cardboardBackListenerHandler.post(new Runnable()
        {
          public void run() {
            synchronized (CardboardViewNativeImpl.this) {
              if (CardboardViewNativeImpl.this.cardboardBackListener != null)
                CardboardViewNativeImpl.this.cardboardBackListener.run();
            }
          }
        });
      }
    }
  }

  @UsedByNative
  private void onCardboardTrigger()
  {
    if (this.convertTapIntoTrigger)
      runOnCardboardTriggerListener();
  }

  @UsedByNative
  private void onCardboardBack()
  {
    runOnCardboardBackListener();
  }

  private void queueEvent(Runnable r)
  {
    this.glSurfaceView.queueEvent(r);
  }

  private void setCardboardDeviceParams(final CardboardDeviceParams newParams) {
    CardboardDeviceParams deviceParams = new CardboardDeviceParams(newParams);
    queueEvent(new Runnable()
    {
      public void run()
      {
        CardboardViewNativeImpl.this.nativeSetCardboardDeviceParams(CardboardViewNativeImpl.this.nativeCardboardView, newParams.toByteArray());
      }
    });
  }

  private void setScreenParams(ScreenParams newParams) {
    final ScreenParams screenParams = new ScreenParams(newParams);
    queueEvent(new Runnable()
    {
      public void run() {
        CardboardViewNativeImpl.this.nativeSetScreenParams(CardboardViewNativeImpl.this.nativeCardboardView, screenParams.getWidth(), screenParams
          .getHeight(), screenParams.getWidthMeters() / screenParams.getWidth(), screenParams
          .getHeightMeters() / screenParams.getHeight(), screenParams
          .getBorderSizeMeters());
      }
    });
  }

  private static native long nativeSetApplicationState(ClassLoader paramClassLoader, Context paramContext);

  private native long nativeInit(int paramInt1, int paramInt2, float paramFloat1, float paramFloat2, float paramFloat3, long paramLong);

  private native void nativeDestroy(long paramLong);

  private native void nativeSetRenderer(long paramLong, CardboardView.Renderer paramRenderer);

  private native void nativeSetStereoRenderer(long paramLong, CardboardView.StereoRenderer paramStereoRenderer);

  private native void nativeOnSurfaceCreated(long paramLong);

  private native void nativeOnSurfaceChanged(long paramLong, int paramInt1, int paramInt2);

  private native void nativeOnDrawFrame(long paramLong);

  private native void nativeGetCurrentEyeParams(long paramLong, HeadTransform paramHeadTransform, Eye paramEye1, Eye paramEye2, Eye paramEye3, Eye paramEye4, Eye paramEye5);

  private native void nativeStartTracking(long paramLong);

  private native void nativeStopTracking(long paramLong);

  private native void nativeResetHeadTracker(long paramLong);

  private native void nativeSetNeckModelEnabled(long paramLong, boolean paramBoolean);

  private native float nativeGetNeckModelFactor(long paramLong);

  private native void nativeSetNeckModelFactor(long paramLong, float paramFloat);

  private native void nativeSetGyroBiasEstimationEnabled(long paramLong, boolean paramBoolean);

  private native boolean nativeGetGyroBiasEstimationEnabled(long paramLong);

  private native void nativeSetCardboardDeviceParams(long paramLong, byte[] paramArrayOfByte);

  private native void nativeSetScreenParams(long paramLong, int paramInt1, int paramInt2, float paramFloat1, float paramFloat2, float paramFloat3);

  private native void nativeSetVRModeEnabled(long paramLong, boolean paramBoolean);

  private native void nativeSetDistortionCorrectionEnabled(long paramLong, boolean paramBoolean);

  private native void nativeSetDistortionCorrectionScale(long paramLong, float paramFloat);

  private native void nativeSetRestoreGLStateEnabled(long paramLong, boolean paramBoolean);

  private native void nativeSetChromaticAberrationCorrectionEnabled(long paramLong, boolean paramBoolean);

  private native void nativeSetVignetteEnabled(long paramLong, boolean paramBoolean);

  private native void nativeSetElectronicDisplayStabilizationEnabled(long paramLong, boolean paramBoolean);

  private native void nativeUndistortTexture(long paramLong, int paramInt);

  private native void nativeDrawUiLayer(long paramLong);

  private native void nativeSetUiLayerAlignmentMarkerEnabled(long paramLong, boolean paramBoolean);

  private static class TraceCompat
  {
    @SuppressLint("NewApi")
	static void beginSection(String sectionName)
    {
      if (Build.VERSION.SDK_INT < 18) {
        return;
      }
      Trace.beginSection(sectionName);
    }

    @SuppressLint("NewApi")
	static void endSection() {
      if (Build.VERSION.SDK_INT < 18) {
        return;
      }
      Trace.endSection();
    }
  }

  private class RendererHelper
    implements GLSurfaceView.Renderer
  {
    private CardboardView.Renderer renderer;
    private CardboardView.StereoRenderer stereoRenderer;
    private HeadMountedDisplay hmd;
    private boolean vrMode;
    private boolean surfaceCreated;
    private boolean invalidSurfaceSizeWarningShown;
    private EGLDisplay eglDisplay;

    public RendererHelper()
    {
      this.hmd = new HeadMountedDisplay(CardboardViewNativeImpl.this.getHeadMountedDisplay());
      this.vrMode = CardboardViewNativeImpl.this.vrMode;
    }

    public void setRenderer(CardboardView.Renderer renderer) {
      this.renderer = renderer;
      CardboardViewNativeImpl.this.nativeSetRenderer(CardboardViewNativeImpl.this.nativeCardboardView, renderer);
    }

    public void setRenderer(CardboardView.StereoRenderer stereoRenderer) {
      this.stereoRenderer = stereoRenderer;
      CardboardViewNativeImpl.this.nativeSetStereoRenderer(CardboardViewNativeImpl.this.nativeCardboardView, stereoRenderer);
    }

    public void shutdown() {
      CardboardViewNativeImpl.this.queueEvent(new Runnable()
      {
        public void run() {
          if ((CardboardViewNativeImpl.RendererHelper.this.renderer != null) && (CardboardViewNativeImpl.RendererHelper.this.surfaceCreated)) {
            CardboardViewNativeImpl.RendererHelper.this.surfaceCreated = false;
            CardboardViewNativeImpl.RendererHelper.this.callOnRendererShutdown();
          }

          CardboardViewNativeImpl.this.shutdownLatch.countDown();
        }
      });
    }

    public void setVRModeEnabled(final boolean enabled) {
      CardboardViewNativeImpl.this.queueEvent(new Runnable()
      {
        public void run() {
          if (CardboardViewNativeImpl.RendererHelper.this.vrMode == enabled) {
            return;
          }

          CardboardViewNativeImpl.RendererHelper.this.vrMode = enabled;

          CardboardViewNativeImpl.this.nativeSetVRModeEnabled(CardboardViewNativeImpl.this.nativeCardboardView, enabled);

          CardboardViewNativeImpl.RendererHelper.this.onSurfaceChanged((GL10)null, CardboardViewNativeImpl.RendererHelper.this.hmd
            .getScreenParams().getWidth(), CardboardViewNativeImpl.RendererHelper.this.hmd
            .getScreenParams().getHeight()); }  } ); } 
    // ERROR //
    public void getCurrentEyeParams(final HeadTransform head, final Eye leftEye, final Eye rightEye, final Eye monocular, final Eye leftEyeNoDistortionCorrection, final Eye rightEyeNoDistortionCorrection) { // Byte code:
      //   0: new 21	java/util/concurrent/CountDownLatch
      //   3: dup
      //   4: iconst_1
      //   5: invokespecial 22	java/util/concurrent/CountDownLatch:<init>	(I)V
      //   8: astore 7
      //   10: aload_0
      //   11: getfield 6	com/google/vrtoolkit/cardboard/CardboardViewNativeImpl$RendererHelper:this$0	Lcom/google/vrtoolkit/cardboard/CardboardViewNativeImpl;
      //   14: new 23	com/google/vrtoolkit/cardboard/CardboardViewNativeImpl$RendererHelper$3
      //   17: dup
      //   18: aload_0
      //   19: aload_1
      //   20: aload_2
      //   21: aload_3
      //   22: aload 4
      //   24: aload 5
      //   26: aload 6
      //   28: aload 7
      //   30: invokespecial 24	com/google/vrtoolkit/cardboard/CardboardViewNativeImpl$RendererHelper$3:<init>	(Lcom/google/vrtoolkit/cardboard/CardboardViewNativeImpl$RendererHelper;Lcom/google/vrtoolkit/cardboard/HeadTransform;Lcom/google/vrtoolkit/cardboard/Eye;Lcom/google/vrtoolkit/cardboard/Eye;Lcom/google/vrtoolkit/cardboard/Eye;Lcom/google/vrtoolkit/cardboard/Eye;Lcom/google/vrtoolkit/cardboard/Eye;Ljava/util/concurrent/CountDownLatch;)V
      //   33: invokestatic 18	com/google/vrtoolkit/cardboard/CardboardViewNativeImpl:access$2200	(Lcom/google/vrtoolkit/cardboard/CardboardViewNativeImpl;Ljava/lang/Runnable;)V
      //   36: aload 7
      //   38: invokevirtual 25	java/util/concurrent/CountDownLatch:await	()V
      //   41: goto +44 -> 85
      //   44: astore 8
      //   46: invokestatic 27	com/google/vrtoolkit/cardboard/CardboardViewNativeImpl:access$2700	()Ljava/lang/String;
      //   49: ldc 28
      //   51: aload 8
      //   53: invokevirtual 29	java/lang/InterruptedException:toString	()Ljava/lang/String;
      //   56: invokestatic 30	java/lang/String:valueOf	(Ljava/lang/Object;)Ljava/lang/String;
      //   59: dup
      //   60: invokevirtual 31	java/lang/String:length	()I
      //   63: ifeq +9 -> 72
      //   66: invokevirtual 32	java/lang/String:concat	(Ljava/lang/String;)Ljava/lang/String;
      //   69: goto +12 -> 81
      //   72: pop
      //   73: new 33	java/lang/String
      //   76: dup_x1
      //   77: swap
      //   78: invokespecial 34	java/lang/String:<init>	(Ljava/lang/String;)V
      //   81: invokestatic 35	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;)I
      //   84: pop
      //   85: return
      //
      // Exception table:
      //   from	to	target	type
      //   36	41	44	java/lang/InterruptedException 
    	} 
    @SuppressLint("NewApi")
	public void onDrawFrame(GL10 gl) { if (((this.renderer == null) && (this.stereoRenderer == null)) || (!this.surfaceCreated)) {
        return;
      }

      long nextVSync = 0L;

      if (CardboardViewNativeImpl.this.lowLatencyModeEnabled) {
        CardboardViewNativeImpl.TraceCompat.beginSection("Sync");
        nextVSync = CardboardViewNativeImpl.this.displaySynchronizer.syncToNextVsync();
        CardboardViewNativeImpl.TraceCompat.endSection();
      }

      CardboardViewNativeImpl.TraceCompat.beginSection("Render");
      CardboardViewNativeImpl.this.nativeOnDrawFrame(CardboardViewNativeImpl.this.nativeCardboardView);
      CardboardViewNativeImpl.TraceCompat.endSection();

      if (Build.VERSION.SDK_INT < 17) {
        return;
      }

      if (CardboardViewNativeImpl.this.lowLatencyModeEnabled) {
        if (Build.VERSION.SDK_INT < 19)
        {
          EGL14.eglSwapInterval(this.eglDisplay, 0);
        }
        else {
          EGLSurface surface = EGL14.eglGetCurrentSurface(12377);

          EGLExt.eglPresentationTimeANDROID(this.eglDisplay, surface, nextVSync - 1000000L);
        }
      }
      else EGL14.eglSwapInterval(this.eglDisplay, 1);
    }

    public void onSurfaceChanged(GL10 gl, int width, int height)
    {
      if (((this.renderer == null) && (this.stereoRenderer == null)) || (!this.surfaceCreated)) {
        return;
      }

      ScreenParams screen = this.hmd.getScreenParams();
      if ((this.vrMode) && ((width != screen.getWidth()) || (height != screen.getHeight()))) {
        if (!this.invalidSurfaceSizeWarningShown)
        {
          int i = screen
            .getWidth(); int j = screen.getHeight();

          Log.e(CardboardViewNativeImpl.TAG, 
            134 + "Surface size " + width + "x" + height + " does not match the expected screen size " + i + "x" + j + ". Stereo rendering might feel off.");
        }

        this.invalidSurfaceSizeWarningShown = true;
      } else {
        this.invalidSurfaceSizeWarningShown = false;
      }

      CardboardViewNativeImpl.this.nativeOnSurfaceChanged(CardboardViewNativeImpl.this.nativeCardboardView, width, height);

      callOnSurfaceChanged(width, height);
    }

    private void callOnSurfaceCreated(EGLConfig config)
    {
      CardboardViewNativeImpl.this.nativeOnSurfaceCreated(CardboardViewNativeImpl.this.nativeCardboardView);

      if (this.renderer != null)
        this.renderer.onSurfaceCreated(config);
      else if (this.stereoRenderer != null)
        this.stereoRenderer.onSurfaceCreated(config);
    }

    public void onSurfaceCreated(GL10 gl, EGLConfig config)
    {
      if ((this.renderer == null) && (this.stereoRenderer == null)) {
        return;
      }

      this.surfaceCreated = true;
      if (!CardboardViewNativeImpl.this.uiLayerAttached) {
        CardboardViewNativeImpl.this.uiLayer.attachUiLayer(null);
        CardboardViewNativeImpl.this.uiLayerAttached = true;
      }

      if (Build.VERSION.SDK_INT > 16) {
        this.eglDisplay = EGL14.eglGetCurrentDisplay();
      }

      callOnSurfaceCreated(config);
    }

    private void callOnSurfaceChanged(int width, int height)
    {
      if (this.renderer != null) {
        this.renderer.onSurfaceChanged(width, height);
      }
      else if (this.stereoRenderer != null)
        if (this.vrMode)
        {
          this.stereoRenderer.onSurfaceChanged(width / 2, height);
        }
        else this.stereoRenderer.onSurfaceChanged(width, height);
    }

    private void callOnRendererShutdown()
    {
      if (this.renderer != null)
        this.renderer.onRendererShutdown();
      else if (this.stereoRenderer != null)
        this.stereoRenderer.onRendererShutdown();
    }
  }
}