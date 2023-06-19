package com.example.eCommerce.WebConfig

import com.example.eCommerce.Models.User
import org.springframework.security.core.Authentication

fun Authentication.toUser(): User {
    return principal as User
}