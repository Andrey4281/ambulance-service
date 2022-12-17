package ru.ambulance.authservice.util

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import ru.ambulance.function.logger
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import kotlin.experimental.and

@Component
class PasswordUtil {
    @Value("\${security.salt}")
    private val salt: String? = null

    private val log = logger()

    fun getSecuredPassword(passwordToHash: String): String? {
        var generatedPassword: String? = null
        try {
            val md = MessageDigest.getInstance("SHA-256")
            md.update(salt!!.toByteArray())
            val bytes = md.digest(passwordToHash.toByteArray())
            val sb = StringBuilder()
            for (byte in bytes) {
                sb.append(((byte.and(0xff.toByte())) + 0x100).toString(16)
                        .substring(1))
            }
            generatedPassword = sb.toString()
        } catch (e: NoSuchAlgorithmException) {
            log.error(e.message, e)
        }
        return generatedPassword
    }
}