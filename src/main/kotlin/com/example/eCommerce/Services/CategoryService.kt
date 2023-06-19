package com.example.eCommerce.Services

import com.example.eCommerce.Models.Admin
import com.example.eCommerce.Models.Category
import com.example.eCommerce.Repositories.CategoryRepository
import org.springframework.stereotype.Service

@Service
class CategoryService(private val categoryRepository: CategoryRepository) {

    fun save(category: Category) : Category = this.categoryRepository.save(category)

    fun getAllCategory() : Collection<Category> = this.categoryRepository.findAll()
}