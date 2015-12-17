package com.wormhole.vrtoolkit.cardboard;

import java.util.Arrays;

public class Distortion {
	private static final float[] DEFAULT_COEFFICIENTS = { 0.441F, 0.156F };
	private float[] coefficients;

	public Distortion() {
		this.coefficients = ((float[]) DEFAULT_COEFFICIENTS.clone());
	}

	public Distortion(Distortion other) {
		setCoefficients(other.coefficients);
	}

	public static Distortion parseFromProtobuf(float[] coefficients) {
		Distortion distortion = new Distortion();
		distortion.setCoefficients(coefficients);
		return distortion;
	}

	public float[] toProtobuf() {
		return (float[]) this.coefficients.clone();
	}

	public void setCoefficients(float[] coefficients) {
		this.coefficients = (coefficients != null ? (float[]) coefficients
				.clone() : new float[0]);
	}

	public float[] getCoefficients() {
		return this.coefficients;
	}

	public float distortionFactor(float radius) {
		float result = 1.0F;
		float rFactor = 1.0F;
		float rSquared = radius * radius;

		for (float ki : this.coefficients) {
			rFactor *= rSquared;
			result += ki * rFactor;
		}

		return result;
	}

	public float distort(float radius) {
		return radius * distortionFactor(radius);
	}

	public float distortInverse(float radius) {
		float r0 = radius / 0.9F;
		float r1 = radius * 0.9F;
		float dr0 = radius - distort(r0);
		while (Math.abs(r1 - r0) > 0.0001D) {
			float dr1 = radius - distort(r1);
			float r2 = r1 - dr1 * ((r1 - r0) / (dr1 - dr0));
			r0 = r1;
			r1 = r2;
			dr0 = dr1;
		}
		return r1;
	}

	private static double[] solveLeastSquares(double[][] matA, double[] vecY) {
		int numSamples = matA.length;
		int numCoefficients = matA[0].length;

		double[][] matATA = new double[numCoefficients][numCoefficients];
		for (int k = 0; k < numCoefficients; k++) {
			for (int j = 0; j < numCoefficients; j++) {
				double sum = 0.0D;
				for (int i = 0; i < numSamples; i++) {
					sum += matA[i][j] * matA[i][k];
				}
				matATA[j][k] = sum;
			}

		}

		double[][] matInvATA = new double[numCoefficients][numCoefficients];

		if (numCoefficients != 2) {
			throw new RuntimeException(
					78
							+ "solveLeastSquares: only 2 coefficients currently supported, "
							+ numCoefficients + " given.");
		}

		double det = matATA[0][0] * matATA[1][1] - matATA[0][1] * matATA[1][0];
		matInvATA[0][0] = (matATA[1][1] / det);
		matInvATA[1][1] = (matATA[0][0] / det);
		matInvATA[0][1] = (-matATA[1][0] / det);
		matInvATA[1][0] = (-matATA[0][1] / det);

		double[] vecATY = new double[numCoefficients];
		for (int j = 0; j < numCoefficients; j++) {
			double sum = 0.0D;
			for (int i = 0; i < numSamples; i++) {
				sum += matA[i][j] * vecY[i];
			}
			vecATY[j] = sum;
		}

		double[] vecX = new double[numCoefficients];
		for (int j = 0; j < numCoefficients; j++) {
			double sum = 0.0D;
			for (int i = 0; i < numCoefficients; i++) {
				sum += matInvATA[i][j] * vecATY[i];
			}
			vecX[j] = sum;
		}

		return vecX;
	}

	public Distortion getApproximateInverseDistortion(float maxRadius) {
		int numSamples = 10;
		int numCoefficients = 2;

		double[][] matA = new double[10][2];
		double[] vecY = new double[10];

		for (int i = 0; i < 10; i++) {
			float r = maxRadius * (i + 1) / 10.0F;
			double rp = distort(r);
			double v = rp;
			for (int j = 0; j < 2; j++) {
				v *= rp * rp;
				matA[i][j] = v;
			}
			vecY[i] = (r - rp);
		}

		double[] vecK = solveLeastSquares(matA, vecY);

		float[] coefficients = new float[vecK.length];
		for (int i = 0; i < vecK.length; i++) {
			coefficients[i] = ((float) vecK[i]);
		}
		Distortion inverse = new Distortion();
		inverse.setCoefficients(coefficients);
		return inverse;
	}

	public boolean equals(Object other) {
		if (other == null) {
			return false;
		}

		if (other == this) {
			return true;
		}

		if (!(other instanceof Distortion)) {
			return false;
		}

		Distortion o = (Distortion) other;
		return Arrays.equals(this.coefficients, o.coefficients);
	}

	public String toString() {
		StringBuilder builder = new StringBuilder().append("{\n").append(
				"  coefficients: [");

		for (int i = 0; i < this.coefficients.length; i++) {
			builder.append(Float.toString(this.coefficients[i]));
			if (i < this.coefficients.length - 1) {
				builder.append(", ");
			}
		}

		builder.append("],\n}");
		return builder.toString();
	}
}