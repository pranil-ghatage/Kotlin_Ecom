package com.example.eCommerce.Models

import com.fasterxml.jackson.annotation.JsonBackReference
import org.jetbrains.annotations.NotNull
import javax.persistence.*

@Entity
class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id : Int = 0

    var productImg : String = ""

    @NotNull("name is required")
    var name : String = ""

    var price : Double = 0.0

    var quantity : Int = 0

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    var category: Category? = null
}