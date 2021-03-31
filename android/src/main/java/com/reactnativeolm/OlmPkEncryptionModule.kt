package com.reactnativeolm

import com.facebook.react.bridge.*
import org.matrix.olm.OlmPkEncryption

class OlmPkEncryptionModule(reactContext: ReactApplicationContext) : ReactContextBaseJavaModule(reactContext) {
  var olmPkEncryption: OlmPkEncryption? = null;

  override fun getName(): String {
    return "OlmPkEncryption"
  }

  @ReactMethod(isBlockingSynchronousMethod = true)
  fun create() {
    this.olmPkEncryption = OlmPkEncryption();
  }

  @ReactMethod(isBlockingSynchronousMethod = true)
  fun free() {
    this.olmPkEncryption?.releaseEncryption()
  }

  @ReactMethod(isBlockingSynchronousMethod = true)
  fun set_recipient_key(key: String) {
    this.olmPkEncryption?.setRecipientKey(key)
  }

  @ReactMethod(isBlockingSynchronousMethod = true)
  fun encrypt(plaintext: String): WritableMap {
    val olmPkMessage = this.olmPkEncryption?.encrypt(plaintext)
    val map = WritableNativeMap()
    if (olmPkMessage != null) {
      map.putString("ciphertext", olmPkMessage.mCipherText)
      map.putString("mac", olmPkMessage.mMac)
      map.putString("ephemeral_key", olmPkMessage.mEphemeralKey)
    }

    return map
  }
}
