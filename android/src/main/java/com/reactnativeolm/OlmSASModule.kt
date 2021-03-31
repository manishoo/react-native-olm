package com.reactnativeolm

import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod
import com.facebook.react.bridge.WritableArray
import org.matrix.olm.OlmSAS

class OlmSASModule(reactContext: ReactApplicationContext) : ReactContextBaseJavaModule(reactContext) {
  var olmSAS: OlmSAS? = null;

  override fun getName(): String {
    return "OlmSAS"
  }

  @ReactMethod(isBlockingSynchronousMethod = true)
  fun create() {
    this.olmSAS = OlmSAS();
  }

  @ReactMethod(isBlockingSynchronousMethod = true)
  fun free() {
    this.olmSAS?.releaseSas()
  }

  @ReactMethod(isBlockingSynchronousMethod = true)
  fun get_pubkey(): String? {
    return this.olmSAS?.publicKey
  }

  @ReactMethod(isBlockingSynchronousMethod = true)
  fun set_their_key(their_key: String) {
    this.olmSAS?.setTheirPublicKey(their_key)
  }

  @ReactMethod(isBlockingSynchronousMethod = true)
  fun generate_bytes(info: String, length: Int): WritableArray {
    return ConversionUtils.convertByteArrayToArray(this.olmSAS?.generateShortCode(info, length)!!)
  }

  @ReactMethod(isBlockingSynchronousMethod = true)
  fun calculate_mac(input: String, info: String): String? {
    return this.olmSAS?.calculateMac(input, info)
  }

  @ReactMethod(isBlockingSynchronousMethod = true)
  fun calculate_mac_long_kdf(input: String, info: String): String? {
    return this.olmSAS?.calculateMacLongKdf(input, info)
  }
}
