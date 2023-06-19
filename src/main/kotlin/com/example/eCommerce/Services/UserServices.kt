package com.example.eCommerce.Services

import com.example.eCommerce.Models.User
import com.example.eCommerce.Repositories.UserRepositiory
import org.springframework.stereotype.Service

@Service
class UserServices(private val userRepositiory: UserRepositiory) {

    fun save(user : User) : User = this.userRepositiory.save(user)

    fun findByEmail(email : String) : User? = this.userRepositiory.findByEmail(email)

    fun getById(id : Int) : User? = this.userRepositiory.getById(id)
}