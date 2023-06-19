package com.example.eCommerce.Controllers

import com.example.eCommerce.DTO.LoginDto
import com.example.eCommerce.DTO.Message
import com.example.eCommerce.DTO.RegistorDto
import com.example.eCommerce.Models.Admin
import com.example.eCommerce.Services.AdminService
import com.example.eCommerce.WebConfig.WebConfig
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletResponse

@RestController
@RequestMapping("/admin")
class AdminAuthController(private val adminServices: AdminService) {

    @GetMapping("/checkAdminApi")
    fun checkAdminAuthController() : String = "Admin controller ok";

    @PostMapping("/register")
    fun registerAdmin(@RequestBody body : RegistorDto) : ResponseEntity<Admin>
    {

        val admin = Admin()
        admin.name = body.name
        admin.email = body.email
        admin.password = body.password

        return ResponseEntity.ok(adminServices.save(admin))
    }

    @PostMapping("/login")
    fun login(@RequestBody body : LoginDto, response : HttpServletResponse) : ResponseEntity<Any> {
        val admin = adminServices.findByEmail(body.email) ?: return ResponseEntity.badRequest().body(Message("Admin not found"))

        if(!admin.comparePassword(body.password))
            return ResponseEntity.badRequest().body(Message("Invalid password"))

        val issuer = admin.id.toString()
        val jwt = Jwts.builder()
                .setIssuer(issuer)
                .setExpiration(Date(System.currentTimeMillis() + 60 * 1 * 1000))
                .signWith(SignatureAlgorithm.HS512, "secret").compact()

        var cookie =  Cookie("jwt", jwt)
        cookie.isHttpOnly = true
        response.addCookie(cookie)
        return ResponseEntity.ok(Message("login Success"))
    }

    @GetMapping("admin")
    fun user(@CookieValue("jwt") jwt : String, @RequestHeader("Authorization") authorizationHeader: String?) : ResponseEntity<Any>{
        val uid = WebConfig.validate(authorizationHeader)
        if (uid == -1) {
            return ResponseEntity.status(401).body(Message("Unauthenticated"))
        }


        return ResponseEntity.ok(this.adminServices.getById(uid))

    }

    @GetMapping("logout")
    fun logout(response: HttpServletResponse) :ResponseEntity<Any>{
        val cookie = Cookie("jwt", "")
        cookie.maxAge = 0
        response.addCookie(cookie)

        return ResponseEntity.ok(Message("Logout success"))
    }

}