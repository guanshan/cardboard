package com.wormhole.vr.cardboard;

import android.view.Choreographer;

public class DisplaySynchronizer implements Choreographer.FrameCallback {
	private static final long FRAME_TIME_NS = 16666666L;
	private Choreographer choreographer;
	private final long nativeDisplaySynchronizer;

	public DisplaySynchronizer() {
		this.nativeDisplaySynchronizer = nativeInit(FRAME_TIME_NS);
		this.choreographer = Choreographer.getInstance();
		this.choreographer.postFrameCallback(this);
	}

	protected void finalize() throws Throwable {
		try {
			nativeDestroy(this.nativeDisplaySynchronizer);

			super.finalize();
		} finally {
			super.finalize();
		}
	}

	public long retainNativeDisplaySynchronizer() {
		return nativeRetainNativeDisplaySynchronizer(this.nativeDisplaySynchronizer);
	}

	public void doFrame(long vsync) {
		nativeAddSyncTime(this.nativeDisplaySynchronizer, vsync);
		this.choreographer.postFrameCallback(this);
	}

	public long syncToNextVsync() {
		return nativeSyncToNextVsync(this.nativeDisplaySynchronizer);
	}

	private native long nativeInit(long paramLong);

	private native void nativeDestroy(long paramLong);

	private native void nativeAddSyncTime(long paramLong1, long paramLong2);

	private native long nativeSyncToNextVsync(long paramLong);

	private native long nativeRetainNativeDisplaySynchronizer(long paramLong);
}