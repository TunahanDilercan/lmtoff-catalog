package com.lmtoff.catalog.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lmtoff.catalog.R
import com.lmtoff.catalog.data.Product
import com.lmtoff.catalog.data.ProductColorOption
import com.lmtoff.catalog.ui.components.ProductColorSelector
import com.lmtoff.catalog.ui.components.ProductImageThumbnail
import com.lmtoff.catalog.ui.theme.LmtoffBlack
import com.lmtoff.catalog.ui.theme.LmtoffBlue
import com.lmtoff.catalog.ui.theme.LmtoffMuted
import com.lmtoff.catalog.ui.theme.LmtoffPanel
import com.lmtoff.catalog.ui.theme.LmtoffPanelLight
import com.lmtoff.catalog.ui.theme.LmtoffSilver
import com.lmtoff.catalog.ui.theme.LmtoffText
import com.lmtoff.catalog.util.openWhatsAppProductInquiry

/** Ürün detayı: görsel galerisi, renk seçimi, açıklama ve sepet aksiyonları. */
@Composable
fun ProductDetailScreen(
    product: Product,
    cartCount: Int,
    onAddToCart: (Product, ProductColorOption?) -> Unit,
    onCartClick: () -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var selectedColor by remember(product.id) { mutableStateOf(product.colorOptions.firstOrNull()) }
    var selectedImageRes by remember(product.id) { mutableStateOf(product.imageRes) }
    val thumbnailImages = remember(product.id) {
        (listOf(product.imageRes) + product.colorOptions.map { it.imageRes } + product.galleryImages)
            .distinct()
    }

    Scaffold(
        modifier = modifier,
        containerColor = LmtoffBlack,
        bottomBar = {
            ProductActionBar(
                product = product,
                cartCount = cartCount,
                onAddToCart = { onAddToCart(product, selectedColor) },
                onWhatsAppClick = { openWhatsAppProductInquiry(context, product) },
                onCartClick = onCartClick
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            item {
                ProductHeroImage(
                    imageRes = selectedImageRes,
                    contentDescription = product.name,
                    onBack = onBack
                )
            }

            if (thumbnailImages.size > 1) {
                item {
                    LazyRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp, vertical = 14.dp),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        items(thumbnailImages) { imageRes ->
                            ProductImageThumbnail(
                                imageRes = imageRes,
                                selected = selectedImageRes == imageRes,
                                onClick = { selectedImageRes = imageRes }
                            )
                        }
                    }
                }
            }

            item {
                ProductInfo(
                    product = product,
                    selectedColor = selectedColor,
                    onColorSelect = { option ->
                        selectedColor = option
                        selectedImageRes = option.imageRes
                    }
                )
            }
        }
    }
}

@Composable
private fun ProductHeroImage(
    imageRes: Int,
    contentDescription: String,
    onBack: () -> Unit
) {
    Box {
        Image(
            painter = painterResource(id = imageRes),
            contentDescription = contentDescription,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1.22f),
            contentScale = ContentScale.Crop
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
                .align(Alignment.BottomCenter)
                .background(
                    Brush.verticalGradient(colors = listOf(Color.Transparent, LmtoffBlack))
                )
        )
        IconButton(
            onClick = onBack,
            modifier = Modifier
                .statusBarsPadding()
                .padding(12.dp)
                .size(44.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(Color.Black.copy(alpha = 0.58f))
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_arrow_back),
                contentDescription = "Geri",
                tint = LmtoffText
            )
        }
    }
}

@Composable
private fun ProductInfo(
    product: Product,
    selectedColor: ProductColorOption?,
    onColorSelect: (ProductColorOption) -> Unit
) {
    Column(modifier = Modifier.padding(horizontal = 20.dp, vertical = 18.dp)) {
        Text(
            text = product.category.uppercase(),
            style = MaterialTheme.typography.labelLarge,
            color = LmtoffBlue,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = product.name,
            style = MaterialTheme.typography.headlineMedium,
            color = LmtoffText,
            fontWeight = FontWeight.Black
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = product.price,
            style = MaterialTheme.typography.headlineSmall,
            color = LmtoffSilver,
            fontWeight = FontWeight.Black
        )
        if (product.colorOptions.isNotEmpty()) {
            Spacer(modifier = Modifier.height(18.dp))
            ProductColorSelector(
                options = product.colorOptions,
                selectedOption = selectedColor,
                onSelect = onColorSelect
            )
        }
        if (!product.inStock) {
            Spacer(modifier = Modifier.height(14.dp))
            OutOfStockNotice()
        }
        Spacer(modifier = Modifier.height(22.dp))
        Text(
            text = "Ürün Açıklaması",
            style = MaterialTheme.typography.titleMedium,
            color = LmtoffText,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = product.description,
            style = MaterialTheme.typography.bodyLarge,
            color = LmtoffMuted
        )
        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
private fun OutOfStockNotice() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(LmtoffPanelLight)
            .padding(14.dp)
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(
                text = "STOK YOK",
                style = MaterialTheme.typography.labelLarge,
                color = LmtoffBlue,
                fontWeight = FontWeight.Black
            )
            Text(
                text = "Bu ürün şu anda sepete eklenemez. WhatsApp üzerinden bilgi isteyebilirsin.",
                style = MaterialTheme.typography.bodyMedium,
                color = LmtoffMuted
            )
        }
    }
}

@Composable
private fun ProductActionBar(
    product: Product,
    cartCount: Int,
    onAddToCart: () -> Unit,
    onWhatsAppClick: () -> Unit,
    onCartClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .navigationBarsPadding()
            .background(LmtoffBlack)
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Button(
            onClick = onAddToCart,
            enabled = product.inStock,
            modifier = Modifier
                .fillMaxWidth()
                .height(54.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = LmtoffBlue,
                contentColor = LmtoffBlack,
                disabledContainerColor = LmtoffPanelLight,
                disabledContentColor = LmtoffMuted
            )
        ) {
            Text(
                text = if (product.inStock) "Sepete Ekle" else "Stok Yok",
                fontWeight = FontWeight.Black
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Button(
                onClick = onWhatsAppClick,
                modifier = Modifier
                    .weight(1f)
                    .height(48.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = LmtoffPanelLight,
                    contentColor = LmtoffText
                )
            ) {
                CompactButtonLabel(text = "WhatsApp Sor")
            }
            Button(
                onClick = onCartClick,
                enabled = cartCount > 0,
                modifier = Modifier
                    .weight(1f)
                    .height(48.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = LmtoffSilver,
                    contentColor = LmtoffBlack,
                    disabledContainerColor = LmtoffPanel,
                    disabledContentColor = LmtoffMuted
                )
            ) {
                CompactButtonLabel(text = "Sepet ($cartCount)")
            }
        }
    }
}

/** Dar butonlarda metnin taşmasını engelleyen tek satırlık etiket. */
@Composable
private fun CompactButtonLabel(text: String) {
    Text(
        text = text,
        fontWeight = FontWeight.Bold,
        fontSize = 12.sp,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        softWrap = false
    )
}
