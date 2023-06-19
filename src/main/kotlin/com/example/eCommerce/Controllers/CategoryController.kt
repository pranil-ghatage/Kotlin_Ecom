package com.example.eCommerce.Controllers

import com.example.eCommerce.DTO.CategoryDto
import com.example.eCommerce.DTO.Message
import com.example.eCommerce.Models.Category
import com.example.eCommerce.Services.CategoryService
import com.example.eCommerce.WebConfig.WebConfig
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("category")
class CategoryController(private val categoryService: CategoryService) {

    @GetMapping("/checkCategoryApi")
    fun checkCategoryController() : String = "Category controller ok";

    @PostMapping("/addCategory")
    fun addCategory(@RequestBody body : CategoryDto) : ResponseEntity<Any>
    {
        var category = Category()
        category.name = body.name
        categoryService.save(category)

        val ret: HashMap<String, Any> = HashMap()
        ret["msg"] = "List of Category"
        ret["List"] = categoryService.getAllCategory()
        return ResponseEntity.ok(ret)
    }

    @GetMapping("/getAllCategory")
    fun getProductsList(@RequestHeader("Authorization") authorizationHeader: String?): ResponseEntity<Any> {
        val uid = WebConfig.validate(authorizationHeader)
        if (uid == -1) {
            return ResponseEntity.status(401).body(Message("Unauthenticated"))
        }
        var list1 = categoryService.getAllCategory()

        val ret: HashMap<String, Any> = HashMap()
        ret["msg"] = "List of Category"
        ret["List"] = list1

        return ResponseEntity(ret, HttpStatus.OK)
    }
}