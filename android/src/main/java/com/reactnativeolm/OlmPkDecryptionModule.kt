package com.reactnativeolm

import com.facebook.react.bridge.*
import org.json.JSONArray
import org.json.JSONException
import org.matrix.olm.OlmException
import org.matrix.olm.OlmPkDecryption
import org.matrix.olm.OlmPkMessage

class OlmPkDecryptionModule(reactContext: ReactApplicationContext) : ReactContextBaseJavaModule(reactContext) {
  var olmPkDecryption: OlmPkDecryption? = null;

  override fun getName(): String {
    return "OlmPkDecryption"
  }

  @ReactMethod(isBlockingSynchronousMethod = true)
  fun create() {
    this.olmPkDecryption = OlmPkDecryption()
  }

  @ReactMethod(isBlockingSynchronousMethod = true)
  fun free() {
    this.olmPkDecryption?.releaseDecryption()
  }

  @ReactMethod(isBlockingSynchronousMethod = true)
  fun init_with_private_key(key: ReadableArray): String? {
    return this.olmPkDecryption?.setPrivateKey(ConversionUtils.convertArrayToByteArray(key))
  }

  @ReactMethod(isBlockingSynchronousMethod = true)
  fun generate_key(): String? {
    return this.olmPkDecryption?.generateKey()
  }

  @ReactMethod(isBlockingSynchronousMethod = true)
  fun get_private_key(): WritableArray {
    val array = WritableNativeArray()
    val privateKeyArray = this.olmPkDecryption?.privateKey()
    privateKeyArray?.forEach { byte ->
      array.pushInt(byte.toInt())
    }

    return array
  }

  @ReactMethod(isBlockingSynchronousMethod = true)
  fun pickle(key: String): String? {
    return SerializationUtils.serializeForRealm(this.olmPkDecryption)
  }

  @ReactMethod(isBlockingSynchronousMethod = true)
  fun unpickle(key: String, pickle: String) {
    this.olmPkDecryption = SerializationUtils.deserializeFromRealm(pickle)
  }

  @ReactMethod(isBlockingSynchronousMethod = true)
  fun decrypt(ephemeral_key: String?, mac: String?, ciphertext: String?): WritableMap? {
    val map = WritableNativeMap();

    try {
      val olmPkMessage = OlmPkMessage()
      olmPkMessage.mCipherText = ciphertext
      olmPkMessage.mMac = mac
      olmPkMessage.mEphemeralKey = ephemeral_key

      val result = this.olmPkDecryption?.decrypt(olmPkMessage)
      map.putString("result", result)
    } catch (e: OlmException) {
      map.putBoolean("error", true)
      map.putString("errorMessage", e.localizedMessage)
    }

    return map
  }

  @ReactMethod(isBlockingSynchronousMethod = true)
  fun PRIVATE_KEY_LENGTH(): Int {
    return OlmPkDecryption.privateKeyLength()
  }
}
