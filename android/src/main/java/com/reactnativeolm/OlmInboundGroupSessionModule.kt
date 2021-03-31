package com.reactnativeolm

import com.facebook.react.bridge.*
import org.matrix.olm.OlmException
import org.matrix.olm.OlmInboundGroupSession
import org.matrix.olm.OlmMessage

class OlmInboundGroupSessionModule(reactContext: ReactApplicationContext) : ReactContextBaseJavaModule(reactContext) {
  var olmInboundGroupSession: OlmInboundGroupSession? = null;

  override fun getName(): String {
    return "OlmInboundGroupSession"
  }


  @ReactMethod(isBlockingSynchronousMethod = true)
  fun free() {
    this.olmInboundGroupSession?.releaseSession()
  }

  @ReactMethod(isBlockingSynchronousMethod = true)
  fun pickle(key: String): String? {
    return SerializationUtils.serializeForRealm(this.olmInboundGroupSession)
  }

  @ReactMethod(isBlockingSynchronousMethod = true)
  fun unpickle(key: String, pickle: String) {
    this.olmInboundGroupSession = SerializationUtils.deserializeFromRealm(pickle)
  }

  @ReactMethod(isBlockingSynchronousMethod = true)
  fun create(session_key: String) {
    this.olmInboundGroupSession = OlmInboundGroupSession(session_key)
  }

  @ReactMethod(isBlockingSynchronousMethod = true)
  fun import_session(session_key: String): WritableMap {
    val map = WritableNativeMap()

    try {
      this.olmInboundGroupSession = OlmInboundGroupSession.importSession(session_key)
    } catch (e: OlmException) {
      map.putBoolean("error", true)
      map.putString("errorMessage", e.localizedMessage)
    }

    return map
  }

  @ReactMethod(isBlockingSynchronousMethod = true)
  fun decrypt(message: String): WritableMap {
    val map = WritableNativeMap()

    try {
      val olmMessage = this.olmInboundGroupSession?.decryptMessage(message)
      if (olmMessage !== null) {
        map.putString("plaintext", olmMessage.mDecryptedMessage)
        map.putInt("message_index", olmMessage.mIndex.toInt())
      }
    } catch (e: OlmException) {
      map.putBoolean("error", true)
      map.putString("errorMessage", e.localizedMessage)
    }

    return map
  }

  @ReactMethod(isBlockingSynchronousMethod = true)
  fun session_id(): WritableMap {
    val map = WritableNativeMap()

    try {
      val result = this.olmInboundGroupSession?.sessionIdentifier()
      map.putString("result", result)
    } catch (e: OlmException) {
      map.putBoolean("error", true)
      map.putString("errorMessage", e.localizedMessage)
    }

    return map
  }

  @ReactMethod(isBlockingSynchronousMethod = true)
  fun first_known_index(): WritableMap {
    val map = WritableNativeMap()

    try {
      val result = this.olmInboundGroupSession?.firstKnownIndex?.toInt()
      if (result !== null) {
        map.putInt("result", result)
      }
    } catch (e: OlmException) {
      map.putBoolean("error", true)
      map.putString("errorMessage", e.localizedMessage)
    }

    return map
  }

  @ReactMethod(isBlockingSynchronousMethod = true)
  fun export_session(message_index: Int): WritableMap {
    val map = WritableNativeMap()

    try {
      val result = this.olmInboundGroupSession?.export(message_index.toLong())
      if (result !== null) {
        map.putString("result", result)
      }
    } catch (e: OlmException) {
      map.putBoolean("error", true)
      map.putString("errorMessage", e.localizedMessage)
    }

    return map
  }
}
