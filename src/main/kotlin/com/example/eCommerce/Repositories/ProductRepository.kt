package com.example.eCommerce.Repositories

import com.example.eCommerce.Models.Product
import org.springframework.data.jpa.repository.JpaRepository

interface ProductRepository : JpaRepository<Product, Int> {

}