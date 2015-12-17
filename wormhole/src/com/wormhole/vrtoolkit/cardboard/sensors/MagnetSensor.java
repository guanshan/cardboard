package com.wormhole.vrtoolkit.cardboard.sensors;

import java.util.ArrayList;
import java.util.Arrays;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;

public class MagnetSensor {
	private static final String HTC_ONE_MODEL = "HTC One";
	private static final String HTC_ONE_M8_MODEL = "HTC_M8x";
	private TriggerDetector detector;
	private Thread detectorThread;

	public MagnetSensor(Context context) {
		if (("HTC One".equals(Build.MODEL)) || ("HTC_M8x".equals(Build.MODEL)))
			this.detector = new VectorTriggerDetector(context);
		else
			this.detector = new ThresholdTriggerDetector(context);
	}

	public void start() {
		this.detectorThread = new Thread(this.detector);
		this.detectorThread.start();
	}

	public void stop() {
		if (this.detectorThread != null) {
			this.detectorThread.interrupt();
			this.detector.stop();
		}
	}

	public void setOnCardboardTriggerListener(
			OnCardboardTriggerListener listener) {
		this.detector.setOnCardboardTriggerListener(listener, new Handler());
	}

	private static class VectorTriggerDetector extends
			MagnetSensor.TriggerDetector {
		private static final long NS_REFRESH_TIME = 350000000L;
		private static final long NS_THROWAWAY_SIZE = 500000000L;
		private static final long NS_WAIT_SIZE = 100000000L;
		private long lastFiring = 0L;
		private static int xThreshold;
		private static int yThreshold;
		private static int zThreshold;
		private ArrayList<float[]> sensorData;
		private ArrayList<Long> sensorTimes;

		public VectorTriggerDetector(Context context) {
			super(context);
			this.sensorData = new ArrayList();
			this.sensorTimes = new ArrayList();

			xThreshold = -3;
			yThreshold = 15;
			zThreshold = 6;
		}

		public VectorTriggerDetector(Context context, int xThreshold,
				int yThreshold, int zThreshold) {
			super(context);
			this.sensorData = new ArrayList();
			this.sensorTimes = new ArrayList();

			xThreshold = xThreshold;
			yThreshold = yThreshold;
			zThreshold = zThreshold;
		}

		private void addData(float[] values, long time) {
			this.sensorData.add(values);
			this.sensorTimes.add(Long.valueOf(time));
			while (((Long) this.sensorTimes.get(0)).longValue() < time - 500000000L) {
				this.sensorData.remove(0);
				this.sensorTimes.remove(0);
			}

			evaluateModel(time);
		}

		private void evaluateModel(long time) {
			if ((time - this.lastFiring < 350000000L)
					|| (this.sensorData.size() < 2)) {
				return;
			}

			int baseIndex = 0;
			for (int i = 1; i < this.sensorTimes.size(); i++) {
				if (time - ((Long) this.sensorTimes.get(i)).longValue() < 100000000L) {
					baseIndex = i;
					break;
				}

			}

			float[] oldValues = (float[]) this.sensorData.get(baseIndex);
			float[] currentValues = (float[]) this.sensorData
					.get(this.sensorData.size() - 1);
			if ((currentValues[0] - oldValues[0] < xThreshold)
					&& (currentValues[1] - oldValues[1] > yThreshold)
					&& (currentValues[2] - oldValues[2] > zThreshold)) {
				this.lastFiring = time;
				handleButtonPressed();
			}
		}

		public void onSensorChanged(SensorEvent event) {
			if (event.sensor.equals(this.magnetometer)) {
				float[] values = event.values;

				if ((values[0] == 0.0F) && (values[1] == 0.0F)
						&& (values[2] == 0.0F)) {
					return;
				}
				addData((float[]) event.values.clone(), event.timestamp);
			}
		}

		public void onAccuracyChanged(Sensor sensor, int accuracy) {
		}
	}

	private static class ThresholdTriggerDetector extends
			MagnetSensor.TriggerDetector {
		private static final long NS_SEGMENT_SIZE = 200000000L;
		private static final long NS_WINDOW_SIZE = 400000000L;
		private static final long NS_WAIT_TIME = 350000000L;
		private long lastFiring = 0L;
		private static int t1 = 30;
		private static int t2 = 60;
		private ArrayList<float[]> sensorData;
		private ArrayList<Long> sensorTimes;

		public ThresholdTriggerDetector(Context context) {
			super(context);
			this.sensorData = new ArrayList();
			this.sensorTimes = new ArrayList();
		}

		public ThresholdTriggerDetector(Context context, int t1, int t2) {
			super(context);
			this.sensorData = new ArrayList();
			this.sensorTimes = new ArrayList();

			t1 = t1;
			t2 = t2;
		}

		private void addData(float[] values, long time) {
			this.sensorData.add(values);
			this.sensorTimes.add(Long.valueOf(time));
			while (((Long) this.sensorTimes.get(0)).longValue() < time - 400000000L) {
				this.sensorData.remove(0);
				this.sensorTimes.remove(0);
			}

			evaluateModel(time);
		}

		private void evaluateModel(long time) {
			if ((time - this.lastFiring < 350000000L)
					|| (this.sensorData.size() < 2)) {
				return;
			}

			float[] baseline = (float[]) this.sensorData.get(this.sensorData
					.size() - 1);

			int startSecondSegment = 0;
			for (int i = 0; i < this.sensorTimes.size(); i++) {
				if (time - ((Long) this.sensorTimes.get(i)).longValue() < 200000000L) {
					startSecondSegment = i;
					break;
				}
			}

			float[] offsets = new float[this.sensorData.size()];
			computeOffsets(offsets, baseline);
			float min1 = computeMinimum(Arrays.copyOfRange(offsets, 0,
					startSecondSegment));
			float max2 = computeMaximum(Arrays.copyOfRange(offsets,
					startSecondSegment, this.sensorData.size()));

			if ((min1 < t1) && (max2 > t2)) {
				this.lastFiring = time;
				handleButtonPressed();
			}
		}

		private void computeOffsets(float[] offsets, float[] baseline) {
			for (int i = 0; i < this.sensorData.size(); i++) {
				float[] point = (float[]) this.sensorData.get(i);
				float[] o = { point[0] - baseline[0], point[1] - baseline[1],
						point[2] - baseline[2] };

				float magnitude = (float) Math.sqrt(o[0] * o[0] + o[1] * o[1]
						+ o[2] * o[2]);
				offsets[i] = magnitude;
			}
		}

		private float computeMaximum(float[] offsets) {
			float max = (1.0F / -1.0F);
			for (float o : offsets) {
				max = Math.max(o, max);
			}
			return max;
		}

		private float computeMinimum(float[] offsets) {
			float min = (1.0F / 1.0F);
			for (float o : offsets) {
				min = Math.min(o, min);
			}
			return min;
		}

		public void onSensorChanged(SensorEvent event) {
			if (event.sensor.equals(this.magnetometer)) {
				float[] values = event.values;

				if ((values[0] == 0.0F) && (values[1] == 0.0F)
						&& (values[2] == 0.0F)) {
					return;
				}
				addData((float[]) event.values.clone(), event.timestamp);
			}
		}

		public void onAccuracyChanged(Sensor sensor, int accuracy) {
		}
	}

	private static abstract class TriggerDetector implements Runnable,
			SensorEventListener {
		protected static final String TAG = "TriggerDetector";
		protected SensorManager sensorManager;
		protected Sensor magnetometer;
		protected MagnetSensor.OnCardboardTriggerListener listener;
		protected Handler handler;
		private Looper looper;

		public TriggerDetector(Context context) {
			this.sensorManager = ((SensorManager) context
					.getSystemService("sensor"));
			this.magnetometer = this.sensorManager.getDefaultSensor(2);
		}

		public synchronized void setOnCardboardTriggerListener(
				MagnetSensor.OnCardboardTriggerListener listener,
				Handler handler) {
			this.listener = listener;
			this.handler = handler;
		}

		protected void handleButtonPressed() {
			synchronized (this) {
				if (this.listener != null)
					this.handler.post(new Runnable() {
						public void run() {
							if (MagnetSensor.TriggerDetector.this.listener != null)
								MagnetSensor.TriggerDetector.this.listener
										.onCardboardTrigger();
						}
					});
			}
		}

		public void run() {
			Looper.prepare();
			this.looper = Looper.myLooper();
			this.sensorManager.registerListener(this, this.magnetometer, 0);

			Looper.loop();
		}

		public void stop() {
			this.sensorManager.unregisterListener(this);

			this.looper.quit();
		}

		public void onSensorChanged(SensorEvent event) {
		}

		public void onAccuracyChanged(Sensor sensor, int accuracy) {
		}
	}

	public static abstract interface OnCardboardTriggerListener {
		public abstract void onCardboardTrigger();
	}
}