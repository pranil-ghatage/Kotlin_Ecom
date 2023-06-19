package com.example.eCommerce.Repositories

import com.example.eCommerce.Models.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepositiory : JpaRepository<User, Int> {
    fun findByEmail(email : String) : User?
}