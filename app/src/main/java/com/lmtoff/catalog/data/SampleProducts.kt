package com.lmtoff.catalog.data

import com.lmtoff.catalog.R

val productCategories = listOf(
    "Tümü",
    "Spor Havlusu",
    "Shaker",
    "Lifting Straps"
)

val sampleProducts = listOf(
    Product(
        id = 1,
        name = "LMTOFF Spor Havlusu",
        category = "Spor Havlusu",
        price = "449 TL",
        imageRes = R.drawable.lmtoff_towel_pink,
        description = "Spor salonu kullanımı için kompakt LMTOFF havlu. Hızlı kuruyan yapısı ve sade marka görünümüyle antrenman ekipmanını tamamlar.",
        colorOptions = listOf(
            ProductColorOption(
                label = "Pembe",
                swatch = 0xFFEBC8CF,
                imageRes = R.drawable.lmtoff_towel_pink
            ),
            ProductColorOption(
                label = "Siyah",
                swatch = 0xFF151A1D,
                imageRes = R.drawable.lmtoff_towel_black
            ),
            ProductColorOption(
                label = "Lacivert",
                swatch = 0xFF071C4D,
                imageRes = R.drawable.lmtoff_towel_navy
            )
        ),
        inStock = true
    ),
    Product(
        id = 2,
        name = "LMTOFF Navy Shaker",
        category = "Shaker",
        price = "699 TL",
        imageRes = R.drawable.lmtoff_shaker,
        description = "Lacivert LMTOFF shaker. Antrenman öncesi ve sonrası kullanım için sızdırmaz kapaklı, sağlam gövdeli ve salon çantasında taşımaya uygun.",
        inStock = false
    ),
    Product(
        id = 3,
        name = "LMTOFF Heavy Lifting Straps",
        category = "Lifting Straps",
        price = "799 TL",
        imageRes = R.drawable.lmtoff_lifting_straps,
        description = "Deadlift, row ve pull hareketlerinde kavrama desteği sağlayan dayanıklı lifting straps. Bileği destekleyen sıkı dokuma yapı, ağır setlerde kontrol hissini artırır.",
        inStock = false
    )
)
