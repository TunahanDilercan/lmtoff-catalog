package com.lmtoff.catalog.ui

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.lmtoff.catalog.ui.cart.CartViewModel
import com.lmtoff.catalog.ui.catalog.CatalogViewModel
import com.lmtoff.catalog.ui.screens.CartScreen
import com.lmtoff.catalog.ui.screens.HomeScreen
import com.lmtoff.catalog.ui.screens.ProductDetailScreen
import com.lmtoff.catalog.ui.screens.SplashScreen
import com.lmtoff.catalog.util.openWhatsAppCartOrder
import kotlinx.coroutines.delay

private const val SPLASH_DURATION_MS = 2400L

/** Uygulamanın gösterilebilecek ekranları. */
private enum class Screen { Splash, Home, Detail, Cart }

/**
 * Ekranlar arası geçişi ve ViewModel bağlantılarını yöneten kök Composable.
 * Ekran ve seçili ürün bilgisi rememberSaveable ile tutulduğundan, ekran
 * döndürüldüğünde kullanıcı bulunduğu yerde kalır.
 */
@Composable
fun LmtoffApp(
    catalogViewModel: CatalogViewModel = viewModel(),
    cartViewModel: CartViewModel = viewModel()
) {
    val context = LocalContext.current
    val catalogState by catalogViewModel.state.collectAsStateWithLifecycle()
    val cartState by cartViewModel.state.collectAsStateWithLifecycle()

    var screen by rememberSaveable { mutableStateOf(Screen.Splash) }
    var selectedProductId by rememberSaveable { mutableStateOf<Int?>(null) }
    val selectedProduct = selectedProductId?.let(catalogViewModel::productById)

    LaunchedEffect(Unit) {
        delay(SPLASH_DURATION_MS)
        if (screen == Screen.Splash) screen = Screen.Home
    }

    when {
        screen == Screen.Splash -> SplashScreen()

        screen == Screen.Cart -> {
            BackHandler { screen = Screen.Home }
            CartScreen(
                cartLines = cartState.lines,
                total = cartState.total,
                onBack = { screen = Screen.Home },
                onUpdateQuantity = cartViewModel::updateQuantity,
                onCheckout = { lines -> openWhatsAppCartOrder(context, lines) }
            )
        }

        screen == Screen.Detail && selectedProduct != null -> {
            BackHandler { screen = Screen.Home }
            ProductDetailScreen(
                product = selectedProduct,
                cartCount = cartState.itemCount,
                onAddToCart = cartViewModel::add,
                onCartClick = { screen = Screen.Cart },
                onBack = { screen = Screen.Home }
            )
        }

        else -> HomeScreen(
            categories = catalogState.categories,
            selectedCategory = catalogState.selectedCategory,
            products = catalogState.products,
            cartCount = cartState.itemCount,
            cartTotal = cartState.total,
            onCategorySelect = catalogViewModel::selectCategory,
            onCartClick = { screen = Screen.Cart },
            onProductClick = { product ->
                selectedProductId = product.id
                screen = Screen.Detail
            }
        )
    }
}
