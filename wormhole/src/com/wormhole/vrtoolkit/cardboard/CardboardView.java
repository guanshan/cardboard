package com.wormhole.vrtoolkit.cardboard;

import javax.microedition.khronos.egl.EGLConfig;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class CardboardView extends GLSurfaceView {
	private CardboardViewApi cardboardViewApi;
	private boolean rendererIsSet = false;

	public CardboardView(Context context) {
		super(context);
		init(context);
	}

	public CardboardView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public void setRenderer(Renderer renderer) {
		GLSurfaceView.Renderer glRenderer = this.cardboardViewApi
				.setRenderer(renderer);

		if (glRenderer == null) {
			return;
		}
		super.setRenderer(glRenderer);
		this.rendererIsSet = true;
	}

	public void setRenderer(StereoRenderer renderer) {
		GLSurfaceView.Renderer glRenderer = this.cardboardViewApi
				.setRenderer(renderer);

		if (glRenderer == null) {
			return;
		}
		super.setRenderer(glRenderer);
		this.rendererIsSet = true;
	}

	public void getCurrentEyeParams(HeadTransform head, Eye leftEye,
			Eye rightEye, Eye monocular, Eye leftEyeNoDistortionCorrection,
			Eye rightEyeNoDistortionCorrection) {
		this.cardboardViewApi.getCurrentEyeParams(head, leftEye, rightEye,
				monocular, leftEyeNoDistortionCorrection,
				rightEyeNoDistortionCorrection);
	}

	public void setVRModeEnabled(boolean enabled) {
		this.cardboardViewApi.setVRModeEnabled(enabled);
	}

	public boolean getVRMode() {
		return this.cardboardViewApi.getVRMode();
	}

	public void setAlignmentMarkerEnabled(boolean enabled) {
		this.cardboardViewApi.setAlignmentMarkerEnabled(enabled);
	}

	public boolean getAlignmentMarkerEnabled() {
		return this.cardboardViewApi.getAlignmentMarkerEnabled();
	}

	public void setSettingsButtonEnabled(boolean enabled) {
		this.cardboardViewApi.setSettingsButtonEnabled(enabled);
	}

	public boolean getSettingsButtonEnabled() {
		return this.cardboardViewApi.getSettingsButtonEnabled();
	}

	public void setOnCardboardBackButtonListener(Runnable listener) {
		this.cardboardViewApi.setOnCardboardBackButtonListener(listener);
	}

	public boolean getCardboardBackButtonEnabled() {
		return this.cardboardViewApi.getCardboardBackButtonEnabled();
	}

	public HeadMountedDisplay getHeadMountedDisplay() {
		return this.cardboardViewApi.getHeadMountedDisplay();
	}

	public void setRestoreGLStateEnabled(boolean enabled) {
		this.cardboardViewApi.setRestoreGLStateEnabled(enabled);
	}

	public boolean getRestoreGLStateEnabled() {
		return this.cardboardViewApi.getRestoreGLStateEnabled();
	}

	public void setChromaticAberrationCorrectionEnabled(boolean enabled) {
		this.cardboardViewApi.setChromaticAberrationCorrectionEnabled(enabled);
	}

	public boolean getChromaticAberrationCorrectionEnabled() {
		return this.cardboardViewApi.getChromaticAberrationCorrectionEnabled();
	}

	public void setVignetteEnabled(boolean enabled) {
		this.cardboardViewApi.setVignetteEnabled(enabled);
	}

	public boolean getVignetteEnabled() {
		return this.cardboardViewApi.getVignetteEnabled();
	}

	public void setElectronicDisplayStabilizationEnabled(boolean enabled) {
		this.cardboardViewApi.setElectronicDisplayStabilizationEnabled(enabled);
	}

	public boolean getElectronicDisplayStabilizationEnabled() {
		return this.cardboardViewApi.getElectronicDisplayStabilizationEnabled();
	}

	public void setNeckModelEnabled(boolean enabled) {
		this.cardboardViewApi.setNeckModelEnabled(enabled);
	}

	public float getNeckModelFactor() {
		return this.cardboardViewApi.getNeckModelFactor();
	}

	public void setNeckModelFactor(float factor) {
		this.cardboardViewApi.setNeckModelFactor(factor);
	}

	public void setGyroBiasEstimationEnabled(boolean enabled) {
		this.cardboardViewApi.setGyroBiasEstimationEnabled(enabled);
	}

	public boolean getGyroBiasEstimationEnabled() {
		return this.cardboardViewApi.getGyroBiasEstimationEnabled();
	}

	public void resetHeadTracker() {
		this.cardboardViewApi.resetHeadTracker();
	}

	public void updateCardboardDeviceParams(
			CardboardDeviceParams cardboardDeviceParams) {
		this.cardboardViewApi
				.updateCardboardDeviceParams(cardboardDeviceParams);
	}

	public CardboardDeviceParams getCardboardDeviceParams() {
		return this.cardboardViewApi.getCardboardDeviceParams();
	}

	public void updateScreenParams(ScreenParams screenParams) {
		this.cardboardViewApi.updateScreenParams(screenParams);
	}

	public ScreenParams getScreenParams() {
		return this.cardboardViewApi.getScreenParams();
	}

	public float getInterpupillaryDistance() {
		return this.cardboardViewApi.getInterpupillaryDistance();
	}

	public void setDistortionCorrectionEnabled(boolean enabled) {
		this.cardboardViewApi.setDistortionCorrectionEnabled(enabled);
	}

	public boolean getDistortionCorrectionEnabled() {
		return this.cardboardViewApi.getDistortionCorrectionEnabled();
	}

	public void setLowLatencyModeEnabled(boolean enabled) {
		this.cardboardViewApi.setLowLatencyModeEnabled(enabled);
	}

	public boolean getLowLatencyModeEnabled() {
		return this.cardboardViewApi.getLowLatencyModeEnabled();
	}

	public void undistortTexture(int inputTexture) {
		this.cardboardViewApi.undistortTexture(inputTexture);
	}

	public void renderUiLayer() {
		this.cardboardViewApi.renderUiLayer();
	}

	public void setDistortionCorrectionScale(float scale) {
		this.cardboardViewApi.setDistortionCorrectionScale(scale);
	}

	public void onResume() {
		if (this.rendererIsSet) {
			super.onResume();
		}

		this.cardboardViewApi.onResume();
	}

	public void onPause() {
		this.cardboardViewApi.onPause();

		if (this.rendererIsSet)
			super.onPause();
	}

	public void queueEvent(Runnable r) {
		if (!this.rendererIsSet) {
			r.run();
			return;
		}

		super.queueEvent(r);
	}

	public void setRenderer(GLSurfaceView.Renderer renderer) {
		throw new RuntimeException(
				"Please use the CardboardView renderer interfaces");
	}

	public void onDetachedFromWindow() {
		if (this.rendererIsSet) {
			this.cardboardViewApi.onDetachedFromWindow();
		}
		super.onDetachedFromWindow();
	}

	public boolean onTouchEvent(MotionEvent e) {
		if (this.cardboardViewApi.onTouchEvent(e)) {
			return true;
		}
		return super.onTouchEvent(e);
	}

	public void setOnCardboardBackListener(Runnable listener) {
		this.cardboardViewApi.setOnCardboardBackListener(listener);
	}

	public void setOnCardboardTriggerListener(Runnable listener) {
		this.cardboardViewApi.setOnCardboardTriggerListener(listener);
	}

	void setConvertTapIntoTrigger(boolean enable) {
		this.cardboardViewApi.setConvertTapIntoTrigger(enable);
	}

	boolean getConvertTapIntoTrigger() {
		return this.cardboardViewApi.getConvertTapIntoTrigger();
	}

	boolean handlesMagnetInput() {
		return this.cardboardViewApi.handlesMagnetInput();
	}

	private void init(Context context) {
		this.cardboardViewApi = ImplementationSelector.createCardboardViewApi(
				context, this);

		if (Build.VERSION.SDK_INT < 19) {
			setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
				public void onSystemUiVisibilityChange(int visibility) {
					if ((CardboardView.this.getConvertTapIntoTrigger())
							&& ((visibility & 0x2) == 0)) {
						CardboardView.this.cardboardViewApi
								.runOnCardboardTriggerListener();
					}

				}

			});
		}

		setEGLContextClientVersion(2);
		setPreserveEGLContextOnPause(true);
	}

	public static abstract interface StereoRenderer {
		@UsedByNative
		public abstract void onNewFrame(HeadTransform paramHeadTransform);

		@UsedByNative
		public abstract void onDrawEye(Eye paramEye);

		@UsedByNative
		public abstract void onFinishFrame(Viewport paramViewport);

		public abstract void onSurfaceChanged(int paramInt1, int paramInt2);

		public abstract void onSurfaceCreated(EGLConfig paramEGLConfig);

		public abstract void onRendererShutdown();
	}

	public static abstract interface Renderer {
		@UsedByNative
		public abstract void onDrawFrame(HeadTransform paramHeadTransform,
				Eye paramEye1, Eye paramEye2);

		@UsedByNative
		public abstract void onFinishFrame(Viewport paramViewport);

		public abstract void onSurfaceChanged(int paramInt1, int paramInt2);

		public abstract void onSurfaceCreated(EGLConfig paramEGLConfig);

		public abstract void onRendererShutdown();
	}
}