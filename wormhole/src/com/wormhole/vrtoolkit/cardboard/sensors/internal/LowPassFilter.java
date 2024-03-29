package com.wormhole.vrtoolkit.cardboard.sensors.internal;

import java.util.concurrent.TimeUnit;

public class LowPassFilter {
	private static final double NANOS_TO_SECONDS = 1.0D / TimeUnit.NANOSECONDS
			.convert(1L, TimeUnit.SECONDS);
	private final double timeConstantSecs;
	private final Vector3d filteredData = new Vector3d();
	private long lastTimestampNs;
	private int numSamples;
	private final Vector3d temp = new Vector3d();

	public LowPassFilter(double cutoffFrequency) {
		this.timeConstantSecs = (1.0D / (6.283185307179586D * cutoffFrequency));
	}

	public int getNumSamples() {
		return this.numSamples;
	}

	public void addSample(Vector3d sampleData, long timestampNs) {
		addWeightedSample(sampleData, timestampNs, 1.0D);
	}

	public void addWeightedSample(Vector3d sampleData, long timestampNs,
			double weight) {
		this.numSamples += 1;
		if (this.numSamples == 1) {
			this.filteredData.set(sampleData);
			this.lastTimestampNs = timestampNs;
			return;
		}

		double weightedDeltaSecs = weight
				* (timestampNs - this.lastTimestampNs) * NANOS_TO_SECONDS;

		double alpha = weightedDeltaSecs
				/ (this.timeConstantSecs + weightedDeltaSecs);
		this.filteredData.scale(1.0D - alpha);
		this.temp.set(sampleData);
		this.temp.scale(alpha);
		Vector3d.add(this.temp, this.filteredData, this.filteredData);
		this.lastTimestampNs = timestampNs;
	}

	public Vector3d getFilteredData() {
		return this.filteredData;
	}
}