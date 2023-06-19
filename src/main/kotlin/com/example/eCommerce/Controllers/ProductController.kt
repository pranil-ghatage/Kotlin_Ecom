package com.example.eCommerce.Controllers

import com.example.eCommerce.DTO.Message
import com.example.eCommerce.DTO.ProductDto
import com.example.eCommerce.Models.Product
import com.example.eCommerce.Services.ProductService
import com.example.eCommerce.WebConfig.WebConfig
import io.jsonwebtoken.Jwts
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/product")
class ProductController(private val productService: ProductService) {

    @GetMapping("/checkProductApi")
    fun checkProductController(): String = "Product controller ok";

    @PostMapping("/add")
    fun register(@RequestBody body: ProductDto, @RequestHeader("Authorization") authorizationHeader: String?): ResponseEntity<Any> {

        val uid = WebConfig.validate(authorizationHeader)
        if (uid == -1) {
            return ResponseEntity.status(401).body(Message("Unauthenticated"))
        }
        var product = Product()
        product.name = body.name
        product.productImg = body.productImg
        product.price = body.price
        product.quantity = body.quantity
        product.category = body.category
        productService.save(product)
        val ret: HashMap<String, Any> = HashMap()
        ret["msg"] = "List of Products"
        ret["List"] = productService.getProducts()
        return ResponseEntity.ok(ret)
    }

    @GetMapping("/getAllProducts")
    fun getProductsList(@RequestHeader("Authorization") authorizationHeader: String?): ResponseEntity<Any> {
        val uid = WebConfig.validate(authorizationHeader)
        if (uid == -1) {
            return ResponseEntity.status(401).body(Message("Unauthenticated"))
        }
        var list1 = productService.getProducts()

        val ret: HashMap<String, Any> = HashMap()
        ret["msg"] = "List of Products"
        ret["List"] = list1

        return ResponseEntity(ret, HttpStatus.OK)
    }


    @GetMapping("/{id}")
    fun getById(@PathVariable id: Int, @RequestHeader("Authorization") authorizationHeader: String?): ResponseEntity<Any> {

        val uid = WebConfig.validate(authorizationHeader)
        if (uid == -1) {
            return ResponseEntity.status(401).body(Message("Unauthenticated"))
        }

        var result = productService.getById(id)
        if (!result.isPresent)
            return ResponseEntity.status(401).body(Message("Data not available for given id"))


        return ResponseEntity.ok(result.get())
    }

    @PostMapping("/delete/{id}")
    fun deleteProduct(@PathVariable id: Int, @RequestHeader("Authorization") authorizationHeader: String?): ResponseEntity<Any> {
        val uid = WebConfig.validate(authorizationHeader)
        if (uid == -1) {
            return ResponseEntity.status(401).body(Message("Unauthenticated"))
        }
        val ret: HashMap<String, Any> = HashMap()
        productService.deleteById(id)
        ret["msg"] = "List of Products"
        ret["List"] = productService.getProducts()
        return ResponseEntity.ok(ret)
    }

    @PostMapping("/update/{id}")
    fun updateProduct(@PathVariable id: Int, @RequestHeader("Authorization") authorizationHeader: String?, @RequestBody product: Product) : ResponseEntity<Any>{
        val uid = WebConfig.validate(authorizationHeader)
        if (uid == -1) {
            return ResponseEntity.status(401).body(Message("Unauthenticated"))
        }

        productService.save(product)

        val ret: HashMap<String, Any> = HashMap()
        ret["msg"] = "updated"
        ret["List"] = productService.getProducts()
        return ResponseEntity.ok(ret)
    }
}