package com.wormhole.vrtoolkit.cardboard;

import android.app.Activity;
import android.os.Build;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;

class FullscreenMode {
	static final int NAVIGATION_BAR_TIMEOUT_MS = 2000;
	Activity activity;

	FullscreenMode(Activity activity) {
		this.activity = activity;
	}

	void startFullscreenMode() {
		this.activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

		if (Build.VERSION.SDK_INT < 19) {
			final Handler handler = new Handler();
			this.activity
					.getWindow()
					.getDecorView()
					.setOnSystemUiVisibilityChangeListener(
							new View.OnSystemUiVisibilityChangeListener() {
								public void onSystemUiVisibilityChange(
										int visibility) {
									if ((visibility & 0x2) == 0)
										handler.postDelayed(new Runnable() {
											public void run() {
												FullscreenMode.this
														.setFullscreenMode();
											}
										}, 2000L);
								}
							});
		}
	}

	void onWindowFocusChanged(boolean hasFocus) {
		if (hasFocus)
			setFullscreenMode();
	}

	void setFullscreenMode() {
		this.activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
	}
}