package com.wormhole.vrtoolkit.cardboard;

public class HeadMountedDisplay {
	private ScreenParams screen;
	private CardboardDeviceParams cardboardDevice;

	public HeadMountedDisplay(ScreenParams screenParams,
			CardboardDeviceParams cardboardDevice) {
		this.screen = screenParams;
		this.cardboardDevice = cardboardDevice;
	}

	public HeadMountedDisplay(HeadMountedDisplay hmd) {
		this.screen = new ScreenParams(hmd.screen);
		this.cardboardDevice = new CardboardDeviceParams(hmd.cardboardDevice);
	}

	public void setScreenParams(ScreenParams screen) {
		this.screen = new ScreenParams(screen);
	}

	public ScreenParams getScreenParams() {
		return this.screen;
	}

	public void setCardboardDeviceParams(
			CardboardDeviceParams cardboardDeviceParams) {
		this.cardboardDevice = new CardboardDeviceParams(cardboardDeviceParams);
	}

	public CardboardDeviceParams getCardboardDeviceParams() {
		return this.cardboardDevice;
	}

	public boolean equals(Object other) {
		if (other == null) {
			return false;
		}
		if (other == this) {
			return true;
		}
		if (!(other instanceof HeadMountedDisplay)) {
			return false;
		}
		HeadMountedDisplay o = (HeadMountedDisplay) other;

		return (this.screen.equals(o.screen))
				&& (this.cardboardDevice.equals(o.cardboardDevice));
	}
}