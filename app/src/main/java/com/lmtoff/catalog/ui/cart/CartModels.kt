package com.lmtoff.catalog.ui.cart

import com.lmtoff.catalog.data.Product
import com.lmtoff.catalog.data.ProductColorOption

/** Sepetteki bir satırın kimliği: aynı ürünün farklı rengi ayrı satırdır. */
data class CartSelection(
    val productId: Int,
    val colorLabel: String?
)

/** Sepet ekranında gösterilen, ürün bilgisiyle zenginleştirilmiş satır. */
data class CartLine(
    val product: Product,
    val colorOption: ProductColorOption?,
    val quantity: Int
)

data class CartUiState(
    val lines: List<CartLine> = emptyList(),
    val itemCount: Int = 0,
    val total: Int = 0
) {
    val isEmpty: Boolean get() = lines.isEmpty()
}
