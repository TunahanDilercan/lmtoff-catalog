package com.lmtoff.catalog.util

import com.lmtoff.catalog.data.Product
import com.lmtoff.catalog.data.ProductColorOption
import com.lmtoff.catalog.data.formatPrice
import com.lmtoff.catalog.data.priceValue
import com.lmtoff.catalog.ui.cart.CartLine
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class WhatsAppOrderTest {

    private val towel = Product(
        id = 1,
        name = "Spor Havlusu",
        category = "Spor Havlusu",
        price = "449 TL",
        imageRes = 0,
        description = "test"
    )

    @Test
    fun `fiyat etiketinden sayisal deger cikarilir`() {
        assertEquals(449, towel.priceValue)
        assertEquals(0, towel.copy(price = "Fiyat sorunuz").priceValue)
    }

    @Test
    fun `fiyat biciminde TL eki bulunur`() {
        assertEquals("1299 TL", formatPrice(1299))
    }

    @Test
    fun `siparis mesaji renk adet ve toplami icerir`() {
        val lines = listOf(
            CartLine(
                product = towel,
                colorOption = ProductColorOption(label = "Pembe", swatch = 0L, imageRes = 0),
                quantity = 2
            )
        )

        val message = buildCartOrderMessage(lines)

        assertTrue(message.contains("2 adet Spor Havlusu (Pembe)"))
        assertTrue(message.contains("Toplam: 898 TL"))
    }
}
