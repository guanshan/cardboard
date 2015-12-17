package com.wormhole.vrtoolkit.cardboard;

import android.content.Context;
import android.os.Process;

public class PermissionUtils {
	public static boolean hasPermission(Context context, String permission) {
		if ((context == null) || (permission == null)) {
			return false;
		}
		return context.checkPermission(permission, Process.myPid(),
				Process.myUid()) == 0;
	}
}