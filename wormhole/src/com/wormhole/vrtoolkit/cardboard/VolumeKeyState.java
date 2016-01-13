package com.wormhole.vrtoolkit.cardboard;

import android.view.KeyEvent;

import com.wormhole.vrtoolkit.cardboard.sensors.NfcSensor;

final class VolumeKeyState {
	private final Handler handler;
	private int volumeKeysMode;

	public VolumeKeyState(Handler handler) {
		this.handler = handler;
		this.volumeKeysMode = 0;
	}

	public void onCreate() {
		this.volumeKeysMode = 2;
	}

	public void setVolumeKeysMode(int mode) {
		this.volumeKeysMode = mode;
	}

	public int getVolumeKeysMode() {
		return this.volumeKeysMode;
	}

	public boolean areVolumeKeysDisabled(NfcSensor nfcSensor) {
		switch (this.volumeKeysMode) {
		case 0:
			return false;
		case 2:
			return nfcSensor.isDeviceInCardboard();
		case 1:
			return true;
		}

		int i = this.volumeKeysMode;
		throw new IllegalStateException("Invalid volume keys mode " + i);
	}

	public boolean onKey(int keyCode) {
		return ((keyCode == KeyEvent.KEYCODE_VOLUME_UP) || (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN))
				&& (this.handler.areVolumeKeysDisabled());
	}

	public static abstract interface Handler {
		public abstract boolean areVolumeKeysDisabled();

		public static abstract class VolumeKeys {
			public static final int NOT_DISABLED = 0;
			public static final int DISABLED = 1;
			public static final int DISABLED_WHILE_IN_CARDBOARD = 2;
		}
	}
}