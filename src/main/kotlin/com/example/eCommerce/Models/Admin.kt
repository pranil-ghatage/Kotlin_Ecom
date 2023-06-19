package com.example.eCommerce.Models

import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import javax.persistence.*

@Entity
class Admin {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id : Int = 0

        var name = ""

        @Column(unique = true)
        var email = ""

        var password = ""
            @JsonIgnore
            get() = field
            set(value) {
                val  passwordEncoder = BCryptPasswordEncoder()
                field = passwordEncoder.encode(value)
            }

        fun comparePassword(password : String) : Boolean{
            return BCryptPasswordEncoder().matches(password, this.password)
        }
    }
