package com.reactnativeolm

import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod
import com.facebook.react.bridge.WritableNativeMap
import org.matrix.olm.OlmOutboundGroupSession

class OlmOutboundGroupSessionModule(reactContext: ReactApplicationContext) : ReactContextBaseJavaModule(reactContext) {
  var olmOutboundGroupSession: OlmOutboundGroupSession? = null;

  override fun getName(): String {
    return "OlmOutboundGroupSession"
  }


  @ReactMethod(isBlockingSynchronousMethod = true)
  fun free() {
    this.olmOutboundGroupSession?.releaseSession()
  }

  @ReactMethod(isBlockingSynchronousMethod = true)
  fun pickle(key: String): String? {
    return SerializationUtils.serializeForRealm(this.olmOutboundGroupSession)
  }

  @ReactMethod(isBlockingSynchronousMethod = true)
  fun unpickle(key: String, pickle: String) {
    this.olmOutboundGroupSession = SerializationUtils.deserializeFromRealm(pickle)
  }

  @ReactMethod(isBlockingSynchronousMethod = true)
  fun create() {
    this.olmOutboundGroupSession = OlmOutboundGroupSession()
  }

  @ReactMethod(isBlockingSynchronousMethod = true)
  fun encrypt(plaintext: String): String? {
    return this.olmOutboundGroupSession?.encryptMessage(plaintext)
  }

  @ReactMethod(isBlockingSynchronousMethod = true)
  fun session_id(): String? {
    return this.olmOutboundGroupSession?.sessionIdentifier()
  }

  @ReactMethod(isBlockingSynchronousMethod = true)
  fun session_key(): String? {
    return this.olmOutboundGroupSession?.sessionKey()
  }

  @ReactMethod(isBlockingSynchronousMethod = true)
  fun message_index(): Int? {
    return this.olmOutboundGroupSession?.messageIndex()
  }
}
