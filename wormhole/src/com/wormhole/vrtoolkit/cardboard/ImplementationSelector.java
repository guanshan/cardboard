package com.wormhole.vrtoolkit.cardboard;

import android.content.Context;
import android.opengl.GLSurfaceView;

public class ImplementationSelector {
	public static CardboardViewApi createCardboardViewApi(Context context,
			GLSurfaceView view) {
		return new CardboardViewNativeImpl(context, view);
	}
}