package com.reactnativeolm

import com.facebook.react.bridge.*
import org.json.JSONObject
import org.matrix.olm.*

class ReactNativeOlmModule(reactContext: ReactApplicationContext) : ReactContextBaseJavaModule(reactContext) {
  override fun getName(): String {
    return "ReactNativeOlm"
  }

  // =============================================>
  // Account
  // =============================================>
  private var account: OlmAccount? = null;

  @ReactMethod(isBlockingSynchronousMethod = true)
  fun freeAccount() {
    this.account?.releaseAccount();
  }

  @ReactMethod(isBlockingSynchronousMethod = true)
  fun createAccount() {
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
  fun signAccount(msg: String): String? {
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
  fun pickleAccount(key: String?): String? {
    return SerializationUtils.serializeForRealm(this.account)
  }

  @ReactMethod(isBlockingSynchronousMethod = true)
  fun unpickleAccount(key: String, pickle: String) {
    this.account = SerializationUtils.deserializeFromRealm(pickle)
  }


  // =============================================>
  // OlmInboundGroupSession
  // =============================================>
  var olmInboundGroupSession: OlmInboundGroupSession? = null;

  @ReactMethod(isBlockingSynchronousMethod = true)
  fun freeInboundGroupSession() {
    this.olmInboundGroupSession?.releaseSession()
  }

  @ReactMethod(isBlockingSynchronousMethod = true)
  fun pickleInboundGroupSession(key: String): String? {
    return SerializationUtils.serializeForRealm(this.olmInboundGroupSession)
  }

  @ReactMethod(isBlockingSynchronousMethod = true)
  fun unpickleInboundGroupSession(key: String, pickle: String) {
    this.olmInboundGroupSession = SerializationUtils.deserializeFromRealm(pickle)
  }

  @ReactMethod(isBlockingSynchronousMethod = true)
  fun createInboundGroupSession(session_key: String) {
    this.olmInboundGroupSession = OlmInboundGroupSession(session_key)
  }

  @ReactMethod(isBlockingSynchronousMethod = true)
  fun importInboundGroupSession(session_key: String): WritableMap {
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
  fun decryptInboundGroupSession(message: String): WritableMap {
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
  fun getInboundGroupSessionSessionId(): WritableMap {
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
  fun getInboundGroupSessionFirstKnownIndex(): WritableMap {
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
  fun exportInboundGroupSession(message_index: Int): WritableMap {
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

  // =============================================>
  // OlmManager
  // =============================================>
  var olmManager: OlmManager? = null;

  @ReactMethod(isBlockingSynchronousMethod = true)
  fun init(): Boolean {
    this.olmManager = OlmManager();
    return true;
  }

  @ReactMethod(isBlockingSynchronousMethod = true)
  fun getOlmLibVersion(): String? {
    return this.olmManager?.olmLibVersion
  }

  // =============================================>
  // OlmOutboundGroupSession
  // =============================================>
  var olmOutboundGroupSession: OlmOutboundGroupSession? = null;

  @ReactMethod(isBlockingSynchronousMethod = true)
  fun freeOutboundGroupSession() {
    this.olmOutboundGroupSession?.releaseSession()
  }

  @ReactMethod(isBlockingSynchronousMethod = true)
  fun pickleOutboundGroupSession(key: String): String? {
    return SerializationUtils.serializeForRealm(this.olmOutboundGroupSession)
  }

  @ReactMethod(isBlockingSynchronousMethod = true)
  fun unpickleOutboundGroupSession(key: String, pickle: String) {
    this.olmOutboundGroupSession = SerializationUtils.deserializeFromRealm(pickle)
  }

  @ReactMethod(isBlockingSynchronousMethod = true)
  fun createOutboundGroupSession() {
    this.olmOutboundGroupSession = OlmOutboundGroupSession()
  }

  @ReactMethod(isBlockingSynchronousMethod = true)
  fun encryptOutboundGroupSession(plaintext: String): String? {
    return this.olmOutboundGroupSession?.encryptMessage(plaintext)
  }

  @ReactMethod(isBlockingSynchronousMethod = true)
  fun getOutboundGroupSessionSessionId(): String? {
    return this.olmOutboundGroupSession?.sessionIdentifier()
  }

  @ReactMethod(isBlockingSynchronousMethod = true)
  fun getOutboundGroupSessionSessionKey(): String? {
    return this.olmOutboundGroupSession?.sessionKey()
  }

  @ReactMethod(isBlockingSynchronousMethod = true)
  fun getOutboundGroupSessionMessageIndex(): Int? {
    return this.olmOutboundGroupSession?.messageIndex()
  }

  // =============================================>
  // OlmPkDecryption
  // =============================================>
  var olmPkDecryption: OlmPkDecryption? = null;

  @ReactMethod(isBlockingSynchronousMethod = true)
  fun createPkDecryption() {
    this.olmPkDecryption = OlmPkDecryption()
  }

  @ReactMethod(isBlockingSynchronousMethod = true)
  fun freePkDecryption() {
    this.olmPkDecryption?.releaseDecryption()
  }

  @ReactMethod(isBlockingSynchronousMethod = true)
  fun initPkDecryptionWithPrivateKey(key: ReadableArray): String? {
    return this.olmPkDecryption?.setPrivateKey(ConversionUtils.convertArrayToByteArray(key))
  }

  @ReactMethod(isBlockingSynchronousMethod = true)
  fun generatePkDecryptionKey(): String? {
    return this.olmPkDecryption?.generateKey()
  }

  @ReactMethod(isBlockingSynchronousMethod = true)
  fun getPkDecryptionPrivateKey(): WritableArray {
    val array = WritableNativeArray()
    val privateKeyArray = this.olmPkDecryption?.privateKey()
    privateKeyArray?.forEach { byte ->
      array.pushInt(byte.toInt())
    }

    return array
  }

  @ReactMethod(isBlockingSynchronousMethod = true)
  fun picklePkDecryption(key: String): String? {
    return SerializationUtils.serializeForRealm(this.olmPkDecryption)
  }

  @ReactMethod(isBlockingSynchronousMethod = true)
  fun unpicklePkDecryption(key: String, pickle: String) {
    this.olmPkDecryption = SerializationUtils.deserializeFromRealm(pickle)
  }

  @ReactMethod(isBlockingSynchronousMethod = true)
  fun decryptPkDecryption(ephemeral_key: String?, mac: String?, ciphertext: String?): WritableMap? {
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

  // =============================================>
  // OlmPkDecryption
  // =============================================>
  var olmPkEncryption: OlmPkEncryption? = null;

  @ReactMethod(isBlockingSynchronousMethod = true)
  fun createPkEncryption() {
    this.olmPkEncryption = OlmPkEncryption();
  }

  @ReactMethod(isBlockingSynchronousMethod = true)
  fun freePkEncryption() {
    this.olmPkEncryption?.releaseEncryption()
  }

  @ReactMethod(isBlockingSynchronousMethod = true)
  fun setPkEncryptionRecipientKey(key: String) {
    this.olmPkEncryption?.setRecipientKey(key)
  }

  @ReactMethod(isBlockingSynchronousMethod = true)
  fun encryptPkEncryption(plaintext: String): WritableMap {
    val olmPkMessage = this.olmPkEncryption?.encrypt(plaintext)
    val map = WritableNativeMap()
    if (olmPkMessage != null) {
      map.putString("ciphertext", olmPkMessage.mCipherText)
      map.putString("mac", olmPkMessage.mMac)
      map.putString("ephemeral_key", olmPkMessage.mEphemeralKey)
    }

    return map
  }

  // =============================================>
  // OlmPkSigning
  // =============================================>
  var olmPkSigning: OlmPkSigning? = null;

  @ReactMethod(isBlockingSynchronousMethod = true)
  fun createPkSigning() {
    this.olmPkSigning = OlmPkSigning();
  }

  @ReactMethod(isBlockingSynchronousMethod = true)
  fun freePkSigning() {
    this.olmPkSigning?.releaseSigning()
  }

  @ReactMethod(isBlockingSynchronousMethod = true)
  fun initPkSigningWithSeed(seed: ReadableArray): String? {
    return this.olmPkSigning?.initWithSeed(ConversionUtils.convertArrayToByteArray(seed))
  }

  @ReactMethod(isBlockingSynchronousMethod = true)
  fun generatePkSigningSeed(): WritableArray {
    return ConversionUtils.convertByteArrayToArray(OlmPkSigning.generateSeed())
  }

  @ReactMethod(isBlockingSynchronousMethod = true)
  fun signPkSigning(message: String): String? {
    return this.olmPkSigning?.sign(message)
  }

  // =============================================>
  // OlmSAS
  // =============================================>
  var olmSAS: OlmSAS? = null;

  @ReactMethod(isBlockingSynchronousMethod = true)
  fun createSAS() {
    this.olmSAS = OlmSAS();
  }

  @ReactMethod(isBlockingSynchronousMethod = true)
  fun freeSAS() {
    this.olmSAS?.releaseSas()
  }

  @ReactMethod(isBlockingSynchronousMethod = true)
  fun getSASPubKey(): String? {
    return this.olmSAS?.publicKey
  }

  @ReactMethod(isBlockingSynchronousMethod = true)
  fun setTheirSASKey(their_key: String) {
    this.olmSAS?.setTheirPublicKey(their_key)
  }

  @ReactMethod(isBlockingSynchronousMethod = true)
  fun generateSASBytes(info: String, length: Int): WritableArray {
    return ConversionUtils.convertByteArrayToArray(this.olmSAS?.generateShortCode(info, length)!!)
  }

  @ReactMethod(isBlockingSynchronousMethod = true)
  fun calculateSASMac(input: String, info: String): String? {
    return this.olmSAS?.calculateMac(input, info)
  }

  @ReactMethod(isBlockingSynchronousMethod = true)
  fun calculateSASMacLongKdf(input: String, info: String): String? {
    return this.olmSAS?.calculateMacLongKdf(input, info)
  }

  // =============================================>
  // OlmSession
  // =============================================>
  var olmSession: OlmSession? = null;

  @ReactMethod(isBlockingSynchronousMethod = true)
  fun createSession() {
    this.olmSession = OlmSession()
  }

  @ReactMethod(isBlockingSynchronousMethod = true)
  fun freeSession() {
    this.olmSession?.releaseSession()
  }

  @ReactMethod(isBlockingSynchronousMethod = true)
  fun pickleSession(key: String): String? {
    return SerializationUtils.serializeForRealm(this.olmSession)
  }

  @ReactMethod(isBlockingSynchronousMethod = true)
  fun unpickleSession(key: String, pickle: String) {
    this.olmSession = SerializationUtils.deserializeFromRealm(pickle)
  }

  @ReactMethod(isBlockingSynchronousMethod = true)
  fun createOutboundSession(
    _account: ReadableMap,
    their_identity_key: String,
    their_one_time_key: String
  ) {
    this.olmSession?.initOutboundSession(this.account, their_identity_key, their_one_time_key)
  }

  @ReactMethod(isBlockingSynchronousMethod = true)
  fun createInboundSession(_account: ReadableMap, one_time_key_message: String) {
    this.olmSession?.initInboundSession(this.account, one_time_key_message)
  }

  @ReactMethod(isBlockingSynchronousMethod = true)
  fun createInboundSessionFrom(
    _account: ReadableMap,
    identity_key: String,
    one_time_key_message: String
  ) {
    this.olmSession?.initInboundSessionFrom(this.account, identity_key, one_time_key_message)
  }

  @ReactMethod(isBlockingSynchronousMethod = true)
  fun getSessionId(): String? {
    return this.olmSession?.sessionIdentifier()
  }

  @ReactMethod(isBlockingSynchronousMethod = true)
  fun sessionHasReceivedMessage(): Boolean? {
    // TODO
    return null
  }

  @ReactMethod(isBlockingSynchronousMethod = true)
  fun sessionMatchesInbound(one_time_key_message: String): Boolean? {
    return this.olmSession?.matchesInboundSession(one_time_key_message)
  }

  @ReactMethod(isBlockingSynchronousMethod = true)
  fun sessionMatchesInboundFrom(identity_key: String, one_time_key_message: String): Boolean? {
    return this.olmSession?.matchesInboundSessionFrom(identity_key, one_time_key_message)
  }

  @ReactMethod(isBlockingSynchronousMethod = true)
  fun encryptSession(plaintext: String): WritableMap? {
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
  fun decryptSession(message_type: Int, message: String): WritableMap {
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
  fun describeSession(): String {
    return "NOT IMPLEMENTED"
  }

  // =============================================>
  // OlmUtility
  // =============================================>
  var olmUtility: OlmUtility? = null;

  @ReactMethod(isBlockingSynchronousMethod = true)
  fun createUtility() {
    this.olmUtility = OlmUtility();
  }

  @ReactMethod(isBlockingSynchronousMethod = true)
  fun freeUtility() {
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
