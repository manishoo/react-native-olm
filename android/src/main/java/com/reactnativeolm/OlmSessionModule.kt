package com.reactnativeolm

import com.facebook.react.bridge.*
import org.matrix.olm.OlmException
import org.matrix.olm.OlmMessage
import org.matrix.olm.OlmSession

class OlmSessionModule(private val reactContext: ReactApplicationContext) : ReactContextBaseJavaModule(reactContext) {
  var olmSession: OlmSession? = null;

  override fun getName(): String {
    return "OlmSession"
  }

  @ReactMethod(isBlockingSynchronousMethod = true)
  fun create() {
    this.olmSession = OlmSession()
  }

  @ReactMethod(isBlockingSynchronousMethod = true)
  fun free() {
    this.olmSession?.releaseSession()
  }

  @ReactMethod(isBlockingSynchronousMethod = true)
  fun pickle(key: String): String? {
    return SerializationUtils.serializeForRealm(this.olmSession)
  }

  @ReactMethod(isBlockingSynchronousMethod = true)
  fun unpickle(key: String, pickle: String) {
    this.olmSession = SerializationUtils.deserializeFromRealm(pickle)
  }

  @ReactMethod(isBlockingSynchronousMethod = true)
  fun create_outbound(
    _account: ReadableMap,
    their_identity_key: String,
    their_one_time_key: String
  ) {
    this.olmSession?.initOutboundSession(reactContext.getNativeModule(OlmAccountModule::class.java)?.getAccount(), their_identity_key, their_one_time_key)
  }

  @ReactMethod(isBlockingSynchronousMethod = true)
  fun create_inbound(_account: ReadableMap, one_time_key_message: String) {
    this.olmSession?.initInboundSession(reactContext.getNativeModule(OlmAccountModule::class.java)?.getAccount(), one_time_key_message)
  }

  @ReactMethod(isBlockingSynchronousMethod = true)
  fun create_inbound_from(
    _account: ReadableMap,
    identity_key: String,
    one_time_key_message: String
  ) {
    this.olmSession?.initInboundSessionFrom(reactContext.getNativeModule(OlmAccountModule::class.java)?.getAccount(), identity_key, one_time_key_message)
  }

  @ReactMethod(isBlockingSynchronousMethod = true)
  fun session_id(): String? {
    return this.olmSession?.sessionIdentifier()
  }

  @ReactMethod(isBlockingSynchronousMethod = true)
  fun has_received_message(): Boolean? {
    // TODO
    return null
  }

  @ReactMethod(isBlockingSynchronousMethod = true)
  fun matches_inbound(one_time_key_message: String): Boolean? {
    return this.olmSession?.matchesInboundSession(one_time_key_message)
  }

  @ReactMethod(isBlockingSynchronousMethod = true)
  fun matches_inbound_from(identity_key: String, one_time_key_message: String): Boolean? {
    return this.olmSession?.matchesInboundSessionFrom(identity_key, one_time_key_message)
  }

  @ReactMethod(isBlockingSynchronousMethod = true)
  fun encrypt(plaintext: String): WritableMap? {
    val olmMessage: OlmMessage?
    val map = WritableNativeMap();

    try {
      olmMessage = this.olmSession?.encryptMessage(plaintext)
      if (olmMessage != null) {
        map.putString("mCipherText", olmMessage.mCipherText)
        map.putInt("mType", olmMessage.mType.toInt())
      }
    } catch (e: OlmException) {
      map.putBoolean("error", true)
      map.putString("errorMessage", e.localizedMessage)
      return map
    }

    return map
  }

  @ReactMethod(isBlockingSynchronousMethod = true)
  fun decrypt(message_type: Int, message: String): WritableMap {
    val map = WritableNativeMap();

    val olmMessage = OlmMessage()
    olmMessage.mCipherText = message
    olmMessage.mType = message_type.toLong()

    try {
      val result = this.olmSession?.decryptMessage(olmMessage)
      map.putString("result", result)
    } catch (e: OlmException) {
      map.putBoolean("error", true)
      map.putString("errorMessage", e.localizedMessage)
    }

    return map
  }

  @ReactMethod(isBlockingSynchronousMethod = true)
  fun describe(): String {
    return "NOT IMPLEMENTED"
  }
}
