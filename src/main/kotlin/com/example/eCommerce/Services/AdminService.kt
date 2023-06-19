package com.example.eCommerce.Services

import com.example.eCommerce.Models.Admin
import com.example.eCommerce.Repositories.AdminRepository
import org.springframework.stereotype.Service

@Service
class AdminService(private val adminRepository: AdminRepository) {
    fun save(admin: Admin) : Admin = this.adminRepository.save(admin)

    fun findByEmail(email : String) : Admin? = this.adminRepository.findByEmail(email)

    fun getById(id : Int) : Admin = this.adminRepository.getById(id)
}