package com.example.eCommerce.WebConfig

import com.example.eCommerce.DTO.Message
import io.jsonwebtoken.Jwts
import org.springframework.context.annotation.Configuration
import org.springframework.http.ResponseEntity
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
@EnableWebMvc
class WebConfig : WebMvcConfigurer {
    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:4200")
                .allowCredentials(true)
    }

    companion object {
        fun validate(token: String?): Int {
            val jwt = extractJwtToken(token)
            try {
                if (jwt != null) {
                    val body = Jwts.parser().setSigningKey("secret").parseClaimsJws(jwt).body
                    return body.issuer.toInt()
                }
            } catch (e: Exception) {
                return -1
            }
            return -1
        }
    }
}

fun extractJwtToken(authorizationHeader: String?): String? {
    if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
        return authorizationHeader.substring(7)
    }
    return null
}