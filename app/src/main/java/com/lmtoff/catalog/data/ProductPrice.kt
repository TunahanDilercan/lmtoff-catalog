package com.lmtoff.catalog.data

/** "449 TL" gibi bir etiketten sayısal fiyatı çıkarır; ayrıştırılamazsa 0 döner. */
val Product.priceValue: Int
    get() = price.filter { it.isDigit() }.toIntOrNull() ?: 0

/** Sayısal fiyatı arayüzde gösterilen biçime çevirir. */
fun formatPrice(value: Int): String = "$value TL"
