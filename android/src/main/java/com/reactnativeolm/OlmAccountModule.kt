package com.reactnativeolm

import com.facebook.react.bridge.*
import com.facebook.react.module.annotations.ReactModule
import org.json.JSONObject
import org.matrix.olm.OlmAccount

@ReactModule(name = "OlmAccount")
class OlmAccountModule(reactContext: ReactApplicationContext) : ReactContextBaseJavaModule(reactContext) {
  private var account: OlmAccount? = null;

  override fun getName(): String {
    return "OlmAccount"
  }

  fun getAccount(): OlmAccount? {
    return this.account
  }

  @ReactMethod(isBlockingSynchronousMethod = true)
  fun free() {
    this.account?.releaseAccount();
  }

  @ReactMethod(isBlockingSynchronousMethod = true)
  fun create() {
    this.account = OlmAccount()
  }

  @ReactMethod(isBlockingSynchronousMethod = true)
  fun identity_keys(): String? {
    val identityKeys = this.account?.identityKeys();

    if (identityKeys === null) {
      return null
    }

    return JSONObject(this.account?.identityKeys() as Map<*, *>).toString()
  }

  @ReactMethod(isBlockingSynchronousMethod = true)
  fun sign(msg: String): String? {
    return this.account?.signMessage(msg)
  }

  @ReactMethod(isBlockingSynchronousMethod = true)
  fun one_time_keys(): String? {
    val oneTimeKeys = this.account?.oneTimeKeys()

    if (oneTimeKeys === null) {
      return null
    }

    return JSONObject(this.account?.oneTimeKeys() as Map<*, *>).toString()
  }

  @ReactMethod(isBlockingSynchronousMethod = true)
  fun mark_keys_as_published() {
    this.account?.markOneTimeKeysAsPublished()
  }

  @ReactMethod(isBlockingSynchronousMethod = true)
  fun max_number_of_one_time_keys(): Int? {
    return this.account?.maxOneTimeKeys()?.toInt()
  }

  @ReactMethod(isBlockingSynchronousMethod = true)
  fun generate_one_time_keys(numberOfKeys: Int) {
    this.account?.generateOneTimeKeys(numberOfKeys)
  }

  // TODO: We have to change olm/android's removeOneTimeKeys method
  //  to only receive a sessionId rather than a Session instance
  @ReactMethod(isBlockingSynchronousMethod = true)
  fun remove_one_time_keys(sessionId: String): Boolean? {
    return null
  }

  // TODO: this is not implemented in olm/android.
  //  Maybe it's okay to leave it out?
  @ReactMethod(isBlockingSynchronousMethod = true)
  fun generate_fallback_key(): String? {
    return null
  }

  // TODO: this is not implemented in olm/android.
  //  Maybe it's okay to leave it out?
  @ReactMethod(isBlockingSynchronousMethod = true)
  fun fallback_key(): String? {
    return null
  }

  @ReactMethod(isBlockingSynchronousMethod = true)
  fun pickle(key: String?): String? {
    return SerializationUtils.serializeForRealm(this.account)
  }

  @ReactMethod(isBlockingSynchronousMethod = true)
  fun unpickle(key: String, pickle: String) {
    this.account = SerializationUtils.deserializeFromRealm(pickle)
  }
}
