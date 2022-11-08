package pe.pcs.crudsqlmvvm.core

import android.util.Base64
import java.security.Key
import java.security.MessageDigest
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec


object UtilsSecurity {

    // Hay que respetar que la longitud de la clave tenga 16 digitos.
    // Longitud de la clave 16 bytes (lo que quieras y, por supuesto, caracteres válidos)
    private val valor_clave = "Z3$2xD#TRwC@AXCM".toByteArray()

    @Throws(Exception::class)
    fun cifrarDato(dato_a_encriptar: String): String {
        val key: Key = SecretKeySpec(valor_clave, "AES")
        val cipher: Cipher = Cipher.getInstance("AES")
        cipher.init(Cipher.ENCRYPT_MODE, key)
        val encrypted: ByteArray =
            cipher.doFinal(dato_a_encriptar.toByteArray(charset("UTF-8")))

        return Base64.encodeToString(encrypted, Base64.DEFAULT)
    }

    @Throws(Exception::class)
    fun descifrarDato(dato_encriptado: String): String {
        val key: Key = SecretKeySpec(valor_clave, "AES")

        val cipher: Cipher = Cipher.getInstance("AES")
        cipher.init(Cipher.DECRYPT_MODE, key)

        val decodificarTexto: ByteArray =
            Base64.decode(dato_encriptado.toByteArray(charset("UTF-8")), Base64.DEFAULT)

        val desencriptado: ByteArray = cipher.doFinal(decodificarTexto)

        return String(desencriptado, charset("UTF-8"))
    }

    fun CreateSHAHash512(cadena: String): String {
        return try {
            val digest: MessageDigest = MessageDigest.getInstance("SHA-512")
            val hash: ByteArray = digest.digest(cadena.toByteArray(charset("UTF-8")))
            Base64.encodeToString(hash, 0).replace("[\n\r]", "") //replaceAll
        } catch (ex: Exception) {
            throw RuntimeException(ex)
        }
    }

}