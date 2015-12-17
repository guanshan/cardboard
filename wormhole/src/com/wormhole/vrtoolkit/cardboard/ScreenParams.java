package com.wormhole.vrtoolkit.cardboard;

import java.io.InputStream;

import android.util.DisplayMetrics;
import android.view.Display;

import com.wormhole.vrtoolkit.cardboard.proto.Phone;

public class ScreenParams {
	private static final float METERS_PER_INCH = 0.0254F;
	private static final float DEFAULT_BORDER_SIZE_METERS = 0.003F;
	private int width;
	private int height;
	private float xMetersPerPixel;
	private float yMetersPerPixel;
	private float borderSizeMeters;

	public ScreenParams(Display display) {
		DisplayMetrics metrics = new DisplayMetrics();
		try {
			display.getRealMetrics(metrics);
		} catch (NoSuchMethodError e) {
			display.getMetrics(metrics);
		}

		this.xMetersPerPixel = (0.0254F / metrics.xdpi);
		this.yMetersPerPixel = (0.0254F / metrics.ydpi);
		this.width = metrics.widthPixels;
		this.height = metrics.heightPixels;
		this.borderSizeMeters = 0.003F;

		if (this.height > this.width) {
			int tempPx = this.width;
			this.width = this.height;
			this.height = tempPx;

			float tempMetersPerPixel = this.xMetersPerPixel;
			this.xMetersPerPixel = this.yMetersPerPixel;
			this.yMetersPerPixel = tempMetersPerPixel;
		}
	}

	public static ScreenParams fromProto(Display display,
			Phone.PhoneParams params) {
		if (params == null) {
			return null;
		}

		ScreenParams screenParams = new ScreenParams(display);

		if (params.hasXPpi()) {
			screenParams.xMetersPerPixel = (0.0254F / params.getXPpi());
		}
		if (params.hasYPpi()) {
			screenParams.yMetersPerPixel = (0.0254F / params.getYPpi());
		}
		if (params.hasBottomBezelHeight()) {
			screenParams.borderSizeMeters = params.getBottomBezelHeight();
		}

		return screenParams;
	}

	public ScreenParams(ScreenParams params) {
		this.width = params.width;
		this.height = params.height;
		this.xMetersPerPixel = params.xMetersPerPixel;
		this.yMetersPerPixel = params.yMetersPerPixel;
		this.borderSizeMeters = params.borderSizeMeters;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getWidth() {
		return this.width;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getHeight() {
		return this.height;
	}

	public float getWidthMeters() {
		return this.width * this.xMetersPerPixel;
	}

	public float getHeightMeters() {
		return this.height * this.yMetersPerPixel;
	}

	public void setBorderSizeMeters(float screenBorderSize) {
		this.borderSizeMeters = screenBorderSize;
	}

	public float getBorderSizeMeters() {
		return this.borderSizeMeters;
	}

	public boolean equals(Object other) {
		if (other == null) {
			return false;
		}

		if (other == this) {
			return true;
		}

		if (!(other instanceof ScreenParams)) {
			return false;
		}

		ScreenParams o = (ScreenParams) other;

		return (this.width == o.width) && (this.height == o.height)
				&& (this.xMetersPerPixel == o.xMetersPerPixel)
				&& (this.yMetersPerPixel == o.yMetersPerPixel)
				&& (this.borderSizeMeters == o.borderSizeMeters);
	}

	public String toString() {
		int i = this.width;
		i = this.height;
		float f = this.xMetersPerPixel;
		f = this.yMetersPerPixel;
		f = this.borderSizeMeters;

		return "{\n"
				+ new StringBuilder(22).append("  width: ").append(i)
						.append(",\n").toString()
				+ new StringBuilder(23).append("  height: ").append(i)
						.append(",\n").toString()
				+ new StringBuilder(39).append("  x_meters_per_pixel: ")
						.append(f).append(",\n").toString()
				+ new StringBuilder(39).append("  y_meters_per_pixel: ")
						.append(f).append(",\n").toString()
				+ new StringBuilder(39).append("  border_size_meters: ")
						.append(f).append(",\n").toString() + "}";
	}

	public static ScreenParams createFromInputStream(Display display,
			InputStream inputStream) {
		Phone.PhoneParams phoneParams = PhoneParams
				.readFromInputStream(inputStream);
		if (phoneParams == null) {
			return null;
		}
		return fromProto(display, phoneParams);
	}
}