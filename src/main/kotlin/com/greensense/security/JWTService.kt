package com.greensense.security

import com.greensense.model.Usuario
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.security.Key
import java.util.Date

@Service
class JWTService(
    @Value("\${jwt.secret}")
    secret: String
) {
    private val secretKey: Key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret))

    fun generateToken(usuario: Usuario): String {
        return Jwts.builder()
            .setSubject(usuario.username)
            .claim("role", usuario.role.name)
            .setIssuedAt(Date())
            .setExpiration(Date(System.currentTimeMillis() + 86400000)) // 1 dia
            .signWith(secretKey)
            .compact()
    }

    fun extractUsername(token: String): String =
        Jwts.parserBuilder()
            .setSigningKey(secretKey)
            .build()
            .parseClaimsJws(token)
            .body
            .subject
}
