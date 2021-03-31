package com.reactnativeolm

import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod
import org.matrix.olm.OlmUtility
import org.matrix.olm.OlmException

class OlmUtilityModule(reactContext: ReactApplicationContext) : ReactContextBaseJavaModule(reactContext) {
  var olmUtility: OlmUtility? = null;

  override fun getName(): String {
    return "OlmUtility"
  }

  @ReactMethod(isBlockingSynchronousMethod = true)
  fun create() {
    this.olmUtility = OlmUtility();
  }

  @ReactMethod(isBlockingSynchronousMethod = true)
  fun free() {
    this.olmUtility?.releaseUtility()
  }

  @ReactMethod(isBlockingSynchronousMethod = true)
  fun sha256(message: String): String? {
    return this.olmUtility?.sha256(message)
  }

  @ReactMethod(isBlockingSynchronousMethod = true)
  fun ed25519_verify(key: String, message: String, signature: String): Boolean {
    try {
      this.olmUtility?.verifyEd25519Signature(signature, key, message)
    } catch (e: OlmException) {
      return false
    }

    return true
  }
}
