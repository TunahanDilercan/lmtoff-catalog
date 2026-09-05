package com.lmtoff.catalog.util

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.lmtoff.catalog.data.Product
import com.lmtoff.catalog.data.formatPrice
import com.lmtoff.catalog.data.priceValue
import com.lmtoff.catalog.ui.cart.CartLine

/** Siparişlerin yönlendirildiği mağaza WhatsApp numarası. */
private const val WHATSAPP_PHONE = "905422926792"

fun openWhatsAppProductInquiry(context: Context, product: Product) {
    openWhatsAppMessage(context, "Merhaba, ${product.name} ürünü hakkında bilgi almak istiyorum.")
}

fun openWhatsAppCartOrder(context: Context, cartLines: List<CartLine>) {
    if (cartLines.isEmpty()) return
    openWhatsAppMessage(context, buildCartOrderMessage(cartLines))
}

/** Sepeti WhatsApp'a gönderilecek düz metin siparişe çevirir. */
fun buildCartOrderMessage(cartLines: List<CartLine>): String {
    val total = cartLines.sumOf { it.product.priceValue * it.quantity }
    val productsText = cartLines.joinToString(separator = "\n") { line ->
        val colorText = line.colorOption?.let { " (${it.label})" }.orEmpty()
        "- ${line.quantity} adet ${line.product.name}$colorText " +
            "(${line.product.price}) = ${formatPrice(line.product.priceValue * line.quantity)}"
    }
    return listOf(
        "Merhaba, aşağıdaki sepet için sipariş vermek istiyorum:",
        productsText,
        "",
        "Toplam: ${formatPrice(total)}"
    ).joinToString(separator = "\n")
}

/**
 * Mesajı sırasıyla WhatsApp, WhatsApp Business ve tarayıcı üzerinden açmayı dener;
 * cihazda hiçbiri yoksa wa.me bağlantısına düşer.
 */
private fun openWhatsAppMessage(context: Context, orderMessage: String) {
    val whatsappUri = Uri.Builder()
        .scheme("https")
        .authority("wa.me")
        .appendPath(WHATSAPP_PHONE)
        .appendQueryParameter("text", orderMessage)
        .build()

    val whatsappIntent = Intent(Intent.ACTION_VIEW, whatsappUri).apply { setPackage("com.whatsapp") }
    val businessIntent = Intent(Intent.ACTION_VIEW, whatsappUri).apply { setPackage("com.whatsapp.w4b") }
    val webIntent = Intent(Intent.ACTION_VIEW, whatsappUri)

    val intent = when {
        whatsappIntent.resolveActivity(context.packageManager) != null -> whatsappIntent
        businessIntent.resolveActivity(context.packageManager) != null -> businessIntent
        else -> webIntent
    }

    runCatching { context.startActivity(intent) }
        .onFailure { context.startActivity(webIntent) }
}
