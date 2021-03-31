package com.reactnativeolm

import android.util.Base64
import java.io.ByteArrayOutputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.util.zip.GZIPInputStream
import java.util.zip.GZIPOutputStream

class SerializationUtils {
  companion object {
    /**
     * Serialize any Serializable object, zip it and convert to Base64 String
     */
    fun serializeForRealm(o: Any?): String? {
      if (o == null) {
        return null
      }

      val baos = ByteArrayOutputStream()
      val gzis = GZIPOutputStream(baos)
      val out = ObjectOutputStream(gzis)
      out.use {
        it.writeObject(o)
      }
      return Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT)
    }

    /**
     * Do the opposite of serializeForRealm.
     */
    @Suppress("UNCHECKED_CAST")
    fun <T> deserializeFromRealm(string: String?): T? {
      if (string == null) {
        return null
      }
      val decodedB64 = Base64.decode(string.toByteArray(), Base64.DEFAULT)

      val bais = decodedB64.inputStream()
      val gzis = GZIPInputStream(bais)
      val ois = ObjectInputStream(gzis)
      return ois.use {
        it.readObject() as T
      }
    }
  }
}
