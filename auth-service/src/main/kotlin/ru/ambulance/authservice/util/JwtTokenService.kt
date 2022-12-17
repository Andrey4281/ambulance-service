package ru.ambulance.authservice.util

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.security.KeyFactory
import java.security.PrivateKey
import java.security.spec.PKCS8EncodedKeySpec
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*
import javax.annotation.PostConstruct

@Service
class JwtTokenService {
    @Value("\${jwt.privateKey}")
    private val privateKeyValue: String? = null

    @Value("\${jwt.livingTimeInHours}")
    private val livingTimeInHours: Int? = null
    private var privateKey: PrivateKey? = null

    @PostConstruct
    private fun init() {
        privateKey = KeyFactory.getInstance("RSA")
                .generatePrivate(
                        PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKeyValue!!.replace("\\n".toRegex(), "")
                                .replace("-----BEGIN PRIVATE KEY-----", "")
                                .replace("-----END PRIVATE KEY-----", "")
                                .replace(" ".toRegex(), ""))))
    }

    fun generate(userLogin: String, userRole: String): String {
        val date = Date.from(LocalDateTime.now().plusHours(livingTimeInHours!!.toLong()).atZone(ZoneId.systemDefault()).toInstant())
        return Jwts.builder()
                .setSubject(userLogin)
                .claim("role", userRole)
                .setExpiration(date)
                .setHeaderParam("alg", "RS256")
                .signWith(privateKey, SignatureAlgorithm.RS256)
                .compact()
    }
}