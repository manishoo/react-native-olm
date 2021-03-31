package com.reactnativeolm

import com.facebook.react.bridge.*
import org.matrix.olm.OlmPkSigning

class OlmPkSigningModule(reactContext: ReactApplicationContext) : ReactContextBaseJavaModule(reactContext) {
  var olmPkSigning: OlmPkSigning? = null;

  override fun getName(): String {
    return "OlmPkSigning"
  }

  @ReactMethod(isBlockingSynchronousMethod = true)
  fun create() {
    this.olmPkSigning = OlmPkSigning();
  }

  @ReactMethod(isBlockingSynchronousMethod = true)
  fun free() {
    this.olmPkSigning?.releaseSigning()
  }

  @ReactMethod(isBlockingSynchronousMethod = true)
  fun init_with_seed(seed: ReadableArray): String? {
    return this.olmPkSigning?.initWithSeed(ConversionUtils.convertArrayToByteArray(seed))
  }

  @ReactMethod(isBlockingSynchronousMethod = true)
  fun generate_seed(): WritableArray {
    return ConversionUtils.convertByteArrayToArray(OlmPkSigning.generateSeed())
  }

  @ReactMethod(isBlockingSynchronousMethod = true)
  fun sign(message: String): String? {
    return this.olmPkSigning?.sign(message)
  }
}
