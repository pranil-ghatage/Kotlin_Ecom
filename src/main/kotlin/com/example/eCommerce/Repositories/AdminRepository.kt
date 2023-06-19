package com.example.eCommerce.Repositories

import com.example.eCommerce.Models.Admin
import org.springframework.data.jpa.repository.JpaRepository

interface AdminRepository: JpaRepository<Admin, Int> {
    fun findByEmail(email : String) : Admin?
}