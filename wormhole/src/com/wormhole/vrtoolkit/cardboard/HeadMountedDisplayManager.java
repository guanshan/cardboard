package com.wormhole.vrtoolkit.cardboard;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

public class HeadMountedDisplayManager {
	private static final String TAG = "HeadMountedDisplayManager";
	private final HeadMountedDisplay hmd;
	private final Context context;

	public HeadMountedDisplayManager(Context context) {
		this.context = context;
		this.hmd = new HeadMountedDisplay(createScreenParams(),
				createCardboardDeviceParams());
	}

	public HeadMountedDisplay getHeadMountedDisplay() {
		return this.hmd;
	}

	public void onResume() {
		CardboardDeviceParams deviceParams = createCardboardDeviceParamsFromExternalStorage();
		if ((deviceParams != null)
				&& (!deviceParams.equals(this.hmd.getCardboardDeviceParams()))) {
			this.hmd.setCardboardDeviceParams(deviceParams);
			Log.i("HeadMountedDisplayManager",
					"Successfully read updated device params from external storage");
		}
		ScreenParams screenParams = createScreenParamsFromExternalStorage(getDisplay());
		if ((screenParams != null)
				&& (!screenParams.equals(this.hmd.getScreenParams()))) {
			this.hmd.setScreenParams(screenParams);
			Log.i("HeadMountedDisplayManager",
					"Successfully read updated screen params from external storage");
		}
	}

	public void onPause() {
	}

	public boolean updateCardboardDeviceParams(
			CardboardDeviceParams cardboardDeviceParams) {
		if ((cardboardDeviceParams == null)
				|| (cardboardDeviceParams.equals(this.hmd
						.getCardboardDeviceParams()))) {
			return false;
		}

		this.hmd.setCardboardDeviceParams(cardboardDeviceParams);
		writeCardboardParamsToExternalStorage();
		return true;
	}

	public boolean updateScreenParams(ScreenParams screenParams) {
		if ((screenParams == null)
				|| (screenParams.equals(this.hmd.getScreenParams()))) {
			return false;
		}
		this.hmd.setScreenParams(screenParams);
		return true;
	}

	private void writeCardboardParamsToExternalStorage() {
		boolean success = false;
		OutputStream stream = null;
		String str;
		try {
			stream = new BufferedOutputStream(new FileOutputStream(
					ConfigUtils.getConfigFile("current_device_params")));

			success = this.hmd.getCardboardDeviceParams().writeToOutputStream(
					stream);
		} catch (FileNotFoundException e) {
			str = String.valueOf(e);
			Log.e("HeadMountedDisplayManager", 37
					+ String.valueOf(str).length()
					+ "Unexpected file not found exception: " + str);
		} catch (IllegalStateException e) {
			str = String.valueOf(e);
			Log.w("HeadMountedDisplayManager", 32
					+ String.valueOf(str).length()
					+ "Error writing phone parameters: " + str);
		} finally {
			if (stream != null)
				try {
					stream.close();
				} catch (IOException localIOException3) {
				}
		}
		if (!success)
			Log.e("HeadMountedDisplayManager",
					"Could not write Cardboard parameters to external storage.");
		else
			Log.i("HeadMountedDisplayManager",
					"Successfully wrote Cardboard parameters to external storage.");
	}

	private Display getDisplay() {
		WindowManager windowManager = (WindowManager) this.context
				.getSystemService("window");

		return windowManager.getDefaultDisplay();
	}

	private ScreenParams createScreenParams() {
		Display display = getDisplay();
		ScreenParams params = createScreenParamsFromExternalStorage(display);
		if (params != null) {
			Log.i("HeadMountedDisplayManager",
					"Successfully read screen params from external storage");
			return params;
		}
		return new ScreenParams(display);
	}

	private CardboardDeviceParams createCardboardDeviceParams() {
		CardboardDeviceParams params = createCardboardDeviceParamsFromExternalStorage();
		if (params != null) {
			Log.i("HeadMountedDisplayManager",
					"Successfully read device params from external storage");
			return params;
		}

		params = createCardboardDeviceParamsFromAssetFolder();
		if (params != null) {
			Log.i("HeadMountedDisplayManager",
					"Successfully read device params from asset folder");
			writeCardboardParamsToExternalStorage();
			return params;
		}

		return new CardboardDeviceParams();
	}

	private CardboardDeviceParams createCardboardDeviceParamsFromAssetFolder() {
		Object localObject1;
		try {
			InputStream stream = null;
			try {
				stream = new BufferedInputStream(
						ConfigUtils.openAssetConfigFile(
								this.context.getAssets(),
								"current_device_params"));
				return CardboardDeviceParams.createFromInputStream(stream);
			} finally {
				if (stream != null)
					stream.close();
			}
		} catch (FileNotFoundException e) {
			localObject1 = String.valueOf(e);
			Log.d("HeadMountedDisplayManager", 47
					+ String.valueOf(localObject1).length()
					+ "Bundled Cardboard device parameters not found: "
					+ (String) localObject1);
		} catch (IOException e) {
			localObject1 = String.valueOf(e);
			Log.e("HeadMountedDisplayManager", 43
					+ String.valueOf(localObject1).length()
					+ "Error reading config file in asset folder: "
					+ (String) localObject1);
		}
		return null;
	}

	private CardboardDeviceParams createCardboardDeviceParamsFromExternalStorage() {
		Object localObject1;
		try {
			InputStream stream = null;
			try {
				stream = new BufferedInputStream(new FileInputStream(
						ConfigUtils.getConfigFile("current_device_params")));

				return CardboardDeviceParams.createFromInputStream(stream);
			} finally {
				if (stream != null)
					try {
						stream.close();
					} catch (IOException localIOException1) {
					}
			}
		} catch (FileNotFoundException e) {
			localObject1 = String.valueOf(e);
			Log.d("HeadMountedDisplayManager", 44
					+ String.valueOf(localObject1).length()
					+ "Cardboard device parameters file not found: "
					+ (String) localObject1);
		} catch (IllegalStateException e) {
			localObject1 = String.valueOf(e);
			Log.w("HeadMountedDisplayManager", 43
					+ String.valueOf(localObject1).length()
					+ "Error reading Cardboard device parameters: "
					+ (String) localObject1);
		}
		return null;
	}

	private ScreenParams createScreenParamsFromExternalStorage(Display display) {
		Object localObject1;
		try {
			InputStream stream = null;
			try {
				stream = new BufferedInputStream(new FileInputStream(
						ConfigUtils.getConfigFile("phone_params")));

				return ScreenParams.createFromInputStream(display, stream);
			} finally {
				if (stream != null)
					try {
						stream.close();
					} catch (IOException localIOException1) {
					}
			}
		} catch (FileNotFoundException e) {
			localObject1 = String.valueOf(e);
			Log.d("HeadMountedDisplayManager", 44
					+ String.valueOf(localObject1).length()
					+ "Cardboard screen parameters file not found: "
					+ (String) localObject1);
		} catch (IllegalStateException e) {
			localObject1 = String.valueOf(e);
			Log.w("HeadMountedDisplayManager", 43
					+ String.valueOf(localObject1).length()
					+ "Error reading Cardboard screen parameters: "
					+ (String) localObject1);
		}
		return null;
	}
}