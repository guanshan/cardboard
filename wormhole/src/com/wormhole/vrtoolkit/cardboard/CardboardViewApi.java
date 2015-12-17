package com.wormhole.vrtoolkit.cardboard;

import android.opengl.GLSurfaceView;
import android.view.MotionEvent;

public abstract interface CardboardViewApi {
	public abstract GLSurfaceView.Renderer setRenderer(
			CardboardView.Renderer paramRenderer);

	public abstract GLSurfaceView.Renderer setRenderer(
			CardboardView.StereoRenderer paramStereoRenderer);

	public abstract void getCurrentEyeParams(HeadTransform paramHeadTransform,
			Eye paramEye1, Eye paramEye2, Eye paramEye3, Eye paramEye4,
			Eye paramEye5);

	public abstract void setVRModeEnabled(boolean paramBoolean);

	public abstract boolean getVRMode();

	public abstract void setAlignmentMarkerEnabled(boolean paramBoolean);

	public abstract boolean getAlignmentMarkerEnabled();

	public abstract void setSettingsButtonEnabled(boolean paramBoolean);

	public abstract boolean getSettingsButtonEnabled();

	public abstract void setOnCardboardBackButtonListener(Runnable paramRunnable);

	public abstract boolean getCardboardBackButtonEnabled();

	public abstract HeadMountedDisplay getHeadMountedDisplay();

	public abstract void setRestoreGLStateEnabled(boolean paramBoolean);

	public abstract boolean getRestoreGLStateEnabled();

	public abstract void setChromaticAberrationCorrectionEnabled(
			boolean paramBoolean);

	public abstract boolean getChromaticAberrationCorrectionEnabled();

	public abstract void setVignetteEnabled(boolean paramBoolean);

	public abstract boolean getVignetteEnabled();

	public abstract void setElectronicDisplayStabilizationEnabled(
			boolean paramBoolean);

	public abstract boolean getElectronicDisplayStabilizationEnabled();

	public abstract void setNeckModelEnabled(boolean paramBoolean);

	public abstract float getNeckModelFactor();

	public abstract void setNeckModelFactor(float paramFloat);

	public abstract void setGyroBiasEstimationEnabled(boolean paramBoolean);

	public abstract boolean getGyroBiasEstimationEnabled();

	public abstract void resetHeadTracker();

	public abstract void updateCardboardDeviceParams(
			CardboardDeviceParams paramCardboardDeviceParams);

	public abstract CardboardDeviceParams getCardboardDeviceParams();

	public abstract void updateScreenParams(ScreenParams paramScreenParams);

	public abstract ScreenParams getScreenParams();

	public abstract float getInterpupillaryDistance();

	public abstract void setDistortionCorrectionEnabled(boolean paramBoolean);

	public abstract boolean getDistortionCorrectionEnabled();

	public abstract void setLowLatencyModeEnabled(boolean paramBoolean);

	public abstract boolean getLowLatencyModeEnabled();

	public abstract void undistortTexture(int paramInt);

	public abstract void renderUiLayer();

	public abstract void setDistortionCorrectionScale(float paramFloat);

	public abstract void onResume();

	public abstract void onPause();

	public abstract void onDetachedFromWindow();

	public abstract boolean onTouchEvent(MotionEvent paramMotionEvent);

	public abstract void setOnCardboardBackListener(Runnable paramRunnable);

	public abstract void runOnCardboardBackListener();

	public abstract void setOnCardboardTriggerListener(Runnable paramRunnable);

	public abstract void runOnCardboardTriggerListener();

	public abstract void setConvertTapIntoTrigger(boolean paramBoolean);

	public abstract boolean getConvertTapIntoTrigger();

	public abstract boolean handlesMagnetInput();
}