package com.wormhole.vrtoolkit.cardboard;

import java.lang.annotation.Target;

@Target({ java.lang.annotation.ElementType.METHOD,
		java.lang.annotation.ElementType.FIELD,
		java.lang.annotation.ElementType.TYPE,
		java.lang.annotation.ElementType.CONSTRUCTOR })
public @interface UsedByNative {
}