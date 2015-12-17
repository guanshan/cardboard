package com.wormhole.vrtoolkit.cardboard;

import android.app.NativeActivity;
import android.nfc.NdefMessage;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;

import com.wormhole.vrtoolkit.cardboard.sensors.NfcSensor;
import com.wormhole.vrtoolkit.cardboard.sensors.SensorConnection;

public class CardboardNativeActivity extends NativeActivity implements
		SensorConnection.SensorListener, VolumeKeyState.Handler {
	private final SensorConnection sensorConnection = new SensorConnection(this);
	private final VolumeKeyState volumeKeyState = new VolumeKeyState(this);
	private final FullscreenMode fullscreenMode = new FullscreenMode(this);
	private CardboardView cardboardView;

	public void setCardboardView(CardboardView cardboardView) {
		this.cardboardView = cardboardView;
		if (cardboardView == null) {
			return;
		}

		NdefMessage tagContents = this.sensorConnection.getNfcSensor()
				.getTagContents();
		if (tagContents != null)
			updateCardboardDeviceParams(CardboardDeviceParams
					.createFromNfcContents(tagContents));
	}

	public CardboardView getCardboardView() {
		return this.cardboardView;
	}

	public NfcSensor getNfcSensor() {
		return this.sensorConnection.getNfcSensor();
	}

	public void setVolumeKeysMode(int mode) {
		this.volumeKeyState.setVolumeKeysMode(mode);
	}

	public int getVolumeKeysMode() {
		return this.volumeKeyState.getVolumeKeysMode();
	}

	public boolean areVolumeKeysDisabled() {
		return this.volumeKeyState.areVolumeKeysDisabled(this.sensorConnection
				.getNfcSensor());
	}

	public void onInsertedIntoCardboard(
			CardboardDeviceParams cardboardDeviceParams) {
		updateCardboardDeviceParams(cardboardDeviceParams);
	}

	public void onRemovedFromCardboard() {
	}

	public void onCardboardTrigger() {
	}

	protected void updateCardboardDeviceParams(CardboardDeviceParams newParams) {
		if (this.cardboardView != null)
			this.cardboardView.updateCardboardDeviceParams(newParams);
	}

	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(1);

		super.onCreate(savedInstanceState);

		this.fullscreenMode.startFullscreenMode();
		this.sensorConnection.onCreate(this);
		this.volumeKeyState.onCreate();
	}

	protected void onResume() {
		super.onResume();

		if (this.cardboardView != null) {
			this.cardboardView.onResume();
		}
		this.sensorConnection.onResume(this);
		this.fullscreenMode.setFullscreenMode();
	}

	protected void onPause() {
		super.onPause();

		if (this.cardboardView != null) {
			this.cardboardView.onPause();
		}
		this.sensorConnection.onPause(this);
	}

	protected void onDestroy() {
		this.sensorConnection.onDestroy(this);

		super.onDestroy();
	}

	public void setContentView(View view) {
		if ((view instanceof CardboardView)) {
			setCardboardView((CardboardView) view);
		}

		super.setContentView(view);
	}

	public void setContentView(View view, ViewGroup.LayoutParams params) {
		if ((view instanceof CardboardView)) {
			setCardboardView((CardboardView) view);
		}

		super.setContentView(view, params);
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		return (this.volumeKeyState.onKey(keyCode))
				|| (super.onKeyDown(keyCode, event));
	}

	public boolean onKeyUp(int keyCode, KeyEvent event) {
		return (this.volumeKeyState.onKey(keyCode))
				|| (super.onKeyUp(keyCode, event));
	}

	public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);
		this.fullscreenMode.onWindowFocusChanged(hasFocus);
	}
}