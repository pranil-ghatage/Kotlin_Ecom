package com.example.eCommerce.Controllers

import com.example.eCommerce.DTO.LoginDto
import com.example.eCommerce.DTO.Message
import com.example.eCommerce.DTO.RegistorDto
import com.example.eCommerce.Models.User
import com.example.eCommerce.Services.UserServices
import com.example.eCommerce.WebConfig.WebConfig
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CookieValue
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletResponse

@RestController
@RequestMapping("/Auth")
class AuthController(private val userServices: UserServices) {

    @GetMapping("/checkAuthApi")
    fun checkAuthController(): String = "Login controller ok";

    @PostMapping("/register")
    fun register(@RequestBody body: RegistorDto): ResponseEntity<Any> {
        val user = User()
        user.name = body.name
        user.email = body.email
        user.password = body.password

        if (userServices.findByEmail(user.email) != null) {
            return ResponseEntity.badRequest().body(Message("User with this mail already exist"))
        }


        return ResponseEntity.ok(userServices.save(user))
    }

    @PostMapping("/login")
    fun login(@RequestBody body: LoginDto, response: HttpServletResponse): ResponseEntity<Any> {
        val user = userServices.findByEmail(body.email)
                ?: return ResponseEntity.badRequest().body(Message("User not found"))

        if (!user.comparePassword(body.password))
            return ResponseEntity.badRequest().body(Message("Invalid password"))

        val issuer = user.id.toString()
        val jwt = Jwts.builder()
                .setIssuer(issuer)
                .setExpiration(Date(System.currentTimeMillis() + 60 * 60 * 1000))
                .signWith(SignatureAlgorithm.HS512, "secret").compact()

        var cookie = Cookie("jwt", jwt)
        cookie.isHttpOnly = true
        response.addCookie(cookie)
        val ret: HashMap<String, Any> = HashMap()
        ret["msg"] = "login Success"
        ret["token"] = jwt

        return ResponseEntity(ret, HttpStatus.OK)
        return ResponseEntity.ok(Message("login Success"))
    }

    @GetMapping("user")
    fun user(@RequestHeader("Authorization") authorizationHeader: String?): ResponseEntity<Any> {

        val uid = WebConfig.validate(authorizationHeader)
        if (uid == -1) {
            return ResponseEntity.status(401).body(Message("Unauthenticated"))
        }


        return ResponseEntity.ok(this.userServices.getById(uid))


    }

    @GetMapping("logout")
    fun logout(response: HttpServletResponse): ResponseEntity<Any> {
        val cookie = Cookie("jwt", "")
        cookie.maxAge = 0
        response.addCookie(cookie)

        return ResponseEntity.ok(Message("Logout success"))
    }

}