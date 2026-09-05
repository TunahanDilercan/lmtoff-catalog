package com.lmtoff.catalog.data

data class ProductColorOption(
    val label: String,
    val swatch: Long,
    val imageRes: Int
)

data class Product(
    val id: Int,
    val name: String,
    val category: String,
    val price: String,
    val imageRes: Int,
    val description: String,
    val galleryImages: List<Int> = emptyList(),
    val colorOptions: List<ProductColorOption> = emptyList(),
    val inStock: Boolean = true
)
