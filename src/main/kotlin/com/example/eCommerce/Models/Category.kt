package com.example.eCommerce.Models

import com.fasterxml.jackson.annotation.JsonManagedReference
import javax.persistence.*

@Entity
class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id : Int = 0

    @Column(name = "name", unique = true, nullable = false)
    var name: String = ""

    @JsonManagedReference
    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    var products: List<Product>? = null

//    constructor(id: Int) {
//        this.id = id
//    }
}