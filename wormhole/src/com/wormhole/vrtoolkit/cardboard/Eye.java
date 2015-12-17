package com.wormhole.vrtoolkit.cardboard;

@UsedByNative
public class Eye {
	private final int type;
	private final float[] eyeView;
	private final Viewport viewport;
	private final FieldOfView fov;
	private volatile boolean projectionChanged;
	private float[] perspective;
	private float lastZNear;
	private float lastZFar;

	public Eye(int type) {
		this.type = type;
		this.eyeView = new float[16];
		this.viewport = new Viewport();
		this.fov = new FieldOfView();
		this.projectionChanged = true;
	}

	public int getType() {
		return this.type;
	}

	@UsedByNative
	public float[] getEyeView() {
		return this.eyeView;
	}

	public float[] getPerspective(float zNear, float zFar) {
		if ((!this.projectionChanged) && (this.lastZNear == zNear)
				&& (this.lastZFar == zFar)) {
			return this.perspective;
		}

		if (this.perspective == null) {
			this.perspective = new float[16];
		}

		getFov().toPerspectiveMatrix(zNear, zFar, this.perspective, 0);

		this.lastZNear = zNear;
		this.lastZFar = zFar;
		this.projectionChanged = false;

		return this.perspective;
	}

	public Viewport getViewport() {
		return this.viewport;
	}

	public FieldOfView getFov() {
		return this.fov;
	}

	public void setProjectionChanged() {
		this.projectionChanged = true;
	}

	public boolean getProjectionChanged() {
		return this.projectionChanged;
	}

	@UsedByNative
	private void setValues(int viewportX, int viewportY, int viewportWidth,
			int viewportHeight, float fovLeft, float fovRight, float fovBottom,
			float fovTop) {
		this.viewport.setViewport(viewportX, viewportY, viewportWidth,
				viewportHeight);
		this.fov.setAngles(fovLeft, fovRight, fovBottom, fovTop);
		this.projectionChanged = true;
	}

	public static abstract class Type {
		public static final int MONOCULAR = 0;
		public static final int LEFT = 1;
		public static final int RIGHT = 2;
	}
}