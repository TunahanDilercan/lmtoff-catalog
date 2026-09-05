package com.lmtoff.catalog.ui.catalog

import androidx.lifecycle.ViewModel
import com.lmtoff.catalog.data.InMemoryProductRepository
import com.lmtoff.catalog.data.Product
import com.lmtoff.catalog.data.ProductRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class CatalogUiState(
    val categories: List<String> = emptyList(),
    val selectedCategory: String = InMemoryProductRepository.ALL_CATEGORY,
    val products: List<Product> = emptyList()
)

/** Ana ekranın kategori filtresi ve ürün listesi durumunu yönetir. */
class CatalogViewModel(
    private val repository: ProductRepository = InMemoryProductRepository()
) : ViewModel() {

    private val _state = MutableStateFlow(
        CatalogUiState(
            categories = repository.allCategories(),
            products = repository.allProducts()
        )
    )
    val state: StateFlow<CatalogUiState> = _state.asStateFlow()

    fun selectCategory(category: String) {
        _state.value = _state.value.copy(
            selectedCategory = category,
            products = repository.productsInCategory(category)
        )
    }

    fun productById(id: Int): Product? = repository.productById(id)
}
