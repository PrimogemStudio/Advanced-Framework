package com.primogemstudio.advancedfmk.interfaces;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * <h3>The target of the annotation is accessed in native code, don't rename it.</h3>
 */
@Retention(RetentionPolicy.SOURCE)
public @interface AccessFromNative {
}
