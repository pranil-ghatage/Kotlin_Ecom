package com.example.eCommerce.Repositories

import com.example.eCommerce.Models.Category
import org.springframework.data.jpa.repository.JpaRepository

interface CategoryRepository : JpaRepository<Category, Int> {
}