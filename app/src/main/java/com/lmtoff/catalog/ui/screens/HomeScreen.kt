package com.lmtoff.catalog.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.lmtoff.catalog.data.Product
import com.lmtoff.catalog.ui.components.CartFloatingButton
import com.lmtoff.catalog.ui.components.CatalogHeader
import com.lmtoff.catalog.ui.components.CategoryChip
import com.lmtoff.catalog.ui.components.ProductCard
import com.lmtoff.catalog.ui.theme.LmtoffBlack
import com.lmtoff.catalog.ui.theme.LmtoffText

private const val PRODUCTS_PER_ROW = 2

/** Kategori filtresi ve ürün listesini gösteren ana ekran. */
@Composable
fun HomeScreen(
    categories: List<String>,
    selectedCategory: String,
    products: List<Product>,
    cartCount: Int,
    cartTotal: Int,
    onCategorySelect: (String) -> Unit,
    onCartClick: () -> Unit,
    onProductClick: (Product) -> Unit,
    modifier: Modifier = Modifier
) {
    val productRows = remember(products) { products.chunked(PRODUCTS_PER_ROW) }

    Scaffold(
        modifier = modifier,
        containerColor = LmtoffBlack
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .statusBarsPadding(),
                contentPadding = PaddingValues(bottom = 28.dp)
            ) {
                item { CatalogHeader() }

                item {
                    Text(
                        text = "Kategoriler",
                        style = MaterialTheme.typography.titleMedium,
                        color = LmtoffText,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 20.dp, vertical = 18.dp)
                    )
                }

                item {
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        contentPadding = PaddingValues(horizontal = 20.dp)
                    ) {
                        items(categories) { category ->
                            CategoryChip(
                                text = category,
                                selected = selectedCategory == category,
                                onClick = { onCategorySelect(category) }
                            )
                        }
                    }
                }

                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 20.dp, end = 20.dp, top = 24.dp, bottom = 12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Bottom
                    ) {
                        Column {
                            Text(
                                text = "Öne Çıkan Ürünler",
                                style = MaterialTheme.typography.titleLarge,
                                color = LmtoffText,
                                fontWeight = FontWeight.Black
                            )
                        }
                    }
                }

                items(productRows) { rowProducts ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp, vertical = 6.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        rowProducts.forEach { product ->
                            ProductCard(
                                product = product,
                                onClick = { onProductClick(product) },
                                modifier = Modifier.weight(1f)
                            )
                        }
                        // Tek ürünlü son satırda kartın tüm genişliğe yayılmasını engeller.
                        if (rowProducts.size < PRODUCTS_PER_ROW) {
                            Spacer(modifier = Modifier.weight(1f))
                        }
                    }
                }
            }

            CartFloatingButton(
                cartCount = cartCount,
                cartTotal = cartTotal,
                onClick = onCartClick,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .statusBarsPadding()
                    .padding(top = 14.dp, end = 18.dp)
            )
        }
    }
}
