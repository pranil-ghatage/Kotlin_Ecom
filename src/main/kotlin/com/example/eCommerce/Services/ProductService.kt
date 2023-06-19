package com.example.eCommerce.Services

import com.example.eCommerce.Models.Product
import com.example.eCommerce.Repositories.ProductRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.util.Optional

@Service
class ProductService(val productRepository: ProductRepository) {

    fun save(product : Product) : Product
    {
        return productRepository.save(product)
    }

    fun getById(id : Int) : Optional<Product>
    {
        return  productRepository.findById(id)
//        if(product == null) {
//            return null
//        }
//        return product
    }

    fun getProducts() : Collection<Product>
    {
        return productRepository.findAll()
    }

    fun deleteById(id : Int)
    {
        productRepository.deleteById(id)
    }
}