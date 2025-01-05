package com.teslasoft.hackerapp

import android.util.Base64
import java.nio.charset.StandardCharsets
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.security.SecureRandom
import javax.crypto.Cipher
import javax.crypto.spec.GCMParameterSpec
import javax.crypto.spec.SecretKeySpec

class SecurityX private constructor() {
    companion object {
        private const val AES_MODE = "AES/GCM/NoPadding"
        private const val HASH_ALGORITHM = "SHA-256"

        @Throws(NoSuchAlgorithmException::class)
        private fun generateKey(password: String): SecretKeySpec {
            val digest = MessageDigest.getInstance(HASH_ALGORITHM)
            val bytes = password.toByteArray(StandardCharsets.UTF_8)
            digest.update(bytes, 0, bytes.size)
            val key = digest.digest()
            return SecretKeySpec(key, "AES")
        }

        @Throws(java.security.GeneralSecurityException::class)
        fun encrypt(password: String, message: String): String {
            return try {
                val key = generateKey(password)
                val ivBytes = byteArrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
                val secureRandom = SecureRandom()
                secureRandom.nextBytes(ivBytes)
                val cipherText = encrypt(key, ivBytes, message.toByteArray(Charsets.UTF_8))
                val ivo = base64urlEncode(ivBytes)
                val msg = cipherText.copyOfRange(0, cipherText.size - 16)
                val tag = cipherText.copyOfRange(cipherText.size - 16, cipherText.size)
                val tagx = base64urlEncode(tag)
                val message = base64urlEncode(msg)
                "$ivo.$tagx.$message"
            } catch (e: java.io.UnsupportedEncodingException) {
                throw java.security.GeneralSecurityException(e)
            }
        }

        @Throws(java.security.GeneralSecurityException::class)
        fun encrypt(key: SecretKeySpec, iv: ByteArray, message: ByteArray): ByteArray {
            val cipher = Cipher.getInstance(AES_MODE)
            val ivSpec = GCMParameterSpec(128, iv)
            cipher.init(Cipher.ENCRYPT_MODE, key, ivSpec)
            return cipher.doFinal(message)
        }

        fun base64urlEncode(data: ByteArray): String {
            return Base64.encodeToString(data, Base64.NO_WRAP).replace("+", "-").replace("/", "_").replace("=", "")
        }

        fun base64urlDecode(data: String): ByteArray {
            return Base64.decode(data.replace("-", "+").replace("_", "/"), Base64.NO_WRAP)
        }

        @Throws(java.security.GeneralSecurityException::class)
        fun decrypt(password: String, base64EncodedCipherText: String): String {
            val key = generateKey(password)
            val cipherParts = base64EncodedCipherText.split(".")
            val iv = base64urlDecode(cipherParts[0])
            val tag = base64urlDecode(cipherParts[1])
            val message = base64urlDecode(cipherParts[2])
            val newCipher = message + tag
            val decryptedBytes = decrypt(key, iv, newCipher)
            return String(decryptedBytes, Charsets.UTF_8)
        }

        @Throws(java.security.GeneralSecurityException::class)
        fun decrypt(
            key: SecretKeySpec,
            iv: ByteArray,
            decodedCipherText: ByteArray
        ): ByteArray {
            val cipher = Cipher.getInstance(AES_MODE)
            val ivSpec = GCMParameterSpec(128, iv)
            cipher.init(Cipher.DECRYPT_MODE, key, ivSpec)
            return cipher.doFinal(decodedCipherText)
        }
    }
}