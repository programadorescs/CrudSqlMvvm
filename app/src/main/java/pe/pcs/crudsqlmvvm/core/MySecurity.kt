package pe.pcs.crudsqlmvvm.core

import androidx.security.crypto.EncryptedFile
import androidx.security.crypto.MasterKeys
import pe.pcs.crudsqlmvvm.CrudSqlMvvmApp
import java.io.ByteArrayOutputStream
import java.io.File
import java.nio.charset.StandardCharsets

object MySecurity {

    fun desencriptar(datoEncriptado: String): String {
        // Although you can define your own key generation parameter specification, it's
// recommended that you use the value specified here.
        val keyGenParameterSpec = MasterKeys.AES256_GCM_SPEC
        val mainKeyAlias = MasterKeys.getOrCreate(keyGenParameterSpec)

        val fileToRead = "my_sensitive_data.txt"
        val encryptedFile = EncryptedFile.Builder(
            File("DIRECTORY", fileToRead),
            CrudSqlMvvmApp.getAppContext(),
            mainKeyAlias,
            EncryptedFile.FileEncryptionScheme.AES256_GCM_HKDF_4KB
        ).build()

        val inputStream = encryptedFile.openFileInput()
        val byteArrayOutputStream = ByteArrayOutputStream()
        var nextByte: Int = inputStream.read()
        while (nextByte != -1) {
            byteArrayOutputStream.write(nextByte)
            nextByte = inputStream.read()
        }

        val plaintext: ByteArray = byteArrayOutputStream.toByteArray()

        return plaintext.toString()
    }

    fun encriptar(dato: String): String {
        // Although you can define your own key generation parameter specification, it's
// recommended that you use the value specified here.
        val keyGenParameterSpec = MasterKeys.AES256_GCM_SPEC
        val mainKeyAlias = MasterKeys.getOrCreate(keyGenParameterSpec)

// Create a file with this name, or replace an entire existing file
// that has the same name. Note that you cannot append to an existing file,
// and the filename cannot contain path separators.
        val fileToWrite = "my_sensitive_data.txt"
        val encryptedFile = EncryptedFile.Builder(
            File("Directory", fileToWrite),
            CrudSqlMvvmApp.getAppContext(),
            mainKeyAlias,
            EncryptedFile.FileEncryptionScheme.AES256_GCM_HKDF_4KB
        ).build()

        val fileContent = "MY SUPER-SECRET INFORMATION".toByteArray(StandardCharsets.UTF_8)
        encryptedFile.openFileOutput().apply {
            write(fileContent)
            flush()
            close()
        }

        return fileContent.toString()
    }
}