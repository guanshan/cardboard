package com.wormhole.vrtoolkit.cardboard.sensors;

import android.hardware.SensorEventListener;

public abstract interface SensorEventProvider {
	public abstract void start();

	public abstract void stop();

	public abstract void registerListener(
			SensorEventListener paramSensorEventListener);

	public abstract void unregisterListener(
			SensorEventListener paramSensorEventListener);
}