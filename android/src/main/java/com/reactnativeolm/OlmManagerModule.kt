package com.reactnativeolm

import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod
import com.facebook.react.bridge.Promise
import org.matrix.olm.OlmManager

class OlmManagerModule(reactContext: ReactApplicationContext) : ReactContextBaseJavaModule(reactContext) {
  var olmManager: OlmManager? = null;

  override fun getName(): String {
    return "OlmManager"
  }

  @ReactMethod(isBlockingSynchronousMethod = true)
  fun init(): Boolean {
    this.olmManager = OlmManager();
    return true;
  }

  @ReactMethod(isBlockingSynchronousMethod = true)
  fun getOlmLibVersion(): String? {
    return this.olmManager?.olmLibVersion
  }
}
